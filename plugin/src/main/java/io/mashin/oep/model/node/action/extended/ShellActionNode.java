package io.mashin.oep.model.node.action.extended;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.property.CheckBoxPropertyElement;
import io.mashin.oep.model.property.PreparePropertyElement;
import io.mashin.oep.model.property.PropertyElementCollection;
import io.mashin.oep.model.property.PropertyPropertyElement;
import io.mashin.oep.model.property.TextPropertyElement;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

public class ShellActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> SHELL_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2,
          SchemaVersion.V_0_3);
  private static final SchemaVersion SHELL_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_3;
  private static final SchemaVersion SHELL_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_3;
  
  public static final String PROP_JOBTRACKER = "prop.node.shell.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.shell.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.shell.prepare";
  public static final String PROP_JOBXML = "prop.node.shell.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.shell.configuration";
  public static final String PROP_EXEC = "prop.node.shell.exec";
  public static final String PROP_ARGUMENT = "prop.node.shell.argument";
  public static final String PROP_ENVVAR = "prop.node.shell.env-var";
  public static final String PROP_FILE = "prop.node.shell.file";
  public static final String PROP_ARCHIVE = "prop.node.shell.archive";
  public static final String PROP_CAPTURE_OUTPUT = "prop.node.shell.capture-output";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement exec;//exec
  protected PropertyElementCollection argument;//argument 0-unbounded
  protected PropertyElementCollection envVar;//env-var 0-unbounded
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive 0-unbounded
  protected CheckBoxPropertyElement captureOutput;//capture-output (flag/checkbox)
  
  public ShellActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public ShellActionNode(Workflow workflow, Node hpdlNode) {
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
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("shell-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element shell = element.addElement("shell");
    
    XMLWriteUtils.writeSchemaVersion(getSchemaVersion(), shell, getNodeType());
    XMLWriteUtils.writeTextPropertyAsElement(jobTracker, shell, "job-tracker");
    XMLWriteUtils.writeTextPropertyAsElement(nameNode, shell, "name-node");
    XMLWriteUtils.writePrepareProperty(prepare, shell, "prepare");
    XMLWriteUtils.writeTextCollectionAsElements(jobXML, shell, "job-xml");
    XMLWriteUtils.writePropertiesCollection(configuration, shell, "configuration", "property");
    XMLWriteUtils.writeTextPropertyAsElement(exec, shell, "exec");
    XMLWriteUtils.writeTextCollectionAsElements(argument, shell, "argument");
    XMLWriteUtils.writeTextCollectionAsElements(envVar, shell, "env-var");
    XMLWriteUtils.writeTextCollectionAsElements(file, shell, "file");
    XMLWriteUtils.writeTextCollectionAsElements(archive, shell, "archive");
    XMLWriteUtils.writeCheckPropertyAsElement(captureOutput, shell, "capture-output");
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./shell/job-tracker");
    XMLReadUtils.initTextPropertyFrom(nameNode, hpdlNode, "./shell/name-node");
    XMLReadUtils.initPreparePropertyFrom(prepare, hpdlNode, "./shell/prepare");
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./shell/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./shell/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(exec, hpdlNode, "./shell/exec");
    XMLReadUtils.initTextCollectionFrom(argument, hpdlNode, "./shell/argument");
    XMLReadUtils.initTextCollectionFrom(envVar, hpdlNode, "./shell/env-var");
    XMLReadUtils.initTextCollectionFrom(file, hpdlNode, "./shell/file");
    XMLReadUtils.initTextCollectionFrom(archive, hpdlNode, "./shell/archive");
    XMLReadUtils.initCheckPropertyFrom(captureOutput, hpdlNode, "./shell/capture-output");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_SHELL;
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
  
}
