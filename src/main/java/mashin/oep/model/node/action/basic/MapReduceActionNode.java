package mashin.oep.model.node.action.basic;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.hpdl.XMLWriteUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.PipesPropertyElement;
import mashin.oep.model.property.PreparePropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.StreamingPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class MapReduceActionNode extends BasicActionNode {

  public static final String PROP_JOBTRACKER = "prop.node.map-reduce.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.map-reduce.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.map-reduce.prepare";
  public static final String PROP_STREAMING = "prop.node.map-reduce.streaming";
  public static final String PROP_PIPES = "prop.node.map-reduce.pipes";
  public static final String PROP_JOBXML = "prop.node.map-reduce.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.map-reduce.configuration";
  public static final String PROP_CONFIGCLASS = "prop.node.map-reduce.config-class";
  public static final String PROP_FILE = "prop.node.map-reduce.file";
  public static final String PROP_ARCHIVE = "prop.node.map-reduce.archive";
  
  public static final String CATEGORY_STREAMING = "Streaming";
  public static final String CATEGORY_PIPES = "Pipes";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  
  //streaming
  protected StreamingPropertyElement streaming;
  
  //pipes
  protected PipesPropertyElement pipes;
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement configClass;//config-class
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive o-unbounded
  
  public MapReduceActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public MapReduceActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    
    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);
    
    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);

    //prepare
    prepare = new PreparePropertyElement(PROP_PREPARE, "Prepare");
    addPropertyElement(prepare);

    //streaming
    streaming = new StreamingPropertyElement(PROP_STREAMING, "Streaming");
    addPropertyElement(streaming);

    //pipes
    pipes = new PipesPropertyElement(PROP_PIPES, "Pipes");
    addPropertyElement(pipes);
        
    jobXML = new PropertyElementCollection("Job XML", new TextPropertyElement(PROP_JOBXML, "Job XML"));
    addPropertyElement(jobXML);
    
    configuration = new PropertyElementCollection("Configuration",
                      new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
    
    configClass = new TextPropertyElement(PROP_CONFIGCLASS, "Config Class");
    addPropertyElement(configClass);
    
    file = new PropertyElementCollection("File", new TextPropertyElement(PROP_FILE, "File"));
    addPropertyElement(file);
    
    archive = new PropertyElementCollection("Archive", new TextPropertyElement(PROP_ARCHIVE, "Archive"));
    addPropertyElement(archive);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("map-reduce-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element mapReduce = (Element) element.selectSingleNode("./map-reduce");
    if (mapReduce == null) {
      mapReduce = element.addElement("map-reduce");
    }
    
    XMLWriteUtils.writeTextPropertyAsElement(jobTracker, mapReduce, "job-tracker");
    XMLWriteUtils.writeTextPropertyAsElement(nameNode, mapReduce, "name-node");
    XMLWriteUtils.writePrepareProperty(prepare, mapReduce, "prepare");
    XMLWriteUtils.writeStreamingProperty(streaming, mapReduce);
    XMLWriteUtils.writePipesProperty(pipes, mapReduce);
    XMLWriteUtils.writeTextCollectionAsElements(jobXML, mapReduce, "job-xml");
    XMLWriteUtils.writePropertiesCollection(configuration, mapReduce, "configuration", "property");
    XMLWriteUtils.writeTextPropertyAsElement(configClass, mapReduce, "config-class");
    XMLWriteUtils.writeTextCollectionAsElements(file, mapReduce, "file");
    XMLWriteUtils.writeTextCollectionAsElements(archive, mapReduce, "archive");
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./map-reduce/job-tracker");
    XMLReadUtils.initTextPropertyFrom(nameNode, hpdlNode, "./map-reduce/name-node");
    XMLReadUtils.initPreparePropertyFrom(prepare, hpdlNode, "./map-reduce/prepare");
    XMLReadUtils.initStreamingPropertyFrom(streaming, hpdlNode, "./map-reduce/streaming");
    XMLReadUtils.initPipesPropertyFrom(pipes, hpdlNode, "./map-reduce/pipes");
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./map-reduce/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./map-reduce/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(configClass, hpdlNode, "./map-reduce/config-class");
    XMLReadUtils.initTextCollectionFrom(file, hpdlNode, "./map-reduce/file");
    XMLReadUtils.initTextCollectionFrom(archive, hpdlNode, "./map-reduce/archive");
  }
  
  @Override
  public String getNodeType() {
    return "map-reduce";
  }
  
}
