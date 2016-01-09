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

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class TextPropertyElement extends SingularPropertyElement {

  private IPropertyDescriptor propertyDescriptor;
  
  private String text;
  
  public TextPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public TextPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    text = "";
  }
  
  @Override
  public void setValue(Object value) {
    text = value.toString();
  }
  
  @Override
  public void resetValue() {
    text = "";
  }

  @Override
  public boolean isSet() {
    return !text.isEmpty();
  }

  @Override
  public Object getValue() {
    return text;
  }

  @Override
  public Object getEditableValue() {
    return this;
  }

  @Override
  public IPropertyDescriptor getPropertyDescriptor() {
    if (!isEditable()) {
      return null;
    }
    if (propertyDescriptor == null) {
      propertyDescriptor = new TextPropertyDescriptor(getId(), getName());
    }
    return propertyDescriptor;
  }
  
  public void setStringValue(String value) {
    text = value;
  }
  
  public String getStringValue() {
    return text;
  }

}
