package io.mashin.oep.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

public class WorkflowMultipartEditor extends MultiPageEditorPart {
	private WorkflowEditor workflowEditor;
	private StructuredTextEditor textEditor;
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		String projectName = ((IFileEditorInput)input).getFile().getProject().getName();
		setPartName(input.getName()+" ["+projectName+"]");
	}
	
	@Override
	public boolean isDirty() {
		return getActiveEditor().isDirty();
	}
	
	@Override
	protected void createPages() {
		try {
			workflowEditor = new WorkflowEditor(this);	
			int index = addPage(workflowEditor, getEditorInput());
			setPageText(index, "Design");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor", null, e.getStatus());
		}
		try {
			textEditor = new StructuredTextEditor();
			textEditor.setEditorPart(this);
			int index = addPage(textEditor, getEditorInput());
			setPageText(index, "Source");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor", null, e.getStatus());
		}
	}
	
	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if (newPageIndex == 0) {
			updateTextEditorToWorkflowEditor(true);
		} else {
			updateWorkflowEditorToTextEditor(true);
		}
	}
	
	private void updateTextEditorToWorkflowEditor(boolean validate) {
		if (textEditor.isDirty() || !validate) {
			workflowEditor.setContent(textEditor.getDocumentProvider()
					.getDocument(textEditor.getEditorInput()).get());
		}
	}
	private void updateWorkflowEditorToTextEditor(boolean validate) {
		//IDocument textDocument = textEditor.getDocumentProvider()
		//		.getDocument(textEditor.getEditorInput());
		if (workflowEditor.isDirty() || !validate) {
			String worflowContent = workflowEditor.writeModel();
			textEditor.getDocumentProvider()
				.getDocument(textEditor.getEditorInput()).set(worflowContent);
		}
	}
	
	@Override
	public IEditorPart getActiveEditor() {
		return super.getActiveEditor();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getActiveEditor().doSave(monitor);
		if (textEditor.equals(getActiveEditor())) {
			updateTextEditorToWorkflowEditor(false);
		}
	}
	@Override
	public void doSaveAs() {
		getActiveEditor().doSaveAs();
	}
	
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}
}
