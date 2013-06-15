/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.ui;

import java.awt.Label;
import java.awt.TextField;
import javax.swing.JPanel;
import javax.swing.JSlider;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;

/**
 *
 * @author lenivo-pna
 */
public abstract class AbstractGeneratorUI<T extends Generator> implements GeneratorUI {

    private T gnrtr;
    protected JPanel panel;
    
    T generator(){
        return gnrtr;
    }
    

    @Override
    public void setup(Generator generator) {
      this.gnrtr = (T) generator;
      if (panel == null){
          panel = createPanel();
      }
    }

       
    
      
     protected static TextField addTextField(JPanel content, String label, String value){
               content.add(new Label(label));
               TextField field = new TextField(value);
               content.add(field);
               return field;
    }

        protected static JSlider addSlider(JPanel content, String label, int min, int max, int defaultValue, int x){
               content.add(new Label(label));               
               JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, defaultValue);
               slider.setMajorTickSpacing(x);
               slider.setPaintLabels(true);
               slider.setPaintTicks(true);
               content.add(slider);
               return slider;
    }

    protected abstract JPanel createPanel();

    @Override
    public void unsetup() {
        panel = null;
    }
    
    
}
