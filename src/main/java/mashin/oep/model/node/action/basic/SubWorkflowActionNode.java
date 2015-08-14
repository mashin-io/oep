package mashin.oep.model.node.action.basic;

import mashin.oep.model.Workflow;
import mashin.oep.model.property.CheckBoxPropertyElement;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class SubWorkflowActionNode extends BasicActionNode {

  public static final String PROP_APPPATH = "prop.node.sub-workflow.app-path";
  public static final String PROP_PROPAGATE_CONFIGURATION = "prop.node.sub-workflow.propagate-configuration";
  public static final String PROP_CONFIGURATION = "prop.node.sub-workflow.configuration";
  
  protected TextPropertyElement appPath;//app-path
  protected CheckBoxPropertyElement propagateConfiguration;//propagate-configuration (flag/checkbox)
  protected PropertyPropertyElement configuration;//configuration
  
  public SubWorkflowActionNode(Workflow workflow) {
    super(workflow);
    
    appPath = new TextPropertyElement(PROP_APPPATH, "App Path");
    addPropertyElement(appPath);
    
    propagateConfiguration = new CheckBoxPropertyElement(PROP_PROPAGATE_CONFIGURATION, "Propagate Configuration");
    addPropertyElement(propagateConfiguration);
    
    configuration = new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration");
    addPropertyElement(configuration);
  }

  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
