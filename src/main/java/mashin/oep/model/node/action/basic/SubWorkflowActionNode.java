package mashin.oep.model.node.action.basic;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.XMLUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.CheckBoxPropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class SubWorkflowActionNode extends BasicActionNode {

  public static final String PROP_APPPATH = "prop.node.sub-workflow.app-path";
  public static final String PROP_PROPAGATE_CONFIGURATION = "prop.node.sub-workflow.propagate-configuration";
  public static final String PROP_CONFIGURATION = "prop.node.sub-workflow.configuration";
  
  protected TextPropertyElement appPath;//app-path
  protected CheckBoxPropertyElement propagateConfiguration;//propagate-configuration (flag/checkbox)
  protected PropertyElementCollection configuration;//configuration
  
  public SubWorkflowActionNode(Workflow workflow) {
    this(workflow, null);
  }

  public SubWorkflowActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    
    appPath = new TextPropertyElement(PROP_APPPATH, "App Path");
    addPropertyElement(appPath);
    
    propagateConfiguration = new CheckBoxPropertyElement(PROP_PROPAGATE_CONFIGURATION, "Propagate Configuration");
    addPropertyElement(propagateConfiguration);
    
    configuration = new PropertyElementCollection("Configuration",
                      new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("sub-workflow-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element paretNode) {
    
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLUtils.initTextPropertyFrom(appPath, hpdlNode, "./sub-workflow/app-path");
    propagateConfiguration.setValue(!XMLUtils.valueOf("./sub-workflow/propagate-configuration", hpdlNode).isEmpty());
    XMLUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./sub-workflow/configuration", "./property");
  }
  
  @Override
  public String getNodeType() {
    return "sub-workflow";
  }
  
}
