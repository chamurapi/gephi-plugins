/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.uniba.fmph.knet.generator.graph.Graph;

/**
 *
 * @author pna
 */
public abstract class AbstractGenerator<T> implements IGenerator<T>{
    protected static Random random = new Random(System.currentTimeMillis());
    
    private ProgressIndicator progressIndicator;
    private boolean cancel = false;
    private final Map<String, NodesProvider<T>> snapshots = new HashMap<String, NodesProvider<T>>();
    
    protected void initModel(Graph<T> graph){
        graph.init();
    }
    
    @Override
    public void generate(Graph<T> graph) {
        
        initModel(graph);
        generateGraph(graph);
        cancel=false;        
    }

    protected abstract void generateGraph(Graph<T> graph);
    
    

    @Override
    public boolean cancel() {
        cancel = true;
        return true;
    }

    @Override
    public void setProgressIndicator(ProgressIndicator pi){
        this.progressIndicator = pi;
    }

    protected void addEdge(Graph<T> graph, T a, T b){
         graph.addEdge(a, b);
    }
    
    protected T addNode(Graph<T> graph){
      T node = graph.addNewNode();
      return node;
    }
    
//    @Override
//    public int size() {
//        return getNodes().size();
//    }
//
//    @Override
//    public Node get(int i) {
//        return getNodes().get(i);
//    }
//
//    @Override
//    public int degree(Node node) {
//        return degrees.get(node);
//    }

//    @Override
//    public int degreeSum() {
//        return getDegreeSum();
//    }

    /**
     * @return the cancel
     */
    protected boolean isCanceled() {
        return cancel;
    }

    /**
     * @return the nodes
     */
//    public List<Node> getNodes() {
//        return nodes;
//    }
//
//    /**
//     * @return the degreeSum
//     */
//    public int getDegreeSum() {
//        return degreeSum;
//    }
    
    protected NodesProvider<T> createSnapshot(String id, Graph<T> graph){
        snapshots.put(id, new Snapshot<T>(graph));
        return getSnapshot(id);
    }
            
    protected NodesProvider<T> getSnapshot(String id){
        return snapshots.get(id);
    }

    @Override
    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }
    
    
}