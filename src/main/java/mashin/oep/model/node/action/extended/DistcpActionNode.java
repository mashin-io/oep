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

public class DistcpActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> DISTCP_POSSIBLE_SCHEMA_VERSIONS = Arrays
      .asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2);
  private static final SchemaVersion DISTCP_DEFAULT_SCHEMA_VERSION = SchemaVersion.V_0_2;
  private static final SchemaVersion DISTCP_LATEST_SCHEMA_VERSION = SchemaVersion.V_0_2;

  public static final String PROP_JOBTRACKER = "prop.node.distcp.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.distcp.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.distcp.prepare";
  //public static final String PROP_PREPARE_DELETE = "prop.node.distcp.prepare.delete";
  //public static final String PROP_PREPARE_MKDIR = "prop.node.distcp.prepare.mkdir";
  public static final String PROP_CONFIGURATION = "prop.node.distcp.configuration";
  public static final String PROP_JAVA_OPTS = "prop.node.distcp.java-opts";
  public static final String PROP_ARG = "prop.node.distcp.arg";

  //public static final String CATEGORY_PREPARE = "Prepare";

  protected TextPropertyElement jobTracker;// job-tracker
  protected TextPropertyElement nameNode;// name-node

  // prepare
  protected PreparePropertyElement prepare;
  //protected PropertyElementCollection prepareDelete;// delete {path} 0-unbounded
  //protected PropertyElementCollection prepareMkdir;// mkdir {path} 0-unbounded

  protected PropertyElementCollection configuration;// configuration
  protected TextPropertyElement javaOpts;// java-opts
  protected PropertyElementCollection arg;// arg 0-unbounded

  public DistcpActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public DistcpActionNode(Workflow workflow, Node hpdlNode) {
    super(workflow, hpdlNode);

    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);

    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);

    // prepare
    prepare = new PreparePropertyElement(PROP_PREPARE, "Prepare");
    addPropertyElement(prepare);
    //prepareDelete = new PropertyElementCollection(CATEGORY_PREPARE,
    //    new TextPropertyElement(PROP_PREPARE_DELETE, "Delete"));
    //addPropertyElement(prepareDelete);
    //
    //prepareMkdir = new PropertyElementCollection(CATEGORY_PREPARE,
    //    new TextPropertyElement(PROP_PREPARE_MKDIR, "Mkdir"));
    //addPropertyElement(prepareMkdir);

    configuration = new PropertyElementCollection("Configuration",
        new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);

    javaOpts = new TextPropertyElement(PROP_JAVA_OPTS, "Java Opts");
    addPropertyElement(javaOpts);

    arg = new PropertyElementCollection("Arg",
            new TextPropertyElement(PROP_ARG, "Arg"));
    addPropertyElement(arg);
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("distcp-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element parentNode) {
    
  }

  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./distcp/job-tracker");
    XMLUtils.initTextPropertyFrom(nameNode, hpdlNode, "./distcp/name-node");
    XMLUtils.initPreparePropertyFrom(prepare, hpdlNode, "./distcp/prepare");
    XMLUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./distcp/configuration", "./property");
    XMLUtils.initTextPropertyFrom(javaOpts, hpdlNode, "./distcp/java-opts");
    XMLUtils.initTextCollectionFrom(arg, hpdlNode, "./distcp/arg");
  }
  
  @Override
  public String getNodeType() {
    return "distcp";
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return DISTCP_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return DISTCP_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return DISTCP_LATEST_SCHEMA_VERSION;
  }

}
