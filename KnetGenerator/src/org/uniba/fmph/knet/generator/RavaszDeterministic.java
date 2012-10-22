/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import org.gephi.desktop.project.api.ProjectControllerUI;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphFactory;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.project.api.Project;
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
    private long delay = 500;
    private int levels = 2;
    
    @Override
    public void generate(org.gephi.io.importer.api.ContainerLoader container) {
        
        
        
        //Reset cancel
        cancel = false;

        //Start progress
        Progress.start(progressTicket);

        //Project
        ProjectController projectController = Lookup.getDefault().lookup(ProjectController.class);
        ProjectControllerUI projectControllerUI = Lookup.getDefault().lookup(ProjectControllerUI.class);
        Project proj =  projectControllerUI.newProject();
        //Get current graph
        GraphController graphController =  Lookup.getDefault().lookup(GraphController.class);
        GraphModel graphModel =   graphController.getModel();
        
        //Create list of nodes and a random obj
        Node node0 = createFirstModel(graphModel);
        
        Graph graph = graphModel.getGraph();
     
        for(int i=1;i<levels;i++){
            Node[] nodes = graph.getNodes().toArray();
            Edge[] edges = graph.getEdges().toArray();
            Set<Node> newNodes = new HashSet<Node>();
            newNodes.addAll(copy(graph, nodes, edges));
            newNodes.addAll(copy(graph, nodes, edges));
            newNodes.addAll(copy(graph, nodes, edges));
            newNodes.addAll(copy(graph, nodes, edges));
            for(Node n:newNodes){
                Edge e = graphModel.factory().newEdge(node0, n);
                graph.addEdge(e);
            }
        }
        Progress.finish(progressTicket);
    
    }

    public Collection<Node> copy(Graph toGraph, Node[] nodes, Edge[] edges){
        HashMap<Node, Node> cp = new HashMap<Node, Node>(nodes.length);
        GraphFactory factory = toGraph.getGraphModel().factory();
        
        for(Node node: nodes){
            Node n = factory.newNode();
            cp.put(node, n);
            toGraph.addNode(n);            
        }
        for(Edge edge:edges){
            Node source = cp.get(edge.getSource());
            Node target = cp.get(edge.getTarget());
            Edge e = factory.newEdge(source, target);
            toGraph.addEdge(e);                    
        }        
        return cp.values();
    }
    
    @Override
    public String getName() {
        return "Ravasz Deterministic Hierarchical model";
    }

    @Override
    public GeneratorUI getUI() {
        
        return new GeneratorUI() {
            private TextField field;
            private RavaszDeterministic gnrtr;
            @Override
            public JPanel getPanel() {
               JPanel content = new JPanel();
               content.setLayout(new GridLayout(1,2));
               content.add(new Label("Levels"));
               field = new TextField(5);
               content.add(field);               
               return content;
            }

            @Override
            public void setup(Generator gnrtr) {
                this.gnrtr = ((RavaszDeterministic) gnrtr);
            }

            @Override
            public void unsetup() {
                gnrtr.setLevels(Integer.parseInt(field.getText()));
            }
        };
    }
    
    public void setLevels(int levels){
        this.levels=levels;
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

    private Node createFirstModel(GraphModel graphModel) {
        Node nodea = graphModel.factory().newNode();
        Node nodeb = graphModel.factory().newNode();
        Node nodec = graphModel.factory().newNode();
        Node noded = graphModel.factory().newNode();
        Node node0 = graphModel.factory().newNode();
        
        Graph g = graphModel.getGraph();
        g.addNode(node0);
        g.addNode(nodea);
        g.addNode(nodeb);
        g.addNode(nodec);
        g.addNode(noded);
        g.addEdge(graphModel.factory().newEdge(nodea, nodeb));
        g.addEdge(graphModel.factory().newEdge(nodea, noded));
        g.addEdge(graphModel.factory().newEdge(nodeb, nodec));
        g.addEdge(graphModel.factory().newEdge(nodec, noded));
        g.addEdge(graphModel.factory().newEdge(node0, nodeb));
        g.addEdge(graphModel.factory().newEdge(node0, noded));
        g.addEdge(graphModel.factory().newEdge(node0, nodec));
        g.addEdge(graphModel.factory().newEdge(node0, nodea));
        return node0;
    }
    
}
