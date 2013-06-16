/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.gephi;

import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.NodeDraft;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.uniba.fmph.knet.generator.RavaszDeterministic;

/**
 *
 * @author pna
 */
@ServiceProvider(service = Generator.class)
public class RavaszDeterministicGenerator extends RavaszModelGenerator<RavaszDeterministic<NodeDraft>>{

    @Override
    protected RavaszDeterministic<NodeDraft> createGenerator() {
        return new RavaszDeterministic<NodeDraft>();
    }

    @Override
    public String getName() {
        return "Ravasz Deterministic Hierarchical Model";
    }

    @Override
    public GeneratorUI getUI() {
         return Lookup.getDefault().lookup(RavaszDeterministicUI.class);            
    }
    
    
}
