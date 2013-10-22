package codegen.swing;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import codegen.i18n.Messages;
import codegen.swing.widget.FormPanel;
import codegen.swing.widget.GTCheckBox;
import codegen.swing.widget.GTComboBox;
import codegen.swing.widget.GTTextField;
import codegen.util.Constants;
import codegen.util.SpringUtilities;
import codegen.util.StringUtil;

/**
 * 
 * @author zhajingzha
 *
 */
public class CodeGenFieldDialog extends FormDialog {
   
   final static int GAP = 10;
   
   final static String[] MEMBER_TYPES = new String[]{Constants.OBJ_TYPE_USER, Constants.OBJ_TYPE_USERPARTY,
      Constants.OBJ_TYPE_USERPARTYGROUP, Constants.OBJ_TYPE_USERPARTYCOMPANY,
      Constants.OBJ_TYPE_COMPANY, Constants.OBJ_TYPE_PRODUCT, Constants.OBJ_TYPE_OTHER};
   
   final static String[] FIELD_TYPES = new String[]{Constants.SPLITTER_BLANK, Constants.FIELD_TYPE_INTEGER, Constants.FIELD_TYPE_BOOLEAN, Constants.FIELD_TYPE_DATE,
      Constants.FIELD_TYPE_DOBULE,  Constants.FIELD_TYPE_STRING,  Constants.FIELD_TYPE_SET};
   
   final static String[] COLUMN_TYPES = new String[]{Constants.SPLITTER_BLANK, Constants.FIELD_COLUMN_TYPE_ID, Constants.FIELD_COLUMN_TYPE_BOOLEAN, Constants.FIELD_COLUMN_TYPE_TS,
      Constants.FIELD_COLUMN_TYPE_NUMBER,  Constants.FIELD_COLUMN_TYPE_SHORT_STR,  Constants.FIELD_COLUMN_TYPE_BIG_STR, Constants.FIELD_COLUMN_TYPE_ORDER};
   
   private CodeGenFieldsPanel _parentPanel;
   
   //主面板
   private FormPanel _mainPanel = new FormPanel();
   
   //字段显示名称
   private GTTextField _fFieldLable;
   
   //字段名称
   private GTTextField _fFieldName;
   
   //字段类型
   private GTComboBox _fFieldType;
   
   //表字段名
   private GTTextField _fColumnName;
   
   //表字段类型
   private GTComboBox _fColumnType;
 
   //是否允许为空
   private GTCheckBox _fNotNull;
   
   //是否主键
   private GTCheckBox _fIsPK;
   
   //列表的查询字段中是否显示
   private GTCheckBox _fShow;
   
   //列表中是否显示
   private GTCheckBox _fShowInList;
   
   //报表的查询字段中是否显示
   private GTCheckBox _fShowInReport;
   
   //报表的结果列表中是否显示
   private GTCheckBox _fShowInReportList;
   
   //默认值
   private GTTextField _fDefaultValue;
   
   //是否one-to-one
   private GTCheckBox _fIsSysObj;
   
   //是否one-to-many
   private GTCheckBox _fIsSysMember;
   
   //引用的系统对象 One-to-one
   private GTTextField _fSysObj;
   
   //Member标识 one-to-many
   private GTTextField _fInvolvement;
   
   //Member标识 one-to-many
   private GTComboBox _fMemberType;
   
   public CodeGenFieldDialog(JFrame parent, boolean modal, boolean disableClose, CodeGenFieldsPanel preCompent) {
      super(parent, modal, disableClose);
      this._parentPanel = preCompent;
      initDialog();
   }

   private void initDialog() {
      
      _mainPanel.setBorder(BorderFactory.createTitledBorder(Messages.getString("field.add")));
      initFields();
      setFormComponent(_mainPanel);
      setPreferredSize(new Dimension(470, 538));
   }
    
