package mashin.oep.model.node.action.extended;

import mashin.oep.hpdl.XMLUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.action.ActionNode;

public abstract class ExtendedActionNode extends ActionNode {

  public ExtendedActionNode(Workflow workflow) {
    this(workflow, null);
  }

  public ExtendedActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    org.dom4j.Node node = XMLUtils.schemaVersionParentNode(hpdlNode);
    schemaVersion.set(XMLUtils.schemaVersionNode(node));
    setSchemaVersion(XMLUtils.schemaVersion(node));
  }
  
  @Override
  public boolean canConnectTo(Node target) {
    return true;
  }
  
  @Override
  public boolean canConnectFrom(Node source) {
    return true;
  }
  
  @Override
  protected boolean isSchemaVersionEditable() { return true; }
  
}
