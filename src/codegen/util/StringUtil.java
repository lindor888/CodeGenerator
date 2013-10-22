
package codegen.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author zhajingzha
 *
 */
public class StringUtil {
   protected static final Pattern VAR_PATTERN = Pattern.compile("(\\$\\{)[^}]{1,}(\\})");

   /**
    * DOCUMENT ME!
    */
   public static final char SINGLE_QUOTES = '\'';

   public static final int SUMMARY_MAX_LENGTH = 50;

   public static final int SUMMARY_SHORT_MAX_LENGTH = 20;

   private static final int TEXT_MAX_LENGTH = 30;
   /**
    * DOCUMENT ME!
    */
   public static final char DOUBLE_QUOTES = '"';

   public static final Random RANDOM = new Random();

   private static final char[] DIGI = "0123456789".toCharArray();

   private static final char[] UC_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

   private static final char[] DC_LETTER = "abcdefghijklmnopqrstuvwxyz".toCharArray();

   private static final char[] ASCII = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

   public static final String CHARSET_UTF8 = "UTF-8";

   public static final HashMap SPECIALCHAR_MAP = new HashMap();

   public static final String PDF_ENCODING_CP1252 = "Cp1252";

   public static final String PDF_ENCODING_UNIGB_UCS2 = "UniGB-UCS2";

   public static final String PDF_ENCODING_UNICNS_UCS2 = "UniCNS-UCS2";

   public static final String PADDING_FOR_NUMBER = "<span style='width:1'></span>";

   public static final String REGEX_EMAIL = "([\\w\\.\\-\\+]+?@[\\w\\.\\-]+?\\.[\\w\\.]{1,8})";
   
   //支持特殊字符分隔符
   private static final Pattern REGEX_PATTERN_3_END = Pattern.compile("\\\\\"\\s*[,\uff0c]|\\\\\"\\s*$");
   
   private static final Pattern REGEX_PATTERN_3_START = Pattern.compile("[,\uff0c]\\s*\\\\\"|^\\s*\\\\\"");
   
   private static final Pattern REGEX_PATTERN_2_END = Pattern.compile("\"\\s*[,\uff0c]|\"\\s*$");
   
   private static final Pattern REGEX_PATTERN_2_START = Pattern.compile("[,\uff0c]\\s*\"|^\\s*\"");
   
   private static final Pattern REGEX_PATTERN_3 = Pattern.compile("[,\uff0c]\\s*\\\\\"|\\\\\"\\s*[,\uff0c]");
   
   private static final Pattern REGEX_PATTERN_2 = Pattern.compile("[,\uff0c]\\s*\"|\"\\s*[,\uff0c]|^\\s*\\\\\".*\\\\\"\\s*$");
   
   private static final Pattern REGEX_PATTERN_1 = Pattern.compile("[,\uff0c]|^\\s*\".*\"\\s*$");
   
   public static final String REGEX_REPLACE_ROW = "<_7a2b6#c5d7e_>";  //由于ajax:autocomplete用到了分号作为分隔符，这里将名称中存在的分号转换成REGEX_REPLACE_ROW
   
   public static final String REGEX_REPLACE_COMMA = "<_7a2b#6c5d8e_>"; //由于ajax:autocomplete用到了分号作为分隔符，这里将名称中存在的分号转换成REGEX_REPLACE_ROW
   

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @param defaultValue
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   private static final Integer ZERO = Integer.valueOf(0);
   static {
      SPECIALCHAR_MAP.put("&darr;", generateStringByCode("-30,-122,-109"));
      SPECIALCHAR_MAP.put("&rarr;", generateStringByCode("-30,-122,-110"));
      SPECIALCHAR_MAP.put("&uarr;", generateStringByCode("-30,-122,-111"));
      SPECIALCHAR_MAP.put("&larr;", generateStringByCode("-30,-122,-112"));
      SPECIALCHAR_MAP.put("&times;", generateStringByCode("-61,-105"));
      SPECIALCHAR_MAP.put("&divide;", generateStringByCode("-61,-73"));
   }

   /**
    * If source String is null, then return object String,else not change;
    * 
    * @param srcStr -
    *           source String
    * @param objStr -
    *           object String
    */
   public static String nvl(String srcStr, String objStr) {
      if (srcStr == null || 0 == srcStr.trim().length() || "null".equalsIgnoreCase(srcStr.trim())) {
         return objStr;
      } else {
         return srcStr;
      }
   }

   /**
    * remove specified char <b>ch</b> from heading of string <b>str</b>. this
    * method will remove char from string head until the first char is not <b>ch</b>.
    * 
    * @param str
    * @param ch
    * @return null if str is null, or the worked string
    */
   public static String removeHeadingChar(String str, char ch) {
      if (str == null) {
         return str;
      }

      StringBuilder sb = new StringBuilder(str);

      while (sb.charAt(0) == ch) {
         sb.deleteCharAt(0);
      }

      return sb.toString();
   }

   /**
    * Return the original uri
    * 
    * @param uri
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static StringBuffer getOriginalUri(String uri) {
      if (uri == null) {
         throw new IllegalArgumentException("Null uri found.");
      }
      int numIndex = uri.indexOf(".do");
      int lastOne = uri.lastIndexOf("/");

      if (lastOne <= -1) {
         return null;
      }

      if (numIndex <= -1) {
         numIndex = uri.length();
      }

      String originalUri = null;

      if (lastOne + 1 > numIndex) {
         return null;
      }

      originalUri = uri.substring(lastOne + 1, numIndex);
      return new StringBuffer(originalUri);
   }

   public static StringBuffer getFullUri(String uri) {
      if (uri == null) {
         throw new IllegalArgumentException("Null uri found.");
      }
      if (uri.startsWith("/")) {
         uri = uri.substring(1);
      }

      int firstOne = uri.indexOf("/");

      if (firstOne <= -1) {
         return null;
      }

      String fullUri = null;

      if (firstOne + 1 > uri.length()) {
         return null;
      }

      fullUri = uri.substring(firstOne);
      return new StringBuffer(fullUri);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param value
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String addQuote(String value) {
      StringBuilder str = new StringBuilder();

      if (null == value) {
         return null;
      }

      str.append("\'").append(value).append("\'");

      return str.toString();
   }

   public static String toSqlInString(Collection col) {
      if (col == null) {
         return null;
      }

      return toSqlInString(col.toArray());
   }

   /**
    * DOCUMENT ME!
    * 
    * @param col
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toSqlInString(Object[] col) {
      if (null == col) {
         return null;
      }
      int index = 0;
      StringBuilder buf = new StringBuilder();
      Object value = null;
      while (index < col.length) {
         value = col[index];
         if (value == null) {
            continue;
         }
         if (index != 0) {
            buf.append(",");
         }
         buf.append(addQuote(String.valueOf(value)));
         index++;
      }
      return buf.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param str
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toSqlInString(String[] str) {
      if (null == str) {
         return null;
      }

      int index = 0;
      StringBuilder buf = new StringBuilder();

      while (index < str.length) {
         if (index != 0) {
            buf.append(",");
         }

         buf.append(addQuote(str[index]));
         index++;
      }

      return buf.toString();
   }

   public static String toSqlInTrimString(String[] str) {
      if (null == str) {
         return null;
      }

      int index = 0;
      StringBuilder buf = new StringBuilder();

      while (index < str.length) {
         if (str[index] == null) {
            continue;
         }
         if (index != 0) {
            buf.append(",");
         }
         buf.append(addQuote(str[index].trim()));
         index++;
      }

      return buf.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param value
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   /*
    * public static String getComment(String value) { if (null == value) {
    * return null; } int cols = 80; int colsNum = cols / 2; int idx = -1;
    * StringBuilder sb = new StringBuilder(); int mark = 0; int oldIdx = 0;
    * while (true) { mark = Math.min(value.length(), (oldIdx + colsNum)); idx =
    * value.indexOf('\n', oldIdx); if (sb.length() > 0) { sb.append('\n'); } if
    * (idx < 0) { sb.append(value.substring(oldIdx, mark)); oldIdx = mark; }
    * else { if (idx > mark) { sb.append(value.substring(oldIdx, mark)); oldIdx =
    * mark; } else { sb.append(value.substring(oldIdx, idx)); oldIdx = idx + 1; } }
    * if (oldIdx > value.length()) { break; } } return sb.toString(); }
    */

   /**
    * eval the src object's string value. if src is null, return alternate
    * string.
    * 
    * @param src
    *           the object to be eval.
    * @param alt
    *           alternate string if src is null.
    * @return src's string value or alt if src is null.
    */
   public static String nvl(Object src, String alt) {
      if (src == null) {
         return alt;
      } else {
         /*
          * try { return (String)srcStr; } catch (Exception e) { return
          * srcStr.toString(); }
          */
         return StringUtil.nvl(src.toString(), alt);
      }
   }

   /**
    * check if the strStr is <code>null</code> or an empty string or is
    * literal "null".
    * 
    * @param srcStr
    *           string to check
    * @return
    */
   public static boolean isEmpty(Object srcStr) {
      // return null == srcStr || srcStr.equals("null");
      return nvl(srcStr, "").trim().length() == 0 || nvl(srcStr, "").equals("null");
   }

   public static boolean isInt(Object srcStr) {
      // return null == srcStr || srcStr.equals("null");
      if (srcStr == null) {
         return false;
      }
      String s = srcStr.toString().replaceAll("(\\s)", "");
      Pattern p = Pattern.compile("([-]?[\\d]+)");
      Matcher m = p.matcher(s);
      return m.matches();
      // return nvl(srcStr, "").trim().length() == 0 || nvl(srcStr,
      // "").equals("null");
   }

   /**
    * DOCUMENT ME!
    * 
    * @param src
    *           DOCUMENT ME!
    * @param trimStr
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isEmpty(Object src, boolean trimStr) {
      String tmpStr = nvl(src, "");
      tmpStr = trimStr ? tmpStr.trim() : tmpStr;

      return isEmpty(tmpStr);
   }

   public static boolean isEmptyStringArray(String s, String splitter) {
      if (s == null || s.trim().equals("")) {
         return true;
      }
      // s != null && !s.equals("")
      if (splitter == null) {
         return false;
      }
      String[] strs = s.split(splitter.trim());
      for (int i = 0; i < strs.length; i++) {
         if (strs[i] != null && !strs[i].trim().equals("")) {
            return false;
         }
      }
      return true;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String trim(Object srcStr) {
      if (srcStr != null) {
         return srcStr.toString().trim();

         // return ((String)srcStr).trim();
      }

      return null;
   }

   public static String trimLeft(String srcStr) {
      if (srcStr != null && srcStr.length() > 0) {
         int i = 0;
         for (; i < srcStr.length(); i++) {
            char c = srcStr.charAt(i);
            if (c != ' ' && c != '\n' && c != '\r' && c != '\t') {
               break;
            }
         }
         if (i > 0) {
            return srcStr.substring(i);
         } else {
            return srcStr;
         }
      }

      return srcStr;
   }

   /**
    * @param str
    * @param trimChar
    * @return
    * @author ericchen
    */
   public static String trimLeft(String str, char trimChar) {
      if (str == null) {
         return null;
      }
      if (trimChar == ' ') {
         return str.trim();
      }
      int len = str.length();
      int st = 0;
      int off = 0; /* avoid getfield opcode */
      char[] val = str.toCharArray(); /* avoid getfield opcode */

      while ((st < len) && (val[off + st] == trimChar)) {
         st++;
      }

      return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
   }

   /**
    * @param str
    * @param trimChar
    * @return
    * @author ericchen
    */
   public static String trimRight(String str, char trimChar) {
      if (str == null) {
         return null;
      }
      if (trimChar == ' ') {
         return str.trim();
      }
      int len = str.length();
      int st = 0;
      int off = 0; /* avoid getfield opcode */
      char[] val = str.toCharArray(); /* avoid getfield opcode */

      while ((st < len) && (val[off + len - 1] == trimChar)) {
         len--;
      }
      return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
   }

   /**
    * @param str
    * @param trimChar
    * @return
    * @author ericchen
    */
   public static String trim(String str, char trimChar) {
      return trimRight(trimLeft(str, trimChar), trimChar);
   }

   /**
    * @param str
    * @return
    * @author ericchen
    */
   public static String trimRightComma(String str) {
      return trimRight(trimRight(str, ','), ',');
   }

   public static String trimRightEnter(String srcStr) {
      srcStr = trimRight(srcStr);
      boolean needPs = false;
      if (srcStr != null && srcStr.length() > 0) {
         int i = 0;
         for (; i < srcStr.length(); i++) {
            char c = srcStr.charAt(i);
            if (c == '\n' || c == '\r') {
               needPs = true;
               break;
            }
         }
         if (i < srcStr.length() - 1) {
            if (needPs) {
               return srcStr.substring(0, i) + "...";
            }
            return srcStr.substring(0, i);
         } else {
            return srcStr;
         }
      }

      return srcStr;
   }

   public static String trimRightEnterWithNoPoints(String srcStr) {
      srcStr = trimRight(srcStr);
      if (srcStr != null && srcStr.length() > 0) {
         int i = 0;
         for (; i < srcStr.length(); i++) {
            char c = srcStr.charAt(i);
            if (c == '\n' || c == '\r' || c == '\t' || c == ' ') {
               break;
            }
         }
         if (i < srcStr.length() - 1) {
            return srcStr.substring(0, i);
         } else {
            return srcStr;
         }
      }

      return srcStr;
   }

