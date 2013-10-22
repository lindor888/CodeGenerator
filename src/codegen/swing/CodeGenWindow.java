package codegen.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import codegen.i18n.Messages;
import codegen.main.business.CodeGenException;
import codegen.main.business.CodeGenHelper;
import codegen.main.business.CodeGeneratorEngine;
import codegen.main.business.model.Entity;
import codegen.main.business.model.Field;
import codegen.main.business.model.I18N;
import codegen.main.business.model.SysMember;
import codegen.util.StringUtil;


public class CodeGenWindow extends JFrame{
   
   private JPanel             pane     = null; // 主要的JPanel，该JPanel的布局管理将被设置成CardLayout

   private JPanel             p        = null; // 放按钮的JPanel

   private CardLayout         card     = null; // CardLayout布局管理器

   private JButton            button_1 = null; // 上一步

   private JButton            button_2 = null; // 下一步
   
   private JButton            button_3 = null; // 完成

   private CodeGenEntityPanel p_1      = null;

   private CodeGenFieldsPanel  p_2      = null; // 要切换的两个JPanel  
   
   public CodeGenWindow() {
      
      super(Messages.getString("gui.title"));

      try {

         // 将LookAndFeel设置成Windows样式  
         UIManager
               .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

      } catch (Exception ex) {

         ex.printStackTrace();

      }
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      /**创建一个具有指定的水平和垂直间隙的新卡片布局*/

      card = new CardLayout(5, 5);

      pane = new JPanel(card); // JPanel的布局管理将被设置成CardLayout  

      p = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 构造放按钮的JPanel  

      button_1 = new JButton(Messages.getString("btn.up"));
      button_1.setEnabled(false);
      button_1.setVisible(false);
      
      button_2 = new JButton(Messages.getString("btn.next"));
      
      button_3 = new JButton(Messages.getString("btn.submit"));
      button_3.setEnabled(false);
      button_3.setVisible(false);
      
      p.add(button_1);
      p.add(button_2);
      p.add(button_3);
      
      p_1 =  new CodeGenEntityPanel();
      
      p_2 =  new CodeGenFieldsPanel(this);
      
      pane.add(p_1, "p1");
      pane.add(p_2, "p2");

      /**下面是翻转到卡片布局的某个组件，可参考API中的文档*/

      button_1.addActionListener(new ActionListener() { // 上一步的按钮动作  

         public void actionPerformed(ActionEvent e) {

            card.previous(pane);
            
            button_1.setEnabled(false);
            button_1.setVisible(false);
            
            button_2.setEnabled(true);
            button_2.setVisible(true);
            
            button_3.setEnabled(false);
            button_3.setVisible(false);
         }

      });

      button_2.addActionListener(new ActionListener() { // 下一步的按钮动作  

         public void actionPerformed(ActionEvent e) {
            
            if (p_1.valid()) {
               card.next(pane);
               
               button_1.setEnabled(true);
               button_1.setVisible(true);
               
               button_2.setEnabled(false);
               button_2.setVisible(false);
               
               button_3.setEnabled(true);
               button_3.setVisible(true);
            }
         }

      });
      
      button_3.addActionListener(new GenActionAdapter(this));
      
      this.getContentPane().add(pane);

      this.getContentPane().add(p, BorderLayout.SOUTH);

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      this.setSize(720, 590);

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = getSize();
      if (frameSize.height > screenSize.height) {
          frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
          frameSize.width = screenSize.width;
      }
      setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      
      //取前一次保存的对象作为备份
      reset();
      
      this.setVisible(true);
   }
   
   public void btn_gen_actionPerformed(ActionEvent e) {
      try {
         Entity entity = convertToEntity();
         entity.setFields(convertToFields(entity));
         CodeGeneratorEngine codeGen = new CodeGeneratorEngine(entity);
         
         codeGen.gen();
         CodeGenHelper.saveTempFile(entity, CodeGenHelper.getAttTempPath());
         JOptionPane.showMessageDialog(pane, Messages.getString("label.success"));
         //this.setVisible(false);
         //this.dispose();
       } catch (Exception e1) {
          JOptionPane.showMessageDialog(pane, StringUtil.nvl(e1.getMessage(),e1.getCause() != null ? e1.getCause().getMessage() : e1.getLocalizedMessage()));
       }
      
   }
   
