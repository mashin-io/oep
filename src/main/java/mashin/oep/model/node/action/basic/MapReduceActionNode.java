package mashin.oep.model.node.action.basic;

import mashin.oep.model.Workflow;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class MapReduceActionNode extends BasicActionNode {

  public static final String PROP_JOBTRACKER = "prop.node.map-reduce.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.map-reduce.name-ndoe";
  public static final String PROP_PREPARE_DELETE = "prop.node.map-reduce.prepare.delete";
  public static final String PROP_PREPARE_MKDIR = "prop.node.map-reduce.prepare.mkdir";
  public static final String PROP_STREAMING_MAPPER = "prop.node.map-reduce.streaming.mapper";
  public static final String PROP_STREAMING_REDUCER = "prop.node.map-reduce.streaming.reducer";
  public static final String PROP_STREAMING_RECORDREADER = "prop.node.map-reduce.streaming.record-reader";
  public static final String PROP_STREAMING_RECORDREADERMAPPING = "prop.node.map-reduce.streaming.record-reader-mapping";
  public static final String PROP_STREAMING_ENV = "prop.node.map-reduce.streaming.env";
  public static final String PROP_PIPES_MAP = "prop.node.map-reduce.pipes.map";
  public static final String PROP_PIPES_REDUCE = "prop.node.map-reduce.pipes.reduce";
  public static final String PROP_PIPES_INPUTFORMAT = "prop.node.map-reduce.pipes.inputformat";
  public static final String PROP_PIPES_OUTPUTFORMAT = "prop.node.map-reduce.pipes.outputformat";
  public static final String PROP_PIPES_PARTITIONER = "prop.node.map-reduce.pipes.partitioner";
  public static final String PROP_PIPES_WRITER = "prop.node.map-reduce.pipes.writer";
  public static final String PROP_PIPES_PROGRAM = "prop.node.map-reduce.pipes.program";
  public static final String PROP_JOBXML = "prop.node.map-reduce.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.map-reduce.configuration";
  public static final String PROP_CONFIGCLASS = "prop.node.map-reduce.config-class";
  public static final String PROP_FILE = "prop.node.map-reduce.file";
  public static final String PROP_ARCHIVE = "prop.node.map-reduce.archive";
  
  public static final String CATEGORY_PREPARE = "Prepare";
  public static final String CATEGORY_STREAMING = "Streaming";
  public static final String CATEGORY_PIPES = "Pipes";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PropertyElementCollection prepareDelete;//delete {path} 0-unbounded
  protected PropertyElementCollection prepareMkdir;//mkdir {path} 0-unbounded
  
  //streaming
  protected TextPropertyElement streamingMapper;//mapper
  protected TextPropertyElement streamingReducer;//reducer
  protected TextPropertyElement streamingRecordReader;//record-reader
  protected PropertyElementCollection streamingRecordReaderMapping;//record-reader-mapping 0-unbounded
  protected PropertyElementCollection streamingEnv;//env 0-unbounded
  
  //pipes
  protected TextPropertyElement pipesMap;//map
  protected TextPropertyElement pipesReduce;//reduce
  protected TextPropertyElement pipesInputFormat;//inputformat
  protected TextPropertyElement pipesOutputFormat;//outputformat
  protected TextPropertyElement pipesPartitioner;//partitioner
  protected TextPropertyElement pipesWriter;//writer
  protected TextPropertyElement pipesProgram;//program
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement configClass;//config-class
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive o-unbounded
  
  public MapReduceActionNode(Workflow workflow) {
    super(workflow);
    
    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);
    
    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);

    //prepare
    prepareDelete = new PropertyElementCollection(CATEGORY_PREPARE, new TextPropertyElement(PROP_PREPARE_DELETE, "Delete"));
    addPropertyElement(prepareDelete);
    
    prepareMkdir = new PropertyElementCollection(CATEGORY_PREPARE, new TextPropertyElement(PROP_PREPARE_MKDIR, "Mkdir"));
    addPropertyElement(prepareMkdir);

    //streaming
    streamingMapper = new TextPropertyElement(PROP_STREAMING_MAPPER, "Mapper");
    streamingMapper.setCategory(CATEGORY_STREAMING);
    addPropertyElement(streamingMapper);
    
    streamingReducer = new TextPropertyElement(PROP_STREAMING_REDUCER, "Reducer");
    streamingReducer.setCategory(CATEGORY_STREAMING);
    addPropertyElement(streamingReducer);
    
    streamingRecordReader = new TextPropertyElement(PROP_STREAMING_RECORDREADER, "Record Reader");
    streamingRecordReader.setCategory(CATEGORY_STREAMING);
    addPropertyElement(streamingRecordReader);
    
    streamingRecordReaderMapping = new PropertyElementCollection(CATEGORY_STREAMING,
        new TextPropertyElement(PROP_STREAMING_RECORDREADERMAPPING, "Record Reader Mapping"));
    addPropertyElement(streamingRecordReaderMapping);
    
    streamingEnv = new PropertyElementCollection(CATEGORY_STREAMING,
        new TextPropertyElement(PROP_STREAMING_ENV, "Env"));
    addPropertyElement(streamingEnv);

    //pipes
    pipesMap = new TextPropertyElement(PROP_PIPES_MAP, "Map");
    pipesMap.setCategory(CATEGORY_PIPES);
    addPropertyElement(pipesMap);
    
    pipesReduce = new TextPropertyElement(PROP_PIPES_REDUCE, "Reduce");
    pipesReduce.setCategory(CATEGORY_PIPES);
    addPropertyElement(pipesReduce);
    
    pipesInputFormat = new TextPropertyElement(PROP_PIPES_INPUTFORMAT, "Inputformat");
    pipesInputFormat.setCategory(CATEGORY_PIPES);
    addPropertyElement(pipesInputFormat);
    
    pipesOutputFormat = new TextPropertyElement(PROP_PIPES_OUTPUTFORMAT, "Outputformat");
    pipesOutputFormat.setCategory(CATEGORY_PIPES);
    addPropertyElement(pipesOutputFormat);
        
    pipesPartitioner = new TextPropertyElement(PROP_PIPES_PARTITIONER, "Partitioner");
    pipesPartitioner.setCategory(CATEGORY_PIPES);
    addPropertyElement(pipesPartitioner);
        
    pipesWriter = new TextPropertyElement(PROP_PIPES_WRITER, "Writer");
    pipesWriter.setCategory(CATEGORY_PIPES);
    addPropertyElement(pipesWriter);
        
    pipesProgram = new TextPropertyElement(PROP_PIPES_PROGRAM, "Program");
    pipesProgram.setCategory(CATEGORY_PIPES);
    addPropertyElement(pipesProgram);
        
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
    
    setName("map-reduce-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
