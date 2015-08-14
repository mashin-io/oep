package mashin.oep.model.node.action.basic;

import mashin.oep.model.Workflow;
import mashin.oep.model.property.CheckBoxPropertyElement;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.PropertyElementGroup;
import mashin.oep.model.property.PropertyPropertyElement;
import mashin.oep.model.property.TextPropertyElement;

public class FSActionNode extends BasicActionNode {

  public static final String PROP_NAMENODE = "prop.node.fs.name-node";
  public static final String PROP_JOBXML = "prop.node.fs.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.fs.configuration";
  public static final String PROP_OP_DELETE = "prop.node.fs.op.delete";
  public static final String PROP_OP_DELETE_PATH = "prop.node.fs.op.delete.path";
  public static final String PROP_OP_MKDIR = "prop.node.fs.op.mkdir";
  public static final String PROP_OP_MKDIR_PATH = "prop.node.fs.op.mkdir.path";
  public static final String PROP_OP_MOVE = "prop.node.fs.op.move";
  public static final String PROP_OP_MOVE_SOURCE = "prop.node.fs.op.move.source";
  public static final String PROP_OP_MOVE_TARGET = "prop.node.fs.op.move.target";
  public static final String PROP_OP_CHMOD = "prop.node.fs.op.chmod";
  public static final String PROP_OP_CHMOD_RECURSIVE = "prop.node.fs.op.chmod.recursive";
  public static final String PROP_OP_CHMOD_PATH = "prop.node.fs.op.chmod.path";
  public static final String PROP_OP_CHMOD_PERMISSIONS = "prop.node.fs.op.chmod.permissions";
  public static final String PROP_OP_CHMOD_DIRFILES = "prop.node.fs.op.chmod.dir-files";
  public static final String PROP_OP_TOUCHZ = "prop.node.fs.op.touchz";
  public static final String PROP_OP_TOUCHZ_PATH = "prop.node.fs.op.touchz.path";
  public static final String PROP_OP_CHGRP = "prop.node.fs.op.chgrp";
  public static final String PROP_OP_CHGRP_RECURSIVE = "prop.node.fs.op.chgrp.recursive";
  public static final String PROP_OP_CHGRP_PATH = "prop.node.fs.op.chgrp.path";
  public static final String PROP_OP_CHGRP_GROUP = "prop.node.fs.op.chgrp.group";
  public static final String PROP_OP_CHGRP_DIRFILES = "prop.node.fs.op.chgrp.dir-files";
  
  public static final String CATEGORY_OPERATIONS = "Operations";
  
  protected TextPropertyElement namenode;//name-node
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyPropertyElement configuration;//configuration
  
  //operations
  protected PropertyElementCollection delete;
  protected PropertyElementCollection mkdir;
  protected PropertyElementCollection move;
  protected PropertyElementCollection chmod;
  protected PropertyElementCollection touchz;
  protected PropertyElementCollection chgrp;
  
  public FSActionNode(Workflow workflow) {
    super(workflow);
    
    namenode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(namenode);
    
    jobXML = new PropertyElementCollection("Job XML", new TextPropertyElement(PROP_JOBXML, "Job XML"));
    addPropertyElement(jobXML);
    
    configuration = new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration");
    addPropertyElement(configuration);
    
    //operations
    delete = new PropertyElementCollection(CATEGORY_OPERATIONS, new OperationDelete(PROP_OP_DELETE, "Delete"));
    addPropertyElement(delete);
    
    mkdir = new PropertyElementCollection(CATEGORY_OPERATIONS, new OperationMkdir(PROP_OP_MKDIR, "Mkdir"));
    addPropertyElement(mkdir);
    
    move = new PropertyElementCollection(CATEGORY_OPERATIONS, new OperationMkdir(PROP_OP_MOVE, "Move"));
    addPropertyElement(move);
    
    chmod = new PropertyElementCollection(CATEGORY_OPERATIONS, new OperationMkdir(PROP_OP_CHMOD, "Chmod"));
    addPropertyElement(chmod);
    
    touchz = new PropertyElementCollection(CATEGORY_OPERATIONS, new OperationMkdir(PROP_OP_TOUCHZ, "touchz"));
    addPropertyElement(touchz);
    
    chgrp = new PropertyElementCollection(CATEGORY_OPERATIONS, new OperationMkdir(PROP_OP_CHGRP, "chgrp"));
    addPropertyElement(chgrp);
    
  }

  class OperationDelete extends PropertyElementGroup {

    public OperationDelete(String id, String name) {
      super(id, name);
      propertyElements.add(new TextPropertyElement(PROP_OP_DELETE_PATH, "Path"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  class OperationMkdir extends PropertyElementGroup {

    public OperationMkdir(String id, String name) {
      super(id, name);
      propertyElements.add(new TextPropertyElement(PROP_OP_MKDIR_PATH, "Path"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  class OperationMove extends PropertyElementGroup {
    
    public OperationMove(String id, String name) {
      super(id, name);
      propertyElements.add(new TextPropertyElement(PROP_OP_MOVE_SOURCE, "Source"));
      propertyElements.add(new TextPropertyElement(PROP_OP_MOVE_TARGET, "Target"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  class OperationChmod extends PropertyElementGroup {
    
    public OperationChmod(String id, String name) {
      super(id, name);
      propertyElements.add(new CheckBoxPropertyElement(PROP_OP_CHMOD_RECURSIVE, "Recursive"));
      propertyElements.add(new TextPropertyElement(PROP_OP_CHMOD_PATH, "Path"));
      propertyElements.add(new TextPropertyElement(PROP_OP_CHMOD_PERMISSIONS, "Permissions"));
      propertyElements.add(new TextPropertyElement(PROP_OP_CHMOD_DIRFILES, "Dir Files"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  class OperationTouchz extends PropertyElementGroup {
    
    public OperationTouchz(String id, String name) {
      super(id, name);
      propertyElements.add(new TextPropertyElement(PROP_OP_TOUCHZ_PATH, "Path"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  class OperationChgrp extends PropertyElementGroup {
    
    public OperationChgrp(String id, String name) {
      super(id, name);
      propertyElements.add(new CheckBoxPropertyElement(PROP_OP_CHGRP_RECURSIVE, "Recursive"));
      propertyElements.add(new TextPropertyElement(PROP_OP_CHGRP_PATH, "Path"));
      propertyElements.add(new TextPropertyElement(PROP_OP_CHGRP_GROUP, "Group"));
      propertyElements.add(new TextPropertyElement(PROP_OP_CHGRP_DIRFILES, "Dir Files"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
