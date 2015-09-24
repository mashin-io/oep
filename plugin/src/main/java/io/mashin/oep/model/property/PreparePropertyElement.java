package io.mashin.oep.model.property;

public class PreparePropertyElement extends PropertyElementGroup {

  public PropertyElementCollection delete;
  public PropertyElementCollection mkdir;
  
  String deleteId, mkdirId;
  
  public PreparePropertyElement(String id, String name) {
    super(id, name);
    
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
