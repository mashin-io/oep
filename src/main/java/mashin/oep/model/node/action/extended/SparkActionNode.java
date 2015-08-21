package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.XMLUtils;
import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.PreparePropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class SparkActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> SPARK_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1);
  private static final SchemaVersion SPARK_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_1;
  private static final SchemaVersion SPARK_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_1;
  
  public static final String PROP_JOBTRACKER = "prop.node.spark.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.spark.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.spark.prepare";
  //public static final String PROP_PREPARE_DELETE = "prop.node.spark.prepare.delete";
  //public static final String PROP_PREPARE_MKDIR = "prop.node.spark.prepare.mkdir";
  public static final String PROP_JOBXML = "prop.node.spark.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.spark.configuration";
  public static final String PROP_MASTER = "prop.node.spark.master";
  public static final String PROP_MODE = "prop.node.spark.mode";
  public static final String PROP_NAME = "prop.node.spark.name";
  public static final String PROP_CLASS = "prop.node.spark.class";
  public static final String PROP_JAR = "prop.node.spark.jar";
  public static final String PROP_SPARK_OPTS = "prop.node.spark.spark-opts";
  public static final String PROP_ARG = "prop.node.spark.arg";
  
  //public static final String CATEGORY_PREPARE = "Prepare";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  //protected PropertyElementCollection prepareDelete;//delete {path} 0-unbounded
  //protected PropertyElementCollection prepareMkdir;//mkdir {path} 0-unbounded
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement master;//master
  protected TextPropertyElement mode;//mode
  protected TextPropertyElement name;//name
  protected TextPropertyElement clazz;//class
  protected TextPropertyElement jar;//jar
  protected TextPropertyElement sparkOpts;//spark-opts
  protected PropertyElementCollection arg;//arg 0-unbounded
  
  public SparkActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public SparkActionNode(Workflow workflow, Node hpdlNode) {
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
    
    master = new TextPropertyElement(PROP_MASTER, "Master");
    addPropertyElement(master);
    
    mode = new TextPropertyElement(PROP_MODE, "Mode");
    addPropertyElement(mode);
    
    name = new TextPropertyElement(PROP_NAME, "Name2");
    addPropertyElement(name);
    
    clazz = new TextPropertyElement(PROP_CLASS, "Class");
    addPropertyElement(clazz);
    
    jar = new TextPropertyElement(PROP_JAR, "Jar");
    addPropertyElement(jar);
    
    sparkOpts = new TextPropertyElement(PROP_SPARK_OPTS, "Spark Opts");
    addPropertyElement(sparkOpts);
    
    arg = new PropertyElementCollection("Arg",
           new TextPropertyElement(PROP_ARG, "Arg"));
    addPropertyElement(arg);
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("spark-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element parentNode) {
    
  }

  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./spark/job-tracker");
    XMLUtils.initTextPropertyFrom(nameNode, hpdlNode, "./spark/name-node");
    XMLUtils.initPreparePropertyFrom(prepare, hpdlNode, "./spark/prepare");
    XMLUtils.initTextCollectionFrom(jobXML, hpdlNode, "./spark/job-xml");
    XMLUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./spark/configuration", "./property");
    XMLUtils.initTextPropertyFrom(master, hpdlNode, "./spark/master");
    XMLUtils.initTextPropertyFrom(mode, hpdlNode, "./spark/mode");
    XMLUtils.initTextPropertyFrom(name, hpdlNode, "./spark/name");
    XMLUtils.initTextPropertyFrom(clazz, hpdlNode, "./spark/class");
    XMLUtils.initTextPropertyFrom(jar, hpdlNode, "./spark/jar");
    XMLUtils.initTextPropertyFrom(sparkOpts, hpdlNode, "./spark/spark-opts");
    XMLUtils.initTextCollectionFrom(arg, hpdlNode, "./spark/arg");
  }
  
  @Override
  public String getNodeType() {
    return "spark";
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return SPARK_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return SPARK_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return SPARK_LATEST_SCHEMA_VERSION;
  }

}
