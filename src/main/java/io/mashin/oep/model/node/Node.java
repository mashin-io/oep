package io.mashin.oep.model.node;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.ModelElementWithSchema;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.property.PointPropertyElement;
import io.mashin.oep.model.property.TextPropertyElement;
import io.mashin.oep.model.terminal.InputTerminal;
import io.mashin.oep.model.terminal.OutputTerminal;
import io.mashin.oep.model.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.dom4j.Element;
import org.eclipse.draw2d.geometry.Point;

public abstract class Node extends ModelElementWithSchema {
  
  public static final AtomicLong ID_SEQ = new AtomicLong(0);
  
  public static final String TYPE_START         = "start";
  public static final String TYPE_END           = "end";
  public static final String TYPE_KILL          = "kill";
  public static final String TYPE_FORK          = "fork";
  public static final String TYPE_JOIN          = "join";
  public static final String TYPE_DECISION      = "decision";
  public static final String TYPE_CUSTOM_ACTION = "custom-action";
  public static final String TYPE_FS            = "fs";
  public static final String TYPE_JAVA          = "java";
  public static final String TYPE_MAPREDUCE     = "map-reduce";
  public static final String TYPE_PIG           = "pig";
  public static final String TYPE_SUBWORKFLOW   = "sub-workflow";
  public static final String TYPE_DISTCP        = "distcp";
  public static final String TYPE_EMAIL         = "email";
  public static final String TYPE_HIVE          = "hive";
  public static final String TYPE_HIVE2         = "hive2";
  public static final String TYPE_SHELL         = "shell";
  public static final String TYPE_SPARK         = "spark";
  public static final String TYPE_SQOOP         = "sqoop";
  public static final String TYPE_SSH           = "ssh";
  
  public static final String TERMINAL_NONE    = "";
  public static final String TERMINAL_FANIN   = "fan-in";
  public static final String TERMINAL_FANOUT  = "fan-out";
  public static final String TERMINAL_CASE    = "case";
  public static final String TERMINAL_DEFAULT = "default";
  public static final String TERMINAL_OUT     = "out";
  public static final String TERMINAL_OK      = "ok";
  public static final String TERMINAL_ERROR   = "error";
  public static final String TERMINAL_NOIN    = "no-in";
  public static final String TERMINAL_NOOUT   = "no-out";
  
  public static final String PROP_CONNECTION_SOURCE  = "prop.node.connection.source";
  public static final String PROP_CONNECTION_TARGET  = "prop.node.connection.target";
  public static final String PROP_POS                = "prop.node.pos";
  public static final String PROP_NODE_NAME          = "prop.node.name";
  public static final String PROP_NODE_TYPE          = "prop.node.type";
  
  protected TextPropertyElement  name;
  protected TextPropertyElement  nodeType;
  protected PointPropertyElement position;
  
  protected Workflow workflow;
  
  protected List<Terminal> terminals;
  
  /**
   * List of connections at which this node is a source
   */
  protected List<WorkflowConnection> sourceConnections;
  
  /**
   * List of connections at which this node is a target
   */
  protected List<WorkflowConnection> targetConnections;
  
  public Node(Workflow workflow) {
    this(workflow, null);
  }
  
  public Node(Workflow workflow, org.dom4j.Node hpdlNode) {
    this.workflow = workflow;
    
    name = new TextPropertyElement(PROP_NODE_NAME, "Name");
    addPropertyElement(name);
    
    nodeType = new TextPropertyElement(PROP_NODE_TYPE, "Type");
    addPropertyElement(nodeType);
    
    position = new PointPropertyElement(PROP_POS, "Position");
    addPropertyElement(position);
    
    terminals = new ArrayList<Terminal>();
    sourceConnections = new ArrayList<WorkflowConnection>();
    targetConnections = new ArrayList<WorkflowConnection>();
    
    setNodeType();
  }
  
  public void init() {
    init(null);
  }
  
  public void init(org.dom4j.Node hpdlNode) {
    if (hpdlNode == null) {
      initDefaults();
    } else {
      read(hpdlNode);
    }
  }
  
  public void initDefaults() {
    
  }
  
  @Override
  public void write(Element parent) {
    Element element = parent.addElement(getNodeType());
    hpdlModel.set(element);
    XMLWriteUtils.writeTextPropertyAsAttribute(name, element, "name");
  }
  
  @Override
  public void read(org.dom4j.Node node) {
    XMLReadUtils.initTextPropertyFrom(name, node, "@name");
  }
  
