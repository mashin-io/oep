package mashin.oep.model.node.action.extended;

import java.util.Arrays;
import java.util.List;

import mashin.oep.model.SchemaVersion;
import mashin.oep.model.Workflow;
import mashin.oep.model.property.TextPropertyElement;

public class EmailActionNode extends ExtendedActionNode {

  private static final List<SchemaVersion> EMAIL_POSSIBLE_SCHEMA_VERSIONS = Arrays
      .asList(SchemaVersion.V_0_1, SchemaVersion.V_0_2);
  private static final SchemaVersion EMAIL_DEFAULT_SCHEMA_VERSION = SchemaVersion.V_0_2;
  private static final SchemaVersion EMAIL_LATEST_SCHEMA_VERSION = SchemaVersion.V_0_2;

  public static final String PROP_TO = "prop.node.email.to";
  public static final String PROP_CC = "prop.node.email.cc";
  public static final String PROP_SUBJECT = "prop.node.email.subject";
  public static final String PROP_BODY = "prop.node.email.body";
  public static final String PROP_CONTENT_TYPE = "prop.node.email.content_type";
  public static final String PROP_ATTACHMENT = "prop.node.email.attachment";
  
  protected TextPropertyElement to;//to
  protected TextPropertyElement cc;//cc
  protected TextPropertyElement subject;//subject
  protected TextPropertyElement body;//body
  protected TextPropertyElement contentType;//content_type
  protected TextPropertyElement attachment;//attachment
  
  public EmailActionNode(Workflow workflow) {
    super(workflow);
    
    to = new TextPropertyElement(PROP_TO, "To");
    addPropertyElement(to);
    
    cc = new TextPropertyElement(PROP_CC, "CC");
    addPropertyElement(cc);
    
    subject = new TextPropertyElement(PROP_SUBJECT, "Subject");
    addPropertyElement(subject);
    
    body = new TextPropertyElement(PROP_BODY, "Body");
    addPropertyElement(body);
    
    contentType = new TextPropertyElement(PROP_CONTENT_TYPE, "Content Type");
    addPropertyElement(contentType);
    
    attachment = new TextPropertyElement(PROP_ATTACHMENT, "Attachment");
    addPropertyElement(attachment);
    
    setName("email-" + ID_SEQ.incrementAndGet());
  }

  @Override
  public List<SchemaVersion> getPossibleSchemaVersions() {
    return EMAIL_POSSIBLE_SCHEMA_VERSIONS;
  }

  @Override
  public SchemaVersion getDefaultSchemaVersion() {
    return EMAIL_DEFAULT_SCHEMA_VERSION;
  }

  @Override
  public SchemaVersion getLatestSchemaVersion() {
    return EMAIL_LATEST_SCHEMA_VERSION;
  }
  
  @Override
  public String toHPDL() {
    return null;
  }

  @Override
  public void fromHPDL(String hpdl) {
    
  }

}
