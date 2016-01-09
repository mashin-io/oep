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
