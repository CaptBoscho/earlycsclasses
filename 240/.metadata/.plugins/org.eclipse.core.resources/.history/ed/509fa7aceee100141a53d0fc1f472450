package client;

import java.awt.Color;
import java.util.List;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import shared.communication.DownloadBatchOutput;
import shared.model.field;

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel implements TableModelListener{

	private int rows;
	private int columns;
	private List<field> fields;
	BatchState bs;
	
	public TableModel(int numofrecs, List<field> flds, BatchState batch)
	{
		bs = batch;
		rows = numofrecs;
		fields = flds;
		columns = fields.size() + 1;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rows;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns;
	}
	
	public boolean isCellEditable(int row, int column)
	{
		if (column == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public String getColumnName(int column)
	{
		String result = null;
		
		if (column >= 0 && column < getColumnCount()) {

			if(column == 0)
			{
				result = "Record Number";
			}
			else
			{
				result = fields.get(column-1).getTitle();
			}
			
		} else {
			throw new IndexOutOfBoundsException();
		}

		return result;
		
	}
	

	@Override
	public Object getValueAt(int row, int column) {
		Object result = null;

		if (row >= 0 && row < getRowCount() && column >= 0
				&& column < getColumnCount()) {
			
			if(column == 0)
			{
				result = row + 1;
			}
			if(bs.values[row][column] != null)
			{
				result = bs.values[row][column];
			}
			
			
		} else {
			throw new IndexOutOfBoundsException();
		}

		return result;
	}
	
	public void setValueAt(Object value, int row, int column) {
			
			if (row >= 0 && row < getRowCount() && column >= 0
					&& column < getColumnCount()) {
	
				
				String name = (String)value;
				//System.out.println("setvalueat: " + name);
				bs.values[row][column] = name;
				
				this.fireTableCellUpdated(row, column);
				
			} else {
				throw new IndexOutOfBoundsException();
			}		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		TableModel model = (TableModel)e.getSource();
		Object data = model.getValueAt(row, col);
		String value = (String)data;
		System.out.println("row " + row + "col " + col + " " + value);
		
		
		this.fireTableCellUpdated(row, col);
		//fireEditingStopped();
		
	}
	
	 
}
