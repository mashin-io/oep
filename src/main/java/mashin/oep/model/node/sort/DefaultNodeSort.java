package mashin.oep.model.node.sort;

import org.eclipse.draw2d.geometry.Point;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;

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
