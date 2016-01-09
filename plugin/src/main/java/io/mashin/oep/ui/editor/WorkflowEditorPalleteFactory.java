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

package io.mashin.oep.ui.editor;

import io.mashin.oep.Activator;
import io.mashin.oep.model.node.action.basic.FSActionNode;
import io.mashin.oep.model.node.action.basic.JavaActionNode;
import io.mashin.oep.model.node.action.basic.MapReduceActionNode;
import io.mashin.oep.model.node.action.basic.PigActionNode;
import io.mashin.oep.model.node.action.basic.SubWorkflowActionNode;
import io.mashin.oep.model.node.action.extended.CustomActionNode;
import io.mashin.oep.model.node.action.extended.DistcpActionNode;
import io.mashin.oep.model.node.action.extended.EmailActionNode;
import io.mashin.oep.model.node.action.extended.Hive2ActionNode;
import io.mashin.oep.model.node.action.extended.HiveActionNode;
import io.mashin.oep.model.node.action.extended.SSHActionNode;
import io.mashin.oep.model.node.action.extended.ShellActionNode;
import io.mashin.oep.model.node.action.extended.SparkActionNode;
import io.mashin.oep.model.node.action.extended.SqoopActionNode;
import io.mashin.oep.model.node.control.DecisionNode;
import io.mashin.oep.model.node.control.ForkNode;
import io.mashin.oep.model.node.control.JoinNode;
import io.mashin.oep.model.node.control.KillNode;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;

public class WorkflowEditorPalleteFactory {

  private static PaletteContainer createToolsGroup(PaletteRoot palette) {
    PaletteToolbar toolbar = new PaletteToolbar("Tools");

    // Add a selection tool to the group
    ToolEntry tool = new PanningSelectionToolEntry();
    toolbar.add(tool);
    palette.setDefaultEntry(tool);

    // Add a marquee tool to the group
    toolbar.add(new MarqueeToolEntry());

    // Add (solid-line) connection tool
    tool = new ConnectionCreationToolEntry("Connection",
        "Create a connection", null,
        ImageDescriptor.createFromFile(Activator.class, "icons/connection16.gif"),
        ImageDescriptor.createFromFile(Activator.class, "icons/connection24.gif"));
    toolbar.add(tool);

    return toolbar;
  }
  
