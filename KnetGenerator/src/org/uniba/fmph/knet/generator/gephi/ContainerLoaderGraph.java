/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.gephi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.uniba.fmph.knet.generator.graph.Graph;

/**
 *
 * @author lenivo-pna
 */
public class ContainerLoaderGraph implements Graph<NodeDraft> {

    private AttributeColumn col;
    private ContainerLoader container;
    private List<NodeDraft> nodes;
    private Map<NodeDraft, Integer> degrees;
    private int degreeSum;
    private int nextId;
    
    public ContainerLoaderGraph(ContainerLoader container, AttributeColumn col) {
        this.container = container;
        this.col = col;
    }

    @Override
    public NodeDraft addNewNode() {
        NodeDraft newNode = container.factory().newNodeDraft();
        newNode.addAttributeValue(col, nextId++);
        nodes.add(newNode);
        degrees.put(newNode, 0);
        container.addNode(newNode);
        return newNode;
    }

    @Override
    public void addEdge(NodeDraft a, NodeDraft b) {        
        EdgeDraft edge = container.factory().newEdgeDraft();
        edge.setSource(a);
        edge.setTarget(b);
        inc(a);
        inc(b);
        container.addEdge(edge);        
    }

    @Override
    public boolean containsEdge(NodeDraft a, NodeDraft b) {
        return container.edgeExists(a, b) || container.edgeExists(b, a);
    }

    @Override
    public void init() {
        nodes = new ArrayList<NodeDraft>();
        degrees = new HashMap<NodeDraft, Integer>();
        degreeSum =0;
        nextId = 0;
    }

    @Override
    public List<NodeDraft> getNodes() {
        return nodes;
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public NodeDraft get(int i) {
        return nodes.get(i);
    }

    @Override
    public int getDegree(NodeDraft node) {
        return degrees.get(node);
    }

    @Override
    public int getDegreeSum() {
        return degreeSum;
    }

    private void inc(NodeDraft a) {
        Integer i = getDegree(a);
        if (i==null) {
            degrees.put(a,1);
        } else{
            degrees.put(a, i+1);
        }
        degreeSum++;
    }
    
}
