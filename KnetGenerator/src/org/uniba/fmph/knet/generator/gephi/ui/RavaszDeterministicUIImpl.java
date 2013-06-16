/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.gephi.ui;

import java.awt.GridLayout;
import java.awt.TextField;
import javax.swing.JPanel;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.lookup.ServiceProvider;
import org.uniba.fmph.knet.generator.gephi.RavaszDeterministicGenerator;
import org.uniba.fmph.knet.generator.gephi.RavaszDeterministicUI;

/**
 *
 * @author lenivo-pna
 */
@ServiceProvider(service = RavaszDeterministicUI.class)
public class RavaszDeterministicUIImpl extends  AbstractGeneratorUI<RavaszDeterministicGenerator> implements RavaszDeterministicUI{

  private TextField levels;
  private TextField pattern;
            
            
  @Override
  protected JPanel createPanel() {
    JPanel content = new JPanel();
    content.setLayout(new GridLayout(2,2));
    levels = addTextField(content, "Levels", "2");
    pattern = addTextField(content, "Pattern size", "5");
    return content;
  }

  @Override
  public void setup(Generator gnrtr) {
    super.setup(gnrtr);
    levels.setText(new Integer(generator().getLevels()+1).toString());
    pattern.setText(new Integer((generator().getPattern()+1)).toString());
  }

  @Override
  public void unsetup() {
    generator().setLevels(Integer.parseInt(levels.getText())-1);
    generator().setPattern(Integer.parseInt(pattern.getText())-1);
  }
  
  @Override
    public JPanel getPanel() {
       if (panel == null){
            panel = createPanel();
       }
        return RavaszDeterministicUIImpl.createValidationPanel(this);
    }
            
   public static JPanel createValidationPanel(RavaszDeterministicUIImpl innerPanel) {
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
}
