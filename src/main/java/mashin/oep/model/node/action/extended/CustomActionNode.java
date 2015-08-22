package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.TextPropertyElement;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

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
  public void write(Element parentNode) {
    super.write(parentNode);
    
    Element element = (Element) hpdlModel.get();
    String xmlContent = xml.getStringValue();
    if (xmlContent.isEmpty()) {
      xmlContent = "<EMPTY_CUSTOM_NODE xmlns=\"uri:oozie:EMPTY_CUSTOM_NODE:0.1\"/>";
    }
    try {
      element.add(DocumentHelper.parseText(xmlContent).getRootElement());
    } catch (Throwable t) {
      element.setText(xmlContent);
    }
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    Node node = XMLReadUtils.schemaVersionParentNode(hpdlNode);
    xml.setStringValue(node.asXML());
  }
  
  @Override
  public String getNodeType() {
    return "custom-action";
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return Arrays.asList(SchemaVersion.V_ANY);
  }
  
  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return SchemaVersion.V_ANY;
  }
  
  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return SchemaVersion.V_ANY;
  }
  
  @Override
  protected boolean isSchemaVersionEditable() { return false; }
  
}
