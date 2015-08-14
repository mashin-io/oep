package mashin.oep.model.node.control;

import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.property.TextPropertyElement;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.NoOutputTerminal;

public class KillNode extends ControlNode {

  public static final String PROP_NODE_KILL_MESSAGE = "prop.node.kill.message";
  
  protected TextPropertyElement message;
  
  protected FanInTerminal fanInTerminal;
  protected NoOutputTerminal noOutputTerminal;
  
  public KillNode(Workflow workflow) {
    super(workflow);
    fanInTerminal     = new FanInTerminal(TERMINAL_FANIN, this);
    noOutputTerminal  = new NoOutputTerminal(TERMINAL_NONE, this);
    terminals.add(fanInTerminal);
    terminals.add(noOutputTerminal);
    setName("kill-" + ID_SEQ.incrementAndGet());
    message = new TextPropertyElement(PROP_NODE_KILL_MESSAGE, "Message");
    addPropertyElement(message);
  }

  public void setMessage(String message) {
    setPropertyValue(PROP_NODE_KILL_MESSAGE, message);
  }
  
  public String getMessage() {
    return message.getStringValue();
  }
  
  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
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
