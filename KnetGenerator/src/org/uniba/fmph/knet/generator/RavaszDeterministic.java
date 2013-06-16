/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.uniba.fmph.knet.generator.graph.Graph;

/**
 *
 * @author pna
 */
public class RavaszDeterministic<T> extends RavaszModel<T> {

    private Set<T> shouldHaveEdge;
    private Set<T> shouldHaveEdgeNew;
    
    private T node0;
       
    
    @Override
    protected void initModel(Graph<T> graph) {
        super.initModel(graph); 
        shouldHaveEdge = new HashSet<T>();
        shouldHaveEdgeNew = new HashSet<T>();
       
    }
          
    @Override
    protected List<T> createFirstModel(Graph<T> graph){
        List<T> newNodes = super.createFirstModel(graph);
        node0=newNodes.iterator().next();
        for(T node:newNodes){
            if (node!=node0){
                shouldHaveEdge.add(node);
            }
        }
        return newNodes;
    }

    @Override
    protected T selectFrom(NodesProvider<T> previous) {
        return node0;
    }

    @Override
    protected void afterLevel(Graph<T> graph) {
        super.afterLevel(graph);
        shouldHaveEdge = shouldHaveEdgeNew;
        shouldHaveEdgeNew = new HashSet<T>();
    }

    @Override
    protected void whenCopyCreated(T newNode, T oldNode) {
         if (shouldHaveEdge.contains(oldNode)){
                shouldHaveEdgeNew.add(newNode);
            }
    }

    @Override
    protected boolean shouldHaveEdge(T nodeDraft) {
       return shouldHaveEdgeNew.contains(nodeDraft);
    }

   
    
}
