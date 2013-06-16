/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.graph;

import java.util.List;
import org.uniba.fmph.knet.generator.NodesProvider;

/**
 *
 * @author lenivo-pna
 */
public interface Graph<T> extends NodesProvider<T> {
    T addNewNode();
    void addEdge(T a, T b);
    boolean containsEdge(T a, T b);
    public void init();
    List<T> getNodes();
    
}
