package mashin.oep;

import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;

public class WorkflowEditorPalleteFactory {

  private static PaletteContainer createToolsGroup(PaletteRoot palette) {
    PaletteToolbar toolbar = new PaletteToolbar("Tools");

    // Add a selection tool to the group
    ToolEntry tool = new PanningSelectionToolEntry();
    toolbar.add(tool);
    palette.setDefaultEntry(tool);

    // Add a marquee tool to the group
    toolbar.add(new MarqueeToolEntry());

    // Add (solid-line) connection tool
    tool = new ConnectionCreationToolEntry("Connection",
        "Create a connection", null,
        ImageDescriptor.createFromFile(Activator.class, "icons/connection16.gif"),
        ImageDescriptor.createFromFile(Activator.class, "icons/connection24.gif"));
    toolbar.add(tool);

    return toolbar;
  }
  
  /**
   * Creates the PaletteRoot and adds all Palette elements.
   * 
   * @return the root
   */
  public static PaletteRoot createPalette() {
    PaletteRoot workflowPalette = new PaletteRoot();
    workflowPalette.add(createToolsGroup(workflowPalette));
    return workflowPalette;
  }
  
}
