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

import java.util.ArrayList;
import java.util.List;

public class HPDLModel {
  
  protected List<org.dom4j.Node> hpdlNodes;
  
  public HPDLModel() {
    hpdlNodes = new ArrayList<org.dom4j.Node>();
  }
  
  public void add(org.dom4j.Node hpdlNode) {
    hpdlNodes.add(hpdlNode);
  }
  
  public void set(org.dom4j.Node hpdlNode) {
    set(0, hpdlNode);
  }
  
  public void set(int i, org.dom4j.Node hpdlNode) {
    for (int j = hpdlNodes.size(); j <= i; j++) {
      add(null);
    }
    hpdlNodes.set(i, hpdlNode);
  }
  
  public org.dom4j.Node get() {
    try {
      return get(0);
    } catch (Exception e) {
      return null;
    }
  }
  
  public org.dom4j.Node get(int i) {
    try {
      return hpdlNodes.get(i);
    } catch (Exception e) {
      return null;
    }
  }
  
  public boolean isSet(int i) {
    try {
      get(i);
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
    return get(i) != null;
  }
  
  public boolean isSet() {
    return get() != null;
  }
  
}
