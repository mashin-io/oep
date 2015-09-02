package mashin.oep.ui.wizard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

public class WorkflowCreationWizard extends Wizard implements INewWizard {

  private CreationPage page;

  public void addPages() {
    // add pages to this wizard
    addPage(page);
  }

  public void init(IWorkbench workbench, IStructuredSelection selection) {
    // create pages for this wizard
    page = new CreationPage(workbench, selection);
  }

  public boolean performFinish() {
    return page.finish();
  }

  /**
   * This WizardPage can create an empty .shapes file for the ShapesEditor.
   */
  private class CreationPage extends WizardNewFileCreationPage {
    private static final String EXTENSION_XML = ".xml";
    private static final String EXTENSION_WORKFLOW = ".workflow";
    private final IWorkbench workbench;

    /**
     * Create a new wizard page instance.
     * 
     * @param workbench
     *            the current workbench
     * @param selection
     *            the current object selection
     * @see ShapesCreationWizard#init(IWorkbench, IStructuredSelection)
     */
    CreationPage(IWorkbench workbench, IStructuredSelection selection) {
      super("Workflow Creation Page", selection);
      this.workbench = workbench;
      setTitle("Create a new Oozie Workflow file");
      setDescription("Create a new Oozie Workflow file");
    }

    public void createControl(Composite parent) {
      super.createControl(parent);
      setFileName("workflow" + EXTENSION_WORKFLOW);
      setPageComplete(validatePage());
    }

    /** Return a new ShapesDiagram instance. */
    private String createDefaultContent() {
      return "<workflow-app xmlns=\"uri:oozie:workflow:0.5\" name=\"" + getFileName() + "\">" +
          "<start />" +
          "<end name=\"end\"/>" +
          "</workflow-app>" +
          "<!--<workflow>" +
          "<node name=\"start\" x=\"50\" y=\"100\"/>" +
          "<node name=\"end\" x=\"500\" y=\"100\"/>" +
          "</workflow>-->";
    }

    /**
     * This method will be invoked, when the "Finish" button is pressed.
     * 
     * @see ShapesCreationWizard#performFinish()
     */
    boolean finish() {
      // create a new file, result != null if successful
      IFile newFile = createNewFile();

      // open newly created file in the editor
      IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
      if (newFile != null && page != null) {
        try {
          IDE.openEditor(page, newFile, "mashin.oep.editor.flow", true);
        } catch (PartInitException e) {
          e.printStackTrace();
          return false;
        }
      }
      return true;
    }

    protected InputStream getInitialContents() {
      return new ByteArrayInputStream(createDefaultContent().getBytes());
    }

    /**
     * Return true, if the file name entered in this page is valid.
     */
    private boolean validateFilename() {
      if (getFileName() != null
          && (getFileName().endsWith(EXTENSION_XML)
              || getFileName().endsWith(EXTENSION_WORKFLOW))) {
        return true;
      }
      setErrorMessage("The 'file' name must end with "
          + EXTENSION_WORKFLOW + " or " + EXTENSION_XML);
      return false;
    }

    protected boolean validatePage() {
      return super.validatePage() && validateFilename();
    }
  }

}
