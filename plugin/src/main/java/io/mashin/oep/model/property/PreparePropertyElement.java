package io.mashin.oep.model.property;

import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;

public class PreparePropertyElement extends PropertyElementGroup {

  public PropertyElementCollection delete;
  public PropertyElementCollection mkdir;
  
  String deleteId, mkdirId;
  
  public PreparePropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public PreparePropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    
    deleteId = id + ".delete";
    mkdirId = id + ".mkdir";
    
    delete = new PropertyElementCollection(name,
              new TextPropertyElement(deleteId, "Delete"));
    mkdir = new PropertyElementCollection(name,
              new TextPropertyElement(mkdirId, "Mkdir"));
    
    propertyElements.add(delete);
    propertyElements.add(mkdir);
  }

  @Override
  public void setComplexValue(Object value) {
  }

}
