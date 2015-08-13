package mashin.oep.model.connection;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import mashin.oep.Utils;
import mashin.oep.model.node.Node;
import mashin.oep.model.property.TextPropertyElement;
import mashin.oep.model.terminal.Terminal;

public class WorkflowCaseConnection extends WorkflowConnection {

  public static final String PROP_CONN_CONDITION = "prop.conn.condition";
  
  protected static IPropertyDescriptor[] CASE_CONNECTION_DESCRIPTORS;
  
  protected TextPropertyElement condition;
  
  public WorkflowCaseConnection(Node source, Node target,
      Terminal sourceTerminal, Terminal targetTerminal) {
    super(source, target, sourceTerminal, targetTerminal);
    this.condition = new TextPropertyElement(PROP_CONN_CONDITION, "Condition");
  }
  
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if (CASE_CONNECTION_DESCRIPTORS == null) {
      CASE_CONNECTION_DESCRIPTORS = new IPropertyDescriptor[] {
          condition.getPropertyDescriptor()
      };
    }
    return Utils.combine(super.getPropertyDescriptors(),
                          CASE_CONNECTION_DESCRIPTORS);
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    switch ((String) propertyName) {
    case PROP_CONN_CONDITION:
      setCondition((String) propertyValue);
      break;
    default:
      super.setPropertyValue(propertyName, propertyValue);
      break;
    }
  }
  
  @Override
  public Object getPropertyValue(Object propertyName) {
    switch ((String) propertyName) {
    case PROP_CONN_CONDITION:
      return getCondition();
    default:
      return super.getPropertyValue(propertyName);
    }
  }
  
  public void setCondition(String condition) {
    String oldCondition = this.condition.getStringValue();
    this.condition.setStringValue(condition);
    firePropertyChange(PROP_CONN_CONDITION, oldCondition, condition);
  }
  
  public String getCondition() {
    return this.condition.getStringValue();
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj) || !(obj instanceof WorkflowCaseConnection)) {
      return false;
    }
    
    return this.getCondition().equals(
        ((WorkflowCaseConnection) obj).getCondition());
  }
  
}
