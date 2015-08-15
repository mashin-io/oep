package mashin.oep.model.node.action.extended;

import mashin.oep.model.Workflow;
import mashin.oep.model.property.TextPropertyElement;

public class CustomActionNode extends ExtendedActionNode {

  public static final String PROP_XML = "prop.node.custom.xml";
  
  protected TextPropertyElement xml;
  
  public CustomActionNode(Workflow workflow) {
    super(workflow);
    
    xml = new TextPropertyElement(PROP_XML, "XML");
    addPropertyElement(xml);
    
    setName("action-" + ID_SEQ.incrementAndGet());
  }

  @Override
  protected boolean isSchemaVersionEditable() { return false; }
  
  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
