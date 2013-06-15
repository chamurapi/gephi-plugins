/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

/**
 *
 * @author lenivo-pna
 */
public abstract class MatrixArrayIterator<T> extends MatrixIterator<T> {
    
    private T[] array;
    
    public MatrixArrayIterator() {
      this(null);
    }
    
    public MatrixArrayIterator(MatrixAccumulator<T,?> acumulator) {
     super(acumulator);
    }

    public void iterate(T... array){
      this.array=array;
      iterate();
    }
   
    @Override
    protected int getSize() {
      return array == null ? 0: array.length;
    }

    @Override
    protected T get(int i) {
        return array[i];
    }

    @Override
    public void iterate() {
        if (array == null){
            throw new IllegalArgumentException("Missing array to iterate");
        }
        super.iterate();
    }
    
    
    
}
