package io.mashin.oep.model.node.action.extended;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.property.CheckBoxPropertyElement;
import io.mashin.oep.model.property.PropertyElementCollection;
import io.mashin.oep.model.property.TextPropertyElement;
import io.mashin.oep.model.property.filter.PropertyFilter;
import io.mashin.oep.model.property.filter.SchemaVersionRangeFilter;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

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
              new TextPropertyElement(PROP_ARGS, "Args"),
              ((PropertyFilter) pe -> !arg.isSet() || (arg.isSet() && args.isSet()))
              .or(new SchemaVersionRangeFilter(SchemaVersion.V_0_1, SchemaVersion.V_0_1, this)));
    addPropertyElement(args);
    
    arg = new PropertyElementCollection("Arg",
              new TextPropertyElement(PROP_ARG, "Arg"),
              ((PropertyFilter) pe -> !args.isSet())
              .and(new SchemaVersionRangeFilter(SchemaVersion.V_0_2, SchemaVersion.V_ANY, this)));
    addPropertyElement(arg);
    
    captureOutput = new CheckBoxPropertyElement(PROP_CAPTURE_OUTPUT, "Capture Output");
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("ssh-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element ssh = element.addElement("ssh");
    
    XMLWriteUtils.writeSchemaVersion(getSchemaVersion(), ssh, getNodeType());
    XMLWriteUtils.writeTextPropertyAsElement(host, ssh, "host");
    XMLWriteUtils.writeTextPropertyAsElement(command, ssh, "command");
    XMLWriteUtils.writeTextCollectionAsElements(args, ssh, "args");
    XMLWriteUtils.writeTextCollectionAsElements(arg, ssh, "arg");
    XMLWriteUtils.writeCheckPropertyAsElement(captureOutput, ssh, "capture-output");
  }

  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(host, hpdlNode, "./ssh/host");
    XMLReadUtils.initTextPropertyFrom(command, hpdlNode, "./ssh/command");
    XMLReadUtils.initTextCollectionFrom(args, hpdlNode, "./ssh/args");
    XMLReadUtils.initTextCollectionFrom(arg, hpdlNode, "./ssh/arg");
    XMLReadUtils.initCheckPropertyFrom(captureOutput, hpdlNode, "./ssh/capture-output");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_SSH;
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
