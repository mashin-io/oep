package mashin.oep.hpdl;

import java.util.List;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.connection.WorkflowCaseConnection;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.node.action.basic.FSActionNode;
import mashin.oep.model.property.CheckBoxPropertyElement;
import mashin.oep.model.property.CredentialPropertyElement;
import mashin.oep.model.property.GlobalPropertyElement;
import mashin.oep.model.property.PipesPropertyElement;
import mashin.oep.model.property.PreparePropertyElement;
import mashin.oep.model.property.PropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.StreamingPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

import org.dom4j.Attribute;
import org.dom4j.Element;

public class XMLWriteUtils {

  public static void writeSchemaVersion(SchemaVersion schemaVersion, Element element, String nodeType) {
    element.addAttribute("schema-version", "uri:oozie:" + nodeType + "-action:" + schemaVersion);
  }
  
  public static void writeWorkflowSchemaVersion(SchemaVersion schemaVersion, Element element) {
    element.addAttribute("schema-version", "uri:oozie:workflow:" + schemaVersion);
  }
  
  public static void writeConnectionsAsAttribute(
      List<WorkflowConnection> connections, Element parent, String name) {
    for (WorkflowConnection conn : connections) {
      parent.addAttribute(name, conn.getTargetNode().getName());
    }
  }
  
  public static void writeConnectionsAsElementWithAttribute(
      List<WorkflowConnection> connections, Element parent, String elementName,
      String attributeName) {
    for (WorkflowConnection conn : connections) {
      parent.addElement(elementName)
        .addAttribute(attributeName, conn.getTargetNode().getName());
    }
  }
  
  public static void writeCaseConnections(List<WorkflowConnection> connections,
      Element parent) {
    for (WorkflowConnection conn : connections) {
      parent.addElement("case")
        .addAttribute("to", conn.getTargetNode().getName())
        .setText(((WorkflowCaseConnection) conn).getCondition());
    }
  }
  
  public static void writeTextPropertyAsAttribute(TextPropertyElement pe, Element parent, String name) {
    Attribute node = (Attribute) pe.get();
    if (node == null) {
      parent.addAttribute(name, pe.getStringValue());
      pe.set(parent.selectSingleNode("@" + name));
    } else {
      node.setValue(pe.getStringValue());
    }
  }
  
  public static void writeTextPropertyAsElement(TextPropertyElement pe, Element parent, String name) {
    Element node = (Element) pe.get();
    if (node == null) {
      node = parent.addElement(name);
      node.setText(pe.getStringValue());
      pe.set(node);
    } else {
      node.setText(pe.getStringValue());
    }
  }
  
  public static void writeCheckPropertyAsElement(CheckBoxPropertyElement pe, Element parent, String name) {
    Element node = (Element) pe.get();
    if (node == null) {
      node = parent.addElement(name);
      pe.set(node);
    }
    
    if (!pe.getBooleanValue()) {
      node.detach();
      pe.set(null);
    }
  }
  
