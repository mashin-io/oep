package io.mashin.oep.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPropertyListener;
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
		if ("workflow.xml".equals(input.getName()))
			setPartName(input.getName()+" ["+projectName+"]");
		else
			setPartName(input.getName());
	}
	
	@Override
	public boolean isDirty() {
		return textEditor.isDirty();
	}
	
	@Override
	protected void createPages() {
		textEditor = new StructuredTextEditor();
		textEditor.setEditorPart(this);
		workflowEditor = new WorkflowEditor(textEditor);
		workflowEditor.addPropertyListener(new IPropertyListener() {
			@Override
			public void propertyChanged(Object source, int propId) {
				if (propId == 257 && !textEditor.isDirty()) {
					updateTextEditorFromWorkflowEditor();
				}
			}
		});
		
		try {
			int index;
			index = addPage(workflowEditor, getEditorInput());
			setPageText(index, "Design");
			index = addPage(textEditor, getEditorInput());
			setPageText(index, "Source");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor", null, e.getStatus());
		}
	}
	
	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if (textEditor.isDirty()) {
			if (newPageIndex == 0)
				updateWorkflowEditorFromTextEditor();
			else
				updateTextEditorFromWorkflowEditor();
		}
	}
	
	private void updateWorkflowEditorFromTextEditor() {
		workflowEditor.setContent(textEditor.getDocumentProvider()
				.getDocument(textEditor.getEditorInput()).get());
	}
	private void updateTextEditorFromWorkflowEditor() {
		String worflowContent = workflowEditor.writeModel();
		textEditor.getDocumentProvider()
			.getDocument(textEditor.getEditorInput()).set(worflowContent);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (getActivePage() == 0)
			updateTextEditorFromWorkflowEditor();
		else
			updateWorkflowEditorFromTextEditor();
		textEditor.doSave(monitor);
	}
	@Override
	public void doSaveAs() {
		textEditor.doSaveAs();
	}
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	@Override
	public IEditorPart getActiveEditor() {
		return super.getActiveEditor();
	}
}
