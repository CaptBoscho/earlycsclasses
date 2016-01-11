package searchgui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ResultsPanel extends JPanel implements ActionListener{

	//private JTextArea ta = new JTextArea(10,30);
	private JLabel results = new JLabel("Results:");
	private JComboBox list = new JComboBox();
	JButton view = new JButton("View");
	ImageIcon image;
	
	
	public ResultsPanel()
	{
		setLayout(new BorderLayout());
		add(results, BorderLayout.NORTH);
		add(list, BorderLayout.WEST);
		view.addActionListener(this);
		add(view, BorderLayout.EAST);
	}
	
	public ResultsPanel(List<String> s)
	{
		setLayout(new BorderLayout());
		add(results, BorderLayout.NORTH);
		String[] full = new String[s.size()];
		for(int i=0; i<s.size(); i++)
		{
			full[i] = s.get(i);
		}
		JComboBox party = new JComboBox(full);
		
		add(party, BorderLayout.SOUTH);
	}
	
	public void addResults(List<String> s)
	{
		//String[] full = new String[s.size()];
		list.removeAllItems();
		for(int i=0; i<s.size(); i++)
		{
			//System.out.println(s.get(i));
			//full[i] = s.get(i);
			list.addItem(s.get(i));
		}
		
		//list.addItem(full);
		//add(list, BorderLayout.WEST);
		//view.addActionListener(this);
		/*
		view.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==view)
				{
				String current = (String) list.getSelectedItem();
				System.out.println(current);
				try {
					BufferedImage bi = ImageIO.read(new URL( current));
					JOptionPane.showMessageDialog(null, null, current, JOptionPane.PLAIN_MESSAGE, new ImageIcon(bi));
					
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				
			}
			
		});
		*/
		//add(view, BorderLayout.EAST);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==view)
		{
		String current = (String) list.getSelectedItem();
		System.out.println(current);
		try {
			BufferedImage bi = ImageIO.read(new URL( current));
			JOptionPane.showMessageDialog(null, null, current, JOptionPane.PLAIN_MESSAGE, new ImageIcon(bi));
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
	}
}
