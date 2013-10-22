package codegen.main.business.model;

import java.util.List;

import codegen.util.Constants;
import codegen.util.StringUtil;

/**
 * @author kentchen
 */
public class Field {

   private Entity entity;

   private String fieldName;

   private String fieldType;

   private String columnName;

   private String columnType;

   private Boolean notnull;

   private Boolean pk;

   //是否在baseInfo上显示
   private Boolean show;

   private I18N i18n;

   private Component component;

   //add by zhajing
   //是否在列表查询条件中显示
   private Boolean showInSearch;

   //是否显示在查询列表中
   private Boolean showInList;
   
   //是否在报表查询条件中显示
   private Boolean showInReport;

   //是否在报表在查询列表中显示
   private Boolean _showInReportList;

   private Boolean readonly;

   private SysMember sysMember;

   private String sysObj;

   private String defaultValue;

   private List<Option> options;


   public boolean getIsSysObj() {
      return !StringUtil.isEmpty(this.sysObj);
   }
   
   public boolean getIsSysMember() {
      SysMember sm = getSysMember();
      return sm != null && !StringUtil.isEmpty(sm.getMemberType()) && !StringUtil.isEmpty(sm.getInvolvement());
   }


   public String getUpperFirstFieldName() {
      return StringUtil.upperCaseFirstChar(getFieldName());
   }

   public String getConstantMemberName() {
      return "MEMBER_INVOLVEMENT_" + getSysMember().getInvolvement().toUpperCase();
   }

   public String getRequired() {
      if (component != null && component.getRequired()) {
         return "true";
      } else {
         return "false";
      }
   }

   public String getMaxLengthAttr() {
      if (getIsSysMember()) {
         return "";
      }

      return "maxlength=\"64\"";
   }

   public String getStyleClassAttr() {
      return "styleClass=\"text\"";
   }

   public String getAlt() {
      boolean isRequired = StringUtil.toBoolean(getRequired(), false);
      if (isRequired) {
         return "notnull";
      } else {
         return "null";
      }
   }

   public String getGearPolicy() {
      if (getReadonly()) {
         return "display";
      }

      return "${" + entity.getLowerFirstFormName() + ".gearPolicy}";
   }

   public String getMemberTypeAttr() {
      if (!getIsSysMember()) {
         return "";
      }

      String memberType = getSysMember().getMemberType();
      String[] typeList = memberType.split(",");
      StringBuilder sb = new StringBuilder();
      for (String type : typeList) {
         sb.append(type.trim());
         sb.append("|");
      }
      sb.deleteCharAt(sb.length() - 1);
      return "memberType=\"" + sb.toString() + "\"";
   }

   public String getMatchMethod() {
      String type = getSysMember().getMemberType().trim();
      if (Constants.OBJ_TYPE_USER.equalsIgnoreCase(type)) {
         return "matchUsers";
      } else if (Constants.OBJ_TYPE_COMPANY.equalsIgnoreCase(type)) {
         return "matchCompanys";
      } else if (Constants.OBJ_TYPE_PRODUCT.equalsIgnoreCase(type)) {
         return "matchProducts";
      } else if (Constants.OBJ_TYPE_USERPARTY.equalsIgnoreCase(type)) {
         return "matchUserOrPartys";
      } else if (Constants.OBJ_TYPE_USERPARTYGROUP.equalsIgnoreCase(type)) {
         return "matchUserOrPartyOrGroups";
      }  else if (Constants.OBJ_TYPE_USERPARTYCOMPANY.equalsIgnoreCase(type)) {
         return "matchUserOrPartyOrCompanies";
      }  else if ("Group".equals(type)) {
         return "matchGroups";
      } else {
         return "match" + StringUtil.upperCaseFirstChar(StringUtil.trim(type)) + "s";
      }
   }

   public boolean getIsBussinessNameField() {
      return getFieldName().equals(entity.getBussinessNameField());
   }

