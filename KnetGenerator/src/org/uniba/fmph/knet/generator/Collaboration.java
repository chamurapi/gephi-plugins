/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.ArrayList;
import java.util.List;
import org.uniba.fmph.knet.generator.graph.Graph;

/**
 *
 * @author pna
 */

public class Collaboration<T> extends AbstractGenerator<T> {

    
    private int networkSize = 1000;
    private double c = 0.005;
    private int cliqueSize = 4;
    private int initialNetworkSize = 50;
    private double initialNetworkDensity  = 0.6;
    private int newEdges = 1;       

    
    
    
    @Override
    protected void generateGraph(Graph<T> graph) {
        
        //Create list of nodes and a random obj
        createFirstModel(graph);
        getProgressIndicator().indicateProgress();
        List<Double> percentageOfExisting = new ArrayList<Double>();
        for(int i=getInitialNetworkSize();i<getNetworkSize() && !isCanceled();i++){
            percentageOfExisting.add(step(i, graph));   
            int x = (getNetworkSize()-getInitialNetworkSize())/10;
            x= x==0 ? 1:x;
            if ((i-getInitialNetworkSize()+1)%x==0){
              getProgressIndicator().indicateProgress();
            }
        }
        double rate = average(percentageOfExisting.toArray(new Double[0]));
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

    private void createFirstModel(final Graph<T> graph) {
        for (int i= 0; i<getInitialNetworkSize() && !isCanceled();i++){
           addNode(graph);
        }
        new MatrixListIterator<T>(){
            @Override
            public void step(T arrayI, T arrayJ) {
              if (random.nextDouble() < getInitialNetworkDensity()){                  
                   addEdge(graph, arrayJ, arrayJ);
              }
              if (isCanceled()){
                  cancel();
              }
            }
            
        }.iterate(graph.getNodes());
    }

    
    private double step(int s, Graph graph) {
        int newKliques = newKliques(s);
        Double[] percentageOfExisting = new Double[newKliques+1];
        for(int i=0; i<newKliques && !isCanceled(); i++){
            percentageOfExisting[i] = newKlique(graph);
        }
        List<T> selected = Selector.select(graph, newEdges(), SelectionAlgorithm.PREFFERENTIAL, false);
        T newNode = addNode(graph);
        selected.add(newNode);
        percentageOfExisting[percentageOfExisting.length-1]=fillKlique(graph, selected);
        return average(percentageOfExisting);
    }
        

    private int newKliques(int s) {
        return (int)Math.round(s*getC());
    }

    private double fillKlique(final Graph graph, List<T> kliqueNodes) {
        int ks = kliqueNodes.size();
        Counter<T> created = new Counter<T>();
        new MatrixListIterator<T>(created){

            @Override
            public void step(T arrayI, T arrayJ) {
                if (!graph.containsEdge(arrayI, arrayJ) ){
                   getAcumulator().accumulate(arrayJ, arrayJ);                
                   addEdge(graph, arrayI, arrayJ);
               } 
                if (isCanceled()){
                    cancel();                    
                }
            }
            
        }.iterate(kliqueNodes);
        int potential = ks*(ks-1)/2;
        return (double)(potential - created.getResult())/(double)potential;
    }

    
    
    private double newKlique(Graph graph) {
        List<T> kliqueNodes = Selector.select(graph, cliqueSize(), SelectionAlgorithm.RANDOM, true);
        return fillKlique(graph, kliqueNodes);
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
    public int getCliqueSize() {
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
    public int getInitialNetworkSize() {
        return initialNetworkSize;
    }

    /**
     * @return the initialNetworkDensity
     */
    public double getInitialNetworkDensity() {
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
    public int getNewEdges() {
        return newEdges;
    }

   
    
}