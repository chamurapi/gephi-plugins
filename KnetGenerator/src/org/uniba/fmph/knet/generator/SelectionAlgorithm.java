/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator;

import java.util.Random;

/**
 *
 * @author lenivo-pna
 */
public enum SelectionAlgorithm {
    RANDOM(){

        @Override
        public <T> T select(NodesProvider<T> provider) {
            return provider.get(random.nextInt(provider.size()));
        }        
    },
    PREFFERENTIAL(){
        @Override
        public <T> T select(NodesProvider<T> provider) {
            
            int counter = 0;
            int target = random.nextInt(provider.getDegreeSum()+provider.size());
            for (int i=0;i<provider.size();i++){
                final T node = provider.get(i);
                if (target>=counter && target<(counter+=provider.getDegree(node)+1)){
                    return node;
                }
            }
            return provider.get(0);
        }
    };
    
    
    
     
    private static Random random = new Random(System.currentTimeMillis());
           
    
    public <T> T select(NodesProvider<T> provider){
        return null;
    }
}
