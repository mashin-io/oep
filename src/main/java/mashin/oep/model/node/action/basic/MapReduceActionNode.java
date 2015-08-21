package mashin.oep.model.node.action.basic;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.hpdl.XMLUtils;
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
  //public static final String PROP_PREPARE_DELETE = "prop.node.map-reduce.prepare.delete";
  //public static final String PROP_PREPARE_MKDIR = "prop.node.map-reduce.prepare.mkdir";
  public static final String PROP_STREAMING = "prop.node.map-reduce.streaming";
  //public static final String PROP_STREAMING_MAPPER = "prop.node.map-reduce.streaming.mapper";
  //public static final String PROP_STREAMING_REDUCER = "prop.node.map-reduce.streaming.reducer";
  //public static final String PROP_STREAMING_RECORDREADER = "prop.node.map-reduce.streaming.record-reader";
  //public static final String PROP_STREAMING_RECORDREADERMAPPING = "prop.node.map-reduce.streaming.record-reader-mapping";
  //public static final String PROP_STREAMING_ENV = "prop.node.map-reduce.streaming.env";
  public static final String PROP_PIPES = "prop.node.map-reduce.pipes";
  //public static final String PROP_PIPES_MAP = "prop.node.map-reduce.pipes.map";
  //public static final String PROP_PIPES_REDUCE = "prop.node.map-reduce.pipes.reduce";
  //public static final String PROP_PIPES_INPUTFORMAT = "prop.node.map-reduce.pipes.inputformat";
  //public static final String PROP_PIPES_OUTPUTFORMAT = "prop.node.map-reduce.pipes.outputformat";
  //public static final String PROP_PIPES_PARTITIONER = "prop.node.map-reduce.pipes.partitioner";
  //public static final String PROP_PIPES_WRITER = "prop.node.map-reduce.pipes.writer";
  //public static final String PROP_PIPES_PROGRAM = "prop.node.map-reduce.pipes.program";
  public static final String PROP_JOBXML = "prop.node.map-reduce.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.map-reduce.configuration";
  public static final String PROP_CONFIGCLASS = "prop.node.map-reduce.config-class";
  public static final String PROP_FILE = "prop.node.map-reduce.file";
  public static final String PROP_ARCHIVE = "prop.node.map-reduce.archive";
  
  //public static final String CATEGORY_PREPARE = "Prepare";
  public static final String CATEGORY_STREAMING = "Streaming";
  public static final String CATEGORY_PIPES = "Pipes";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  //protected PropertyElementCollection prepareDelete;//delete {path} 0-unbounded
  //protected PropertyElementCollection prepareMkdir;//mkdir {path} 0-unbounded
  
  //streaming
  protected StreamingPropertyElement streaming;
  //protected TextPropertyElement streamingMapper;//mapper
  //protected TextPropertyElement streamingReducer;//reducer
  //protected TextPropertyElement streamingRecordReader;//record-reader
  //protected PropertyElementCollection streamingRecordReaderMapping;//record-reader-mapping 0-unbounded
  //protected PropertyElementCollection streamingEnv;//env 0-unbounded
  
  //pipes
  protected PipesPropertyElement pipes;
  //protected TextPropertyElement pipesMap;//map
  //protected TextPropertyElement pipesReduce;//reduce
  //protected TextPropertyElement pipesInputFormat;//inputformat
  //protected TextPropertyElement pipesOutputFormat;//outputformat
  //protected TextPropertyElement pipesPartitioner;//partitioner
  //protected TextPropertyElement pipesWriter;//writer
  //protected TextPropertyElement pipesProgram;//program
  
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
    //prepareDelete = new PropertyElementCollection(CATEGORY_PREPARE, new TextPropertyElement(PROP_PREPARE_DELETE, "Delete"));
    //addPropertyElement(prepareDelete);
    //
    //prepareMkdir = new PropertyElementCollection(CATEGORY_PREPARE, new TextPropertyElement(PROP_PREPARE_MKDIR, "Mkdir"));
    //addPropertyElement(prepareMkdir);

    //streaming
    streaming = new StreamingPropertyElement(PROP_STREAMING, "Streaming");
    addPropertyElement(streaming);
    //streamingMapper = new TextPropertyElement(PROP_STREAMING_MAPPER, "Mapper");
    //streamingMapper.setCategory(CATEGORY_STREAMING);
    //addPropertyElement(streamingMapper);
    //
    //streamingReducer = new TextPropertyElement(PROP_STREAMING_REDUCER, "Reducer");
    //streamingReducer.setCategory(CATEGORY_STREAMING);
    //addPropertyElement(streamingReducer);
    //
    //streamingRecordReader = new TextPropertyElement(PROP_STREAMING_RECORDREADER, "Record Reader");
    //streamingRecordReader.setCategory(CATEGORY_STREAMING);
    //addPropertyElement(streamingRecordReader);
    //
    //streamingRecordReaderMapping = new PropertyElementCollection(CATEGORY_STREAMING,
    //    new TextPropertyElement(PROP_STREAMING_RECORDREADERMAPPING, "Record Reader Mapping"));
    //addPropertyElement(streamingRecordReaderMapping);
    //
    //streamingEnv = new PropertyElementCollection(CATEGORY_STREAMING,
    //    new TextPropertyElement(PROP_STREAMING_ENV, "Env"));
    //addPropertyElement(streamingEnv);

    //pipes
    pipes = new PipesPropertyElement(PROP_PIPES, "Pipes");
    addPropertyElement(pipes);
    //pipesMap = new TextPropertyElement(PROP_PIPES_MAP, "Map");
    //pipesMap.setCategory(CATEGORY_PIPES);
    //addPropertyElement(pipesMap);
    //
    //pipesReduce = new TextPropertyElement(PROP_PIPES_REDUCE, "Reduce");
    //pipesReduce.setCategory(CATEGORY_PIPES);
    //addPropertyElement(pipesReduce);
    //
    //pipesInputFormat = new TextPropertyElement(PROP_PIPES_INPUTFORMAT, "Inputformat");
    //pipesInputFormat.setCategory(CATEGORY_PIPES);
    //addPropertyElement(pipesInputFormat);
    //
    //pipesOutputFormat = new TextPropertyElement(PROP_PIPES_OUTPUTFORMAT, "Outputformat");
    //pipesOutputFormat.setCategory(CATEGORY_PIPES);
    //addPropertyElement(pipesOutputFormat);
    //    
    //pipesPartitioner = new TextPropertyElement(PROP_PIPES_PARTITIONER, "Partitioner");
    //pipesPartitioner.setCategory(CATEGORY_PIPES);
    //addPropertyElement(pipesPartitioner);
    //    
    //pipesWriter = new TextPropertyElement(PROP_PIPES_WRITER, "Writer");
    //pipesWriter.setCategory(CATEGORY_PIPES);
    //addPropertyElement(pipesWriter);
    //    
    //pipesProgram = new TextPropertyElement(PROP_PIPES_PROGRAM, "Program");
    //pipesProgram.setCategory(CATEGORY_PIPES);
    //addPropertyElement(pipesProgram);
        
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
    
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./map-reduce/job-tracker");
    XMLUtils.initTextPropertyFrom(nameNode, hpdlNode, "./map-reduce/name-node");
    
    //prepare
    XMLUtils.initPreparePropertyFrom(prepare, hpdlNode, "./map-reduce/prepare");

    //streaming
    XMLUtils.initStreamingPropertyFrom(streaming, hpdlNode, "./map-reduce/streaming");
    
    //pipes
    XMLUtils.initPipesPropertyFrom(pipes, hpdlNode, "./map-reduce/pipes");
    
    XMLUtils.initTextCollectionFrom(jobXML, hpdlNode, "./map-reduce/job-xml");
    XMLUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./map-reduce/configuration", "./property");
    XMLUtils.initTextPropertyFrom(configClass, hpdlNode, "./map-reduce/config-class");
    XMLUtils.initTextCollectionFrom(file, hpdlNode, "./map-reduce/file");
    XMLUtils.initTextCollectionFrom(archive, hpdlNode, "./map-reduce/archive");
  }
  
  @Override
  public String getNodeType() {
    return "map-reduce";
  }
  
}
