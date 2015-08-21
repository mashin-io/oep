package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.hpdl.XMLUtils;
import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.PreparePropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class HiveActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> HIVE_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_2, SchemaVersion.V_0_3,
          SchemaVersion.V_0_4, SchemaVersion.V_0_5);
  private static final SchemaVersion HIVE_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_5;
  private static final SchemaVersion HIVE_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_5;
  
  public static final String PROP_JOBTRACKER = "prop.node.hive.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.hive.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.hive.prepare";
  //public static final String PROP_PREPARE_DELETE = "prop.node.hive.prepare.delete";
  //public static final String PROP_PREPARE_MKDIR = "prop.node.hive.prepare.mkdir";
  public static final String PROP_JOBXML = "prop.node.hive.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.hive.configuration";
  public static final String PROP_SCRIPT = "prop.node.hive.script";
  public static final String PROP_PARAM = "prop.node.hive.param";
  public static final String PROP_ARGUMENT = "prop.node.hive.argument";
  public static final String PROP_FILE = "prop.node.hive.file";
  public static final String PROP_ARCHIVE = "prop.node.hive.archive";
  
  public static final String CATEGORY_PREPARE = "Prepare";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  //protected PropertyElementCollection prepareDelete;//delete {path} 0-unbounded
  //protected PropertyElementCollection prepareMkdir;//mkdir {path} 0-unbounded
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement script;//script
  protected PropertyElementCollection param;//param 0-unbounded
  protected PropertyElementCollection argument;//argument 0-unbounded
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive 0-unbounded
  
  public HiveActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public HiveActionNode(Workflow workflow, Node hpdlNode) {
    super(workflow, hpdlNode);
    
    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);
    
    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);
    
    //prepare
    prepare = new PreparePropertyElement(PROP_PREPARE, "Prepare");
    addPropertyElement(prepare);
    //prepareDelete = new PropertyElementCollection(CATEGORY_PREPARE,
    //                  new TextPropertyElement(PROP_PREPARE_DELETE, "Delete"));
    //addPropertyElement(prepareDelete);
    //
    //prepareMkdir = new PropertyElementCollection(CATEGORY_PREPARE,
    //                  new TextPropertyElement(PROP_PREPARE_MKDIR, "Mkdir"));
    //addPropertyElement(prepareMkdir);
    
    jobXML = new PropertyElementCollection("Job XML", new TextPropertyElement(PROP_JOBXML, "Job XML"));
    addPropertyElement(jobXML);
    
    configuration = new PropertyElementCollection("Configuration",
                      new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
    
    script = new TextPropertyElement(PROP_SCRIPT, "Script");
    addPropertyElement(script);
    
    param = new PropertyElementCollection("Param",
        new TextPropertyElement(PROP_PARAM, "Param"));
    addPropertyElement(param);
    
    argument = new PropertyElementCollection("Argument",
                new TextPropertyElement(PROP_ARGUMENT, "Argument"));
    addPropertyElement(argument);
    
    file = new PropertyElementCollection("File",
            new TextPropertyElement(PROP_FILE, "File"));
    addPropertyElement(file);
    
    archive = new PropertyElementCollection("Archive",
                new TextPropertyElement(PROP_ARCHIVE, "Archive"));
    addPropertyElement(archive);
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("hive-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element paretNode) {
    
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./hive/job-tracker");
    XMLUtils.initTextPropertyFrom(nameNode, hpdlNode, "./hive/name-node");
    XMLUtils.initPreparePropertyFrom(prepare, hpdlNode, "./hive/prepare");
    XMLUtils.initTextCollectionFrom(jobXML, hpdlNode, "./hive/job-xml");
    XMLUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./hive/configuration", "./property");
    XMLUtils.initTextPropertyFrom(script, hpdlNode, "./hive/script");
    XMLUtils.initTextCollectionFrom(param, hpdlNode, "./hive/param");
    XMLUtils.initTextCollectionFrom(argument, hpdlNode, "./hive/argument");
    XMLUtils.initTextCollectionFrom(file, hpdlNode, "./hive/file");
    XMLUtils.initTextCollectionFrom(archive, hpdlNode, "./hive/archive");
  }
  
  @Override
  public String getNodeType() {
    return "hive";
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return HIVE_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return HIVE_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return HIVE_LATEST_SCHEMA_VERSION;
  }

}
