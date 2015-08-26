package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.hpdl.XMLWriteUtils;
import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.PreparePropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class Hive2ActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> HIVE2_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1);
  private static final SchemaVersion HIVE2_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_1;
  private static final SchemaVersion HIVE2_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_1;
  
  public static final String PROP_JOBTRACKER = "prop.node.hive2.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.hive2.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.hive2.prepare";
  public static final String PROP_JOBXML = "prop.node.hive2.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.hive2.configuration";
  public static final String PROP_JDBC_URL = "prop.node.hive2.jdbc-url";
  public static final String PROP_PASSWORD = "prop.node.hive2.password";
  public static final String PROP_SCRIPT = "prop.node.hive2.script";
  public static final String PROP_PARAM = "prop.node.hive2.param";
  public static final String PROP_ARGUMENT = "prop.node.hive2.argument";
  public static final String PROP_FILE = "prop.node.hive2.file";
  public static final String PROP_ARCHIVE = "prop.node.hive2.archive";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement jdbcURL;//jdbc-url
  protected TextPropertyElement password;//password
  protected TextPropertyElement script;//script
  protected PropertyElementCollection param;//param 0-unbounded
  protected PropertyElementCollection argument;//argument 0-unbounded
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive 0-unbounded
  
  public Hive2ActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public Hive2ActionNode(Workflow workflow, Node hpdlNode) {
    super(workflow, hpdlNode);
    
    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);
    
    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);
    
    //prepare
    prepare = new PreparePropertyElement(PROP_PREPARE, "Prepare");
    addPropertyElement(prepare);
    
    jobXML = new PropertyElementCollection("Job XML", new TextPropertyElement(PROP_JOBXML, "Job XML"));
    addPropertyElement(jobXML);
    
    configuration = new PropertyElementCollection("Configuration",
                      new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
    
    jdbcURL = new TextPropertyElement(PROP_JDBC_URL, "JDBC URL");
    addPropertyElement(jdbcURL);
    
    password = new TextPropertyElement(PROP_PASSWORD, "Password");
    addPropertyElement(password);
    
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
    
    archive = new PropertyElementCollection("Archive2",
                new TextPropertyElement(PROP_ARCHIVE, "Archive2"));
    addPropertyElement(archive);
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("hive2-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element hive2 = element.addElement("hive2");
    
    XMLWriteUtils.writeSchemaVersion(getSchemaVersion(), hive2, getNodeType());
    XMLWriteUtils.writeTextPropertyAsElement(jobTracker, hive2, "job-tracker");
    XMLWriteUtils.writeTextPropertyAsElement(nameNode, hive2, "name-node");
    XMLWriteUtils.writePrepareProperty(prepare, hive2, "prepare");
    XMLWriteUtils.writeTextCollectionAsElements(jobXML, hive2, "job-xml");
    XMLWriteUtils.writePropertiesCollection(configuration, hive2, "configuration", "property");
    XMLWriteUtils.writeTextPropertyAsElement(jdbcURL, hive2, "jdbc-url");
    XMLWriteUtils.writeTextPropertyAsElement(password, hive2, "password");
    XMLWriteUtils.writeTextPropertyAsElement(script, hive2, "script");
    XMLWriteUtils.writeTextCollectionAsElements(param, hive2, "param");
    XMLWriteUtils.writeTextCollectionAsElements(argument, hive2, "argument");
    XMLWriteUtils.writeTextCollectionAsElements(file, hive2, "file");
    XMLWriteUtils.writeTextCollectionAsElements(archive, hive2, "archive");
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./hive2/job-tracker");
    XMLReadUtils.initTextPropertyFrom(nameNode, hpdlNode, "./hive2/name-node");
    XMLReadUtils.initPreparePropertyFrom(prepare, hpdlNode, "./hive2/prepare");
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./hive2/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./hive2/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(jdbcURL, hpdlNode, "./hive2/jdbc-url");
    XMLReadUtils.initTextPropertyFrom(password, hpdlNode, "./hive2/password");
    XMLReadUtils.initTextPropertyFrom(script, hpdlNode, "./hive2/script");
    XMLReadUtils.initTextCollectionFrom(param, hpdlNode, "./hive2/param");
    XMLReadUtils.initTextCollectionFrom(argument, hpdlNode, "./hive2/argument");
    XMLReadUtils.initTextCollectionFrom(file, hpdlNode, "./hive2/file");
    XMLReadUtils.initTextCollectionFrom(archive, hpdlNode, "./hive2/archive");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_HIVE2;
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return HIVE2_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return HIVE2_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return HIVE2_LATEST_SCHEMA_VERSION;
  }

}
