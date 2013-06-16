/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.gephi;

import org.uniba.fmph.knet.generator.*;
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
public class RavaszStochasticGenerator extends RavaszModelGenerator<RavaszStochastic<NodeDraft>> {

   
   
    @Override
    public String getName() {
        return "Ravasz Stochastic Hierarchical Model";
    }

    @Override
    public GeneratorUI getUI() {
        return Lookup.getDefault().lookup(RavaszStochasticUI.class);            
    }
    
    public void setPercents(double perc){
        getGenerator().setPercents(perc);
    }

    public Double getPercents(){
        return getGenerator().getPercents();
    }

    @Override
    protected RavaszStochastic<NodeDraft> createGenerator() {
        return new RavaszStochastic<NodeDraft>();
    }

    
}
