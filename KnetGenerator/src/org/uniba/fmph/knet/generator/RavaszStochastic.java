/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

/**
 *
 * @author pna
 */
public class RavaszStochastic<T> extends RavaszModel<T> {

    private double p = 0.6;
    
    
    public void setPercents(double perc){
        this.p = perc;
    }

    public double getPercents(){
        return p;
    }

    @Override
    protected T selectFrom(NodesProvider<T> previous) {
       return Selector.select(previous, 1, SelectionAlgorithm.PREFFERENTIAL, true).iterator().next();
    }

    @Override
    protected void whenCopyCreated(T newNode, T oldNode) {
        //do nothing
    }

    @Override
    protected boolean shouldHaveEdge(T nodeDraft) {
        return random.nextDouble()<p;
    }

    
}
