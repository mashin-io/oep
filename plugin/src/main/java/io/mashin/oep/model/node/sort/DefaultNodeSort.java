package io.mashin.oep.model.node.sort;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;

import org.eclipse.draw2d.geometry.Point;

public class DefaultNodeSort extends NodeSort {

  public DefaultNodeSort(Workflow workflow) {
    super(workflow);
  }

  @Override
  public void sort() {
    for (Node node : nodes) {
      node.setPosition(new Point(0, 0));
    }
  }

}
