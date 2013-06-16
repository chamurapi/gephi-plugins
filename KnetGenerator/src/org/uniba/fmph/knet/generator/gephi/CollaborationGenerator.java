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
import org.uniba.fmph.knet.generator.Collaboration;

/**
 *
 * @author pna
 */
@ServiceProvider(service = Generator.class)
public class CollaborationGenerator extends AbstractGenerator<Collaboration<NodeDraft>> {

    @Override
    protected int workUnits() {
        return 12;
    }
    
    @Override
    public String getName() {
        return "Colaboration model";
    }
    
    

    
    @Override
    public GeneratorUI getUI() {        
        return Lookup.getDefault().lookup(CollaborationUI.class);            
    }
    
    public void setNetworkSize(int size){
        getGenerator().setNetworkSize(size);
    }

    public void setInitialNetworkSize(int size){
        getGenerator().setInitialNetworkSize(size);
    }

    public void setNewEdges(int size){
        getGenerator().setNewEdges(size);
    }

    
    /**
     * @return the c
     */
    public Double getC() {
        return getGenerator().getC();
    }

    /**
     * @param c the c to set
     */
    public void setC(double c) {
        getGenerator().setC(c);
    }

    /**
     * @return the kliqueSize
     */
    public Integer getCliqueSize() {
        return getGenerator().getCliqueSize();
    }

    /**
     * @param kliqueSize the kliqueSize to set
     */
    public void setCliqueSize(int kliqueSize) {
        getGenerator().setCliqueSize(kliqueSize);
    }

    /**
     * @return the initialNetworkSize
     */
    public Integer getInitialNetworkSize() {
        return getGenerator().getInitialNetworkSize();
    }

    /**
     * @return the initialNetworkDensity
     */
    public Double getInitialNetworkDensity() {
        return getGenerator().getInitialNetworkDensity();
    }

    /**
     * @param initialNetworkDensity the initialNetworkDensity to set
     */
    public void setInitialNetworkDensity(double initialNetworkDensity) {
        getGenerator().setInitialNetworkDensity(initialNetworkDensity);
    }

    /**
     * @return the newEdges
     */
    public Integer getNewEdges() {
        return getGenerator().getNewEdges();
    }
    
     public Integer getNetworkSize() {
        return getGenerator().getNetworkSize();
    }


    @Override
    protected Collaboration<NodeDraft> createGenerator() {
        return new Collaboration<NodeDraft>();
    }
    
}