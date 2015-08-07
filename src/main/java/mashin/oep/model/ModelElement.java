package mashin.oep.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class ModelElement implements IPropertySource {
  transient protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
  
  protected static final IPropertyDescriptor[] EMPTY_IPROPERTY_DESCRIPTOR_ARRAY = new IPropertyDescriptor[0];
  
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
    return EMPTY_IPROPERTY_DESCRIPTOR_ARRAY;
  }

  @Override
  public Object getPropertyValue(Object propertyName) {
    return null;
  }

  @Override
  public boolean isPropertySet(Object propertyName) {
    return true;
  }

  @Override
  public void resetPropertyValue(Object propertyName) {}

  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {}
}
