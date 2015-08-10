package mashin.oep.figures;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

public class TerminalConnectionAnchor extends AbstractConnectionAnchor {

  public static final int TYPE_FANIN  = 1;
  public static final int TYPE_FANOUT = 2;
  public static final int TYPE_OUT    = 3;
  
  private int type;
  private int verticalOffsetPercentage;
  private String label;
  
  public TerminalConnectionAnchor(IFigure owner, int type, int verticalOffsetPercentage, String label) {
    super(owner);
    
    switch (type) {
    case TYPE_FANIN:
    case TYPE_FANOUT:
    case TYPE_OUT:
      this.type = type;
      break;
    default:
      throw new IllegalArgumentException("Type " + type + " is not supported!");
    }
    
    if (verticalOffsetPercentage >= 0 && verticalOffsetPercentage <= 100) {
      this.verticalOffsetPercentage = verticalOffsetPercentage;
    } else {
      throw new IllegalArgumentException(
          "Vertical Offset Percentage should be an integer between 0 and 100 inclusive!");
    }
    
    this.label = label;
  }
  
  public NodeFigure getCastedOwner() {
    return (NodeFigure) getOwner();
  }
  
  public int getType() {
    return this.type;
  }
  
  public int getVerticalOffsetPercentage() {
    return this.verticalOffsetPercentage;
  }
  
  public String getLabel() {
    return label;
  }
  
  @Override
  public Point getLocation(Point reference) {
    Point p = new PrecisionPoint(getLocalLocation());
    getCastedOwner().getBodyFigure().translateToAbsolute(p);
    return p;
  }
  
  public Point getLocalLocation() {
    RectangleFigure body = getCastedOwner().getBodyFigure();
    Rectangle r = body.getBounds();
    
    int x = type == TYPE_FANIN ? r.x : r.right();
    int y = (int) (r.y + verticalOffsetPercentage * r.height / 100.0);

    return new Point(x, y);
  }

}
