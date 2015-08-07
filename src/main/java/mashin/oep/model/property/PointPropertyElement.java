package mashin.oep.model.property;

import org.eclipse.draw2d.geometry.Point;


public class PointPropertyElement extends PropertyElementGroup {

  private PointCoordinatePropertyElement x;
  private PointCoordinatePropertyElement y;
  
  public PointPropertyElement(String id, String name) {
    super(id, name);
    x = new PointCoordinatePropertyElement(id + ".x", "X");
    y = new PointCoordinatePropertyElement(id + ".y", "Y");
    propertyElements.add(x);
    propertyElements.add(y);
  }
  
  public Point getAsPoint() {
    return new Point((Integer) x.getValue(), (Integer) y.getValue());
  }
  
  public void setFromPoint(Point point) {
    x.setValue(point.x);
    y.setValue(point.y);
  }
  
}
