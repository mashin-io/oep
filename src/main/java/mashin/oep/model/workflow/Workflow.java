package mashin.oep.model.workflow;

import java.util.Arrays;
import java.util.List;

import mashin.oep.Utils;
import mashin.oep.model.HPDLSerializable;
import mashin.oep.model.ModelElementWithSchema;
import mashin.oep.model.SchemaVersion;
import mashin.oep.model.property.PropertyElementCollection;
import mashin.oep.model.property.TextPropertyElement;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class Workflow extends ModelElementWithSchema implements
    HPDLSerializable {

  private static final List<SchemaVersion> WORKFLOW_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2,
                    SchemaVersion.V_0_2_5, SchemaVersion.V_0_3,
                    SchemaVersion.V_0_4, SchemaVersion.V_0_4_5,
                    SchemaVersion.V_0_5);
  private static final SchemaVersion WORKFLOW_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_5;
  private static final SchemaVersion WORKFLOW_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_5;

  private static final String PROP_NAME           = "prop.workflow.name";
  private static final String PROP_PROPERTY       = "prop.workflow.property";
  private static final String PROP_JOBTRACKER     = "prop.workflow.global.job-tracker";
  private static final String PROP_NAMENDOE       = "prop.workflow.global.name-node";
  private static final String PROP_JOB_XML        = "prop.workflow.global.job-xml";
  private static final String PROP_CONFIGURATION  = "prop.workflow.global.configuration";
  private static final String PROP_CREDENTIALS    = "prop.workflow.credentials";

  private static final String CATEGORY_GLOBAL       = "Global";
  private static final String CATEGORY_CREDENTIALS  = "Credentials";

  private static IPropertyDescriptor[] WORKFLOW_PROPERTY_DESCRIPTORS;

  private TextPropertyElement       name;
  private PropertyElementCollection parameters;
  private TextPropertyElement       jobtracker;
  private TextPropertyElement       namenode;
  private PropertyElementCollection jobxml;
  private PropertyElementCollection configuration;
  private PropertyElementCollection credentials;

  public Workflow() {
    name          = new TextPropertyElement(PROP_NAME, "Name");
    parameters    = new PropertyElementCollection("Parameters", new Property(
                        PROP_PROPERTY, "Property"));
    jobtracker    = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    namenode      = new TextPropertyElement(PROP_NAMENDOE, "Namenode");
    jobxml        = new PropertyElementCollection(CATEGORY_GLOBAL,
                        new TextPropertyElement(PROP_JOB_XML, "Job XML"));
    configuration = new PropertyElementCollection(CATEGORY_GLOBAL,
                        new Property(PROP_CONFIGURATION, "Configuration"));
    credentials   = new PropertyElementCollection(CATEGORY_CREDENTIALS,
                        new Property(PROP_CREDENTIALS, "Credential"));
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if (WORKFLOW_PROPERTY_DESCRIPTORS == null) {
      WORKFLOW_PROPERTY_DESCRIPTORS = new IPropertyDescriptor[3];
      WORKFLOW_PROPERTY_DESCRIPTORS[0] = name.getPropertyDescriptor();
      WORKFLOW_PROPERTY_DESCRIPTORS[1] = jobtracker.getPropertyDescriptor();
      WORKFLOW_PROPERTY_DESCRIPTORS[2] = namenode.getPropertyDescriptor();

      ((PropertyDescriptor) WORKFLOW_PROPERTY_DESCRIPTORS[1])
          .setCategory(CATEGORY_GLOBAL);
      ((PropertyDescriptor) WORKFLOW_PROPERTY_DESCRIPTORS[2])
          .setCategory(CATEGORY_GLOBAL);
    }
    return Utils
        .combine(super.getPropertyDescriptors(),
            WORKFLOW_PROPERTY_DESCRIPTORS,
            parameters.getPropertyDescriptors(),
            jobxml.getPropertyDescriptors(),
            configuration.getPropertyDescriptors(),
            credentials.getPropertyDescriptors());
  }

  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    String propertyNameStr = (String) propertyName;
    switch (propertyNameStr) {
    case PROP_NAME:
      setName((String) propertyValue);
      break;
    case PROP_JOBTRACKER:
      setJobtracker((String) propertyValue);
      break;
    case PROP_NAMENDOE:
      setNamenode((String) propertyValue);
      break;
    default:
      if (parameters.hasProperty(propertyNameStr)) {
        Object oldValue = parameters.getPropertyValue(propertyNameStr);
        parameters.setPropertyValue(propertyNameStr, propertyValue);
        firePropertyChange(propertyNameStr, oldValue, propertyValue);
      } else if (jobxml.hasProperty(propertyNameStr)) {
        Object oldValue = jobxml.getPropertyValue(propertyNameStr);
        jobxml.setPropertyValue(propertyNameStr, propertyValue);
        firePropertyChange(propertyNameStr, oldValue, propertyValue);
      } else if (configuration.hasProperty(propertyNameStr)) {
        Object oldValue = configuration.getPropertyValue(propertyNameStr);
        configuration.setPropertyValue(propertyNameStr, propertyValue);
        firePropertyChange(propertyNameStr, oldValue, propertyValue);
      } else if (credentials.hasProperty(propertyNameStr)) {
        Object oldValue = credentials.getPropertyValue(propertyNameStr);
        credentials.setPropertyValue(propertyNameStr, propertyValue);
        firePropertyChange(propertyNameStr, oldValue, propertyValue);
      } else {
        super.setPropertyValue(propertyName, propertyValue);
      }
      break;
    }
  }

  @Override
  public Object getPropertyValue(Object propertyName) {
    String propertyNameStr = (String) propertyName;
    switch (propertyNameStr) {
    case PROP_NAME:
      return getName();
    case PROP_JOBTRACKER:
      return getJobtracker();
    case PROP_NAMENDOE:
      return getNamenode();
    default:
      if (parameters.hasProperty(propertyNameStr)) {
        return parameters.getPropertyValue(propertyNameStr);
      } else if (jobxml.hasProperty(propertyNameStr)) {
        return jobxml.getPropertyValue(propertyNameStr);
      } else if (configuration.hasProperty(propertyNameStr)) {
        return configuration.getPropertyValue(propertyNameStr);
      } else if (credentials.hasProperty(propertyNameStr)) {
        return credentials.getPropertyValue(propertyNameStr);
      } else {
        return super.getPropertyValue(propertyName);
      }
    }
  }

  public void setName(String name) {
    String oldName = (String) this.name.getValue();
    this.name.setValue(name);
    firePropertyChange(PROP_NAME, oldName, name);
  }

  public String getName() {
    return (String) name.getValue();
  }

  public void setJobtracker(String jobtracker) {
    String oldJobtracker = (String) this.jobtracker.getValue();
    this.jobtracker.setValue(jobtracker);
    firePropertyChange(PROP_JOBTRACKER, oldJobtracker, jobtracker);
  }

  public String getJobtracker() {
    return (String) jobtracker.getValue();
  }

  public void setNamenode(String namenode) {
    String oldNamenode = (String) this.namenode.getValue();
    this.namenode.setValue(namenode);
    firePropertyChange(PROP_NAMENDOE, oldNamenode, namenode);
  }

  public String getNamenode() {
    return (String) namenode.getValue();
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

  @Override
  public String toHPDL() {
    return "";
  }

  @Override
  public void fromHPDL(String hpdl) {

  }

}
