package io.mashin.oep.figures;

import io.mashin.oep.model.node.Node;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

public class TerminalConnectionAnchor extends AbstractConnectionAnchor {

  public static final int TYPE_FANIN  = 1;
  public static final int TYPE_FANOUT = 2;
  public static final int TYPE_OUT    = 3;
  public static final int TYPE_NOIN   = 4;
  public static final int TYPE_NOOUT  = 5;
  
  private int type;
  private int verticalOffsetPercentage;
  private String label;
  
  public TerminalConnectionAnchor(IFigure owner, int type, String label) {
    super(owner);
    
    switch (type) {
    case TYPE_FANIN:
    case TYPE_FANOUT:
    case TYPE_OUT:
    case TYPE_NOIN:
    case TYPE_NOOUT:
      this.type = type;
      break;
    default:
      throw new IllegalArgumentException("Type " + type + " is not supported!");
    }
    
    this.label = label;
    this.verticalOffsetPercentage = getConnectionAnchorVerticalOffset();
  }
  
  private int getConnectionAnchorVerticalOffset() {
    switch (getLabel()) {
    case Node.TERMINAL_NOIN:
    case Node.TERMINAL_NOOUT:
      return 0;
    case Node.TERMINAL_FANIN:
    case Node.TERMINAL_CASE:
    case Node.TERMINAL_FANOUT:
    case Node.TERMINAL_OUT:
    case Node.TERMINAL_OK:
      return 50;
    case Node.TERMINAL_DEFAULT:
    case Node.TERMINAL_ERROR:
      return 90;
    default: return 50;
    }
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
    RoundedRectangle body = getCastedOwner().getBodyFigure();
    Rectangle r = body.getBounds();
    
    int x = (type == TYPE_FANIN || type == TYPE_NOIN) ? r.x - 5 : r.right() + 5;
    int y = (int) (r.y + verticalOffsetPercentage * r.height / 100.0);

    return new Point(x, y);
  }
  
  @Override
  public Point getReferencePoint() {
    return getLocation(null);
  }
  
  public boolean isSource() {
    switch (type) {
    case TYPE_NOOUT:
    case TYPE_FANOUT:
    case TYPE_OUT:
      return true;
    case TYPE_NOIN:
    case TYPE_FANIN:
      return false;
    }
    return false;
  }
  
}
