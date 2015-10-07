package io.mashin.oep.model.property.filter;

import io.mashin.oep.model.property.PropertyElement;

@FunctionalInterface
public interface PropertyFilter {
  
  public boolean filter(PropertyElement propertyElement);
  
  default public PropertyFilter and(PropertyFilter other) {
    return p -> filter(p) && other.filter(p);
  }
  
  default public PropertyFilter or(PropertyFilter other) {
    return p -> filter(p) || other.filter(p);
  }
  
  default public PropertyFilter negate() {
    return p -> !filter(p);
  }
  
}
