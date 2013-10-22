package codegen.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import codegen.i18n.Messages;
import codegen.main.business.CodeGenHelper;

/**
 * 
 * @author zhajingzha
 *
 */
public class CodeGenFieldsPanel extends JPanel {
   
   public final static String OPER_DEL = "Del";
   
   private JFrame _frame;
   
   private Vector cellsVector = new Vector();
   
   private Vector columnNameVector = new Vector();
   
   private int[] columnWidths;
   
   private JTable _table;
   
   private JButton delBtn;
   
   private JButton addBtn;
   
   public  String[] toptitless = null;

   private void init() {
      
      toptitless = new String[] {
         Messages.getString("field.fieldLabel.desc"),
         Messages.getString("field.fieldName.desc"), 
         Messages.getString("field.fieldType.desc"),
         Messages.getString("field.columnName.desc"), 
         Messages.getString("field.columnType.desc"),
         Messages.getString("field.isPK.desc"), 
         Messages.getString("field.isSysObj.desc"), 
         Messages.getString("field.sysObj.desc"),
         Messages.getString("field.isSysMember.desc"), 
         Messages.getString("field.memberType.desc"),
         Messages.getString("field.involvement.desc"), 
         Messages.getString("field.notNull.desc"), 
         Messages.getString("field.show.desc"), 
         Messages.getString("field.showInList.desc"), 
         Messages.getString("field.showInReport.desc"), 
         Messages.getString("field.showInReportList.desc"), 
         Messages.getString("field.defaultValue.desc")
      };
            
      columnNameVector.add(Messages.getString("field.fieldLabel"));
      columnNameVector.add(Messages.getString("field.fieldName"));
      columnNameVector.add(Messages.getString("field.fieldType"));
      columnNameVector.add(Messages.getString("field.columnName"));
      columnNameVector.add(Messages.getString("field.columnType"));
      columnNameVector.add(Messages.getString("field.isPK"));
      columnNameVector.add(Messages.getString("field.isSysObj"));
      columnNameVector.add(Messages.getString("field.sysObj.desc"));
      columnNameVector.add(Messages.getString("field.isSysMember"));
      columnNameVector.add(Messages.getString("field.memberType.desc"));
      columnNameVector.add(Messages.getString("field.involvement.desc"));
      columnNameVector.add(Messages.getString("field.notNull.desc"));
      columnNameVector.add(Messages.getString("field.show.desc"));
      columnNameVector.add(Messages.getString("field.showInList.desc"));
      columnNameVector.add(Messages.getString("field.showInReport.desc"));
      columnNameVector.add(Messages.getString("field.showInReportList.desc"));
      columnNameVector.add(Messages.getString("field.defaultValue.desc"));
      
      columnWidths = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 40};
      
   }
   public CodeGenFieldsPanel(JFrame frame) {
      this._frame = frame;
      
      // 初始化
      init();
     
      // 创建table
      _table = new JTable(cellsVector, columnNameVector) {
         @Override
         protected JTableHeader createDefaultTableHeader() {
            return new JTableHeader(columnModel) {
                public String getToolTipText(MouseEvent e) {
                    String tip = null;
                    Point p = e.getPoint();
                    int index = columnModel.getColumnIndexAtX(p.x);
                    int realIndex = 
                            columnModel.getColumn(index).getModelIndex();
                    return toptitless[realIndex];
                }
            };
        }
      };
      
      _table.setSurrendersFocusOnKeystroke(false);
      _table.setFillsViewportHeight(true);
      
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      
      genTitle();
      
      genMenu();
      
      add(getContent());
      
   }

   /**
    * 生成title
    * @return
    */
   private void genTitle() {
      
      //set border
      setBorder(BorderFactory.createTitledBorder(Messages.getString("field.info")));
      
   }
   
   //生成menu
   private void genMenu() {
      JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      
      delBtn = new JButton(Messages.getString("btn.del"), new ImageIcon(CodeGenHelper.getAbsolutePath() + "\\icons\\delete.gif"));
      delBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
      delBtn.addActionListener(new DelActionAdapter(this));
      delBtn.setHorizontalAlignment(SwingConstants.CENTER);
      delBtn.setEnabled(false);
      delBtn.setDisabledIcon(new ImageIcon(CodeGenHelper.getAbsolutePath() + "\\icons\\noDelete.gif"));
      panel.add(delBtn);
      
      addBtn = new JButton(Messages.getString("btn.add"), new ImageIcon(CodeGenHelper.getAbsolutePath() + "\\icons\\add.gif"));
      addBtn.addActionListener(new AddActionAdapter(this));
      addBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
      addBtn.setHorizontalAlignment(SwingConstants.CENTER);
      
      panel.add(addBtn);
      
      add(panel);
   }
   
   //add table
   private JScrollPane getContent() {
      
      JScrollPane scrollPane = new JScrollPane(_table);
      //paintRow(); //将奇偶行分别设置为不同颜色
      
      //通过点击表头来排序列中数据resort data by clicking table header
      RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(_table.getModel());
      _table.setRowSorter(sorter);
      
      _table.setIntercellSpacing(new Dimension(5,5)); //设置数据与单元格边框的眉边距
      
      alignTableColumns(_table);
      
      fitTableColumns(_table);
      
      //选中监听事件
      _table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
      _table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

         public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
               delBtn.setEnabled(true);   
            }
         }
      });
      
      //Fiddle with the Sport column's cell editors/renderers. 
      //setUpSportColumn(_table, _table.getColumnModel().getColumn(2)); 
      
      return scrollPane;
   }
   
   public void btn_add_actionPerformed(ActionEvent e) {
      
      CodeGenFieldDialog fieldDialog = new CodeGenFieldDialog(getFrame(), true, true, this);
      
      fieldDialog.openDialog();
   }
   
   class AddActionAdapter implements ActionListener {
      
      private CodeGenFieldsPanel adaptee;

      public AddActionAdapter(CodeGenFieldsPanel adaptee) {
         this.adaptee = adaptee;
      }

      public void actionPerformed(ActionEvent e) {
         adaptee.btn_add_actionPerformed(e);
      }
   }
   
   public void btn_del_actionPerformed(ActionEvent e) {
      
      int row = _table.getSelectedRow(); 
      if (row != -1) {
         cellsVector.remove(row);
         _table.getSelectionModel().clearSelection();
         _table.updateUI();
         delBtn.setEnabled(false);
      }
   }
   
   class DelActionAdapter implements ActionListener {
      
      private CodeGenFieldsPanel adaptee;

      public DelActionAdapter(CodeGenFieldsPanel adaptee) {
         this.adaptee = adaptee;
      }

      public void actionPerformed(ActionEvent e) {
         adaptee.btn_del_actionPerformed(e);
      }
   }
   
   //动态添加行
   public void initColoumnData(Vector<Vector> ves ) {
      
      cellsVector.clear();
      cellsVector.addAll(ves);
      _table.updateUI();
   }
   
   //动态添加行
   public void dynAddRow(Vector v ) {
      
      cellsVector.add(v);
      _table.updateUI();
   }
   
   /**
    * 验证
    * @return
    */
   public boolean valid() {
    
      
      return true;
   }
   
   //设置单元内数据内容显示方式：居中
   private void alignTableColumns(JTable table) {
      Enumeration columns = table.getColumnModel().getColumns();  
      while (columns.hasMoreElements()) {  
          TableColumn column = (TableColumn) columns.nextElement();  
          DefaultTableCellRenderer render = new DefaultTableCellRenderer();
          render.setHorizontalAlignment(SwingConstants.CENTER);
          column.setCellRenderer(render);
      }  
   }
   
   //根据单元内的数据内容自动调整列宽resize column width accordng to content of cell automatically
   private void fitTableColumns(JTable table) {
      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      table.setRowHeight(20);
      int minPreferedWidth = 120;
      JTableHeader header = table.getTableHeader();  
      int rowCount = table.getRowCount();  
      Enumeration columns = table.getColumnModel().getColumns();  
      while (columns.hasMoreElements()) {  
          TableColumn column = (TableColumn) columns.nextElement();  
          int col = header.getColumnModel().getColumnIndex(column.getIdentifier());  
          int width = (int) header.getDefaultRenderer()  
                  .getTableCellRendererComponent(table,column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
          
          for (int row = 0; row < rowCount; row++) {  
              int factPpreferedWidth = (int) table.getCellRenderer(row, col)  
                      .getTableCellRendererComponent(table,  
                              table.getValueAt(row, col), false, false, row,col).getPreferredSize().getWidth();  
              width = Math.max(width, factPpreferedWidth);  
          } 
          
          header.setResizingColumn(column); // 此行很重要
          column.setMinWidth(minPreferedWidth);
          column.setWidth(width + table.getIntercellSpacing().width);
      }  
   } 
   
   //手动设置列宽  
   private static void fitTableColumns(JTable table, int[] columnWidths) {  
       for (int i = 0; i < columnWidths.length; i++) {  
           table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);  
       }  
   }  
   
   /**
    * 根据color数组中相应字符串所表示的颜色来设置某行的颜色，注意，JTable中可以对列进行整体操作
    * 而无法对行进行整体操作，故设置行颜色实际上是逐列地设置该行所有单元格的颜色。
    */
   private void paintRow() {
       TableColumnModel tcm = this._table.getColumnModel();
       for (int i = 0, n = tcm.getColumnCount(); i < n; i++) {
           TableColumn tc = tcm.getColumn(i);
           tc.setCellRenderer(new RowRenderer());
       }
   }
   
   /**
    * 定义内部类，用于控制单元格颜色，每两行颜色相间，本类中定义为蓝色和绿色。
    *
    *
    */
   private class RowRenderer extends DefaultTableCellRenderer {
       public Component getTableCellRendererComponent(JTable t, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
           //设置奇偶行的背景色，可在此根据需要进行修改
           if (row % 2 == 0)
               setBackground(Color.PINK);
           else
               setBackground(Color.LIGHT_GRAY);
  
           return super.getTableCellRendererComponent(t, value, isSelected,
                   hasFocus, row, column);
       }
   }

   
   public JTable getTable() {
      return _table;
   }

   public void setTable(JTable table) {
      _table = table;
   }
   public Vector getCellsVector() {
      return cellsVector;
   }
   public void setCellsVector(Vector cellsVector) {
      this.cellsVector = cellsVector;
   }
   public Vector getColumnNameVector() {
      return columnNameVector;
   }
   public void setColumnNameVector(Vector columnNameVector) {
      this.columnNameVector = columnNameVector;
   }
   public String[] getToptitless() {
      return toptitless;
   }
   public void setToptitless(String[] toptitless) {
      this.toptitless = toptitless;
   }
   public JFrame getFrame() {
      return _frame;
   }
   public void setFrame(JFrame frame) {
      _frame = frame;
   }
    
  
}

