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
