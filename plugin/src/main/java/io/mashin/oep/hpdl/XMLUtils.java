package io.mashin.oep.hpdl;

public class XMLUtils {

  public static final String XMLNS_TAG = "xmlns";
  public static final String SCHEMA_VERSION_TAG = "schema-version";
  public static final String XMLNS_SLA_TAG = "xmlns:sla";
  public static final String SLA_VERSION_TAG = "sla-version";
  
  public static String xmlnsToSchemaVersion(String xml) {
    return xml.replaceAll(XMLNS_TAG + "=", SCHEMA_VERSION_TAG + "=");
  }
  
  public static String schemaVersionToXmlns(String xml) {
    return xml.replaceAll(SCHEMA_VERSION_TAG + "=", XMLNS_TAG + "=");
  }
  
  //public static String xmlnsSLAToSLAVersion(String xml) {
  //  return xml.replaceAll(XMLNS_SLA_TAG + "=", SLA_VERSION_TAG + "=");
  //}
  
  //public static String slaVersionToXmlnsSLA(String xml) {
  //  return xml.replaceAll(SLA_VERSION_TAG + "=", XMLNS_SLA_TAG + "=");
  //}
  
}
