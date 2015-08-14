package mashin.oep.model.property;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicLong;

public class PropertyElementCreationFactory {

  private static volatile AtomicLong ID_SEQ = new AtomicLong(0);
  
  public static PropertyElement create(PropertyElement template) {
    
    if (template instanceof ComboBoxPropertyElement) {
      
      ComboBoxPropertyElement comboBoxPropertyElement = new ComboBoxPropertyElement(
          template.getId() + "." + ID_SEQ.incrementAndGet(), template.getName());
      
      ComboBoxPropertyElement templateComboBox = (ComboBoxPropertyElement) template;
      comboBoxPropertyElement.setLabelsArray(templateComboBox.getLabelsArray());
      comboBoxPropertyElement.setValuesArray(templateComboBox.getValuesArray());
      
      return comboBoxPropertyElement;
      
    } else {
      
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
  
}
