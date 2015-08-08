package mashin.oep.model.property;


public class PropertyPropertyElement extends PropertyElementGroup {

  public PropertyPropertyElement(String id, String name) {
    super(id, name);
    propertyElements.add(new TextPropertyElement(id + ".name", "Name"));
    propertyElements.add(new TextPropertyElement(id + ".value", "Value"));
    propertyElements.add(new TextPropertyElement(id + ".description", "Description"));
  }
  
  @Override
  public void setComplexValue(Object value) {}
  
}
