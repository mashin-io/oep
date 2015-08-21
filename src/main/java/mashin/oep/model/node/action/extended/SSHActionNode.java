package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.XMLUtils;
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
    this(workflow, null);
  }
  
  public SSHActionNode(Workflow workflow, Node hpdlNode) {
    super(workflow, hpdlNode);
    
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
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("ssh-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element parentNode) {
    
  }

  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLUtils.initTextPropertyFrom(host, hpdlNode, "./ssh/host");
    XMLUtils.initTextPropertyFrom(command, hpdlNode, "./ssh/command");
    XMLUtils.initTextCollectionFrom(args, hpdlNode, "./ssh/args");
    XMLUtils.initTextCollectionFrom(arg, hpdlNode, "./ssh/arg");
    captureOutput.setValue(!XMLUtils.valueOf("./ssh/capture-output", hpdlNode).isEmpty());
  }
  
  @Override
  public String getNodeType() {
    return "ssh";
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
  
}
