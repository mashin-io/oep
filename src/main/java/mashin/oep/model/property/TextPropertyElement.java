package mashin.oep.model.property;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class TextPropertyElement extends PropertyElement {

  private IPropertyDescriptor propertyDescriptor;
  
  private String text = "";
  
  public TextPropertyElement(String id, String name) {
    super(id, name);
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
    if(null == propertyDescriptor) {
      propertyDescriptor = new TextPropertyDescriptor(getId(), getName());
    }
    return propertyDescriptor;
  }

}
