package mashin.oep.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelElementWithSchemaTest {

  @Test
  public void test() {
    Workflow workflow = new Workflow();
    Integer schemaVersionIndex1 = (Integer) workflow.getPropertyValue(Workflow.PROP_SCHEMA_VERSION);
    Integer schemaVersionIndex2 = workflow.getPossibleSchemaVersions().indexOf(workflow.getDefaultSchemaVersion());
    assertEquals(schemaVersionIndex2, schemaVersionIndex1);
    
    schemaVersionIndex1 = 1;
    workflow.setPropertyValue(Workflow.PROP_SCHEMA_VERSION, schemaVersionIndex1);
    schemaVersionIndex2 = workflow.getPossibleSchemaVersions().indexOf(workflow.getSchemaVersion());
    assertEquals(schemaVersionIndex2, schemaVersionIndex1);
  }
  
}
