
package codegen.main.business.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import codegen.main.business.CodeGeneratorEngine;
import codegen.util.StringUtil;

public class Entity {

   private CodeGeneratorEngine codeGenerator;

   private String outputAll;

   //model package
   private String packagePath;

   //action controller package
   private String controllerPackagePath;
   
   //view package
   private String jspPath;

   //struts pattern path
   private String strutsActionPath;

   //资源Key前缀
   private String resourceKeyPath;
   
   //实体类名
   private String className;

   //对应表名
   private String tableName;

   private String idGetter;

   private String idCounter;

   private List fields;

   private String bussinessNameField;

   private String author;

   //对应资源
   private I18N i18n;

   private Integer viewFuncId;

   private Integer editFuncId;

   private Boolean useDialog;
   
   //by zhajing
   //生成常规CURD
   private Boolean genCurd;
   
   //生成报表
   private Boolean genReport;
   
   /**
    * 报表ID
    */
   private String reportId;
   
   /**
    * 报表名称
    */
   private String reportName;

   public void setReportId(String reportId) {
      this.reportId = reportId;
   }
   
   public String getReportId() {
      if ("auto".equalsIgnoreCase(this.reportId)) {
         return "(SELECT MAX(r.ReportId) + 1 FROM reports r)";
      }
      return reportId;
   }
   
   public String getReportName() {
      return reportName;
   }

   public void setReportName(String reportName) {
      this.reportName = reportName;
   }

   public String getJspPath() {
      return jspPath;
   }

   public void setJspPath(String jspPath) {
      this.jspPath = jspPath;
   }

   public String getResourceKeyPath() {
      return resourceKeyPath;
   }

   public void setResourceKeyPath(String resourceKeyPath) {
      this.resourceKeyPath = resourceKeyPath;
   }

   public String getStrutsActionPath() {
      return strutsActionPath;
   }

   public void setStrutsActionPath(String strutsActionPath) {
      this.strutsActionPath = strutsActionPath;
   }

   public Integer getViewFuncId() {
      return viewFuncId;
   }

   public void setViewFuncId(Integer viewFuncId) {
      this.viewFuncId = viewFuncId;
   }

   public Integer getEditFuncId() {
      return editFuncId;
   }

   public void setEditFuncId(Integer editFuncId) {
      this.editFuncId = editFuncId;
   }

   public I18N getI18n() {
      return i18n;
   }

   public void setI18n(I18N i18n) {
      this.i18n = i18n;
   }

   public String getControllerPackagePath() {
      return controllerPackagePath;
   }

   public void setControllerPackagePath(String controllerPackagePath) {
      this.controllerPackagePath = controllerPackagePath;
   }

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public String getBussinessNameField() {
      return bussinessNameField;
   }

   public void setBussinessNameField(String bussinessNameField) {
      this.bussinessNameField = bussinessNameField;
   }

   public CodeGeneratorEngine getCodeGenerator() {
      return codeGenerator;
   }

   public void setCodeGenerator(CodeGeneratorEngine codeGenerator) {
      this.codeGenerator = codeGenerator;
   }

   public String getIdGetter() {
      return idGetter;
   }

   public void setIdGetter(String idGetter) {
      this.idGetter = idGetter;
   }

   public String getIdCounter() {
      return idCounter;
   }

   public void setIdCounter(String idCounter) {
      this.idCounter = idCounter;
   }

   public String getOutputAll() {
      return outputAll;
   }

   public void setOutputAll(String outputAll) {
      this.outputAll = outputAll;
   }

   public String getPackagePath() {
      return packagePath;
   }

   public void setPackagePath(String packagePath) {
      this.packagePath = packagePath;
   }

   public String getClassName() {
      return className;
   }

   public String getLowerFirstClassName() {
      return StringUtil.lowerCaseFirstChar(getClassName());
   }
   
   public String getUpperFirstClassName() {
      return StringUtil.upperCaseFirstChar(getClassName());
   }

   public void setClassName(String className) {
      this.className = className;
   }

   public String getTableName() {
      return tableName;
   }

   public void setTableName(String tableName) {
      this.tableName = tableName;
   }

   public List getFields() {
      return fields;
   }

   public void setFields(List fields) {
      this.fields = fields;
   }

