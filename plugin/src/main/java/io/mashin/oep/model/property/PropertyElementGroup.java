package io.mashin.oep.model.property;

import io.mashin.oep.Utils;
import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public abstract class PropertyElementGroup extends PropertyElement {

  protected List<PropertyElement> propertyElements;
  private IPropertySource value;

  public PropertyElementGroup(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public PropertyElementGroup(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    propertyElements = new ArrayList<PropertyElement>();
  }

  @Override
  public boolean hasId(String id) {
    if (this.id.equals(id)) {
      return true;
    } else {
      for (PropertyElement propertyElement: propertyElements) {
        if(propertyElement.hasId(id)) {
          return true;
        }
      }
    }
    return false;
  }
  
  @Override
  public void setValue(Object value) {
    PropertyElementGroup peg = null;
    if (value instanceof IPropertySource) {
      peg = (PropertyElementGroup) ((IPropertySource) value).getEditableValue();
    } else if (value instanceof PropertyElementGroup){
      peg = (PropertyElementGroup) value;
    } else {
      setComplexValue(value);
      return;
    }
    for (int i = 0; i < propertyElements.size(); i++) {
      propertyElements.get(i).setValue(peg.propertyElements.get(i).getValue());
    }
  }
  
  public abstract void setComplexValue(Object value);
  
  @Override
  public void setValue(String id, Object value) {
    if (id.equals(getId())) {
      setValue(value);
    } else {
      for (PropertyElement propertyElement : propertyElements) {
        if (propertyElement.hasId(id)) {
          propertyElement.setValue(id, value);
        }
      }
    }
  }

  @Override
  public void resetValue() {
    for (PropertyElement propertyElement : propertyElements) {
      propertyElement.resetValue();
    }
  }
  
  @Override
  public void resetValue(String id) {
    if (id.equals(getId())) {
      resetValue();
    } else {
      for (PropertyElement propertyElement : propertyElements) {
        propertyElement.resetValue(id);
      }
    }
  }
  
  @Override
  public boolean isSet() {
    for (PropertyElement propertyElement : propertyElements) {
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
    
    for (PropertyElement propertyElement : propertyElements) {
      if (propertyElement.isSet(id)) {
        return true;
      }
    }
    
    return false;
  }
  
  @Override
  public Object getValue() {
    if (value == null) {
      value = new IPropertySource() {

        @Override
        public void setPropertyValue(Object propertyName, Object propertyValue) {
          PropertyElementGroup.this.setValue((String) propertyName, propertyValue);
        }

        @Override
        public void resetPropertyValue(Object propertyName) {
          PropertyElementGroup.this.resetValue((String) propertyName);
        }

        @Override
        public boolean isPropertySet(Object propertyName) {
          return PropertyElementGroup.this.isSet((String) propertyName);
        }

        @Override
        public Object getPropertyValue(Object propertyName) {
          return PropertyElementGroup.this.getValue((String) propertyName);
        }

        @Override
        public IPropertyDescriptor[] getPropertyDescriptors() {
          return Utils.getPropertyDescriptors(propertyElements);
        }

        @Override
        public Object getEditableValue() {
          return PropertyElementGroup.this.getEditableValue();
        }
      };
    }
    return value;
  }
  
  @Override
  public Object getValue(String id) {
    if (id.equals(getId())) { 
      return getValue();
    }
    
    for (PropertyElement propertyElement : propertyElements) {
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
    
    for (PropertyElement propertyElement : propertyElements) {
      if (propertyElement.hasId(id)) {
        return propertyElement.getEditableValue(id);
      }
    }
    
    return null;
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if (!isEditable() || !filter()) {
      return null;
    }
    PropertyDescriptor groupPropertyDescriptor = new PropertyDescriptor(
        getId(), getName());
    groupPropertyDescriptor.setLabelProvider(new LabelProvider() {
      @Override
      public String getText(Object element) {
        return element.toString();
      }
    });
    if (category != null && !category.isEmpty()) {
      groupPropertyDescriptor.setCategory(category);
    }
    return new IPropertyDescriptor[] { groupPropertyDescriptor };
  }
  
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("{");
    for(PropertyElement propertyElement: propertyElements) {
      if (propertyElement.filter()) {
        sb.append(propertyElement.toString());
        sb.append(", ");
      }
    }
    sb.delete(sb.length() - 2, sb.length());
    sb.append("}");
    return sb.toString();
  }
  
}
