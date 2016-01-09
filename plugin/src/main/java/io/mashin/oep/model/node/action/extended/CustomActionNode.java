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

package io.mashin.oep.model.node.action.extended;

import io.mashin.oep.hpdl.XMLReadUtils;
import io.mashin.oep.hpdl.XMLUtils;
import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.property.TextPropertyElement;

import java.util.Arrays;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class CustomActionNode extends ExtendedActionNode {

  public static final String PROP_XML = "prop.node.custom.xml";
  
  protected TextPropertyElement xml;
  
  public CustomActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public CustomActionNode(Workflow workflow, Node hpdlNode) {
    super(workflow, hpdlNode);
    xml = new TextPropertyElement(PROP_XML, "XML");
    addPropertyElement(xml);
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName(workflow.nextId("action"));
  }
  
  @Override
  public void write(Element parentNode) {
    super.write(parentNode);
    
    Element element = (Element) hpdlModel.get();
    String xmlContent = xml.getStringValue();
    if (xmlContent.isEmpty()) {
      xmlContent = "<EMPTY_CUSTOM_NODE xmlns=\"uri:oozie:EMPTY_CUSTOM_NODE:0.1\"/>";
    }
    try {
      element.add(DocumentHelper.parseText(xmlContent).getRootElement());
    } catch (Throwable t) {
      element.setText(xmlContent);
    }
    
    writeConnections(element);
  }
  
  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    Node node = XMLReadUtils.schemaVersionParentNode(hpdlNode);
    xml.setStringValue(XMLUtils.schemaVersionToXmlns(node.asXML()));
  }
  
  @Override
  public String getNodeType() {
    return TYPE_CUSTOM_ACTION;
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return Arrays.asList(SchemaVersion.V_ANY);
  }
  
  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return SchemaVersion.V_ANY;
  }
  
  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return SchemaVersion.V_ANY;
  }
  
  @Override
  protected boolean isSchemaVersionEditable() { return false; }
  
}
