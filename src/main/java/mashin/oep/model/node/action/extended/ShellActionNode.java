package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.CheckBoxPropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class ShellActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> SHELL_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2,
          SchemaVersion.V_0_3);
  private static final SchemaVersion SHELL_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_3;
  private static final SchemaVersion SHELL_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_3;
  
  public static final String PROP_JOBTRACKER = "prop.node.shell.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.shell.name-ndoe";
  public static final String PROP_PREPARE_DELETE = "prop.node.shell.prepare.delete";
  public static final String PROP_PREPARE_MKDIR = "prop.node.shell.prepare.mkdir";
  public static final String PROP_JOBXML = "prop.node.shell.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.shell.configuration";
  public static final String PROP_EXEC = "prop.node.shell.exec";
  public static final String PROP_ARGUMENT = "prop.node.shell.argument";
  public static final String PROP_ENVVAR = "prop.node.shell.env-var";
  public static final String PROP_FILE = "prop.node.shell.file";
  public static final String PROP_ARCHIVE = "prop.node.shell.archive";
  public static final String PROP_CAPTURE_OUTPUT = "prop.node.shell.capture-output";
  
  public static final String CATEGORY_PREPARE = "Prepare";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PropertyElementCollection prepareDelete;//delete {path} 0-unbounded
  protected PropertyElementCollection prepareMkdir;//mkdir {path} 0-unbounded
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement exec;//exec
  protected PropertyElementCollection argument;//argument 0-unbounded
  protected PropertyElementCollection envVar;//env-var 0-unbounded
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive 0-unbounded
  protected CheckBoxPropertyElement captureOutput;//capture-output (flag/checkbox)
  
  public ShellActionNode(Workflow workflow) {
    super(workflow);
    
    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);
    
    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);
    
    //prepare
    prepareDelete = new PropertyElementCollection(CATEGORY_PREPARE,
                      new TextPropertyElement(PROP_PREPARE_DELETE, "Delete"));
    addPropertyElement(prepareDelete);
    
    prepareMkdir = new PropertyElementCollection(CATEGORY_PREPARE,
                      new TextPropertyElement(PROP_PREPARE_MKDIR, "Mkdir"));
    addPropertyElement(prepareMkdir);
    
    jobXML = new PropertyElementCollection("Job XML", new TextPropertyElement(PROP_JOBXML, "Job XML"));
    addPropertyElement(jobXML);
    
    configuration = new PropertyElementCollection("Configuration",
                      new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
    
    exec = new TextPropertyElement(PROP_EXEC, "Exec");
    addPropertyElement(exec);
    
    argument = new PropertyElementCollection("Argument",
                new TextPropertyElement(PROP_ARGUMENT, "Argument"));
    addPropertyElement(argument);
    
    envVar = new PropertyElementCollection("Env Var",
              new TextPropertyElement(PROP_ENVVAR, "Env Var"));
    addPropertyElement(envVar);
    
    file = new PropertyElementCollection("File",
            new TextPropertyElement(PROP_FILE, "File"));
    addPropertyElement(file);
    
    archive = new PropertyElementCollection("Archive",
                new TextPropertyElement(PROP_ARCHIVE, "Archive"));
    addPropertyElement(archive);
    
    captureOutput = new CheckBoxPropertyElement(PROP_CAPTURE_OUTPUT, "Capture Output");
    addPropertyElement(captureOutput);
    
    setName("shell-" + ID_SEQ.incrementAndGet());
  }

  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return SHELL_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return SHELL_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return SHELL_LATEST_SCHEMA_VERSION;
  }
  
  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}