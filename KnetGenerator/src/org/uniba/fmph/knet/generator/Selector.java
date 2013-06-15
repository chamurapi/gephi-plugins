/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author lenivo-pna
 */
public class Selector {
    public static<T> T[] select(NodesProvider<T> provider, int size, SelectionAlgorithm alg, boolean distinct){
        Collection<T> nodes = createCollection(distinct);
        int i=0;
        while (nodes.size()<size && i<10*size){
            nodes.add(alg.select(provider));
        }
        return (T[])nodes.toArray();
    }

    private static <T> Collection<T> createCollection(boolean distinct) {
        if (distinct) {
            return new HashSet<T>();
        }
        return new ArrayList<T>();
    }
            

}
