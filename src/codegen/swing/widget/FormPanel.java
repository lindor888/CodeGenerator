
package codegen.swing.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


/**
 * 
 * @author zhajingzha
 *
 */
public class FormPanel extends JPanel {

   private List<GTField> _fields = new ArrayList<GTField>();

   public FormPanel() {
      super(new SpringLayout());
   }

   public void setMargin(int top, int left, int bottom, int right) {
      setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
   }

   public void add(GTTextField textField) {
      add(textField.getLabelControl());
      add(textField.getValueControl());
      _fields.add(textField);
   }

   public void add(GTCheckBox checkBox) {
      add(checkBox.getLabelControl());
      add(checkBox.getValueControl());

      _fields.add(checkBox);
   }
   
   public void add(GTComboBox comboBox) {
      add(comboBox.getLabelControl());
      add(comboBox.getValueControl());
      _fields.add(comboBox);
   }

   public List<GTField> getFields() {
      return _fields;
   }

   @Override
   public void removeAll() {
      super.removeAll();
      _fields.clear();
   }
}
