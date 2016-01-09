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

import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;

import org.eclipse.draw2d.geometry.Point;


public class PointPropertyElement extends PropertyElementGroup {

  public PointCoordinatePropertyElement x;
  public PointCoordinatePropertyElement y;
  
  public PointPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public PointPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    x = new PointCoordinatePropertyElement(id + ".x", "X");
    y = new PointCoordinatePropertyElement(id + ".y", "Y");
    propertyElements.add(x);
    propertyElements.add(y);
  }
  
  @Override
  public void setComplexValue(Object value) {
    if(value instanceof Point) {
      setFromPoint((Point) value);
    }
  }
  
  public Point getAsPoint() {
    return new Point(x.getAsInteger(), y.getAsInteger());
  }
  
  public void setFromPoint(Point point) {
    x.setFromInteger(point.x);
    y.setFromInteger(point.y);
  }
  
}