   class GenActionAdapter implements ActionListener {
      
      private CodeGenWindow adaptee;

      public GenActionAdapter(CodeGenWindow adaptee) {
         this.adaptee = adaptee;
      }

      public void actionPerformed(ActionEvent e) {
         adaptee.btn_gen_actionPerformed(e);
      }
  }
  
   //默认显示前一次的修改
   private void reset() {
      try {
         Entity entity = CodeGenHelper.loadEntity(CodeGenHelper.getAttTempPath() + "entity.xml");
         resetEntity(entity);
         resetEntityFields(entity);
      } catch (CodeGenException e) {
         JOptionPane.showMessageDialog(this, StringUtil.nvl(e.getMessage(), e.getCause().getMessage()));
      }
   }
  
   private void resetEntity(Entity entity) {
      if (entity != null) {
         this.p_1.getFModelClassPath().setValue(entity.getPackagePath());
         this.p_1.getFControlClassPath().setValue(entity.getControllerPackagePath());
         this.p_1.getFViewClassPath().setValue(entity.getJspPath());
         this.p_1.getFResourceKeyPreStuff().setValue(entity.getResourceKeyPath());
         this.p_1.getFClassName().setValue(entity.getClassName());
         this.p_1.getFTableName().setValue(entity.getTableName());
         this.p_1.getFIdGetter().setValue(entity.getIdGetter());
         this.p_1.getFIdCounter().setValue(entity.getIdCounter());
         this.p_1.getFBussinessNameField().setValue(entity.getBussinessNameField());
         this.p_1.getFBusinessName().setValue(entity.getI18n().getZh());
         this.p_1.getFViewFuncId().setValue(StringUtil.toString(entity.getViewFuncId(), "-1"));
         this.p_1.getFEditFuncId().setValue(StringUtil.toString(entity.getEditFuncId(), "-1"));
         this.p_1.getFGenCURD().setValue("true");
         this.p_1.getFGenReport().setValue(StringUtil.isEmpty1(entity.getReportName()) ? "false" : "true");
         this.p_1.getFReportId().setValue(entity.getReportId());
         this.p_1.getFReportName().setValue(entity.getReportName());
         this.p_1.getFOutput().setValue(entity.getOutputAll());
      }
   }
   
   /**
    * 解析实体类
    * @return
    */
   private Entity convertToEntity() {
      
      Entity entity = new Entity();
      
      entity.setPackagePath(this.p_1.getFModelClassPath().getValue());
      entity.setControllerPackagePath(this.p_1.getFControlClassPath().getValue());
      entity.setJspPath(this.p_1.getFViewClassPath().getValue());
      entity.setStrutsActionPath(StringUtil.replaceAllIgnoreCase(this.p_1.getFViewClassPath().getValue(), ".", "\\"));
      entity.setResourceKeyPath(this.p_1.getFResourceKeyPreStuff().getValue());
      entity.setClassName(this.p_1.getFClassName().getValue());
      entity.setTableName(this.p_1.getFTableName().getValue());
      entity.setIdCounter(this.p_1.getFIdCounter().getValue());
      entity.setIdGetter(this.p_1.getFIdGetter().getValue());
      entity.setBussinessNameField(this.p_1.getFBussinessNameField().getValue());

      I18N ln = new I18N();
      ln.setKey(entity.getLowerFirstClassName() + ".name");
      ln.setZh(this.p_1.getFBusinessName().getValue());
      ln.setEn(ln.getZh());
      ln.setTw(ln.getZh());
      entity.setI18n(ln);
      
      entity.setViewFuncId(StringUtil.toInteger(this.p_1.getFViewFuncId().getValue(), -1));
      entity.setEditFuncId(StringUtil.toInteger(this.p_1.getFEditFuncId().getValue(), -1));
      entity.setUseDialog(StringUtil.toBoolean(this.p_1.getFUseDialog().getValue(), false));
      entity.setGenCurd(StringUtil.toBoolean(this.p_1.getFGenCURD().getValue(), true));
      entity.setGenReport(StringUtil.toBoolean(this.p_1.getFGenReport().getValue(), true));
      entity.setReportId(this.p_1.getFReportId().getValue());
      entity.setReportName(this.p_1.getFReportName().getValue());
      entity.setOutputAll(this.p_1.getFOutput().getValue());
      return entity;
   }
   