  private static PaletteContainer createControlNodesGroup(WorkflowEditor workflowEditor) {
    PaletteDrawer controlNodesDrawer = new PaletteDrawer("Control Nodes");

    CombinedTemplateCreationEntry component = null;
    
    //CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
    //    "End", "Create an End node",
    //    EndNode.class, new NodeCreationFactory<EndNode>(workflowEditor, EndNode.class),
    //    ImageDescriptor.createFromFile(Activator.class, "icons/rectangle16.gif"),
    //    ImageDescriptor.createFromFile(Activator.class, "icons/rectangle24.gif"));
    //controlNodesDrawer.add(component);

    component = new CombinedTemplateCreationEntry(
        "Kill", "Create a Kill node",
        KillNode.class, new NodeCreationFactory<KillNode>(workflowEditor, KillNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Kill-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Kill-24.png"));
    controlNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Decision", "Create a Decision node",
        DecisionNode.class, new NodeCreationFactory<DecisionNode>(workflowEditor, DecisionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Decision-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Decision-24.png"));
    controlNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Fork", "Create a Fork node",
        ForkNode.class, new NodeCreationFactory<ForkNode>(workflowEditor, ForkNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Fork-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Fork-24.png"));
    controlNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Join", "Create a Join node",
        JoinNode.class, new NodeCreationFactory<JoinNode>(workflowEditor, JoinNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Join-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Join-24.png"));
    controlNodesDrawer.add(component);

    return controlNodesDrawer;
  }

  private static PaletteContainer createBasicActionNodesGroup(WorkflowEditor workflowEditor) {
    PaletteDrawer actionNodesDrawer = new PaletteDrawer("Action Nodes");

    CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
        "MapReduce", "Create a MapReduce node",
        MapReduceActionNode.class, new NodeCreationFactory<MapReduceActionNode>(workflowEditor, MapReduceActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/MapReduce-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/MapReduce-24.png"));
    actionNodesDrawer.add(component);

    component = new CombinedTemplateCreationEntry(
        "Pig", "Create a Pig node",
        PigActionNode.class, new NodeCreationFactory<PigActionNode>(workflowEditor, PigActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Pig-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Pig-24.png"));
    actionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Sub Workflow", "Create a Sub Workflow node",
        SubWorkflowActionNode.class, new NodeCreationFactory<SubWorkflowActionNode>(workflowEditor, SubWorkflowActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/SubWorkflow-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/SubWorkflow-24.png"));
    actionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "FS", "Create an FS node",
        FSActionNode.class, new NodeCreationFactory<FSActionNode>(workflowEditor, FSActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/FS-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/FS-24.png"));
    actionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Java", "Create a Java node",
        JavaActionNode.class, new NodeCreationFactory<JavaActionNode>(workflowEditor, JavaActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Java-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Java-24.png"));
    actionNodesDrawer.add(component);

    return actionNodesDrawer;
  }
  
  private static PaletteContainer createExtendedActionNodesGroup(WorkflowEditor workflowEditor) {
    PaletteDrawer extendedActionNodesDrawer = new PaletteDrawer("Extended Action Nodes");

    CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
        "SSH", "Create an SSH node",
        SSHActionNode.class, new NodeCreationFactory<SSHActionNode>(workflowEditor, SSHActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/SSH-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/SSH-24.png"));
    extendedActionNodesDrawer.add(component);

    component = new CombinedTemplateCreationEntry(
        "Sqoop", "Create a Sqoop node",
        SqoopActionNode.class, new NodeCreationFactory<SqoopActionNode>(workflowEditor, SqoopActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Sqoop-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Sqoop-24.png"));
    extendedActionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Spark", "Create a Spark node",
        SparkActionNode.class, new NodeCreationFactory<SparkActionNode>(workflowEditor, SparkActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Spark-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Spark-24.png"));
    extendedActionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Shell", "Create a Shell node",
        ShellActionNode.class, new NodeCreationFactory<ShellActionNode>(workflowEditor, ShellActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Shell-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Shell-24.png"));
    extendedActionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Distcp", "Create a Distcp node",
        DistcpActionNode.class, new NodeCreationFactory<DistcpActionNode>(workflowEditor, DistcpActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Distcp-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Distcp-24.png"));
    extendedActionNodesDrawer.add(component);

    component = new CombinedTemplateCreationEntry(
        "Hive", "Create a Hive node",
        HiveActionNode.class, new NodeCreationFactory<HiveActionNode>(workflowEditor, HiveActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Hive-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Hive-24.png"));
    extendedActionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Hive2", "Create a Hive2 node",
        Hive2ActionNode.class, new NodeCreationFactory<Hive2ActionNode>(workflowEditor, Hive2ActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Hive2-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Hive2-24.png"));
    extendedActionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Email", "Create an Email node",
        EmailActionNode.class, new NodeCreationFactory<EmailActionNode>(workflowEditor, EmailActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/Email-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/Email-24.png"));
    extendedActionNodesDrawer.add(component);
    
    component = new CombinedTemplateCreationEntry(
        "Custom Action", "Create a Custom Action node",
        CustomActionNode.class, new NodeCreationFactory<CustomActionNode>(workflowEditor, CustomActionNode.class),
        ImageDescriptor.createFromFile(Activator.class, "icons/CustomAction-16.png"),
        ImageDescriptor.createFromFile(Activator.class, "icons/CustomAction-24.png"));
    extendedActionNodesDrawer.add(component);
    
    return extendedActionNodesDrawer;
  }
  
  /**
   * Creates the PaletteRoot and adds all Palette elements.
   * 
   * @return the root
   */
  public static PaletteRoot createPalette(WorkflowEditor workflowEditor) {
    PaletteRoot workflowPalette = new PaletteRoot();
    workflowPalette.add(createToolsGroup(workflowPalette));
    workflowPalette.add(createControlNodesGroup(workflowEditor));
    workflowPalette.add(createBasicActionNodesGroup(workflowEditor));
    workflowPalette.add(createExtendedActionNodesGroup(workflowEditor));
    return workflowPalette;
  }
  
}
