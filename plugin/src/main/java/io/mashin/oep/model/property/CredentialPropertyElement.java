/**
 * Copyright (c) 2015 Mashin (http://mashin.io). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
