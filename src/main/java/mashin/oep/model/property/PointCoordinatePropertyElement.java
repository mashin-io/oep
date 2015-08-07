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
    return coordinate;
  }

  @Override
  public Object getEditableValue() {
    return this;
  }

  @Override
  public IPropertyDescriptor getPropertyDescriptor() {
    if (null == propertyDescriptor) {
      propertyDescriptor = new TextPropertyDescriptor(getId(), getName());
      ((PropertyDescriptor) propertyDescriptor)
        .setValidator(new ICellEditorValidator() {
          @Override
          public String isValid(Object value) {
            int intValue = -1;
            try {
              intValue = Integer.parseInt((String) value);
            } catch (NumberFormatException exc) {
              return "Not a number";
            }
            return (intValue >= 0) ? null
                : "Value must be >=  0";
          }
        });
    }
    return propertyDescriptor;
  }
  
}
