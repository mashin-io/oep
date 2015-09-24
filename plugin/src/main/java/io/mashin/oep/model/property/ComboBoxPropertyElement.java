package io.mashin.oep.model.property;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class ComboBoxPropertyElement extends SingularPropertyElement {

  private IPropertyDescriptor propertyDescriptor;
  
  private String[] labelsArray;
  private Object[] valuesArray;
  
  private int valueIndex;
  
  public ComboBoxPropertyElement(String id, String name) {
    super(id, name);
    valueIndex = 0;
  }
  
  public void setLabelsArray(String[] labelsArray) {
    this.labelsArray = labelsArray;
  }
  
  public String[] getLabelsArray() {
    return labelsArray;
  }
  
  public void setValuesArray(Object[] valuesArray) {
    this.valuesArray = valuesArray;
  }
  
  public Object[] getValuesArray() {
    return valuesArray;
  }

  @Override
  public IPropertyDescriptor getPropertyDescriptor() {
    if (!isEditable()) {
      return null;
    }
    if (propertyDescriptor == null) {
      propertyDescriptor = new ComboBoxPropertyDescriptor(
          getId(), getName(),
          getLabelsArray());
    }
    return propertyDescriptor;
  }

  @Override
  public void setValue(Object value) {
    valueIndex = (Integer) value;
  }

  @Override
  public void resetValue() {
    valueIndex = 0;
  }

  @Override
  public boolean isSet() {
    return true;
  }

  @Override
  public Object getValue() {
    return valueIndex;
  }

  @Override
  public Object getEditableValue() {
    return this;
  }
  
  public Object getContentValue() {
    return valuesArray[valueIndex];
  }
  
  public void setContentValue(Object contentValue) {
    for (int i = 0; i < valuesArray.length; i++) {
      if (valuesArray[i].equals(contentValue)) {
        setValue(i);
        return;
      }
    }
  }

}
