package io.mashin.oep.model.node.action.extended;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;
import io.mashin.oep.model.node.action.ActionNode;

import org.dom4j.Element;

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
    this.setSchemaVersion(getDefaultSchemaVersion());
  }
  
  @Override
  public void write(Element parentNode) {
    super.write(parentNode);
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    
    org.dom4j.Node node = XMLReadUtils.schemaVersionParentNode(hpdlNode);
    setSchemaVersion(XMLReadUtils.schemaVersion(node));
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
