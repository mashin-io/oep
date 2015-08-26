package mashin.oep.model.node.action.basic;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.hpdl.XMLWriteUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.CheckBoxPropertyElement;
import mashin.oep.model.property.PreparePropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class JavaActionNode extends BasicActionNode {

  public static final String PROP_JOBTRACKER = "prop.node.java.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.java.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.java.prepare";
  public static final String PROP_JOBXML = "prop.node.java.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.java.configuration";
  public static final String PROP_MAIN_CLASS = "prop.node.java.main-class";
  public static final String PROP_JAVA_OPTS = "prop.node.java.java-opts";
  public static final String PROP_JAVA_OPT = "prop.node.java.java-opt";
  public static final String PROP_ARG = "prop.node.java.arg";
  public static final String PROP_FILE = "prop.node.java.file";
  public static final String PROP_ARCHIVE = "prop.node.java.archive";
  public static final String PROP_CAPTURE_OUTPUT = "prop.node.java.capture-output";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement mainClass;//main-class
  protected TextPropertyElement javaOpts;//java-opts
  protected PropertyElementCollection javaOpt;//java-opt 0-unbounded
  protected PropertyElementCollection arg;//arg 0-unbounded
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive o-unbounded
  protected CheckBoxPropertyElement captureOutput;//capture-output (flag/checkbox)
  
  public JavaActionNode(Workflow workflow) {
    this(workflow, null);
  }

  public JavaActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
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
    
    mainClass = new TextPropertyElement(PROP_MAIN_CLASS, "Main Class");
    addPropertyElement(mainClass);
    
    javaOpts = new TextPropertyElement(PROP_JAVA_OPTS, "Java Opts");
    addPropertyElement(javaOpts);
    
    javaOpt = new PropertyElementCollection("Java Opt", new TextPropertyElement(PROP_JAVA_OPT, "Java Opt"));
    addPropertyElement(javaOpt);
    
    arg = new PropertyElementCollection("Arg", new TextPropertyElement(PROP_ARG, "Arg"));
    addPropertyElement(arg);
    
    file = new PropertyElementCollection("File", new TextPropertyElement(PROP_FILE, "File"));
    addPropertyElement(file);
    
    archive = new PropertyElementCollection("Archive", new TextPropertyElement(PROP_ARCHIVE, "Archive"));
    addPropertyElement(archive);
    
    captureOutput = new CheckBoxPropertyElement(PROP_CAPTURE_OUTPUT, "Capture Output");
    addPropertyElement(captureOutput);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("java-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element java = element.addElement("java");
    
    XMLWriteUtils.writeTextPropertyAsElement(jobTracker, java, "job-tracker");
    XMLWriteUtils.writeTextPropertyAsElement(nameNode, java, "name-node");
    XMLWriteUtils.writePrepareProperty(prepare, java, "prepare");
    XMLWriteUtils.writeTextCollectionAsElements(jobXML, java, "job-xml");
    XMLWriteUtils.writePropertiesCollection(configuration, java, "configuration", "property");
    XMLWriteUtils.writeTextPropertyAsElement(mainClass, java, "main-class");
    XMLWriteUtils.writeTextPropertyAsElement(javaOpts, java, "java-opts");
    XMLWriteUtils.writeTextCollectionAsElements(javaOpt, java, "java-opt");
    XMLWriteUtils.writeTextCollectionAsElements(arg, java, "arg");
    XMLWriteUtils.writeTextCollectionAsElements(file, java, "file");
    XMLWriteUtils.writeTextCollectionAsElements(archive, java, "archive");
    XMLWriteUtils.writeCheckPropertyAsElement(captureOutput, java, "capture-output");
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    XMLReadUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./java/job-tracker");
    XMLReadUtils.initTextPropertyFrom(nameNode, hpdlNode, "./java/name-node");
    XMLReadUtils.initPreparePropertyFrom(prepare, hpdlNode, "./java/prepare");
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./java/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./java/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(mainClass, hpdlNode, "./java/main-class");
    XMLReadUtils.initTextPropertyFrom(javaOpts, hpdlNode, "./java/java-opts");
    XMLReadUtils.initTextCollectionFrom(javaOpt, hpdlNode, "./java/java-opt");
    XMLReadUtils.initTextCollectionFrom(arg, hpdlNode, "./java/arg");
    XMLReadUtils.initTextCollectionFrom(file, hpdlNode, "./java/file");
    XMLReadUtils.initTextCollectionFrom(archive, hpdlNode, "./java/archive");
    XMLReadUtils.initCheckPropertyFrom(captureOutput, hpdlNode, "./java/capture-output");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_JAVA;
  }
  
}
