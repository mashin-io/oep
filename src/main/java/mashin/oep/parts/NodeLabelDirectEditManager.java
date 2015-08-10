package mashin.oep.parts;

import org.eclipse.draw2d.Label;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

public class NodeLabelDirectEditManager extends DirectEditManager {

  private Label label;

  public NodeLabelDirectEditManager(GraphicalEditPart source,
      Class<TextCellEditor> editorType, CellEditorLocator locator, Label label) {
    super(source, editorType, locator);
    this.label = label;
  }

  @Override
  protected void initCellEditor() {
    String initialLabelText = label.getText();
    getCellEditor().setValue(initialLabelText);
  }

}