  public static void writeTextCollectionAsElements(PropertyElementCollection pec, Element parent, String name) {
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      TextPropertyElement tpe = (TextPropertyElement) pe;
      Element node = (Element) tpe.get();
      if (node == null) {
        node = parent.addElement(name);
        node.setText(tpe.getStringValue());
        tpe.set(node);
      } else {
        node.setText(tpe.getStringValue());
        parent.add(node);
      }
    }
  }
  
  public static void writeTextCollectionAsElementsWithAttribute(
      PropertyElementCollection pec, Element parent, String elementName, String attributeName) {
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      TextPropertyElement tpe = (TextPropertyElement) pe;
      Element node = (Element) tpe.get();
      if (node == null) {
        node = parent.addElement(elementName);
        node.addAttribute(attributeName, tpe.getStringValue());
        tpe.set(node);
      } else {
        node.addAttribute(attributeName, tpe.getStringValue());
        parent.add(node);
      }
    }
  }
  
  public static void writePropertiesCollection(PropertyElementCollection pec,
      Element parent, String groupName, String elementName) {
    Element groupElement = null;
    if (groupName != null && !groupName.isEmpty()) {
      groupElement = (Element) pec.get();
      if (groupElement == null) {
        groupElement = parent.addElement(groupName);
        pec.set(groupElement);
      }
    } else {
      groupElement = parent;
    }
    
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      PropertyPropertyElement ppe = (PropertyPropertyElement) pe;
      Element node = (Element) ppe.get();
      if (node == null) {
        node = groupElement.addElement(elementName);
        ppe.set(node);
      } else {
        groupElement.add(node);
      }
      writeTextPropertyAsElement(ppe.name, node, "name");
      writeTextPropertyAsElement(ppe.value, node, "value");
      writeTextPropertyAsElement(ppe.description, node, "description");
    }
  }
  
  public static void writeFSDeleteCollection(PropertyElementCollection pec, Element parent) {
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      FSActionNode.FSOperationDelete delete = (FSActionNode.FSOperationDelete) pe;
      Element node = (Element) delete.get();
      if (node == null) {
        node = parent.addElement("delete");
        delete.set(node);
      } else {
        parent.add(node);
      }
      writeTextPropertyAsAttribute(delete.path, node, "path");
    }
  }
  
  public static void writeFSMkdirCollection(PropertyElementCollection pec, Element parent) {
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      FSActionNode.FSOperationMkdir mkdir = (FSActionNode.FSOperationMkdir) pe;
      Element node = (Element) mkdir.get();
      if (node == null) {
        node = parent.addElement("mkdir");
        mkdir.set(node);
      } else {
        parent.add(node);
      }
      writeTextPropertyAsAttribute(mkdir.path, node, "path");
    }
  }
  
  public static void writeFSMoveCollection(PropertyElementCollection pec, Element parent) {
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      FSActionNode.FSOperationMove move = (FSActionNode.FSOperationMove) pe;
      Element node = (Element) move.get();
      if (node == null) {
        node = parent.addElement("move");
        move.set(node);
      } else {
        parent.add(node);
      }
      writeTextPropertyAsAttribute(move.source, node, "source");
      writeTextPropertyAsAttribute(move.target, node, "target");
    }
  }
  
  public static void writeFSChmodCollection(PropertyElementCollection pec, Element parent) {
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      FSActionNode.FSOperationChmod chmod = (FSActionNode.FSOperationChmod) pe;
      Element node = (Element) chmod.get();
      if (node == null) {
        node = parent.addElement("chmod");
        chmod.set(node);
      } else {
        parent.add(node);
      }
      writeCheckPropertyAsElement(chmod.recursive, node, "recursive");
      writeTextPropertyAsAttribute(chmod.path, node, "path");
      writeTextPropertyAsAttribute(chmod.permissions, node, "permissions");
      writeTextPropertyAsAttribute(chmod.dirFiles, node, "dir-files");
    }
  }
  
  public static void writeFSTouchzCollection(PropertyElementCollection pec, Element parent) {
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      FSActionNode.FSOperationTouchz touchz = (FSActionNode.FSOperationTouchz) pe;
      Element node = (Element) touchz.get();
      if (node == null) {
        node = parent.addElement("touchz");
        touchz.set(node);
      } else {
        parent.add(node);
      }
      writeTextPropertyAsAttribute(touchz.path, node, "path");
    }
  }
  
  public static void writeFSChgrpCollection(PropertyElementCollection pec, Element parent) {
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      FSActionNode.FSOperationChgrp chgrp = (FSActionNode.FSOperationChgrp) pe;
      Element node = (Element) chgrp.get();
      if (node == null) {
        node = parent.addElement("chgrp");
        chgrp.set(node);
      } else {
        parent.add(node);
      }
      writeCheckPropertyAsElement(chgrp.recursive, node, "recursive");
      writeTextPropertyAsAttribute(chgrp.path, node, "path");
      writeTextPropertyAsAttribute(chgrp.group, node, "group");
      writeTextPropertyAsAttribute(chgrp.dirFiles, node, "dir-files");
    }
  }
  
  public static void writePrepareProperty(PreparePropertyElement ppe, Element parent, String groupName) {
    Element groupElement = (Element) ppe.get();
    if (groupElement == null) {
      groupElement = parent.addElement(groupName);
      ppe.set(groupElement);
    }
    writeTextCollectionAsElementsWithAttribute(ppe.delete, groupElement, "delete", "path");
    writeTextCollectionAsElementsWithAttribute(ppe.mkdir, groupElement, "mkdir", "path");
  }
  
  public static void writeStreamingProperty(StreamingPropertyElement spe, Element parent) {
    Element streamingNode = (Element) spe.get();
    if (streamingNode == null) {
      streamingNode = parent.addElement("streaming");
      spe.set(streamingNode);
    } else {
      parent.add(streamingNode);
    }
    writeTextPropertyAsElement(spe.mapper, streamingNode, "mapper");
    writeTextPropertyAsElement(spe.reducer, streamingNode, "reducer");
    writeTextPropertyAsElement(spe.recordReader, streamingNode, "record-reader");
    writeTextCollectionAsElements(spe.recordReaderMapping, streamingNode, "record-reader-mapping");
    writeTextCollectionAsElements(spe.env, streamingNode, "env");
  }
  
  public static void writePipesProperty(PipesPropertyElement ppe, Element parent) {
    Element pipesNode = (Element) ppe.get();
    if (pipesNode == null) {
      pipesNode = parent.addElement("pipes");
      ppe.set(pipesNode);
    } else {
      parent.add(pipesNode);
    }
    writeTextPropertyAsElement(ppe.map, pipesNode, "map");
    writeTextPropertyAsElement(ppe.reduce, pipesNode, "reduce");
    writeTextPropertyAsElement(ppe.inputFormat, pipesNode, "inputformat");
    writeTextPropertyAsElement(ppe.outputFormat, pipesNode, "outputformat");
    writeTextPropertyAsElement(ppe.partitioner, pipesNode, "partitioner");
    writeTextPropertyAsElement(ppe.writer, pipesNode, "writer");
    writeTextPropertyAsElement(ppe.program, pipesNode, "program");
  }
  
  public static void writeGlobalProperty(GlobalPropertyElement gpe, Element parent) {
    Element element = (Element) gpe.get();
    if (element == null) {
      element = parent.addElement("global");
      gpe.set(element);
    } else {
      parent.add(element);
    }
    writeTextPropertyAsElement(gpe.jobtracker, element, "job-tracker");
    writeTextPropertyAsElement(gpe.namenode, element, "name-node");
    writeTextCollectionAsElements(gpe.jobxml, element, "job-xml");
    writePropertiesCollection(gpe.configuration, element, "configuration", "property");
  }
  
  public static void writeCredentialsCollection(PropertyElementCollection pec, Element parent) {
    Element element = (Element) pec.get();
    if (element == null) {
      element = parent.addElement("credentials");
      pec.set(element);
    } else {
      parent.add(element);
    }
    
    List<PropertyElement> list = pec.getPropertyElements();
    for (PropertyElement pe : list) {
      CredentialPropertyElement cpe = (CredentialPropertyElement) pe;
      Element node = (Element) cpe.get();
      if (node == null) {
        node = parent.addElement("credential");
        cpe.set(node);
      } else {
        parent.add(node);
      }
      writeTextPropertyAsAttribute(cpe.name, node, "name");
      writeTextPropertyAsAttribute(cpe.type, node, "type");
      writePropertiesCollection(cpe.credential, node, "", "property");
    }
  }
  
}
