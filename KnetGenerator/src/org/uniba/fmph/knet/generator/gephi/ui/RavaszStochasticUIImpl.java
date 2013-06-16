/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uniba.fmph.knet.generator.gephi.ui;

import java.awt.GridLayout;
import java.awt.TextField;
import javax.swing.JPanel;
import javax.swing.JSlider;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.lookup.ServiceProvider;
import org.uniba.fmph.knet.generator.RavaszStochasticUI;
import org.uniba.fmph.knet.generator.gephi.RavaszStochasticGenerator;

/**
 *
 * @author lenivo-pna
 */
@ServiceProvider(service = RavaszStochasticUI.class)
public class RavaszStochasticUIImpl extends AbstractGeneratorUI<RavaszStochasticGenerator> implements RavaszStochasticUI{

  private TextField levels;
  private TextField pattern;
  private JSlider percents;           
            
  @Override
  protected JPanel createPanel () {
    JPanel content = new JPanel();
    content.setLayout(new GridLayout(3,2));
    levels = addTextField(content, "Levels", "2");
    pattern = addTextField(content, "PatternSize", "5");
    percents = addSlider(content, "Ratio", 0, 100, 60, 10);
    return content;
  }

  @Override
  public void setup(Generator gnrtr) {
    super.setup(gnrtr);
    levels.setText(new Integer(generator().getLevels()+1).toString());
    pattern.setText(new Integer(generator().getPattern()+1).toString());
    percents.setValue((int)(generator().getPercents()*100));
  }

  @Override
  public void unsetup() {
    generator().setLevels(Integer.parseInt(levels.getText())-1);
    generator().setPattern(Integer.parseInt(pattern.getText())-1);
    generator().setPercents(percents.getValue()/100.0);
  }

  @Override
    public JPanel getPanel() {
       if (panel == null){
            panel = createPanel();
       }
        return RavaszStochasticUIImpl.createValidationPanel(this);
    }
            
   public static JPanel createValidationPanel(RavaszStochasticUIImpl innerPanel) {
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
