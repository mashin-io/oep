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

public class HiveActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> HIVE_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_2, SchemaVersion.V_0_3,
          SchemaVersion.V_0_4, SchemaVersion.V_0_5);
  private static final SchemaVersion HIVE_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_5;
  private static final SchemaVersion HIVE_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_5;
  
  public static final String PROP_JOBTRACKER = "prop.node.hive.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.hive.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.hive.prepare";
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
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element hive = element.addElement("hive");
    
    XMLWriteUtils.writeSchemaVersion(getSchemaVersion(), hive, getNodeType());
    XMLWriteUtils.writeTextPropertyAsElement(jobTracker, hive, "job-tracker");
    XMLWriteUtils.writeTextPropertyAsElement(nameNode, hive, "name-node");
    XMLWriteUtils.writePrepareProperty(prepare, hive, "prepare");
    XMLWriteUtils.writeTextCollectionAsElements(jobXML, hive, "job-xml");
    XMLWriteUtils.writePropertiesCollection(configuration, hive, "configuration", "property");
    XMLWriteUtils.writeTextPropertyAsElement(script, hive, "script");
    XMLWriteUtils.writeTextCollectionAsElements(param, hive, "param");
    XMLWriteUtils.writeTextCollectionAsElements(argument, hive, "argument");
    XMLWriteUtils.writeTextCollectionAsElements(file, hive, "file");
    XMLWriteUtils.writeTextCollectionAsElements(archive, hive, "archive");
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./hive/job-tracker");
    XMLReadUtils.initTextPropertyFrom(nameNode, hpdlNode, "./hive/name-node");
    XMLReadUtils.initPreparePropertyFrom(prepare, hpdlNode, "./hive/prepare");
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./hive/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./hive/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(script, hpdlNode, "./hive/script");
    XMLReadUtils.initTextCollectionFrom(param, hpdlNode, "./hive/param");
    XMLReadUtils.initTextCollectionFrom(argument, hpdlNode, "./hive/argument");
    XMLReadUtils.initTextCollectionFrom(file, hpdlNode, "./hive/file");
    XMLReadUtils.initTextCollectionFrom(archive, hpdlNode, "./hive/archive");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_HIVE;
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
