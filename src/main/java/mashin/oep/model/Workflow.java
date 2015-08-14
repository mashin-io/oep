package mashin.oep.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mashin.oep.model.node.Node;
import mashin.oep.model.node.control.StartNode;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class Workflow extends ModelElementWithSchema implements
    HPDLSerializable {

  private static final List<SchemaVersion> WORKFLOW_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2,
                    SchemaVersion.V_0_2_5, SchemaVersion.V_0_3,
                    SchemaVersion.V_0_4, SchemaVersion.V_0_4_5,
                    SchemaVersion.V_0_5);
  private static final SchemaVersion WORKFLOW_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_5;
  private static final SchemaVersion WORKFLOW_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_5;

  public static final String PROP_NAME           = "prop.workflow.name";
  public static final String PROP_PROPERTY       = "prop.workflow.property";
  public static final String PROP_JOBTRACKER     = "prop.workflow.global.job-tracker";
  public static final String PROP_NAMENDOE       = "prop.workflow.global.name-node";
  public static final String PROP_JOB_XML        = "prop.workflow.global.job-xml";
  public static final String PROP_CONFIGURATION  = "prop.workflow.global.configuration";
  public static final String PROP_CREDENTIALS    = "prop.workflow.credentials";
  
  public static final String PROP_NODE_ADDED     = "prop.workflow.node.added";
  public static final String PROP_NODE_REMOVED   = "prop.workflow.node.removed";

  private static final String CATEGORY_GLOBAL       = "Global";
  private static final String CATEGORY_CREDENTIALS  = "Credentials";

  private TextPropertyElement       name;
  private PropertyElementCollection parameters;
  private TextPropertyElement       jobtracker;
  private TextPropertyElement       namenode;
  private PropertyElementCollection jobxml;
  private PropertyElementCollection configuration;
  private PropertyElementCollection credentials;

  private StartNode   startNode;
  private List<Node>  nodes;
  
  public Workflow() {
    name = new TextPropertyElement(PROP_NAME, "Name");
    addPropertyElement(name);
    
    parameters = new PropertyElementCollection("Parameters", new PropertyPropertyElement(
                        PROP_PROPERTY, "Property"));
    addPropertyElement(parameters);
    
    jobtracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    jobtracker.setCategory(CATEGORY_GLOBAL);
    addPropertyElement(jobtracker);
    
    namenode = new TextPropertyElement(PROP_NAMENDOE, "Namenode");
    namenode.setCategory(CATEGORY_GLOBAL);
    addPropertyElement(namenode);
    
    jobxml = new PropertyElementCollection(CATEGORY_GLOBAL,
                      new TextPropertyElement(PROP_JOB_XML, "Job XML"));
    addPropertyElement(jobxml);
    
    configuration = new PropertyElementCollection(CATEGORY_GLOBAL,
                          new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
    
    credentials = new PropertyElementCollection(CATEGORY_CREDENTIALS,
                        new PropertyPropertyElement(PROP_CREDENTIALS, "Credential"));
    addPropertyElement(credentials);
  }

  public void init() {
    startNode = new StartNode(this);
    nodes = new ArrayList<Node>();
    nodes.add(startNode);
  }
  
  public void setName(String name) {
    setPropertyValue(PROP_NAME, name);
  }

  public String getName() {
    return name.getStringValue();
  }

  public void setJobtracker(String jobtracker) {
    setPropertyValue(PROP_JOBTRACKER, jobtracker);
  }

  public String getJobtracker() {
    return jobtracker.getStringValue();
  }

  public void setNamenode(String namenode) {
    setPropertyValue(PROP_NAMENDOE, namenode);
  }

  public String getNamenode() {
    return namenode.getStringValue();
  }

  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return WORKFLOW_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return WORKFLOW_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return WORKFLOW_LATEST_SCHEMA_VERSION;
  }

  public boolean canAddNode(Node node) {
    return true;
  }
  
  public void addNode(Node node) {
    nodes.add(node);
    firePropertyChange(PROP_NODE_ADDED, null, node);
  }
  
  public void removeNode(Node node) {
    nodes.remove(node);
    firePropertyChange(PROP_NODE_REMOVED, node, null);
  }
  
  public List<Node> getNodes() {
    return nodes;
  }
  
  @Override
  public String toHPDL() {
    return "";
  }

  @Override
  public void fromHPDL(String hpdl) {

  }

}
