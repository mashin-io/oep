/**
 * Copyright (c) 2015 Mashin (http://mashin.io). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mashin.oep.ui.editor;

import io.mashin.oep.figures.NodeFigure;
import io.mashin.oep.hpdl.XMLUtils;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.parts.WorkflowEditPartFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
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
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class WorkflowEditor extends GraphicalEditorWithFlyoutPalette {
  
  private KeyHandler sharedKeyHandler;
  private Workflow workflow;
  private boolean editorSaving = false;
  
  OutlinePage outlinePage;
  
  private PaletteRoot paletteRoot;
  
  /**
   * This is called by the Workspace.
   */
  public WorkflowEditor() {
	  setEditDomain(new DefaultEditDomain(this));
  }
  public WorkflowEditor(IEditorPart editorPart) {
	  setEditDomain(new DefaultEditDomain(editorPart));
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

    ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();
    
    GraphicalViewer viewer = getGraphicalViewer();
    viewer.setEditPartFactory(new WorkflowEditPartFactory());
    viewer.setRootEditPart(root);
    viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer)
                              .setParent(getCommonKeyHandler()));
    
    List<String> zoomLevels = new ArrayList<>(3);
    zoomLevels.add(ZoomManager.FIT_ALL);
    zoomLevels.add(ZoomManager.FIT_WIDTH);
    zoomLevels.add(ZoomManager.FIT_HEIGHT);
    root.getZoomManager().setZoomLevelContributions(zoomLevels);
    root.getZoomManager().setZoomAnimationStyle(ZoomManager.ANIMATE_ZOOM_IN_OUT);

    IAction zoomIn = new ZoomInAction(root.getZoomManager());
    IAction zoomOut = new ZoomOutAction(root.getZoomManager());
    getActionRegistry().registerAction(zoomIn);
    getActionRegistry().registerAction(zoomOut);
    //getSite().getKeyBindingService().registerAction(zoomIn);
    //getSite().getKeyBindingService().registerAction(zoomOut);
    
    IHandlerService service = (IHandlerService) getEditorSite().getService(IHandlerService.class);
    service.activateHandler(zoomIn.getActionDefinitionId(), new ActionHandler(zoomIn));
    service.activateHandler(zoomOut.getActionDefinitionId(), new ActionHandler(zoomOut));
    
    IAction snapAction = new ToggleSnapToGeometryAction(getGraphicalViewer());
    getActionRegistry().registerAction(snapAction);

    IAction showGrid = new ToggleGridAction(getGraphicalViewer());
    getActionRegistry().registerAction(showGrid);
    
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
    
    //action = new AlignmentAction((IWorkbenchPart) this,
    //    PositionConstants.LEFT);
    //registry.registerAction(action);
    //getSelectionActions().add(action.getId());

    //action = new AlignmentAction((IWorkbenchPart) this,
    //    PositionConstants.RIGHT);
    //registry.registerAction(action);
    //getSelectionActions().add(action.getId());

    //action = new AlignmentAction((IWorkbenchPart) this,
    //    PositionConstants.TOP);
    //registry.registerAction(action);
    //getSelectionActions().add(action.getId());

    //action = new AlignmentAction((IWorkbenchPart) this,
    //    PositionConstants.BOTTOM);
    //registry.registerAction(action);
    //getSelectionActions().add(action.getId());

    action = new AlignmentAction((IWorkbenchPart) this,
        PositionConstants.CENTER);
    registry.registerAction(action);
    getSelectionActions().add(action.getId());

    action = new AlignmentAction((IWorkbenchPart) this,
        PositionConstants.MIDDLE);
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
    if (paletteRoot == null) {
      paletteRoot = WorkflowEditorPalleteFactory.createPalette(this);
    }
    return paletteRoot;
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
        NodeFigure.disposeImages();
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
        hpdl = XMLUtils.xmlnsToSchemaVersion(hpdl);
        //hpdl = XMLUtils.xmlnsSLAToSLAVersion(hpdl);
        
        br.close();
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
    
    if (hpdl.isEmpty()) {
      
      workflow = new Workflow();
      workflow.setName(input.getName());
      
    } else {
      
      try {
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(hpdl));
        //document.accept(new XMLReadUtils.NameSpaceCleaner());
        
        workflow = new Workflow(document);
        
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
      
    }
    
    setPartName(input.getName());
    
    if (!editorSaving) {
      if (getGraphicalViewer() != null) {
        getGraphicalViewer().setContents(getModel());
        //loadProperties();
      }
      if (outlinePage != null) {
        outlinePage.setContents(getModel());
      }
    }
  }
  
	public void setContent(String hpdl) {
		hpdl = XMLUtils.xmlnsToSchemaVersion(hpdl);
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(new StringReader(hpdl));
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
		workflow = new Workflow(document);
		if (getGraphicalViewer() != null) {
			getGraphicalViewer().setContents(getModel());
		}
		if (outlinePage != null) {
			outlinePage.setContents(getModel());
		}
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
  
  String writeModel() {
    try {
      Document document = DocumentHelper.createDocument();
      workflow.write(document.addElement("dummy"));
      OutputFormat format = OutputFormat.createPrettyPrint();
      
      IPreferencesService ps = Platform.getPreferencesService();
      boolean useSpaces = ps.getBoolean(EditorsUI.PLUGIN_ID,
    		  AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS, false, null);
      int tabWidth = ps.getInt(EditorsUI.PLUGIN_ID,
    		  AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH, 4, null);
      if (useSpaces) {
    	  StringBuilder sb = new StringBuilder();
    	  for (int i=0;i<tabWidth;i++)
    		  sb.append(" ");
    	  format.setIndent(sb.toString());
      } else {
    	  format.setIndent("\t");
      }
      
      StringWriter stringWriter = new StringWriter();
      XMLWriter writer = new XMLWriter(stringWriter, format);
      writer.write(document);
      stringWriter.flush();
      String hpdl = stringWriter.toString();
      hpdl = XMLUtils.schemaVersionToXmlns(hpdl);
      //hpdl = XMLUtils.slaVersionToXmlnsSLA(hpdl);
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
  
  @Override
  @SuppressWarnings("rawtypes")
  public Object getAdapter(Class type) {
    if (type == IContentOutlinePage.class) {
      outlinePage = new OutlinePage(new TreeViewer(), this,
          getActionRegistry(), getEditDomain(), getGraphicalViewer(),
          getSelectionSynchronizer());
      return outlinePage;
    } else if (type == ZoomManager.class) {
      return getGraphicalViewer().getProperty(ZoomManager.class.toString());
    }
    return super.getAdapter(type);
  }
  
  @Override
  public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	IEditorPart editor = getSite().getPage().getActiveEditor();
	if (editor instanceof WorkflowMultipartEditor) {
		WorkflowMultipartEditor wfMPEditor = (WorkflowMultipartEditor)editor;
		if (this.equals(wfMPEditor.getActiveEditor()))
			updateActions(getSelectionActions());
	} else {
		super.selectionChanged(part, selection);
	}
  }
}
