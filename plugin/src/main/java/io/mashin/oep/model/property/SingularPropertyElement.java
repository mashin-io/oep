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
import org.eclipse.ui.views.properties.PropertyDescriptor;


public abstract class SingularPropertyElement extends PropertyElement {

  public SingularPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public SingularPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
  }

  @Override
  public void setValue(String id, Object value) {
    setValue(value);
  }

  @Override
  public void resetValue(String id) {
    resetValue();
  }

  @Override
  public boolean isSet(String id) {
    return isSet();
  }

  @Override
  public Object getValue(String id) {
    return getValue();
  }

  @Override
  public Object getEditableValue(String id) {
    return getEditableValue();
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    IPropertyDescriptor[] descriptor = null;
    IPropertyDescriptor desc = getPropertyDescriptor();
    if (desc != null && isEditable() && filter()) {
      if (category != null && !category.isEmpty()) {
        ((PropertyDescriptor) desc).setCategory(category);
      }
      descriptor = new IPropertyDescriptor[] { desc };
    } else {
      descriptor = new IPropertyDescriptor[0];
    }
    return descriptor;
  }
  
  public abstract IPropertyDescriptor getPropertyDescriptor();
  
}
