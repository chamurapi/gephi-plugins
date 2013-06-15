/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lenivo-pna
 */
public class Snapshot<T> implements NodesProvider<T> {
    final List<T> nodes;
    final Map<T,Integer> degrees;
    final int degreeSum;

    public Snapshot(List<T> nodes, Map<T,Integer> degrees, int degreeSum ){ 
       this.nodes = Collections.unmodifiableList(nodes);
       this.degrees = Collections.unmodifiableMap(degrees);
       this.degreeSum = degreeSum;
    }
    
    
    
    @Override
    public int size() {
      return nodes.size();
    }

    @Override
    public T get(int i) {
      return nodes.get(i);
    }

    @Override
    public int degree(T node) {
       return degrees.get(node);
    }

    @Override
    public int degreeSum() {
        return degreeSum;
    }
    
}
