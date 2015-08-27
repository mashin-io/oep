package mashin.oep.editor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.EventObject;

import mashin.oep.figures.NodeFigure;
import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.model.Workflow;
import mashin.oep.parts.WorkflowEditPartFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;

public class WorkflowEditor extends GraphicalEditorWithFlyoutPalette {
  
  private KeyHandler sharedKeyHandler;
  private Workflow workflow;
  private boolean editorSaving = false;
  
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
    viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer)
                              .setParent(getCommonKeyHandler()));
    
    //// configure the context menu provider
    //ContextMenuProvider cmProvider = new ShapesEditorContextMenuProvider(
    //    viewer, getActionRegistry());
    //viewer.setContextMenu(cmProvider);
    //getSite().registerContextMenu(cmProvider, viewer);
    
    //getSite().setSelectionProvider(viewer);
    
  }
  
  /**
   * Returns the KeyHandler with common bindings for both the Outline and
   * Graphical Views. For example, delete is a common action.
   */
  protected KeyHandler getCommonKeyHandler() {
    if (sharedKeyHandler == null) {
      sharedKeyHandler = new KeyHandler();
      sharedKeyHandler.put(
          KeyStroke.getPressed(SWT.F2, 0),
          getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
      //sharedKeyHandler.put(
      //    KeyStroke.getPressed(SWT.DEL, 0),
      //    getActionRegistry().getAction(ActionFactory.DELETE.getId()));
    }
    return sharedKeyHandler;
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
    if (PALETTE_MODEL == null) {
      PALETTE_MODEL = WorkflowEditorPalleteFactory.createPalette(this);
    }
    return PALETTE_MODEL;
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
    
    getSite().getWorkbenchWindow().getWorkbench().addWorkbenchListener(new IWorkbenchListener() {
      @Override
      public boolean preShutdown(IWorkbench workbench, boolean forced) {
        return true;
      }
      
      @Override
      public void postShutdown(IWorkbench workbench) {
        NodeFigure.nodeImagesMap.values().forEach(Image::dispose);
        NodeFigure.nodeBgImage.dispose();
      }
    });
  }
  
  @Override
  protected void setInput(IEditorInput input) {
    super.setInput(input);
    BufferedReader br = null;
    String hpdl = "";
    try {
      
      if (input instanceof FileStoreEditorInput) {
        
        File file = new File(((FileStoreEditorInput) input).getURI());
        br = new BufferedReader(new FileReader(file));
        
      } else if (input instanceof IFileEditorInput) {
        
        IFile file = ((IFileEditorInput) input).getFile();
        br = new BufferedReader(new InputStreamReader(file.getContents()));
        
      }
      
      if (br != null) {
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
          sb.append(line);
        }
        hpdl = sb.toString().trim();
        hpdl = hpdl.replaceAll("xmlns=", "schema-version=");
        
        br.close();
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    if (hpdl.isEmpty()) {
      
      workflow = new Workflow();
      workflow.setName(input.getName());
      
    } else {
      
      try {
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(hpdl));
        document.accept(new XMLReadUtils.NameSpaceCleaner());
        
        workflow = new Workflow(document);
        
      } catch(Exception e) {
        e.printStackTrace();
      }
      
    }
    
    setPartName(input.getName());
  }
  
  @Override
  public void doSave(IProgressMonitor progressMonitor) {
    editorSaving = true;
    SafeRunner.run(new SafeRunnable() {
      public void run() throws Exception {
        //saveProperties();
        String hpdl = writeModel();
        
        IEditorInput input = getEditorInput();
        //IFile file = null;
        
        if (input instanceof FileStoreEditorInput) {
          
          //IWorkspace workspace = ResourcesPlugin.getWorkspace();
          //file = workspace.getRoot().getFile(new Path(((FileStoreEditorInput) input).getURI().getPath()));
          
          File file = new File(((FileStoreEditorInput) input).getURI());
          if (file.canWrite()) {
            byte[] bytes = hpdl.getBytes();
            progressMonitor.beginTask("Saving " + file.getAbsolutePath(), bytes.length);
            FileOutputStream fos = new FileOutputStream(file);
            for (int i = 0; i < bytes.length; i += 64) {
              int work = Math.min(64, bytes.length - i);
              fos.write(bytes, i, work);
              progressMonitor.worked(work);
            }
            fos.flush();
            fos.close();
            getCommandStack().markSaveLocation();
            progressMonitor.done();
          }
          
        } else if (input instanceof IFileEditorInput) {
          
          IFile file = ((IFileEditorInput) input).getFile();
          file.setContents(new ByteArrayInputStream(hpdl.getBytes()),
              true, false, progressMonitor);
          getCommandStack().markSaveLocation();
          
        }
        
        //if (file != null) {
        //  file.setContents(new ByteArrayInputStream(hpdl.getBytes()),
        //      true, false, progressMonitor);
        //  getCommandStack().markSaveLocation();
        //}
      }
    });
    editorSaving = false;
  }
  
  @Override
  public void doSaveAs() {
    SaveAsDialog dialog = new SaveAsDialog(getSite().getWorkbenchWindow()
        .getShell());
    
    IEditorInput input = getEditorInput();
    IFile file = null;
    
    if (input instanceof FileStoreEditorInput) {
      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      file = workspace.getRoot().getFile(new Path(((FileStoreEditorInput) input).getURI().getPath()));
    } else if (input instanceof IFileEditorInput) {
      file = ((IFileEditorInput) input).getFile();
    }
    
    if (file == null) {
      return;
    }
    
    dialog.setOriginalFile(file);
    dialog.open();
    IPath path = dialog.getResult();

    if (path == null) {
      return;
    }

    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    final IFile newFile = workspace.getRoot().getFile(path);

    if (!file.exists()) {
      WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
        public void execute(final IProgressMonitor monitor) {
          //saveProperties();
          try {
            String hpdl = writeModel();
            newFile.create(
                new ByteArrayInputStream(hpdl.getBytes()),
                true, monitor);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };
      try {
        new ProgressMonitorDialog(getSite().getWorkbenchWindow()
            .getShell()).run(false, true, op);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    try {
      superSetInput(new FileEditorInput(newFile));
      getCommandStack().markSaveLocation();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private String writeModel() {
    try {
      Document document = DocumentHelper.createDocument();
      workflow.write(document.addElement("dummy"));
      OutputFormat format = OutputFormat.createPrettyPrint();
      StringWriter stringWriter = new StringWriter();
      XMLWriter writer = new XMLWriter(stringWriter, format);
      writer.write(document);
      stringWriter.flush();
      String hpdl = stringWriter.toString();
      hpdl = hpdl.replaceAll("schema-version=", "xmlns=");
      return hpdl;
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }
  
  protected void superSetInput(IEditorInput input) {
    // The workspace never changes for an editor. So, removing and re-adding
    // the
    // resourceListener is not necessary. But it is being done here for the
    // sake
    // of proper implementation. Plus, the resourceListener needs to be
    // added
    // to the workspace the first time around.
    //if (getEditorInput() != null) {
    //  IFile file = ((IFileEditorInput) getEditorInput()).getFile();
    //  file.getWorkspace().removeResourceChangeListener(resourceListener);
    //}

    super.setInput(input);

    if (getEditorInput() != null) {
      //IFile file = ((IFileEditorInput) getEditorInput()).getFile();
      //file.getWorkspace().addResourceChangeListener(resourceListener);
      setPartName(input.getName());
    }
  }
  
}
