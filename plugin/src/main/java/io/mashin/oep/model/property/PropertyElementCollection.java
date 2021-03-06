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

import io.mashin.oep.Utils;
import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class PropertyElementCollection extends PropertyElement {

  private List<PropertyElement> list;
  private PropertyElement template;
  private PropertyElement empty;

  public PropertyElementCollection(String category, PropertyElement template) {
    this(category, template, new DefaultPropertyFilter());
  }
  
  public PropertyElementCollection(String category, PropertyElement template, PropertyFilter filter) {
    super(template.getId(), category, filter);
    this.category = category;
    list = new ArrayList<PropertyElement>();
    this.template = template;
    empty = createEmpty();
    list.add(empty);
  }

  @Override
  public boolean hasId(String propertyName) {
    if (id.equals(propertyName)) {
      return true;
    }
    for (PropertyElement propertyElement : list) {
      if (propertyElement.hasId(propertyName)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof PropertyElementCollection) {
      PropertyElementCollection other = (PropertyElementCollection) value;
      this.list = other.list;
      this.template = other.template;
      this.empty = other.empty;
    }
  }
  
  @Override
  public void setValue(String id, Object value) {
    if (id.equals(getId())) {
      setValue(value);
    }
    
    for (int i = 0; i < list.size(); i++) {
      PropertyElement propertyElement = list.get(i);
      if (propertyElement.hasId(id)) {
        propertyElement.setValue(id, value);
        if (!propertyElement.isSet() && list.size() > 1) {
          list.remove(i);
          i--;
        }
      }
    }
    
    if (empty.isSet()) {
      empty = createEmpty();
      list.add(empty);
    }
  }
  
  public PropertyElement createAndAdd() {
    PropertyElement pe = createEmpty();
    list.add(list.size() - 1, pe);
    return pe;
  }
  
  @Override
  public void resetValue() {
    list.clear();
    empty = createEmpty();
    list.add(empty);
  }
  
  @Override
  public void resetValue(String id) {
    if (id.equals(getId())) {
      resetValue();
    }
    
    for (int i = 0; i < list.size(); i++) {
      PropertyElement propertyElement = list.get(i);
      if (propertyElement.hasId(id)) {
        propertyElement.resetValue(id);
        if (!propertyElement.isSet() && list.size() > 1) {
          list.remove(i);
          i--;
        }
      }
    }
    
    if (empty.isSet()) {
      empty = createEmpty();
      list.add(empty);
    }
  }
  
  @Override
  public boolean isSet() {
    for (PropertyElement propertyElement : list) {
      if (propertyElement.isSet()) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public boolean isSet(String id) {
    if (id.equals(getId())) {
      return isSet();
    }
    
    for (PropertyElement propertyElement : list) {
      if (propertyElement.hasId(id)) {
        return propertyElement.isSet(id);
      }
    }
    
    return false;
  }
  
  @Override
  public Object getValue() {
    List<Object> value = new ArrayList<Object>(list.size());
    for (PropertyElement propertyElement : list) {
      value.add(propertyElement.getValue());
    }
    return value;
  }
  
  @Override
  public Object getValue(String id) {
    if (id.equals(getId())) {
      return getValue();
    }
    
    for (PropertyElement propertyElement : list) {
      if (propertyElement.hasId(id)) {
        return propertyElement.getValue(id);
      }
    }
    
    return null;
  }

  @Override
  public Object getEditableValue() {
    return this;
  }
  
  @Override
  public Object getEditableValue(String id) {
    if (id.equals(getId())) {
      return getEditableValue();
    }
    
    for (PropertyElement propertyElement : list) {
      if (propertyElement.hasId(id)) {
        return propertyElement.getEditableValue(id);
      }
    }
    
    return null;
  }
  
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if (!isEditable() || !filter()) {
      return new IPropertyDescriptor[0];
    }
    return Utils.getPropertyDescriptors(list, category);
  }

  private PropertyElement createEmpty() {
    return PropertyElementCreationFactory.create(template);
  }
  
  public List<PropertyElement> getPropertyElements() {
    List<PropertyElement> list = new ArrayList<PropertyElement>(this.list);
    list.remove(list.size() - 1);
    return list;
  }
  
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    for(PropertyElement propertyElement: list) {
      if (propertyElement != empty) {
        sb.append(propertyElement.toString());
        sb.append(", ");
      }
    }
    if (sb.toString().endsWith(", ")) {
      sb.delete(sb.length() - 2, sb.length());
    }
    sb.append("]");
    return sb.toString();
  }

}
