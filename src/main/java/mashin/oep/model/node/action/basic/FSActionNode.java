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
  public static final String PROP_OP_MKDIR = "prop.node.fs.op.mkdir";
  public static final String PROP_OP_MOVE = "prop.node.fs.op.move";
  public static final String PROP_OP_CHMOD = "prop.node.fs.op.chmod";
  public static final String PROP_OP_TOUCHZ = "prop.node.fs.op.touchz";
  public static final String PROP_OP_CHGRP = "prop.node.fs.op.chgrp";
  
  public static final String CATEGORY_OPERATIONS = "Operations";
  
  protected TextPropertyElement namenode;//name-node
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  
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
    
    configuration = new PropertyElementCollection("Configuration",
                      new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
    
    //operations
    delete = new PropertyElementCollection(CATEGORY_OPERATIONS,
                new FSOperationDelete(PROP_OP_DELETE, "Delete"));
    addPropertyElement(delete);
    
    mkdir = new PropertyElementCollection(CATEGORY_OPERATIONS,
              new FSOperationMkdir(PROP_OP_MKDIR, "Mkdir"));
    addPropertyElement(mkdir);
    
    move = new PropertyElementCollection(CATEGORY_OPERATIONS,
              new FSOperationMove(PROP_OP_MOVE, "Move"));
    addPropertyElement(move);
    
    chmod = new PropertyElementCollection(CATEGORY_OPERATIONS,
              new FSOperationChmod(PROP_OP_CHMOD, "Chmod"));
    addPropertyElement(chmod);
    
    touchz = new PropertyElementCollection(CATEGORY_OPERATIONS,
                new FSOperationTouchz(PROP_OP_TOUCHZ, "Touchz"));
    addPropertyElement(touchz);
    
    chgrp = new PropertyElementCollection(CATEGORY_OPERATIONS,
              new FSOperationChgrp(PROP_OP_CHGRP, "Chgrp"));
    addPropertyElement(chgrp);
    
    setName("fs-" + ID_SEQ.incrementAndGet());
  }

  public static class FSOperationDelete extends PropertyElementGroup {

    public FSOperationDelete(String id, String name) {
      super(id, name);
      this.propertyElements.add(new TextPropertyElement(id + ".path", "Path"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  public static class FSOperationMkdir extends PropertyElementGroup {

    public FSOperationMkdir(String id, String name) {
      super(id, name);
      this.propertyElements.add(new TextPropertyElement(id + ".path", "Path"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  public static class FSOperationMove extends PropertyElementGroup {
    
    public FSOperationMove(String id, String name) {
      super(id, name);
      this.propertyElements.add(new TextPropertyElement(id + ".source", "Source"));
      this.propertyElements.add(new TextPropertyElement(id + ".target", "Target"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  public static class FSOperationChmod extends PropertyElementGroup {
    
    public FSOperationChmod(String id, String name) {
      super(id, name);
      this.propertyElements.add(new CheckBoxPropertyElement(id + ".recursive", "Recursive"));
      this.propertyElements.add(new TextPropertyElement(id + ".path", "Path"));
      this.propertyElements.add(new TextPropertyElement(id + ".permissions", "Permissions"));
      this.propertyElements.add(new TextPropertyElement(id + ".dir-files", "Dir Files"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  public static class FSOperationTouchz extends PropertyElementGroup {
    
    public FSOperationTouchz(String id, String name) {
      super(id, name);
      this.propertyElements.add(new TextPropertyElement(id + ".path", "Path"));
    }

    @Override
    public void setComplexValue(Object value) {}
    
  }
  
  public static class FSOperationChgrp extends PropertyElementGroup {
    
    public FSOperationChgrp(String id, String name) {
      super(id, name);
      this.propertyElements.add(new CheckBoxPropertyElement(id + ".recursive", "Recursive"));
      this.propertyElements.add(new TextPropertyElement(id + ".path", "Path"));
      this.propertyElements.add(new TextPropertyElement(id + ".group", "Group"));
      this.propertyElements.add(new TextPropertyElement(id + ".dir-files", "Dir Files"));
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
