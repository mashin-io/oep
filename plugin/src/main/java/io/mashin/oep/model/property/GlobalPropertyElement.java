package io.mashin.oep.model.property;

public class GlobalPropertyElement extends PropertyElementGroup {

  public TextPropertyElement jobtracker;
  public TextPropertyElement namenode;
  public PropertyElementCollection jobxml;
  public PropertyElementCollection configuration;
  
  String jobtrackerId, namenodeId, jobxmlId, configurationId;
  
  public GlobalPropertyElement(String id, String name) {
    super(id, name);
    
    jobtrackerId = id + ".job-tracker";
    namenodeId = id + ".name-node";
    jobxmlId = id + ".job-xml";
    configurationId = id + ".configuration";
    
    jobtracker = new TextPropertyElement(jobtrackerId, "Jobtracker");
    jobtracker.setCategory(name);
    
    namenode = new TextPropertyElement(namenodeId, "Namenode");
    namenode.setCategory(name);
    
    jobxml = new PropertyElementCollection(name,
        new TextPropertyElement(jobxmlId, "Job XML"));
    
    configuration = new PropertyElementCollection(name,
        new PropertyPropertyElement(configurationId, "Configuration"));
    
    propertyElements.add(jobtracker);
    propertyElements.add(namenode);
    propertyElements.add(jobxml);
    propertyElements.add(configuration);
  }

  @Override
  public void setComplexValue(Object value) { }
  
  public void setValueOfJobtracker(String jobtracker) {
    setValue(jobtrackerId, jobtracker);
  }
  
  public void setValueOfNamenode(String namenode) {
    setValue(namenodeId, namenode);
  }
  
  public void setValueOfJobxml(PropertyElementCollection jobxml) {
    setValue(jobxmlId, jobxml);
  }
  
  public void setValueOfConfiguration(PropertyElementCollection configuration) {
    setValue(configurationId, configuration);
  }
  
}
