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

package io.mashin.oep.model.property;

import io.mashin.oep.model.property.filter.DefaultPropertyFilter;
import io.mashin.oep.model.property.filter.PropertyFilter;

public class GlobalPropertyElement extends PropertyElementGroup {

  public TextPropertyElement jobtracker;
  public TextPropertyElement namenode;
  public PropertyElementCollection jobxml;
  public PropertyElementCollection configuration;
  
  String jobtrackerId, namenodeId, jobxmlId, configurationId;
  
  public GlobalPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public GlobalPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    
    jobtrackerId = id + ".job-tracker";
    namenodeId = id + ".name-node";
    jobxmlId = id + ".job-xml";
    configurationId = id + ".configuration";
    
    jobtracker = new TextPropertyElement(jobtrackerId, "Jobtracker");
    jobtracker.setCategory(name);
    
    namenode = new TextPropertyElement(namenodeId, "Namenode");
    namenode.setCategory(name);
    
    jobxml = new PropertyElementCollection(name,
        new TextPropertyElement(jobxmlId, "Job XML"));
    
    configuration = new PropertyElementCollection(name,
        new PropertyPropertyElement(configurationId, "Configuration"));
    
    propertyElements.add(jobtracker);
    propertyElements.add(namenode);
    propertyElements.add(jobxml);
    propertyElements.add(configuration);
  }

  @Override
  public void setComplexValue(Object value) { }
  
  public void setValueOfJobtracker(String jobtracker) {
    setValue(jobtrackerId, jobtracker);
  }
  
  public void setValueOfNamenode(String namenode) {
    setValue(namenodeId, namenode);
  }
  
  public void setValueOfJobxml(PropertyElementCollection jobxml) {
    setValue(jobxmlId, jobxml);
  }
  
  public void setValueOfConfiguration(PropertyElementCollection configuration) {
    setValue(configurationId, configuration);
  }
  
}
