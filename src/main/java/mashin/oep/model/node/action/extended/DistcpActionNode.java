package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
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
  public static final String PROP_PREPARE_DELETE = "prop.node.distcp.prepare.delete";
  public static final String PROP_PREPARE_MKDIR = "prop.node.distcp.prepare.mkdir";
  public static final String PROP_CONFIGURATION = "prop.node.distcp.configuration";
  public static final String PROP_JAVA_OPTS = "prop.node.distcp.java-opts";
  public static final String PROP_ARG = "prop.node.distcp.arg";

  public static final String CATEGORY_PREPARE = "Prepare";

  protected TextPropertyElement jobTracker;// job-tracker
  protected TextPropertyElement nameNode;// name-node

  // prepare
  protected PropertyElementCollection prepareDelete;// delete {path} 0-unbounded
  protected PropertyElementCollection prepareMkdir;// mkdir {path} 0-unbounded

  protected PropertyElementCollection configuration;// configuration
  protected TextPropertyElement javaOpts;// java-opts
  protected PropertyElementCollection arg;// arg 0-unbounded

  public DistcpActionNode(Workflow workflow) {
    super(workflow);

    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);

    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);

    // prepare
    prepareDelete = new PropertyElementCollection(CATEGORY_PREPARE,
        new TextPropertyElement(PROP_PREPARE_DELETE, "Delete"));
    addPropertyElement(prepareDelete);

    prepareMkdir = new PropertyElementCollection(CATEGORY_PREPARE,
        new TextPropertyElement(PROP_PREPARE_MKDIR, "Mkdir"));
    addPropertyElement(prepareMkdir);

    configuration = new PropertyElementCollection("Configuration",
        new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);

    javaOpts = new TextPropertyElement(PROP_JAVA_OPTS, "Java Opts");
    addPropertyElement(javaOpts);

    arg = new PropertyElementCollection("Arg",
            new TextPropertyElement(PROP_ARG, "Arg"));
    addPropertyElement(arg);

    setName("distcp-" + ID_SEQ.incrementAndGet());
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

  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
