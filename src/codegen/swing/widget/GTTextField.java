
package codegen.swing.widget;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * 
 * @author zhajingzha
 *
 */
public class GTTextField extends GTField {

   static final Border DEFAULT_BORDER = new JTextField().getBorder();

   JTextField _valueControl;

   public GTTextField() {
      this("", "");
   }

   public GTTextField(String label) {
      this(label, "");
   }

   public GTTextField(String label, String value) {
      this(label, value, "");
   }

   public GTTextField(String label, String value, String desc) {
      super(label, desc);
      _valueControl = new JTextField(25);
      _valueControl.setText(value);
      _valueControl.setToolTipText(desc);
      _labelControl.setLabelFor(this._valueControl);
      
   }
   
   public GTTextField(String label, String value, String desc, boolean require) {
      super(label, desc, require);
      _valueControl = new JTextField(25);
      _valueControl.setText(value);
      _valueControl.setToolTipText(desc);
      _labelControl.setLabelFor(this._valueControl);
   }
   
   @Override
   public String getValue() {
      return _valueControl.getText();
   }

   @Override
   public void setValue(String value) {
      _valueControl.setText(value);
   }

   public JTextField getValueControl() {
      return _valueControl;
   }
}
