package mashin.oep;

import java.util.ArrayList;
import java.util.List;

import mashin.oep.model.property.PropertyElement;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class Utils {

  public static IPropertyDescriptor[] combine(List<IPropertyDescriptor[]> list) {
    int size = 0;
    for (IPropertyDescriptor[] t : list) {
      size += t.length;
    }
    IPropertyDescriptor[] ts = new IPropertyDescriptor[size];
    int i = 0;
    for (IPropertyDescriptor[] t : list) {
      for (IPropertyDescriptor p : t) {
        ts[i++] = p;
      }
    }
    return ts;
  }
  
  public static IPropertyDescriptor[] combine(IPropertyDescriptor[]... descriptors) {
    int totalNumberOfDescriptors = 0;
    for (IPropertyDescriptor[] ds : descriptors)
      totalNumberOfDescriptors += ds.length;
    IPropertyDescriptor[] descriptorsToReturn = new IPropertyDescriptor[totalNumberOfDescriptors];
    int i = 0;
    for (IPropertyDescriptor[] ds : descriptors) {
      for (IPropertyDescriptor p : ds) {
        descriptorsToReturn[i++] = p;
      }
    }
    return descriptorsToReturn;
  }
  
  public static IPropertyDescriptor[] getPropertyDescriptors(List<PropertyElement> list) {
    int totalNumberOfPropertyDescriptors = 0;
    List<IPropertyDescriptor[]> pdsList = new ArrayList<IPropertyDescriptor[]>(list.size());
    
    for (PropertyElement propertyElement : list) {
      IPropertyDescriptor[] propertyElementPds = propertyElement.getPropertyDescriptors();
      totalNumberOfPropertyDescriptors += propertyElementPds.length;
      pdsList.add(propertyElementPds);
    }
    
    int i = 0;
    IPropertyDescriptor[] descriptors = new IPropertyDescriptor[totalNumberOfPropertyDescriptors];
    
    for (IPropertyDescriptor[] propertyElementPds : pdsList) {
      for (IPropertyDescriptor propertyDescriptor : propertyElementPds) {
        descriptors[i++] = propertyDescriptor;
      }
    }
    
    return descriptors;
  }
  
  public static IPropertyDescriptor[] getPropertyDescriptors(List<PropertyElement> list, String category) {
    int totalNumberOfPropertyDescriptors = 0;
    List<IPropertyDescriptor[]> pdsList = new ArrayList<IPropertyDescriptor[]>(list.size());
    
    for (PropertyElement propertyElement : list) {
      IPropertyDescriptor[] propertyElementPds = propertyElement.getPropertyDescriptors();
      totalNumberOfPropertyDescriptors += propertyElementPds.length;
      pdsList.add(propertyElementPds);
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
    
    return descriptors;
  }
  
}
