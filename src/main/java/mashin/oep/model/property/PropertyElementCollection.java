package mashin.oep.model.property;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class PropertyElementCollection {

  private static AtomicLong ID_SEQ = new AtomicLong(0);

  private List<PropertyElement> list;
  private PropertyElement template;
  private PropertyElement empty;
  private String category;

  public PropertyElementCollection(String category, PropertyElement template) {
    this.category = category;
    list = new ArrayList<PropertyElement>();
    this.template = template;
    empty = createEmpty();
    list.add(empty);
  }

  public boolean hasProperty(String propertyName) {
    for (PropertyElement propertyElement : list) {
      if (propertyElement.hasId(propertyName)) {
        return true;
      }
    }
    return false;
  }

  public void setPropertyValue(String propertyName, Object propertyValue) {
    for(int i = 0; i < list.size(); i++) {
      PropertyElement propertyElement = list.get(i);
      if (propertyElement.hasId(propertyName)) {
        propertyElement.setValue(propertyValue);
        if (!propertyElement.isSet() && list.size() > 1) {
          list.remove(i);
          i--;
        }
      }
    }
    if (empty.isSet()) {
      empty = createEmpty();
      list.add(empty);
    }
  }

  public Object getPropertyValue(String propertyName) {
    for (PropertyElement propertyElement : list) {
      if (propertyElement.hasId(propertyName)) {
        return propertyElement.getValue();
      }
    }
    return null;
  }

  public IPropertyDescriptor[] getPropertyDescriptors() {
    int i = 0;
    IPropertyDescriptor[] descriptors = new IPropertyDescriptor[list.size()];
    for (PropertyElement propertyElement : list) {
      descriptors[i] = propertyElement.getPropertyDescriptor();
      ((PropertyDescriptor) descriptors[i]).setCategory(this.category);
      i++;
    }
    return descriptors;
  }

  private PropertyElement createEmpty() {
    try {
      return template
          .getClass()
          .getConstructor(String.class, String.class)
          .newInstance(template.getId() + "." + ID_SEQ.incrementAndGet(),
              template.getName());
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      return null;
    }
  }

}
