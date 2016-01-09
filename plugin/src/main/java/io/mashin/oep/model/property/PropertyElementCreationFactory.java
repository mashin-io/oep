/**
 * Copyright (c) 2015 Mashin (http://mashin.io). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mashin.oep.model.property;

import io.mashin.oep.model.node.action.basic.FSActionNode;
import io.mashin.oep.model.property.filter.PropertyFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicLong;

public class PropertyElementCreationFactory {

  private static volatile AtomicLong ID_SEQ = new AtomicLong(0);
  
  public static PropertyElement create(PropertyElement template) {
    
    if (template instanceof ComboBoxPropertyElement) {
      
      ComboBoxPropertyElement comboBoxPropertyElement = new ComboBoxPropertyElement(
          template.getId() + "." + ID_SEQ.incrementAndGet(), template.getName(), template.getPropertyFilter());
      
      ComboBoxPropertyElement templateComboBox = (ComboBoxPropertyElement) template;
      comboBoxPropertyElement.setLabelsArray(templateComboBox.getLabelsArray());
      comboBoxPropertyElement.setValuesArray(templateComboBox.getValuesArray());
      
      return comboBoxPropertyElement;
    
    } else if (template instanceof FSActionNode.FSOperationDelete) {
      return new FSActionNode.FSOperationDelete(template.getId() + "." + ID_SEQ.incrementAndGet(),
          template.getName(), template.getPropertyFilter());
    } else if (template instanceof FSActionNode.FSOperationMkdir) {
      return new FSActionNode.FSOperationMkdir(template.getId() + "." + ID_SEQ.incrementAndGet(),
          template.getName(), template.getPropertyFilter());
    } else if (template instanceof FSActionNode.FSOperationMove) {
      return new FSActionNode.FSOperationMove(template.getId() + "." + ID_SEQ.incrementAndGet(),
          template.getName(), template.getPropertyFilter());
    } else if (template instanceof FSActionNode.FSOperationChmod) {
      return new FSActionNode.FSOperationChmod(template.getId() + "." + ID_SEQ.incrementAndGet(),
          template.getName(), template.getPropertyFilter(),
          ((FSActionNode.FSOperationChmod) template).getModelElementWithSchema());
    } else if (template instanceof FSActionNode.FSOperationTouchz) {
      return new FSActionNode.FSOperationTouchz(template.getId() + "." + ID_SEQ.incrementAndGet(),
          template.getName(), template.getPropertyFilter());
    } else if (template instanceof FSActionNode.FSOperationChgrp) {
      return new FSActionNode.FSOperationChgrp(template.getId() + "." + ID_SEQ.incrementAndGet(),
          template.getName(), template.getPropertyFilter());
    } else {
      
      try {
        return template
                .getClass()
                .getConstructor(String.class, String.class, PropertyFilter.class)
                .newInstance(template.getId() + "." + ID_SEQ.incrementAndGet(),
                    template.getName(), template.getPropertyFilter());
      } catch (InstantiationException | IllegalAccessException
          | IllegalArgumentException | InvocationTargetException
          | NoSuchMethodException | SecurityException e) {
        e.printStackTrace();
        return null;
      }
      
    }
    
  }
  
}