    private void initFields() {
       int cou = 0;
       _fFieldLable = new GTTextField(Messages.getString("field.fieldLabel"), "", Messages.getString("field.fieldLabel.desc"), true); cou++;
       
       _fFieldName = new GTTextField(Messages.getString("field.fieldName"), "", Messages.getString("field.fieldName.desc"), true); cou++;
       
       _fFieldType = new GTComboBox(Messages.getString("field.fieldType"), FIELD_TYPES); cou++;
       
       _fColumnName = new GTTextField(Messages.getString("field.columnName"), "", Messages.getString("field.columnName.desc"), true); cou++;
     
       _fColumnType = new GTComboBox(Messages.getString("field.columnType"), COLUMN_TYPES); cou++;
       
       _fNotNull = new GTCheckBox(Messages.getString("field.notNull"), true); cou++;
       
       _fIsPK = new GTCheckBox(Messages.getString("field.isPK"), false); cou++;
       
       _fShow = new GTCheckBox(Messages.getString("field.show"), true); cou++;
       
       _fShowInList = new GTCheckBox(Messages.getString("field.showInList"), true); cou++;
       
       _fShowInReport = new GTCheckBox(Messages.getString("field.showInReport"), true); cou++;
       
       _fShowInReportList = new GTCheckBox(Messages.getString("field.showInReportList"), true); cou++;
       
       _fDefaultValue = new GTTextField(Messages.getString("field.defaultValue"), "", Messages.getString("field.defaultValue.desc")); cou++;
       
       _fIsSysObj = new GTCheckBox(Messages.getString("field.isSysObj"), false); cou++;
       _fIsSysObj.getValueControl().addChangeListener(new ChangeListener() {

         public void stateChanged(ChangeEvent e) {
            if (_fIsSysObj.getValue() == "true") {
               _fSysObj.setVisible(true);
               
               _fIsSysMember.setValue("false");
               _fFieldType.setSelectedIndex(-1);
               _fColumnType.setSelectedItem(Constants.FIELD_COLUMN_TYPE_ID);
               
               _fFieldType.getValueControl().setEnabled(false);
               _fColumnType.getValueControl().setEnabled(false);
            } else {
               _fSysObj.setVisible(false);
               _fFieldType.getValueControl().setEnabled(true);
               _fColumnType.getValueControl().setEnabled(true);
            }
         }
          
       });
       _fSysObj = new GTTextField(Messages.getString("field.sysObj"), "", Messages.getString("field.sysObj.desc")); cou++;
       _fSysObj.setVisible(false);
       
       _fIsSysMember = new GTCheckBox(Messages.getString("field.isSysMember"), false); cou++;
       _fIsSysMember.getValueControl().addChangeListener(new ChangeListener() {

          public void stateChanged(ChangeEvent e) {
             if (_fIsSysMember.getValue() == "true") {
                _fInvolvement.setVisible(true);
                _fMemberType.setVisible(true);
               
                _fIsSysObj.setValue("false");
                _fFieldType.setSelectedItem(Constants.FIELD_TYPE_SET);
                _fColumnType.setSelectedIndex(-1);
                _fColumnName.setValue("");
                
                _fFieldType.getValueControl().setEnabled(false);
                _fColumnType.getValueControl().setEnabled(false);
                _fColumnName.getValueControl().setEnabled(false);
             } else {
                _fInvolvement.setVisible(false);
                _fMemberType.setVisible(false);
                
                _fFieldType.getValueControl().setEnabled(true);
                _fColumnType.getValueControl().setEnabled(true);
                _fColumnName.getValueControl().setEnabled(true);
             }
          }
           
        });
       
       _fInvolvement = new GTTextField(Messages.getString("field.involvement"), "", Messages.getString("field.involvement")); cou++;
       _fInvolvement.setVisible(false);
       _fMemberType = new GTComboBox(Messages.getString("field.memberType"), MEMBER_TYPES); cou++;
       _fMemberType.setVisible(false);
       
       this._mainPanel.add(_fFieldLable);
       this._mainPanel.add(_fFieldName);
       this._mainPanel.add(_fFieldType);
       this._mainPanel.add(_fColumnName);
       
       this._mainPanel.add(_fColumnType);
       this._mainPanel.add(_fNotNull);
       this._mainPanel.add(_fIsPK);
       this._mainPanel.add(_fShow);
       this._mainPanel.add(_fShowInList);
       this._mainPanel.add(_fShowInReport);
       this._mainPanel.add(_fShowInReportList);
       this._mainPanel.add(_fDefaultValue);
       this._mainPanel.add(_fIsSysObj);
       this._mainPanel.add(_fSysObj);
       this._mainPanel.add(_fIsSysMember);
       this._mainPanel.add(_fInvolvement);
       this._mainPanel.add(_fMemberType);
       
       SpringUtilities.makeCompactGrid(_mainPanel, cou,  2, GAP, GAP, GAP, GAP/2);
    }
    
    
   @Override
   protected boolean onSubmit() {
      
      if (!valid()) {
         return false;
      }
      setReturnValue(this);
      return true;
   }
   
   protected boolean valid() {
      boolean flag = !ValidHelper.checkNotNull(this._fFieldLable, Messages.getString("field.fieldLabel"), _mainPanel);
      flag = !ValidHelper.checkNotNull(this._fFieldName, Messages.getString("field.fieldName"), _mainPanel) && flag;
      if (!StringUtil.toBoolean(this._fIsSysObj.getValue(), false)) {
         flag = !ValidHelper.checkNotNull(this._fFieldType, Messages.getString("field.fieldType"), _mainPanel) && flag;
      } else {
         ValidHelper.resetFieldBackGround(this._fFieldType);
         flag = !ValidHelper.checkNotNull(this._fSysObj, Messages.getString("field.sysObj"), _mainPanel) && flag;
      }
      if (!StringUtil.toBoolean(this._fIsSysMember.getValue(), false)) {
         flag = !ValidHelper.checkNotNull(this._fColumnName, Messages.getString("field.columnName"), _mainPanel) && flag;
         flag = !ValidHelper.checkNotNull(this._fColumnType, Messages.getString("field.columnType"), _mainPanel) && flag;
      } else {
         ValidHelper.resetFieldBackGround(this._fColumnName);
         ValidHelper.resetFieldBackGround(this._fColumnType);
         flag = !ValidHelper.checkNotNull(this._fInvolvement, Messages.getString("field.involvement"), _mainPanel) && flag;
      }
      return flag;
   }

