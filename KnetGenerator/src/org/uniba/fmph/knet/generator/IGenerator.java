/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import org.uniba.fmph.knet.generator.graph.Graph;

/**
 *
 * @author lenivo-pna
 */
public interface IGenerator<T> {
    
    public void generate(Graph<T> graph);
    
    public boolean cancel();
    
    void setProgressIndicator(ProgressIndicator pi);
    
    ProgressIndicator getProgressIndicator();
}
