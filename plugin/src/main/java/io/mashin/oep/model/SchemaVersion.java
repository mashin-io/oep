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

package io.mashin.oep.model;

public class SchemaVersion implements Comparable<SchemaVersion> {
  
  public static final SchemaVersion V_ANY   = new SchemaVersion("");
  public static final SchemaVersion V_0_1   = new SchemaVersion("0.1");
  public static final SchemaVersion V_0_2   = new SchemaVersion("0.2");
  public static final SchemaVersion V_0_2_5 = new SchemaVersion("0.2.5");
  public static final SchemaVersion V_0_3   = new SchemaVersion("0.3");
  public static final SchemaVersion V_0_4   = new SchemaVersion("0.4");
  public static final SchemaVersion V_0_4_5 = new SchemaVersion("0.4.5");
  public static final SchemaVersion V_0_5   = new SchemaVersion("0.5");
  
  String version;
  
  public SchemaVersion() {}
  
  public SchemaVersion(String version) {
    this.version = version;
  }
  
  public void setVersion(String version) {
    this.version = version;
  }
  
  public String getVersion() {
    return this.version;
  }
  
  public int compareTo(SchemaVersion other) {
    if(this.version.equalsIgnoreCase(other.version)
        || this.version == V_ANY.version
        || other.version == V_ANY.version) {
      return 0;
    }
    
    String[] v1Splits = this.version.split("\\.");
    String[] v2Splits = other.version.split("\\.");
    
    int v1 = 0;
    for(String v1Split: v1Splits) {
      v1 = 10 * v1 + Integer.parseInt(v1Split);
    }
    
    int v2 = 0;
    for(String v2Split: v2Splits) {
      v2 = 10 * v2 + Integer.parseInt(v2Split);
    }
    
    if(v1Splits.length < v2Splits.length) {
      v1 *= 10 * (v2Splits.length - v1Splits.length);
    } else if(v2Splits.length < v1Splits.length) {
      v2 *= 10 * (v1Splits.length - v2Splits.length);
    }
    
    return Integer.compare(v1, v2);
  }
  
  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof SchemaVersion)) return false;
    return this.version.equals(((SchemaVersion) obj).version);
  }
  
  public boolean isLessThanOrEqual(SchemaVersion other) {
    return this.compareTo(other) <= 0;
  }
  
  public boolean isLessThan(SchemaVersion other) {
    return this.compareTo(other) < 0;
  }
  
  public boolean isGreaterThanOrEqual(SchemaVersion other) {
    return this.compareTo(other) >= 0;
  }
  
  public boolean isGreaterThan(SchemaVersion other) {
    return this.compareTo(other) > 0;
  }
  
  @Override
  public String toString() {
    return this.version;
  }
  
}
