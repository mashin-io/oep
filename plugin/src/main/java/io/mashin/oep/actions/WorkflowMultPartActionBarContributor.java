package io.mashin.oep.actions;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

import io.mashin.oep.ui.editor.WorkflowEditor;

public class WorkflowMultPartActionBarContributor extends MultiPageEditorActionBarContributor {
	private IEditorPart activeEditorPart;
	private WorkflowActionBarContributor workflowActionContributor;
	private TextEditorActionContributor textEditorActionContributor;
	
	public WorkflowMultPartActionBarContributor() {
		workflowActionContributor = new WorkflowActionBarContributor();
		textEditorActionContributor = new TextEditorActionContributor();		
	}
	
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		workflowActionContributor.init(bars, page);
		textEditorActionContributor.init(bars, page);
	}
	
	@Override
	public void setActivePage(IEditorPart editor) {
		if (activeEditorPart == editor)
			return;
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {
			if (editor instanceof WorkflowEditor) {
				workflowActionContributor.setActiveEditor(editor);
			} else if (editor instanceof StructuredTextEditor) {
				textEditorActionContributor.setActiveEditor(editor);
			}
			actionBars.updateActionBars();
		}
		activeEditorPart = editor;
	}
	
	@Override
	public void dispose() {
		workflowActionContributor.dispose();
		textEditorActionContributor.dispose();
		super.dispose();
	}
}
