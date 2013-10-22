package codegen.main.business;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import codegen.main.business.model.Component;
import codegen.main.business.model.Entity;
import codegen.main.business.model.Field;
import codegen.main.business.model.I18N;
import codegen.main.business.model.Option;
import codegen.main.business.model.SysMember;
import codegen.util.StringUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class CodeGenHelper {

   private static VelocityEngine _engine = new VelocityEngine();

   private static boolean _hasInit;

   public static void genTbl() {

   }

   public synchronized static boolean initEngine() {
      if (_hasInit) {
         return false;
      }

      _engine.addProperty("file.resource.loader.class",
         "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
      _engine.addProperty("input.encoding", "utf-8");
      _engine.addProperty("output.encoding", "utf-8");
      _engine.addProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
      try {
         _engine.init();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return true;
   }

   /**
    * template： 如com/wisagetech/codegen/template/modelTbl.tpl
    * 
    * @param template
    * @return
    * @throws CodeGenException
    * @throws Exception
    * @throws ParseErrorException
    * @throws ResourceNotFoundException
    * @throws ResourceNotFoundException
    * @throws ParseErrorException
    * @throws Exception
    */
   public static synchronized Template getTemplate(String template) throws CodeGenException {
      if (!_hasInit) {
         initEngine();
      }
      Template t = null;
      try {
         t = _engine.getTemplate(template);
      } catch (ResourceNotFoundException e) {
         throw new CodeGenException(e);
      } catch (ParseErrorException e) {
         throw new CodeGenException(e);
      } catch (Exception e) {
         throw new CodeGenException(e);
      }

      return t;
   }

   public static Entity loadEntity(String file) throws CodeGenException {
      XStream xstream = new XStream(new DomDriver());
      String xml;
      try {
         xml = StringUtil.readFromFile(new File(file), "UTF-8");
      } catch (Exception e) {
         throw new CodeGenException(e);
      }
      xstream.alias("field", Field.class);
      xstream.alias("entity", Entity.class);
      xstream.alias("i18n", I18N.class);
      xstream.alias("component", Component.class);
      xstream.alias("sysMember", SysMember.class);
      xstream.alias("option", Option.class);
      Entity entity = (Entity) xstream.fromXML(xml);
      return entity;
   }

   public static Entity loadEntity() throws CodeGenException {
      XStream xstream = new XStream(new DomDriver());
      String xml = null;
      try {
         xml = StringUtil.readFromStream(CodeGenHelper.class.getResourceAsStream("entity.xml"), "UTF-8");
      } catch (IOException e) {
         throw new CodeGenException(e);
      }
      // xstream.aliasPackage("c", "com.wisagetech.codegen.model");
      xstream.alias("field", Field.class);
      xstream.alias("entity", Entity.class);
      xstream.alias("i18n", I18N.class);
      xstream.alias("component", Component.class);
      xstream.alias("sysMember", SysMember.class);
      xstream.alias("option", Option.class);
      Entity entity = (Entity) xstream.fromXML(xml);
   
      return entity;
   }

   public static String saveTempFile(Entity entity, String path) throws CodeGenException, IOException {
      XStream xstream = new XStream(new DomDriver());
      
      xstream.aliasField("field", Field.class, "field");
      xstream.aliasField("entity", Entity.class, "entity");
      xstream.aliasField("i18n", I18N.class, "i18n");
      xstream.aliasField("component", Component.class, "component");
      xstream.aliasField("sysMember", SysMember.class, "sysMember");
      xstream.aliasField("option", Option.class, "option");
      
      String xml = xstream.toXML(entity);
      String fileName = path + "entity.xml";
      FileUtils.writeStringToFile(new File(fileName), xml, "UTF-8");
      return fileName;
   }
   
   //返回临时存放文件路径
   public static String getAttTempPath() {
      return getAbsolutePath() + "\\src\\codegen\\main\\business\\temp\\";
   }
   
   public static String getAbsolutePath() {
//    System.out.println(CodeGenWindow.class.getClassLoader().getResource(""));  
//    System.out.println(ClassLoader.getSystemResource(""));  
//    System.out.println(CodeGenWindow.class.getResource(""));  
//    System.out.println(CodeGenWindow.class.getResource("/"));
//    //Class文件所在路径
//    System.out.println(new File("/").getAbsolutePath());  
//    System.out.println(System.getProperty("user.dir"));  
    return System.getProperty("user.dir");
 }
   
   public static void main(String[] args) throws Exception {
      Entity entity = loadEntity("D:\\myworkspaces\\work5\\BR_2_2_0-8thManage\\src\\com\\wisagetech\\codegen\\entity.xml");
   }
}
