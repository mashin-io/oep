package mashin.oep.model.node.action.extended;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.hpdl.XMLUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.TextPropertyElement;

public class CustomActionNode extends ExtendedActionNode {

  public static final String PROP_XML = "prop.node.custom.xml";
  
  protected TextPropertyElement xml;
  
  public CustomActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public CustomActionNode(Workflow workflow, Node hpdlNode) {
    super(workflow, hpdlNode);
    xml = new TextPropertyElement(PROP_XML, "XML");
    addPropertyElement(xml);
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("action-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element parent) {
    
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    Node node = XMLUtils.schemaVersionParentNode(hpdlNode);
    xml.setStringValue(node.asXML());
    xml.set(node);
  }
  
  @Override
  public String getNodeType() {
    return "custom-action";
  }
  
  @Override
  protected boolean isSchemaVersionEditable() { return false; }
  
}
