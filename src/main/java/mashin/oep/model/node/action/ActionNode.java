package mashin.oep.model.node.action;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.hpdl.XMLWriteUtils;
import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.connection.WorkflowConnectionEndPoint;
import mashin.oep.model.node.Node;
import mashin.oep.model.property.TextPropertyElement;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.SingleOutputTerminal;

public abstract class ActionNode extends Node {
  
  public static final String PROP_NODE_CRED          = "prop.node.action.cred";
  public static final String PROP_NODE_RETRYMAX      = "prop.node.action.retry-max";
  public static final String PROP_NODE_RETRYINTERVAL = "prop.node.action.retry-interval";
  
  protected FanInTerminal fanInTerminal;
  protected SingleOutputTerminal okSingleOutputTerminal;
  protected SingleOutputTerminal errSingleOutputTerminal;
  
  protected TextPropertyElement cred;
  protected TextPropertyElement retryMax;
  protected TextPropertyElement retryInterval;
  
  public ActionNode(Workflow workflow) {
    this(workflow, null);
  }

  public ActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    
    fanInTerminal           = new FanInTerminal(TERMINAL_FANIN, this);
    okSingleOutputTerminal  = new SingleOutputTerminal(TERMINAL_OK, this);
    errSingleOutputTerminal = new SingleOutputTerminal(TERMINAL_ERROR, this);
    terminals.add(fanInTerminal);
    terminals.add(okSingleOutputTerminal);
    terminals.add(errSingleOutputTerminal);
    
    cred = new TextPropertyElement(PROP_NODE_CRED, "Cred");
    addPropertyElement(cred);
    
    retryMax = new TextPropertyElement(PROP_NODE_RETRYMAX, "Retry Max");
    addPropertyElement(retryMax);
    
    retryInterval = new TextPropertyElement(PROP_NODE_RETRYINTERVAL, "Retry Interval");
    addPropertyElement(retryInterval);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
  }
  
  @Override
  public void write(Element parent) {
    super.write(parent);
    
    Element element = (Element) hpdlModel.get();
    
    element.setName("action");
    XMLWriteUtils.writeTextPropertyAsAttribute(cred, element, "cred");
    XMLWriteUtils.writeTextPropertyAsAttribute(retryMax, element, "retry-max");
    XMLWriteUtils.writeTextPropertyAsAttribute(retryInterval, element, "retry-interval");
    XMLWriteUtils.writeConnectionsAsElementWithAttribute(okSingleOutputTerminal.getConnections(), element, "ok", "to");
    XMLWriteUtils.writeConnectionsAsElementWithAttribute(errSingleOutputTerminal.getConnections(), element, "error", "to");
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(cred, hpdlNode, "@cred");
    XMLReadUtils.initTextPropertyFrom(retryMax, hpdlNode, "@retry-max");
    XMLReadUtils.initTextPropertyFrom(retryInterval, hpdlNode, "@retry-interval");
    
    // read connections
    
    String okConn = XMLReadUtils.valueOf("./ok/@to", hpdlNode);
    if (!okConn.isEmpty()) {
      WorkflowConnection conn = new WorkflowConnection(
          new WorkflowConnectionEndPoint(this, okSingleOutputTerminal),
          new WorkflowConnectionEndPoint(okConn, TERMINAL_FANIN));
      sourceConnections.add(conn);
    }
    
    String errConn = XMLReadUtils.valueOf("./error/@to", hpdlNode);
    if (!errConn.isEmpty()) {
      WorkflowConnection conn = new WorkflowConnection(
          new WorkflowConnectionEndPoint(this, errSingleOutputTerminal),
          new WorkflowConnectionEndPoint(errConn, TERMINAL_FANIN));
      sourceConnections.add(conn);
    }
  }
      
  
  public void setCred(String cred) {
    setPropertyValue(PROP_NODE_CRED, cred);
  }
  
  public String getCred() {
    return cred.getStringValue();
  }
  
  public void setRetryMax(String retryMax) {
    setPropertyValue(PROP_NODE_RETRYMAX, retryMax);
  }
  
  public String getRetryMax() {
    return retryMax.getStringValue();
  }
  
  public void setRetryInterval(String retryInterval) {
    setPropertyValue(PROP_NODE_RETRYINTERVAL, retryInterval);
  }
  
  public String getRetryInterval() {
    return retryInterval.getStringValue();
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    if (this.workflow == null) {
      return Arrays.asList(SchemaVersion.V_ANY);
    }
    return this.workflow.getPossibleSchemaVersions();
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    if (this.workflow == null) {
      return SchemaVersion.V_ANY;
    }
    return this.workflow.getSchemaVersion();
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    if (this.workflow == null) {
      return SchemaVersion.V_ANY;
    }
    return this.workflow.getLatestSchemaVersion();
  }
  
  @Override
  protected boolean isSchemaVersionEditable() { return false; }
  
}
