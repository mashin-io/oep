package io.mashin.oep.hpdl;

import io.mashin.oep.model.SchemaVersion;
import io.mashin.oep.model.connection.WorkflowCaseConnection;
import io.mashin.oep.model.connection.WorkflowConnection;
import io.mashin.oep.model.node.action.basic.FSActionNode;
import io.mashin.oep.model.property.CheckBoxPropertyElement;
import io.mashin.oep.model.property.CredentialPropertyElement;
import io.mashin.oep.model.property.GlobalPropertyElement;
import io.mashin.oep.model.property.PipesPropertyElement;
import io.mashin.oep.model.property.PreparePropertyElement;
import io.mashin.oep.model.property.PropertyElement;
import io.mashin.oep.model.property.PropertyElementCollection;
import io.mashin.oep.model.property.PropertyPropertyElement;
import io.mashin.oep.model.property.StreamingPropertyElement;
import io.mashin.oep.model.property.TextPropertyElement;

import java.util.List;

import org.dom4j.Element;

public class XMLWriteUtils {

  public static void writeSchemaVersion(SchemaVersion schemaVersion, Element element, String nodeType) {
    element.addAttribute(XMLUtils.SCHEMA_VERSION_TAG, "uri:oozie:" + nodeType + "-action:" + schemaVersion);
  }
  
  public static void writeWorkflowSchemaVersion(SchemaVersion schemaVersion, Element element) {
    element.addAttribute(XMLUtils.SCHEMA_VERSION_TAG, "uri:oozie:workflow:" + schemaVersion);
  }
  
  public static void writeConnectionsAsAttribute(
      List<WorkflowConnection> connections, Element parent, String name) {
    for (WorkflowConnection conn : connections) {
      parent.addAttribute(name, conn.getTargetNode().getName());
    }
  }
  
  public static void writeConnectionsAsElementWithAttribute(
      List<WorkflowConnection> connections, Element parent, String elementName,
      String attributeName) {
    for (WorkflowConnection conn : connections) {
      parent.addElement(elementName)
        .addAttribute(attributeName, conn.getTargetNode().getName());
    }
  }
  
  public static void writeCaseConnections(List<WorkflowConnection> connections,
      Element parent) {
    for (WorkflowConnection conn : connections) {
      parent.addElement("case")
        .addAttribute("to", conn.getTargetNode().getName())
        .setText(((WorkflowCaseConnection) conn).getCondition());
    }
  }
  
  public static void writeTextPropertyAsAttribute(TextPropertyElement pe, Element parent, String name) {
    if (pe.isSet()) {
      parent.addAttribute(name, pe.getStringValue());
    }
  }
  
  public static void writeTextPropertyAsElement(TextPropertyElement pe, Element parent, String name) {
    if (pe.isSet() && pe.filter()) {
      Element node = parent.addElement(name);
      node.setText(pe.getStringValue());
    }
  }
  
  public static void writeTextPropertyAsElementWithAttribute(
      TextPropertyElement pe, Element parent, String elementName, String attributeName) {
    if (pe.isSet() && pe.filter()) {
      Element node = parent.addElement(elementName);
      node.addAttribute(attributeName, pe.getStringValue());
    }
  }
  
  public static void writeCheckPropertyAsElement(CheckBoxPropertyElement pe, Element parent, String name) {
    if (pe.getBooleanValue() && pe.filter()) {
      parent.addElement(name);
    }
  }
  
