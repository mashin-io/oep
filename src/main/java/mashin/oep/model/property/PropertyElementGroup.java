package mashin.oep.model.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public abstract class PropertyElementGroup extends PropertyElement {

  protected List<PropertyElement> propertyElements;
  private IPropertySource value;

  public PropertyElementGroup(String id, String name) {
    super(id, name);
    propertyElements = new ArrayList<PropertyElement>();
  }

  @Override
  public boolean hasId(String id) {
    if(this.id.equals(id)) {
      return true;
    } else {
      for (PropertyElement propertyElement: propertyElements) {
        if(propertyElement.hasId(id)) {
          return true;
        }
      }
    }
    return false;
  }
  
  @Override
  public IPropertyDescriptor getPropertyDescriptor() {
    PropertyDescriptor groupPropertyDescriptor = new PropertyDescriptor(
        getId(), getName());
    groupPropertyDescriptor.setLabelProvider(new LabelProvider() {
      
    });
    return groupPropertyDescriptor;
  }

  @Override
  public Object getValue() {
    if (value == null) {
      value = new IPropertySource() {

        @Override
        public void setPropertyValue(Object propertyName, Object propertyValue) {
          String propertyNameStr = (String) propertyName;
          for (PropertyElement propertyElement : propertyElements) {
            if (propertyElement.getId().equals(propertyNameStr)) {
              propertyElement.setValue(propertyValue);
            }
          }
        }

        @Override
        public void resetPropertyValue(Object propertyName) {
          String propertyNameStr = (String) propertyName;
          for (PropertyElement propertyElement : propertyElements) {
            if (propertyElement.getId().equals(propertyNameStr)) {
              propertyElement.resetValue();
            }
          }
        }

        @Override
        public boolean isPropertySet(Object propertyName) {
          String propertyNameStr = (String) propertyName;
          for (PropertyElement propertyElement : propertyElements) {
            if (propertyElement.getId().equals(propertyNameStr)) {
              return propertyElement.isSet();
            }
          }
          return false;
        }

        @Override
        public Object getPropertyValue(Object propertyName) {
          String propertyNameStr = (String) propertyName;
          for (PropertyElement propertyElement : propertyElements) {
            if (propertyElement.getId().equals(propertyNameStr)) {
              return propertyElement.getValue();
            }
          }
          return null;
        }

        @Override
        public IPropertyDescriptor[] getPropertyDescriptors() {
          IPropertyDescriptor[] ds = new IPropertyDescriptor[propertyElements
              .size()];
          int i = 0;
          for (PropertyElement propertyElement : propertyElements) {
            ds[i++] = propertyElement.getPropertyDescriptor();
          }
          return ds;
        }

        @Override
        public Object getEditableValue() {
          return PropertyElementGroup.this;
        }
      };
    }
    return value;
  }

  @Override
  public boolean isSet() {
    for (PropertyElement propertyElement : propertyElements) {
      if (propertyElement.isSet()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void setValue(Object value) {
    PropertyElementGroup peg = null;
    if (value instanceof IPropertySource) {
      peg = (PropertyElementGroup) ((IPropertySource) value).getEditableValue();
    } else {
      peg = (PropertyElementGroup) value;
    }
    for (int i = 0; i < propertyElements.size(); i++) {
      propertyElements.get(i).setValue(peg.propertyElements.get(i).getValue());
    }
  }

  @Override
  public void resetValue() {
    for (PropertyElement propertyElement : propertyElements) {
      propertyElement.resetValue();
    }
  }

  @Override
  public Object getEditableValue() {
    return this;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("{");
    for(PropertyElement propertyElement: propertyElements) {
      sb.append(propertyElement.toString());
      sb.append(", ");
    }
    sb.delete(sb.length() - 2, sb.length());
    sb.append("}");
    return sb.toString();
  }
  
}
