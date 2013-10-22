package codegen.i18n;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * @author zhajingzha
 *
 */
public class Validator {
   public static boolean isPwd(String s) {
      if (s == null) {
         return false;
      }
      return Pattern.matches("^\\w+$", s);
   }

   public static boolean isIPAddress(String s) {
      if (s == null) {
         return false;
      }
      try {
         String[] segs = s.split("\\.");
         if (segs.length != 4) {
            return false;
         }

         for (String seg : segs) {
            int i = Integer.parseInt(seg);
            if (i < 0 || i > 255) {
               return false;
            }
         }
      } catch (Exception ex) {
         return false;
      }
      return true;
   }

   public static boolean isPort(String s) {
      try {
         int port = Integer.parseInt(s);
         if (port < 1 || port > 65535) {
            return false;
         }
      } catch (Exception e) {
         return false;
      }
      return true;
   }

   /**
    * Form control value constrain.
    * 
    * @author taylortang
    */
   public abstract static class Constrain {
      Collection<Constrain> _constrains = new ArrayList<Constrain>();

      public final Constrain add(Constrain constrain) {
         this._constrains.add(constrain);
         return this;
      }

      public final boolean check(Object obj) {
         boolean b = true;
         for (Constrain c : _constrains) {
            b = b && c.check(obj);
         }
         return b && isValid(obj);
      }

      protected abstract boolean isValid(Object obj);
   }

   public static Constrain notNullConstrain() {
      return new Constrain() {
         @Override
         protected boolean isValid(Object obj) {
            return obj != null;
         }

         public String toString() {
            return "Not null constrain";
         }
      };
   }

   public static Constrain notEmptyConstrain() {
      return new Constrain() {
         @Override
         protected boolean isValid(Object obj) {
            return obj != null && obj.toString().trim().length() > 0;
         }

         public String toString() {
            return "Not empty constrain";
         }
      };
   }

   public static Constrain intConstrain() {
      return new Constrain() {
         @Override
         protected boolean isValid(Object obj) {
            try {
               Integer.parseInt(obj.toString());
               return true;
            } catch (Exception e) {
               return false;
            }
         }

         public String toString() {
            return "Integer constrain";
         }
      };
   }

   public static Constrain intRangeConstrain(final int min, final int max, final boolean include) {
      return new Constrain() {
         @Override
         protected boolean isValid(Object obj) {
            try {
               int i = Integer.parseInt(obj.toString());
               if (include) {
                  return i >= min && i <= max;
               }
               return i > min && i < max;
            } catch (Exception e) {
               return false;
            }
         }

         public String toString() {
            return "Integer range constrain";
         }
      };
   }

   public static Constrain positiveIntConstrain() {
      return new Constrain() {
         @Override
         protected boolean isValid(Object obj) {
            try {
               return Integer.parseInt(obj.toString()) > 0;
            } catch (Exception e) {
               return false;
            }
         }

         public String toString() {
            return "Positive integer constrain";
         }
      };
   }

   public static Constrain ipAddrConstrain() {
      return new Constrain() {
         @Override
         protected boolean isValid(Object obj) {
            return obj != null && isIPAddress(obj.toString());
         }

         public String toString() {
            return "IP address constrain";
         }
      };
   }

   public static Constrain tcpPortConstrain() {
      return new Constrain() {
         @Override
         protected boolean isValid(Object obj) {
            return obj != null && isPort(obj.toString());
         }

         public String toString() {
            return "TCP port constrain";
         }
      };
   }

   public static String generateErrorMessage(List errs) {
      StringBuffer sb = new StringBuffer();
      for (Object o : errs) {
         if (o != null && o.toString().trim().length() > 0) {
            sb.append(o.toString().trim()).append("\n");
         }
      }
      return sb.toString();
   }
}
