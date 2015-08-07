package mashin.oep;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class Utils {

  public static IPropertyDescriptor[] combine(List<IPropertyDescriptor[]> list) {
    int size = 0;
    for(IPropertyDescriptor[] t: list) {
      size += t.length;
    }
    IPropertyDescriptor[] ts = new IPropertyDescriptor[size];
    int i = 0;
    for(IPropertyDescriptor[] t: list) {
      for(IPropertyDescriptor p: t) {
        ts[i++] = p;
      }
    }
    return ts;
  }
  
  public static IPropertyDescriptor[] combine(IPropertyDescriptor[]... descriptors) {
    int totalNumberOfDescriptors = 0;
    for(IPropertyDescriptor[] ds: descriptors)
      totalNumberOfDescriptors += ds.length;
    IPropertyDescriptor[] descriptorsToReturn = new IPropertyDescriptor[totalNumberOfDescriptors];
    int i = 0;
    for(IPropertyDescriptor[] ds: descriptors) {
      for (IPropertyDescriptor p: ds) {
        descriptorsToReturn[i++] = p;
      }
    }
    return descriptorsToReturn;
  }
  
}
