package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.CheckBoxPropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.TextPropertyElement;

public class SSHActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> SSH_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2);
  private static final SchemaVersion SSH_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_2;
  private static final SchemaVersion SSH_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_2;
  
  public static final String PROP_HOST = "prop.node.ssh.host";
  public static final String PROP_COMMAND = "prop.node.ssh.command";
  public static final String PROP_ARGS = "prop.node.ssh.args";
  public static final String PROP_ARG = "prop.node.ssh.arg";
  public static final String PROP_CAPTURE_OUTPUT = "prop.node.ssh.capture-output";
  
  protected TextPropertyElement host;//host
  protected TextPropertyElement command;//command
  
  //choice
  protected PropertyElementCollection args;//args 0-unbounded
  protected PropertyElementCollection arg;//arg 0-unbounded
  
  protected CheckBoxPropertyElement captureOutput;//capture-output (flag/checkbox)
  
  public SSHActionNode(Workflow workflow) {
    super(workflow);
    
    host = new TextPropertyElement(PROP_HOST, "Host");
    addPropertyElement(host);
    
    command = new TextPropertyElement(PROP_COMMAND, "Command");
    addPropertyElement(command);
    
    args = new PropertyElementCollection("Args",
              new TextPropertyElement(PROP_ARGS, "Args"));
    addPropertyElement(args);
    
    arg = new PropertyElementCollection("Arg",
              new TextPropertyElement(PROP_ARG, "Arg"));
    addPropertyElement(arg);
    
    captureOutput = new CheckBoxPropertyElement(PROP_CAPTURE_OUTPUT, "Capture Output");
    
    setName("ssh-" + ID_SEQ.incrementAndGet());
  }

  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return SSH_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return SSH_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return SSH_LATEST_SCHEMA_VERSION;
  }
  
  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
