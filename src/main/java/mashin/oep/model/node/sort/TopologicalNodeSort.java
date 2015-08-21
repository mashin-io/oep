package mashin.oep.model.node.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mashin.oep.figures.NodeFigure;
import mashin.oep.model.Workflow;
import mashin.oep.model.connection.WorkflowConnection;
import mashin.oep.model.node.Node;
import mashin.oep.model.node.control.StartNode;

import org.eclipse.draw2d.geometry.Point;

public class TopologicalNodeSort extends NodeSort {

  public TopologicalNodeSort(Workflow workflow) {
    super(workflow);
  }

  @Override
  public void sort() {
    List<Topology> topologies = buildTopology();
    
    Topology theTopology = null;
    for (Topology topology : topologies) {
      if (topology.node instanceof StartNode) {
        theTopology = topology;
        break;
      }
    }
    
    Map<Integer, List<Topology>> levels = new HashMap<Integer, List<Topology>>();
    
    theTopology.visitCount = -1;
    setLevels(theTopology, null, levels);
    
    int numberOfLevels = levels.size();
    for (int i = 0; i < numberOfLevels; i++) {
      List<Topology> level = levels.get(i);
      int x = i * (NodeFigure.WIDTH + 5);
      int y = NodeFigure.HEIGHT;
      for (Topology topology : level) {
        Node node = topology.node;
        node.setPosition(new Point(x, y));
        y += NodeFigure.HEIGHT + 5;
      }
    }
    
  }
  
  private void setLevels(Topology topology, Topology fromTopology, Map<Integer, List<Topology>> levels) {
    if (topology.isVisited()) {
      return;
    }
    
    topology.visit();
    
    // get or initialize the list of nodes at the same
    // level as the current node
    List<Topology> level = levels.get(topology.level);
    if (level == null) {
      level = new ArrayList<Topology>();
      level.add(topology);
      levels.put(topology.level, level);
    }
    
    topology.updateLevel(fromTopology);
    
    // remove the current node from the old level list
    // and add it to the list of the new level
    // initializing the list of the new level if
    // not exist
    level.remove(topology);
    level = levels.get(topology.level);
    if (level == null) {
      level = new ArrayList<Topology>();
      levels.put(topology.level, level);
    }
    level.add(topology);
    
    for (Topology toTopology : topology.to) {
      setLevels(toTopology, topology, levels);
    }
  }
  
  private List<Topology> buildTopology() {
    List<Topology> topologies = new ArrayList<Topology>();
    
    for (Node node : workflow.getNodes()) {
      Topology nodeTopology = getOrAddTopologyFor(node, topologies);
      
      List<WorkflowConnection> srcConn = node.getSourceConnections();
      for (WorkflowConnection conn : srcConn) {
        Node toNode = conn.getTargetNode();
        Topology toTopology = getOrAddTopologyFor(toNode, topologies);
        nodeTopology.to.add(toTopology);
        toTopology.from.add(nodeTopology);
      }
    }
    
    for (int i = 0; i < topologies.size(); i++) {
      Topology nodeTopology = topologies.get(i);
      if (nodeTopology.from.size() > 0) {
        topologies.remove(i);
        i--;
      }
    }
    
    return topologies;
  }
  
  private Topology getOrAddTopologyFor(Node node, List<Topology> topologies) {
    Topology topology = new Topology(node);
    int index = topologies.indexOf(topology);
    if (index > -1) {
      return topologies.get(index);
    } else {
      topologies.add(topology);
      return topology;
    }
  }
  
  class Topology {
    List<Topology> from = new ArrayList<Topology>();
    List<Topology> to = new ArrayList<Topology>();
    Node node;
    
    int level = 0;
    int visitCount = 0;
    
    public Topology(Node node) {
      this.node = node;
    }
    
    public void visit() {
      visitCount++;
    }
    
    public void updateLevel(Topology fromTopology) {
      if (fromTopology != null) {
        if (fromTopology.level >= level) {
          level = fromTopology.level + 1;
        }
      }
    }
    
    public boolean isVisited() {
      return visitCount >= from.size();
    }
    
    @Override
    public boolean equals(Object obj) {
      if (obj == null || !(obj instanceof Topology)) {
        return false;
      }
      return node.equals(((Topology) obj).node);
    }
  }

}
