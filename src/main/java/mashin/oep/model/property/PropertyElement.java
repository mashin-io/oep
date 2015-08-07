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
  public abstract void resetValue();
  public abstract boolean isSet();
  public abstract Object getValue();
  public abstract Object getEditableValue();
  public abstract IPropertyDescriptor getPropertyDescriptor();
  
  @Override
  public String toString() {
    return name + ": " + getValue();
  }
  
}
