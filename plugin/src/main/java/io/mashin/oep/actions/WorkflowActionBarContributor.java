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

package io.mashin.oep.actions;

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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

public class WorkflowActionBarContributor extends ActionBarContributor {

  @Override
  protected void buildActions() {
    addRetargetAction(new UndoRetargetAction());
    addRetargetAction(new RedoRetargetAction());
    
    //addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
    addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
    //addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
    //addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
    addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
    //addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));

    addRetargetAction(new ZoomInRetargetAction());
    addRetargetAction(new ZoomOutRetargetAction());
    
    RetargetAction snapAction = new RetargetAction(
            GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY,
            "Snap To Geometry", IAction.AS_CHECK_BOX);
    snapAction.setImageDescriptor(ImageDescriptor.createFromURL(
    		getClass().getResource("/io/mashin/oep/icons/snap-16.png")));
    addRetargetAction(snapAction);

    RetargetAction gridAction = new RetargetAction(
            GEFActionConstants.TOGGLE_GRID_VISIBILITY,
            "Grid", IAction.AS_CHECK_BOX);
    gridAction.setImageDescriptor(ImageDescriptor.createFromURL(
    		getClass().getResource("/io/mashin/oep/icons/grid-16.png")));
    addRetargetAction(gridAction);
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
    tbm.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
    tbm.add(getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY));
    
    tbm.add(new Separator());
    //tbm.add(getAction(GEFActionConstants.ALIGN_LEFT));
    tbm.add(getAction(GEFActionConstants.ALIGN_CENTER));
    //tbm.add(getAction(GEFActionConstants.ALIGN_RIGHT));
    
    //tbm.add(new Separator());
    //tbm.add(getAction(GEFActionConstants.ALIGN_TOP));
    tbm.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
    //tbm.add(getAction(GEFActionConstants.ALIGN_BOTTOM));

    tbm.add(new Separator());
    String[] zoomStrings = new String[] { ZoomManager.FIT_ALL,
        ZoomManager.FIT_HEIGHT, ZoomManager.FIT_WIDTH };
    tbm.add(new ZoomComboContributionItem(getPage(), zoomStrings) {
    	@Override
    	protected int computeWidth(Control control) {
    		return Math.min(super.computeWidth(control), 130);
    	}
    });
  }

  public void contributeToMenu(IMenuManager menubar) {
    super.contributeToMenu(menubar);
    MenuManager viewMenu = new MenuManager("View");
    viewMenu.add(getAction(GEFActionConstants.ZOOM_IN));
    viewMenu.add(getAction(GEFActionConstants.ZOOM_OUT));
    viewMenu.add(new Separator());
    viewMenu.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
    viewMenu.add(getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY));
    menubar.insertAfter(IWorkbenchActionConstants.M_EDIT, viewMenu);
  }

}
