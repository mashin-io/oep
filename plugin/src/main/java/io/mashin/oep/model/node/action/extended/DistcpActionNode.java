package io.mashin.oep.model.node.action.extended;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.property.PreparePropertyElement;
import io.mashin.oep.model.property.PropertyElementCollection;
import io.mashin.oep.model.property.PropertyPropertyElement;
import io.mashin.oep.model.property.TextPropertyElement;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

public class DistcpActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> DISTCP_POSSIBLE_SCHEMA_VERSIONS = Arrays
      .asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2);
  private static final SchemaVersion DISTCP_DEFAULT_SCHEMA_VERSION = SchemaVersion.V_0_2;
  private static final SchemaVersion DISTCP_LATEST_SCHEMA_VERSION = SchemaVersion.V_0_2;

  public static final String PROP_JOBTRACKER = "prop.node.distcp.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.distcp.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.distcp.prepare";
  public static final String PROP_CONFIGURATION = "prop.node.distcp.configuration";
  public static final String PROP_JAVA_OPTS = "prop.node.distcp.java-opts";
  public static final String PROP_ARG = "prop.node.distcp.arg";

  protected TextPropertyElement jobTracker;// job-tracker
  protected TextPropertyElement nameNode;// name-node

  // prepare
  protected PreparePropertyElement prepare;

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
    setName(workflow.nextId("distcp"));
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element distcp =  element.addElement("distcp");
    
    XMLWriteUtils.writeSchemaVersion(getSchemaVersion(), distcp, getNodeType());
    XMLWriteUtils.writeTextPropertyAsElement(jobTracker, distcp, "job-tracker");
    XMLWriteUtils.writeTextPropertyAsElement(nameNode, distcp, "name-node");
    XMLWriteUtils.writePrepareProperty(prepare, distcp, "prepare");
    XMLWriteUtils.writePropertiesCollection(configuration, distcp, "configuration", "property");
    XMLWriteUtils.writeTextPropertyAsElement(javaOpts, distcp, "java-opts");
    XMLWriteUtils.writeTextCollectionAsElements(arg, distcp, "arg");
    
    writeConnections(element);
  }

  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./distcp/job-tracker");
    XMLReadUtils.initTextPropertyFrom(nameNode, hpdlNode, "./distcp/name-node");
    XMLReadUtils.initPreparePropertyFrom(prepare, hpdlNode, "./distcp/prepare");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./distcp/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(javaOpts, hpdlNode, "./distcp/java-opts");
    XMLReadUtils.initTextCollectionFrom(arg, hpdlNode, "./distcp/arg");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_DISTCP;
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
