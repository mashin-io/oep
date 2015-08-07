package mashin.oep;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;

public class WorkflowEditorPalleteFactory {

  private static List<Object> createCategories(PaletteRoot root) {
    List<Object> categories = new ArrayList<Object>();
    categories.add(createControlGroup(root));
    return categories;
  }

  private static PaletteContainer createControlGroup(PaletteRoot root) {
    PaletteGroup controlGroup = new PaletteGroup("Control Group");

    List<Object> entries = new ArrayList<Object>();

    ToolEntry tool = new SelectionToolEntry();
    entries.add(tool);
    root.setDefaultEntry(tool);

    tool = new MarqueeToolEntry();
    entries.add(tool);

    PaletteSeparator sep = new PaletteSeparator("mashin.oep.sep2");
    sep.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
    entries.add(sep);

    //tool = new ConnectionCreationToolEntry("Connection Creation",
    //    "Creating connections", null, ImageDescriptor.createFromFile(
    //        FlowPlugin.class, "images/connection16.gif"),
    //    ImageDescriptor.createFromFile(Activity.class,
    //        "images/connection16.gif"));
    //entries.add(tool);
    controlGroup.addAll(entries);
    return controlGroup;
  }

  /**
   * Creates the PaletteRoot and adds all Palette elements.
   * 
   * @return the root
   */
  public static PaletteRoot createPalette() {
    PaletteRoot flowPalette = new PaletteRoot();
    flowPalette.addAll(createCategories(flowPalette));
    return flowPalette;
  }
  
}
