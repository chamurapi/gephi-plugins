/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

/**
 *
 * @author lenivo-pna
 */
public interface NodesProvider<T> {
    
    int size();
    
    T get(int i);
    
    int degree(T node);
    
    int degreeSum();
}
