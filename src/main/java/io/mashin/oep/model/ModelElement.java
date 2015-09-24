package io.mashin.oep.model;

import io.mashin.oep.Utils;
import io.mashin.oep.model.property.PropertyElement;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class ModelElement implements IPropertySource {
  
  transient protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
  
  protected static final IPropertyDescriptor[] EMPTY_IPROPERTY_DESCRIPTOR_ARRAY = new IPropertyDescriptor[0];
  protected IPropertyDescriptor[] descriptors;
  
  protected List<PropertyElement> propertyElements;
  
  public ModelElement() {
    propertyElements = new ArrayList<PropertyElement>();
  }
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
    listeners.addPropertyChangeListener(l);
  }

  protected void firePropertyChange(String prop, Object old, Object newValue) {
    listeners.firePropertyChange(prop, old, newValue);
  }

  protected void fireStructureChange(String prop, Object child) {
    listeners.firePropertyChange(prop, null, child);
  }
  
  public void removePropertyChangeListener(PropertyChangeListener l) {
    listeners.removePropertyChangeListener(l);
  }

  @Override
  public Object getEditableValue() {
    return this;
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    //if(descriptors == null) {
      List<IPropertyDescriptor[]> descriptorsList = new ArrayList<IPropertyDescriptor[]>(propertyElements.size());
      for (PropertyElement propertyElement : propertyElements) {
        descriptorsList.add(propertyElement.getPropertyDescriptors());
      }
      descriptors = Utils.combine(descriptorsList);
    //}
    return descriptors;
  }

  @Override
  public Object getPropertyValue(Object propertyName) {
    String propertyNameStr = (String) propertyName;
    for (PropertyElement propertyElement : propertyElements) {
      if (propertyElement.hasId(propertyNameStr)) {
        return propertyElement.getValue(propertyNameStr);
      }
    }
    return null;
  }

  @Override
  public boolean isPropertySet(Object propertyName) {
    String propertyNameStr = (String) propertyName;
    for (PropertyElement propertyElement : propertyElements) {
      if (propertyElement.hasId(propertyNameStr)) {
        return propertyElement.isSet(propertyNameStr);
      }
    }
    return true;
  }

  @Override
  public void resetPropertyValue(Object propertyName) {
    String propertyNameStr = (String) propertyName;
    for (PropertyElement propertyElement : propertyElements) {
      if (propertyElement.hasId(propertyNameStr)) {
        propertyElement.resetValue(propertyNameStr);
        return;
      }
    }
  }

  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    String propertyNameStr = (String) propertyName;
    for (PropertyElement propertyElement : propertyElements) {
      if (propertyElement.hasId(propertyNameStr)) {
        Object oldValue = propertyElement.getValue(propertyNameStr);
        propertyElement.setValue(propertyNameStr, propertyValue);
        firePropertyChange(propertyNameStr, oldValue, propertyValue);
        return;
      }
    }
  }
  
  protected final void addPropertyElement(PropertyElement propertyElement) {
    propertyElements.add(propertyElement);
  }
  
}
