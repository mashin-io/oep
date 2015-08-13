package mashin.oep.actions;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;

public class WorkflowActionBarContributor extends ActionBarContributor {

  @Override
  protected void buildActions() {
    addRetargetAction(new UndoRetargetAction());
    addRetargetAction(new RedoRetargetAction());
    
    addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
    addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
    addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
    addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
    addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
    addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));

    addRetargetAction(new ZoomInRetargetAction());
    addRetargetAction(new ZoomOutRetargetAction());
  }

  @Override
  protected void declareGlobalActionKeys() {
    addGlobalActionKey(ActionFactory.SELECT_ALL.getId());
    addGlobalActionKey(ActionFactory.DELETE.getId());
  }
  
  public void contributeToToolBar(IToolBarManager tbm) {
    tbm.add(getAction(ActionFactory.UNDO.getId()));
    tbm.add(getAction(ActionFactory.REDO.getId()));

    tbm.add(new Separator());
    tbm.add(getAction(GEFActionConstants.ALIGN_LEFT));
    tbm.add(getAction(GEFActionConstants.ALIGN_CENTER));
    tbm.add(getAction(GEFActionConstants.ALIGN_RIGHT));
    
    tbm.add(new Separator());
    tbm.add(getAction(GEFActionConstants.ALIGN_TOP));
    tbm.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
    tbm.add(getAction(GEFActionConstants.ALIGN_BOTTOM));

    tbm.add(new Separator());
    String[] zoomStrings = new String[] { ZoomManager.FIT_ALL,
        ZoomManager.FIT_HEIGHT, ZoomManager.FIT_WIDTH };
    tbm.add(new ZoomComboContributionItem(getPage(), zoomStrings));
  }

  /**
   * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(IMenuManager)
   */
  public void contributeToMenu(IMenuManager menubar) {
    super.contributeToMenu(menubar);
    MenuManager viewMenu = new MenuManager("View");
    viewMenu.add(getAction(GEFActionConstants.ZOOM_IN));
    viewMenu.add(getAction(GEFActionConstants.ZOOM_OUT));
    menubar.insertAfter(IWorkbenchActionConstants.M_EDIT, viewMenu);
  }

}
