/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author pna
 */
@ServiceProvider(service = Generator.class)
public class RavaszStochastic extends RavaszModel {

    private double p = 0.6;
    
    @Override
    public String getName() {
        return "Ravasz Stochastic Hierarchical Model";
    }

    @Override
    public GeneratorUI getUI() {
        return Lookup.getDefault().lookup(RavaszStochasticUI.class);            
    }
    
    public void setPercents(double perc){
        this.p = perc;
    }

    public Double getPercents(){
        return p;
    }

    @Override
    protected NodeDraft selectFrom(NodesProvider<NodeDraft> previous) {
       return Selector.select(previous, 1, SelectionAlgorithm.PREFFERENTIAL, true)[0];
    }

    @Override
    protected void whenCopyCreated(NodeDraft newNode, NodeDraft oldNode) {
        //do nothing
    }

    @Override
    protected boolean shouldHaveEdge(NodeDraft nodeDraft) {
        return random.nextDouble()<p;
    }

    
}
