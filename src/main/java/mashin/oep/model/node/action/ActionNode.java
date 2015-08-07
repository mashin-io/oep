package mashin.oep.model.node.action;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.node.Node;
import mashin.oep.model.workflow.Workflow;

public abstract class ActionNode extends Node {
  public ActionNode(Workflow workflow) {
    super(workflow);
    this.setSchemaVersion(workflow.getSchemaVersion());
  }

  @Override
  public SchemaVersion[] getPossibleSchemaVersions() {
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
