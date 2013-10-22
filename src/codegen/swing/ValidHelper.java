package codegen.swing;

import java.awt.Color;

import javax.swing.JComponent;

import codegen.swing.widget.GTField;
import codegen.util.StringUtil;

public class ValidHelper {
   
   public final static Color DEFAULT_TEXT_COLOR = new Color(204, 232, 207);
   /**
    * 验证非空， 为空的时候返回true
    * @param obj
    * @param fieldName
    * @param parent
    * @return
    */
   public static boolean checkNotNull(GTField field, String fieldName, JComponent parent) {
      
      Color c = field.getValueControl().getBackground();
      if (StringUtil.isEmpty(field.getValue())) {
         field.getValueControl().setBackground(Color.ORANGE);
         //field.getValueControl().setToolTipText(Messages.getString("error.common.not.null", fieldName));
         return true;
      } else {
         resetFieldBackGround(field);
      }
      return false;
   }
   
   /**
    * 初始化Field默认底色
    * @param field
    */
   public static void resetFieldBackGround(GTField field) {
      field.getValueControl().setBackground(DEFAULT_TEXT_COLOR);
   }
}
