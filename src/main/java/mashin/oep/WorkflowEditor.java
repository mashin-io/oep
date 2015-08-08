package mashin.oep;

import java.util.EventObject;

import mashin.oep.model.Workflow;
import mashin.oep.parts.WorkflowEditPartFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

public class WorkflowEditor extends GraphicalEditorWithFlyoutPalette {
  
  private Workflow workflow;
  
  private static PaletteRoot PALETTE_MODEL;
  
  /**
   * This is called by the Workspace.
   */
  public WorkflowEditor() {
    setEditDomain(new DefaultEditDomain(this));
  }
  
  /**
   * Configure the graphical viewer before it receives contents.
   * <p>
   * This is the place to choose an appropriate RootEditPart and
   * EditPartFactory for your editor. The RootEditPart determines the behavior
   * of the editor's "work-area". For example, GEF includes zoomable and
   * scrollable root edit parts. The EditPartFactory maps model elements to
   * edit parts (controllers).
   * </p>
   */
  protected void configureGraphicalViewer() {
    super.configureGraphicalViewer();

    GraphicalViewer viewer = getGraphicalViewer();
    viewer.setEditPartFactory(new WorkflowEditPartFactory());
    viewer.setRootEditPart(new ScalableFreeformRootEditPart());
    viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));
    
    //// configure the context menu provider
    //ContextMenuProvider cmProvider = new ShapesEditorContextMenuProvider(
    //    viewer, getActionRegistry());
    //viewer.setContextMenu(cmProvider);
    //getSite().registerContextMenu(cmProvider, viewer);
  }
  
  @Override
  public void commandStackChanged(EventObject event) {
    firePropertyChange(IEditorPart.PROP_DIRTY);
    super.commandStackChanged(event);
  }
  
  @Override
  protected PaletteViewerProvider createPaletteViewerProvider() {
    return new PaletteViewerProvider(getEditDomain()) {
      protected void configurePaletteViewer(PaletteViewer viewer) {
        super.configurePaletteViewer(viewer);
        // create a drag source listener for this palette viewer
        // together with an appropriate transfer drop target listener,
        // this will enable
        // model element creation by dragging a
        // CombinatedTemplateCreationEntries
        // from the palette into the editor
        // @see ShapesEditor#createTransferDropTargetListener()
        viewer.addDragSourceListener(new TemplateTransferDragSourceListener(
            viewer));
      }
    };
  }
  
  @SuppressWarnings("unchecked")
  @Override
  protected void createActions() {
    super.createActions();
    ActionRegistry registry = getActionRegistry();
    IAction action;

    action = new DirectEditAction((IWorkbenchPart) this);
    registry.registerAction(action);
    getSelectionActions().add(action.getId());
  }
  
  /**
   * Create a transfer drop target listener. When using a
   * CombinedTemplateCreationEntry tool in the palette, this will enable model
   * element creation by dragging from the palette.
   * 
   * @see #createPaletteViewerProvider()
   */
  private TransferDropTargetListener createTransferDropTargetListener() {
    return new TemplateTransferDropTargetListener(getGraphicalViewer()) {
      @SuppressWarnings("rawtypes")
      protected CreationFactory getFactory(Object template) {
        return new SimpleFactory((Class) template);
      }
    };
  }
  
  @Override
  protected PaletteRoot getPaletteRoot() {
    if (PALETTE_MODEL == null)
      PALETTE_MODEL = WorkflowEditorPalleteFactory.createPalette();
    return PALETTE_MODEL;
  }

  @Override
  public void doSave(IProgressMonitor monitor) {
    
  }
  
  Workflow getModel() {
    return this.workflow;
  }
  
  /**
   * Set up the editor's inital content (after creation).
   */
  protected void initializeGraphicalViewer() {
    super.initializeGraphicalViewer();
    GraphicalViewer viewer = getGraphicalViewer();
    viewer.setContents(getModel()); // set the contents of this editor

    // listen for dropped parts
    viewer.addDropTargetListener(createTransferDropTargetListener());
  }
  
  @Override
  protected void setInput(IEditorInput input) {
    super.setInput(input);
    workflow = new Workflow();
    workflow.init();
    workflow.setName(input.getName());
    setPartName(input.getName());
  }
  
}
