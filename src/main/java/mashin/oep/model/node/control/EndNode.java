package mashin.oep.model.node.control;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.NoOutputTerminal;

import org.dom4j.Element;

public class EndNode extends ControlNode {

  protected FanInTerminal fanInTerminal;
  protected NoOutputTerminal noOutputTerminal;
  
  public EndNode(Workflow workflow) {
    this(workflow, null);
  }

  public EndNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    fanInTerminal     = new FanInTerminal(TERMINAL_FANIN, this);
    noOutputTerminal  = new NoOutputTerminal(TERMINAL_NOOUT, this);
    terminals.add(fanInTerminal);
    terminals.add(noOutputTerminal);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("end-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element parent) {
    super.write(parent);
  }
  
  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
  }
  
  @Override
  public String getNodeType() {
    return TYPE_END;
  }
  
  @Override
  public boolean canConnectTo(Node target) {
    return false;
  }

  @Override
  public boolean canConnectFrom(Node source) {
    return true;
  }

}
