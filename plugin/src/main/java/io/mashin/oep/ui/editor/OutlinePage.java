package io.mashin.oep.ui.editor;

import io.mashin.oep.Activator;
import io.mashin.oep.parts.tree.WorkflowTreeEditPartFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.PageBook;

public class OutlinePage extends ContentOutlinePage implements IAdaptable {

  private WorkflowEditor workflowEditor;
  private ActionRegistry actionRegistry;
  EditDomain editDomain;
  GraphicalViewer graphicalViewer;
  SelectionSynchronizer selectionSynchronizer;
  
  private PageBook pageBook;
  private Control outline;
  private Canvas overview;
  private IAction showOutlineAction, showOverviewAction;
  static final int ID_OUTLINE = 0;
  static final int ID_OVERVIEW = 1;
  private Thumbnail thumbnail;
  private DisposeListener disposeListener;
  
  public OutlinePage(EditPartViewer viewer, WorkflowEditor workflowEditor,
      ActionRegistry actionRegistry, EditDomain editDomain,
      GraphicalViewer graphicalViewer,
      SelectionSynchronizer selectionSynchronizer) {
    super(viewer);
    this.workflowEditor = workflowEditor;
    this.actionRegistry = actionRegistry;
    this.editDomain = editDomain;
    this.graphicalViewer = graphicalViewer;
    this.selectionSynchronizer = selectionSynchronizer;
  }

  public void init(IPageSite pageSite) {
    super.init(pageSite);
    ActionRegistry registry = actionRegistry;
    IActionBars bars = pageSite.getActionBars();
    String id = ActionFactory.UNDO.getId();
    bars.setGlobalActionHandler(id, registry.getAction(id));
    id = ActionFactory.REDO.getId();
    bars.setGlobalActionHandler(id, registry.getAction(id));
    id = ActionFactory.DELETE.getId();
    bars.setGlobalActionHandler(id, registry.getAction(id));
    bars.updateActionBars();
  }

  protected void configureOutlineViewer() {
    getViewer().setEditDomain(editDomain);
    getViewer().setEditPartFactory(new WorkflowTreeEditPartFactory());
    //ContextMenuProvider provider = new LogicContextMenuProvider(
    //    getViewer(), getActionRegistry());
    //getViewer().setContextMenu(provider);
    //getSite().registerContextMenu(
    //    "org.eclipse.gef.examples.logic.outline.contextmenu", //$NON-NLS-1$
    //    provider, getSite().getSelectionProvider());
    getViewer().setKeyHandler(workflowEditor.getCommonKeyHandler());
    getViewer().addDropTargetListener(
        (TransferDropTargetListener) new TemplateTransferDropTargetListener(
            getViewer()));
    IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
    
    showOutlineAction = new Action() {
      public void run() {
        showPage(ID_OUTLINE);
      }
    };
    showOutlineAction.setImageDescriptor(ImageDescriptor
        .createFromFile(Activator.class, "icons/outline.gif"));
    showOutlineAction.setToolTipText("Show Outline");
    tbm.add(showOutlineAction);
    
    showOverviewAction = new Action() {
      public void run() {
        showPage(ID_OVERVIEW);
      }
    };
    showOverviewAction.setImageDescriptor(ImageDescriptor
        .createFromFile(Activator.class, "icons/overview.gif"));
    showOverviewAction.setToolTipText("Show Overview");
    tbm.add(showOverviewAction);
    
    showPage(ID_OUTLINE);
  }

  public void createControl(Composite parent) {
    pageBook = new PageBook(parent, SWT.NONE);
    outline = getViewer().createControl(pageBook);
    overview = new Canvas(pageBook, SWT.NONE);
    pageBook.showPage(outline);
    configureOutlineViewer();
    hookOutlineViewer();
    initializeOutlineViewer();
  }

  public void dispose() {
    unhookOutlineViewer();
    if (thumbnail != null) {
      thumbnail.deactivate();
      thumbnail = null;
    }
    super.dispose();
    workflowEditor.outlinePage = null;
  }

  @SuppressWarnings("rawtypes")
  public Object getAdapter(Class type) {
    if (type == ZoomManager.class)
      return graphicalViewer.getProperty(ZoomManager.class.toString());
    return null;
  }

  public Control getControl() {
    return pageBook;
  }

  protected void hookOutlineViewer() {
    selectionSynchronizer.addViewer(getViewer());
  }

  protected void initializeOutlineViewer() {
    setContents(workflowEditor.getModel());
  }

  protected void initializeOverview() {
    LightweightSystem lws = new LightweightSystem(overview);
    RootEditPart rep = graphicalViewer.getRootEditPart();
    if (rep instanceof ScalableFreeformRootEditPart) {
      ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) rep;
      thumbnail = new ScrollableThumbnail((Viewport) root.getFigure());
      thumbnail.setBorder(new MarginBorder(3));
      thumbnail.setSource(root
          .getLayer(LayerConstants.PRINTABLE_LAYERS));
      lws.setContents(thumbnail);
      disposeListener = new DisposeListener() {
        public void widgetDisposed(DisposeEvent e) {
          if (thumbnail != null) {
            thumbnail.deactivate();
            thumbnail = null;
          }
        }
      };
      getEditor().addDisposeListener(disposeListener);
    }
  }

  public void setContents(Object contents) {
    getViewer().setContents(contents);
  }

  protected void showPage(int id) {
    if (id == ID_OUTLINE) {
      showOutlineAction.setChecked(true);
      showOverviewAction.setChecked(false);
      pageBook.showPage(outline);
      if (thumbnail != null)
        thumbnail.setVisible(false);
    } else if (id == ID_OVERVIEW) {
      if (thumbnail == null)
        initializeOverview();
      showOutlineAction.setChecked(false);
      showOverviewAction.setChecked(true);
      pageBook.showPage(overview);
      thumbnail.setVisible(true);
    }
  }

  protected void unhookOutlineViewer() {
    selectionSynchronizer.removeViewer(getViewer());
    if (disposeListener != null && getEditor() != null
        && !getEditor().isDisposed())
      getEditor().removeDisposeListener(disposeListener);
  }
  
  protected FigureCanvas getEditor() {
    return (FigureCanvas) graphicalViewer.getControl();
  }

}
