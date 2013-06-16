/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uniba.fmph.knet.generator.graph.Graph;

/**
 *
 * @author lenivo-pna
 */
public class Snapshot<T> implements NodesProvider<T> {
    final List<T> nodes;
    final Map<T,Integer> degrees;
    final int degreeSum;

    public Snapshot(NodesProvider<T> provider ){ 
       List<T> list =  new ArrayList<T>(provider.size());
       Map<T, Integer> map = new HashMap<T, Integer>(provider.size());
       for(int i=0;i<provider.size();i++){
           T node = provider.get(i);
           list.add(node);
           map.put(node, provider.getDegree(node));
       }
       this.nodes = Collections.unmodifiableList(list);
       this.degrees = Collections.unmodifiableMap(map);
       this.degreeSum = provider.getDegreeSum();
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
    public int getDegree(T node) {
       return degrees.get(node);
    }

    @Override
    public int getDegreeSum() {
        return degreeSum;
    }
   
}
