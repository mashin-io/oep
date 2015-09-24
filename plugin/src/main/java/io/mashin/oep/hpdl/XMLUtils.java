package io.mashin.oep.hpdl;

public class XMLUtils {

  public static final String XMLNS_TAG = "xmlns";
  public static final String SCHEMA_VERSION_TAG = "schema-version";
  
  public static String xmlnsToSchemaVersion(String xml) {
    return xml.replaceAll(XMLNS_TAG + "=", SCHEMA_VERSION_TAG + "=");
  }
  
  public static String schemaVersionToXmlns(String xml) {
    return xml.replaceAll(SCHEMA_VERSION_TAG + "=", XMLNS_TAG + "=");
  }
  
}
