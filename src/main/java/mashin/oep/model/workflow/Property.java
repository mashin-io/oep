package mashin.oep.model.workflow;

import mashin.oep.model.property.PropertyElementGroup;
import mashin.oep.model.property.TextPropertyElement;

public class Property extends PropertyElementGroup {

  public Property(String id, String name) {
    super(id, name);
    propertyElements.add(new TextPropertyElement(id + ".name", "Name"));
    propertyElements.add(new TextPropertyElement(id + ".value", "Value"));
    propertyElements.add(new TextPropertyElement(id + ".description", "Description"));
  }
  
}
