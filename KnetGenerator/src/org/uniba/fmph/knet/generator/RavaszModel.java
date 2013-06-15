/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.HashMap;
import java.util.Map;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.utils.progress.Progress;

/**
 *
 * @author pna
 */
public abstract class RavaszModel extends AbstractGennerator {
    protected static final String PREVIOUS = "PREVIOUS";

    private NodesProvider<NodeDraft> previous;
    private int levels = 2;
    private int pattern = 4;
   
     @Override
    protected int workUnits() {
        return (int)Math.pow(pattern+1, levels);
    }
    
    @Override
    public void generateGraph(ContainerLoader container) {
        
        //Create list of nodes and a random obj
        createFirstModel(container);
        
        for(int l=1;l<getLevels() && !isCanceled() ;l++){
            
            NodeDraft[][] copies = new NodeDraft[getPattern()][];
            for(int i=0;i<getPattern() && !isCanceled();i++){
                copies[i] = copy(container, getPrevious());
            }
            for(int i=0;i<getPattern() && !isCanceled();i++){
                for (int j=0;j<copies[i].length && !isCanceled();j++){
                    if (shouldHaveEdge(copies[i][j])){
                        addEdge(container, selectFrom(getPrevious()), copies[i][j]);
                    }
                }
            }
            afterLevel();
        }
        Progress.finish(getProgressTicket());
    
    }
    
    protected abstract NodeDraft selectFrom(NodesProvider<NodeDraft> previous);
    
    public NodeDraft[] copy(final ContainerLoader container, NodesProvider<NodeDraft> toCopy){
        NodeDraft[] newNodes = new NodeDraft[toCopy.size()];
        final Map<NodeDraft, NodeDraft> copyN = new HashMap<NodeDraft, NodeDraft>();
        for(int i=0;i<toCopy.size() && !isCanceled(); i++){
            newNodes[i] = addNode(container);
            Progress.progress(getProgressTicket());
            copyN.put(newNodes[i], toCopy.get(i));
            whenCopyCreated(newNodes[i], toCopy.get(i));
        }
        new MatrixArrayIterator<NodeDraft>(){

            @Override
            public void step(NodeDraft arrayI, NodeDraft arrayJ) {
                NodeDraft a = copyN.get(arrayI);
                NodeDraft b = copyN.get(arrayJ);
                if (container.edgeExists(a, b)){
                    addEdge(container, arrayI, arrayJ);
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

    
    
    protected NodeDraft[] createFirstModel(ContainerLoader container){
        NodeDraft[] newNodes = new NodeDraft[getPattern()+1] ;
        newNodes[0]=addNode(container);
        Progress.progress(getProgressTicket());
        for(int i=1;i<=getPattern();i++){
            newNodes[i] = addNode(container);            
            if (i>2){
                addEdge(container, newNodes[i-1], newNodes[i]);
            }
            addEdge(container, newNodes[i], newNodes[0]);
        }
        addEdge(container, newNodes[1], newNodes[getPattern()]);
        previous = createSnapshot(PREVIOUS);
        return newNodes;
    }

    /**
     * @return the levels
     */
    public Integer getLevels() {
        return levels;
    }

    /**
     * @return the pattern
     */
    public Integer getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(int pattern) {
        this.pattern = pattern;
    }
   
    protected void afterLevel(){
       previous = createSnapshot(PREVIOUS);
    };

    protected abstract void whenCopyCreated(NodeDraft newNode, NodeDraft oldNode);

    protected abstract boolean shouldHaveEdge(NodeDraft nodeDraft);

    /**
     * @return the previous
     */
    public NodesProvider<NodeDraft> getPrevious() {
        return previous;
    }

    
}
