package mashin.oep.figures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class NodeFigure extends Figure {

  private RectangleFigure body;
  private Label label;
  private Label toolTipLabel;
  private List<TerminalConnectionAnchor> connectionAnchors;
  
  public NodeFigure() {
    FreeformLayout layout = new FreeformLayout();
    //ToolbarLayout layout = new ToolbarLayout();
    //layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
    //layout.setSpacing(2);
    //layout.setStretchMinorAxis(false);
    setLayoutManager(layout);
    
    setSize(90, 60);
    
    body = new RectangleFigure();
    body.setPreferredSize(30, 40);
    body.setSize(body.getPreferredSize());
    body.setLocation(new Point(30, 0));
    body.setOpaque(true);
    body.setBackgroundColor(ColorConstants.lightGray);
    add(body);
    
    label = new Label();
    label.setLabelAlignment(PositionConstants.CENTER);
    label.setTextAlignment(PositionConstants.CENTER);
    label.setMaximumSize(new Dimension(90, 20));
    label.setSize(90, 20);
    label.setLocation(new Point(0, 40));
    add(label);
    
    toolTipLabel = new Label();
    toolTipLabel.setLabelAlignment(PositionConstants.CENTER);
    toolTipLabel.setTextAlignment(PositionConstants.CENTER);
    setToolTip(toolTipLabel);
    
    connectionAnchors = new ArrayList<TerminalConnectionAnchor>();
  }
  
  public Label getLabelFigure() {
    return label;
  }
  
  public RectangleFigure getBodyFigure() {
    return body;
  }
  
  public void setLabel(String label) {
    this.label.setText(label);
    this.toolTipLabel.setText(label);
  }
  
  public String getLabel() {
    return label.getText();
  }
  
  public void addConnectionAnchor(TerminalConnectionAnchor terminalConnectionAnchor) {
    connectionAnchors.add(terminalConnectionAnchor);
    Point p = terminalConnectionAnchor.getLocalLocation();
    RectangleFigure r = new RectangleFigure();
    r.setSize(5, 5);
    r.setOpaque(true);
    r.setBackgroundColor(ColorConstants.gray);
    switch (terminalConnectionAnchor.getType()) {
    case TerminalConnectionAnchor.TYPE_FANIN:
      r.setLocation(new Point(p.x - 5, p.y - 2));
      break;
    case TerminalConnectionAnchor.TYPE_FANOUT:
    case TerminalConnectionAnchor.TYPE_OUT:
      r.setLocation(new Point(p.x, p.y - 2));
      break;
    }
    r.setToolTip(new Label(terminalConnectionAnchor.getLabel()));
    add(r);
  }
  
  public List<TerminalConnectionAnchor> getConnectionAnchors() {
    return new ArrayList<TerminalConnectionAnchor>(connectionAnchors);
  }
  
}
