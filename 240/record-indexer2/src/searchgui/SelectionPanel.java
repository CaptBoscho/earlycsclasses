package searchgui;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.communicator.ClientCommunicator;
import server.communicator.ClientException;
import shared.communication.SearchOutput;
import shared.communication.ValidateUserInput;
import shared.communication.ValidateUserOutput;
import shared.communication.getFieldsOutput;
import shared.communication.getProjectsOutput;
import shared.model.field;
import shared.model.project;

public class SelectionPanel extends JPanel implements ActionListener{
	
	private JLabel projects = new JLabel("Projects:");
	//private JList projectslist = new JList();
	private JLabel fields = new JLabel("Fields:");
	//private JList<String> fieldslist = new JList();
	private JLabel values = new JLabel("Search For:");
	private JTextField valuestext = new JTextField(20);
	JButton b = new JButton("SEARCH");
	ArrayList<JCheckBox> checklist = new ArrayList<JCheckBox>();
	private ResultsPanel rp = new ResultsPanel();
	ClientCommunicator cc;
	ValidateUserInput person;
	SearchBox sb;
	
	
	public SelectionPanel(getProjectsOutput plist, ClientCommunicator com, ValidateUserInput dude, SearchBox box)
	{
		
		cc = com;
		person = dude;
		sb = box;
		ArrayList<project> prolist = (ArrayList<project>) plist.getProjects();
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int line = 1;
		c.fill = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = .25;
		c.weighty = .5;
		add(projects, c);
		
		c.fill = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = .5;
		c.weighty = .5;
		add(fields, c);
		
		
		for(int i=0; i<prolist.size(); i++)
		{
			c.anchor = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.gridy = line;
			c.weightx = .5;
			c.weighty = .5;
			JLabel name = new JLabel(prolist.get(i).getTitle());
			add(name, c);
			
			try {
				getFieldsOutput fields = cc.getFields(person.getUsername(), person.getPassword(), prolist.get(i).getPersonalID());
				ArrayList<field> fieldlist = (ArrayList<field>) fields.getFields();
				
				for(int k=0; k<fieldlist.size(); k++)
				{
					//create button and label
					//increment gridy by i
					JCheckBox check = new JCheckBox(fieldlist.get(k).getTitle());
					checklist.add(check);

					c.anchor = GridBagConstraints.LINE_START;
					c.gridx = 1;
					c.gridy = line;
					c.weightx = .5;
					c.weighty = .5;
					add(check, c);
					line++;
				}
			
			
			} catch (ClientException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
					
		}
	
		line++;
		
		c.fill = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = line;
		c.weighty = .5;
		add(values, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = line;
		c.gridwidth = 2;
		c.weighty = .5;
		add(valuestext, c);
		//add action listener
		line++;
		
		c.gridx = 0;
		c.gridy = line;
		c.gridwidth = 2;
		c.weighty = .5;
		b.addActionListener(this);
		
		/*
		b.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Integer> ids = new ArrayList<Integer>();
				for(int i=0; i<checklist.size(); i++)
				{
					if(checklist.get(i).isSelected())
					{
						ids.add(i+1);
					}
				}
				String valuestemp = valuestext.getText();
				//System.out.println(valuestemp);
				String[] valuesarray = valuestemp.split(",", -1);
				//System.out.println(valuesarray.length);
				List<String> values = new ArrayList<String>();
				
				for(int k=0; k<valuesarray.length; k++)
				{
					values.add(valuesarray[k]);
				}
				/*List<Integer> testID = new ArrayList<Integer>();
				testID.add(10);
				List<String> testValue = new ArrayList<String>();
				testValue.add("FOX");
				
				
				try {
					SearchOutput so = new SearchOutput();
					so = cc.Search(person.getUsername(), person.getPassword(), ids, values);
					List<String> images = so.getURLs();
					//System.out.println("amount of images: " + images.size());
					//sb.results.
					sb.results.addResults(images);
					sb.setFrame(sb.results);l
					
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				e.getSource();
				e.getActionCommand();
				//setVisible(false);
				}
			}
	
			
			
		);
		*/
		add(b, c);
		//add action listener
		//I'll get back a List<String> images, and then call the ResultsPanel
		
	}
	
	public ResultsPanel getResults()
	{
		return rp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b)
		{
		List<Integer> ids = new ArrayList<Integer>();
		for(int i=0; i<checklist.size(); i++)
		{
			if(checklist.get(i).isSelected())
			{
				ids.add(i+1);
			}
		}
		String valuestemp = valuestext.getText();
		//System.out.println(valuestemp);
		String[] valuesarray = valuestemp.split(",", -1);
		//System.out.println(valuesarray.length);
		List<String> values = new ArrayList<String>();
		
		for(int k=0; k<valuesarray.length; k++)
		{
			values.add(valuesarray[k]);
		}
		/*List<Integer> testID = new ArrayList<Integer>// TODO Auto-generated method stub();
		testID.add(10);
		List<String> testValue = new ArrayList<String>();
		testValue.add("FOX");*/
		
		
		try {
			SearchOutput so = new SearchOutput();
			so = cc.Search(person.getUsername(), person.getPassword(), ids, values);
			List<String> images = so.getURLs();
			//System.out.println("amount of images: " + images.size());
			//sb.results.
			sb.results.addResults(images);
			//sb.setFrame(sb.results);
			
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		e.getSource();
		e.getActionCommand();
		//setVisible(false);
		}
	}
		
}
	

	/*
	//for testing purposes:
	String[] test = {"All", "test1", "test2"};
	projectslist = new JList(test);
	fieldslist = new JList(test);
	//end the testing
	
	MouseListener fieldListener = new MouseAdapter(){
		private String field = null;
		public void mouseClicked(MouseEvent e)
		{
			if(e.getClickCount() == 2)
			{
				String f = (String) fieldslist.getSelectedValue();
				
				if(f.equals("All"))
				{
					System.out.println("working");
					//figure this out
				}
				
				
				
			}
		}
		
		
	};
	fieldslist.addMouseListener(fieldListener);*/


