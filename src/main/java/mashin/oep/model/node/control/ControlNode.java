package mashin.oep.model.node.control;

import java.util.Arrays;
import java.util.List;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;

public abstract class ControlNode extends Node {
  
  public ControlNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public ControlNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    setSchemaVersion(workflow.getSchemaVersion());
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
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
