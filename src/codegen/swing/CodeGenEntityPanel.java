package codegen.swing;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

import codegen.i18n.Messages;
import codegen.swing.widget.FormPanel;
import codegen.swing.widget.GTCheckBox;
import codegen.swing.widget.GTTextField;
import codegen.util.SpringUtilities;
import codegen.util.StringUtil;

/**
 * 
 * @author zhajingzha
 *
 */
public class CodeGenEntityPanel extends FormPanel {
   
   final static int GAP = 10;
   
   //业务对象名称
   private GTTextField _fBusinessName;
   
   //model 类路径
   private GTTextField _fModelClassPath;
   
   //action 类路径
   private GTTextField _fControlClassPath;
   
   //视图 类路径
   private GTTextField _fViewClassPath;
   
   //资源Key前缀
   private GTTextField _fResourceKeyPreStuff;
 
   //类名
   private GTTextField _fClassName;
   
   //对应表名称
   private GTTextField _fTableName;
   
   //定义IdGetter 常量
   private GTTextField _fIdGetter;
   
   //定义IdCounter 内部变量，指向IdGetter
   private GTTextField _fIdCounter;
   
   //定义业务对象的Name字段
   private GTTextField _fBussinessNameField;
   
   //定义View角色ID
   private GTTextField _fViewFuncId;
   
   //定义Edit角色Id
   private GTTextField _fEditFuncId;
   
   //是否使用弹出窗口
   private GTCheckBox _fUseDialog;
   
   //生成CURD
   private GTCheckBox _fGenCURD;
   
   //是否生成报表
   private GTCheckBox _fGenReport;
   
   //报表ID
   private GTTextField _fReportId;
   
   //报表名称
   private GTTextField _fReportName;
   
   //文件输出地址
   private GTTextField _fOutput;
   
   //作者
   private GTTextField _fAuthor;
   
   public CodeGenEntityPanel() {
      this.setLayout(new SpringLayout());
      setBorder(BorderFactory.createTitledBorder(Messages.getString("entity.info")));
      
      initEntity();
   }

    
   private void initEntity() {
       int cou = 0;
       _fBusinessName = new GTTextField(Messages.getString("entity.businessName"), "", Messages.getString("entity.businessName.desc"), true); cou++;
       
       _fModelClassPath = new GTTextField(Messages.getString("entity.modelClassPath"), "", Messages.getString("entity.modelClassPath.desc"), true); cou++;
       
       _fControlClassPath = new GTTextField(Messages.getString("entity.controlClassPath"), "", Messages.getString("entity.controlClassPath.desc"), true); cou++;
       
       _fViewClassPath = new GTTextField(Messages.getString("entity.viewClassPath"), "", Messages.getString("entity.viewClassPath.desc"), true); cou++;
       
       _fResourceKeyPreStuff = new GTTextField(Messages.getString("entity.resourceKeyPreStuff"), "", Messages.getString("entity.resourceKeyPreStuff.desc"), true); cou++;
     
       _fClassName = new GTTextField(Messages.getString("entity.className"), "", Messages.getString("entity.className.desc"), true); cou++;
       
       _fTableName = new GTTextField(Messages.getString("entity.tableName"), "", Messages.getString("entity.tableName.desc"), true); cou++;
       
       _fIdGetter = new GTTextField(Messages.getString("entity.idGetter"), "", Messages.getString("entity.idGetter.desc"), true); cou++;
       
       _fIdCounter = new GTTextField(Messages.getString("entity.idCounter"), "", Messages.getString("entity.idCounter.desc"), true); cou++;
       
       _fBussinessNameField = new GTTextField(Messages.getString("entity.bussinessNameField"), "", Messages.getString("entity.bussinessNameField.desc")); cou++;
       
       _fViewFuncId = new GTTextField(Messages.getString("entity.viewFuncId"), "", Messages.getString("entity.viewFuncId.desc")); cou++;
       
       _fEditFuncId = new GTTextField(Messages.getString("entity.editFuncId"), "", Messages.getString("entity.editFuncId.desc")); cou++;
       
       _fUseDialog = new GTCheckBox(Messages.getString("entity.useDialog"), true); cou++;
       
       _fGenCURD = new GTCheckBox(Messages.getString("entity.genCurd"), true); cou++;
       
       _fGenReport = new GTCheckBox(Messages.getString("entity.genReport"), true); cou++;
       
       _fReportId = new GTTextField(Messages.getString("entity.genReportId"), "", Messages.getString("entity.genReportId.desc"), false); cou++;
       
       _fReportName = new GTTextField(Messages.getString("entity.genReportName"), "", Messages.getString("entity.genReportName.desc"), false); cou++;
   
       _fOutput = new GTTextField(Messages.getString("entity.output"), "", Messages.getString("entity.output.desc"), true); cou++;
       
       _fAuthor = new GTTextField(Messages.getString("entity.author"), "", Messages.getString("entity.author.desc"), true); cou++;
       
       this.add(_fBusinessName);
       this.add(_fModelClassPath);
       this.add(_fControlClassPath);
       this.add(_fViewClassPath);
       this.add(_fResourceKeyPreStuff);
       
       this.add(_fClassName);
       this.add(_fTableName);
       this.add(_fIdGetter);
       this.add(_fIdCounter);
       this.add(_fBussinessNameField);
       this.add(_fViewFuncId);
       this.add(_fEditFuncId);
       this.add(_fUseDialog);
       this.add(_fGenCURD);
       this.add(_fGenReport);
       this.add(_fReportId);
       this.add(_fReportName);
       this.add(_fOutput);
       this.add(_fAuthor);
       
       SpringUtilities.makeCompactGrid(this, cou,  2, GAP, GAP, GAP, GAP/2);
   }
   
