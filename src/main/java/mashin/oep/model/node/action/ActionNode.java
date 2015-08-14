package mashin.oep.model.node.action;

import java.util.List;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
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
    super(workflow);
    this.setSchemaVersion(workflow.getSchemaVersion());
    
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
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return this.workflow.getPossibleSchemaVersions();
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return this.workflow.getSchemaVersion();
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return this.workflow.getLatestSchemaVersion();
  }
  
  @Override
  protected boolean isSchemaVersionEditable() { return false; }
  
}
