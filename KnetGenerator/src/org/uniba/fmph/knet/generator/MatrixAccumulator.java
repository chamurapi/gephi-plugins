/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

/**
 *
 * @author lenivo-pna
 */
public interface MatrixAccumulator<T,R> {
    
    void accumulate(T itemA, T itemB );
    
    R getResult();
}

