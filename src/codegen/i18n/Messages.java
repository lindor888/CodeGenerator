package codegen.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import codegen.util.StringUtil;


/**
 * 
 * @author zhajingzha
 *
 */
public class Messages {

   private static final String BUNDLE_NAME = "codegen.i18n.messages";
   
   private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.SIMPLIFIED_CHINESE);

   public static String getString(String key) {
      try {
         return RESOURCE_BUNDLE.getString(key);
      } catch (MissingResourceException e) {
         return '!' + key + '!';
      }
   }
   
   public static String getString(String key, String fieldName) {
      
      if (!StringUtil.isEmpty1(fieldName)) {
         String[] fieldsNames = new String[]{fieldName};
         return getString(key, fieldsNames);
      }
      return getString(key);
   }
   
   public static String getString(String key, String[] fieldNames) {
      try {
         String mes = RESOURCE_BUNDLE.getString(key);
         if (fieldNames != null && fieldNames.length > 0) {
            for (int i = 0; i < fieldNames.length; i++) {
               mes = StringUtil.replaceAllIgnoreCase(mes, "{" + i + "}", fieldNames[i]);
            }
         }
         return mes;
      } catch (MissingResourceException e) {
         return '!' + key + '!';
      }
   }
   
}
