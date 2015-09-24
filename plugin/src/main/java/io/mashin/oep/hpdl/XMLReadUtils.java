package io.mashin.oep.hpdl;

import io.mashin.oep.model.ModelElement;
import io.mashin.oep.model.ModelElementWithSchema;
import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.node.action.basic.FSActionNode;
import io.mashin.oep.model.property.CheckBoxPropertyElement;
import io.mashin.oep.model.property.ComboBoxPropertyElement;
import io.mashin.oep.model.property.CredentialPropertyElement;
import io.mashin.oep.model.property.GlobalPropertyElement;
import io.mashin.oep.model.property.PipesPropertyElement;
import io.mashin.oep.model.property.PreparePropertyElement;
import io.mashin.oep.model.property.PropertyElementCollection;
import io.mashin.oep.model.property.PropertyPropertyElement;
import io.mashin.oep.model.property.StreamingPropertyElement;
import io.mashin.oep.model.property.TextPropertyElement;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.eclipse.draw2d.geometry.Point;

public class XMLReadUtils {

  public static final class NameSpaceCleaner extends VisitorSupport {
    public void visit(Document document) {
      Element rootElement = document.getRootElement();
      if (rootElement.getNamespaceURI() != null && !rootElement.getNamespaceURI().isEmpty()) {
        rootElement.addAttribute("schema-version", rootElement.getNamespaceURI());
      }
      ((DefaultElement) document.getRootElement())
          .setNamespace(Namespace.NO_NAMESPACE);
      document.getRootElement().additionalNamespaces().clear();
    }

    public void visit(Namespace namespace) {
      namespace.detach();
    }

    public void visit(Attribute node) {
      if (node.getName().contains("xmlns")) {
        node.setName("schema-version");
      } else if (node.getName().contains("xsi:")) {
        node.detach();
      }
    }

    public void visit(Element node) {
      if (node instanceof DefaultElement) {
        DefaultElement de = (DefaultElement) node;
        de.setNamespace(Namespace.NO_NAMESPACE);
      }
    }
  }
  
  public static HashMap<String, Point> graphicalInfoFrom(Document document) {
    HashMap<String, Point> graphicalInfoMap = new HashMap<String, Point>();
    try {
      SAXReader reader = new SAXReader();
      Pattern p = Pattern.compile("\\s*<workflow>.*</workflow>\\s*", Pattern.DOTALL);
      
      @SuppressWarnings("unchecked")
      Iterator<Node> iter = document.nodeIterator();
      while (iter.hasNext()) {
        
        Node xmlNode = iter.next();
        if (xmlNode.getNodeType() == Node.COMMENT_NODE) {
          
          String graphicalInfo = xmlNode.getText();
          if (p.matcher(graphicalInfo).find()) {
            
            Element graphicalElement = reader.read(new StringReader(graphicalInfo))
                                              .getRootElement();
            @SuppressWarnings("unchecked")
            Iterator<Node> gIter = graphicalElement.nodeIterator();
            
            while (gIter.hasNext()) {
              Node gNode = gIter.next();
              if (gNode.getName() != null && gNode.getName().equals("node")) {
                graphicalInfoMap.put(gNode.valueOf("@name"), new Point(
                    Integer.parseInt(gNode.valueOf("@x")),
                    Integer.parseInt(gNode.valueOf("@y"))
                    ));
              }
            }
            break;
            
          }
          
        }
        
      }
    } catch(DocumentException ex) {
      ex.printStackTrace();
    }
    return graphicalInfoMap;
  }
  
  public static Point getNodePosition(String name, HashMap<String, Point> graphicalInfoMap) {
    return graphicalInfoMap.getOrDefault(name, new Point(0, 0));
  }
  
  public static void initSchemaVersionFrom(Node node, ModelElementWithSchema model, ComboBoxPropertyElement pe) {
    model.setSchemaVersion(schemaVersion(node));
  }
  
  public static void setSchemaVersion(String xpath, Node node, ModelElementWithSchema model, ComboBoxPropertyElement pe) {
    model.setSchemaVersion(schemaVersion(xpath, node));
  }
  
  public static Node schemaVersionNode(Node node) {
    return schemaVersionNode("", node);
  }
  
  public static Node schemaVersionNode(String xpath, Node node) {
    return node.selectSingleNode(xpath + "@schema-version");
  }
  
