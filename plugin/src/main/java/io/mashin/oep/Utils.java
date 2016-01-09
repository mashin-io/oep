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

package io.mashin.oep;

import io.mashin.oep.model.property.PropertyElement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class Utils {

  public static IPropertyDescriptor[] combine(List<IPropertyDescriptor[]> list) {
    List<IPropertyDescriptor> descriptorsList = new ArrayList<IPropertyDescriptor>();
    for (IPropertyDescriptor[] ipdsa : list) {
      if (ipdsa != null) {
        for (IPropertyDescriptor ipds : ipdsa) {
          if (ipds != null) {
            descriptorsList.add(ipds);
          }
        }
      }
    }
    
    int i = 0;
    IPropertyDescriptor[] descriptorsToReturn = new IPropertyDescriptor[descriptorsList.size()];
    for (IPropertyDescriptor ipds : descriptorsList) {
      descriptorsToReturn[i++] = ipds;
    }
    
    /*
    int size = 0;
    for (IPropertyDescriptor[] t : list) {
      if (t != null) {
        size += t.length;
      }
    }
    IPropertyDescriptor[] ts = new IPropertyDescriptor[size];
    int i = 0;
    for (IPropertyDescriptor[] t : list) {
      if (t != null) {
        for (IPropertyDescriptor p : t) {
          ts[i++] = p;
        }
      }
    }
    */
    
    return descriptorsToReturn;
  }
  
  public static IPropertyDescriptor[] combine(IPropertyDescriptor[]... descriptors) {
    List<IPropertyDescriptor> descriptorsList = new ArrayList<IPropertyDescriptor>();
    for (IPropertyDescriptor[] ipdsa : descriptors) {
      if (ipdsa != null) {
        for (IPropertyDescriptor ipds : ipdsa) {
          if (ipds != null) {
            descriptorsList.add(ipds);
          }
        }
      }
    }
    
    int i = 0;
    IPropertyDescriptor[] descriptorsToReturn = new IPropertyDescriptor[descriptorsList.size()];
    for (IPropertyDescriptor ipds : descriptorsList) {
      descriptorsToReturn[i++] = ipds;
    }
    
    /*
    int totalNumberOfDescriptors = 0;
    for (IPropertyDescriptor[] ds : descriptors) {
      if (ds != null) {
        totalNumberOfDescriptors += ds.length;
      }
    }
    IPropertyDescriptor[] descriptorsToReturn = new IPropertyDescriptor[totalNumberOfDescriptors];
    int i = 0;
    for (IPropertyDescriptor[] ds : descriptors) {
      if (ds != null) {
        for (IPropertyDescriptor p : ds) {
          descriptorsToReturn[i++] = p;
        }
      }
    }
    */
    
    return descriptorsToReturn;
  }
  
  public static IPropertyDescriptor[] getPropertyDescriptors(List<PropertyElement> list) {
    List<IPropertyDescriptor> descriptorsList = new ArrayList<IPropertyDescriptor>();
    for (PropertyElement propertyElement : list) {
      IPropertyDescriptor[] propertyElementPds = propertyElement.getPropertyDescriptors();
      if (propertyElementPds != null) {
        for (IPropertyDescriptor ipds : propertyElementPds) {
          if (ipds != null) {
            descriptorsList.add(ipds);
          }
        }
      }
    }
    
    int i = 0;
    IPropertyDescriptor[] descriptors = new IPropertyDescriptor[descriptorsList.size()];
    for (IPropertyDescriptor ipds : descriptorsList) {
      descriptors[i++] = ipds;
    }
    
    /*
    int totalNumberOfPropertyDescriptors = 0;
    List<IPropertyDescriptor[]> pdsList = new ArrayList<IPropertyDescriptor[]>(list.size());
    
    for (PropertyElement propertyElement : list) {
      IPropertyDescriptor[] propertyElementPds = propertyElement.getPropertyDescriptors();
      if (propertyElementPds != null) {
        totalNumberOfPropertyDescriptors += propertyElementPds.length;
        pdsList.add(propertyElementPds);
      }
    }
    
    int i = 0;
    IPropertyDescriptor[] descriptors = new IPropertyDescriptor[totalNumberOfPropertyDescriptors];
    
    for (IPropertyDescriptor[] propertyElementPds : pdsList) {
      for (IPropertyDescriptor propertyDescriptor : propertyElementPds) {
        descriptors[i++] = propertyDescriptor;
      }
    }
    */
    
    return descriptors;
  }
  
  public static IPropertyDescriptor[] getPropertyDescriptors(List<PropertyElement> list, String category) {
    List<IPropertyDescriptor> descriptorsList = new ArrayList<IPropertyDescriptor>();
    for (PropertyElement propertyElement : list) {
      IPropertyDescriptor[] propertyElementPds = propertyElement.getPropertyDescriptors();
      if (propertyElementPds != null) {
        for (IPropertyDescriptor ipds : propertyElementPds) {
          if (ipds != null) {
            descriptorsList.add(ipds);
          }
        }
      }
    }
    
    int i = 0;
    IPropertyDescriptor[] descriptors = new IPropertyDescriptor[descriptorsList.size()];
    for (IPropertyDescriptor ipds : descriptorsList) {
      descriptors[i] = ipds;
      ((PropertyDescriptor) ipds).setCategory(category);
      i++;
    }
    
    /*
    int totalNumberOfPropertyDescriptors = 0;
    List<IPropertyDescriptor[]> pdsList = new ArrayList<IPropertyDescriptor[]>(list.size());
    
    for (PropertyElement propertyElement : list) {
      IPropertyDescriptor[] propertyElementPds = propertyElement.getPropertyDescriptors();
      if (propertyElementPds != null) {
        totalNumberOfPropertyDescriptors += propertyElementPds.length;
        pdsList.add(propertyElementPds);
      }
    }
    
    int i = 0;
    IPropertyDescriptor[] descriptors = new IPropertyDescriptor[totalNumberOfPropertyDescriptors];
    
    for (IPropertyDescriptor[] propertyElementPds : pdsList) {
      for (IPropertyDescriptor propertyDescriptor : propertyElementPds) {
        descriptors[i] = propertyDescriptor;
        ((PropertyDescriptor) propertyDescriptor).setCategory(category);
        i++;
      }
    }
    */
    
    return descriptors;
  }
  
}
