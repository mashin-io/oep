package mashin.oep.model.node.action.basic;

import mashin.oep.model.Workflow;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class PigActionNode extends BasicActionNode {

  public static final String PROP_JOBTRACKER = "prop.node.pig.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.pig.name-ndoe";
  public static final String PROP_PREPARE_DELETE = "prop.node.pig.prepare.delete";
  public static final String PROP_PREPARE_MKDIR = "prop.node.pig.prepare.mkdir";
  public static final String PROP_JOBXML = "prop.node.pig.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.pig.configuration";
  public static final String PROP_SCRIPT = "prop.node.pig.script";
  public static final String PROP_PARAM = "prop.node.pig.param";
  public static final String PROP_ARGUMENT = "prop.node.pig.argument";
  public static final String PROP_FILE = "prop.node.pig.file";
  public static final String PROP_ARCHIVE = "prop.node.pig.archive";
  
  public static final String CATEGORY_PREPARE = "Prepare";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PropertyElementCollection prepareDelete;//delete {path} 0-unbounded
  protected PropertyElementCollection prepareMkdir;//mkdir {path} 0-unbounded

  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyPropertyElement configuration;//configuration
  protected TextPropertyElement script;//script
  protected PropertyElementCollection param;//param 0-unbounded
  protected PropertyElementCollection argument;//argument 0-unbounded
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive o-unbounded
  
  public PigActionNode(Workflow workflow) {
    super(workflow);
    
    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);
    
    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);

    //prepare
    prepareDelete = new PropertyElementCollection(CATEGORY_PREPARE, new TextPropertyElement(PROP_PREPARE_DELETE, "Delete"));
    addPropertyElement(prepareDelete);
    
    prepareMkdir = new PropertyElementCollection(CATEGORY_PREPARE, new TextPropertyElement(PROP_PREPARE_MKDIR, "Mkdir"));
    addPropertyElement(prepareMkdir);

    jobXML = new PropertyElementCollection("Job XML", new TextPropertyElement(PROP_JOBXML, "Job XML"));
    addPropertyElement(jobXML);
    
    configuration = new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration");
    addPropertyElement(configuration);
    
    script = new TextPropertyElement(PROP_SCRIPT, "Script");
    addPropertyElement(script);
    
    param = new PropertyElementCollection("Param", new TextPropertyElement(PROP_PARAM, "Param"));
    addPropertyElement(param);
    
    argument = new PropertyElementCollection("Argument", new TextPropertyElement(PROP_ARGUMENT, "Argument"));
    addPropertyElement(argument);
    
    file = new PropertyElementCollection("File", new TextPropertyElement(PROP_FILE, "File"));
    addPropertyElement(file);
    
    archive = new PropertyElementCollection("Archive", new TextPropertyElement(PROP_ARCHIVE, "Archive"));
    addPropertyElement(archive);
  }

  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
