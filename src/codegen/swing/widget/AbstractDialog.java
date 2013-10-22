package codegen.swing.widget;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author zhajingzha
 *
 */
public class AbstractDialog extends JDialog {

   protected int _exitCode;

   protected Object _returnValue;

   public Object getReturnValue() {
      return _returnValue;
   }

   public void setReturnValue(Object returnValue) {
      _returnValue = returnValue;
   }

   public void setExitCode(int exitCode) {
      _exitCode = exitCode;
   }

   public AbstractDialog(JFrame parent, boolean modal, boolean disableClose) {
      super(parent, modal);

      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      if (!disableClose) {
         addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               _exitCode = JOptionPane.ABORT;
               closeDialog();
            }
         });
      }
   }
   
   /**
    * Open the dialog.
    * 
    * @return
    */
   public int openDialog() {
      onShowDialog();

      pack();
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      int x = (screen.width - getWidth()) / 2;
      int y = (screen.height - getHeight()) / 2;
      setLocation(x, y);

      beforeVisiable();
      setVisible(true);
      return _exitCode;
   }

   /**
    * This method will be called just before setVisible(true).
    */
   protected void beforeVisiable() {

   }

   /**
    * Override this method if you want to do something just before the dialog to
    * be show(visible).
    */
   protected void onShowDialog() {

   }

   public void closeDialog() {
      if (isVisible()) {
         setVisible(false);
         dispose();
      }
   }
}
