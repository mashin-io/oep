package mashin.oep.model.property;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class PointCoordinatePropertyElement extends SingularPropertyElement {

  private IPropertyDescriptor propertyDescriptor;
  
  private Integer coordinate;
  
  public PointCoordinatePropertyElement(String id, String name) {
    super(id, name);
    coordinate = 0;
  }

  @Override
  public void setValue(Object value) {
    coordinate = Integer.parseInt(value.toString());
  }

  @Override
  public void resetValue() {
    //coordinate = 0;
  }

  @Override
  public boolean isSet() {
    return true;
  }

  @Override
  public Object getValue() {
    return coordinate + "";
  }
  
  public void setFromInteger(Integer value) {
    coordinate = new Integer(value);
  }
  
  public Integer getAsInteger() {
    return new Integer(coordinate);
  }
  
  @Override
  public Object getEditableValue() {
    return this;
  }

  @Override
  public IPropertyDescriptor getPropertyDescriptor() {
    if (!isEditable()) {
      return null;
    }
    if (propertyDescriptor == null) {
      propertyDescriptor = new TextPropertyDescriptor(getId(), getName());
      ((PropertyDescriptor) propertyDescriptor)
        .setValidator(new ICellEditorValidator() {
          @Override
          public String isValid(Object value) {
            try {
              Integer.parseInt((String) value);
            } catch (NumberFormatException exc) {
              return "Not a number";
            }
            return null;
          }
        });
    }
    return propertyDescriptor;
  }
  
}
