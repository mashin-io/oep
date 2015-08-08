package mashin.oep.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class ModelElement implements IPropertySource {
  
  transient protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
  
  protected static final IPropertyDescriptor[] EMPTY_IPROPERTY_DESCRIPTOR_ARRAY = new IPropertyDescriptor[0];
  //protected IPropertyDescriptor[] EMPTY_IPROPERTY_DESCRIPTOR_ARRAY;
  
  //public static final String PROP_DEBUG = "DEBUG";
  //protected TextPropertyElement DEBUG;
  
  public ModelElement() {
    //DEBUG = new TextPropertyElement(PROP_DEBUG, "DEBUG");
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
    //if(null == EMPTY_IPROPERTY_DESCRIPTOR_ARRAY) {
    //  EMPTY_IPROPERTY_DESCRIPTOR_ARRAY = new IPropertyDescriptor[] {
    //      DEBUG.getPropertyDescriptor()
    //  };
    //}
    return EMPTY_IPROPERTY_DESCRIPTOR_ARRAY;
  }

  @Override
  public Object getPropertyValue(Object propertyName) {
    //switch((String) propertyName) {
    //case PROP_DEBUG:
    //  return DEBUG.getValue();
    //}
    return null;
  }

  @Override
  public boolean isPropertySet(Object propertyName) {
    //switch((String) propertyName) {
    //case PROP_DEBUG:
    //  return DEBUG.isSet();
    //}
    return true;
  }

  @Override
  public void resetPropertyValue(Object propertyName) {
    //switch((String) propertyName) {
    //case PROP_DEBUG:
    //  DEBUG.resetValue();
    //  break;
    //}
  }

  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    //switch((String) propertyName) {
    //case PROP_DEBUG:
    //  String oldValue = (String) DEBUG.getValue();
    //  DEBUG.setValue(propertyValue);
    //  firePropertyChange(PROP_DEBUG, oldValue, (String) DEBUG.getValue());
    //  break;
    //}
  }
  
  //protected void debug(String msg) {
  //  setPropertyValue(PROP_DEBUG, msg);
  //}
  
}
