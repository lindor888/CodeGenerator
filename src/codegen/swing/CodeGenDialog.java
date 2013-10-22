package codegen.swing;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import codegen.i18n.Messages;
import codegen.swing.widget.FormPanel;
import codegen.swing.widget.GTCheckBox;
import codegen.swing.widget.GTTextField;
import codegen.util.SpringUtilities;

/**
 * 
 * @author zhajingzha
 *
 */
public class CodeGenDialog extends FormDialog {
   
   final static int GAP = 10;
   
   private FormPanel _mainPanel = new FormPanel();
   
   //业务对象名称
   private GTTextField _fBusinessName;
   
   //model 类路径
   private GTTextField _fModelClassPath;
   
   //action 类路径
   private GTTextField _fControlClassPath;
   
   //视图 类路径
   private GTTextField _fViewClassPath;
 
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
   
   
   public CodeGenDialog(JFrame parent, boolean modal, boolean disableClose) {
      super(parent, modal, disableClose);
      super.setSaveActionLable(Messages.getString("btn.next"));
      initDialog();
   }

   private void initDialog() {
      JPanel titlePane = new JPanel();
      BoxLayout layout = new BoxLayout(titlePane, BoxLayout.PAGE_AXIS);
      titlePane.setLayout(layout);
      JLabel label = new JLabel(Messages.getString("entity.info"));
      titlePane.add(label);
      titlePane.add(new JSeparator());
      setTitleComponent(titlePane);
      
      initEntity();
      setFormComponent(_mainPanel);
      
      setPreferredSize(new Dimension(470, 430));
   }
    
    private void initEntity() {
       int cou = 0;
       _fBusinessName = new GTTextField(Messages.getString("entity.businessName"), "", Messages.getString("entity.businessName.desc")); cou++;
       
       _fModelClassPath = new GTTextField(Messages.getString("entity.modelClassPath"), "", Messages.getString("entity.modelClassPath.desc")); cou++;
       
       _fControlClassPath = new GTTextField(Messages.getString("entity.controlClassPath"), "", Messages.getString("entity.controlClassPath.desc")); cou++;
       
       _fViewClassPath = new GTTextField(Messages.getString("entity.viewClassPath"), "", Messages.getString("entity.viewClassPath.desc")); cou++;
     
       _fClassName = new GTTextField(Messages.getString("entity.className"), "", Messages.getString("entity.className.desc")); cou++;
       
       _fTableName = new GTTextField(Messages.getString("entity.tableName"), "", Messages.getString("entity.tableName.desc")); cou++;
       
       _fIdGetter = new GTTextField(Messages.getString("entity.idGetter"), "", Messages.getString("entity.idGetter.desc")); cou++;
       
       _fIdCounter = new GTTextField(Messages.getString("entity.idCounter"), "", Messages.getString("entity.idCounter.desc")); cou++;
       
       _fBussinessNameField = new GTTextField(Messages.getString("entity.bussinessNameField"), "", Messages.getString("entity.bussinessNameField.desc")); cou++;
       
       _fViewFuncId = new GTTextField(Messages.getString("entity.viewFuncId"), "", Messages.getString("entity.viewFuncId.desc")); cou++;
       
       _fEditFuncId = new GTTextField(Messages.getString("entity.editFuncId"), "", Messages.getString("entity.editFuncId.desc")); cou++;
       
       _fUseDialog = new GTCheckBox(Messages.getString("entity.useDialog"), true); cou++;
       
       GridLayout gridLayout = new GridLayout(0, 2);
       this._mainPanel.setLayout(gridLayout);
       this._mainPanel.add(_fBusinessName);
       this._mainPanel.add(_fModelClassPath);
       this._mainPanel.add(_fControlClassPath);
       this._mainPanel.add(_fViewClassPath);
       
       this._mainPanel.add(_fClassName);
       this._mainPanel.add(_fTableName);
       this._mainPanel.add(_fIdGetter);
       this._mainPanel.add(_fIdCounter);
       this._mainPanel.add(_fBussinessNameField);
       this._mainPanel.add(_fViewFuncId);
       this._mainPanel.add(_fEditFuncId);
       this._mainPanel.add(_fUseDialog);
       
       SpringUtilities.makeCompactGrid(_mainPanel, cou,  2, GAP, GAP, GAP, GAP/2);
    }
    
    
   @Override
   protected boolean onSubmit() {
      JOptionPane.showMessageDialog(this.getParent(), this._fBusinessName.getValue());

      setReturnValue(this);
      return true;
   }

   @Override
   protected void afterSubmit() {
      JOptionPane.showMessageDialog(this.getParent(), "下一页");
      this.openDialog();
      //CodeGenFieldDialog dialog = new CodeGenFieldDialog(this.getParent(), true, true);
      //dialog.openDialog();
   }

   public FormPanel getMainPanel() {
      return _mainPanel;
   }

   public void setMainPanel(FormPanel mainPanel) {
      _mainPanel = mainPanel;
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
}
