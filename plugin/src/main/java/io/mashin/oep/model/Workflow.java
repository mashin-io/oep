/**
 * Copyright (c) 2015 Mashin (http://mashin.io). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mashin.oep.model;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.connection.WorkflowCaseConnection;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.connection.WorkflowConnectionDummyEndPoint;
import io.mashin.oep.model.connection.WorkflowConnectionEndPoint;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.action.basic.FSActionNode;
import io.mashin.oep.model.node.action.basic.JavaActionNode;
import io.mashin.oep.model.node.action.basic.MapReduceActionNode;
import io.mashin.oep.model.node.action.basic.PigActionNode;
import io.mashin.oep.model.node.action.basic.SubWorkflowActionNode;
import io.mashin.oep.model.node.action.extended.CustomActionNode;
import io.mashin.oep.model.node.action.extended.DistcpActionNode;
import io.mashin.oep.model.node.action.extended.EmailActionNode;
import io.mashin.oep.model.node.action.extended.Hive2ActionNode;
import io.mashin.oep.model.node.action.extended.HiveActionNode;
import io.mashin.oep.model.node.action.extended.SSHActionNode;
import io.mashin.oep.model.node.action.extended.ShellActionNode;
import io.mashin.oep.model.node.action.extended.SparkActionNode;
import io.mashin.oep.model.node.action.extended.SqoopActionNode;
import io.mashin.oep.model.node.control.DecisionNode;
import io.mashin.oep.model.node.control.EndNode;
import io.mashin.oep.model.node.control.ForkNode;
import io.mashin.oep.model.node.control.JoinNode;
import io.mashin.oep.model.node.control.KillNode;
import io.mashin.oep.model.node.control.StartNode;
import io.mashin.oep.model.node.sort.NodeSort;
import io.mashin.oep.model.node.sort.TopologicalNodeSort;
import io.mashin.oep.model.property.ComboBoxPropertyElement;
import io.mashin.oep.model.property.CredentialPropertyElement;
import io.mashin.oep.model.property.GlobalPropertyElement;
import io.mashin.oep.model.property.PropertyElementCollection;
import io.mashin.oep.model.property.PropertyPropertyElement;
import io.mashin.oep.model.property.SLAPropertyElement;
import io.mashin.oep.model.property.TextPropertyElement;
import io.mashin.oep.model.property.filter.SchemaVersionRangeFilter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.draw2d.geometry.Point;


public class Workflow extends ModelElementWithSchema implements HasSLAVersion {

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
  public static final String PROP_SLA_VERSION    = "prop.workflow.sla-version";
  public static final String PROP_SLA            = "prop.workflow.sla";
  
  public static final String PROP_NODE_ADDED     = "prop.workflow.node.added";
  public static final String PROP_NODE_REMOVED   = "prop.workflow.node.removed";

  private static final String CATEGORY_PARAMETERS   = "Parameters";
  private static final String CATEGORY_CREDENTIALS  = "Credentials";

  private TextPropertyElement       name;
  private PropertyElementCollection parameters;
  private GlobalPropertyElement     global;
  private PropertyElementCollection credentials;
  private ComboBoxPropertyElement   slaVersion;
  private SLAPropertyElement        sla;

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
                        new PropertyPropertyElement(PROP_PROPERTY, "Property"),
                        new SchemaVersionRangeFilter(SchemaVersion.V_0_4, SchemaVersion.V_ANY, this));
    addPropertyElement(parameters);
    
    global = new GlobalPropertyElement(PROP_GLOBAL, "Global",
        new SchemaVersionRangeFilter(SchemaVersion.V_0_4, SchemaVersion.V_ANY, this));
    addPropertyElement(global);
    
    credentials = new PropertyElementCollection(CATEGORY_CREDENTIALS,
                        new CredentialPropertyElement(PROP_CREDENTIALS, "Credential"),
                        new SchemaVersionRangeFilter(SchemaVersion.V_0_2_5, SchemaVersion.V_ANY, this));
    addPropertyElement(credentials);
    
    slaVersion = new ComboBoxPropertyElement(PROP_SLA_VERSION, "SLA Version");
    setSLAVersion();
    addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
        case PROP_SCHEMA_VERSION:
          setSLAVersion();
          break;
        }
      }
    });
    addPropertyElement(slaVersion);
    
    sla = new SLAPropertyElement(PROP_SLA, "SLA", this,
        new SchemaVersionRangeFilter(SchemaVersion.V_0_2, SchemaVersion.V_ANY, this)
        .and(pe -> SchemaVersion.V_0_1.equals(getSLAVersion())),
        new SchemaVersionRangeFilter(SchemaVersion.V_0_5, SchemaVersion.V_ANY, this)
        .and(pe -> SchemaVersion.V_0_2.equals(getSLAVersion())));
    addPropertyElement(sla);
    
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

  @Override
  public void setSLAVersion(SchemaVersion slaVersion) {
    int index = Arrays.asList(this.slaVersion.getLabelsArray())
        .indexOf(slaVersion.toString());
    setPropertyValue(PROP_SLA_VERSION, index);
  }
  
  @Override
  public SchemaVersion getSLAVersion() {
    return (SchemaVersion) slaVersion.getContentValue();
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

  public boolean hasNode(String nodeName) {
    return nodes.stream().anyMatch(node -> node.getName().equals(nodeName));
  }
  
  public synchronized String nextId(String nodeNamePrefix) {
    long id = nodes
        .stream()
        .filter(node -> node.getName().matches(nodeNamePrefix + "-[0-9]+"))
        .mapToLong(node -> Long.parseLong(node.getName().substring(
              node.getName().lastIndexOf("-") + 1, node.getName().length())))
        .max().orElse(0) + 1;
    return nodeNamePrefix + "-" + id;
  }
  
  @Override
  public void write(org.dom4j.Element parent) {
    Document document = parent.getDocument();
    parent.detach();
    
    Element rootElement = document.addElement("workflow-app");
    Element graphicalInfoElement = DocumentHelper.createElement("workflow");
    
    XMLWriteUtils.writeWorkflowSchemaVersion(getSchemaVersion(), rootElement);
    XMLWriteUtils.writeSLAVersion(this, rootElement);
    XMLWriteUtils.writeTextPropertyAsAttribute(name, rootElement, "name");
    XMLWriteUtils.writePropertiesCollection(parameters, rootElement, "parameters", "property");
    XMLWriteUtils.writeGlobalProperty(global, rootElement);
    XMLWriteUtils.writeCredentialsCollection(credentials, rootElement);
    
    startNode.write(rootElement);
    for (Node node : nodes) {
      if (!(node.equals(startNode) || node.equals(endNode))) {
        node.write(rootElement);
      }
      graphicalInfoElement.addElement("node")
        .addAttribute("name", node.getName())
        .addAttribute("x", node.getPosition().x + "")
        .addAttribute("y", node.getPosition().y + "");
    }
    endNode.write(rootElement);
    
    XMLWriteUtils.writeSLAProperty(this, sla, rootElement);
    
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
    
    XMLReadUtils.initSchemaVersionFrom(rootElement, this);
    XMLReadUtils.initSLAVersionFrom(rootElement, this);
    XMLReadUtils.initSLAPropertyFrom(sla, rootElement, "./sla:info");
    XMLReadUtils.initTextPropertyFrom(this, PROP_NAME, name, "@name", rootElement);
    XMLReadUtils.initPropertiesCollectionFrom(parameters, rootElement, "./parameters", "./property");
    XMLReadUtils.initGlobalPropertyFrom(global, rootElement, "./global");
    XMLReadUtils.initCredentialsCollectionFrom(credentials, rootElement, "./credentials", "./credential");
    
    nodeSort = new TopologicalNodeSort(this);
    
    // nodes
    List<org.dom4j.Node> hpdlNodes = XMLReadUtils.nodesList(rootElement);
    for (org.dom4j.Node hpdlChildNode : hpdlNodes) {
      Node node = workflowNodeFromHPDLNode(hpdlChildNode, graphicalInfoMap);
      nodes.add(node);
      if (node instanceof StartNode) {
        startNode = (StartNode) node;
      } else if (node instanceof EndNode) {
        endNode = (EndNode) node;
      }
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
      } else {
        throw new RuntimeException(
            "Unrecognized action node; may be 'xmlns' is not provided: "
                + hpdlNode.asXML());
      }
      break;
    }
    
    if (node == null) {
      throw new RuntimeException("Couldn't parse node: " + hpdlNode.asXML());
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
        WorkflowConnectionDummyEndPoint dummyTarget = sourceConn.getDummyTarget();
        Node targetNode = nodeMap.get(dummyTarget.getNode());
        if (targetNode != null) {
          WorkflowConnectionEndPoint concreteTarget = new WorkflowConnectionEndPoint(
              targetNode, targetNode.getTerminal(dummyTarget.getTerminal()));
          if (sourceConn instanceof WorkflowCaseConnection) {
            WorkflowCaseConnection caseConn = (WorkflowCaseConnection) sourceConn;
            WorkflowCaseConnection caseConnConcrete = new WorkflowCaseConnection(
                sourceConn.getSource(), concreteTarget);
            caseConnConcrete.setCondition(caseConn.getCondition());
            caseConnConcrete.reconnect();
          } else {
            new WorkflowConnection(sourceConn.getSource(), concreteTarget).reconnect();
          }
        }
      }
    }
  }
  
  private void setSLAVersion() {
    List<String> labels = new ArrayList<>(Arrays.asList(SchemaVersion.V_ANY.toString()));
    List<SchemaVersion> values = new ArrayList<>(Arrays.asList(SchemaVersion.V_ANY));
    
    if (getSchemaVersion().isGreaterThanOrEqual(SchemaVersion.V_0_2)) {
      labels.add(SchemaVersion.V_0_1.toString());
      values.add(SchemaVersion.V_0_1);
    }
    
    if (getSchemaVersion().isGreaterThanOrEqual(SchemaVersion.V_0_5)) {
      labels.add(SchemaVersion.V_0_2.toString());
      values.add(SchemaVersion.V_0_2);
    }
    
    String[] labelsArray = labels.toArray(new String[0]);
    SchemaVersion[] valuesArray = values.toArray(new SchemaVersion[0]);
    
    if (((Integer) slaVersion.getValue()) >= labelsArray.length) {
      slaVersion.setValue(new Integer(labelsArray.length - 1));
    }
    
    slaVersion.setLabelsArray(labelsArray);
    slaVersion.setValuesArray(valuesArray);
  }
  
}
