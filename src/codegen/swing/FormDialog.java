package codegen.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import codegen.i18n.Messages;
import codegen.swing.widget.AbstractDialog;
import codegen.util.StringUtil;

/**
 * 
 * @author zhajingzha
 *
 */
public class FormDialog extends AbstractDialog {

   private JComponent _titleComponent;
   
   private JComponent _formComponent;
   
   private JComponent _buttomComponent;
   
   private String _saveActionLable;
   
   public FormDialog(JFrame parent, boolean modal, boolean disableClose) {
      super(parent, modal, disableClose);

      JPanel panel = new JPanel(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      setContentPane(panel);
   }
   
   /**
    * This method will be called just before the form going to submit.
    */
   protected boolean onSubmit() {
      return true;
   }

   /**
    * This method will be called just after the form going to submit.
    */
   protected void afterSubmit() {

   }
   
   @Override
   public int openDialog() {
      if (_titleComponent != null) {
         getContentPane().add(_titleComponent, BorderLayout.NORTH);
      }

      if (_formComponent != null) {
         getContentPane().add(_formComponent, BorderLayout.CENTER);
      }

      if (_buttomComponent == null) {
         
         Action _saveAction = new AbstractAction(StringUtil.isEmpty(_saveActionLable) ? Messages.getString("btn.save") : _saveActionLable) {
            public void actionPerformed(ActionEvent e) {
               if (onSubmit()) {
                  _exitCode = JOptionPane.OK_OPTION;
                  closeDialog();
                  afterSubmit();
               }
            }
         };
         
         JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

         JButton btnOK = new JButton(_saveAction);
         btnOK.setPreferredSize(new Dimension(70, 25));
         getRootPane().setDefaultButton(btnOK);
         panel.add(btnOK);

         JButton btnCancel = new JButton(Messages.getString("btn.cancel"));
         btnCancel.setPreferredSize(new Dimension(70, 25));
         btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               _exitCode = JOptionPane.ABORT;
               closeDialog();
            }
         });
         panel.add(btnCancel);
         
         _buttomComponent = panel;
      }
      getContentPane().add(_buttomComponent, BorderLayout.SOUTH);

      return super.openDialog();
   }

   public JComponent getTitleComponent() {
      return _titleComponent;
   }

   public void setTitleComponent(JComponent titleComponent) {
      _titleComponent = titleComponent;
   }

   public JComponent getFormComponent() {
      return _formComponent;
   }

   public void setFormComponent(JComponent formComponent) {
      _formComponent = formComponent;
   }

   public JComponent getButtomComponent() {
      return _buttomComponent;
   }

   public void setButtomComponent(JComponent buttomComponent) {
      _buttomComponent = buttomComponent;
   }

   public String getSaveActionLable() {
      return _saveActionLable;
   }

   public void setSaveActionLable(String saveActionLable) {
      _saveActionLable = saveActionLable;
   }
}
