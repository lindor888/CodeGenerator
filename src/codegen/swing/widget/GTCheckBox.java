package codegen.swing.widget;

import javax.swing.JCheckBox;

/**
 * 
 * @author zhajingzha
 *
 */
public class GTCheckBox extends GTField {

   JCheckBox _valueControl;

   public GTCheckBox(String label, boolean checked) {
      super(label);
      _valueControl = new JCheckBox();
      _valueControl.setSelected(checked);
   }

   @Override
   public String getValue() {
      return String.valueOf(_valueControl.isSelected());
   }

   @Override
   public void setValue(String value) {
      if ("true".equalsIgnoreCase(value)) {
         _valueControl.setSelected(true);
      } else if ("false".equalsIgnoreCase(value)) {
         _valueControl.setSelected(false);
      }
   }

   public JCheckBox getValueControl() {
      return _valueControl;
   }

   public boolean isSelected() {
      return _valueControl.isSelected();
   }
}
