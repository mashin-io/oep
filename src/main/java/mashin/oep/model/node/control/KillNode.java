package mashin.oep.model.node.control;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.hpdl.XMLWriteUtils;
import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.property.TextPropertyElement;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.NoOutputTerminal;

import org.dom4j.Element;

public class KillNode extends ControlNode {

  public static final String PROP_NODE_KILL_MESSAGE = "prop.node.kill.message";
  
  protected TextPropertyElement message;
  
  protected FanInTerminal fanInTerminal;
  protected NoOutputTerminal noOutputTerminal;
  
  public KillNode(Workflow workflow) {
    this(workflow, null);
  }

  public KillNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    fanInTerminal     = new FanInTerminal(TERMINAL_FANIN, this);
    noOutputTerminal  = new NoOutputTerminal(TERMINAL_NOOUT, this);
    terminals.add(fanInTerminal);
    terminals.add(noOutputTerminal);
    message = new TextPropertyElement(PROP_NODE_KILL_MESSAGE, "Message");
    addPropertyElement(message);
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("kill-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(org.dom4j.Element parentNode) {
    super.write(parentNode);
    
    Element element = (Element) hpdlModel.get();
    XMLWriteUtils.writeTextPropertyAsElement(message, element, "message");
  }

  @Override
  public void read(org.dom4j.Node hpdlNode) {
    super.read(hpdlNode);
    XMLReadUtils.initTextPropertyFrom(message, hpdlNode, "./message");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_KILL;
  }
  
  public void setMessage(String message) {
    setPropertyValue(PROP_NODE_KILL_MESSAGE, message);
  }
  
  public String getMessage() {
    return message.getStringValue();
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
