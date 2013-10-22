package codegen.util;

import java.io.IOException;

public class Native2ascii {

   private static final String java_path   = "C:\\WisageTech\\WisageTechR1\\8thManage\\jdk";
   private static final String target_file = "D:\\WorkSpace\\TestSpace\\codeGen\\src\\codegen\\i18n\\messages_zh_CN.properties.native";
   private static final String result_file = "D:\\WorkSpace\\TestSpace\\codeGen\\src\\codegen\\i18n\\messages_zh_CN.properties";
   private static final String encoding    = "utf-8";

   public static void native2ascii() {
      try {
         System.out.println("Begin to execute...");
         Runtime.getRuntime().exec(
               java_path + "\\bin\\native2ascii.exe -encoding " + encoding
                     + " " + target_file + " " + result_file);
         
         System.out.println("End");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void main(String arg[]) {
      native2ascii();
   }
}