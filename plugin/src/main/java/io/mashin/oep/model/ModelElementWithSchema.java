/**
 * Copyright (c) 2015 Mashin (http://mashin.io). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mashin.oep.model;

import io.mashin.oep.hpdl.HPDLModel;
import io.mashin.oep.hpdl.HPDLSerializable;
import io.mashin.oep.model.property.ComboBoxPropertyElement;

import java.util.List;


public abstract class ModelElementWithSchema extends ModelElement implements HPDLSerializable, Comparable<ModelElementWithSchema> {
  
  public static final String PROP_SCHEMA_VERSION = "prop.schema.version";
  
  protected ComboBoxPropertyElement schemaVersion;
  protected HPDLModel hpdlModel;
  
  public ModelElementWithSchema() {
    schemaVersion = new ComboBoxPropertyElement(PROP_SCHEMA_VERSION, "Schema Version");
    List<SchemaVersion> possibleSchemaVersions = getPossibleSchemaVersions();
    String[] labelsArray = new String[possibleSchemaVersions.size()];
    SchemaVersion[] valuesArray = new SchemaVersion[possibleSchemaVersions.size()];
    for (int i = 0; i < labelsArray.length; i++) {
      SchemaVersion sv = possibleSchemaVersions.get(i);
      labelsArray[i] = sv.version;
      valuesArray[i] = sv;
    }
    schemaVersion.setLabelsArray(labelsArray);
    schemaVersion.setValuesArray(valuesArray);
    schemaVersion.setContentValue(getDefaultSchemaVersion());
    schemaVersion.setEditable(isSchemaVersionEditable());
    addPropertyElement(schemaVersion);
    hpdlModel = new HPDLModel();
  }
  
  public void setSchemaVersion(String schemaVersion) {
    setPropertyValue(PROP_SCHEMA_VERSION, schemaVersionIndex(new SchemaVersion(schemaVersion)));
  }
  
  public void setSchemaVersion(SchemaVersion schemaVersion) {
    setPropertyValue(PROP_SCHEMA_VERSION, schemaVersionIndex(schemaVersion));
  }
  
  public SchemaVersion getSchemaVersion() {
    return (SchemaVersion) schemaVersion.getContentValue();
  }
  
  private Integer schemaVersionIndex(SchemaVersion schemaVersion) {
    return getPossibleSchemaVersions().indexOf(schemaVersion);
  }
  
  public abstract List<SchemaVersion> getPossibleSchemaVersions();
  public abstract SchemaVersion getDefaultSchemaVersion();
  public abstract SchemaVersion getLatestSchemaVersion();
  
  protected boolean isSchemaVersionEditable() { return true; }
  
  @Override
  public int compareTo(ModelElementWithSchema other) {
    return this.getSchemaVersion().compareTo(other.getSchemaVersion());
  }
  
}
