package mashin.oep.ui.editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;


public class XMLEditor {

  private static XMLEditor instance = null;
  
  private Map<Object, IEditorInput> inputMap;
  private Map<Object, File> fileMap;
  
  private XMLEditor() {
    inputMap = new HashMap<Object, IEditorInput>();
    fileMap = new HashMap<Object, File>();
  }
  
  public static synchronized XMLEditor getInstance() {
    if (instance == null) {
      instance = new XMLEditor();
    }
    return instance;
  }
  
  public void open(Object key, String xmlContent, String fileInfix, Consumer<String> consumer) {
    
    try {
      
      IWorkbenchPage page = PlatformUI.getWorkbench()
                              .getActiveWorkbenchWindow()
                              .getActivePage();
      
      IEditorInput input = inputMap.get(key);
      File tmpFile = fileMap.get(key);
      boolean shouldOpenNewEditor = false;
      
      if (input == null || tmpFile == null) {
        tmpFile = File.createTempFile("oep_" + fileInfix + "_" + System.nanoTime(), ".xml");
        fileMap.put(key, tmpFile);
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
        bw.write(xmlContent);
        bw.flush();
        bw.close();
        
        input = new FileStoreEditorInput(EFS.getStore(tmpFile.toURI()));
        inputMap.put(key, input);
        
        shouldOpenNewEditor = true;
      }
      
      IEditorPart editor = page.openEditor(
          input,
          IDE.getEditorDescriptor(tmpFile.getName()).getId());
      
      if (shouldOpenNewEditor) {
        page.addPartListener(new IPartListener() {
          
          @Override
          public void partClosed(IWorkbenchPart part) {
            if (part.equals(editor)) {
              File tmpFile = fileMap.get(key);
              consumer.accept(readContentFromTmpFile(tmpFile));
              tmpFile.delete();
              fileMap.remove(key);
              inputMap.remove(key);
              page.removePartListener(this);
            }
          }
          
          @Override
          public void partOpened(IWorkbenchPart part) {}
          @Override
          public void partDeactivated(IWorkbenchPart part) {}
          @Override
          public void partBroughtToTop(IWorkbenchPart part) {}
          @Override
          public void partActivated(IWorkbenchPart part) {}
          
        });
        
        editor.addPropertyListener(new IPropertyListener() {
          boolean wasDirty = false;
          @Override
          public void propertyChanged(Object source, int propId) {
            if (propId == IEditorPart.PROP_DIRTY) {
              if (!editor.isDirty() && wasDirty) {
                File tmpFile = fileMap.get(key);
                consumer.accept(readContentFromTmpFile(tmpFile));
              }
              wasDirty = editor.isDirty();
            }
          }
        });
      }
      
    } catch (Exception e) {
      
    }
    
  }
  
  private static String readContentFromTmpFile(File tmpFile) {
    try {
      StringBuffer sb = new StringBuffer();
      BufferedReader br = new BufferedReader(new FileReader(tmpFile));
      Iterator<String> linesIter = br.lines().iterator();
      while (linesIter.hasNext()) {
        sb.append(linesIter.next());
      }
      br.close();
      return sb.toString();
    } catch (Exception e) {
      return "";
    }
  }
  
  
}
