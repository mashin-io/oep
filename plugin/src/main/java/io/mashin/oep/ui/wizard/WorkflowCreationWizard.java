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

package io.mashin.oep.ui.wizard;

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
    private static final String EXTENSION_OZW = ".ozw";
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
      setFileName("workflow" + EXTENSION_OZW);
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
          IDE.openEditor(page, newFile, "io.mashin.oep.editor.flow", true);
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
          && (getFileName().endsWith(EXTENSION_OZW)
              || getFileName().endsWith(EXTENSION_WORKFLOW)
              || getFileName().equals("workflow.xml"))) {
        return true;
      }
      setErrorMessage("The 'file' name must end with "
          + EXTENSION_WORKFLOW + " or " + EXTENSION_OZW
          + " or file name must workflow.xml");
      return false;
    }

    protected boolean validatePage() {
      return super.validatePage() && validateFilename();
    }
  }

}
