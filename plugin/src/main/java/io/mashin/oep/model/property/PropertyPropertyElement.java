package io.mashin.oep.model.property;

import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;


public class PropertyPropertyElement extends PropertyElementGroup {

  public TextPropertyElement name;
  public TextPropertyElement value;
  public TextPropertyElement description;
  
  String nameId, valueId, descriptionId;
  
  public PropertyPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public PropertyPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    
    nameId = id + ".name";
    valueId = id + ".value";
    descriptionId = id + ".description";
    
    this.name = new TextPropertyElement(nameId, "Name");
    value = new TextPropertyElement(valueId, "Value");
    description = new TextPropertyElement(descriptionId, "Description");
    
    propertyElements.add(this.name);
    propertyElements.add(value);
    propertyElements.add(description);
  }
  
  @Override
  public void setComplexValue(Object value) {}
  
  public void setValueOfName(String name) {
    setValue(nameId, name);
  }
  
  public void setValueOfValue(String value) {
    setValue(valueId, value);
  }
  
  public void setValueOfDescription(String description) {
    setValue(descriptionId, description);
  }
  
}
