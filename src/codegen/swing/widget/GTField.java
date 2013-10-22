package codegen.swing.widget;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * 
 * @author zhajingzha
 * 
 */
public abstract class GTField {

   public static final Font DEFAULT_FONT = UIManager.getFont("Label.font");

   public static final int DEFAULT_FONT_SIZE = DEFAULT_FONT.getSize();
   
   protected JLabel _labelControl;

   protected String _label;
   
   protected boolean _require = false;

   protected String _desc;
   
   public JLabel getLabelControl() {
      return _labelControl;
   }

   public void setLabelControl(JLabel labelControl) {
      _labelControl = labelControl;
   }

   public String getLabel() {
      return _label;
   }

   public void setLabel(String label) {
      _label = label;
   }

   protected GTField() {
      this("undifined");
   }

   protected GTField(String label) {
      this._label = label;
      _labelControl = new JLabel((_require ? "*" : "") + _label + ":", JLabel.TRAILING);
   }
   
   protected GTField(String label, String desc) {
      this._label = label;
      this._desc = desc;
      _labelControl = new JLabel((_require ? "*" : "") +_label + ":", JLabel.TRAILING);
   }
   
   protected GTField(String label, String desc, boolean require) {
      this._label = label;
      this._desc = desc;
      this._require = require;
      _labelControl = new JLabel((_require ? "*" : "") +_label + ":", JLabel.TRAILING);
   }
   
   public abstract JComponent getValueControl();

   public abstract String getValue();
   
   public abstract void setValue(String value);

   public String getDesc() {
      return _desc;
   }

   public void setDesc(String desc) {
      _desc = desc;
   }

   public boolean isRequire() {
      return _require;
   }

   public void setRequire(boolean require) {
      _require = require;
   }
   
   public void setVisible(boolean aFlag) {
      
      this._labelControl.setVisible(aFlag);
      this.getValueControl().setVisible(aFlag);
   }
      
}
