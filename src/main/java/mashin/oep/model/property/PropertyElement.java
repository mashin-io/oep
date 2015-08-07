package mashin.oep.model.property;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class PropertyElement {

  protected String name;
  protected String id;
  
  public PropertyElement(String id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public boolean hasId(String id) {
    return this.id.equals(id);
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
    return name + ": " + getValue(getId());
  }
  
}
