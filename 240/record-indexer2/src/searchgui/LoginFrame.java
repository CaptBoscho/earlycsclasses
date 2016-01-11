package searchgui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextField;

import server.communicator.ClientCommunicator;
import server.communicator.ClientException;
import shared.communication.ValidateUserInput;
import shared.communication.ValidateUserOutput;
import shared.communication.getProjectsOutput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JDialog {

	JPanel p = new JPanel();
	JButton b = new JButton("login");
	JLabel hostname = new JLabel("Hostname:");
	JTextField hostreceive = new JTextField();
	JLabel port = new JLabel("Port:");
	JTextField portreceive = new JTextField(10);
	JLabel usname = new JLabel("Username:");
	JTextField usnamereceive = new JTextField(20);
	JLabel pword = new JLabel("Password:");
	JTextField pwordreceive = new JTextField(20);
	JOptionPane op = new JOptionPane();
	
			
	
	public LoginFrame(JFrame f)
	{
		
		super(f, "Login", true);
		setSize(400,200);
		setResizable(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.WEST;
		c.weightx = 0.1;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		p.add(hostname, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		p.add(hostreceive, c);
		
		c.fill = GridBagConstraints.EAST;
		c.weightx = 0.15;
		c.weighty = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		p.add(port, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = .3;
		c.weighty = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		p.add(portreceive, c);
		
		c.fill = GridBagConstraints.WEST;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 1;
		c.gridy = 1;
		p.add(usname, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 1;
		p.add(usnamereceive, c);
		
		c.fill = GridBagConstraints.WEST;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 1;
		c.gridy = 2;
		p.add(pword, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0.25;
		c.gridx = 2;
		c.gridy = 2;
		p.add(pwordreceive, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0.5;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 3;
		
		b.addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent e) {
					String hostnamevalue = hostreceive.getText();
					String portvalue = portreceive.getText();
					String username = usnamereceive.getText();
					String password = pwordreceive.getText();
					
					ClientCommunicator cc = new ClientCommunicator(hostnamevalue, portvalue);
					System.out.println(username);
					System.out.println(password);
					try {
						ValidateUserOutput person = cc.ValidateUser(username, password);
						getProjectsOutput plist = cc.getProjects(username, password);
						ValidateUserInput p = new ValidateUserInput(username, password);
						
						op.showMessageDialog(null, "Welcome " + person.getFirstname() + " " + person.getLastname() +"!");
						
						JFrame s = new SearchBox(plist, cc, p);
						
					} catch (ClientException e1) {
						
						op.showMessageDialog(null, "I'm sorry, the username or password was incorrect.");	
						e1.printStackTrace();
					}
					
					
									
					
					e.getSource();
					e.getActionCommand();
					setVisible(false);
				}
				}
		
				
				
				);
		
		
		
		
		p.add(b, c);
		
		
		add(p);
		setVisible(true);
	}
}
