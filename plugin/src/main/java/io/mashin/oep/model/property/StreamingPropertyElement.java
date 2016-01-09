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

public class StreamingPropertyElement extends PropertyElementGroup {

  public TextPropertyElement mapper;//mapper
  public TextPropertyElement reducer;//reducer
  public TextPropertyElement recordReader;//record-reader
  public PropertyElementCollection recordReaderMapping;//record-reader-mapping 0-unbounded
  public PropertyElementCollection env;//env 0-unbounded
  
  String mapperId, reducerId, recordReaderId, recordReaderMappingId, envId;
  
  public StreamingPropertyElement(String id, String name) {
    this(id, name, new DefaultPropertyFilter());
  }
  
  public StreamingPropertyElement(String id, String name, PropertyFilter filter) {
    super(id, name, filter);
    
    mapperId = id + ".mapper";
    reducerId = id + ".reducer";
    recordReaderId = id + ".record-reader";
    recordReaderMappingId = id + ".record-reader-mapping";
    envId = id + ".env";
    
    mapper = new TextPropertyElement(mapperId, "Mapper");
    reducer = new TextPropertyElement(reducerId, "Reducer");
    recordReader = new TextPropertyElement(recordReaderId, "Record Reader");
    recordReaderMapping = new PropertyElementCollection(name,
        new TextPropertyElement(recordReaderMappingId, "Record Reader Mapping"));
    env = new PropertyElementCollection(name,
        new TextPropertyElement(envId, "Env"));
    
    propertyElements.add(mapper);
    propertyElements.add(reducer);
    propertyElements.add(recordReader);
    propertyElements.add(recordReaderMapping);
    propertyElements.add(env);
  }

  @Override
  public void setComplexValue(Object value) {
  }

}
