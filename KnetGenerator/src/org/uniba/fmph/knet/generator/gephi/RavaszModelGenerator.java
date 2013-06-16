/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.gephi;

import org.gephi.io.importer.api.NodeDraft;
import org.uniba.fmph.knet.generator.RavaszModel;

/**
 *
 * @author pna
 */
public abstract class RavaszModelGenerator<T extends RavaszModel<NodeDraft>> extends AbstractGenerator<T> {
   @Override
    protected int workUnits() {
        return (int)Math.pow(getGenerator().getPattern(),getGenerator().getLevels());
    }
    
    

    
    public void setLevels(int size){
        getGenerator().setLevels(size);
    }

    public void setPattern(int size){
        getGenerator().setPattern(size);
    }

    
   
    /**
     * @return the levels
     */
    public Integer getLevels() {
        return getGenerator().getLevels();
    }

    /**
     * @return the pattern
     */
    public Integer getPattern() {
        return getGenerator().getPattern();
    }

    
}
