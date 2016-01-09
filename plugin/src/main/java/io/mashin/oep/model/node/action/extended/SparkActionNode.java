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

public class SparkActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> SPARK_POSSIBLE_SCHEMA_VERSIONS = 
      Arrays.asList(SchemaVersion.V_0_1);
  private static final SchemaVersion SPARK_DEFAULT_SCHEMA_VERSION  = SchemaVersion.V_0_1;
  private static final SchemaVersion SPARK_LATEST_SCHEMA_VERSION   = SchemaVersion.V_0_1;
  
  public static final String PROP_JOBTRACKER = "prop.node.spark.job-tracker";
  public static final String PROP_NAMENODE = "prop.node.spark.name-ndoe";
  public static final String PROP_PREPARE = "prop.node.spark.prepare";
  public static final String PROP_JOBXML = "prop.node.spark.job-xml";
  public static final String PROP_CONFIGURATION = "prop.node.spark.configuration";
  public static final String PROP_MASTER = "prop.node.spark.master";
  public static final String PROP_MODE = "prop.node.spark.mode";
  public static final String PROP_NAME = "prop.node.spark.name";
  public static final String PROP_CLASS = "prop.node.spark.class";
  public static final String PROP_JAR = "prop.node.spark.jar";
  public static final String PROP_SPARK_OPTS = "prop.node.spark.spark-opts";
  public static final String PROP_ARG = "prop.node.spark.arg";
  
  protected TextPropertyElement jobTracker;//job-tracker
  protected TextPropertyElement nameNode;//name-node
  
  //prepare
  protected PreparePropertyElement prepare;
  
  protected PropertyElementCollection jobXML;//job-xml 0-unbounded
  protected PropertyElementCollection configuration;//configuration
  protected TextPropertyElement master;//master
  protected TextPropertyElement mode;//mode
  protected TextPropertyElement name;//name
  protected TextPropertyElement clazz;//class
  protected TextPropertyElement jar;//jar
  protected TextPropertyElement sparkOpts;//spark-opts
  protected PropertyElementCollection arg;//arg 0-unbounded
  
  public SparkActionNode(Workflow workflow) {
    this(workflow, null);
  }
  
  public SparkActionNode(Workflow workflow, Node hpdlNode) {
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
    
    master = new TextPropertyElement(PROP_MASTER, "Master");
    addPropertyElement(master);
    
    mode = new TextPropertyElement(PROP_MODE, "Mode");
    addPropertyElement(mode);
    
    name = new TextPropertyElement(PROP_NAME, "Name2");
    addPropertyElement(name);
    
    clazz = new TextPropertyElement(PROP_CLASS, "Class");
    addPropertyElement(clazz);
    
    jar = new TextPropertyElement(PROP_JAR, "Jar");
    addPropertyElement(jar);
    
    sparkOpts = new TextPropertyElement(PROP_SPARK_OPTS, "Spark Opts");
    addPropertyElement(sparkOpts);
    
    arg = new PropertyElementCollection("Arg",
           new TextPropertyElement(PROP_ARG, "Arg"));
    addPropertyElement(arg);
  }

  @Override
  public void initDefaults() {
    super.initDefaults();
    setName(workflow.nextId("spark"));
  }
  
  @Override
  public void write(Element paretNode) {
    super.write(paretNode);
    
    Element element = (Element) hpdlModel.get();
    Element spark = element.addElement("spark");
    
    XMLWriteUtils.writeSchemaVersion(getSchemaVersion(), spark, getNodeType());
    XMLWriteUtils.writeTextPropertyAsElement(jobTracker, spark, "job-tracker");
    XMLWriteUtils.writeTextPropertyAsElement(nameNode, spark, "name-node");
    XMLWriteUtils.writePrepareProperty(prepare, spark, "prepare");
    XMLWriteUtils.writeTextCollectionAsElements(jobXML, spark, "job-xml");
    XMLWriteUtils.writePropertiesCollection(configuration, spark, "configuration", "property");
    XMLWriteUtils.writeTextPropertyAsElement(master, spark, "master");
    XMLWriteUtils.writeTextPropertyAsElement(mode, spark, "mode");
    XMLWriteUtils.writeTextPropertyAsElement(name, spark, "name");
    XMLWriteUtils.writeTextPropertyAsElement(clazz, spark, "class");
    XMLWriteUtils.writeTextPropertyAsElement(jar, spark, "jar");
    XMLWriteUtils.writeTextPropertyAsElement(sparkOpts, spark, "spark-opts");
    XMLWriteUtils.writeTextCollectionAsElements(arg, spark, "arg");
    
    writeConnections(element);
  }

  @Override
  public void read(Node hpdlNode) {
    super.read(hpdlNode);
    
    XMLReadUtils.initTextPropertyFrom(jobTracker, hpdlNode, "./spark/job-tracker");
    XMLReadUtils.initTextPropertyFrom(nameNode, hpdlNode, "./spark/name-node");
    XMLReadUtils.initPreparePropertyFrom(prepare, hpdlNode, "./spark/prepare");
    XMLReadUtils.initTextCollectionFrom(jobXML, hpdlNode, "./spark/job-xml");
    XMLReadUtils.initPropertiesCollectionFrom(configuration, hpdlNode, "./spark/configuration", "./property");
    XMLReadUtils.initTextPropertyFrom(master, hpdlNode, "./spark/master");
    XMLReadUtils.initTextPropertyFrom(mode, hpdlNode, "./spark/mode");
    XMLReadUtils.initTextPropertyFrom(name, hpdlNode, "./spark/name");
    XMLReadUtils.initTextPropertyFrom(clazz, hpdlNode, "./spark/class");
    XMLReadUtils.initTextPropertyFrom(jar, hpdlNode, "./spark/jar");
    XMLReadUtils.initTextPropertyFrom(sparkOpts, hpdlNode, "./spark/spark-opts");
    XMLReadUtils.initTextCollectionFrom(arg, hpdlNode, "./spark/arg");
  }
  
  @Override
  public String getNodeType() {
    return TYPE_SPARK;
  }
  
  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return SPARK_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return SPARK_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return SPARK_LATEST_SCHEMA_VERSION;
  }

}
