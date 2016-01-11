package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import client.ISpellCorrector.src.spell.SpellCorrector;
import client.ISpellCorrector.src.spell.ISpellCorrector.NoSimilarWordFoundException;
import client.TableTab2.PopupPrintListener;
import shared.communication.DownloadBatchOutput;

public class FormTab extends JPanel{
	
	BatchState bs;
	JList list;
	FormRight fr;
	int changerow;
	int mouserow;
	int mousecolumn;
	
	JDialog dia;
	JList cb;
	JButton use;
	Map<String, Integer> knownvalues;
	
	public FormTab(BatchState batch)
	{
		bs = batch;
		knownvalues = new HashMap<String, Integer>();
		//setLayout(new GridBagLayout());
		setLayout(new BorderLayout());
	}
	
	public void initialize()
	{
		int amount = bs.recordsperimage;
		String[] recs = new String[amount];
		for(int i=0; i<amount; i++)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(Integer.toString(i+1) + "                    ");
			recs[i] = sb.toString();
		}
		
		list = new JList(recs);	
		list.addListSelectionListener(new Listen());
		
		JScrollPane scroll1 = new JScrollPane(list);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll1, BorderLayout.WEST);
		
		fr = new FormRight();
		
		Cell c = bs.getCell();
		
		//fr.update(c.record);
		
		JScrollPane scroll2 = new JScrollPane(fr);
		scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(scroll2, BorderLayout.CENTER);
		
	}
	
	/*public void update(int r)
	{
		remove(fr);
		fr = new FormRight(r);
		add(fr, BorderLayout.CENTER);
		revalidate();
		repaint();
	}*/
	
	public class FormRight extends JPanel
	{
		GridBagLayout gb = new GridBagLayout();
		JTextField[] texts;
		
		
		public FormRight()
		{
			setLayout(gb);
			texts = new JTextField[bs.fields.size()];
			
			for(int i=0; i<(bs.fields.size()); i++)
			{
				
				JLabel name = new JLabel(bs.fields.get(i).getTitle());
				GridBagConstraints cc = new GridBagConstraints();
				cc.anchor = GridBagConstraints.LINE_START;
				cc.gridx = 1;
				cc.gridy = i;
				cc.weighty = .25;
				add(name, cc);
			}
			
			int f =1;
			for(int i=0; i<bs.fields.size(); i++)
			{
				int r = bs.getSelectedCell().record;
				String s = bs.values[r][f];
				JTextField text = new JTextField(s, 20);
				
				text.addActionListener(new TextListen(r, f, text));
				text.addFocusListener(new Focuser(r, f, text));
				
				texts[i] = text;
				
				GridBagConstraints cc = new GridBagConstraints();
				cc.anchor = GridBagConstraints.LINE_START;
				cc.gridx = 2;
				cc.gridy = i;
				cc.weighty = .25;
				add(text, cc);
				f++;
			}
			revalidate();
			this.repaint();
		}
		
		public void updateFR(int r)
		{
			Cell cell = bs.getSelectedCell();
			int x = cell.field -1;
			//System.out.println(x);
			changerow = r;
			for (int i=0; i<bs.fields.size(); i++)
			{
				//texts[i].addActionListener(new TextListen(r, i, texts[i]));
				String s = bs.values[r][i+1];
				
				if(bs.known.containsKey(i+1))
				{
					Set<String> dict = bs.known.get(i+1);
					
					
					//System.out.println(dict.toString());
					if(s!= null)
					{
						s = s.toUpperCase();

						if(!dict.contains(s) && !s.isEmpty())
						{
							texts[i].setOpaque(true);
							texts[i].setBackground(Color.RED);
							knownvalues.put(s, i+1);
							//mouseAdapter.setNumbers(changerow, i+1);
							texts[i].addMouseListener(mouseAdapter);
						}
						else
						{
							texts[i].setOpaque(false);
						}
					}
					
					//System.out.println(temp);
					
					
				}
				
				
				texts[i].setText(s);
				if(i == x)
				{
					texts[i].requestFocusInWindow();
				}
			}
			repaint();
		}
		
		
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
				JTextField te = (JTextField) e.getSource();
				
				String val = te.getText();
				
				String temp = val.toUpperCase();
				int column = -1;
				if(knownvalues.containsKey(temp))
				{
					System.out.println("check");
					column = knownvalues.get(temp);
				}
				final int col = column;
				
				final Set<String> dict = bs.known.get(column);
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
										bs.values[changerow][col] = (String) cb.getSelectedValue();
										dia.setVisible(false);
										dia.dispose();
										//table.revalidate();
										//table.repaint();
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
	
	public class Listen implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			
			System.out.println(e.getLastIndex());
			fr.updateFR(e.getLastIndex());
			revalidate();
			repaint();
		}
		
		
		
	}
	
	public class TextListen implements ActionListener
	{
		int row;
		int column;
		JTextField text;
		
		TextListen(int r, int c, JTextField t)
		{
			row = r;
			column = c;
			text = t;
			
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("action. row=" + row + " col=" + column);
			//System.out.println(text.getText());
			
			bs.values[changerow][column] = text.getText();
			repaint();
		}
		
	}
	
	public class Focuser implements FocusListener
	{
		int row;
		int column;
		JTextField text;
		
		Focuser(int r, int c, JTextField t)
		{
			row = r;
			column = c;
			text = t;
			
			
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("focuser. row=" + row + " col=" + column);
			Cell c = new Cell(column, changerow);
			bs.setSelectedCell(c);
			bs.updateCell(changerow, column);
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			//System.out.println(text.getText());
			bs.values[changerow][column] = text.getText();
			repaint();
		}
		
	}
	
	
	
	
	
	

	/*GridBagConstraints c = new GridBagConstraints();
	c.anchor = GridBagConstraints.LINE_START;
	c.fill = GridBagConstraints.WEST;
	c.gridx = 0;
	c.gridy = 0;
	c.weighty = 1;
	//c.weightx = .25;
	c.gridheight = bs.fields.size();
	add(list, c);
	
	//int line = 0;
	for(int i=0; i<bs.fields.size(); i++)
	{
		JLabel name = new JLabel(bs.fields.get(i).getTitle());
		JTextField text = new JTextField(20);
		
		GridBagConstraints cc = new GridBagConstraints();
		cc.anchor = GridBagConstraints.LINE_START;
		cc.gridx = 1;
		cc.gridy = i;
		cc.weighty = .25;
		add(name, cc);
		
		cc.gridx = 2;
		cc.gridy = i;
		cc.weighty = .25;
		add(text, cc);
		
	}*/

}