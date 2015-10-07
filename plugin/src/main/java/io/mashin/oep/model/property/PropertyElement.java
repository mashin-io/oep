package io.mashin.oep.model.property;

import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class PropertyElement /*extends HPDLModel*/ {

  // view data
  protected String name;
  protected String id;
  protected boolean isEditable;
  protected String category;
  
  protected PropertyFilter filter;
  
  public PropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public PropertyElement(String id, String name, PropertyFilter filter) {
    this.id = id;
    this.name = name;
    this.isEditable = true;
    this.filter = filter;
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setEditable(boolean isEditable) {
    this.isEditable = isEditable;
  }
  
  public boolean isEditable() {
    return isEditable;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getCategory() {
    return category;
  }
  
  public boolean hasId(String id) {
    return this.id.equals(id);
  }
  
  public boolean filter() {
    return filter.filter(this);
  }
  
  protected PropertyFilter getPropertyFilter() {
    return filter;
  }
  
  public abstract void setValue(Object value);
  public abstract void setValue(String id, Object value);
  public abstract void resetValue();
  public abstract void resetValue(String id);
  public abstract boolean isSet();
  public abstract boolean isSet(String id);
  public abstract Object getValue();
  public abstract Object getValue(String id);
  public abstract Object getEditableValue();
  public abstract Object getEditableValue(String id);
  public abstract IPropertyDescriptor[] getPropertyDescriptors();
  
  @Override
  public String toString() {
    return name + ": " + getValue();
  }
  
}