   public boolean getHasOptions() {
      return getOptions() != null && getOptions().size() > 0;
   }

   public boolean getHasFormat() {
      return getComponent() != null && !StringUtil.isEmpty(getComponent().getFormat());
   }

   public boolean getIsNumberFormat() {
      return "number".equalsIgnoreCase(getComponent().getFormat().trim());
   }

   public boolean getIsTextFormat() {
      return "text".equalsIgnoreCase(getComponent().getFormat().trim());
   }

   public String getSysObjClassName() {
      
      return StringUtil.upperCaseFirstChar(StringUtil.trim(getSysObj()));
   }

   public boolean getIsSysObjCurrency() {
      return "currency".equalsIgnoreCase(StringUtil.trim(getSysObj()));
   }

   public String getSysObjLoadCode() {
      
      return getEntity().getProcessName() + "getInstance().getObject(this.get" + getUpperFirstFieldName() + "()";
   }

   public boolean getIsDefaultValueAuto() {
      return "auto".equalsIgnoreCase(getDefaultValue());
   }
   
   public String getDefaultValueAuto() {
      if (getIsDefaultValueAuto()) {
         if (getIsSysObjCurrency()) {
            return "CurrencyProcess.getInstance().getDefaultCurrency()";
         }
      }
      return "null";
   }


   public Entity getEntity() {
      return entity;
   }


   public void setEntity(Entity entity) {
      this.entity = entity;
   }


   public String getFieldName() {
      return fieldName;
   }


   public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
   }


   public String getFieldType() {
      return fieldType;
   }


   public void setFieldType(String fieldType) {
      this.fieldType = fieldType;
   }


   public String getColumnName() {
      return columnName;
   }


   public void setColumnName(String columnName) {
      this.columnName = columnName;
   }


   public String getColumnType() {
      return columnType;
   }


   public void setColumnType(String columnType) {
      this.columnType = columnType;
   }


   public Boolean getNotnull() {
      return notnull;
   }


   public void setNotnull(Boolean notnull) {
      this.notnull = notnull;
   }


   public Boolean getPk() {
      return pk;
   }


   public void setPk(Boolean pk) {
      this.pk = pk;
   }


   public Boolean getShow() {
      return show;
   }


   public void setShow(Boolean show) {
      this.show = show;
   }


   public I18N getI18n() {
      return i18n;
   }


   public void setI18n(I18N i18n) {
      this.i18n = i18n;
   }


   public Component getComponent() {
      return component;
   }


   public void setComponent(Component component) {
      this.component = component;
   }


   public Boolean getShowInSearch() {
      return showInSearch;
   }


   public void setShowInSearch(Boolean showInSearch) {
      this.showInSearch = showInSearch;
   }


   public Boolean getShowInList() {
      return showInList;
   }


   public void setShowInList(Boolean showInList) {
      this.showInList = showInList;
   }


   public Boolean getShowInReport() {
      return showInReport;
   }


   public void setShowInReport(Boolean showInReport) {
      this.showInReport = showInReport;
   }


   public Boolean getShowInReportList() {
      return _showInReportList;
   }


   public void setShowInReportList(Boolean showInReportList) {
      _showInReportList = showInReportList;
   }


   public Boolean getReadonly() {
      return readonly;
   }


   public void setReadonly(Boolean readonly) {
      this.readonly = readonly;
   }


   public SysMember getSysMember() {
      return sysMember;
   }


   public void setSysMember(SysMember sysMember) {
      this.sysMember = sysMember;
   }


   public String getSysObj() {
      return sysObj;
   }


   public void setSysObj(String sysObj) {
      this.sysObj = sysObj;
   }


   public String getDefaultValue() {
      return defaultValue;
   }


   public void setDefaultValue(String defaultValue) {
      this.defaultValue = defaultValue;
   }


   public List<Option> getOptions() {
      return options;
   }


   public void setOptions(List<Option> options) {
      this.options = options;
   }
   
}
