package mashin.oep.model.property;


public class Property extends PropertyElementGroup {

  public Property(String id, String name) {
    super(id, name);
    propertyElements.add(new TextPropertyElement(id + ".name", "Name"));
    propertyElements.add(new TextPropertyElement(id + ".value", "Value"));
    propertyElements.add(new TextPropertyElement(id + ".description", "Description"));
  }
  
}
