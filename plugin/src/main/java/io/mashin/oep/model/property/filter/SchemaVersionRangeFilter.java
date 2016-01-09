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

package io.mashin.oep.model.property.filter;

import io.mashin.oep.model.ModelElementWithSchema;
import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.property.PropertyElement;

public class SchemaVersionRangeFilter implements PropertyFilter {

  private SchemaVersion lowerBound;
  private SchemaVersion upperBound;
  private ModelElementWithSchema modelElementWithSchema;
  
  public SchemaVersionRangeFilter(SchemaVersion lowerBound,
      SchemaVersion upperBound, ModelElementWithSchema modelElementWithSchema) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.modelElementWithSchema = modelElementWithSchema;
  }
  
  @Override
  public boolean filter(PropertyElement propertyElement) {
    SchemaVersion schemaVersion = modelElementWithSchema.getSchemaVersion();
    return schemaVersion.isGreaterThanOrEqual(lowerBound)
        && schemaVersion.isLessThanOrEqual(upperBound);
  }

}
