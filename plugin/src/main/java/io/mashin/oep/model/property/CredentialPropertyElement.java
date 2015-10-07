package io.mashin.oep.model.property;

import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;

public class CredentialPropertyElement extends PropertyElementGroup {

  public PropertyElementCollection credential;
  public TextPropertyElement name;
  public TextPropertyElement type;
  
  String credentialId, nameId, typeId;
  
  public CredentialPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public CredentialPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    
    credentialId = id + ".credential";
    nameId = id + ".name";
    typeId = id + ".type";
    
    credential = new PropertyElementCollection(getCategory(),
          new PropertyPropertyElement(credentialId, "Property"));
    this.name = new TextPropertyElement(nameId, "Name");
    type = new TextPropertyElement(typeId, "Type");
    
    propertyElements.add(credential);
    propertyElements.add(this.name);
    propertyElements.add(type);
  }

  @Override
  public void setComplexValue(Object value) { }
  
  public void setValueOfCredential(PropertyElementCollection credential) {
    setValue(credentialId, credential);
  }
  
  public void setValueOfName(String name) {
    setValue(nameId, name);
  }
  
  public void setValueOfType(String type) {
    setValue(typeId, type);
  }
  
}
