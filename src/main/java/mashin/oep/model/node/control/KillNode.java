package mashin.oep.model.node.control;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import mashin.oep.Utils;
import mashin.oep.model.Workflow;
import mashin.oep.model.node.Node;
import mashin.oep.model.property.TextPropertyElement;
import mashin.oep.model.terminal.FanInTerminal;
import mashin.oep.model.terminal.NoOutputTerminal;

public class KillNode extends ControlNode {

  public static final String PROP_NODE_KILL_MESSAGE = "prop.node.kill.message";
  
  private static IPropertyDescriptor[] KILL_NODE_PROPERTY_DESCRIPTORS;
  
  protected TextPropertyElement message;
  
  protected FanInTerminal fanInTerminal;
  protected NoOutputTerminal noOutputTerminal;
  
  public KillNode(Workflow workflow) {
    super(workflow);
    fanInTerminal     = new FanInTerminal("fan-in", this);
    noOutputTerminal  = new NoOutputTerminal("", this);
    terminals.add(fanInTerminal);
    terminals.add(noOutputTerminal);
    setName("kill-" + ID_SEQ.incrementAndGet());
    message = new TextPropertyElement(PROP_NODE_KILL_MESSAGE, "Message");
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if (KILL_NODE_PROPERTY_DESCRIPTORS == null) {
      KILL_NODE_PROPERTY_DESCRIPTORS = Utils.combine (
          super.getPropertyDescriptors(),
          message.getPropertyDescriptors()
      );
    }
    return KILL_NODE_PROPERTY_DESCRIPTORS;
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    switch ((String) propertyName) {
    case PROP_NODE_KILL_MESSAGE:
      setMessage((String) propertyValue);
      break;
    default:
      super.setPropertyValue(propertyName, propertyValue);
      break;
    }
  }
  
  @Override
  public Object getPropertyValue(Object propertyName) {
    switch ((String) propertyName) {
    case PROP_NODE_KILL_MESSAGE:
      return getMessage();
    default:
      return super.getPropertyValue(propertyName);
    }
  }
  
  public void setMessage(String message) {
    String oldMessage = this.message.getStringValue();
    this.message.setStringValue(message);
    firePropertyChange(PROP_NODE_KILL_MESSAGE, oldMessage, message);
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
