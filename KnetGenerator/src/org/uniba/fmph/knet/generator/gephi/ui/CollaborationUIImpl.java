/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.gephi.ui;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.JPanel;
import javax.swing.JSlider;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.lookup.ServiceProvider;
import org.uniba.fmph.knet.generator.Collaboration;
import org.uniba.fmph.knet.generator.gephi.CollaborationGenerator;
import org.uniba.fmph.knet.generator.gephi.CollaborationUI;

/**
 *
 * @author lenivo-pna
 */
@ServiceProvider(service = CollaborationUI.class)
public class CollaborationUIImpl extends AbstractGeneratorUI<CollaborationGenerator> implements CollaborationUI{

   private TextField sizeField;
   private TextField cField;
   private TextField initialSizeField;
   private JSlider kliqueSizeField;
   private JSlider newEdgesField;
   private JSlider initialdensityField;

    @Override
    public JPanel getPanel() {
       if (panel == null){
            panel = createPanel();
       }
        return CollaborationUIImpl.createValidationPanel(this);
    }
            
   public static JPanel createValidationPanel(CollaborationUIImpl innerPanel) {
       return innerPanel.panel;
//		ValidationPanel validationPanel = new ValidationPanel();
//		if (innerPanel == null)
//			innerPanel = new ErdosRenyiGnpPanel();
//		validationPanel.setInnerComponent(innerPanel);
//
//		ValidationGroup group = validationPanel.getValidationGroup();
//
//		group.add(innerPanel.nField, Validators.REQUIRE_NON_EMPTY_STRING,
//				new PositiveNumberValidator());
//		group.add(innerPanel.pField, Validators.REQUIRE_NON_EMPTY_STRING,
//				new BetweenZeroAndOneValidator());
//
//		return validationPanel;
	}
   
   @Override
   protected JPanel createPanel() {
     JPanel content = new JPanel();
     content.setLayout(new GridLayout(3,4));
     sizeField = addTextField(content, "Size", "1000");
     cField = addTextField(content, "c (ct cliques will be created)", "0.005");
     kliqueSizeField = addSlider(content, "Size of clique", 2,10,4,5);
     newEdgesField = addSlider(content, "New edges per time unit", 1,10,1,5);
     initialSizeField =  addTextField(content, "Initial size of random network", "50");
     initialdensityField = addSlider(content, "Initial density",0,100,20,10);               
     return content;
   }

   @Override
   public void setup(Generator generator) {
     super.setup(generator);
     sizeField.setText(generator().getNetworkSize().toString());
     cField.setText(generator().getC().toString());
     kliqueSizeField.setValue(generator().getCliqueSize());
     newEdgesField.setValue(generator().getNewEdges());
     initialSizeField.setText(generator().getInitialNetworkSize().toString());
     initialdensityField.setValue((int)(generator().getInitialNetworkDensity()*100));
   }

   @Override
   public void unsetup() {
     generator().setNetworkSize(Integer.parseInt(sizeField.getText()));
     generator().setC(Double.parseDouble(cField.getText()));
     generator().setCliqueSize(kliqueSizeField.getValue());
     generator().setNewEdges(newEdgesField.getValue());               
     generator().setInitialNetworkSize(Integer.parseInt(initialSizeField.getText()));
     generator().setInitialNetworkDensity((double)initialdensityField.getValue()/(double)100);
     super.unsetup();
   }
      
}