  public Point getPosition() {
    return position.getAsPoint();
  }
  
  public void setPosition(Point point) {
    setPropertyValue(PROP_POS, point);
  }
  
  public String getName() {
    return name.getStringValue();
  }
  
  public void setName(String name) {
    setPropertyValue(PROP_NODE_NAME, name);
  }
  
  public abstract String getNodeType();
  
  public void setNodeType() {
    setPropertyValue(PROP_NODE_TYPE, "");
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    if (propertyName.equals(PROP_NODE_TYPE)) {
      super.setPropertyValue(PROP_NODE_TYPE, getNodeType());
    } else {
      super.setPropertyValue(propertyName, propertyValue);
    }
  }
  
  public List<WorkflowConnection> getSourceConnections() {
    return this.sourceConnections;
  }
  
  public List<WorkflowConnection> getTargetConnections() {
    return this.targetConnections;
  }
  
  public boolean addConnectionInitiate(WorkflowConnection connection) {
    if (connection.getSourceNode() == connection.getTargetNode()) {
      
      return false;
      
    } else if (connection.getSourceNode() == this) {
      
      if (!canConnectTo(connection.getTargetNode())
          || !connection.getSourceTerminal().canAddConnection(connection)) {
        return false;
      }
      
      boolean success = sourceConnections.add(connection);
      if (success) {
        connection.getSourceTerminal().addConnectionUpdate(connection);
        firePropertyChange(PROP_CONNECTION_SOURCE, null, connection);
      }
      return success;
      
    } else if (connection.getTargetNode() == this) {
      
      if (!canConnectFrom(connection.getSourceNode())
          || !connection.getTargetTerminal().canAddConnection(connection)) {
        return false;
      }
      
      boolean success = targetConnections.add(connection);
      if (success) {
        connection.getTargetTerminal().addConnectionUpdate(connection);
        firePropertyChange(PROP_CONNECTION_TARGET, null, connection);
      }
      return success;
      
    }
    
    return false;
  }
  
  public void addConnectionUpdate(WorkflowConnection connection) {
    if (connection.getSourceNode() == this) {
      boolean success = sourceConnections.add(connection);
      if (success) {
        firePropertyChange(PROP_CONNECTION_SOURCE, null, connection);
      }
    } else if (connection.getTargetNode() == this) {
      boolean success = targetConnections.add(connection);
      if (success) {
        firePropertyChange(PROP_CONNECTION_TARGET, null, connection);
      }
    }
  }
  
  public void removeConnectionInitiate(WorkflowConnection connection) {
    if (connection.getSourceNode() == this) {
      boolean success = sourceConnections.remove(connection);
      if (success) {
        connection.getSourceTerminal().removeConnectionUpdate(connection);
        firePropertyChange(PROP_CONNECTION_SOURCE, connection, null);
      }
    } else if (connection.getTargetNode() == this) {
      boolean success = targetConnections.remove(connection);
      if (success) {
        connection.getTargetTerminal().removeConnectionUpdate(connection);
        firePropertyChange(PROP_CONNECTION_TARGET, connection, null);
      }
    }
  }
  
  public void removeConnectionUpdate(WorkflowConnection connection) {
    if (connection.getSourceNode() == this) {
      boolean success = sourceConnections.remove(connection);
      if (success) {
        firePropertyChange(PROP_CONNECTION_SOURCE, connection, null);
      }
    } else if (connection.getTargetNode() == this) {
      boolean success = targetConnections.remove(connection);
      if (success) {
        firePropertyChange(PROP_CONNECTION_TARGET, connection, null);
      }
    }
  }
  
  public abstract boolean canConnectTo(Node target);
  public abstract boolean canConnectFrom(Node source);
  
  public List<Terminal> getTerminals() {
    return new ArrayList<Terminal>(terminals);
  }
  
  public List<Terminal> getInputTerminals() {
    return terminals.stream()
        .filter(t -> t instanceof InputTerminal)
        .collect(Collectors.toList());
  }
  
  public List<Terminal> getOutputTerminals() {
    return terminals.stream()
        .filter(t -> t instanceof OutputTerminal)
        .collect(Collectors.toList());
  }
  
  public Terminal getTerminal(String label) {
    for (Terminal terminal : terminals) {
      if (terminal.getLabel().equals(label)) {
        return terminal;
      }
    }
    return null;
  }
  
}
