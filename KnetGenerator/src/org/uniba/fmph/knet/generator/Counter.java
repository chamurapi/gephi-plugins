/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

public class Counter<T> implements MatrixAccumulator<T, Integer>{

    private Integer counter;
    
    public Counter(int start){
        counter = start;
    }
    
    public Counter(){
        this(0);
    }
    
    @Override
    public void accumulate(T itemA, T itemB) {
        counter++;
    }

    @Override
    public Integer getResult() {
       return  counter;
    }
    
}
