/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.ArrayList;
import java.util.List;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.utils.progress.Progress;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author pna
 */
@ServiceProvider(service = Generator.class)
public class Collaboration extends AbstractGennerator {

    
    private int networkSize = 1000;
    private double c = 0.005;
    private int cliqueSize = 4;
    private int initialNetworkSize = 50;
    private double initialNetworkDensity  = 0.6;
    private int newEdges = 1;       

    @Override
    protected int workUnits() {
        return 12;
    }
    
    
    
    @Override
    protected void generateGraph(ContainerLoader container) {
        
        //Create list of nodes and a random obj
        createFirstModel(container);
        Progress.progress(getProgressTicket());
        List<Double> percentageOfExisting = new ArrayList<Double>();
        for(int i=getInitialNetworkSize();i<getNetworkSize() && !isCanceled();i++){
            percentageOfExisting.add(step(i, container));          
            if ((i-getInitialNetworkSize()+1)%((getNetworkSize()-getInitialNetworkSize())/10)==0){
              Progress.progress(getProgressTicket());
            }
        }
        double rate = average(percentageOfExisting.toArray(new Double[0]));
        Progress.finish(getProgressTicket());
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
        this.networkSize=size;
    }

    public void setInitialNetworkSize(int size){
        this.initialNetworkSize=size;
    }

    public void setNewEdges(int size){
        this.newEdges=size;
    }

    private void createFirstModel(final ContainerLoader container) {
        for (int i= 0; i<getInitialNetworkSize() && !isCanceled();i++){
           addNode(container);
        }
        new MatrixListIterator<NodeDraft>(){
            @Override
            public void step(NodeDraft arrayI, NodeDraft arrayJ) {
              if (random.nextDouble() < getInitialNetworkDensity()){                  
                   addEdge(container, arrayJ, arrayJ);
              }
              if (isCanceled()){
                  cancel();
              }
            }
            
        }.iterate(getNodes());
    }

    
    private double step(int s, ContainerLoader container) {
        int newKliques = newKliques(s);
        Double[] percentageOfExisting = new Double[newKliques];
        for(int i=0; i<newKliques && !isCanceled(); i++){
            percentageOfExisting[i] = newKlique(container);
        }
        NodeDraft[] selected = Selector.select(this, newEdges(), SelectionAlgorithm.PREFFERENTIAL, false);
        NodeDraft newNode = addNode(container);
        for(NodeDraft oldNode: selected){
            if (!container.edgeExists(newNode, oldNode)){
                addEdge(container, newNode, oldNode);
            }
            if (isCanceled()){
                break;
            }
        }        
        return average(percentageOfExisting);
    }
        

    private int newKliques(int s) {
        return (int)Math.round(s*getC());
    }

    private double newKlique(final ContainerLoader container) {
        NodeDraft[] kliqueNodes = Selector.select(this, cliqueSize(), SelectionAlgorithm.RANDOM, true);
        int ks = kliqueNodes.length;
        Counter<NodeDraft> created = new Counter<NodeDraft>();
        new MatrixArrayIterator<NodeDraft>(created){

            @Override
            public void step(NodeDraft arrayI, NodeDraft arrayJ) {
                if (container.edgeExists(arrayI, arrayJ)){
                   getAcumulator().accumulate(arrayJ, arrayJ);                
                   addEdge(container, arrayI, arrayJ);
               } 
                if (isCanceled()){
                    cancel();                    
                }
            }
            
        }.iterate(kliqueNodes);
        
        return (double)created.getResult()/(double)(ks*(ks-1)/2);
    }


    private int cliqueSize() {
        return getCliqueSize();
    }

    private double average(Double[] percentageOfExisting) {
        double sum = 0;
        for(int i=0;i<percentageOfExisting.length;i++){
            sum+=percentageOfExisting[i];
        }
        return sum/percentageOfExisting.length;
    }

    private Integer newEdges() {
       return getNewEdges();
    }

    /**
     * @return the networkSize
     */
    public Integer getNetworkSize() {
        return networkSize;
    }

    /**
     * @return the c
     */
    public Double getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(double c) {
        this.c = c;
    }

    /**
     * @return the kliqueSize
     */
    public Integer getCliqueSize() {
        return cliqueSize;
    }

    /**
     * @param kliqueSize the kliqueSize to set
     */
    public void setCliqueSize(int kliqueSize) {
        this.cliqueSize = kliqueSize;
    }

    /**
     * @return the initialNetworkSize
     */
    public Integer getInitialNetworkSize() {
        return initialNetworkSize;
    }

    /**
     * @return the initialNetworkDensity
     */
    public Double getInitialNetworkDensity() {
        return initialNetworkDensity;
    }

    /**
     * @param initialNetworkDensity the initialNetworkDensity to set
     */
    public void setInitialNetworkDensity(double initialNetworkDensity) {
        this.initialNetworkDensity = initialNetworkDensity;
    }

    /**
     * @return the newEdges
     */
    public Integer getNewEdges() {
        return newEdges;
    }

   
    
}