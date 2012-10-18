/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.data.attributes.type.TimeInterval;
import org.gephi.desktop.project.api.ProjectControllerUI;
import org.gephi.dynamic.api.DynamicModel;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.project.api.ProjectController;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author pna
 */
@ServiceProvider(service = Generator.class)
public class RavaszDeterministic implements Generator {

     private boolean cancel = false;
     private ProgressTicket progressTicket;
    private long delay;
 
    @Override
    public void generate(org.gephi.io.importer.api.ContainerLoader container) {
        //Reset cancel
        cancel = false;

        //Start progress
        Progress.start(progressTicket);

        //Project
        ProjectController projectController = Lookup.getDefault().lookup(ProjectController.class);
        ProjectControllerUI projectControllerUI = Lookup.getDefault().lookup(ProjectControllerUI.class);
        if (projectController.getCurrentProject() == null) {
            projectControllerUI.newProject();
        }

        //Add the time interval column if not exist
        AttributeController attributeController = Lookup.getDefault().lookup(AttributeController.class);
        AttributeModel attributeModel = attributeController.getModel();
        AttributeColumn col = null;
        if ((col = attributeModel.getNodeTable().getColumn(DynamicModel.TIMEINTERVAL_COLUMN)) == null) {
            col = attributeModel.getNodeTable().addColumn(DynamicModel.TIMEINTERVAL_COLUMN, AttributeType.TIME_INTERVAL);
        }

        //Get current graph
        GraphController graphController = Lookup.getDefault().lookup(GraphController.class);
        GraphModel graphModel = graphController.getModel();
        Graph graph = graphModel.getGraph();

        //Create list of nodes and a random obj
        List<Node> nodes = new ArrayList<Node>();
        Random random = new Random(232323);

        //Create nodes and edges until cancelled
        while (!cancel) {

            //Create a new node and assign random position
            Node n = graphModel.factory().newNode();
            n.getNodeData().setX(random.nextInt(2000) - 1000);
            n.getNodeData().setY(random.nextInt(2000) - 1000);

            //Create a new random time interval and set it to the node
            double min = random.nextInt(2000) + 100;//Min value is 100
            double max = random.nextInt(2000) + 100;//Max value is 2099
            TimeInterval timeInterval = new TimeInterval(min < max ? min : max, max > min ? max : min);
            n.getNodeData().getAttributes().setValue(col.getIndex(), timeInterval);

            //Add the node to the graph
            graph.addNode(n);

            //Add a random number of edges between 0 and 3
            int nbedges = random.nextInt(4);
            for (int i = 0; i < nbedges; i++) {

                //Shuffle an index in the list of nodes
                int index = random.nextInt(nodes.size() + 1);
                if (index < nodes.size()) {

                    //Add an edge if not already exist
                    Node m = nodes.get(index);
                    if (n != m && graph.getEdge(n, m) == null) {
                        Edge e = graphModel.factory().newEdge(n, m);
                        graph.addEdge(e);
                    }
                }
            }

            //Add the node to the list of nodes
            nodes.add(n);

            //Sleep some time
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        Progress.finish(progressTicket);
    
    }

    @Override
    public String getName() {
        return "Ravasz Deterministic Hierarchical model";
    }

    @Override
    public GeneratorUI getUI() {
        return null;
    }

    @Override
    public boolean cancel() {
        cancel = true;
        return true;
    }

    @Override
    public void setProgressTicket(ProgressTicket pt) {
        this.progressTicket = pt;
    }
    
}
