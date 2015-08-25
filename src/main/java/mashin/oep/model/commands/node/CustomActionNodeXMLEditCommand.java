package mashin.oep.model.commands.node;

import mashin.oep.model.node.action.extended.CustomActionNode;

import org.eclipse.gef.commands.Command;

public class CustomActionNodeXMLEditCommand extends Command {

  private CustomActionNode customActionNode;
  private String oldXmlContent;
  private String newXmlContent;
  
  public CustomActionNodeXMLEditCommand(CustomActionNode customActionNode, String xmlContent) {
    super("custom action node XML edit");
    this.customActionNode = customActionNode;
    oldXmlContent = (String) customActionNode.getPropertyValue(CustomActionNode.PROP_XML);
    newXmlContent = xmlContent;
  }
  
  @Override
  public boolean canExecute() {
    return !oldXmlContent.equals(newXmlContent);
  }
  
  @Override
  public void execute() {
    customActionNode.setPropertyValue(CustomActionNode.PROP_XML, newXmlContent);
  }
  
  @Override
  public void undo() {
    customActionNode.setPropertyValue(CustomActionNode.PROP_XML, oldXmlContent);
  }
  
}
