package mashin.oep.model.node.sort;

import java.util.ArrayList;
import java.util.List;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;

public abstract class NodeSort {

  Workflow workflow;
  List<Node> nodes;
  
  public NodeSort(Workflow workflow) {
    this.workflow = workflow;
    nodes = new ArrayList<Node>();
  }
  
  public void add(Node node) {
    nodes.add(node);
  }
  
  public void remove(Node node) {
    nodes.remove(node);
  }
  
  public void reset() {
    nodes.clear();
  }
  
  public boolean needsSort() {
    return nodes.size() > 0;
  }
  
  public abstract void sort();
  
}