  public static Node schemaVersionParentNode(Node node) {
    Node[] parentNode = new Node[1];
    node.accept(new VisitorSupport() {
      @Override
      public void visit(Attribute node) {
        if (node.getName().equalsIgnoreCase("schema-version")) {
          parentNode[0] = node.getParent();
        }
      }
    });
    return parentNode[0];
  }
  
  public static String schemaVersion(Node node) {
    return schemaVersion("", node);
  }
  
  public static String schemaVersion(String xpath, Node node) {
    String xmlns = node.valueOf(xpath + "@schema-version");
    return schemaVersion(xmlns);
  }
  
  public static String schemaVersion(String xmlns) {
    if (xmlns != null && !xmlns.isEmpty()) {
      int schemaVersionIndex = xmlns.lastIndexOf(":");
      if (schemaVersionIndex > -1) {
        return xmlns.substring(schemaVersionIndex + 1, xmlns.length());
      }
    }
    return SchemaVersion.V_ANY.toString();
  }
  
  public static void initTextPropertyFrom(ModelElement model, String prop, TextPropertyElement pe, String xpath, Node node) {
    model.setPropertyValue(prop, valueOf(node.selectSingleNode(xpath)));
  }
  
  public static void initTextPropertyFrom(TextPropertyElement pe, Node node, String xpath) {
    pe.setStringValue(valueOf(node.selectSingleNode(xpath)));
  }
  
  public static void initCheckPropertyFrom(CheckBoxPropertyElement pe, Node node, String xpath) {
    pe.setValue(!valueOf(xpath, node).isEmpty());
  }
  
  public static String valueOf(String xpath, Node node) {
    String value = "";
    if (node != null) {
      value = node.valueOf(xpath);
    }
    return value == null ? "" : value;
  }
  
  public static String valueOf(Node node) {
    String value = "";
    if (node != null) {
      value = node.getStringValue();
    }
    return value == null ? "" : value;
  }
  
