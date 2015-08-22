package mashin.oep.model;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.hpdl.XMLWriteUtils;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.connection.WorkflowConnectionEndPoint;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.action.basic.FSActionNode;
import mashin.oep.model.node.action.basic.JavaActionNode;
import mashin.oep.model.node.action.basic.MapReduceActionNode;
import mashin.oep.model.node.action.basic.PigActionNode;
import mashin.oep.model.node.action.basic.SubWorkflowActionNode;
import mashin.oep.model.node.action.extended.CustomActionNode;
import mashin.oep.model.node.action.extended.DistcpActionNode;
import mashin.oep.model.node.action.extended.EmailActionNode;
import mashin.oep.model.node.action.extended.Hive2ActionNode;
import mashin.oep.model.node.action.extended.HiveActionNode;
import mashin.oep.model.node.action.extended.SSHActionNode;
import mashin.oep.model.node.action.extended.ShellActionNode;
import mashin.oep.model.node.action.extended.SparkActionNode;
import mashin.oep.model.node.action.extended.SqoopActionNode;
import mashin.oep.model.node.control.DecisionNode;
import mashin.oep.model.node.control.EndNode;
import mashin.oep.model.node.control.ForkNode;
import mashin.oep.model.node.control.JoinNode;
import mashin.oep.model.node.control.KillNode;
import mashin.oep.model.node.control.StartNode;
import mashin.oep.model.node.sort.NodeSort;
import mashin.oep.model.node.sort.TopologicalNodeSort;
import mashin.oep.model.property.CredentialPropertyElement;
import mashin.oep.model.property.GlobalPropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.eclipse.draw2d.geometry.Point;

public class Workflow extends ModelElementWithSchema {

