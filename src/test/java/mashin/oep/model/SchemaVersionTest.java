package mashin.oep.model;

import mashin.oep.model.SchemaVersion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SchemaVersionTest {
  @Test
  public void testCompareTo() {
    SchemaVersion schemaVersion1 = new SchemaVersion();
    SchemaVersion schemaVersion2 = new SchemaVersion();
    
    schemaVersion1.version = "1.0";
    schemaVersion2.version = "1.0";
    assertEquals(0, schemaVersion1.compareTo(schemaVersion2));
    
    schemaVersion1.version = "1.0";
    schemaVersion2.version = "1.0.0";
    assertEquals(0, schemaVersion1.compareTo(schemaVersion2));
    
    schemaVersion1.version = "1.0";
    schemaVersion2.version = "2.0";
    assertTrue(schemaVersion1.compareTo(schemaVersion2) < 0);
    assertTrue(schemaVersion2.compareTo(schemaVersion1) > 0);
    
    schemaVersion1.version = "0.1";
    schemaVersion2.version = "0.2";
    assertTrue(schemaVersion1.compareTo(schemaVersion2) < 0);
    assertTrue(schemaVersion2.compareTo(schemaVersion1) > 0);
    
    schemaVersion1.version = "0.2";
    schemaVersion2.version = "0.2.5";
    assertTrue(schemaVersion1.compareTo(schemaVersion2) < 0);
    assertTrue(schemaVersion2.compareTo(schemaVersion1) > 0);
  }
}
