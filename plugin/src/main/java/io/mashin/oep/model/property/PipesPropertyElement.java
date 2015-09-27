package io.mashin.oep.model.property;

import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;

public class PipesPropertyElement extends PropertyElementGroup {

  public TextPropertyElement map;//map
  public TextPropertyElement reduce;//reduce
  public TextPropertyElement inputFormat;//inputformat
  public TextPropertyElement outputFormat;//outputformat
  public TextPropertyElement partitioner;//partitioner
  public TextPropertyElement writer;//writer
  public TextPropertyElement program;//program
  
  String mapId, reduceId, inputFormatId, outputFormatId, partitionerId,
      writerId, programId;
  
  public PipesPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public PipesPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    
    mapId = id + ".map";
    reduceId = id + ".reduce";
    inputFormatId = id + ".inputformat";
    outputFormatId = id + ".outputformat";
    partitionerId = id + ".partitioner";
    writerId = id + ".writer";
    programId = id + ".program";
    
    map = new TextPropertyElement(mapId, "Map");
    reduce = new TextPropertyElement(reduceId, "Reduce");
    inputFormat = new TextPropertyElement(inputFormatId, "Input Format");
    outputFormat = new TextPropertyElement(outputFormatId, "Output Format");
    partitioner = new TextPropertyElement(partitionerId, "Partitioner");
    writer = new TextPropertyElement(writerId, "Writer");
    program = new TextPropertyElement(programId, "Program");
    
    propertyElements.add(map);
    propertyElements.add(reduce);
    propertyElements.add(inputFormat);
    propertyElements.add(outputFormat);
    propertyElements.add(partitioner);
    propertyElements.add(writer);
    propertyElements.add(program);
  }

  @Override
  public void setComplexValue(Object value) {
    
  }

}
