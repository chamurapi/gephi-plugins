/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.List;

/**
 *
 * @author lenivo-pna
 */
public abstract class MatrixListIterator<T> extends MatrixIterator<T> {
    
    private List<T> array;
    
    public MatrixListIterator() {
      this(null);
    }
    
    public MatrixListIterator(MatrixAccumulator<T,?> acumulator) {
     super(acumulator);
    }

    public void iterate(List<T> array){
      this.array=array;
      iterate();
    }
   
    @Override
    protected int getSize() {
      return array == null ? 0: array.size();
    }

    @Override
    protected T get(int i) {
        return array.get(i);
    }

    @Override
    public void iterate() {
        if (array == null){
            throw new IllegalArgumentException("Missing array to iterate");
        }
        super.iterate();
    }
    
    
    
}
