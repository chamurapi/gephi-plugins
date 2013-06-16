/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author lenivo-pna
 */
public class Selector {
    public static<T> List<T> select(NodesProvider<T> provider, int size, SelectionAlgorithm alg, boolean distinct){
        Collection<T> nodes = createCollection(distinct);
        int i=0;
        while (nodes.size()<size && i<10*size){
            nodes.add(alg.select(provider));
        }
        return nodes instanceof List ? (List<T>)nodes : new ArrayList<T>(nodes);
    }

    private static <T> Collection<T> createCollection(boolean distinct) {
        if (distinct) {
            return new HashSet<T>();
        }
        return new ArrayList<T>();
    }
            

}
