/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

/**
 *
 * @author lenivo-pna
 */
public abstract class MatrixIterator<T> {
    
    private MatrixAccumulator<T, ?> acumulator;
    private boolean cancel;
    
    public MatrixIterator() {
        this(null);
    }
    
    public MatrixIterator(MatrixAccumulator<T,?> acumulator) {
       this.acumulator = acumulator;
    }

    public void setAcumulator(MatrixAccumulator<T, ?> acumulator) {
        this.acumulator = acumulator;
    }

    public MatrixAccumulator<T, ?> getAcumulator() {
        return acumulator;
    }
    
    public void iterate(){
        cancel = false;
        for (int i=0;i<getSize() && !cancel;i++){
            for (int j=i+1;j<getSize() &&! cancel;j++){
                step(get(i), get(j));
            }
        }
        cancel = false;
    };

    public void cancel(){
        cancel = true;
    }
    
    public abstract void step(T arrayI, T arrayJ);

    protected abstract int getSize();
    
    protected abstract T get(int i);
}