   /**
    * 解析类字段
    * @param entity
    * @return
    */
   private void resetEntityFields(Entity entity) {
      if (entity == null || entity.getFields() == null ||  entity.getFields().size() == 0) {
         return;
      }
      List<Field> fields = entity.getFields();
      Vector<Vector> cellsVector = new Vector<Vector>();
      for (Field field : fields) {
         Vector v = new Vector();
         v.add(field.getI18n().getZh());
         v.add(field.getFieldName());
         v.add(field.getFieldType());
         v.add(field.getColumnName());
         v.add(field.getColumnType());
         v.add(String.valueOf(StringUtil.toBoolean(field.getPk(), false)));
         v.add(StringUtil.isEmpty1(field.getSysObj()) ? "false" : "true");
         v.add(field.getSysObj());
         if (field.getIsSysMember()) {
            v.add("true");
            v.add(field.getSysMember().getMemberType());
            v.add(field.getSysMember().getInvolvement());
         } else {
            v.add("false");
            v.add("");
            v.add("");
         }
         v.add(String.valueOf(StringUtil.toBoolean(field.getNotnull(), false)));
         v.add(String.valueOf(StringUtil.toBoolean(field.getShow(), false)));
         v.add(String.valueOf(StringUtil.toBoolean(field.getShowInList(), false)));
         v.add(String.valueOf(StringUtil.toBoolean(field.getShowInReport(), false)));
         v.add(String.valueOf(StringUtil.toBoolean(field.getShowInSearch(), false)));
         v.add(String.valueOf(StringUtil.toBoolean(field.getShowInReportList(), false)));
         v.add(field.getDefaultValue());
         v.add(String.valueOf(StringUtil.toBoolean(field.getReadonly(), false)));
         cellsVector.add(v);
      }
      this.p_2.initColoumnData(cellsVector);
   }
   
   /**
    * 解析类字段
    * @param entity
    * @return
    */
   private List<Field> convertToFields(Entity entity) {
      List<Field> fields = new ArrayList<Field>();
      Vector<Vector<String>> cellsVector = this.p_2.getCellsVector();
      for (Vector<String> ve : cellsVector) {
         Field field = new Field();
         int index = 0;         
         I18N ln = new I18N();
         ln.setKey(ve.get(1));
         ln.setZh(ve.get(index++));
         ln.setEn(ln.getZh());
         ln.setTw(ln.getZh());
         field.setI18n(ln);
         
         field.setFieldName(ve.get(index++));
         field.setFieldType(String.valueOf(ve.get(index++)));
         field.setColumnName(ve.get(index++));
         field.setColumnType(ve.get(index++));
         field.setPk(StringUtil.toBoolean(ve.get(index++), false));
         
         //one-to-one
         boolean isSysObj = StringUtil.toBoolean(ve.get(index++), false);
         if (isSysObj) {
            field.setSysObj(ve.get(index++));
         } else {
            index++;
         }
         
         //one-to-many
         boolean isSysMembers = StringUtil.toBoolean(ve.get(index++), false);
         if (isSysMembers) {
            SysMember sm = new SysMember();
            sm.setMemberType(ve.get(index++));
            sm.setInvolvement(ve.get(index++));
            field.setSysMember(sm);
         } else {
            index++;
            index++;
         }
         
         //showOrHide
         field.setNotnull(StringUtil.toBoolean(ve.get(index++), false));
         field.setShow(StringUtil.toBoolean(ve.get(index++), false));
         field.setShowInSearch(field.getShow());
         field.setShowInList(StringUtil.toBoolean(ve.get(index++), false));
         field.setShowInReport(StringUtil.toBoolean(ve.get(index++), false));
         field.setShowInReportList(StringUtil.toBoolean(ve.get(index++), false));
         field.setDefaultValue(ve.get(index++));
         field.setReadonly(false);
         field.setEntity(entity);
         fields.add(field);
      }
      return fields;
   }
   
   public static void main(String[] args) {

      new CodeGenWindow();
      
   }
}
