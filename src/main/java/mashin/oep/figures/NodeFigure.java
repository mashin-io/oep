package mashin.oep.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;

public class NodeFigure extends Figure {

  private RectangleFigure body;
  private Label label;
  private Label toolTipLabel;
  
  public NodeFigure() {
    ToolbarLayout layout = new ToolbarLayout();
    layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
    layout.setSpacing(2);
    layout.setStretchMinorAxis(false);
    setLayoutManager(layout);
    
    body = new RectangleFigure();
    body.setPreferredSize(30, 40);
    body.setSize(body.getPreferredSize());
    body.setOpaque(true);
    body.setBackgroundColor(ColorConstants.lightGray);
    add(body);
    
    label = new Label();
    label.setLabelAlignment(PositionConstants.CENTER);
    label.setTextAlignment(PositionConstants.CENTER);
    label.setMaximumSize(new Dimension(90, 28));
    add(label);
    
    toolTipLabel = new Label();
    toolTipLabel.setLabelAlignment(PositionConstants.CENTER);
    toolTipLabel.setTextAlignment(PositionConstants.CENTER);
    setToolTip(toolTipLabel);
    
    setSize(90, 60);
  }
  
  public Label getLabelFigure() {
    return label;
  }
  
  public void setLabel(String label) {
    this.label.setText(label);
    this.toolTipLabel.setText(label);
  }
  
  public String getLabel() {
    return label.getText();
  }
  
}
