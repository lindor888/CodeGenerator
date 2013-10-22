package codegen.main.business;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import codegen.main.business.model.Entity;
import codegen.main.business.model.Field;
import codegen.util.StringUtil;

public class CodeGeneratorEngine {

   private Entity _entity;

   private int _dfCount = 1;
   
   private int _fkCount = 1;

   public CodeGeneratorEngine(Entity entity) {
      _entity = entity; //CodeGenHelper.loadEntity();
      _entity.setCodeGenerator(this);
   }

   public void genTblFile() throws CodeGenException {
      genModelTblFile();
      if (_entity.getSysMemberFields().size() > 0) {
         genModelMemberTblFile();
      }
      genDataSqlFile();
   }

   public void genDataSqlFile() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + "data.sql";
      mergeContext(GenTemplate.NAME_DATA_SQL, context, output, true);
   }

   public void deleteOutput() throws CodeGenException {
      File file = new File(_entity.getOutputAll());
      System.out.print("[Path]Output: " + file.getAbsolutePath());
      if (file.exists() && file.isDirectory()) {
         file.delete();
      }
   }

   public void createOutput() {
      File file = new File(_entity.getOutputAll());

      if (!file.exists()) {
         file.mkdir();
      }
   }

   public void genModelMemberTblFile() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("tableName", _entity.getTableName());
      context.put("pk", _entity.getPK());
      context.put("entity", _entity);
      context.put("header", true);

      String output = _entity.genOutputPath() + _entity.getTableName() + "_member.tbl";
      mergeContext(GenTemplate.NAME_MODEL_MEMBER_TBL, context, output, true);
   }

   private void mergeContext(String templateName, VelocityContext context, String outputFileName, boolean mergeToUpgrade)
      throws CodeGenException {
      Template template = CodeGenHelper.getTemplate(templateName);
      if (template != null) {

         String output = outputFileName;
         File file = new File(output);
         if (!file.exists()) {
            try {
               file.createNewFile();
            } catch (IOException e) {
               throw new CodeGenException(e);
            }
         }

         FileWriterWithEncoding fwwe = null;
         FileWriterWithEncoding fwweUpgrade = null;
         try {
            fwwe = new FileWriterWithEncoding(output, "UTF-8");
            template.merge(context, fwwe);
            fwwe.flush();

            if (mergeToUpgrade) {
               String upgrade = _entity.genUpgradeScriptFileName();
               fwweUpgrade = new FileWriterWithEncoding(upgrade, "UTF-8", true);
               context.remove("header");
               template.merge(context, fwweUpgrade);
               fwweUpgrade.flush();
            }
         } catch (IOException e) {
            throw new CodeGenException(e);
         } catch (ResourceNotFoundException e) {
            throw new CodeGenException(e);
         } catch (ParseErrorException e) {
            throw new CodeGenException(e);
         } catch (MethodInvocationException e) {
            throw new CodeGenException(e);
         } catch (Exception e) {
            throw new CodeGenException(e);
         } finally {
            if (fwwe != null) {
               try {
                  fwwe.close();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
            if (fwweUpgrade != null) {
               try {
                  fwweUpgrade.close();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         }
      }
   }

   public void genModelTblFile() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("tableName", _entity.getTableName());
      context.put("pk", _entity.getPK());
      context.put("fields", parseToDbFieldList(_entity.getFieldListExclueSysMember()));
      context.put("entity", _entity);
      context.put("header", true);

      String output = _entity.genOutputPath() + _entity.getTableName() + ".tbl";
      mergeContext(GenTemplate.NAME_MODEL_TBL, context, output, true);
   }

   public String parseToDbField(Field field) {
      if (field == null) {
         return "";
      }

      StringBuilder sb = new StringBuilder();
      sb.append(field.getColumnName());
      sb.append("\t");
      sb.append(field.getColumnType());
      sb.append("\t");
      sb.append(Boolean.TRUE.equals(field.getNotnull()) ? "NOT NULL" : "NULL");
      return sb.toString();
   }

   public String parseToCreater() {
      return "CreaterId\tid NOT NULL";
   }

   public String parseToCreateTs() {
      return "CreateTS\tts CONSTRAINT [DF" + getCurrentDF() + "_" + _entity.getTableName() +
         "] DEFAULT (getdate()) NOT NULL";
   }

   public String parseToTs() {
      return "Ts\tts CONSTRAINT [DF" + getCurrentDF() + "_" + _entity.getTableName() + "] DEFAULT (getdate()) NOT NULL";
   }

   public String parseToTsUser() {
      return "TsUserId\tid NOT NULL";
   }

   public synchronized int getCurrentDF() {
      int df = _dfCount;
      _dfCount++;
      return df;
   }
   
   public synchronized int getCurrentFK() {
      int fk = _fkCount;
      _fkCount++;
      return fk;
   }

   public List<String[]> parseToGetsetterList(List<Field> fieldList) {
      List<String[]> list = new ArrayList<String[]>();
      for (Field field : fieldList) {
         String[] getsetter = new String[2];
         getsetter[0] = parseToGetter(field);
         getsetter[1] = parseToSetter(field);
         list.add(getsetter);
      }
      return list;
   }

   public String parseToGetter(Field field) {
      StringBuilder sb = new StringBuilder();
      sb.append("public ").append(field.getFieldType()).append(" get");
      sb.append(StringUtil.upperCaseFirstChar(field.getFieldName()));
      sb.append("() {\n");
      sb.append("return _" + field.getFieldName());
      sb.append("}");
      return sb.toString();
   }

   public String parseToSetter(Field field) {
      StringBuilder sb = new StringBuilder();
      sb.append("public void set");
      sb.append(StringUtil.upperCaseFirstChar(field.getFieldName()));
      sb.append("(" + field.getFieldType() + " " + field.getFieldName() + ") {\n");
      sb.append("_" + field.getFieldName() + " = " + field.getFieldName());
      sb.append("}");
      return sb.toString();
   }

   public String parseToJavaField(Field field) {
      StringBuilder sb = new StringBuilder();
      sb.append("private ").append(field.getFieldType()).append(" _").append(field.getFieldName()).append(";");
      return sb.toString();
   }

   public String parseToInSQL(Collection<String> col) {
      StringBuilder sb = new StringBuilder();
      Iterator<String> it = col.iterator();
      while (it.hasNext()) {
         String s = it.next();
         sb.append("'").append(s).append("'");
         if (it.hasNext()) {
            sb.append(",");
         }
      }
      return sb.toString();
   }

   public List<String> parseToJavaFieldList(List<Field> fieldList) {
      List<String> list = new ArrayList<String>();
      for (Field field : fieldList) {
         list.add(parseToJavaField(field));
      }
      return list;
   }

   public List<String> parseToDbFieldList(List<Field> fieldList) {
      List<String> list = new ArrayList<String>();
      Iterator<Field> it = fieldList.iterator();

      while (it.hasNext()) {
         Field f = it.next();
         StringBuilder line = new StringBuilder();
         line.append(parseToDbField(f)).append(",");
         list.add(line.toString());
      }

      list.add(_entity.getCreater() + ",");
      list.add(_entity.getCreateTs() + ",");
      list.add(_entity.getTs() + ",");
      list.add(_entity.getTsUser());
      return list;
   }

   public void genJavaModelFile() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getClassName() + ".java";
      mergeContext(GenTemplate.NAME_MODEL_JAVA_SQL, context, output, false);
   }

   public void genJavaMemberFile() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getMemberName() + ".java";
      mergeContext(GenTemplate.NAME_ENTITY_MEMBER, context, output, false);
   }

   public void genJavaMemberPKFile() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getMemberName() + "PK.java";
      mergeContext(GenTemplate.NAME_ENTITY_MEMBERPK, context, output, false);
   }

   public void genJavaController() throws CodeGenException {
      genJavaEntityForm();
      genJavaEntityAction();
      genJavaEntityHelper();
   }

   public void genJavaProcess() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getProcessName() + ".java";
      mergeContext(GenTemplate.NAME_ENTITY_PROCESS, context, output, false);
   }

   public void genJavaExportProcess() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getExportProcessName() + ".java";
      mergeContext(GenTemplate.NAME_ENTITY_EXPORT_PROCESS, context, output, false);
   }
   
   public void genJavaEntityForm() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getClassName() + "Form.java";
      mergeContext(GenTemplate.NAME_ENTITY_FORM, context, output, false);
   }

   public void genJavaEntityAction() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getClassName() + "Action.java";
      mergeContext(GenTemplate.NAME_ENTITY_ACTION, context, output, false);
   }

   public void genJavaEntityHelper() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getClassName() + "Helper.java";
      mergeContext(GenTemplate.NAME_ENTITY_HELPER, context, output, false);
   }

   public void genHibernateFile() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getClassName() + ".hbm.xml";
      mergeContext(GenTemplate.NAME_MODEL_HBM_XML, context, output, false);

      if (_entity.getSysMemberFields().size() > 0) {
         output = _entity.genOutputPath() + _entity.getMemberName() + ".hbm.xml";
         mergeContext(GenTemplate.NAME_ENTITY_MEMBER_HBM_XML, context, output, false);
      }

      output = _entity.genOutputPath() + "hiberante.cfg.xml";
      mergeContext(GenTemplate.NAME_HIBERANATECFG_XML, context, output, false);
   }

   public void gen() throws CodeGenException {
      deleteOutput();
      createOutput();
      genTblFile();
      genDependency();
      genJavaModelFile();
      if (_entity.getSysMemberFields().size() > 0) {
         genJavaMemberFile();
         genJavaMemberPKFile();
      }
      genHibernateFile();
      genJavaController();
      genJavaProcess();
      if (_entity.getGenReport()) {
         genJavaExportProcess();
      }
      genLookupManager();
      genApplicationResource();
      genJsp();
      genStrutsConfig();
   }

   public void genEntityReportJsp() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getLowerFirstClassName() + "Report.jsp";
      mergeContext(GenTemplate.NAME_ENTITY_REPORT_JSP, context, output, false);
   }
   
   public void genStrutsConfig() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + "struts-config.xml";
      mergeContext(GenTemplate.NAME_STRUTS_CONFIG, context, output, false);
   }

   public void genApplicationResource() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + "ApplicationResources_zh_CN.properties";
      mergeContext(GenTemplate.NAME_APPLICATIONRESOURCE, context, output, false);
   }

   public void genDependency() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + "dependency.txt";
      mergeContext(GenTemplate.NAME_DEPENDENCY, context, output, false);
   }

   public void genLookupManager() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + "LookupManager.java.part";
      mergeContext(GenTemplate.NAME_LOOKUPMANAGER, context, output, false);
   }

   public void genJsp() throws CodeGenException {
      genEntityListJsp();
      genEntityInfoJsp();
      genDialogJs();
      if (_entity.getGenReport()) {
         genEntityReportJsp();
      }
   }

   public void genDialogJs() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + "dialog.js.part";
      mergeContext(GenTemplate.NAME_ENTITY_DIALOG_JS, context, output, false);
   }
   
   public void genEntityListJsp() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getLowerFirstClassName() + "List.jsp";
      mergeContext(GenTemplate.NAME_ENTITY_LIST_JSP, context, output, false);
   }
   
   public void genEntityInfoJsp() throws CodeGenException {
      VelocityContext context = new VelocityContext();
      context.put("entity", _entity);

      String output = _entity.genOutputPath() + _entity.getLowerFirstClassName() + "Info.jsp";
      mergeContext(GenTemplate.NAME_ENTITY_INFO_JSP, context, output, false);
   }

   public static void main(String[] args) throws ResourceNotFoundException, ParseErrorException,
      MethodInvocationException, Exception {
     
   }

   public Entity getEntity() {
      return _entity;
   }

   public void setEntity(Entity entity) {
      _entity = entity;
   }

}
