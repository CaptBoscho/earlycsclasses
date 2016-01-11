package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import client.ISpellCorrector.src.spell.ISpellCorrector.NoSimilarWordFoundException;
import client.ISpellCorrector.src.spell.SpellCorrector;
import shared.model.field;

public class TableTab2 extends JPanel{

	BatchState bs;
	JTable table;
	private TableModel tm;
	Map<Integer, Set<String>> knownvalues;
	JDialog dia;
	JList cb;
	JButton use;
	
	public TableTab2(BatchState batch)
	{
		bs = batch;
		setLayout(new BorderLayout());
	}
	
	public void initialize(int numofrecs, List<field> fields)
	{
		tm = new TableModel(numofrecs, fields, bs);
		
		knownvalues = bs.known;
		table = new JTable(tm);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(mouseAdapter);
		
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < tm.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(150);
		}
		
		for (int i = 0; i < tm.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(new TRenderer(bs));
		}
		
		add(table.getTableHeader(), BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
	}
	
	public void updateSelected(int x, int y)
	{
		table.changeSelection(y, x, false, false);
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {

			if (e.isPopupTrigger()) {
				
				
			}
		}
		
		public void mousePressed(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON3)
		    {
				final int row = table.rowAtPoint(e.getPoint());
				final int column = table.columnAtPoint(e.getPoint());
				//System.out.println("row: " + row);
				//System.out.println("column: " + column);
				String val = bs.values[row][column];
				//System.out.println(val);
				
				final Set<String> dict = knownvalues.get(column);
				final String valu = val.toUpperCase();
				if(!dict.contains(valu))
				{
					//System.out.println("Eureka");
					JPopupMenu pop = new JPopupMenu();
					
					JMenuItem seesug = new JMenuItem("See Suggestions");
					
					seesug.addMouseListener(new MouseAdapter()
					{
						public void mousePressed(MouseEvent e)
						{
							SpellCorrector spell = new SpellCorrector();
							try {
								spell.useDictionary(dict);
								String[] suggestions = spell.suggestSimilarWord(valu);
								
								dia = new JDialog(bs.main, "Suggestions", Dialog.ModalityType.APPLICATION_MODAL);
								dia.setBounds(500, 500, 250, 250);
								dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);	
								dia.setResizable(false);
								dia.setModal(true);
								
								JPanel panel = new JPanel();
								panel.setLayout(new BorderLayout());
								
								use = new JButton("Use Suggestion");
								use.setEnabled(false);
								use.addActionListener(new ActionListener()
								{

									@Override
									public void actionPerformed(ActionEvent e) {
										
										//System.out.println(cb.getSelectedValue());
										bs.values[row][column] = (String) cb.getSelectedValue();
										dia.setVisible(false);
										dia.dispose();
										table.revalidate();
										table.repaint();
									}
									
								});
								
								cb = new JList(suggestions);
								cb.setFixedCellWidth(150);
								cb.addListSelectionListener(new ListSelectionListener()
								{

									@Override
									public void valueChanged(
											ListSelectionEvent e) {
										use.setEnabled(true);
										
									}
									
								});
								
								JScrollPane scroll = new JScrollPane(cb);
								scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
								
								panel.add(scroll, BorderLayout.CENTER);
								
								JPanel panel2 = new JPanel();
								
								JButton cancel = new JButton("Cancel");
								cancel.addActionListener(new ActionListener()
								{

									@Override
									public void actionPerformed(ActionEvent e) {
										dia.setVisible(false);
										
									}
									
								});
								
								cancel.setSize(100, 50);
								panel2.add(cancel);
								
								
								
								panel2.add(use);
								
								panel.add(panel2, BorderLayout.SOUTH);
								
								dia.add(panel);
								dia.setVisible(true);
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (NoSimilarWordFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
					});
					
					
					
					
					pop.add(seesug);
					
					pop.addPopupMenuListener(new PopupPrintListener());
					//pop.setBorder(new BevelBorder(BevelBorder.RAISED));
					maybeShowPopup(e, pop);
					
				}
		    }
		}
		
		private void maybeShowPopup(MouseEvent e, JPopupMenu popup) {
	        if (e.isPopupTrigger()) {
	            popup.show(e.getComponent(),
	                       e.getX(), e.getY());
	        }
	    }
	};
	
	class TRenderer extends JLabel implements TableCellRenderer 
	{
		private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 0);
		private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		BatchState bs;
		
		public TRenderer(BatchState batch)
		{
			bs = batch;
			setOpaque(true);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			
			
			if(isSelected)
			{
				this.setBorder(selectedBorder);
				this.setOpaque(true);
				this.setBackground(Color.YELLOW);
			}
			else
			{
				this.setBorder(unselectedBorder);
				this.setOpaque(false);
			}
			
			
			
			if(value == null)
			{
				this.setText("");
				this.setOpaque(false);
			}
			else
			{
				
				if(knownvalues.containsKey(column))
				{
					Set<String> dict = knownvalues.get(column);
					String temp = value.toString();
					
					Iterator iter = dict.iterator();
					
					
					//System.out.println(dict.toString());
					temp = temp.toUpperCase();
					//System.out.println(temp);
					
					if(!dict.contains(temp) && !temp.isEmpty())
					{
						this.setOpaque(true);
						this.setBackground(Color.RED);
					}
					else
					{
						this.setOpaque(false);
					}
				}
				
				this.setText(value.toString());
			}
			
			if(hasFocus)
			{
				if(column > 0)
				{
					bs.updateCell(row, column);
					this.setOpaque(true);
					this.setBackground(Color.YELLOW);
				}
				if(value != null)
				{
					
				}
			}
			
			return this;
		}
		
	}
	
	private FocusListener focus = new FocusListener()
	{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	class PopupPrintListener implements PopupMenuListener {
		  
		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			
			
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		
			
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
			
			
		}
	  }
}
