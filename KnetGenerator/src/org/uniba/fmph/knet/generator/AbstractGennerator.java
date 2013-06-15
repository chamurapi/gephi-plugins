/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author pna
 */
public abstract class AbstractGennerator implements Generator, NodesProvider<NodeDraft> {
    public static final String COLUMN_TIME = "TIME";

    protected static Random random = new Random(System.currentTimeMillis());
    
    private ProgressTicket progressTicket;   
    private boolean cancel = false;
    private AttributeColumn col = null;
    private final Map<String, NodesProvider<NodeDraft>> snapshots = new HashMap<String, NodesProvider<NodeDraft>>();
    
    private List<NodeDraft> nodes;
    private Map<NodeDraft, Integer> degrees;
    private int degreeSum;
    private int nextId;
    
    protected abstract int workUnits();
    
    protected void setupGenerator(ContainerLoader container){
        //Start progress
        Progress.start(getProgressTicket(),workUnits());

        //Add the time interval column if not exist
        AttributeModel attributeModel = container.getAttributeModel();
        if ((col = attributeModel.getNodeTable().getColumn(COLUMN_TIME)) == null) {
            col = attributeModel.getNodeTable().addColumn(COLUMN_TIME, AttributeType.INT);
        }
        container.setEdgeDefault(EdgeDefault.UNDIRECTED);
        
    }
    
    protected void initModel(ContainerLoader container){
        degreeSum = 0;
        nodes = new ArrayList<NodeDraft>();
        degrees = new HashMap<NodeDraft, Integer>();  
        nextId = 0;
    }
    
    @Override
    public void generate(ContainerLoader container) {
        
        setupGenerator(container);
        initModel(container);
        
        generateGraph(container);
        cancel=false;
        Progress.finish(getProgressTicket());
    }

    protected abstract void generateGraph(ContainerLoader container);
    
    

    @Override
    public boolean cancel() {
        cancel = true;
        return true;
    }

    @Override
    public void setProgressTicket(ProgressTicket pt) {
        this.progressTicket = pt;
    }

    protected EdgeDraft addEdge(ContainerLoader container, NodeDraft a, NodeDraft b){
         EdgeDraft newEdgeDraft = container.factory().newEdgeDraft();
         newEdgeDraft.setSource(a);
         newEdgeDraft.setTarget(b);
         container.addEdge(newEdgeDraft);
         incDegree(a);
         incDegree(b);
         return newEdgeDraft;
    }
    
    protected NodeDraft addNode(final ContainerLoader container){
      NodeDraft node = container.factory().newNodeDraft();
      node.addAttributeValue(getCol(), nextId++);
      container.addNode(node);
      getNodes().add(node);
      degrees.put(node, 0);
      return node;
    }
    
    private AttributeColumn getCol() {
        return col;
    }

    private void incDegree(NodeDraft node){
        Integer d = degrees.get(node);
        degrees.put(node,  d ==null? 1 : ++d);   
        degreeSum++;
    }

    @Override
    public int size() {
        return getNodes().size();
    }

    @Override
    public NodeDraft get(int i) {
        return getNodes().get(i);
    }

    @Override
    public int degree(NodeDraft node) {
        return degrees.get(node);
    }

    @Override
    public int degreeSum() {
        return getDegreeSum();
    }

    /**
     * @return the progressTicket
     */
    public ProgressTicket getProgressTicket() {
        return progressTicket;
    }

    /**
     * @return the cancel
     */
    public boolean isCanceled() {
        return cancel;
    }

    /**
     * @return the nodes
     */
    public List<NodeDraft> getNodes() {
        return nodes;
    }

    /**
     * @return the degreeSum
     */
    public int getDegreeSum() {
        return degreeSum;
    }
    
    protected NodesProvider<NodeDraft> createSnapshot(String id){
        snapshots.put(id, new Snapshot<NodeDraft>(nodes, degrees, degreeSum));
        return getSnapshot(id);
    }
            
    protected NodesProvider<NodeDraft> getSnapshot(String id){
        return snapshots.get(id);
    }
}