package client;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.CellEditor;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import shared.communication.DownloadBatchOutput;
import shared.model.field;

public class TableTab extends JPanel{
	
	BatchState bs;
	JTable table;
	private TableModel tm;
	
	public TableTab(BatchState batch)
	{
		bs = batch;
		setLayout(new BorderLayout());
	}
	
	public void initialize(int numofrecs, List<field> fields)
	{
		/*GridBagConstraints c = new GridBagConstraints();
		int numrecs = dbo.getNumofRecords();
		int line = 1;
		JLabel l = new JLabel("Number of Records");
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		
		
		for(int i=0; i<numrecs; i++)
		{
			String number = Integer.toString(i+1);
			JLabel rec = new JLabel(number);
		}*/
		bs.values = new String[numofrecs+1][fields.size()+1];
		tm = new TableModel(numofrecs, fields, bs);
		tm.addTableModelListener(tm);
		
		table = new JTable(tm);
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(mouseAdapter);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		//table.setSurrenderFocusOnKeystroke(true);
		
		/*table.addFocusListener(new FocusListener() {
	            public void focusGained(FocusEvent e) {
	            }

	            // this function successfully provides cell editing stop
	            // on cell losts focus (but another cell doesn't gain focus)
	            public void focusLost(FocusEvent e) {
	            	
	            	
	            	System.out.println("focuslost " + data);
	            }
		});*/
		
		
		TableColumnModel columnModel = table.getColumnModel();		
		for (int i = 0; i < tm.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(150); 
		}
		
		for (int i = 0; i < tm.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			//column.setCellRenderer(new TRenderer());
			column.setCellEditor(new TableEditor());			 
		}
		
		add(table.getTableHeader(), BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
	}

	
	public class TextFieldCell extends JTextField {
	    public TextFieldCell(JTable cellTable) {
	        super();                            // calling parent constructor
	        final JTable table = cellTable;     // this one is required to get cell editor and stop editing

	        this.addFocusListener(new FocusListener() {
	            public void focusGained(FocusEvent e) {
	            }

	            // this function successfully provides cell editing stop
	            // on cell losts focus (but another cell doesn't gain focus)
	            public void focusLost(FocusEvent e) {
	            	
	            	JTextComponent cell = (JTextComponent) e.getSource();  
	            	String data = cell.getText();
	            	System.out.println("focuslost " + data);
	            	
	                CellEditor cellEditor = table.getCellEditor();
	                if (cellEditor != null)
	                    if (cellEditor.getCellEditorValue() != null)
	                        cellEditor.stopCellEditing();
	                    else
	                        cellEditor.cancelCellEditing();
	            }
	        });
	    }
	}
	
	/*private TableCellRenderer tcellRenderer = new TableCellRenderer()
	{

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			if(hasFocus)
			{
				System.out.println("row: " + row);
			}
			
			return null;
		}
		
	};
	
	private FocusEvent focus = new FocusEvent(paintingChild, flags)
	{
		
	};*/
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {

			//if (e.isPopupTrigger()) {
				
				final int row = table.rowAtPoint(e.getPoint());
				final int column = table.columnAtPoint(e.getPoint());
				System.out.println("row: " + row);
				System.out.println("column: " + column);
				
				if (row >= 0 && row < tm.getRowCount() &&
						column >= 1 && column < tm.getColumnCount()) {
										
					//***************I need to be able to set the value of something
					
					ActionListener okListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e2) {
							System.out.println("something");
							tm.setValueAt(e2, row, column);
						}					
					};
					
					//JDialog dialog = JColorChooser.createDialog(table, "Pick a Color", true, colorChooser, okListener, null);
					//dialog.setVisible(true);
				//}
			}
		}
	};
	
	class TRenderer extends JLabel implements TableCellRenderer 
	{
		private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 0);
		private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		public TRenderer()
		{
			//setOpaque(true);
			setFont(getFont().deriveFont(16.0f));
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			if(isSelected)
			{
				this.setBorder(selectedBorder);
			}
			else
			{
				this.setBorder(unselectedBorder);
			}
			
			//this.setText("what");
			
			return this;
		}
		
	}
	
	class TableEditor extends AbstractCellEditor implements TableCellEditor
	{

		private String currentValue;
		private JTextField spot;
		
		public TableEditor()
		{
			spot = new JTextField();
			spot.setEditable(true);
			spot.addActionListener(actionListener);
		}
		
		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return currentValue;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			// TODO Auto-generated method stub
			currentValue = (String)value;
			spot = new JTextField(currentValue);
			return spot;
			
		}
		
		private ActionListener actionListener = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				currentValue = spot.getText();
				fireEditingStopped();
				
			}
			
		};
		
	}

	/*@Override
	public void tableChanged(TableModelEvent e) {
		
			int row = e.getFirstRow();
			int col = e.getColumn();
			TableModel model = (TableModel)e.getSource();
			Object data = model.getValueAt(row, col);
			String value = (String)data;
			System.out.println("row " + row + "col " + col + " " + value);
			
			
			tm.fireTableCellUpdated(row, col);
		}*/
		
	}



