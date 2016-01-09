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

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class ComboBoxPropertyElement extends SingularPropertyElement {

  private IPropertyDescriptor propertyDescriptor;
  
  private String[] labelsArray;
  private Object[] valuesArray;
  
  private int valueIndex;
  
  public ComboBoxPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public ComboBoxPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    valueIndex = 0;
  }
  
  public void setLabelsArray(String[] labelsArray) {
    this.labelsArray = labelsArray;
    if (valueIndex >= labelsArray.length)
      resetValue();
  }
  
  public String[] getLabelsArray() {
    return labelsArray;
  }
  
  public void setValuesArray(Object[] valuesArray) {
    this.valuesArray = valuesArray;
    if (valueIndex >= valuesArray.length)
      resetValue();
  }
  
  public Object[] getValuesArray() {
    return valuesArray;
  }

  @Override
  public IPropertyDescriptor getPropertyDescriptor() {
    if (!isEditable()) {
      return null;
    }
    propertyDescriptor = new ComboBoxPropertyDescriptor(
        getId(), getName(),
        getLabelsArray());
    return propertyDescriptor;
  }

  @Override
  public void setValue(Object value) {
    valueIndex = (Integer) value;
  }

  @Override
  public void resetValue() {
    valueIndex = 0;
  }

  @Override
  public boolean isSet() {
    return true;
  }

  @Override
  public Object getValue() {
    return valueIndex;
  }

  @Override
  public Object getEditableValue() {
    return this;
  }
  
  public Object getContentValue() {
    return valuesArray[valueIndex];
  }
  
  public void setContentValue(Object contentValue) {
    for (int i = 0; i < valuesArray.length; i++) {
      if (valuesArray[i].equals(contentValue)) {
        setValue(i);
        return;
      }
    }
  }

}
