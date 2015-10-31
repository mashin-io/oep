package io.mashin.oep.model.property;

import io.mashin.oep.model.ModelElementWithSchema;
import io.mashin.oep.model.property.filter.PropertyFilter;

public class SLAPropertyElement extends PropertyElementGroup {

  public TextPropertyElement appName; //app-name
  public TextPropertyElement nominalTime; //nominal-time
  public TextPropertyElement shouldStart; //should-start
  public TextPropertyElement shouldEnd; //should-end
  public TextPropertyElement maxDuration; //max-duration
  public TextPropertyElement parentClientId; //parent-client-id
  public TextPropertyElement parentSlaId; //parent-sla-id
  public TextPropertyElement notificationMsg; //notification-msg
  public TextPropertyElement alertEvents; //alert-events
  public TextPropertyElement alertContact; //alert-contact
  public TextPropertyElement devContact; //dev-contact
  public TextPropertyElement qaContact; //qa-contact
  public TextPropertyElement seContact; //se-contact
  public TextPropertyElement alertFrequency; //alert-frequency
  public TextPropertyElement alertPercentage; //alert-percentage
  public TextPropertyElement upstreamApps; //upstream-apps
  
  //public SLAPropertyElement(String id, String name, ModelElementWithSchema model) {
  //  this(id, name, model, new DefaultPropertyFilter());
  //}
  
  public SLAPropertyElement(String id, String name,
      ModelElementWithSchema model, PropertyFilter sla010Filter,
      PropertyFilter sla020Filter) {
    super(id, name, sla010Filter.or(sla020Filter));
    
    appName = new TextPropertyElement(id + ".app-name", "App Name", sla010Filter);
    nominalTime = new TextPropertyElement(id + ".nominal-time", "Nominal Time", sla010Filter.or(sla020Filter));
    shouldStart = new TextPropertyElement(id + ".should-start", "Should Start", sla010Filter.or(sla020Filter));
    shouldEnd = new TextPropertyElement(id + ".should-end", "Should End", sla010Filter.or(sla020Filter));
    maxDuration = new TextPropertyElement(id + ".max-duration", "Max Duration", sla020Filter);
    parentClientId = new TextPropertyElement(id + ".parent-client-id", "Parent Client Id", sla010Filter);
    parentSlaId = new TextPropertyElement(id + ".parent-sla-id", "Parent SLA Id", sla010Filter);
    notificationMsg = new TextPropertyElement(id + ".notification-msg", "Notification Msg", sla010Filter.or(sla020Filter));
    alertEvents = new TextPropertyElement(id + ".alert-events", "Alert Events", sla020Filter);
    alertContact = new TextPropertyElement(id + ".alert-contact", "Alert Contact", sla010Filter.or(sla020Filter));
    devContact = new TextPropertyElement(id + ".dev-contact", "Dev Contact", sla010Filter);
    qaContact = new TextPropertyElement(id + ".qa-contact", "QA Contact", sla010Filter);
    seContact = new TextPropertyElement(id + ".se-contact", "SE Contact", sla010Filter);
    alertFrequency = new TextPropertyElement(id + ".alert-frequency", "Alert Frequency", sla010Filter);
    alertPercentage = new TextPropertyElement(id + ".alert-percentage", "Alert Percentage", sla010Filter);
    upstreamApps = new TextPropertyElement(id + ".upstream-apps", "Upstream Apps", sla010Filter.or(sla020Filter));
    
    propertyElements.add(appName);
    propertyElements.add(nominalTime);
    propertyElements.add(shouldStart);
    propertyElements.add(shouldEnd);
    propertyElements.add(maxDuration);
    propertyElements.add(parentClientId);
    propertyElements.add(parentSlaId);
    propertyElements.add(notificationMsg);
    propertyElements.add(alertEvents);
    propertyElements.add(alertContact);
    propertyElements.add(devContact);
    propertyElements.add(qaContact);
    propertyElements.add(seContact);
    propertyElements.add(alertFrequency);
    propertyElements.add(alertPercentage);
    propertyElements.add(upstreamApps);
  }
  
  @Override
  public void setComplexValue(Object value) {}
  
}
