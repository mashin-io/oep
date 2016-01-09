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

package io.mashin.oep.parts;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

public class NodeLabelCellEditorLocator implements CellEditorLocator {

  private Label label;

  public NodeLabelCellEditorLocator(Label label) {
    this.label = label;
  }

  @Override
  public void relocate(CellEditor celleditor) {
    Text text = (Text) celleditor.getControl();
    Point pref = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
    Rectangle rect = label.getTextBounds().getCopy();
    label.translateToAbsolute(rect);
    text.setBounds(rect.x - 1, rect.y - 1, pref.x + 1, pref.y + 1);
  }

}
