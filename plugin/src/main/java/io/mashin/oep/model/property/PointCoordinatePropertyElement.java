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

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class PointCoordinatePropertyElement extends SingularPropertyElement {

  private IPropertyDescriptor propertyDescriptor;
  
  private Integer coordinate;
  
  public PointCoordinatePropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public PointCoordinatePropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    coordinate = 0;
  }

  @Override
  public void setValue(Object value) {
    coordinate = Integer.parseInt(value.toString());
  }

  @Override
  public void resetValue() {
    //coordinate = 0;
  }

  @Override
  public boolean isSet() {
    return true;
  }

  @Override
  public Object getValue() {
    return coordinate + "";
  }
  
  public void setFromInteger(Integer value) {
    coordinate = new Integer(value);
  }
  
  public Integer getAsInteger() {
    return new Integer(coordinate);
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
      ((PropertyDescriptor) propertyDescriptor)
        .setValidator(new ICellEditorValidator() {
          @Override
          public String isValid(Object value) {
            try {
              Integer.parseInt((String) value);
            } catch (NumberFormatException exc) {
              return "Not a number";
            }
            return null;
          }
        });
    }
    return propertyDescriptor;
  }
  
}