   public String getPK() {
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (Boolean.TRUE.equals(f.getPk())) {
            return f.getColumnName();
         }
      }
      return null;
   }

   public Field getKey() {
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (Boolean.TRUE.equals(f.getPk())) {
            return f;
         }
      }
      return null;
   }

   public String genUpgradeScriptFileName() {
      return genOutputPath() + "upgradeScript.sql";
   }

   public String getCreateTs() {
      return getCodeGenerator().parseToCreateTs();
   }

   public String getCreater() {
      return getCodeGenerator().parseToCreater();
   }

   public String getTs() {
      return getCodeGenerator().parseToTs();
   }

   public String getTsUser() {
      return getCodeGenerator().parseToTsUser();
   }

   public String genOutputPath() {
      if (this.getOutputAll().endsWith("\\") || this.getOutputAll().endsWith("/")) {
         return this.getOutputAll().substring(0, this.getOutputAll().length() - 1) + "\\";
      } else {
         return this.getOutputAll() + "\\";
      }
   }

   public List getFieldListExclueSys() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (!f.getIsSysMember() && !f.getIsSysObj()) {
            list.add(f);
         }
      }
      return list;
   }
   
   public List getFieldListExclueSysMember() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (!f.getIsSysMember()) {
            list.add(f);
         }
      }
      return list;
   }
   
   public List getFieldListSysObj() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getIsSysObj()) {
            list.add(f);
         }
      }
      return list;
   }
   
   public List getFieldListSysObjCurrency() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getIsSysObjCurrency()) {
            list.add(f);
         }
      }
      return list;
   }

   public List getFieldListHasDefault() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (!StringUtil.isEmpty(f.getDefaultValue())) {
            list.add(f);
         }
      }
      return list;
   }

   public List getFieldListHasOption() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getHasOptions()) {
            list.add(f);
         }
      }
      return list;
   }
   
   public List getFieldListHasFormat() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getHasFormat()) {
            list.add(f);
         }
      }
      return list;
   }

   public List getFieldListReadonly() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getReadonly()) {
            list.add(f);
         }
      }
      return list;
   }


   /**
    * 显示列表中，非One-to-many, 非主键， 非one-to-one的字段
    * @return
    */
   public List getFieldListExcluePkAndSys() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (!f.getIsSysMember() && !f.getPk() &&!f.getIsSysObj()) {
            list.add(f);
         }
      }
      return list;
   }

   /**
    * One-to-many
    * @return
    */
   public List getSysMemberFields() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getIsSysMember()) {
            list.add(f);
         }
      }
      return list;
   }


   /**
    * 显示在基本信息中
    * @return
    */
   public List getShowFields() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getShow()) {
            list.add(f);
         }
      }
      return list;
   }

   /**
    * 显示在列表的查询中
    * @return
    */
   public List getShowInSearchFields() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getShowInSearch()) {
            list.add(f);
         }
      }
      return list;
   }
   
   /**
    * 显示在列表中
    * @return
    */
   public List getShowInListFields() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getShowInList()) {
            list.add(f);
         }
      }
      return list;
   }
   
   /**
    * 显示在报表列表中
    * @return
    */
   public List getShowInReportListFields() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getShowInReportList()) {
            list.add(f);
         }
      }
      return list;
   }

   /**
    * 显示在报表查询条件中
    * @return
    */
   public List getShowInReportSearchFields() {
      List list = new ArrayList();
      for (Object obj : fields) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getShowInList()) {
            list.add(f);
         }
      }
      return list;
   }
   
   public String getFullPathClassName() {
      return getPackagePath() + "." + getClassName();
   }

   public List<String> getSysMemberInvolvementList() {
      List<String> list = new ArrayList();
      for (Object obj : getFields()) {
         Field f = (Field) obj;
         f.setEntity(this);
         if (f.getIsSysMember()) {
            list.add(f.getSysMember().getInvolvement());
         }
      }
      return list;
   }

   public String getSysMemberInvolvementInSql() {
      List invList = getSysMemberInvolvementList();
      Set invSet = new HashSet();
      invSet.addAll(invList);
      return getCodeGenerator().parseToInSQL(invSet);
   }

   public boolean getHasSysMember() {
      return getSysMemberFields().size() > 0;
   }

   public String getMemberName() {
      return getClassName() + "Member";
   }

   public String getLowerFirstMemberName() {
      return StringUtil.lowerCaseFirstChar(getMemberName());
   }

   public String getMemberTableName() {
      return getTableName() + "_member";
   }

   public String getHelperName() {
      return getUpperFirstClassName() + "Helper";
   }

   public String getProcessName() {
      return getUpperFirstClassName() + "Process";
   }

   public String getFormName() {
      return getUpperFirstClassName() + "Form";
   }

   public String getActionName() {
      return getUpperFirstClassName() + "Action";
   }

   public String getExportProcessName() {
      return getUpperFirstClassName() + "ExportProcess";
   }
   
   public String getLowerFirstFormName() {
      return StringUtil.lowerCaseFirstChar(getFormName());
   }

   public String getShortName() {
      return toShortName(getClassName());
   }

   public String getMemberShortName() {
      return toShortName(getMemberName());
   }

   public String toShortName(String name) {
      String shortName = "";
      char[] charArray = name.toCharArray();
      for (char c : charArray) {
         if (c >= 'A' && c <= 'Z') {
            shortName += String.valueOf(c).toLowerCase();
         }
      }
      if (shortName == "" && name != "") {
         shortName = String.valueOf(name.charAt(0)).toLowerCase();
      }
      return shortName;
   }

   public String getObjTypeConstant() {
      return "TYPE_" + getClassName().toUpperCase();
   }

   public String getNameResourceKey() {
      return getResourceKeyPath() + getLowerFirstClassName() + ".name";
   }

   public boolean getHasBussinessNameField() {
      return !StringUtil.isEmpty(getBussinessNameField());
   }
   
   public boolean getHasSysObjCurrency() {
      List<Field> list = getFieldListSysObj();
      for(Field f : list) {
         if (f.getIsSysObjCurrency()) {
            return true;
         }
      }
      return false;
   }

   public Boolean getUseDialog() {
      return useDialog;
   }

   public void setUseDialog(Boolean useDialog) {
      this.useDialog = useDialog;
   }

   public Boolean getGenCurd() {
      return genCurd;
   }

   public void setGenCurd(Boolean genCurd) {
      this.genCurd = genCurd;
   }

   public Boolean getGenReport() {
      return genReport;
   }

   public void setGenReport(Boolean genReport) {
      this.genReport = genReport;
   }
   
}
