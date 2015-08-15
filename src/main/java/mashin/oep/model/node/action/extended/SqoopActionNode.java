package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class SqoopActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> SQOOP_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_2, SchemaVersion.V_0_3,
          SchemaVersion.V_0_4);
  private static final SchemaVersion SQOOP_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_4;
  private static final SchemaVersion SQOOP_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_4;
  
  public static final String PROP_JOBTRACKER = "prop.node.sqoop.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.sqoop.name-ndoe";
  public static final String PROP_PREPARE_DELETE = "prop.node.sqoop.prepare.delete";
  public static final String PROP_PREPARE_MKDIR = "prop.node.sqoop.prepare.mkdir";
  public static final String PROP_JOBXML = "prop.node.sqoop.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.sqoop.configuration";
  public static final String PROP_COMMAND = "prop.node.sqoop.command";
  public static final String PROP_ARG = "prop.node.sqoop.arg";
  public static final String PROP_FILE = "prop.node.sqoop.file";
  public static final String PROP_ARCHIVE = "prop.node.sqoop.archive";
  
  public static final String CATEGORY_PREPARE = "Prepare";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node

  //prepare
  protected PropertyElementCollection prepareDelete;//delete {path} 0-unbounded
  protected PropertyElementCollection prepareMkdir;//mkdir {path} 0-unbounded

  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  
  //choice
  protected TextPropertyElement command;//command
  protected PropertyElementCollection arg;//arg 1-unbounded
  
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive 0-unbounded
  
  public SqoopActionNode(Workflow workflow) {
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
    
    command = new TextPropertyElement(PROP_COMMAND, "Command");
    addPropertyElement(command);
    
    arg = new PropertyElementCollection("Arg",
           new TextPropertyElement(PROP_ARG, "Arg"));
    addPropertyElement(arg);
    
    file = new PropertyElementCollection("File", new TextPropertyElement(PROP_FILE, "File"));
    addPropertyElement(file);
    
    archive = new PropertyElementCollection("Archive", new TextPropertyElement(PROP_ARCHIVE, "Archive"));
    addPropertyElement(archive);
    
    setName("sqoop-" + ID_SEQ.incrementAndGet());
  }

  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return SQOOP_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return SQOOP_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return SQOOP_LATEST_SCHEMA_VERSION;
  }
  
  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