   public static String trimRight(String srcStr) {
      if (srcStr != null && srcStr.length() > 0) {
         int i = srcStr.length() - 1;
         for (; i >= 0; i--) {
            char c = srcStr.charAt(i);
            if (c != ' ' && c != '\n' && c != '\r' && c != '\t') {
               break;
            }
         }
         if (i == -1) { // added by macroliu at 2007-8-3 for CR 25369
            return "";
         } else if (i < srcStr.length() - 1) {
            return srcStr.substring(0, i + 1);
         } else {
            return srcStr;
         }
      }

      return srcStr;
   }

   public static int toInt(Object srcStr, int defaultValue) {
      try {
         if (srcStr != null && StringUtil.isInt(srcStr)) {
            String s = srcStr.toString().replaceAll("(\\s)", "");
            return s.length() > 0 ? Integer.parseInt(s) : defaultValue;
         }
      } catch (Exception e) {
         ;
         // Simply skip.
      }
      return defaultValue;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @param defaultValue
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static Integer toInteger(Object srcStr, Integer defaultValue) {
      try {
         if (srcStr != null && StringUtil.isInt(srcStr)) {
            String s = srcStr.toString().replaceAll("(\\s)", "");
            return s.length() > 0 ? Integer.valueOf(s) : defaultValue;
         }
         // return Integer.valueOf(srcStr.toString().replaceAll("(\\s)", ""));
      } catch (Exception e) {
         /*
          * if (null == defaultValue) { defaultValue = ZERO; } return
          * defaultValue;
          */
         ;
      }
      return defaultValue;
   }

   /**
    * convert a string to double value.
    * 
    * @param srcStr
    * @return a double value or 0.0 if cant convert.
    * @see #convertStrToDouble(String, Double)
    */
   public static Double convertStrToDouble(String srcStr) {
      return convertStrToDouble(srcStr, 0.0);
   }

   /**
    * convert a object to double value.
    * <p>
    * null => defaultValue<br/> "abc" => defaultValue<br/> "123.0" => 123.0<br/> "
    * 123.4 " => 123.4<br/> "1,234.5" => 1234.5<br/>
    * </p>
    * 
    * @param srcStr
    * @param defaultValue
    * @return a double value or <code>defaultValue</code> if cant convert.
    */
   public static Double convertStrToDouble(Object srcStr, Double defaultValue) {
      if (null == srcStr) {
         return defaultValue;
      }
      try {
         String s = srcStr.toString().replace(",", "");
         return Double.valueOf(s.trim());
      } catch (Exception e) {
         return defaultValue;
      }
   }

   /**
    * convert a object to double value.
    * <p>
    * null => defaultValue<br/> "" => defaultValue<br/> "abc" => defaultValue<br/>
    * "123.0" => 123.0<br/> " 123.4 " => 123.4<br/> "1,234.5" => 1234.5<br/>
    * </p>
    * 
    * @param srcStr
    * @param defaultValue
    * @return a double value or <code>defaultValue</code> if cant convert.
    */
   public static Double convertStrToDouble2(Object srcStr, Double defaultValue) {
      if (isEmpty(srcStr)) {
         return defaultValue;
      }
      try {
         String s = srcStr.toString().replace(",", "");
         return Double.valueOf(s.trim());
      } catch (Exception e) {
         return defaultValue;
      }
   }

   /**
    * convert a string to integer.
    * 
    * @param str
    * @return a int value or 0 if cant convert.
    * @see #convertStrToInteger(Object, Integer)
    */
   public static Integer convertStrToInteger(String str) {
      return convertStrToInteger(str, 0);
   }

   /**
    * convert a string to integer.
    * <p>
    * null => defaultValue<br/> "abc" => defaultValue<br/> "123" => 123<br/> "
    * 123 " => 123<br/> "1,234,567" => 1234567<br/>
    * </p>
    * 
    * @param str
    * @param defaultValue
    * @return a int value or <code>defaultValue</code> if cant convert.
    */
   public static Integer convertStrToInteger(Object str, Integer defaultValue) {
      if (null == str) {
         return defaultValue;
      }
      try {
         String s = str.toString().replace(",", "");
         return Integer.valueOf(s.trim());
      } catch (Exception e) {
         return defaultValue;
      }
   }

   public static Boolean convertStrToBoolean(Object str, Boolean defaultValue) {
      if (null == str) {
         return defaultValue;
      }
      try {
         String s = str.toString().replace(",", "");
         return Boolean.valueOf(s.trim());
      } catch (Exception e) {
         return defaultValue;
      }
   }

   /**
    * convert a string to float.
    * 
    * @param str
    * @return a float value or 0.0f if cant convert.
    * @see #convertStrToFloat(Object, Float)
    */
   public static Float convertStrToFloat(String str) {
      return convertStrToFloat(str, 0.0f);
   }

   /**
    * convert a object to float.
    * <p>
    * null => defaultValue<br/> "abc" => defaultValue<br/> "123.0" => 123.0f<br/> "
    * 123.0 " => 123.0f<br/> "1,234.0" => 1234.0f<br/>
    * </p>
    * 
    * @param srcStr
    * @return a float value or <code>defaultValue</code> if cant convert.
    * @return
    */
   public static Float convertStrToFloat(Object srcStr, Float defaultValue) {
      if (null == srcStr) {
         return defaultValue;
      }
      try {
         String s = srcStr.toString().replace(",", "");
         return Float.valueOf(s.trim());
      } catch (Exception e) {
         return defaultValue;
      }
   }

   public static Long convertStrToLong(Object srcStr, Long defaultValue) {
      if (null == srcStr) {
         return defaultValue;
      }
      try {
         String s = srcStr.toString().replace(",", "");
         return Long.valueOf(s.trim());
      } catch (Exception e) {
         return defaultValue;
      }
   }

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @param defaultValue
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static long toLong(Object srcStr, long defaultValue) {
      try {
         if (srcStr != null) {
            return Long.parseLong(srcStr.toString());
         }
      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @param defaultValue
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static double toDouble(Object srcStr, double defaultValue) {
      try {
         if (srcStr != null) {
            return Double.valueOf(srcStr.toString().replaceAll(",", ""));
         }
      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }

   public static Double toDouble(Object srcStr, Double defaultValue) {
      try {
         if (srcStr != null) {
            return Double.valueOf(srcStr.toString().replaceAll(",", ""));
         }

      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @param defaultValue
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static float toFloat(Object srcStr, float defaultValue) {
      try {
         if (srcStr != null) {
            return Float.parseFloat(srcStr.toString());
         }
      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @param defaultValue
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean toBoolean(Object srcStr, boolean defaultValue) {
      try {
         if (srcStr != null) {
            return Boolean.parseBoolean(trim(srcStr.toString()));
         }
      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }

   public static boolean toBooleanFlag(Object srcStr, boolean defaultValue) {
      try {
         if (srcStr != null) {
            return trim(srcStr.toString()).equalsIgnoreCase("true") || trim(srcStr.toString()).equalsIgnoreCase("1") ||
               trim(srcStr.toString()).equalsIgnoreCase("on");
         }
      } catch (Exception e) {
         ;
      }
      return defaultValue;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param str
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    * @throws Exception
    *            DOCUMENT ME!
    */
   public static Integer parseFirstInt(String str) {
      char[] divs = {' ', ':'};

      StringBuilder intStr = new StringBuilder();

      if (null != str) {
         str = str.trim();

         boolean match = false;

         for (int i = 0; i < str.length(); i++) {
            match = false;

            char c = str.charAt(i);

            if (c < '0' || c > '9') {
               for (int j = 0; j < divs.length; j++) {
                  if (divs[j] == c) {
                     match = true;

                     break;
                  }
               }

               if (match) {
                  break;
               } else {
                  return null;
               }
            } else {
               intStr.append(c);
            }
         }

         if (intStr.length() > 0) {
            try {
               return Integer.valueOf(intStr.toString());
            } catch (Exception ex) {
               return null;
            }
         }
      }

      return null;
   }

   /**
    * If source String is blank, then return objectString,else not change;
    * 
    * @param srcStr -
    *           source String
    * @param objStr -
    *           object String
    */
   public static String blankToOtherString(String srcStr, String objStr) {
      if ((srcStr == null) || (0 == srcStr.trim().length())) {
         return objStr;
      } else {
         return srcStr;
      }
   }

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @param format
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static Integer[] split(String srcStr, String format) {
      if (nvl(srcStr, "").length() == 0) {
         return null;
      } else {
         String[] strList = srcStr.split(format);

         Integer[] intList = new Integer[strList.length];

         String tmp = null;

         for (int i = 0; i < strList.length; i++) {
            tmp = strList[i].trim();
            if (!isEmpty(tmp)) {
               intList[i] = new Integer(tmp);
            }
         }

         return intList;
      }
   }

   public static Double[] splitToDouble(String srcStr, String splitter) {
      if (nvl(srcStr, "").length() == 0) {
         return null;
      } else {
         String[] strList = srcStr.split(splitter);
         Double[] intList = new Double[strList.length];
         String tmp = null;
         for (int i = 0; i < strList.length; i++) {
            tmp = strList[i].trim();
            if (!isEmpty1(tmp)) {
               intList[i] = new Double(tmp);
            }
         }
         return intList;
      }
   }

   public static Object[] splitIgnoreEmptyStr(String srcStr, String format) {
      if (nvl(srcStr, "").length() == 0) {
         return null;
      } else {
         String[] strList = srcStr.split(format);
         List<Integer> intList = new ArrayList<Integer>();

         // Integer[] intList = new Integer[strList.length];

         String tmp = null;

         for (int i = 0; i < strList.length; i++) {
            tmp = strList[i].trim();
            if (!isEmpty(tmp) && !tmp.equalsIgnoreCase("undefined")) {
               intList.add(new Integer(tmp));
            }
         }

         return (Object[]) (intList.toArray());
      }
   }

   public static List transferToListIgnoreEmptyStr(String srcStr, String format) {
      if (nvl(srcStr, "").length() == 0) {
         return null;
      } else {
         String[] strList = srcStr.split(format);
         List<Integer> intList = new ArrayList<Integer>();

         // Integer[] intList = new Integer[strList.length];

         String tmp = null;

         for (int i = 0; i < strList.length; i++) {
            tmp = strList[i].trim();
            if (!isEmpty(tmp)) {
               intList.add(new Integer(tmp));
            }
         }

         return intList;
      }
   }

   public static String[] splitString(String srcStr) {
      if (nvl(srcStr, "").length() == 0) {
         return null;
      } else {
         return srcStr.split(Constants.SPLITTER_COL);
      }
   }

   public static String[] splitStringWithComma(String srcStr) {
      if (nvl(srcStr, "").length() == 0) {
         return null;
      } else {
         return srcStr.split(Constants.SPLITTER_COMMA);
      }
   }

   public static String[] splitStringWithEnZhComma(String srcStr) {
      if (isEmpty1(srcStr)) {
         return null;
      } else {
         return srcStr.split("[,\uff0c]");
      }
   }

   public static String[] splitStringWithAnd(String srcStr) {
      if (nvl(srcStr, "").length() == 0) {
         return null;
      } else {
         return srcStr.split(Constants.SPLITTER_AND);
      }
   }

   /**
    * DOCUMENT ME!
    * 
    * @param srcStr
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static Integer[] split(String srcStr) {
      return split(srcStr, Constants.SPLITTER_COL);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String convertQuoteSymbol(String s) {
      if (s == null) {
         return null;
      }

      StringBuilder stringBuffer = new StringBuilder();

      int length = s.length();

      for (int i = 0; i < length; i++) {
         char c = s.charAt(i);

         if (c == '\'') {
            stringBuffer.append("''");
         } else {
            stringBuffer.append(c);
         }
      }

      return stringBuffer.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @param properties
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    * @throws Exception 
    * @throws Exception
    *            DOCUMENT ME!
    */
   public static String replaceVariables(String s, Map properties) throws Exception {
      if (s == null) {
         return s;
      }

      if (properties == null) {
         throw new IllegalArgumentException("Cannot perform function"
            + " replaceVariables(String, Map) with null Map value.");
      }

      int last = 0;

      Matcher m = VAR_PATTERN.matcher(s);

      StringBuilder b = null;

      while (m.find()) {
         if (b == null) {
            b = new StringBuilder();
         }

         b.append(s.substring(last, m.start()));

         String n = s.substring(m.start() + 2, m.end() - 1);

         String v = StringUtil.toString(properties.get(n));

         if (v == null) {
            throw new Exception("Cannot find variable:" + n);
         }

         b.append(v);

         last = m.end();
      }

      if (last != 0) {
         b.append(s.substring(last));

         return b.toString();
      } else {
         return s;
      }
   }

   /**
    * generate a random sting by [0-9A-Za-z]
    * 
    * @param length
    *           string length.
    * @return
    */
   public static String randomString(int length) {
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < length; i++) {
         sb.append(ASCII[RANDOM.nextInt(ASCII.length)]);
      }

      return sb.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param fullPath
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String getFileName(String fullPath) {
      if (fullPath == null) {
         return null;
      }

      fullPath = fullPath.replace('\\', '/');

      File file = new File(fullPath);

      return file.getName();
   }


   public static Date toDate(Object srcStr, String formatStr) {
      if (StringUtil.isEmpty(srcStr)) {
         return null;
      }

      ParsePosition pp = new ParsePosition(0);

      SimpleDateFormat sdf = new SimpleDateFormat();

      sdf.applyPattern(formatStr);

      return sdf.parse((String) srcStr, pp);
   }


   public static Date toDate(Object srcStr, String formatStr, int pot) {
      if (StringUtil.isEmpty(srcStr)) {
         return null;
      }

      ParsePosition pp = new ParsePosition(pot);

      SimpleDateFormat sdf = new SimpleDateFormat();

      sdf.applyPattern(formatStr);

      return sdf.parse((String) srcStr, pp);
   }


   public static Date toDate(Object srcStr, String formatStr, int pot, boolean lenient) {
      if (srcStr == null) {
         return null;
      }

      ParsePosition pp = new ParsePosition(pot);
      SimpleDateFormat sdf = new SimpleDateFormat();
      sdf.setLenient(lenient);
      sdf.applyPattern(formatStr);
      return sdf.parse((String) srcStr, pp);
   }


   public static String toString(Object obj) {
      if (null == obj) {
         return "";
      }

      return obj.toString();
   }


   public static String xmlEscape(String s) {
      if (s == null || "".equals(s)) {
         return "";
      }

      String rtnStr = null;
      rtnStr = s.replaceAll("\"", "&quot;");
      //rtnStr = rtnStr.replaceAll("'", "&apos;");
      rtnStr = rtnStr.replaceAll("\'", "\\&#39;");
      rtnStr = rtnStr.replaceAll("<", "&lt;");
      rtnStr = rtnStr.replaceAll(">", "&gt;");
      // rtnStr = rtnStr.replaceAll("&", "&amp;");
      return rtnStr;
   }

   public static String xmlEscapeIncludeAnd(String s) {
      if (s == null || "".equals(s)) {
         return "";
      }

      String rtnStr = null;
      rtnStr = s.replaceAll("\"", "&quot;");
      //rtnStr = rtnStr.replaceAll("'", "&apos;");
      rtnStr = rtnStr.replaceAll("\'", "\\&#39;");
      rtnStr = rtnStr.replaceAll("<", "&lt;");
      rtnStr = rtnStr.replaceAll(">", "&gt;");
      rtnStr = rtnStr.replaceAll("&", "&amp;");
      return rtnStr;
   }

   /**
    * Return xml unescape string.
    * 
    * @param s
    * @return
    * @author ericchen
    */
   public static String xmlUnescape(String s) {
      if (s == null || "".equals(s)) {
         return "";
      }

      String rtnStr = null;
      rtnStr = s.replaceAll("&quot;", "\"");
      //rtnStr = rtnStr.replaceAll("&apos;", "'");
      rtnStr = rtnStr.replaceAll("\\&#39;", "'");
      rtnStr = rtnStr.replaceAll("&lt;", "<");
      rtnStr = rtnStr.replaceAll("&gt;", ">");
      rtnStr = rtnStr.replaceAll("&amp;", "&");
      return rtnStr;
   }

   /**
    * This method is used to handle special characters in value of javascript
    * variable.
    * 
    * @param s
    * @return
    * @author ericchen
    */
   public static String javascriptEscape(String s) {
      if (s == null) {
         return s;
      }

      StringBuilder filtered = new StringBuilder(s.length());
      char prevChar = '\u0000';
      char c;
      for (int idx = 0; idx < s.length(); idx++) {
         c = s.charAt(idx);
         if (c == '"') {
            filtered.append("\\\"");
         } else if (c == '\'') {
            filtered.append("\\'");
         } else if (c == '\\') {
            filtered.append("\\\\");
         } else if (c == '\t') {
            filtered.append("\\t");
         } else if (c == '\n') {
            if (prevChar != '\r') {
               filtered.append("\\n");
            }
         } else if (c == '\r') {
            filtered.append("\\n");
         } else if (c == '\f') {
            filtered.append("\\f");
         } else if (c == '/') {
            filtered.append("\\/");
         } else {
            filtered.append(c);
         }
         prevChar = c;
      }
      return filtered.toString();
   }

   /**
    * This method is used to handle special characters in HTML TAG's value.
    * 
    * @param s
    * @return
    * @author ericchen
    */
   public static String htmlTagValueEscape(String s) {
      String s1 = javascriptEscape(s);
      s1 = s1.replaceAll("\"", "&quot;");
      //s1 = s1.replaceAll("\'", "&apos;");
      s1 = s1.replaceAll("\'", "\\&#39;");
      s1 = s1.replaceAll("<", "&lt;");
      s1 = s1.replaceAll(">", "&gt;");
      return s1;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toHTMLString(String s) {
      if (s == null || "".equals(s)) {
         return "";
      }

      String rtnStr = null; // s.replaceAll("&", "&amp;");
      // rtnStr = rtnStr.replaceAll(" ", "&nbsp;");
      rtnStr = s.replaceAll("\"", "&quot;");
      rtnStr = rtnStr.replaceAll("<", "&lt;");
      rtnStr = rtnStr.replaceAll(">", "&gt;");
      rtnStr = rtnStr.replaceAll("\r\n", "<br/>");
      rtnStr = rtnStr.replaceAll("\r", "<br/>");
      rtnStr = rtnStr.replaceAll("\n", "<br/>");
      //rtnStr = rtnStr.replaceAll("'", "&acute;");
      rtnStr = rtnStr.replaceAll("'", "\\&#39;");
      return rtnStr;
   }

   public static String toHTMLStringForDisplayName(String s) {
      if (s == null || "".equals(s)) {
         return "";
      }

      String rtnStr = null; // s.replaceAll("&", "&amp;");
      // rtnStr = rtnStr.replaceAll(" ", "&nbsp;");
      rtnStr = s.replaceAll("\"", "&quot;");
      rtnStr = rtnStr.replaceAll("<", "&lt;");
      rtnStr = rtnStr.replaceAll(">", "&gt;");
      rtnStr = rtnStr.replaceAll("\r\n", "<br/>");
      rtnStr = rtnStr.replaceAll("\r", "<br/>");
      rtnStr = rtnStr.replaceAll("\n", "<br/>");
      // rtnStr = rtnStr.replaceAll("'", "&acute;");
      // rtnStr = rtnStr.replaceAll("'", "\\&#39;");
      return rtnStr;
   }

   public static String toHTMLStringWithSpace(String s) {
      String rtnStr = toHTMLString(s);
      return rtnStr.replaceAll(" ", "&nbsp;");

   }

   public static String specialQuotesReplace(String s) {
      if (s == null || "".equals(s)) {
         return "";
      }
      String rtnStr = null;
      rtnStr = s.replaceAll("´", "'");

      return rtnStr;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    * @author kevinyu This function was created for report, please MAKE SURE it
    *         is useful for you;
    */
   public static String htmlToPlainString(String str) {
      if (str == null) {
         return "";
      }

      String retValue = str;

      retValue = retValue.replaceAll("<br/>", "\n");
      retValue = retValue.replaceAll("&nbsp;", " ");
      retValue = retValue.replaceAll("&nbsp", " ");
      retValue = retValue.replaceAll("&acute;", "`");
      //retValue = retValue.replaceAll("&apos;", "\'");
      retValue = retValue.replaceAll("\\&#39;", "\'");
      retValue = retValue.replaceAll("&quot;", "\"");
      retValue = retValue.replaceAll("&gt;", ">");
      retValue = retValue.replaceAll("&lt;", "<");
      retValue = retValue.replaceAll("&amp;", "&");

      return retValue;
   }

   /**
    * @param str
    * @return
    */
   public static String convertHtmlToPlainText(String str) {
      if (str == null) {
         return "";
      }

      String retValue = str;

      retValue = retValue.replaceAll("&nbsp;", " ");
      retValue = retValue.replaceAll("&acute;", "`");
      //retValue = retValue.replaceAll("&apos;", "\'");
      retValue = retValue.replaceAll("\\&#39;", "\'");
      retValue = retValue.replaceAll("&quot;", "\"");
      retValue = retValue.replaceAll("&gt;", ">");
      retValue = retValue.replaceAll("&lt;", "<");
      retValue = retValue.replaceAll("&amp;", "&");
      retValue = retValue.replaceAll("&ldquo;", "\"");
      retValue = retValue.replaceAll("&rdquo;", "\"");

      retValue = retValue.replaceAll("&rarr;", "->");
      retValue = retValue.replaceAll((String) SPECIALCHAR_MAP.get("&rarr;"), "->");
      retValue = retValue.replaceAll("&larr;", "<-");
      retValue = retValue.replaceAll((String) SPECIALCHAR_MAP.get("&larr;"), "<-");
      return retValue;
   }

   /**
    * Input Output <Font COLOR = RED>Overdue</FONT> Overdue
    * <TD><FONT color=red>(102% CENT)</FONT></TD>
    * (102% CENT)
    * 
    * @param value
    * @return
    */
   public static String trimHtmlInStatusValue(String value) {
      if (value == null) {
         return null;
      }
      Pattern pattern = Pattern.compile("<[^<|^>]*>");
      Matcher matcher = pattern.matcher(value);
      value = matcher.replaceAll("");
      return htmlToPlainString(value);
   }

   /**
    * Input Output <Font COLOR = RED>Overdue</FONT> true <Font COLOR =
    * 'RED'>Overdue</FONT> true <font color = red>Overdue</font> true Not Yet
    * Due false
    * 
    * @param status
    * @return
    */
   public static boolean isRedStatus(String status, List<String> keyWords) {
      if (status == null || keyWords == null) {
         return false;
      }
      StringBuilder regex = new StringBuilder();
      regex.append(".*");
      for (int i = 0; i < keyWords.size(); i++) {
         regex.append(keyWords.get(i));
         if (i != keyWords.size()) {
            regex.append(".+");
         }
      }
      regex.append(".*");
      Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
      Matcher m = pattern.matcher(status);

      return m.matches();
   }

   public static boolean isRedStatus(String status) {
      List keyWords = new ArrayList();
      keyWords.add("FONT");
      keyWords.add("COLOR");
      keyWords.add("RED");

      List keyWords1 = new ArrayList();
      keyWords1.add("CLASS");
      keyWords1.add("REDTEXT");

      return isRedStatus(status, keyWords) || isRedStatus(status, keyWords1);
   }

   public static String generateStringByCode(String code) {
      try {
         if (code == null) {
            return "";
         }
         String[] byteStr = code.split(",");
         byte[] bytes = new byte[byteStr.length];
         for (int i = 0; i < bytes.length; i++) {
            int value = Integer.parseInt(byteStr[i]);
            bytes[i] = (byte) value;
         }
         return new String(bytes, CHARSET_UTF8);
      } catch (Exception ex) {
         return "";
      }
   }

   public static String trimAll(String res, List<String> trimList) {
      if (res == null) {
         return null;
      }
      for (String trim : trimList) {
         res = res.replaceAll(trim, "");
      }

      return res;
   }

   public static String generateString(int depth) {
      return generateString(depth, " ");
   }

   public static String generateString(int depth, String blanks) {
      if (depth < 0) {
         depth = 0;
      }
      StringBuilder buffer = new StringBuilder();
      for (int i = 0; i < depth; i++) {
         buffer.append(blanks);
      }

      return buffer.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toHtml(String s) {
      return toHTMLString(s);
   }

   public static String toHtmlForTextarea(String s) {
      if (s == null) {
         return "";
      }

      String rtnStr = null;
      rtnStr = s.replaceAll("&", "&amp;");
      rtnStr = rtnStr.replaceAll("\"", "&quot;");
      rtnStr = rtnStr.replaceAll("<", "&lt;");
      rtnStr = rtnStr.replaceAll(">", "&gt;");
      // rtnStr = rtnStr.replaceAll("\r\n", "<br/>");
      rtnStr = rtnStr.replaceAll("'", "&acute;");
      return rtnStr;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String replaceQuote(String s) {
      if (s == null) {
         return "";
      }

      String rtnStr = s.replaceAll("\"", "\\\\\"");
      rtnStr = rtnStr.replaceAll("'", "\\\\'");
      return rtnStr;
   }

   public static String replaceQuote2(String s) {
      if (s == null) {
         return "";
      }
      String rtnStr = s.replaceAll("\"", "&quot;");
      return rtnStr;
   }

   public static String replaceAcute(String s) {
      if (s == null) {
         return "";
      }

      String rtnStr = s.replaceAll("'", "\\\\'");
      return rtnStr;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toCSVString(String s) {
      if (s == null) {
         return "";
      }

      if (s.indexOf("\"") >= 0) {
         s = s.replaceAll("\"", "\"\"");
      }

      if ((s.indexOf(",") >= 0) || (s.indexOf("\"") >= 0) || (s.indexOf("\n") >= 0)) {
         s = "\"" + s + "\"";
      }

      if (s.indexOf("&") >= 0) {
         s = s.replaceAll("&", "&amp;");
      }

      if (s.indexOf("<") >= 0) {
         s = s.replaceAll("<", "&lt;");
      }

      if (s.indexOf(">") >= 0) {
         s = s.replaceAll(">", "&gt;");
      }

      return s;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param originalStr
    *           DOCUMENT ME!
    * @param beReplaceSubStr
    *           DOCUMENT ME!
    * @param replaceSubStr
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String replaceString(String originalStr, String beReplaceSubStr, String replaceSubStr) {
      StringBuilder tmpStr = new StringBuilder();

      int spos = 0;

      int epos = 0;

      if (originalStr == null) {
         return "";
      } else if ((beReplaceSubStr == null) || (replaceSubStr == null)) {
         return originalStr;
      }

      while ((epos = originalStr.indexOf(beReplaceSubStr, spos)) != -1) {
         tmpStr.append(originalStr.substring(spos, epos));

         tmpStr.append(replaceSubStr);

         spos = epos + beReplaceSubStr.length();
      }

      if (spos < originalStr.length()) {
         tmpStr.append(originalStr.substring(spos));
      }

      return new String(tmpStr);
   }

   /**
    * 去掉名称中的两端空格及中间的多个连续空格缩短成一个空格
    * 
    * @param originalStr
    * @return
    */
   public static String replaceAllMutiSpaceToOneSpace(String originalStr) {
      if (originalStr == null) {
         return originalStr;
      }
      // 过滤全角空格成半角空格
      originalStr = originalStr.replaceAll("　", " ");
      // 去掉两端空格
      originalStr = originalStr.trim();
      // 返回多个连续空格合并成一个空格
      return originalStr.replaceAll("(\\s+)", " ");
   }
   
   /**
    * 去掉名称中的所有空格缩短成一个空格
    * 
    * @param originalStr
    * @return
    */
   public static String replaceAllSpace(String originalStr) {
      if (originalStr == null) {
         return originalStr;
      }
      // 过滤全角空格成半角空格
      originalStr = originalStr.replaceAll("　", " ");
      // 去掉两端空格
      originalStr = originalStr.trim();
      // 去掉中间空格
      return originalStr.replaceAll("(\\s)", "");
   }
   

   /**
    * DOCUMENT ME!
    * 
    * @param number
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String formatNumberNoNegative(Integer number) {
      if (number >= 0) {
         return number + "";
      } else {
         return "N/A";
         // return "<font color='red'>(" + Math.abs(number) + ")</font>";
      }
   }

   public static String formatNumberWithBraceWhenNegative(Integer number, boolean withRed) {
      if (number >= 0) {
         return number + "";
      } else {
         if (withRed) {
            return "<font color='red'>" + formatNumber(number, 0, 0) + "</font>";
         } else {
            return number.toString();
         }
      }
   }

   public static String formatNumberWithBraceWhenNegative(Double number, boolean withRed, int digit) {
      if (number >= 0) {
         return number + "";
      } else {
         if (withRed) {
            return "<font color='red'>" + formatNumber(number, digit, digit) + "</font>";
         } else {
            return formatNumber(number, digit, digit);
         }
      }
   }

   public static String formatNumberWithBrace(Double number, boolean withRed, int digit) {
      if (number >= 0) {
         return formatNumber(number, digit, digit) + "";
      } else {
         if (withRed) {
            return "<font color='red'>" + formatNumber(number, digit, digit) + "</font>";
         } else {
            return formatNumber(number, digit, digit);
         }
      }
   }
   
   public static String formatNumber(Integer number) {
      if (number == null) {
         return "";
      }

      int size = number.toString().length();

      StringBuilder numberBuff = new StringBuilder();

      for (int i = size; i < Constants.NUMBER_MAX_SIZE; i++) {
         numberBuff.append("0");
      }

      numberBuff.append(number);

      return numberBuff.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param number
    *           DOCUMENT ME!
    * @param fixLength
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toString(Object obj, String defaultStr) {
      if (null == obj) {
         return defaultStr;
      } else {
         return obj.toString();
      }
   }

   /**
    * DOCUMENT ME!
    * 
    * @param number
    *           DOCUMENT ME!
    * @param fixLength
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toString(long number, int fixLength) {
      int realLength = String.valueOf(number).length();

      if (realLength >= fixLength) {
         return String.valueOf(number);
      } else {
         StringBuilder sb = new StringBuilder();

         for (int i = 0; i < fixLength - realLength; i++) {
            sb.append("0");
         }

         sb.append(number);

         return sb.toString();
      }
   }

   public static String formatObjNo(int number, String pre, int minLength, int groupLength) {
      String objNo = toString(number, minLength);
      if (groupLength <= 0) {
         if (pre == null) {
            return objNo;
         } else {
            return pre + objNo;
         }
      } else {
         int realLength = objNo.length();
         StringBuilder sb = new StringBuilder(objNo);
         if (realLength > groupLength) {
            for (int i = realLength - groupLength - 1; i > 0; i = i - groupLength) {
               sb.insert(i, "-");
            }
         }
         return sb.insert(0, pre).toString();
      }
   }

   /**
    * DOCUMENT ME!
    * 
    * @param number
    *           DOCUMENT ME!
    * @param fixLength
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toString(int number, int fixLength) {
      return toString((long) number, fixLength);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param number
    *           DOCUMENT ME!
    * @param fixLength
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toString(float number, int fixLength) {
      return toString((double) number, fixLength);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param number
    *           DOCUMENT ME!
    * @param fixLength
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toString(double number, int fixLength) {
      String numStr = formatNumber(number, 2, 2); // String.valueOf(number);

      int idx = numStr.indexOf(".");

      int startLen = 0;

      int endLen = 0;

      StringBuilder sb = new StringBuilder();

      if (-1 == idx) {
         startLen = numStr.length();

         endLen = numStr.length() - idx;
      } else {
         startLen = idx;

         endLen = numStr.length() - idx;
      }

      for (int i = 0; i < fixLength - startLen; i++) {
         sb.append("0");
      }

      sb.append(number);

      for (int i = 0; i < fixLength - endLen; i++) {
         sb.append("0");
      }

      return sb.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @param fixLength
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toString(String s, int fixLength) {
      StringBuilder strBuff = new StringBuilder();

      int len = 0;

      if (s != null) {
         len = s.length();
      }

      for (int i = 0; i < fixLength - len; i++) {
         strBuff.append("0");
      }

      if (s != null) {
         strBuff.append(s);
      }

      return strBuff.toString();
   }

   public static String formatNumberNoDigitWhenZero(double num, int minDigits, int maxDigits) {
      String result = formatNumber(num, minDigits, maxDigits);

      try {
         java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

         if (Double.valueOf(0).equals(nf.parse(result).doubleValue())) {
            result = "0";
         }
      } catch (Exception e) {
         ;
      }

      return result;
   }

  
   public static double roundTo(double number, int scale) {
      try {
         BigDecimal bigDecimal = BigDecimal.valueOf(number);
         return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
      } catch (NumberFormatException ne) {
         ne.printStackTrace();
         return 0.0;
      }
   }
   
   public static String formatNumberCompatibleNAN(double num, int minDigits, int maxDigits) {
      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);
      nf.setGroupingUsed(true);

      try {
         return nf.format(roundTo(num, maxDigits));
      } catch (Exception e) {
         return nf.format(num);
      }

   }

   public static String formatNumber(double num, int digits) {
      return formatNumber(num, digits, digits);
   }

   public static String formatNumberNoNegative(double num, int digits) {
      if (num >= 0) {
         return formatNumber(num, digits, digits);
      } else {
         return "<font color='red'>(" + formatNumber(Math.abs(num), digits, digits) + ")</font>";
      }
   }

   public static String formatNumber(double num, int minDigits, int maxDigits) {
      return formatNumber(num, minDigits, maxDigits, true);
   }

   public static String formatDouble(Double num, int minDigits, int maxDigits) {
      if (null == num) {
         return "";
      }
      double numb = 0;
      numb = num.doubleValue();
      return formatNumber(numb, minDigits, maxDigits);
   }

   public static String formatDeltaNumberInReport(double numerator, double denominator, int minDigits, int maxDigits) {
      return formatDeltaNumber(numerator, denominator, minDigits, maxDigits, true);
   }

   public static String formatDeltaNumber(double numerator, double denominator, int minDigits, int maxDigits) {
      return formatDeltaNumber(numerator, denominator, minDigits, maxDigits, false);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param num
    *           DOCUMENT ME!
    * @param minDigits
    *           DOCUMENT ME!
    * @param maxDigits
    *           DOCUMENT ME!
    * @param groupingUsed
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String formatDeltaNumber(double numerator, double denominator, int minDigits, int maxDigits,
      boolean noTag) {
      if (Double.isNaN(numerator) || Double.isNaN(denominator) || denominator == 0) {
         if (noTag) {
            return "-";
         }
         return "-";
      } else {
         double value = ((denominator - numerator) / denominator) * 100d;
         value = roundTo(value, maxDigits);
         java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
         nf.setMaximumFractionDigits(maxDigits);
         nf.setMinimumFractionDigits(minDigits);
         if (noTag) {
            return value + "%";
         }
         if (value > 0) {
            return nf.format(value) + "%" + PADDING_FOR_NUMBER;
         } else if (value == 0) {
            if (nf.format(value).startsWith("-")) {
               StringBuilder sb = new StringBuilder();
               sb.append("<font color='red'>(").append(nf.format(-value)).append(")%</font>");
               return sb.toString();
            } else {
               return nf.format(value) + "%";
            }
         } else {
            StringBuilder sb = new StringBuilder();
            sb.append("<font color='red'>(").append(nf.format(-value)).append(")%</font>");
            return sb.toString();
         }
      }
   }

   public static String formatNumber(double num, int minDigits, int maxDigits, boolean groupingUsed) {
      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);
      nf.setGroupingUsed(groupingUsed);

      if (Double.isNaN(num)) {
         return "N/A";
      }
      return nf.format(roundTo(num, maxDigits));

   }

   /**
    * DOCUMENT ME!
    * 
    * @param useGroup
    *           DOCUMENT ME!
    * @param num
    *           DOCUMENT ME!
    * @param minDigits
    *           DOCUMENT ME!
    * @param maxDigits
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String formatNumber(boolean useGroup, double num, int minDigits, int maxDigits) {
      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setGroupingUsed(useGroup);
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);

      return nf.format(num);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param useGroup
    *           DOCUMENT ME!
    * @param num
    *           DOCUMENT ME!
    * @param minDigits
    *           DOCUMENT ME!
    * @param maxDigits
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String formatNumber(boolean useGroup, Double num, int minDigits, int maxDigits) {
      if (null == num) {
         return "";
      }

      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setGroupingUsed(useGroup);
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);

      return nf.format(roundTo(num, maxDigits));
   }

   /**
    * DOCUMENT ME!
    * 
    * @param num
    *           DOCUMENT ME!
    * @param minDigits
    *           DOCUMENT ME!
    * @param maxDigits
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String formatNumber(Double num, int minDigits, int maxDigits) {
      double numb = 0;

      if (null != num) {
         numb = num.doubleValue();
      }

      return formatNumber(numb, minDigits, maxDigits);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param num
    *           DOCUMENT ME!
    * @param minDigits
    *           DOCUMENT ME!
    * @param maxDigits
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String getCeilNumber(double num, int minDigits, int maxDigits) {
      return getCeilNumber(num, minDigits, maxDigits, true);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param num
    *           DOCUMENT ME!
    * @param minDigits
    *           DOCUMENT ME!
    * @param maxDigits
    *           DOCUMENT ME!
    * @param useGroup
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String getCeilNumber(double num, int minDigits, int maxDigits, boolean useGroup) {
      num = Math.ceil(num);

      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setGroupingUsed(useGroup);
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);

      return nf.format(num);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param num
    *           DOCUMENT ME!
    * @param minDigits
    *           DOCUMENT ME!
    * @param maxDigits
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String formatNumber(float num, int minDigits, int maxDigits) {
      return formatNumber(new Double(num + "").doubleValue(), minDigits, maxDigits);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param num
    *           DOCUMENT ME!
    * @param minDigits
    *           DOCUMENT ME!
    * @param maxDigits
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String formatNumber(int num, int minDigits, int maxDigits) {
      /*
       * by ericchen 2008-03-20. formatNumber(long num, int minDigits, int
       * maxDigits, boolean grouping) may round a big int
       */
      // return formatNumber((long)num, minDigits, maxDigits);
      return formatNumber(Integer.valueOf(num), minDigits, maxDigits, true);
   }

   public static String formatNumber(long num, int minDigits, int maxDigits) {
      return formatNumber(num, minDigits, maxDigits, true);
   }

   public static String formatNumber(long num, int minDigits, int maxDigits, boolean grouping) {
      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setGroupingUsed(grouping);
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);
      return nf.format(Math.round(num));
   }

   public static String formatNumber(Integer num, int minDigits, int maxDigits, boolean grouping) {
      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setGroupingUsed(grouping);
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);
      return nf.format(num);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param old
    *           DOCUMENT ME!
    * @param sub
    *           DOCUMENT ME!
    * @param div
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static StringBuffer append(StringBuffer old, Object sub, Object div) {
      if (old.toString().trim().equals("")) {
         old.append(sub.toString());
      } else {
         old.append(div.toString()).append(sub.toString());
      }

      return old;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param old
    *           DOCUMENT ME!
    * @param sub
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static StringBuffer append(StringBuffer old, Object sub) {
      return append(old, sub, Constants.SPLITTER_COL);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toDisplay(String s) {
      if (s == null) {
         return "";
      }

      if (TEXT_MAX_LENGTH == 0) {
         return s;
      }

      int index = s.indexOf("\r\n");
      if (index < 0) {
         index = s.indexOf("\n");
      }
      if (index == 0) {
         return "...";
      }

      if (index > 0) {
         s = s.substring(0, index);

         if (s.length() <= TEXT_MAX_LENGTH) {
            return s + "...";
         }
      }

      if (s.length() > TEXT_MAX_LENGTH) {
         return s.substring(0, TEXT_MAX_LENGTH) + "...";
      } else {
         return s;
      }
   }

   /**
    * DOCUMENT ME!
    * 
    * @param id
    *           DOCUMENT ME!
    * @param name
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toSummary(Integer id, String name) {
      return toSummary(id, name, 50);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param id
    *           DOCUMENT ME!
    * @param name
    *           DOCUMENT ME!
    * @param len
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toSummary(Integer id, String name, int len) {
      StringBuilder summary = new StringBuilder();

      if (id != null) {
         summary.append(id).append(": ");
      }

      if (name != null) {
         if ((name.length() + getCNNumInString(name)) > len && len > 0) {
            int tempLen = 0;
            for (int i = 0; i < name.length(); i++) {
               if (tempLen >= len) {
                  break;
               }
               summary.append(name.charAt(i));
               tempLen += getCNNumInString(name.charAt(i) + "") > 0 ? 2 : 1;
            }
            summary.append("...");
         } else {
            summary.append(name);
         }
      }

      return summary.toString();
   }

   public static String toSummaryStr(String code, String name, int len) {
      StringBuilder summary = new StringBuilder();

      if (code != null) {
         summary.append(code).append(": ");
      }

      if (name != null) {
         if (name.length() > len && len > 0) {
            summary.append(name.substring(0, len)).append("...");
         } else {
            summary.append(name);
         }
      }

      return summary.toString();
   }

   /**
    * 取得字符串中中文的字符个�?
    * 
    * @param str
    * @return
    */
   public static int getCNNumInString(String str) {
      int count = 0;
      String regEx = "[\\u4e00-\\u9fa5]";
      Pattern p = Pattern.compile(regEx);
      Matcher m = p.matcher(str);
      while (m.find()) {
         count++;
      }
      return count;
   }

   public static int getUpperNumInString(String str) {
      if (str == null) {
         return 0;
      }
      int count = 0;
      for (int i = 0; i < str.length(); i++) {
         char c = str.charAt(i);
         if (c >= 'A' && c <= 'Z') {
            count++;
         }
      }
      return count;
   }

   public static String toDisplaySummary(Integer id, String name, int len) {
      StringBuilder summary = new StringBuilder();

      if (id != null) {
         summary.append(id).append(": ");
      }

      if (name != null) {
         if (len == 0) {
            summary.append(name);
            return summary.toString();
         }
         int byteLen = 0;
         for (int i = 0; i < name.length(); i++) {
            String charStr = name.substring(i, i + 1);
            summary.append(charStr);
            byteLen += charStr.getBytes().length;
            if (byteLen > len) {
               if (i < name.length() - 1) {
                  summary.append("...");
               }
               break;
            }
         }
      }

      return summary.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param name
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toNowarpSummary(String name) {
      return toNowarpSummary(name, 49);
   }

   public static String toGeneralSummary(String name) {
      // return toNowarpSummary(name, 32);
      return toNowarpSummary(name, 100);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param name
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toNowarpSummary(String name, int length) {
      if (length == 0) {
         return name;
      }
      StringBuilder summary = new StringBuilder();

      if (name != null) {
         int idx = name.indexOf('\n');
         if (name.indexOf('\r') > -1) {
            if (idx > -1) {
               idx = idx < name.indexOf('\r') ? idx : name.indexOf('\r');
            } else {
               idx = name.indexOf('\r');
            }
         }

         if (idx > -1) {
            if (idx > length) {
               idx = length;
            } else {
               if (name.substring(idx).replaceAll("(\\s)", "").length() == 0) {
                  if (idx > 0) {
                     name = name.substring(0, idx);
                  } else {
                     name = "";
                  }
               }
            }
         } else {
            idx = length;
         }

         if (name.length() > idx) {
            summary.append(name.substring(0, idx)).append("...");
         } else {
            summary.append(name);
         }
      }

      return summary.toString();
   }

   public static String toOneRowSummary(String summary) {
      if (null == summary || summary.trim().length() == 0) {
         return "";
      }

      return summary.replaceAll("[\\r\\n\\s]", " ");
   }

   /**
    * DOCUMENT ME!
    * 
    * @param len
    *           DOCUMENT ME!
    * @param name
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toSummary(int len, String name) {
      return toSummary(null, name, len);
   }
   
   /**
    * beform summary, convert the html special character, and remove MutiSpace
    * e.g. &nbsp; to " "
    *  
    * @param convertHtmlSpecialCharacter
    * @param len
    * @param name
    * @return
    */
   public static String toSummary(boolean convertHtmlSpecialCharacter, int len, String name) {
      if (convertHtmlSpecialCharacter) {
         return toSummary(null, replaceAllSpace(htmlToPlainString(name)), len);
      }
      
      return toSummary(null, name, len);
   }
   /**
    * DOCUMENT ME!
    * 
    * @param s
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String removeComma(String s) {
      if (s == null) {
         return s;
      }

      StringBuilder buff = new StringBuilder();

      int len = s.length();

      for (int i = 0; i < len; i++) {
         if (s.charAt(i) != ',') {
            buff.append(s.charAt(i));
         }
      }

      return buff.toString();
   }

   public static String removeCommaAtEnd(String s) {
      if (!isEmpty1(s)) {
         char c = s.charAt(s.length() - 1);
         if (c == '\uff0c' || c == ',') {
            return s.substring(0, s.length() - 1);
         }
      }

      return s;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param id
    *           DOCUMENT ME!
    * @param ids
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isIn(String id, String idstr) {
      if ((null == id) || (id.trim().equals("")) || (null == idstr)) {
         return false;
      }

      String[] ids = idstr.split(Constants.SPLITTER_COL.trim());

      return isIn(id, ids);
   }

   public static boolean isIn(Object obj, Object[] objs) {
      if (obj == null || objs == null) {
         return false;
      }
      for (Object o : objs) {
         if (o != null && o.equals(obj)) {
            return true;
         }
      }
      return false;
   }
   
   public static boolean isIn(Integer[] objs, Integer obj) {
      if (obj == null || objs == null) {
         return false;
      }
      for (Integer o : objs) {
         if (o != null && o.intValue() == obj.intValue()) {
            return true;
         }
      }
      return false;
   }

   public static boolean isIn(int obj, int[] objs) {
      for (int o : objs) {
         if (o == obj) {
            return true;
         }
      }
      return false;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param id
    *           DOCUMENT ME!
    * @param ids
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isIn(String id, String[] ids) {
      if ((null == id) || (id.trim().equals("")) || (null == ids)) {
         return false;
      }

      boolean flag = false;

      for (int i = 0; i < ids.length; i++) {
         if (ids[i] != null && id.trim().equalsIgnoreCase(ids[i].trim())) {
            flag = true;

            break;
         }
      }

      return flag;
   }

   /**
    * @param strs
    * @param inStrs
    * @return 返回的数组是strs和inStrs中的共有的值
    */
   public static Object[] filtrateArray(Object[] strs, Object[] inStrs) {
      List list = new ArrayList();
      for (int i = 0; i < strs.length; i++) {
         if (isIn(strs[i], inStrs)) {
            list.add(strs[i]);
            continue;
         }
      }
      return list.toArray();
   }

   public static boolean isEmptyArray(String[] strs) {
      if (strs == null) {
         return true;
      }
      for (int i = 0; i < strs.length; i++) {
         if (strs[i] != null && !"".equals(strs[i].trim())) {
            return false;
         }
      }
      return true;
   }

   public static String matchEqualsIgnoreCase(String s, String strs) {
      if ((null == s) || (s.trim().equals("")) || (null == strs)) {
         return null;
      }

      String[] ids = strs.split(Constants.SPLITTER_COL.trim());

      if (ids != null) {
         for (int i = 0; i < ids.length; i++) {
            if (s.trim().equalsIgnoreCase(ids[i].trim())) {
               return ids[i].trim();
            }
         }
      }
      return null;
   }

   public static boolean isInIgnoreCase(String str, String strs) {
      if ((null == str) || (str.trim().equals("")) || (null == strs)) {
         return false;
      }

      String[] ids = strs.split(Constants.SPLITTER_COMMA.trim());

      return isInIgnoreCase(str, ids);
   }

   public static boolean isInIgnoreCase(String s, String[] strs) {
      if ((null == s) || (s.trim().equals("")) || (null == strs)) {
         return false;
      }

      boolean flag = false;

      for (int i = 0; i < strs.length; i++) {
         if (s.trim().equalsIgnoreCase(strs[i].trim())) {
            flag = true;

            break;
         }
      }

      return flag;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param originalSql
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    * @throws Exception
    *            DOCUMENT ME!
    */
   public static String getSqlCond(String originalSql) throws Exception {
      if (originalSql == null) {
         return null;
      }

      String aSql = originalSql.toLowerCase();

      if (aSql.indexOf("order by") != -1) {
         originalSql = originalSql.substring(0, aSql.indexOf("order by") - 1);
      }

      String distinctCols = "";

      if (aSql.indexOf("where") >= 0) {
         distinctCols = originalSql.substring(aSql.indexOf("where"));
      }

      return distinctCols;
   }

   /**
    * @param length
    *           the max lenght of the expected string, length > 0
    * @param source
    *           source string
    * @return cutted string!
    */
   public static String cutString(int length, String source) {
      if (source == null || source.length() <= length) {
         return source;
      } else {
         return source.substring(0, length);
      }
   }
   
   public static String cutDescription(int length, String source) {
      if (source == null || source.length() <= length) {
         return source;
      } else {
         return source.substring(0, length - 4) + "...";
      }
   }
   
   public static String cutDescription(String source) {
      return cutDescription(2000, source);
   }


   public static String formatCurrency(double value, int minDigits, int maxDigits) {
      return formatCurrency(null, value, minDigits, maxDigits);
   }

   public static String formatCurrency(String currency, double value, int minDigits, int maxDigits) {
      return formatCurrency(currency, value, minDigits, maxDigits, true);
   }

   /**
    * @param value
    * @param minDigits
    * @param maxDigits
    * @return
    */
   public static String formatCurrencyWithoutPadding(double value, int minDigits, int maxDigits) {
      return formatCurrency(null, value, minDigits, maxDigits, false);
   }

   /**
    * @param currency
    * @param value
    * @param minDigits
    * @param maxDigits
    * @return
    * @author macroliu
    */
   public static String formatCurrencyWithoutPadding(String currency, double value, int minDigits, int maxDigits) {
      return formatCurrency(currency, value, minDigits, maxDigits, false);
   }

   /**
    * Last edited by macro
    * 
    * @param currency
    * @param value
    * @param minDigits
    * @param maxDigits
    * @param needPadding
    * @return
    */
   public static String formatCurrency(String currency, double value, int minDigits, int maxDigits, boolean needPadding) {
      if (Double.isNaN(value)) {
         return "N/A";
      }
      if (currency != null) {
         currency += "    ";
      } else {
         currency = "";
      }
      value = roundTo(value, maxDigits);
      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);
      nf.setGroupingUsed(true);
      if (value > 0) {
         String retStr = currency + nf.format(value);
         if (needPadding) {
            retStr += "&nbsp;" + PADDING_FOR_NUMBER;
         }
         return retStr;
      } else if (value == 0) {
         if (nf.format(value).startsWith("-")) {
            StringBuilder sb = new StringBuilder();
            sb.append("<font color='red'>(").append(nf.format(-value)).append(")</font>");
            return currency + sb.toString();
         } else {
            String retStr = currency + nf.format(value);
            if (needPadding) {
               retStr += "&nbsp;" + PADDING_FOR_NUMBER;
            }
            return retStr;
         }
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append("<font color='red'>(").append(nf.format(-value)).append(")</font>");
         return currency + sb.toString();
      }

   }

   /**
    * 对比率进行格式话，会将原值乘以100，后面加多个百分号
    * 
    * @param value
    * @param minDigits
    * @param maxDigits
    * @param needPadding
    * @return
    */
   public static String formatRate(Double value, int minDigits, int maxDigits, boolean needPadding) {
      if (value == null) {
         return "-";
      }
      if (Double.isNaN(value)) {
         return "N/A";
      }
      value = roundTo(value * 100, maxDigits);
      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);
      nf.setGroupingUsed(true);
      if (value > 0) {
         String retStr = nf.format(value) + "%";
         if (needPadding) {
            retStr += "&nbsp;" + PADDING_FOR_NUMBER;
         }
         return retStr;
      } else if (value == 0) {
         if (nf.format(value).startsWith("-")) {
            StringBuilder sb = new StringBuilder(); 
            sb.append("<font color='red'>").append(nf.format(value)).append("%</font>");
            return sb.toString();
         } else {
            String retStr = nf.format(value) + "%";
            if (needPadding) {
               retStr += "&nbsp;" + PADDING_FOR_NUMBER;
            }
            return retStr;
         }
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append("<font color='red'>").append(nf.format(value)).append("%</font>");
         return sb.toString();
      }

   }

   public static String formatCurrencyWithTable(String currency, double value, int minDigits, int maxDigits) {
      StringBuilder sb = new StringBuilder(
         "<table  border='0'  cellpadding='0' cellspacing='0'  width='100%' cellspacing='0'  class='noPadding' >");
      sb.append("<tr ><td  align='left' nowrap='true'>");
      if (!StringUtil.isEmpty(currency)) {
         sb.append(currency);
      }
      sb.append("<td  align='right' nowrap='true'>");
      sb.append(formatNumber(value, minDigits, maxDigits));
      sb.append("&nbsp;<span style='width:1'></span></td></tr></table>");
      return sb.toString();
   }

   public static String formatCurrencyWithTable(String currency, String value) {
      StringBuilder sb = new StringBuilder(
         "<table  border='0'  cellpadding='0' cellspacing='0'  width='100%' cellspacing='0'  class='noPadding' >");
      sb.append("<tr ><td  align='left' nowrap='true'>");
      if (!StringUtil.isEmpty(currency)) {
         sb.append(currency);
      }
      sb.append("<td  align='right' nowrap='true'>");
      sb.append(value);
      sb.append("&nbsp;<span style='width:1'></span></td></tr></table>");
      return sb.toString();
   }

   /**
    * @param currency
    * @param value
    * @param style
    * @return
    * @author macroliu
    */
   public static String formatCurrencyWithTable(String currency, String value, String style, String tdClass) {
      if (style == null) {
         style = "margin:0;padding:0";
      }
      StringBuilder sb = new StringBuilder(
         "<table  border='0' cellpadding='0' cellspacing='0' width='100%' cellspacing='0' style='" + style + "'>");
      sb.append("<tr><td align='left' nowrap='true'");
      if (tdClass != null && !"".equals(tdClass.trim())) {
         sb.append(" class='").append(tdClass).append("'");
      }
      sb.append(">");
      if (!StringUtil.isEmpty(currency)) {
         sb.append(currency);
      }
      sb.append("<td align='right' nowrap='true'");
      if (tdClass != null && !"".equals(tdClass.trim())) {
         sb.append(" class='").append(tdClass).append("'");
      }
      sb.append(">");
      sb.append(value);
      sb.append("</td></tr></table>");
      return sb.toString();
   }

   /**
    * @param currency
    * @param amount
    * @param minDigits
    * @param maxDigits
    * @author macroliu
    * @return
    */
   public static String formatCurrencyWithTableForListGrandTotal(String currency, Double amount, int minDigits,
      int maxDigits) {
      String value = formatCurrency((amount != null ? amount : 0.0), minDigits, maxDigits);
      return formatCurrencyWithTable(currency, value, null, "listGrandTotalcolor");
   }
   
   /**
    * 
    * @param currency
    * @param value
    * @return
    * @author macroliu
    */
   public static String formatCurrencyWithTableForListGrandTotal(String currency, String value) {
      return formatCurrencyWithTable(currency, value, null, "listGrandTotalcolor");
   }
   
   /**
    * @param value
    * @param minDigits
    * @param maxDigits
    * @param needPadding
    * @return
    * @author macroliu
    */
   public static String formatPercentage(double value, int minDigits, int maxDigits, boolean needPadding) {
      if (Double.isNaN(value)) {
         return "N/A";
      }
      value = roundTo(value, maxDigits);
      java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
      nf.setMaximumFractionDigits(maxDigits);
      nf.setMinimumFractionDigits(minDigits);
      nf.setGroupingUsed(true);
      if (value > 0) {
         String retStr = nf.format(value) + "%";
         if (needPadding) {
            retStr += "&nbsp;" + PADDING_FOR_NUMBER;
         }
         return retStr;
      } else if (value == 0) {
         if (nf.format(value).startsWith("-")) {
            StringBuilder sb = new StringBuilder();
            sb.append("<font color='red'>(").append(nf.format(-value)).append(")</font>").append("%");
            return sb.toString();
         } else {
            String retStr = nf.format(value) + "%";
            if (needPadding) {
               retStr += "&nbsp;" + PADDING_FOR_NUMBER;
            }
            return retStr;
         }
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append("<font color='red'>(").append(nf.format(-value)).append(")</font>").append("%");
         return sb.toString();
      }

   }

   /**
    * DOCUMENT ME!
    * 
    * @param a
    *           DOCUMENT ME!
    * @param b
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static Double count(Double a, Double b) {
      Double c = null;

      if (null == a) {
         c = b;
      } else if (null == b) {
         c = a;
      } else {
         c = Double.valueOf(a.doubleValue() + b.doubleValue());
      }

      return c;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param oldStr
    *           DOCUMENT ME!
    * @param length
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String addLeftSpace(String oldStr, int length) {
      StringBuilder sf = new StringBuilder();
      int l = length - oldStr.length();

      for (int i = l; i > 0; i--) {
         sf.append(" ");
      }

      sf.append(oldStr);

      return sf.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param oldStr
    *           DOCUMENT ME!
    * @param length
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String genCompareString(String oldStr, int length) {
      if (null == oldStr) {
         oldStr = "";
      }

      StringBuilder sf = new StringBuilder();
      sf.append(oldStr.toUpperCase());

      int l = length - oldStr.length();

      for (int i = l; i > 0; i--) {
         sf.append(" ");
      }

      return sf.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param oldNum
    *           DOCUMENT ME!
    * @param length
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String genCompareString(Integer oldNum, int length) {
      StringBuilder sf = new StringBuilder();

      int l = length - oldNum.toString().length();

      for (int i = l; i > 0; i--) {
         sf.append("0");
      }

      sf.append(oldNum);

      return sf.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param oldStr
    *           DOCUMENT ME!
    * @param newStr
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isEqualsTrim(String oldStr, String newStr) {
      if ((null == oldStr) || (null == newStr)) {
         return false;
      }

      return oldStr.trim().equals(newStr.trim());
   }

   /**
    * DOCUMENT ME!
    * 
    * @param oldStr
    *           DOCUMENT ME!
    * @param newStr
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isEqualsIgnoreCaseTrim(String oldStr, String newStr) {
      if ((null == oldStr) || (null == newStr)) {
         return false;
      }

      return newStr.trim().equalsIgnoreCase(oldStr.trim());
   }

   /**
    * @param names1
    * @param names2
    * @return
    * @author macroliu
    */
   public static boolean isEqualsObjectNames(String names1, String names2) {
      if (names1 == null || "".equals(names1.trim()) || ",".equals(names1.trim()) || "，".equals(names1.trim())) {
         return false;
      }
      if (names2 == null || "".equals(names2.trim()) || ",".equals(names2.trim()) || "，".equals(names2.trim())) {
         return false;
      }

      String[] strs1 = splitStringWithZhEnComma(names1);
      String[] strs2 = splitStringWithZhEnComma(names2);

      if (strs1 == null || strs2 == null) {
         return false;
      }

      for (int i = 0; i < strs1.length; i++) {
         String name = strs1[i];
         if (name != null && !"".equals(name.trim())) {
            if (!isIn(name, strs2)) {
               return false;
            }
         }
      }
      for (int i = 0; i < strs2.length; i++) {
         String name = strs2[i];
         if (name != null && !"".equals(name.trim())) {
            if (!isIn(name, strs1)) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param oldArray
    *           DOCUMENT ME!
    * @param newArray
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String[][] batchArrays(String[][] oldArray, String[][] newArray) {
      int length = oldArray.length + newArray.length;
      String[][] batchArray = new String[length][2];

      for (int i = 0; i < length; i++) {
         if (i < oldArray.length) {
            batchArray[i] = oldArray[i];
         } else {
            batchArray[i] = newArray[i - oldArray.length];
         }
      }

      return batchArray;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param oldValue
    *           DOCUMENT ME!
    * @param newValue
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isEqualInteger(Integer oldValue, Integer newValue) {
      if ((null == oldValue) && (null == newValue)) {
         return true;
      }

      if ((null == oldValue) || (null == newValue)) {
         return false;
      }

      boolean flag = false;

      if (oldValue.intValue() == newValue.intValue()) {
         flag = true;
      }

      return flag;
   }

   //

   /**
    * DOCUMENT ME!
    * 
    * @param methodName
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isInfoPage(String methodName) {
      boolean flag = false;

      if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doShowInfo")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doUpdate")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doAdd")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doCopy")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doAddNoGenKey")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doShowViewInfo")) {
         flag = true;
      }

      return flag;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param methodName
    *           DOCUMENT ME!
    * @param defaultFlag
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isInfoPage(String methodName, boolean defaultFlag) {
      boolean flag = false;

      if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doShowInfo")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doUpdate")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doAdd")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doCopy")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doAddNoGenKey")) {
         flag = true;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doShowAdd")) {
         flag = false;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doShowCopy")) {
         flag = false;
      } else if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doShowList")) {
         flag = false;
      } else {
         flag = defaultFlag;
      }

      return flag;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param methodName
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static boolean isViewPage(String methodName) {
      boolean flag = false;

      if (StringUtil.isEqualsIgnoreCaseTrim(methodName, "doView")) {
         flag = true;
      }

      return flag;
   }

   /**
    * DOCUMENT ME!
    * 
    * @param qs
    *           DOCUMENT ME!
    * @param params
    *           DOCUMENT ME!
    * @param pChar
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String messageFormat(String qs, String[] params, String pChar) {
      StringBuilder sb = new StringBuilder();
      int index = qs.indexOf(pChar);
      int lastPoint = 0;
      int i = 0;

      while (index > 0) {
         sb.append(qs.subSequence(lastPoint, index));

         if (i < params.length) {
            sb.append(params[i]);
            i++;
         }

         lastPoint = index + 1;
         index = qs.indexOf(pChar, index + 1);
      }

      if (lastPoint < qs.length()) {
         sb.append(qs.subSequence(lastPoint, qs.length()));
      }

      return sb.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param qs
    *           DOCUMENT ME!
    * @param params
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String messageFormat(String qs, String[] params) {
      return messageFormat(qs, params, Constants.PARAMETOR_CHARECTER);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param col
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toSqlInStringWithoutQuote(Collection col) {
      if (col == null) {
         return null;
      }

      return toSqlInStringWithoutQuote(col.toArray());
   }

   /**
    * DOCUMENT ME!
    * 
    * @param col
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toSqlInStringWithoutQuote(Object[] col) {
      if (null == col) {
         return null;
      }

      int index = 0;
      StringBuilder buf = new StringBuilder();
      Object value;

      while (index < col.length) {
         value = col[index];

         if (value == null) {
            continue;
         }

         if (index != 0) {
            buf.append(",");
         }

         buf.append(String.valueOf(value));
         index++;
      }

      return buf.toString();
   }

   /**
    * DOCUMENT ME!
    * 
    * @param summary
    *           DOCUMENT ME!
    * @param number
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toTitle(String summary, int number) {
      return summary + ": " + StringUtil.formatNumber(number, 0, 0);
   }

   /**
    * DOCUMENT ME!
    * 
    * @param summary
    *           DOCUMENT ME!
    * @param number
    *           DOCUMENT ME!
    * @return DOCUMENT ME!
    */
   public static String toTitle(String summary, double number) {
      return summary + ": " + StringUtil.formatNumber(number, 2, 2);
   }

   public static String decorate(String src, String header, String footer) {
      return header + src + footer;
   }


   public static String wrapString(String str, int distance) {
      if (str == null || str.equals("")) {
         return str;
      }
      if (distance < 0) {
         distance = 25;
      }
      StringBuilder newStr = new StringBuilder();
      int j = 0;
      for (int i = 0; i < str.length(); i++) {
         char ch = str.charAt(i);
         newStr.append(ch);
         if (ch != ' ' && ch != '<' && ch != '>' && ch != '&' && ch != '\n') {
            j++;
         } else {
            j = 0;
         }
         if (j == distance) {
            newStr.append("<wbr/>");
            j = 0;
         }
      }


      return newStr.toString();
   }

   public static String convertJsQuoteMark(String srcString) {
      if (srcString == null) {
         return null;
      }

      StringBuilder sb = new StringBuilder();
      int length = srcString.length();
      for (int i = 0; i < length; i++) {
         char c = srcString.charAt(i);
         // single quote
         if (c == 39) {
            sb.append("&acute;");
            // double quote
         } else if (c == 34) {
            sb.append("&quot;");
         } else {
            sb.append(c);
         }
      }

      return sb.toString();
   }

   public static String convertJsBackslash(String srcString) {
      if (srcString == null) {
         return null;
      }

      StringBuilder sb = new StringBuilder();
      int length = srcString.length();
      for (int i = 0; i < length; i++) {
         char c = srcString.charAt(i);
         if (c == 92) {
            sb.append("\\\\");
         } else {
            sb.append(c);
         }
      }

      return sb.toString();
   }

   public static boolean matchStringWithRegex(String s, String regex) {
      Pattern p = Pattern.compile(regex); // StringUtil.REGEX_EMAIL);
      Matcher m = p.matcher(StringUtil.trim1(s));
      return m.matches();
   }

   public static boolean findStringWithRegex(String s, String regex) {
      Pattern p = Pattern.compile(regex); // StringUtil.REGEX_EMAIL);
      Matcher m = p.matcher(StringUtil.trim1(s));
      return m.find();
   }

   public static boolean checkEmail(String input) {
      // boolean flag = true;

      Pattern p = Pattern.compile(REGEX_EMAIL);
      Matcher m = p.matcher(input);
      if (!m.find()) {
         return false;
      }
      return true;
   }

   public static void main(String[] args) {

      // System.out.println(s.substring(0, s.indexOf(":")));
      // System.out.println(s.substring(s.indexOf(":") + 1));
      // Pattern p =
      // Pattern.compile("^[A-Za-z0-9\\._\\-~#+]+@[^\\.]+(\\.[A-Za-z0-9_\\-~#+]+)+$");
      // Matcher m = p.matcher("a@a.ombc.cc.bb.aa.pp");

      // System.out.println(generateString(4, "dsdsds") + "A");
      // System.out.println(generateString(5) + "A");
      // System.out.println(generateString(6) + "A");
      // System.out.println(generateString(7) + "A");
      //String s = "12\n\r\n\r";
      //s = StringUtil.toNowarpSummary(s);
      //System.out.println(s);
      //String[] array = {"abc","abcd","abcde","abcdef","abcedfg"};
      //isRepeatInArrays(array);
      
//      String[] test = null; 
//      test = splitByCommaPattern("\"Kent,John and Billy Limited\",\"tom,jerry and jack\"");      
//      test = splitByCommaPattern("\\\"\"f\" , \"g\"e\\\" , abc , \"e , f\" , dd");
//      test = splitByCommaPattern("\\\"\"a,\"\\\",\\\"\"a,b\"\\\",b");
//      test = splitByCommaPattern("\"Hong Kong\" Airport , \"Kent,John and Billy Limited\"");
//      test = splitByCommaPattern("\\\"Hong Kong\" Airport , \\\"\"Kent\",\"John\" and \"Billy\"\\\"");
//      test = splitByCommaPattern("\"Hong Kong\" Airport, \\\"\"Hong Kong\",\"Kowloon\" and \"New Territory\" Corporation\\\"");
//      System.out.println(test != null ? test.length : 0);
//      System.out.println(wrapName("\"f\",\"g\"e"));
//      System.out.println(wrapName("\\\"e,f\\\""));
//      System.out.println(wrapName("e,f"));
//      System.out.println(wrapName("\"abc\""));
//      System.out.println(wrapName("Hong Kong\" Airport , \"Kent,John and Billy Limited"));
      
      //EmailUtil.connectSmtp("mail.8mcrm.com", 25, false, "nickjohnson", "1qazXSW@", false);
      
      try {
         throw new Exception("1111");
      } catch (Exception e) {
         e.printStackTrace();
         return;
      } finally {
         System.out.print("22222222");
      }
      
   }

   public static boolean containsEmptyStringOnly(String[] strArray) {
      if (strArray == null) {
         return false;
      }

      boolean ret = true;
      for (int i = 0; i < strArray.length; i++) {
         String str = strArray[i];
         if (str != null && !str.trim().equals("")) {
            ret = false;
         }
      }
      return ret;
   }

   public static byte[] zip(String detail) throws IOException {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ZipOutputStream zos = new ZipOutputStream(bos);
      zos.putNextEntry(new ZipEntry("zip"));
      zos.write(detail.getBytes("UTF-8"));
      zos.close();
      return bos.toByteArray();
   }

   public static String unzip(byte[] bytes) throws IOException {
      ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
      ZipInputStream zis = new ZipInputStream(bis);
      zis.getNextEntry();

      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      int i = zis.read();
      while (i != -1) {
         bos.write(i);
         i = zis.read();
      }

      return new String(bos.toByteArray(), "UTF-8");
   }

   public static String readFromStream(InputStream stream) throws IOException {
      return readFromStream(stream, null);
   }
   
   public static String readFromStream(InputStream stream, String encoding) throws IOException {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      int i = stream.read();
      while (i != -1) {
         bos.write(i);
         i = stream.read();
      }
      stream.close();
      String value = null;
      if (isEmpty1(encoding)) {
         value = new String(bos.toByteArray());
      } else {
         value = new String(bos.toByteArray(), encoding);
      }
      return value;
   }

   public static String readFromFile(File f) throws Exception {
      return readFromFile(f, null);
   }
   
   public static String readFromFile(File f, String encoding) throws Exception {
      String result = null;
      FileInputStream fis = null;
      try {
         fis = new FileInputStream(f);
         result = readFromStream(fis, encoding);
      } catch (Exception e) {
         throw e;
      } finally {
         if (fis != null) {
            try {
               fis.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
      return result;
   }

   /**
    * return "" , if the str is a empty string
    * 
    * @param str
    * @return
    */
   public static String trim1(String str) {
      if (null == str) {
         return "";
      }

      return str.trim();
   }

   /**
    * return false , if the str is a empty string or null
    * 
    * @param str
    * @return
    */
   public static boolean isEmpty1(String str) {
      return "".equals(trim1(str));
   }

   /**
    * given the column name and its values in collection as params,the method
    * will return the result as " a=1 or a=2 or a=3"
    * 
    * @param column
    * @param ids
    * @return
    */
   public static String getOrConditionHql(String column, Collection ids) {
      if (column == null || ids == null) {
         return "";
      }
      int i = 0;
      StringBuilder sb = new StringBuilder("");
      for (Iterator<Integer> it = ids.iterator(); it.hasNext(); i++) {
         if (i == 0) {
            sb.append(column).append("=").append(it.next());
         } else {
            sb.append(" or ").append(column).append("=").append(it.next());
         }
      }
      return sb.toString();
   }

   /**
    * trim the desc to type string3
    * 
    * @param desc
    * @return
    */
   public static String toString3(String desc) {
      if (desc.length() > Constants.MAXLEN_STRING3) {
         return desc.substring(0, Constants.MAXLEN_STRING3);
      }
      return desc;
   }

   /****************************************************************************
    * Add for decorating the display of discussion-reply content.
    * 
    * @param srcValue
    * @param maxLinesToDisplay
    * @param suffix
    * @return
    */
   public static String wrapContent(String srcValue, int maxLinesToDisplay) {
      return wrapContent(srcValue, maxLinesToDisplay, "...");
   }

   public static String wrapContent(String srcValue, int maxLinesToDisplay, String suffix) {
      if (srcValue == null) {
         return null;
      }

      int linesCount = 0;
      String symbol = "\r\n";
      int symbolLen = symbol.length();

      int index = srcValue.indexOf(symbol);
      while (index >= 0) {
         linesCount++;
         if (linesCount >= maxLinesToDisplay) {
            break;
         }
         index = srcValue.indexOf(symbol, index + symbolLen);
      }

      String retValue = "";

      if (index > 0) {
         retValue = srcValue.substring(0, index) + symbol + suffix;
      } else {
         retValue = srcValue;
      }

      return toHTMLString(retValue);

   }

   public static String toString(Object[] array, String splitter) {
      if (array == null) {
         return null;
      }

      StringBuilder sb = new StringBuilder();
      for (int idx = 0; idx < array.length; idx++) {
         sb.append(array[idx].toString());
         if (splitter != null && idx < array.length - 1) {
            sb.append(splitter);
         }
      }

      return sb.toString();
   }

   public static String toString(Object[] array, String quote, String splitter) {
      if (array == null) {
         return null;
      }

      StringBuilder sb = new StringBuilder();
      for (int idx = 0; idx < array.length; idx++) {
         sb.append(quote);
         sb.append(array[idx].toString());
         sb.append(quote);
         if (splitter != null && idx < array.length - 1) {
            sb.append(splitter);
         }
      }

      return sb.toString();
   }

   /**
    * 将字符串的首个字母大写，值得注意的是，不会trim
    * 
    * @param s
    * @return
    */
   public static String upperCaseFirstChar(String s) {
      if (isEmpty(s)) {
         return s;
      }
      StringBuilder sb = new StringBuilder(s.substring(0, 1).toUpperCase());
      if (s.length() > 1) {
         sb.append(s.substring(1));
      }
      return sb.toString();
   }

   /**
    * 将字符串的首个字母小写，值得注意的是，不会trim
    * 
    * @param s
    * @return
    */
   public static String lowerCaseFirstChar(String s) {
      if (isEmpty1(s)) {
         return s;
      }
      StringBuilder sb = new StringBuilder(s.substring(0, 1).toLowerCase());
      if (s.length() > 1) {
         sb.append(s.substring(1));
      }
      return sb.toString();
   }

   // 在1.4中需要把中文都好改为utf8的方式
   public static String replaceZhSplitCol(String str) {
      if (isEmpty(str)) {
         return str;
      }
      return str.replaceAll("，", ",");
   }

   public static String[] splitStringWithZhEnComma(String srcStr) {
      // if (nvl(srcStr, "").length() == 0) {
      if (srcStr == null) { // modified by hill
         return null;
      } else {
         srcStr = replaceZhSplitCol(srcStr);
         return srcStr.split(Constants.SPLITTER_COMMA);
      }
   }

   public static boolean isStrInArray(String[] arrays, String str) {
      if (arrays == null) {
         return false;
      }
      int count = 0;
      for (int i = 0; i < arrays.length; i++) {
         if (arrays[i] == null && str == null) {
            count++;
         } else if (arrays[i] != null && arrays[i].equals(str)) {
            count++;
         } else if (str.equals(arrays[i])) {
            count++;
         }
      }
      return count > 0;
   }

   /**
    * 去掉字符串中最末端的一个分隔符
    * 
    * @param str
    * @return
    */
   public static String removeLastSplitChar(String str) {
      if (isEmpty(str)) {
         return str;
      }
      str = str.trim();
      str = str.replaceAll("，", ",");
      char lastChar = str.charAt(str.length() - 1);
      if (lastChar == ',') {
         str = str.substring(0, str.length() - 1);
      }
      return str;
   }

   public static String replaceVariables(Pattern p, String s, Map properties, int startPosition, int endPosition)
      throws Exception {
      if (s == null || p == null) {
         return s;
      }

      if (properties == null) {
         throw new IllegalArgumentException("Cannot perform function"
            + " replaceVariables(Pattern, String, Map, int, int) with null Map value.");
      }

      int last = 0;
      Matcher m = p.matcher(s);
      StringBuilder b = null;
      while (m.find()) {
         if (b == null) {
            b = new StringBuilder();
         }

         b.append(s.substring(last, m.start()));
         String n = s.substring(m.start() + startPosition, m.end() - endPosition);
         String v = (String) properties.get(n);
         if (v == null) {
            throw new Exception("Cannot find variable:" + n);
         }

         b.append(v);
         last = m.end();
      }

      if (last != 0) {
         b.append(s.substring(last));
         return b.toString();
      } else {
         return s;
      }
   }

   /**
    * visonlong
    * 
    * @param ors
    * @return json特殊字符处理
    */
   public static String dealString4JSON(String ors) {
      ors = ors == null ? "" : ors;
      StringBuilder buffer = new StringBuilder(ors);
      int i = 0;
      while (i < buffer.length()) {
         if (buffer.charAt(i) == '\'' || buffer.charAt(i) == '\\') {
            buffer.insert(i, '\\');
            i += 2;
         } else {
            i++;
         }
      }
      return buffer.toString().replaceAll("\r", "").replaceAll("\n", "");
   }

   private static Pattern PATTERN_NAME = Pattern.compile("[^,'\"><\\\\]*");

   private static Pattern PATTERN_NAME1 = Pattern.compile("[^,'\"]*");

   private static Pattern PATTERN_NAME2 = Pattern.compile("[^,\\\\]*");

   private static Pattern PATTERN_NAME3 = Pattern.compile("[^:\\-]*");

   private static Pattern PATTERN_NAME4 = Pattern.compile("[^\\\\]*");

   // private static Pattern PATTERN_FLOAT = Pattern.compile("^-?\\d*\\.\\d*$");
   private static Pattern PATTERN_FLOAT_TWO_FIXEDDIGIT = Pattern.compile("^-?\\d*\\.\\d{2}$");

   // private static Pattern PATTERN_NUMBER =
   // Pattern.compile("^-?\\d*\\.?\\d*$");
   private static Pattern PATTERN_NUMBER_TWO_FIXEDDIGIT = Pattern.compile("^-?\\d*\\.\\d{2}$");

   // private static Pattern PATTERN_PLUSNUMBER =
   // Pattern.compile("^\\d*\\.?\\d*$");
   private static Pattern PATTERN_PLUSNUMBER_TWO_FIXEDDIGIT = Pattern.compile("^\\d*\\.\\d{2}$");


   public static boolean isName(String inPara) {
      if (!StringUtil.isEmpty(inPara)) {
         if (inPara.indexOf(",") != -1 || inPara.indexOf("'") != -1 || inPara.indexOf("\"") != -1 ||
            inPara.indexOf("\\") != -1 || inPara.indexOf(">") != -1 || inPara.indexOf("<") != -1) {
            return false;
         }
      }
      return true;
   }

   public static boolean isName1(String inPara) {
      if (!StringUtil.isEmpty(inPara)) {
         if (inPara.indexOf(",") != -1 || inPara.indexOf("'") != -1 || inPara.indexOf("\"") != -1 ||
            inPara.indexOf("\\") != -1) {
            return false;
         }
      }
      return true;
   }

   public static boolean isName2(String inPara) {
      if (!StringUtil.isEmpty(inPara)) {
         if (inPara.indexOf(",") != -1 || inPara.indexOf("\\") != -1) {
            return false;
         }
      }
      return true;
   }

   public static boolean isName3(String inPara) {
      if (!StringUtil.isEmpty(inPara)) {
         if (inPara.indexOf(":") != -1 || inPara.indexOf("-") != -1) {
            return false;
         }
      }
      return true;
   }

   public static boolean isName4(String inPara) {
      if (!StringUtil.isEmpty(inPara)) {
         if (inPara.indexOf("\\") != -1) {
            return false;
         }
      }
      return true;
   }


   public static String buildURL(String protocol, String serverAddress, Integer serverPort, String contextPath) {
      StringBuilder sb = new StringBuilder();
      int port = serverPort;

      if (port < 0) {
         port = 80; // Work around java.net.URL bug
      }

      sb.append(protocol);

      if (protocol.indexOf("://") == -1) {
         sb.append("://");
      }

      sb.append(serverAddress);

      if ((sb.indexOf("http://") != -1 && (port != 80)) || (sb.indexOf("https://") != -1 && (port != 443))) {
         sb.append(':');
         sb.append(port);
      }

      sb.append(contextPath);
      return sb.toString();
   }


   public static String trimFirstEnter(String src) {
      if (isEmpty(src)) {
         return src;
      }
      if (src.indexOf("\r\n") == 0) {
         src = src.substring(2);
      } else {
         return src;
      }
      return trimFirstEnter(src);
   }

   public static boolean getBooleanFromMap(Map<Object, Boolean> map, Object keyObj, boolean defaultValue) {
      Boolean v = map.get(keyObj);
      return v == null ? defaultValue : v;
   }

   public static String splitAndFilterHTMLString(String input) {
      if (isEmpty(input)) {
         return "";
      }
      return input.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "").replaceAll("&nbsp;",
         "");

   }

   public static boolean isNullOrZero(Integer id) {
      return id == null || id == 0;
   }


   /**
    * Whether the value is contained in the string array.
    * 
    * @param values
    * @param val
    * @return
    */
   public static boolean contains(String[] values, String val) {
      if (values == null || values.length == 0) {
         return false;
      }
      for (int idx = 0; idx < values.length; idx++) {
         if ((val == null && values[idx] == null) || (val != null && values[idx] != null && values[idx].equals(val))) {
            return true;
         }
      }
      return false;
   }

   public static String toXmpFormat(String input) {
      if (isEmpty(input)) {
         return "";
      }
      return input.replaceAll("&lt;", "<xmp style='display:inline'>&lt;</xmp>").replaceAll("&gt;",
         "<xmp style='display:inline'>&gt;</xmp>");
   }

   public static String removeTrTdTag(String errorMsg) {
      if (errorMsg == null) {
         return "";
      }
      errorMsg = errorMsg.replaceAll("<tr>", "").replaceAll("<td>", "").replaceAll("</td>", "").replaceAll("</tr>", "");
      return errorMsg;
   }

   /**
    * 作为正则表达式的替换文字，有如下要求 1. 不能只有单个"\",必须替换为"\\" 如： "t\t",在作为替换附时，必须改写为"t\\t" 2.
    * 不能只有单个"$",必须替换为"\$" 如：
    * "t$",在作为替换文字时,必须改写为"t\$",否则调用Matcher.appendReplacement时，有Exception
    * 
    * @param org
    * @return
    */
   public static String replaceReplacement(String org) {
      if (org == null) {
         return org;
      }

      StringBuilder sb = new StringBuilder();

      if (org.indexOf("\\") >= 0) {
         org = org.replaceAll("\\\\", "\\\\\\\\");
      }

      if (org.indexOf("$") >= 0) {
         org = org.replaceAll("\\$", "\\\\\\$");
      }
      return org;
   }

   public static String replaceScareQuote(String org) {
      if (org == null) {
         return org;
      }
      org = org.replace("'", "\\'");

      return org;
   }

   public static boolean checkPhoneNumber(String phoneNumber) {
      Pattern p = Pattern.compile("^\\+?\\d+(\\d+-?)*\\d+$");
      Matcher m = p.matcher(phoneNumber.replaceAll(" ", ""));
      if (!m.find()) {
         return false;
      }

      return true;
   }

   public static String trimPhoneNumber(String phoneNumber) {
      String strPhone = phoneNumber.replaceAll("-", "");

      strPhone = strPhone.replaceAll("\\+", "");
      strPhone = strPhone.replaceAll(" ", "");

      return strPhone;
   }


   public static boolean isSpaceString(String str) {
      String s = str.replaceAll("(\\s)", "");
      return ("".equals(s));
      // if (c == '\t' || c == ' ') {
      // return false;
      // } else {
      // return true;
      // }
   }

   public static int getDisplayRowCount(String str, int cols) {
      if (str == null || cols == 0) {
         return 0;
      }
      boolean isOdd = cols % 2 == 1;
      String[] rows = str.split("\n");
      int rowsCount = 0;
      for (int i = 0; i < rows.length; i++) {
         int rowLength = 0;
         int wordLength = 0;
         int preSpaceIndex = -2;
         boolean preIsSBC = false;
         for (int j = 0; j < rows[i].length(); j++) {
            if (rows[i].charAt(j) < 256) {
               rowLength++;
               if (isSpaceString(String.valueOf(rows[i].charAt(j)))) {
                  wordLength = 0;
                  preSpaceIndex = j;
               } else {
                  if (preSpaceIndex == j - 1 || preIsSBC) {
                     wordLength = 1;
                     preIsSBC = false;
                  } else {
                     wordLength++;
                  }
               }
            } else {
               rowLength += 2;
               wordLength = 2;
               preIsSBC = true;
            }

            if (rowLength >= cols - 1) {
               rowsCount++;
               if (rows[i].charAt(j) >= 256 || (j + 1 < rows[i].length() && rows[i].charAt(j + 1) >= 256) ||
                  rowLength == wordLength) {
                  if (rowLength == wordLength) {
                     wordLength = rowLength - cols + 1;
                  }
                  rowLength = rowLength - cols + 1;
                  if (isOdd && (rowLength % 2 == 1)) {
                     rowLength++;
                  }
               } else {
                  rowLength = wordLength;
               }
            }
         }
         if (rowLength > 0 || rows[i].length() == 0) {
            rowsCount++;
         }
      }
      return rowsCount;
   }
   
   /**
    * 判断字符串数字中是否存在重复的值
    * @param arrays
    * @return
    */
   public static boolean isRepeatInArrays(String[] arrays) {
      if ((null == arrays) || arrays.length < 2) {
         return false;
      }
      
      boolean flag = false;
      String s = StringUtil.toString(arrays, ",") + ","; 
      
      for (int i = 0; i < arrays.length; i++) {
         if (s.replaceFirst(arrays[i] + ",", "").indexOf(arrays[i] + ",") > -1) {
            flag = true;
         }
      } 
      return flag;
   }
   
   /**
    * 
    * 
    * @param str
    * @param key
    * @param replaceStr
    * @return
    */
   public static String replaceAllIgnoreCase(String str, String key, String replaceStr) {
      if (key == null || replaceStr == null) {
         return str;
      }
      StringBuilder tempStr = new StringBuilder();
      String lStr = str.toLowerCase();
      String lKey = key.toLowerCase();
      int begin = 0;
      int end = lStr.indexOf(lKey);
      
      while (end > -1) {
         tempStr.append(str.substring(begin, end));
         tempStr.append(replaceStr);
         begin = end + key.length();
         end = lStr.indexOf(lKey, begin);
      }
      if (str.length() > begin) {
         tempStr.append(str.substring(begin));
      }
      return tempStr.toString();
   }

   
   /**
    * 支持特殊符号分割
    * @param s
    * @return
    */
   public static String[] splitByCommaPattern(String s) {
      if (s != null && !StringUtil.isEmpty1(s)) {
         s = s.replaceAll(REGEX_REPLACE_COMMA, Constants.SPLITTER_COMMA);
         s = s.replaceAll(REGEX_REPLACE_ROW, Constants.SPLITTER_ROW);
      }
      List<String> results = new ArrayList<String>();
      //results = splitByCommaPattern(s, result);
      
      List<String> remains = new ArrayList<String>();
      // 先分析\"括住的名称
      parseNameByQuotePattern(s, REGEX_PATTERN_3_START, REGEX_PATTERN_3_END, results, remains);
      if (!remains.isEmpty()) {
         List<String> remains2 = new ArrayList<String>();
         for (String s2 : remains) {
            // 再分析"括住的名称
            parseNameByQuotePattern(s2, REGEX_PATTERN_2_START, REGEX_PATTERN_2_END, results, remains2);
         }
         if (!remains2.isEmpty()) {
            for (String s3 : remains2) {
               // 最后按中英文逗号分割名称
               String[] strs = splitStringWithEnZhComma(s3);
               if (strs != null) {
                  for (String str : strs) {
                     if (str != null && !"".equals(str.trim())) {
                        results.add(str.trim());
                     }
                  }
               }
            }
         }
      }
      
      return results.toArray(new String[results.size()]);
   }
   
   /**
    * 按模式的开头和结束分析名称，把分析成功的结果放到results，不成功的放到remains继续下一层次的分析
    * @param s
    * @param patternStart 模式的开头正则表达式
    * @param patternEnd  模式的结束正则表达式
    * @param results 分析成功的结果列表
    * @param remains 分析不成功的结果列表
    * @author macroliu
    */
   private static void parseNameByQuotePattern(String s, Pattern patternStart, Pattern patternEnd,
      List<String> results, List<String> remains) {
      s = s.trim();
      Matcher mStart = patternStart.matcher(s);
      Matcher mEnd = patternEnd.matcher(s);
      int start = -1;
      int startLength = 0;
      int end = -1;
      int endLength = 0;
      if (mEnd.find()) {
         end = mEnd.start();
         endLength = mEnd.end() - end;
         if (end == 0) {
            if (mEnd.find()) {
               end = mEnd.start();
               endLength = mEnd.end() - end;
            }
         }
         while (start + startLength < end && mStart.find()) {
            if (mStart.end() < end) {
               start = mStart.start();
               startLength = mStart.end() - start;
            }
         }
         if (end > 0 && start != -1) {
            results.add(s.substring(start + startLength, end).trim());
            // 保存前面的部分（分析不成功的部分）
            if (start > 0) {
               remains.add(s.substring(0, start).trim());
            }
            // 递归分析字符串后面的部分
            if (end + endLength < s.length()) {
               parseNameByQuotePattern(s.substring(end + endLength), patternStart, patternEnd, results, remains);
            }
         } else {
            remains.add(s);
         }
      } else {
         remains.add(s);
      }
   }
      
   /**
    * 是否满足某种模型
    * @param s
    * @param patternStart
    * @param patternEnd
    * @return -1: 匹配失败；0：匹配的开头=匹配的结尾
    * @author macroliu
    */
   private static int isPattern(String s, Pattern pattern) {
      if (s == null || "".equals(s.trim())) {
         return -1;
      }
      Matcher m = pattern.matcher(s);
      if (m.find()) {
         int idx = m.start();
         if (idx == 0) {
            if (m.find()) {
               idx = m.start();
            }
         }
         return idx;
      }
      return -1;
   }
   
   /**
    * 转化单个名称
    * @param str
    * @return
    */
   public static String wrapName(String str) {
      int idx = isPattern(str, REGEX_PATTERN_2);
      if (idx != -1) {
         return "\\\"" + str + "\\\"";
      } else {
         idx = isPattern(str, REGEX_PATTERN_1);
         if (idx != -1) {
            return "\"" + str + "\"";
         } else {
            return str;
         }
      }
   }
   
   /**
    * 扩展toHtml,使其支持空格符
    * @param s
    * @return
    */
   public static String toHTMLForCommonPattern(String s) {
      s = toHtml(s);
      //支持空格符
      s = s.replaceAll(" ", "&nbsp;");
      return s;
   }
   
   /**
    * 主要用于js传参，必须与splitByCommaPattern方法配对;前台用了toJsForCommonPattern方法，对应的后台match时，必须要采用支持特殊符号解析的match方法
    * 如ajax:autocomplete
    * @param s
    * @return
    */
   public static String toJsForCommonPattern(String s) {
      if (s == null || "".equals(s.trim())) {
         return s;
      }
      s = wrapName(s);
      s = s.replaceAll(Constants.SPLITTER_COMMA, REGEX_REPLACE_COMMA);
      s = s.replaceAll(Constants.SPLITTER_ROW, REGEX_REPLACE_ROW);
      return javascriptEscape(s);
   }
   
}
