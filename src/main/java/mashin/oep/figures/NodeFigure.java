package mashin.oep.figures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mashin.oep.Activator;
import mashin.oep.model.node.Node;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonShape;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class NodeFigure extends Figure {

  private static Map<String, Image> nodeImagesMap;
  private static Image nodeBgImage;
  
  static {
    nodeImagesMap = new HashMap<String, Image>();
    nodeImagesMap.put(Node.TYPE_START, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Start-24.png")));
    nodeImagesMap.put(Node.TYPE_END, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/End-24.png")));
    nodeImagesMap.put(Node.TYPE_KILL, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Kill-24.png")));
    nodeImagesMap.put(Node.TYPE_FORK, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Fork-24.png")));
    nodeImagesMap.put(Node.TYPE_JOIN, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Join-24.png")));
    nodeImagesMap.put(Node.TYPE_DECISION, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Decision-24.png")));
    nodeImagesMap.put(Node.TYPE_CUSTOM_ACTION, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/CustomAction-24.png")));
    nodeImagesMap.put(Node.TYPE_FS, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/FS-24.png")));
    nodeImagesMap.put(Node.TYPE_JAVA, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Java-24.png")));
    nodeImagesMap.put(Node.TYPE_MAPREDUCE, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/MapReduce-24.png")));
    nodeImagesMap.put(Node.TYPE_PIG, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Pig-24.png")));
    nodeImagesMap.put(Node.TYPE_SUBWORKFLOW, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/SubWorkflow-24.png")));
    nodeImagesMap.put(Node.TYPE_DISTCP, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Distcp-24.png")));
    nodeImagesMap.put(Node.TYPE_EMAIL, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Email-24.png")));
    nodeImagesMap.put(Node.TYPE_HIVE, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Hive-24.png")));
    nodeImagesMap.put(Node.TYPE_HIVE2, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Hive2-24.png")));
    nodeImagesMap.put(Node.TYPE_SHELL, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Shell-24.png")));
    nodeImagesMap.put(Node.TYPE_SPARK, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Spark-24.png")));
    nodeImagesMap.put(Node.TYPE_SQOOP, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Sqoop-24.png")));
    nodeImagesMap.put(Node.TYPE_SSH, new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/SSH-24.png")));
    nodeBgImage = new Image(Display.getCurrent(),
        Activator.class.getResourceAsStream("icons/Node.png"));
  }
  
  public static void disposeImages() {
    nodeImagesMap.values().forEach(Image::dispose);
    nodeBgImage.dispose();
  }
  
  public static final int WIDTH  = 100;
  public static final int HEIGHT = 60;
  
  private RoundedRectangle body;
  private ImageFigure bodyBg;
  private ImageFigure image;
  private Label label;
  private Label toolTipLabel;
  private List<TerminalConnectionAnchor> connectionAnchors;
  private String nodeType;
  
  public NodeFigure(String nodeType) {
    FreeformLayout layout = new FreeformLayout();
    setLayoutManager(layout);
    
    setSize(WIDTH, HEIGHT);
    
    body = new RoundedRectangle();
    body.setCornerDimensions(new Dimension(5, 5));
    body.setPreferredSize(WIDTH / 3, 2 * HEIGHT / 3);
    body.setSize(body.getPreferredSize());
    body.setLocation(new Point(WIDTH / 3, 0));
    body.setOpaque(true);
    body.setBackgroundColor(ColorConstants.white);
    add(body);
    
    bodyBg = new ImageFigure(nodeBgImage);
    bodyBg.setPreferredSize(WIDTH / 3, 2 * HEIGHT / 3);
    bodyBg.setSize(body.getPreferredSize());
    bodyBg.setLocation(new Point(WIDTH / 3, 0));
    add(bodyBg);
    
    image = new ImageFigure(nodeImagesMap.get(nodeType));
    image.setBounds(body.getBounds());
    add(image);
    
    label = new Label();
    label.setLabelAlignment(PositionConstants.CENTER);
    label.setTextAlignment(PositionConstants.CENTER);
    label.setMaximumSize(new Dimension(WIDTH, HEIGHT / 3));
    label.setSize(WIDTH, HEIGHT / 3);
    label.setLocation(new Point(0, 2 * HEIGHT / 3));
    add(label);
    
    toolTipLabel = new Label();
    toolTipLabel.setLabelAlignment(PositionConstants.CENTER);
    toolTipLabel.setTextAlignment(PositionConstants.CENTER);
    setToolTip(toolTipLabel);
    
    connectionAnchors = new ArrayList<TerminalConnectionAnchor>();
    
    this.nodeType = nodeType;
  }
  
  public Label getLabelFigure() {
    return label;
  }
  
  public RoundedRectangle getBodyFigure() {
    return body;
  }
  
  public void setLabel(String label) {
    this.label.setText(label);
    this.toolTipLabel.setText(nodeType + ": " + label);
  }
  
  public String getLabel() {
    return label.getText();
  }
  
  public void addConnectionAnchor(TerminalConnectionAnchor terminalConnectionAnchor) {
    connectionAnchors.add(terminalConnectionAnchor);
    Point p = terminalConnectionAnchor.getLocalLocation();
    IFigure anchor = null;
    
    int terminalConnectionAnchorType = terminalConnectionAnchor.getType(); 
    switch (terminalConnectionAnchorType) {
    case TerminalConnectionAnchor.TYPE_NOIN:
      RoundedRectangle noin = new RoundedRectangle();
      noin.setSize(5, body.getSize().height);
      noin.setBackgroundColor(ColorConstants.lightGreen);
      noin.setLocation(new Point(p.x, p.y));
      noin.setCornerDimensions(new Dimension(2, 2));
      anchor = noin;
      break;
    case TerminalConnectionAnchor.TYPE_NOOUT:
      RoundedRectangle noout = new RoundedRectangle();
      noout.setSize(5, body.getSize().height);
      noout.setBackgroundColor(ColorConstants.lightGray);
      noout.setLocation(new Point(p.x - 5, p.y));
      noout.setCornerDimensions(new Dimension(2, 2));
      anchor = noout;
      break;
    case TerminalConnectionAnchor.TYPE_FANIN:
      RectangleFigure fanin = new RectangleFigure();
      fanin.setSize(5, 5);
      fanin.setBackgroundColor(ColorConstants.lightGray);
      fanin.setToolTip(new Label(terminalConnectionAnchor.getLabel()));
      fanin.setLocation(new Point(p.x, p.y - 2));
      anchor = fanin;
      break;
    case TerminalConnectionAnchor.TYPE_FANOUT:
      RectangleFigure fanout = new RectangleFigure();
      fanout.setSize(5, 5);
      fanout.setBackgroundColor(ColorConstants.lightGray);
      fanout.setToolTip(new Label(terminalConnectionAnchor.getLabel()));
      fanout.setLocation(new Point(p.x - 5, p.y - 2));
      anchor = fanout;
      break;
    case TerminalConnectionAnchor.TYPE_OUT:
      PolygonShape out = new PolygonShape();
      out.setSize(5, 5);
      out.setPoints(new PointList(new int[] { 0, 0, 2, 0, 4, 2, 2, 4, 0, 4 }));
      out.setBackgroundColor(ColorConstants.lightGray);
      out.setToolTip(new Label(terminalConnectionAnchor.getLabel()));
      out.setLocation(new Point(p.x - 5, p.y - 2));
      anchor = out;
      break;
    }
    anchor.setOpaque(true);
    add(anchor);
  }
  
  public TerminalConnectionAnchor getConnectionAnchor(String label) {
    for (TerminalConnectionAnchor ca : connectionAnchors) {
      if (ca.getLabel().equals(label)) {
        return ca;
      }
    }
    return null;
  }
  
  public List<TerminalConnectionAnchor> getConnectionAnchors() {
    return new ArrayList<TerminalConnectionAnchor>(connectionAnchors);
  }
  
  public List<TerminalConnectionAnchor> getSourceConnectionAnchors() {
    return connectionAnchors.stream()
            .filter(ca -> ca.isSource())
            .collect(Collectors.toList());
  }
  
  public List<TerminalConnectionAnchor> getTargetConnectionAnchors() {
    return connectionAnchors.stream()
            .filter(ca -> !ca.isSource())
            .collect(Collectors.toList());
  }
  
  public TerminalConnectionAnchor getSourceConnectionAnchorAt(Point p) {
    return getConnectionAnchorAt(p, getSourceConnectionAnchors());
  }
  
  public TerminalConnectionAnchor getTargetConnectionAnchorAt(Point p) {
    return getConnectionAnchorAt(p, getTargetConnectionAnchors());
  }
  
  private TerminalConnectionAnchor getConnectionAnchorAt(Point p, List<TerminalConnectionAnchor> connectionAnchors) {
    TerminalConnectionAnchor closest = null;
    double min = Double.MAX_VALUE;

    for (TerminalConnectionAnchor connectionAnchor : connectionAnchors) {
      Point p2 = connectionAnchor.getLocation(null);
      double d = Math.abs(p.getDistance(p2));
      if (d < min) {
        min = d;
        closest = connectionAnchor;
      }
    }
    return closest;
  }
  
}
