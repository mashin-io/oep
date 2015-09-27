package io.mashin.oep.model.property.filter;

import io.mashin.oep.model.property.PropertyElement;

public class DefaultPropertyFilter implements PropertyFilter {

  @Override
  public boolean filter(PropertyElement t) {
    return true;
  }

}
