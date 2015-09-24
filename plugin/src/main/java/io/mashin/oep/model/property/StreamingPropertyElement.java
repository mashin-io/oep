package io.mashin.oep.model.property;

public class StreamingPropertyElement extends PropertyElementGroup {

  public TextPropertyElement mapper;//mapper
  public TextPropertyElement reducer;//reducer
  public TextPropertyElement recordReader;//record-reader
  public PropertyElementCollection recordReaderMapping;//record-reader-mapping 0-unbounded
  public PropertyElementCollection env;//env 0-unbounded
  
  String mapperId, reducerId, recordReaderId, recordReaderMappingId, envId;
  
  public StreamingPropertyElement(String id, String name) {
    super(id, name);
    
    mapperId = id + ".mapper";
    reducerId = id + ".reducer";
    recordReaderId = id + ".record-reader";
    recordReaderMappingId = id + ".record-reader-mapping";
    envId = id + ".env";
    
    mapper = new TextPropertyElement(mapperId, "Mapper");
    reducer = new TextPropertyElement(reducerId, "Reducer");
    recordReader = new TextPropertyElement(recordReaderId, "Record Reader");
    recordReaderMapping = new PropertyElementCollection(name,
        new TextPropertyElement(recordReaderMappingId, "Record Reader Mapping"));
    env = new PropertyElementCollection(name,
        new TextPropertyElement(envId, "Env"));
    
    propertyElements.add(mapper);
    propertyElements.add(reducer);
    propertyElements.add(recordReader);
    propertyElements.add(recordReaderMapping);
    propertyElements.add(env);
  }

  @Override
  public void setComplexValue(Object value) {
  }

}
