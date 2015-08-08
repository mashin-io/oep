package mashin.oep.model;

import java.util.List;

import mashin.oep.Utils;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;


public abstract class ModelElementWithSchema extends ModelElement implements Comparable<ModelElementWithSchema> {
  
  public static final String PROP_SCHEMA_VERSION = "prop.schema.version";
  
  protected SchemaVersion schemaVersion;
  private IPropertyDescriptor[] modelElementWithSchemaDescriptors;
  
  public ModelElementWithSchema() {
    this.schemaVersion = new SchemaVersion(getDefaultSchemaVersion().version);
  }
  
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if(isSchemaVersionEditable()) {
      if(modelElementWithSchemaDescriptors == null) {
        List<SchemaVersion> possibleSchemaVersions = getPossibleSchemaVersions();
        
        String[] labelsArray = new String[possibleSchemaVersions.size()];
        for (int i = 0; i < labelsArray.length; i++) {
          labelsArray[i] = possibleSchemaVersions.get(i).version;
        }
        
        this.modelElementWithSchemaDescriptors = Utils.combine(
            super.getPropertyDescriptors(),
            new IPropertyDescriptor[] { new ComboBoxPropertyDescriptor(
                PROP_SCHEMA_VERSION, "Schema Version", labelsArray) });
      }
      return this.modelElementWithSchemaDescriptors;
    } else {
      return super.getPropertyDescriptors();
    }
  }
  
  @Override
  public void setPropertyValue(Object propertyName, Object propertyValue) {
    switch((String) propertyName) {
    case PROP_SCHEMA_VERSION:
      setSchemaVersion(schemaVersionOfIndex((Integer) propertyValue)); 
      break;
    default:
      super.setPropertyValue(propertyName, propertyValue);
      break;
    }
  }
  
  @Override
  public Object getPropertyValue(Object propertyName) {
    switch((String) propertyName) {
    case PROP_SCHEMA_VERSION:
      return schemaVersionIndex(getSchemaVersion());
    default: 
      return super.getPropertyValue(propertyName);
    }
  }
  
  public void setSchemaVersion(String schemaVersion) {
    Integer oldSchemaVersionIndex = schemaVersionIndex(this.schemaVersion);
    this.schemaVersion.version = schemaVersion;
    Integer newSchemaVersionIndex = schemaVersionIndex(this.schemaVersion);
    firePropertyChange(PROP_SCHEMA_VERSION, oldSchemaVersionIndex, newSchemaVersionIndex);
  }
  
  public void setSchemaVersion(SchemaVersion schemaVersion) {
    Integer oldSchemaVersionIndex = schemaVersionIndex(this.schemaVersion);
    this.schemaVersion.version = schemaVersion.version;
    Integer newSchemaVersionIndex = schemaVersionIndex(this.schemaVersion);
    firePropertyChange(PROP_SCHEMA_VERSION, oldSchemaVersionIndex, newSchemaVersionIndex);
  }
  
  public SchemaVersion getSchemaVersion() {
    return this.schemaVersion;
  }
  
  private Integer schemaVersionIndex(SchemaVersion schemaVersion) {
    return getPossibleSchemaVersions().indexOf(schemaVersion);
  }
  
  private SchemaVersion schemaVersionOfIndex(Integer schemaVersionIndex) {
    return getPossibleSchemaVersions().get(schemaVersionIndex);
  }
  
  public abstract List<SchemaVersion> getPossibleSchemaVersions();
  public abstract SchemaVersion getDefaultSchemaVersion();
  public abstract SchemaVersion getLatestSchemaVersion();
  
  protected boolean isSchemaVersionEditable() { return true; }
  
  @Override
  public int compareTo(ModelElementWithSchema other) {
    return this.schemaVersion.compareTo(other.schemaVersion);
  }
  
}
