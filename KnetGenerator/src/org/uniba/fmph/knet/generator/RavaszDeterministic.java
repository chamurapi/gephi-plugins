/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.HashSet;
import java.util.Set;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author pna
 */
@ServiceProvider(service = Generator.class)
public class RavaszDeterministic extends RavaszModel {

    private Set<NodeDraft> shouldHaveEdge;
    private Set<NodeDraft> shouldHaveEdgeNew;
    
    private NodeDraft node0;
       
    @Override
    public String getName() {
        return "Ravasz Deterministic Hierarchical model";
    }

    @Override
    public GeneratorUI getUI() {
         return Lookup.getDefault().lookup(RavaszDeterministicUI.class);            
    }

    @Override
    protected void initModel(ContainerLoader container) {
        super.initModel(container); 
        shouldHaveEdge = new HashSet<NodeDraft>();
        shouldHaveEdgeNew = new HashSet<NodeDraft>();
       
    }
          
    @Override
    protected NodeDraft[] createFirstModel(ContainerLoader container){
        NodeDraft[] newNodes = super.createFirstModel(container);
        node0=newNodes[0];
        for(NodeDraft node:newNodes){
            if (node!=node0){
                shouldHaveEdge.add(node);
            }
        }
        return newNodes;
    }

    @Override
    protected NodeDraft selectFrom(NodesProvider<NodeDraft> previous) {
        return node0;
    }

    @Override
    protected void afterLevel() {
        shouldHaveEdge = shouldHaveEdgeNew;
        shouldHaveEdgeNew = new HashSet<NodeDraft>();
    }

    @Override
    protected void whenCopyCreated(NodeDraft newNode, NodeDraft oldNode) {
         if (shouldHaveEdge.contains(oldNode)){
                shouldHaveEdgeNew.add(newNode);
            }
    }

    @Override
    protected boolean shouldHaveEdge(NodeDraft nodeDraft) {
       return shouldHaveEdgeNew.contains(nodeDraft);
    }

   
    
}