   public boolean valid() {
      boolean flag = true;
      flag = !ValidHelper.checkNotNull(this._fBusinessName, Messages.getString("entity.businessName"), this);
      flag = !ValidHelper.checkNotNull(this._fModelClassPath, Messages.getString("entity.modelClassPath"), this);
      flag = !ValidHelper.checkNotNull(this._fControlClassPath, Messages.getString("entity.controlClassPath"), this);
      flag = !ValidHelper.checkNotNull(this._fViewClassPath, Messages.getString("entity.viewClassPath"), this);
      flag = !ValidHelper.checkNotNull(this._fResourceKeyPreStuff, Messages.getString("entity.resourceKeyPreStuff"), this);
      flag = !ValidHelper.checkNotNull(this._fClassName, Messages.getString("entity.className"), this);
      flag = !ValidHelper.checkNotNull(this._fTableName, Messages.getString("entity.tableName"), this);
      flag = !ValidHelper.checkNotNull(this._fIdGetter, Messages.getString("entity.idGetter"), this);
      flag = !ValidHelper.checkNotNull(this._fIdCounter, Messages.getString("entity.idCounter"), this);
      flag = !ValidHelper.checkNotNull(this._fOutput, Messages.getString("entity.output"), this);
      flag = !ValidHelper.checkNotNull(this._fAuthor, Messages.getString("entity.author"), this);
      
      if (StringUtil.toBoolean(this._fGenReport.getValue(), false)) {
         flag = !ValidHelper.checkNotNull(this._fReportId, Messages.getString("entity.genReportId"), this);
         flag = !ValidHelper.checkNotNull(this._fReportName, Messages.getString("entity.genReportName"), this);
      } else {
         ValidHelper.resetFieldBackGround(_fReportId);
         ValidHelper.resetFieldBackGround(_fReportName);
      }
      return flag;
   }
   
   public GTTextField getFBusinessName() {
      return _fBusinessName;
   }

   public void setFBusinessName(GTTextField businessName) {
      _fBusinessName = businessName;
   }

   public GTTextField getFModelClassPath() {
      return _fModelClassPath;
   }

   public void setFModelClassPath(GTTextField modelClassPath) {
      _fModelClassPath = modelClassPath;
   }

   public GTTextField getFControlClassPath() {
      return _fControlClassPath;
   }

   public void setFControlClassPath(GTTextField controlClassPath) {
      _fControlClassPath = controlClassPath;
   }

   public GTTextField getFViewClassPath() {
      return _fViewClassPath;
   }

   public void setFViewClassPath(GTTextField viewClassPath) {
      _fViewClassPath = viewClassPath;
   }

   public GTTextField getFClassName() {
      return _fClassName;
   }

   public void setFClassName(GTTextField className) {
      _fClassName = className;
   }

   public GTTextField getFTableName() {
      return _fTableName;
   }

   public void setFTableName(GTTextField tableName) {
      _fTableName = tableName;
   }

   public GTTextField getFIdGetter() {
      return _fIdGetter;
   }

   public void setFIdGetter(GTTextField idGetter) {
      _fIdGetter = idGetter;
   }

   public GTTextField getFIdCounter() {
      return _fIdCounter;
   }

   public void setFIdCounter(GTTextField idCounter) {
      _fIdCounter = idCounter;
   }

   public GTTextField getFBussinessNameField() {
      return _fBussinessNameField;
   }

   public void setFBussinessNameField(GTTextField bussinessNameField) {
      _fBussinessNameField = bussinessNameField;
   }

   public GTTextField getFViewFuncId() {
      return _fViewFuncId;
   }

   public void setFViewFuncId(GTTextField viewFuncId) {
      _fViewFuncId = viewFuncId;
   }

   public GTTextField getFEditFuncId() {
      return _fEditFuncId;
   }

   public void setFEditFuncId(GTTextField editFuncId) {
      _fEditFuncId = editFuncId;
   }

   public GTCheckBox getFUseDialog() {
      return _fUseDialog;
   }

   public void setFUseDialog(GTCheckBox useDialog) {
      _fUseDialog = useDialog;
   }

   public GTTextField getFResourceKeyPreStuff() {
      return _fResourceKeyPreStuff;
   }

   public void setFResourceKeyPreStuff(GTTextField resourceKeyPreStuff) {
      _fResourceKeyPreStuff = resourceKeyPreStuff;
   }

   public GTCheckBox getFGenCURD() {
      return _fGenCURD;
   }

   public void setFGenCURD(GTCheckBox genCURD) {
      _fGenCURD = genCURD;
   }

   public GTCheckBox getFGenReport() {
      return _fGenReport;
   }

   public void setFGenReport(GTCheckBox genReport) {
      _fGenReport = genReport;
   }

   public GTTextField getFReportId() {
      return _fReportId;
   }

   public void setFReportId(GTTextField reportId) {
      _fReportId = reportId;
   }

   public GTTextField getFReportName() {
      return _fReportName;
   }

   public void setFReportName(GTTextField reportName) {
      _fReportName = reportName;
   }

   public GTTextField getFOutput() {
      return _fOutput;
   }

   public void setFOutput(GTTextField output) {
      _fOutput = output;
   }
}
