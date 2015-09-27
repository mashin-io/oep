package io.mashin.oep.model.node.action.basic;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.property.ComboBoxPropertyElement;
import io.mashin.oep.model.property.PipesPropertyElement;
import io.mashin.oep.model.property.PreparePropertyElement;
import io.mashin.oep.model.property.PropertyElementCollection;
import io.mashin.oep.model.property.PropertyPropertyElement;
import io.mashin.oep.model.property.StreamingPropertyElement;
import io.mashin.oep.model.property.TextPropertyElement;
import io.mashin.oep.model.property.filter.SchemaVersionRangeFilter;

import org.dom4j.Element;
import org.dom4j.Node;

public class MapReduceActionNode extends BasicActionNode {

  public static final String PROP_JOBTRACKER = "prop.node.map-reduce.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.map-reduce.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.map-reduce.prepare";
  public static final String PROP_JOB_TYPE = "prop.node.map-reduce.job-type";
  public static final String PROP_STREAMING = "prop.node.map-reduce.streaming";
  public static final String PROP_PIPES = "prop.node.map-reduce.pipes";
  public static final String PROP_JOBXML = "prop.node.map-reduce.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.map-reduce.configuration";
  public static final String PROP_CONFIGCLASS = "prop.node.map-reduce.config-class";
  public static final String PROP_FILE = "prop.node.map-reduce.file";
  public static final String PROP_ARCHIVE = "prop.node.map-reduce.archive";
  
  private static final Integer JOB_TYPE_NA = 0;
  private static final Integer JOB_TYPE_STREAMING = 1;
  private static final Integer JOB_TYPE_PIPES = 2;
  
  public static final String CATEGORY_STREAMING = "Streaming";
  public static final String CATEGORY_PIPES = "Pipes";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  
  protected ComboBoxPropertyElement jobType;
  
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

    jobType = new ComboBoxPropertyElement(PROP_JOB_TYPE, "Job Type");
    jobType.setLabelsArray(new String[] { " ", "Streaming", "Pipes" });
    jobType.setValuesArray(new Integer[] { JOB_TYPE_NA, JOB_TYPE_STREAMING, JOB_TYPE_PIPES });
    addPropertyElement(jobType);
    
    //streaming
    streaming = new StreamingPropertyElement(PROP_STREAMING, "Streaming",
        pe -> jobType.getValue() == JOB_TYPE_STREAMING);
    addPropertyElement(streaming);

    //pipes
    pipes = new PipesPropertyElement(PROP_PIPES, "Pipes",
        pe -> jobType.getValue() == JOB_TYPE_PIPES);
    addPropertyElement(pipes);
        
    jobXML = new PropertyElementCollection("Job XML", new TextPropertyElement(PROP_JOBXML, "Job XML"));
    addPropertyElement(jobXML);
    
    configuration = new PropertyElementCollection("Configuration",
                      new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
    
    configClass = new TextPropertyElement(PROP_CONFIGCLASS, "Config Class",
        new SchemaVersionRangeFilter(SchemaVersion.V_0_5, SchemaVersion.V_ANY, workflow));
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
    Element mapReduce = element.addElement("map-reduce");
    
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
    if (streaming.isSet()) {
      jobType.setValue(JOB_TYPE_STREAMING);
    } else if (pipes.isSet()) {
      jobType.setValue(JOB_TYPE_PIPES);
    } else {
      jobType.setValue(JOB_TYPE_NA);
    }
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./map-reduce/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./map-reduce/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(configClass, hpdlNode, "./map-reduce/config-class");
    XMLReadUtils.initTextCollectionFrom(file, hpdlNode, "./map-reduce/file");
    XMLReadUtils.initTextCollectionFrom(archive, hpdlNode, "./map-reduce/archive");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_MAPREDUCE;
  }
  
}