  @SuppressWarnings("unchecked")
  public static void initPropertiesCollectionFrom(
      PropertyElementCollection pec, Node node, String groupXPath,
      String propertyXPath) {
    List<Node> subNodes = null;
    if (groupXPath != null && !groupXPath.isEmpty()) {
      if (node.selectSingleNode(groupXPath) != null) {
        subNodes = node.selectSingleNode(groupXPath)
                        .selectNodes(propertyXPath);
      }
    } else {
      subNodes = node.selectNodes(propertyXPath);
    }
    
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        PropertyPropertyElement pe = (PropertyPropertyElement) pec.createAndAdd();
        pe.setValueOfName(XMLReadUtils.valueOf("./name", subNode));
        pe.setValueOfValue(XMLReadUtils.valueOf("./value", subNode));
        pe.setValueOfDescription(XMLReadUtils.valueOf("./description", subNode));
      }
    }
  }
  
  public static PropertyElementCollection propertiesCollectionFrom(
      Node node, String xpath, String category, String id, String name) {
    PropertyElementCollection pec = new PropertyElementCollection(category, new PropertyPropertyElement(id, name));
    return propertiesCollectionFrom(node, xpath, pec);
  }
  
  public static PropertyElementCollection propertiesCollectionFrom(
      Node node, String xpath, PropertyElementCollection pec) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        initPropertyPropertyElementFrom((PropertyPropertyElement) pec.createAndAdd(), subNode);
      }
    }
    return pec;
  }
  
  private static void initPropertyPropertyElementFrom(PropertyPropertyElement ppe, Node node) {
    String propName = "";
    String propValue = "";
    String propDescription = "";
    
    Node n = node.selectSingleNode("name");
    if (n != null) {
      propName = n.getText();
    }
    
    n = node.selectSingleNode("value");
    if (n != null) {
      propValue = n.getText();
    }
    
    n = node.selectSingleNode("description");
    if (n != null) {
      propDescription = n.getText();
    }
    
    ppe.setValueOfName(propName);
    ppe.setValueOfValue(propValue);
    ppe.setValueOfDescription(propDescription);
  }
  
  public static void initGlobalPropertyFrom(GlobalPropertyElement gpe, Node node, String xpath) {
    Node globalNode = node.selectSingleNode(xpath);
    if (globalNode != null) {
      initTextPropertyFrom(gpe.jobtracker, globalNode, "./job-tracker");
      initTextPropertyFrom(gpe.namenode, globalNode, "./name-node");
      initTextCollectionFrom(gpe.jobxml, globalNode, "./job-xml");
      initPropertiesCollectionFrom(gpe.configuration, globalNode, "./configuration", "./property");
    }
  }
  
  public static void initPreparePropertyFrom(PreparePropertyElement ppe, Node node, String xpath) {
    Node prepareNode = node.selectSingleNode(xpath);
    if (prepareNode != null) {
      initTextCollectionFromAttribute(ppe.delete, prepareNode, "./delete", "@path");
      initTextCollectionFromAttribute(ppe.mkdir, prepareNode, "./mkdir", "@path");
    }
  }
  
  public static void initCredentialsCollectionFrom(PropertyElementCollection pec, Node node, String groupXPath, String childXPath) {
    Node groupNode = node.selectSingleNode(groupXPath);
    if (groupNode != null) {
      @SuppressWarnings("unchecked")
      List<Node> subNodes = groupNode.selectNodes(childXPath);
      if (subNodes != null) {
        for (Node subNode : subNodes) {
          CredentialPropertyElement cpe = (CredentialPropertyElement) pec.createAndAdd();
          cpe.setValueOfName(valueOf("@name", subNode));
          cpe.setValueOfType(valueOf("@type", subNode));
          initPropertiesCollectionFrom(cpe.credential, subNode, null, "./property");
        }
      }
    }
  }
  
  public static void initStreamingPropertyFrom(StreamingPropertyElement spe, Node node, String xpath) {
    Node streamingNode = node.selectSingleNode(xpath);
    if (streamingNode != null) {
      initTextPropertyFrom(spe.mapper, streamingNode, "./mapper");
      initTextPropertyFrom(spe.reducer, streamingNode, "./reducer");
      initTextPropertyFrom(spe.recordReader, streamingNode, "./record-reader");
      initTextCollectionFrom(spe.recordReaderMapping, streamingNode, "./record-reader-mapping");
      initTextCollectionFrom(spe.env, streamingNode, "./env");
    }
  }
  
  public static void initPipesPropertyFrom(PipesPropertyElement ppe, Node node, String xpath) {
    Node pipesNode = node.selectSingleNode(xpath);
    if (pipesNode != null) {
      initTextPropertyFrom(ppe.map, pipesNode, "./map");
      initTextPropertyFrom(ppe.reduce, pipesNode, "./reduce");
      initTextPropertyFrom(ppe.inputFormat, pipesNode, "./inputformat");
      initTextPropertyFrom(ppe.outputFormat, pipesNode, "./outputformat");
      initTextPropertyFrom(ppe.partitioner, pipesNode, "./partitioner");
      initTextPropertyFrom(ppe.writer, pipesNode, "./writer");
      initTextPropertyFrom(ppe.program, pipesNode, "./program");
    }
  }
  
  public static PropertyElementCollection credentialsCollectionFrom(
      Node element, String xpath, String category, String id, String name) {
    PropertyElementCollection pec = new PropertyElementCollection(category,
        new CredentialPropertyElement(id, name));
    return credentialsCollectionFrom(element, xpath, pec);
  }
  
  public static PropertyElementCollection credentialsCollectionFrom(
      Node element, String xpath, PropertyElementCollection pec) {
    @SuppressWarnings("unchecked")
    List<Node> nodes = element.selectNodes(xpath);
    if (nodes != null) {
      for (Node node : nodes) {
        initCredentialPropertyElementFrom(
            (CredentialPropertyElement) pec.createAndAdd(), node, pec.getCategory());
      }
    }
    return pec;
  }
  
  private static void initCredentialPropertyElementFrom(
      CredentialPropertyElement cpe, Node node, String category) {
    cpe.setValueOfName(valueOf("@name", node));
    cpe.setValueOfType(valueOf("@type", node));
    cpe.setValueOfCredential(propertiesCollectionFrom(node, "./property", category, cpe.getId(), cpe.getName()));
  }
  
  public static void initTextCollectionFrom(PropertyElementCollection pec, Node node, String xpath) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        TextPropertyElement tpe = (TextPropertyElement) pec.createAndAdd();
        tpe.setStringValue(valueOf(subNode));
      }
    }
  }
  
  public static void initTextCollectionFromAttribute(PropertyElementCollection pec, Node node, String xpath, String attributeXPath) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        TextPropertyElement tpe = (TextPropertyElement) pec.createAndAdd();
        tpe.setStringValue(valueOf(attributeXPath, subNode));
      }
    }
  }
  
  public static PropertyElementCollection textCollectionFrom(Node element,
      String xpath, String category, String id, String name) {
    PropertyElementCollection pec = new PropertyElementCollection(category, new TextPropertyElement(id, name));
    return textCollectionFrom(element, xpath, pec);
  }
  
  public static PropertyElementCollection textCollectionFrom(Node element,
      String xpath, PropertyElementCollection pec) {
    @SuppressWarnings("unchecked")
    List<Node> nodes = element.selectNodes(xpath);
    if (nodes != null) {
      for (Node node : nodes) {
        TextPropertyElement tpe = (TextPropertyElement) pec.createAndAdd();
        String value = node.getText();
        if (value != null) {
          tpe.setStringValue(value);
        }
      }
    }
    return pec;
  }
  
  public static List<Node> nodesList(Element rootElement) {
    List<Node> list = new ArrayList<Node>();
    @SuppressWarnings("unchecked")
    Iterator<Node> iter = rootElement.nodeIterator();
    while (iter.hasNext()) {
      Node node = iter.next();
      if (node.getNodeType() == Node.ELEMENT_NODE
          && node.getName() != null
          && !node.getName().isEmpty()) {
        switch (node.getName()) {
        case "start":
        case "end":
        case "decision":
        case "fork":
        case "join":
        case "kill":
        case "action":
          list.add(node);
        }
      }
    }
    return list;
  }
  
  public static void initFSDeleteCollectionFrom(PropertyElementCollection pec, Node node,
      String xpath) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        FSActionNode.FSOperationDelete delete = (FSActionNode.FSOperationDelete) pec.createAndAdd();
        initTextPropertyFrom(delete.path, subNode, "@path");
      }
    }
  }
  
  public static void initFSMkdirCollectionFrom(PropertyElementCollection pec, Node node,
      String xpath) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        FSActionNode.FSOperationMkdir mkdir = (FSActionNode.FSOperationMkdir) pec.createAndAdd();
        initTextPropertyFrom(mkdir.path, subNode, "@path");
      }
    }
  }
  
  public static void initFSMoveCollectionFrom(PropertyElementCollection pec, Node node,
      String xpath) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        FSActionNode.FSOperationMove move = (FSActionNode.FSOperationMove) pec.createAndAdd();
        initTextPropertyFrom(move.source, subNode, "@source");
        initTextPropertyFrom(move.target, subNode, "@target");
      }
    }
  }
  
  public static void initFSChmodCollectionFrom(PropertyElementCollection pec, Node node,
      String xpath) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        FSActionNode.FSOperationChmod chmod = (FSActionNode.FSOperationChmod) pec.createAndAdd();
        initCheckPropertyFrom(chmod.recursive, subNode, "./recursive");
        initTextPropertyFrom(chmod.path, subNode, "@path");
        initTextPropertyFrom(chmod.permissions, subNode, "@permissions");
        initTextPropertyFrom(chmod.dirFiles, subNode, "@dir-files");
      }
    }
  }
  
  public static void initFSTouchzCollectionFrom(PropertyElementCollection pec, Node node,
      String xpath) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        FSActionNode.FSOperationTouchz touchz = (FSActionNode.FSOperationTouchz) pec.createAndAdd();
        initTextPropertyFrom(touchz.path, subNode, "@path");
      }
    }
  }
  
  public static void initFSChgrpCollectionFrom(PropertyElementCollection pec, Node node,
      String xpath) {
    @SuppressWarnings("unchecked")
    List<Node> subNodes = node.selectNodes(xpath);
    if (subNodes != null) {
      for (Node subNode : subNodes) {
        FSActionNode.FSOperationChgrp chgrp = (FSActionNode.FSOperationChgrp) pec.createAndAdd();
        initCheckPropertyFrom(chgrp.recursive, subNode, "./recursive");
        initTextPropertyFrom(chgrp.path, subNode, "@path");
        initTextPropertyFrom(chgrp.group, subNode, "@group");
        initTextPropertyFrom(chgrp.dirFiles, subNode, "@dir-files");
      }
    }
  }
  
}