  private static final List<SchemaVersion> WORKFLOW_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2,
                    SchemaVersion.V_0_2_5, SchemaVersion.V_0_3,
                    SchemaVersion.V_0_4, SchemaVersion.V_0_4_5,
                    SchemaVersion.V_0_5);
  private static final SchemaVersion WORKFLOW_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_5;
  private static final SchemaVersion WORKFLOW_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_5;

  public static final String PROP_NAME           = "prop.workflow.name";
  public static final String PROP_PROPERTY       = "prop.workflow.property";
  public static final String PROP_GLOBAL         = "prop.workflow.global";
  public static final String PROP_CREDENTIALS    = "prop.workflow.credentials";
  
  public static final String PROP_NODE_ADDED     = "prop.workflow.node.added";
  public static final String PROP_NODE_REMOVED   = "prop.workflow.node.removed";

  private static final String CATEGORY_PARAMETERS   = "Parameters";
  private static final String CATEGORY_CREDENTIALS  = "Credentials";

  private TextPropertyElement       name;
  private PropertyElementCollection parameters;
  private GlobalPropertyElement     global;
  private PropertyElementCollection credentials;

  private StartNode   startNode;
  private EndNode     endNode;
  private List<Node>  nodes;
  
  private NodeSort    nodeSort;
  
  public Workflow() {
    this(null);
  }
  
  public Workflow(org.dom4j.Node hpdlNode) {
    super();
    
    name = new TextPropertyElement(PROP_NAME, "Name");
    addPropertyElement(name);
    
    parameters = new PropertyElementCollection(CATEGORY_PARAMETERS,
                        new PropertyPropertyElement(PROP_PROPERTY, "Property"));
    addPropertyElement(parameters);
    
    global = new GlobalPropertyElement(PROP_GLOBAL, "Global");
    addPropertyElement(global);
    
    credentials = new PropertyElementCollection(CATEGORY_CREDENTIALS,
                        new CredentialPropertyElement(PROP_CREDENTIALS, "Credential"));
    addPropertyElement(credentials);
    
    nodes = new ArrayList<Node>();
    
    if (hpdlNode == null) {
      startNode = new StartNode(this);
      startNode.init();
      startNode.setPosition(new Point(50, 100));
      
      endNode = new EndNode(this);
      endNode.init();
      endNode.setPosition(new Point(500, 100));
      endNode.setName("end");
      
      nodes.add(startNode);
      nodes.add(endNode);
    } else {
      read(hpdlNode);
    }
  }

  public void setName(String name) {
    setPropertyValue(PROP_NAME, name);
  }

  public String getName() {
    return name.getStringValue();
  }

  public void setJobtracker(String jobtracker) {
    global.setValueOfJobtracker(jobtracker);
  }

  public void setNamenode(String namenode) {
    global.setValueOfNamenode(namenode);
  }

  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return WORKFLOW_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return WORKFLOW_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return WORKFLOW_LATEST_SCHEMA_VERSION;
  }

  public boolean canAddNode(Node node) {
    return true;
  }
  
  public void addNode(Node node) {
    nodes.add(node);
    firePropertyChange(PROP_NODE_ADDED, null, node);
  }
  
  public void removeNode(Node node) {
    nodes.remove(node);
    firePropertyChange(PROP_NODE_REMOVED, node, null);
  }
  
  public List<Node> getNodes() {
    return nodes;
  }

  @Override
  public void write(org.dom4j.Element parent) {
    Document document = parent.getDocument();
    parent.detach();
    
    Element rootElement = document.addElement("workflow-app");
    Element graphicalInfoElement = DocumentHelper.createElement("workflow");
    
    XMLWriteUtils.writeWorkflowSchemaVersion(getSchemaVersion(), rootElement);
    XMLWriteUtils.writeTextPropertyAsAttribute(name, rootElement, "name");
    XMLWriteUtils.writePropertiesCollection(parameters, rootElement, "parameters", "property");
    XMLWriteUtils.writeGlobalProperty(global, rootElement);
    XMLWriteUtils.writeCredentialsCollection(credentials, rootElement);
    
    for (Node node : nodes) {
      node.write(rootElement);
      graphicalInfoElement.addElement("node")
        .addAttribute("name", node.getName())
        .addAttribute("x", node.getPosition().x + "")
        .addAttribute("y", node.getPosition().y + "");
    }
    
    Comment graphicalInfoNode = null;
    try {
      StringWriter stringWriter = new StringWriter();
      XMLWriter writer = new XMLWriter(stringWriter, OutputFormat.createPrettyPrint());
      writer.write(graphicalInfoElement);
      writer.flush();
      graphicalInfoNode = DocumentHelper.createComment(stringWriter.toString());
    } catch (Exception e) {
      graphicalInfoNode = DocumentHelper.createComment(graphicalInfoElement.asXML());
    }
    document.add(graphicalInfoNode);
  }

  @Override
  public void read(org.dom4j.Node hpdlNode) {
    Document document = (Document) hpdlNode;
    
    Element rootElement = document.getRootElement();
    
    HashMap<String, Point> graphicalInfoMap = XMLReadUtils.graphicalInfoFrom(document);
    
    XMLReadUtils.initSchemaVersionFrom(rootElement, this, schemaVersion);
    XMLReadUtils.initTextPropertyFrom(this, PROP_NAME, name, "@name", rootElement);
    XMLReadUtils.initPropertiesCollectionFrom(parameters, rootElement, "./parameters", "./property");
    XMLReadUtils.initGlobalPropertyFrom(global, rootElement, "./global");
    XMLReadUtils.initCredentialsCollectionFrom(credentials, rootElement, "./credentials", "./credential");
    
    nodeSort = new TopologicalNodeSort(this);
    
    // nodes
    List<org.dom4j.Node> hpdlNodes = XMLReadUtils.nodesList(rootElement);
    for (org.dom4j.Node hpdlChildNode : hpdlNodes) {
      nodes.add(workflowNodeFromHPDLNode(hpdlChildNode, graphicalInfoMap));
    }
    
    // connections
    initConnections();
    
    if (nodeSort.needsSort()) {
      nodeSort.sort();
    }
  }
  
  private Node workflowNodeFromHPDLNode(org.dom4j.Node hpdlNode, HashMap<String, Point> graphicalInfoMap) {
    Node node = null;
    switch (hpdlNode.getName()) {
    case "start":
      node = new StartNode(this, hpdlNode);
      break;
    case "end":
      node = new EndNode(this, hpdlNode);
      break;
    case "decision":
      node = new DecisionNode(this, hpdlNode);
      break;
    case "fork":
      node = new ForkNode(this, hpdlNode);
      break;
    case "join":
      node = new JoinNode(this, hpdlNode);
      break;
    case "kill":
      node = new KillNode(this, hpdlNode);
      break;
    case "action":
      if (hpdlNode.selectSingleNode("./map-reduce") != null) {
        node = new MapReduceActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./pig") != null) {
        node = new PigActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./sub-workflow") != null) {
        node = new SubWorkflowActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./fs") != null) {
        node = new FSActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./java") != null) {
        node = new JavaActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./distcp") != null) {
        node = new DistcpActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./hive") != null) {
        node = new HiveActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./hive2") != null) {
        node = new Hive2ActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./shell") != null) {
        node = new ShellActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./spark") != null) {
        node = new SparkActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./sqoop") != null) {
        node = new SqoopActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./ssh") != null) {
        node = new SSHActionNode(this, hpdlNode);
      } else if (hpdlNode.selectSingleNode("./email") != null) {
        node = new EmailActionNode(this, hpdlNode);
      } else if (XMLReadUtils.schemaVersionParentNode(hpdlNode) != null) {
        node = new CustomActionNode(this, hpdlNode);
      }
      break;
    }
    node.init(hpdlNode);
    Point point = graphicalInfoMap.get(node.getName());
    if (point != null) {
      node.setPosition(point);
    } else {
      nodeSort.add(node);
    }
    return node;
  }
  
  private void initConnections() {
    HashMap<String, Node> nodeMap = new HashMap<String, Node>(nodes.size());
    for (Node node : nodes) {
      nodeMap.put(node.getName(), node);
    }
    
    for (Node node : nodes) {
      List<WorkflowConnection> sourceConns = new ArrayList<WorkflowConnection>(
          node.getSourceConnections());
      node.getSourceConnections().clear();
      
      for (WorkflowConnection sourceConn : sourceConns) {
        WorkflowConnectionEndPoint target = sourceConn.getTarget();
        Node targetNode = nodeMap.get(target.getNodeAsString());
        target.setNode(targetNode);
        if (targetNode != null) {
          target.setTerminal(targetNode.getTerminal(target
              .getTerminalAsString()));
          sourceConn.reconnect();
        }
      }
    }
  }
  
  @SuppressWarnings({ "rawtypes", "unused" })
  public static void main(String[] args) {
    
    try {
      
      String hpdlPath = "test.xml";
      
      SAXReader reader = new SAXReader();
      Document document;
      Element rootElement;
      
      document = reader.read(new File(hpdlPath));
      document.accept(new XMLReadUtils.NameSpaceCleaner());
      rootElement = document.getRootElement();
      
      rootElement.selectNodes("./prepare/aa/@c");
      rootElement.selectNodes("./prepare/bb/@c");
      
      System.exit(0);
      
      hpdlPath = "workflow1.workflow";
      
      document = reader.read(new File(hpdlPath));
      document.accept(new XMLReadUtils.NameSpaceCleaner());
      rootElement = document.getRootElement();
      
      ((DefaultElement) rootElement).getNamespace();
      ((DefaultElement) rootElement).getNamespaceURI();
      ((DefaultElement) rootElement).getNamespacePrefix();
      
      List nnodes1 = rootElement.selectNodes("./parameters/property");
      
      @SuppressWarnings("unchecked")
      Iterator<org.dom4j.Node> iter = document.nodeIterator();
      Pattern p = Pattern.compile("\\s*<workflow>.*</workflow>\\s*", Pattern.DOTALL);
      while (iter.hasNext()) {
        org.dom4j.Node xmlNode = iter.next();
        if (xmlNode.getNodeType() == org.dom4j.Node.COMMENT_NODE) {
          System.out.println(xmlNode.getNodeTypeName());
          System.out.println(xmlNode.getPath());
          System.out.println(xmlNode.getText());
          if (p.matcher(xmlNode.getText()).find()) {
            System.out.println("Hiiiiiiiiiiiiiiiiiii");
          }
          System.out.println("=======================");
        }
      }
      System.out.println(rootElement.getNodeTypeName());
      System.out.println(rootElement.getPath());
      
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    
  }

}
