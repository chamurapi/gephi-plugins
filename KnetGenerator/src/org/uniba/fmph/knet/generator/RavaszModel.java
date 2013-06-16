/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uniba.fmph.knet.generator.graph.Graph;

/**
 *
 * @author pna
 */
public abstract class RavaszModel<T> extends AbstractGenerator<T> {
    protected static final String PREVIOUS = "PREVIOUS";

    private NodesProvider<T> previous;
    private int levels = 2;
    private int pattern = 4;
   
   
    @Override
    public void generateGraph(Graph<T> graph) {
        
        //Create list of nodes and a random obj
        createFirstModel(graph);
        
        for(int l=0;l<getLevels() && !isCanceled() ;l++){
            
            List<List<T>> copies = new ArrayList<List<T>>(getPattern());
            for(int i=0;i<getPattern() && !isCanceled();i++){
                copies.add(copy(graph, getPrevious()));
            }
            for(List<T> module: copies){
                for (T node: module){
                    if (isCanceled()){
                        break;
                    }
                    if (shouldHaveEdge(node)){
                        addEdge(graph, selectFrom(getPrevious()), node);
                    }
                }
            }
            afterLevel(graph);
        }
    
    }
    
    protected abstract T selectFrom(NodesProvider<T> previous);
    
    public List<T> copy(final Graph<T> graph, NodesProvider<T> toCopy){
        List<T> newNodes = new ArrayList<T>(toCopy.size());
        final Map<T, T> copyN = new HashMap<T, T>();
        int s = toCopy.size();
        for(int i=0;i< s && !isCanceled(); i++){
            T newNode = addNode(graph);
            newNodes.add(newNode);
            getProgressIndicator().indicateProgress();
            copyN.put(newNode, toCopy.get(i));
            whenCopyCreated(newNode, toCopy.get(i));
        }
        new MatrixListIterator<T>(){

            @Override
            public void step(T arrayI, T arrayJ) {
                T a = copyN.get(arrayI);
                T b = copyN.get(arrayJ);
                if (graph.containsEdge(a, b)){
                    addEdge(graph, arrayI, arrayJ);
                }
                if (isCanceled()) {
                    cancel();
                }
            }
            
        }.iterate(newNodes);        
        return newNodes;
    }
    
    public void setLevels(int levels){
        this.levels=levels;
    }

    
    
    protected List<T> createFirstModel(Graph<T> graph){
        List<T> newNodes = new ArrayList<T>(getPattern()+1) ;
        newNodes.add(addNode(graph));
        getProgressIndicator().indicateProgress();
        for(int i=1;i<=getPattern();i++){
            newNodes.add(addNode(graph));            
            if (i>1){
                addEdge(graph, newNodes.get(i-1), newNodes.get(i));
            }
            addEdge(graph, newNodes.get(i), newNodes.get(0));
        }
        addEdge(graph, newNodes.get(1), newNodes.get(getPattern()));
        previous = createSnapshot(PREVIOUS, graph);
        return newNodes;
    }

    /**
     * @return the levels
     */
    public int getLevels() {
        return levels;
    }

    /**
     * @return the pattern
     */
    public int getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(int pattern) {
        this.pattern = pattern;
    }
   
    protected void afterLevel(Graph<T> graph){
       previous = createSnapshot(PREVIOUS, graph);
    };

    protected abstract void whenCopyCreated(T newNode, T oldNode);

    protected abstract boolean shouldHaveEdge(T nodeDraft);

    /**
     * @return the previous
     */
    public NodesProvider<T> getPrevious() {
        return previous;
    }

    
}
