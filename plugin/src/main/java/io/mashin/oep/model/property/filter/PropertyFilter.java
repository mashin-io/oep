package io.mashin.oep.model.property.filter;

import io.mashin.oep.model.property.PropertyElement;

@FunctionalInterface
public interface PropertyFilter {
  
  public boolean filter(PropertyElement propertyElement);
  
}
