package codegen.swing.widget;

import javax.swing.JComboBox;

/**
 * 
 * @author zhajingzha
 *
 */
public class GTComboBox extends GTField {
   
   JComboBox _valueControl;

   public GTComboBox() {
      this("", new String[0]);
   }

   public GTComboBox(String label) {
      this(label, new String[0]);
   }

   public GTComboBox(String label, Object[] values) {
      super(label);

      _valueControl = new JComboBox(values);
      _labelControl.setLabelFor(this._valueControl);
   }
   
   public JComboBox getValueControl() {
      return _valueControl;
   }

   public void setSelectedItem(Object obj) {
      _valueControl.setSelectedItem(obj);
   }

   public void setSelectedIndex(int index) {
      _valueControl.setSelectedIndex(index);
   }

   public Object getSelectedItem() {
      return _valueControl.getSelectedItem();
   }

   @Override
   public String getValue() {
      Object selectedItem = getSelectedItem();
      return selectedItem == null ? null : selectedItem.toString();
   }

   @Override
   public void setValue(String value) {
      setSelectedItem(value);
   }

   public int getSelectedIndex() {
      return _valueControl.getSelectedIndex();
   }
  
}
