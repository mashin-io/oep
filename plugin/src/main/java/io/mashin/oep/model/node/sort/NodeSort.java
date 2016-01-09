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

package io.mashin.oep.model.node.sort;

import io.mashin.oep.model.Workflow;
import io.mashin.oep.model.node.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class NodeSort {

  Workflow workflow;
  List<Node> nodes;
  
  public NodeSort(Workflow workflow) {
    this.workflow = workflow;
    nodes = new ArrayList<Node>();
  }
  
  public void add(Node node) {
    nodes.add(node);
  }
  
  public void remove(Node node) {
    nodes.remove(node);
  }
  
  public void reset() {
    nodes.clear();
  }
  
  public boolean needsSort() {
    return nodes.size() > 0;
  }
  
  public abstract void sort();
  
}
