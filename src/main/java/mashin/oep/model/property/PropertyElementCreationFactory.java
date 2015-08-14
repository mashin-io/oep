package mashin.oep.model.property;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicLong;

import mashin.oep.model.node.action.basic.FSActionNode;

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
    
    } else if (template instanceof FSActionNode.FSOperationDelete) {
      return new FSActionNode.FSOperationDelete(template.getId() + "." + ID_SEQ.incrementAndGet(), template.getName());
    } else if (template instanceof FSActionNode.FSOperationMkdir) {
      return new FSActionNode.FSOperationMkdir(template.getId() + "." + ID_SEQ.incrementAndGet(), template.getName());
    } else if (template instanceof FSActionNode.FSOperationMove) {
      return new FSActionNode.FSOperationMove(template.getId() + "." + ID_SEQ.incrementAndGet(), template.getName());
    } else if (template instanceof FSActionNode.FSOperationChmod) {
      return new FSActionNode.FSOperationChmod(template.getId() + "." + ID_SEQ.incrementAndGet(), template.getName());
    } else if (template instanceof FSActionNode.FSOperationTouchz) {
      return new FSActionNode.FSOperationTouchz(template.getId() + "." + ID_SEQ.incrementAndGet(), template.getName());
    } else if (template instanceof FSActionNode.FSOperationChgrp) {
      return new FSActionNode.FSOperationChgrp(template.getId() + "." + ID_SEQ.incrementAndGet(), template.getName());
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
