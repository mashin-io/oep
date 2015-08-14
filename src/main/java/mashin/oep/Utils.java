package mashin.oep;

import java.util.ArrayList;
import java.util.List;

import mashin.oep.model.property.PropertyElement;

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
