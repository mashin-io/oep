package mashin.oep.model.node.action.basic;

import org.dom4j.Element;
import org.dom4j.Node;

import mashin.oep.hpdl.XMLReadUtils;
import mashin.oep.hpdl.XMLWriteUtils;
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
    this(workflow, null);
  }

  public FSActionNode(Workflow workflow, org.dom4j.Node hpdlNode) {
    super(workflow, hpdlNode);
    
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
  }
  
  @Override
  public void initDefaults() {
    super.initDefaults();
    setName("fs-" + ID_SEQ.incrementAndGet());
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element fs = element.addElement("fs");
    
    XMLWriteUtils.writeTextPropertyAsElement(namenode, fs, "name-node");
    XMLWriteUtils.writeTextCollectionAsElements(jobXML, fs, "job-xml");
    XMLWriteUtils.writePropertiesCollection(jobXML, fs, "configuration", "property");
    XMLWriteUtils.writeFSDeleteCollection(delete, fs);
    XMLWriteUtils.writeFSMkdirCollection(mkdir, fs);
    XMLWriteUtils.writeFSMoveCollection(move, fs);
    XMLWriteUtils.writeFSChmodCollection(chmod, fs);
    XMLWriteUtils.writeFSTouchzCollection(touchz, fs);
    XMLWriteUtils.writeFSChgrpCollection(chgrp, fs);
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    XMLReadUtils.initTextPropertyFrom(namenode, hpdlNode, "./fs/name-node");
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./fs/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./fs/configuration", "./property");
    XMLReadUtils.initFSDeleteCollectionFrom(delete, hpdlNode, "./fs/delete");
    XMLReadUtils.initFSMkdirCollectionFrom(mkdir, hpdlNode, "./fs/mkdir");
    XMLReadUtils.initFSMoveCollectionFrom(move, hpdlNode, "./fs/move");
    XMLReadUtils.initFSChmodCollectionFrom(chmod, hpdlNode, "./fs/chmod");
    XMLReadUtils.initFSTouchzCollectionFrom(touchz, hpdlNode, "./fs/touchz");
    XMLReadUtils.initFSChgrpCollectionFrom(chgrp, hpdlNode, "./fs/chgrp");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_FS;
  }
  
  public void setNamenode(String namenode) {
    setPropertyValue(PROP_NAMENODE, namenode);
  }
  
  public String getNamenode() {
    return namenode.getStringValue();
  }
  
  public static class FSOperationDelete extends PropertyElementGroup {

    public TextPropertyElement path;
    
    String pathId;
    
    public FSOperationDelete(String id, String name) {
      super(id, name);
      pathId = id + ".path";
      path = new TextPropertyElement(pathId, "Path");
      this.propertyElements.add(path);
    }

    @Override
    public void setComplexValue(Object value) {}
    
    public void setValueOfPath(String path) {
      setValue(pathId, path);
    }
    
    public String getValueOfPath() {
      return (String) getValue(pathId);
    }
    
  }
  
  public static class FSOperationMkdir extends PropertyElementGroup {

    public TextPropertyElement path;
    
    String pathId;
    
    public FSOperationMkdir(String id, String name) {
      super(id, name);
      pathId = id + ".path";
      path = new TextPropertyElement(pathId, "Path");
      this.propertyElements.add(path);
    }

    @Override
    public void setComplexValue(Object value) {}
    
    public void setValueOfPath(String path) {
      setValue(pathId, path);
    }
    
    public String getValueOfPath() {
      return (String) getValue(pathId);
    }
    
  }
  
  public static class FSOperationMove extends PropertyElementGroup {
    
    public TextPropertyElement source;
    public TextPropertyElement target;
    
    String sourceId, targetId;
    
    public FSOperationMove(String id, String name) {
      super(id, name);
      
      sourceId = id + ".source";
      targetId = id + ".target";
      
      source = new TextPropertyElement(sourceId, "Source");
      target = new TextPropertyElement(targetId, "Target");
      
      this.propertyElements.add(source);
      this.propertyElements.add(target);
    }

    @Override
    public void setComplexValue(Object value) {}
    
    public void setValueOfSource(String source) {
      setValue(sourceId, source);
    }
    
    public String getValueOfSource() {
      return (String) getValue(sourceId);
    }
    
    public void setValueOfTarget(String target) {
      setValue(targetId, target);
    }
    
    public String getValueOfTarget() {
      return (String) getValue(targetId);
    }
    
  }
  
  public static class FSOperationChmod extends PropertyElementGroup {
    
    public CheckBoxPropertyElement recursive;
    public TextPropertyElement path;
    public TextPropertyElement permissions;
    public TextPropertyElement dirFiles;
    
    String recursiveId, pathId, permissionsId, dirFilesId;
    
    public FSOperationChmod(String id, String name) {
      super(id, name);
      
      recursiveId = id + ".recursive";
      pathId = id + ".path";
      permissionsId = id + ".permissions";
      dirFilesId = id + ".dir-files";
      
      recursive = new CheckBoxPropertyElement(recursiveId, "Recursive");
      path = new TextPropertyElement(pathId, "Path");
      permissions = new TextPropertyElement(permissionsId, "Permissions");
      dirFiles = new TextPropertyElement(dirFilesId, "Dir Files");
      
      this.propertyElements.add(recursive);
      this.propertyElements.add(path);
      this.propertyElements.add(permissions);
      this.propertyElements.add(dirFiles);
    }

    @Override
    public void setComplexValue(Object value) {}
    
    public void setValueOfRecursive(Boolean recursive) {
      setValue(recursiveId, recursive);
    }
    
    public Boolean getValueOfRecursive() {
      return (Boolean) getValue(recursiveId);
    }
    
    public void setValueOfPath(String path) {
      setValue(pathId, path);
    }
    
    public String getValueOfPath() {
      return (String) getValue(pathId);
    }
    
    public void setValueOfPermissions(String permissions) {
      setValue(permissionsId, permissions);
    }

    public String getValueOfPermissions() {
      return (String) getValue(permissionsId);
    }
    
    public void setValueOfDirFiles(String dirFiles) {
      setValue(dirFilesId, dirFiles);
    }
    
    public String getValueOfDirFiles() {
      return (String) getValue(dirFilesId);
    }
    
  }
  
  public static class FSOperationTouchz extends PropertyElementGroup {
    
    public TextPropertyElement path;
    
    String pathId;
    
    public FSOperationTouchz(String id, String name) {
      super(id, name);
      pathId = id + ".path";
      path = new TextPropertyElement(pathId, "Path");
      this.propertyElements.add(path);
    }

    @Override
    public void setComplexValue(Object value) {}
    
    public void setValueOfPath(String path) {
      setValue(pathId, path);
    }
    
    public String getValueOfPath() {
      return (String) getValue(pathId);
    }
    
  }
  
  public static class FSOperationChgrp extends PropertyElementGroup {
    
    public CheckBoxPropertyElement recursive;
    public TextPropertyElement path;
    public TextPropertyElement group;
    public TextPropertyElement dirFiles;
    
    String recursiveId, pathId, groupId, dirFilesId;
    
    public FSOperationChgrp(String id, String name) {
      super(id, name);
      
      recursiveId = id + ".recursive";
      pathId = id + ".path";
      groupId = id + ".group";
      dirFilesId = id + ".dir-files";
      
      recursive = new CheckBoxPropertyElement(recursiveId, "Recursive");
      path = new TextPropertyElement(pathId, "Path");
      group = new TextPropertyElement(groupId, "Group");
      dirFiles = new TextPropertyElement(dirFilesId, "Dir Files");
      
      this.propertyElements.add(recursive);
      this.propertyElements.add(path);
      this.propertyElements.add(group);
      this.propertyElements.add(dirFiles);
    }

    @Override
    public void setComplexValue(Object value) {}
    
    public void setValueOfRecursive(Boolean recursive) {
      setValue(recursiveId, recursive);
    }
    
    public Boolean getValueOfRecursive() {
      return (Boolean) getValue(recursiveId);
    }
    
    public void setValueOfPath(String path) {
      setValue(pathId, path);
    }
    
    public String getValueOfPath() {
      return (String) getValue(pathId);
    }
    
    public void setValueOfGroup(String group) {
      setValue(groupId, group);
    }

    public String getValueOfGroup() {
      return (String) getValue(groupId);
    }
    
    public void setValueOfDirFiles(String dirFiles) {
      setValue(dirFilesId, dirFiles);
    }
    
    public String getValueOfDirFiles() {
      return (String) getValue(dirFilesId);
    }
    
  }
  
}