  public static void writeTextCollectionAsElements(PropertyElementCollection pec, Element parent, String name) {
    if (pec.isSet() && pec.filter()) {
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        writeTextPropertyAsElement((TextPropertyElement) pe, parent, name);
      }
    }
  }
  
  public static void writeTextCollectionAsElementsWithAttribute(
      PropertyElementCollection pec, Element parent, String elementName, String attributeName) {
    if (pec.isSet() && pec.filter()) {
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        writeTextPropertyAsElementWithAttribute((TextPropertyElement) pe, parent, elementName, attributeName);
      }
    }
  }
  
  public static void writePropertiesCollection(PropertyElementCollection pec,
      Element parent, String groupName, String elementName) {
    if (pec.isSet() && pec.filter()) {
      Element groupElement = null;
      if (groupName != null && !groupName.isEmpty()) {
        groupElement = parent.addElement(groupName);
      } else {
        groupElement = parent;
      }
      
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        if (pe.isSet() && pe.filter()) {
          PropertyPropertyElement ppe = (PropertyPropertyElement) pe;
          Element node = groupElement.addElement(elementName);
          writeTextPropertyAsElement(ppe.name, node, "name");
          writeTextPropertyAsElement(ppe.value, node, "value");
          writeTextPropertyAsElement(ppe.description, node, "description");
        }
      }
    }
  }
  
  public static void writeFSDeleteCollection(PropertyElementCollection pec, Element parent) {
    if (pec.isSet() && pec.filter()) {
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        if (pe.isSet() && pe.filter()) {
          FSActionNode.FSOperationDelete delete = (FSActionNode.FSOperationDelete) pe;
          Element node = parent.addElement("delete");
          writeTextPropertyAsAttribute(delete.path, node, "path");
        }
      }
    }
  }
  
  public static void writeFSMkdirCollection(PropertyElementCollection pec, Element parent) {
    if (pec.isSet() && pec.filter()) {
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        if (pe.isSet() && pe.filter()) {
          FSActionNode.FSOperationMkdir mkdir = (FSActionNode.FSOperationMkdir) pe;
          Element node = parent.addElement("mkdir");
          writeTextPropertyAsAttribute(mkdir.path, node, "path");
        }
      }
    }
  }
  
  public static void writeFSMoveCollection(PropertyElementCollection pec, Element parent) {
    if (pec.isSet() && pec.filter()) {
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        if (pe.isSet() && pe.filter()) {
          FSActionNode.FSOperationMove move = (FSActionNode.FSOperationMove) pe;
          Element node = parent.addElement("move");
          writeTextPropertyAsAttribute(move.source, node, "source");
          writeTextPropertyAsAttribute(move.target, node, "target");
        }
      }
    }
  }
  
  public static void writeFSChmodCollection(PropertyElementCollection pec, Element parent) {
    if (pec.isSet() && pec.filter()) {
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        if (pe.isSet() && pe.filter()) {
          FSActionNode.FSOperationChmod chmod = (FSActionNode.FSOperationChmod) pe;
          Element node = parent.addElement("chmod");
          writeCheckPropertyAsElement(chmod.recursive, node, "recursive");
          writeTextPropertyAsAttribute(chmod.path, node, "path");
          writeTextPropertyAsAttribute(chmod.permissions, node, "permissions");
          writeTextPropertyAsAttribute(chmod.dirFiles, node, "dir-files");
        }
      }
    }
  }
  
  public static void writeFSTouchzCollection(PropertyElementCollection pec, Element parent) {
    if (pec.isSet() && pec.filter()) {
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        if (pe.isSet() && pe.filter()) {
          FSActionNode.FSOperationTouchz touchz = (FSActionNode.FSOperationTouchz) pe;
          Element node = parent.addElement("touchz");
          writeTextPropertyAsAttribute(touchz.path, node, "path");
        }
      }
    }
  }
  
  public static void writeFSChgrpCollection(PropertyElementCollection pec, Element parent) {
    if (pec.isSet() && pec.filter()) {
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        if (pe.isSet() && pe.filter()) {
          FSActionNode.FSOperationChgrp chgrp = (FSActionNode.FSOperationChgrp) pe;
          Element node = parent.addElement("chgrp");
          writeCheckPropertyAsElement(chgrp.recursive, node, "recursive");
          writeTextPropertyAsAttribute(chgrp.path, node, "path");
          writeTextPropertyAsAttribute(chgrp.group, node, "group");
          writeTextPropertyAsAttribute(chgrp.dirFiles, node, "dir-files");
        }
      }
    }
  }
  
  public static void writePrepareProperty(PreparePropertyElement ppe, Element parent, String groupName) {
    if (ppe.isSet() && ppe.filter()) {
      Element groupElement = parent.addElement(groupName);
      writeTextCollectionAsElementsWithAttribute(ppe.delete, groupElement, "delete", "path");
      writeTextCollectionAsElementsWithAttribute(ppe.mkdir, groupElement, "mkdir", "path");
    }
  }
  
  public static void writeStreamingProperty(StreamingPropertyElement spe, Element parent) {
    if (spe.isSet() && spe.filter()) {
      Element streamingNode = parent.addElement("streaming");
      writeTextPropertyAsElement(spe.mapper, streamingNode, "mapper");
      writeTextPropertyAsElement(spe.reducer, streamingNode, "reducer");
      writeTextPropertyAsElement(spe.recordReader, streamingNode, "record-reader");
      writeTextCollectionAsElements(spe.recordReaderMapping, streamingNode, "record-reader-mapping");
      writeTextCollectionAsElements(spe.env, streamingNode, "env");
    }
  }
  
  public static void writePipesProperty(PipesPropertyElement ppe, Element parent) {
    if (ppe.isSet() && ppe.filter()) {
      Element pipesNode = parent.addElement("pipes");
      writeTextPropertyAsElement(ppe.map, pipesNode, "map");
      writeTextPropertyAsElement(ppe.reduce, pipesNode, "reduce");
      writeTextPropertyAsElement(ppe.inputFormat, pipesNode, "inputformat");
      writeTextPropertyAsElement(ppe.outputFormat, pipesNode, "outputformat");
      writeTextPropertyAsElement(ppe.partitioner, pipesNode, "partitioner");
      writeTextPropertyAsElement(ppe.writer, pipesNode, "writer");
      writeTextPropertyAsElement(ppe.program, pipesNode, "program");
    }
  }
  
  public static void writeGlobalProperty(GlobalPropertyElement gpe, Element parent) {
    if (gpe.isSet() && gpe.filter()) {
      Element element = parent.addElement("global");
      writeTextPropertyAsElement(gpe.jobtracker, element, "job-tracker");
      writeTextPropertyAsElement(gpe.namenode, element, "name-node");
      writeTextCollectionAsElements(gpe.jobxml, element, "job-xml");
      writePropertiesCollection(gpe.configuration, element, "configuration", "property");
    }
  }
  
  public static void writeCredentialsCollection(PropertyElementCollection pec, Element parent) {
    if (pec.isSet() && pec.filter()) {
      Element element = parent.addElement("credentials");
      
      List<PropertyElement> list = pec.getPropertyElements();
      for (PropertyElement pe : list) {
        if (pe.isSet() && pe.filter()) {
          CredentialPropertyElement cpe = (CredentialPropertyElement) pe;
          Element node = element.addElement("credential");
          writeTextPropertyAsAttribute(cpe.name, node, "name");
          writeTextPropertyAsAttribute(cpe.type, node, "type");
          writePropertiesCollection(cpe.credential, node, "", "property");
        }
      }
    }
  }
  
}
