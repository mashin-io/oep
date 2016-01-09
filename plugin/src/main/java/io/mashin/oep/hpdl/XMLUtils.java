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
