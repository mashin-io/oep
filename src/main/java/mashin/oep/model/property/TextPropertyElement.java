package mashin.oep.model.property;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class TextPropertyElement extends SingularPropertyElement {

  private IPropertyDescriptor propertyDescriptor;
  
  private String text;
  
  public TextPropertyElement(String id, String name) {
    super(id, name);
    text = "";
  }
  
  @Override
  public void setValue(Object value) {
    text = value.toString();
  }
  
  @Override
  public void resetValue() {
    text = "";
  }

  @Override
  public boolean isSet() {
    return !text.isEmpty();
  }

  @Override
  public Object getValue() {
    return text;
  }

  @Override
  public Object getEditableValue() {
    return this;
  }

  @Override
  public IPropertyDescriptor getPropertyDescriptor() {
    if (propertyDescriptor == null) {
      propertyDescriptor = new TextPropertyDescriptor(getId(), getName());
    }
    return propertyDescriptor;
  }
  
  public void setStringValue(String value) {
    text = value;
  }
  
  public String getStringValue() {
    return text;
  }

}
