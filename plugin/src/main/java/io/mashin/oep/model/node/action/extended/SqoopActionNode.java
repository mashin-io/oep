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
import io.mashin.oep.hpdl.XMLWriteUtils;
import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.property.PreparePropertyElement;
import io.mashin.oep.model.property.PropertyElementCollection;
import io.mashin.oep.model.property.PropertyPropertyElement;
import io.mashin.oep.model.property.TextPropertyElement;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

public class SqoopActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> SQOOP_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_2, SchemaVersion.V_0_3,
          SchemaVersion.V_0_4);
  private static final SchemaVersion SQOOP_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_4;
  private static final SchemaVersion SQOOP_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_4;
  
  public static final String PROP_JOBTRACKER = "prop.node.sqoop.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.sqoop.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.sqoop.prepare";
  public static final String PROP_JOBXML = "prop.node.sqoop.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.sqoop.configuration";
  public static final String PROP_COMMAND = "prop.node.sqoop.command";
  public static final String PROP_ARG = "prop.node.sqoop.arg";
  public static final String PROP_FILE = "prop.node.sqoop.file";
  public static final String PROP_ARCHIVE = "prop.node.sqoop.archive";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node

  //prepare
  protected PreparePropertyElement prepare;

  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  
  //choice
  protected TextPropertyElement command;//command
  protected PropertyElementCollection arg;//arg 1-unbounded
  
  protected PropertyElementCollection file;//file 0-unbounded
  protected PropertyElementCollection archive;//archive 0-unbounded
  
  public SqoopActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public SqoopActionNode(Workflow workflow, Node hpdlNode) {
    super(workflow, hpdlNode);
    
    jobTracker = new TextPropertyElement(PROP_JOBTRACKER, "Jobtracker");
    addPropertyElement(jobTracker);
    
    nameNode = new TextPropertyElement(PROP_NAMENODE, "Namenode");
    addPropertyElement(nameNode);
    
    //prepare
    prepare = new PreparePropertyElement(PROP_PREPARE, "Prepare");
    addPropertyElement(prepare);
    
    jobXML = new PropertyElementCollection("Job XML", new TextPropertyElement(PROP_JOBXML, "Job XML"));
    addPropertyElement(jobXML);
    
    configuration = new PropertyElementCollection("Configuration",
                      new PropertyPropertyElement(PROP_CONFIGURATION, "Configuration"));
    addPropertyElement(configuration);
    
    command = new TextPropertyElement(PROP_COMMAND, "Command");
    addPropertyElement(command);
    
    arg = new PropertyElementCollection("Arg",
           new TextPropertyElement(PROP_ARG, "Arg"));
    addPropertyElement(arg);
    
    file = new PropertyElementCollection("File", new TextPropertyElement(PROP_FILE, "File"));
    addPropertyElement(file);
    
    archive = new PropertyElementCollection("Archive", new TextPropertyElement(PROP_ARCHIVE, "Archive"));
    addPropertyElement(archive);
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName(workflow.nextId("sqoop"));
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element sqoop = element.addElement("sqoop");
    
    XMLWriteUtils.writeSchemaVersion(getSchemaVersion(), sqoop, getNodeType());
    XMLWriteUtils.writeTextPropertyAsElement(jobTracker, sqoop, "job-tracker");
    XMLWriteUtils.writeTextPropertyAsElement(nameNode, sqoop, "name-node");
    XMLWriteUtils.writePrepareProperty(prepare, sqoop, "prepare");
    XMLWriteUtils.writeTextCollectionAsElements(jobXML, sqoop, "job-xml");
    XMLWriteUtils.writePropertiesCollection(configuration, sqoop, "configuration", "property");
    XMLWriteUtils.writeTextPropertyAsElement(command, sqoop, "command");
    XMLWriteUtils.writeTextCollectionAsElements(arg, sqoop, "arg");
    XMLWriteUtils.writeTextCollectionAsElements(file, sqoop, "file");
    XMLWriteUtils.writeTextCollectionAsElements(archive, sqoop, "archive");
    
    writeConnections(element);
  }

  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./sqoop/job-tracker");
    XMLReadUtils.initTextPropertyFrom(nameNode, hpdlNode, "./sqoop/name-node");
    XMLReadUtils.initPreparePropertyFrom(prepare, hpdlNode, "./sqoop/prepare");
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./sqoop/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./sqoop/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(command, hpdlNode, "./sqoop/command");
    XMLReadUtils.initTextCollectionFrom(arg, hpdlNode, "./sqoop/arg");
    XMLReadUtils.initTextCollectionFrom(file, hpdlNode, "./sqoop/file");
    XMLReadUtils.initTextCollectionFrom(archive, hpdlNode, "./sqoop/archive");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_SQOOP;
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return SQOOP_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return SQOOP_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return SQOOP_LATEST_SCHEMA_VERSION;
  }
  
}