   @Override
   protected void afterSubmit() {
      Vector<String> ve = new Vector<String>();
      ve.add(this._fFieldLable.getValue());
      ve.add(this._fFieldName.getValue());
      ve.add(this._fFieldType.getValue());
      ve.add(this._fColumnName.getValue());
      ve.add(this._fColumnType.getValue());
      ve.add(this._fIsPK.getValue());
      ve.add(this._fIsSysObj.getValue());
      ve.add(this._fSysObj.getValue());
      ve.add(this._fIsSysMember.getValue());
      ve.add(this._fMemberType.getValue());
      ve.add(this._fInvolvement.getValue());
      ve.add(this._fNotNull.getValue());
      ve.add(this._fShow.getValue());
      ve.add(this._fShowInList.getValue());
      ve.add(this._fShowInReport.getValue());
      ve.add(this._fShowInReportList.getValue());
      ve.add(this._fDefaultValue.getValue());
      this.getParentPanel().dynAddRow(ve);
   }

   public FormPanel getMainPanel() {
      return _mainPanel;
   }

   public CodeGenFieldsPanel getParentPanel() {
      return _parentPanel;
   }

   public void setParentPanel(CodeGenFieldsPanel parentPanel) {
      _parentPanel = parentPanel;
   }

   public GTTextField getFFieldLable() {
      return _fFieldLable;
   }

   public void setFFieldLable(GTTextField fieldLable) {
      _fFieldLable = fieldLable;
   }

   public GTTextField getFFieldName() {
      return _fFieldName;
   }

   public void setFFieldName(GTTextField fieldName) {
      _fFieldName = fieldName;
   }

   public GTComboBox getFFieldType() {
      return _fFieldType;
   }

   public void setFFieldType(GTComboBox fieldType) {
      _fFieldType = fieldType;
   }

   public GTTextField getFColumnName() {
      return _fColumnName;
   }

   public void setFColumnName(GTTextField columnName) {
      _fColumnName = columnName;
   }

   public GTComboBox getFColumnType() {
      return _fColumnType;
   }

   public void setFColumnType(GTComboBox columnType) {
      _fColumnType = columnType;
   }

   public GTCheckBox getFNotNull() {
      return _fNotNull;
   }

   public void setFNotNull(GTCheckBox notNull) {
      _fNotNull = notNull;
   }

   public GTCheckBox getFIsPK() {
      return _fIsPK;
   }

   public void setFIsPK(GTCheckBox isPK) {
      _fIsPK = isPK;
   }

   public GTCheckBox getFShow() {
      return _fShow;
   }

   public void setFShow(GTCheckBox show) {
      _fShow = show;
   }

   public GTCheckBox getFShowInList() {
      return _fShowInList;
   }

   public void setFShowInList(GTCheckBox showInList) {
      _fShowInList = showInList;
   }

   public GTCheckBox getFShowInReport() {
      return _fShowInReport;
   }

   public void setFShowInReport(GTCheckBox showInReport) {
      _fShowInReport = showInReport;
   }

   public GTCheckBox getFShowInReportList() {
      return _fShowInReportList;
   }

   public void setFShowInReportList(GTCheckBox showInReportList) {
      _fShowInReportList = showInReportList;
   }

   public GTTextField getFDefaultValue() {
      return _fDefaultValue;
   }

   public void setFDefaultValue(GTTextField defaultValue) {
      _fDefaultValue = defaultValue;
   }

   public GTCheckBox getFIsSysObj() {
      return _fIsSysObj;
   }

   public void setFIsSysObj(GTCheckBox isSysObj) {
      _fIsSysObj = isSysObj;
   }

   public GTCheckBox getFIsSysMember() {
      return _fIsSysMember;
   }

   public void setFIsSysMember(GTCheckBox isSysMember) {
      _fIsSysMember = isSysMember;
   }

   public GTTextField getFSysObj() {
      return _fSysObj;
   }

   public void setFSysObj(GTTextField sysObj) {
      _fSysObj = sysObj;
   }

   public GTTextField getFInvolvement() {
      return _fInvolvement;
   }

   public void setFInvolvement(GTTextField involvement) {
      _fInvolvement = involvement;
   }

   public GTComboBox getFMemberType() {
      return _fMemberType;
   }

   public void setFMemberType(GTComboBox memberType) {
      _fMemberType = memberType;
   }

   public void setMainPanel(FormPanel mainPanel) {
      _mainPanel = mainPanel;
   }

}
