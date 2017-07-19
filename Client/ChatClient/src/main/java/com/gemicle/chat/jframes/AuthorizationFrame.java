package com.gemicle.chat.jframes;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import com.gemicle.chat.message.out.MessageOutput;
import com.gemicle.chat.message.out.MessageSender;
import com.gemicle.chat.message.out.MessageUser;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.preferences.Preference;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class AuthorizationFrame extends JFrame {
	private static final int PORT = 5555;
	private static final String IP = "127.0.0.1";

	private JTextField textUserName;
	private JButton btnOK;
	private JButton btnCancel;
	private MessageSender messageSender = new MessageUser();
	private MessageOutput messageOutput = new MessageOutput(messageSender);

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnOK){
				
				Preference.user = new User(textUserName.getText());
				messageOutput.start();
			}
			if(e.getSource() == btnCancel){
				setVisible(false);
			}
		}
	};
	
	public static void main(String[] args) {
		AuthorizationFrame auth = new AuthorizationFrame();
		if(Preference.socket != null && Preference.user != null){
			auth.setVisible(false);
		} 
		
		try {
			Preference.socket = new Socket(IP, PORT);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public AuthorizationFrame() {
		super("Authorization");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JLabel lblLabelUser = new JLabel("Your name:");
		springLayout.putConstraint(SpringLayout.NORTH, lblLabelUser, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblLabelUser, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblLabelUser);

		textUserName = new JTextField();
		textUserName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		textUserName.setToolTipText("Please write your nice name");
		springLayout.putConstraint(SpringLayout.NORTH, textUserName, -3, SpringLayout.NORTH, lblLabelUser);
		springLayout.putConstraint(SpringLayout.WEST, textUserName, 6, SpringLayout.EAST, lblLabelUser);
		springLayout.putConstraint(SpringLayout.EAST, textUserName, 195, SpringLayout.EAST, lblLabelUser);
		getContentPane().add(textUserName);
		textUserName.setColumns(10);

		btnOK = new JButton("OK");
		btnOK.addActionListener(listener);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(listener);
		
		springLayout.putConstraint(SpringLayout.NORTH, btnCancel, 9, SpringLayout.SOUTH, textUserName);
		springLayout.putConstraint(SpringLayout.EAST, btnCancel, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnCancel);

		springLayout.putConstraint(SpringLayout.SOUTH, btnOK, 0, SpringLayout.SOUTH, btnCancel);
		springLayout.putConstraint(SpringLayout.EAST, btnOK, -6, SpringLayout.WEST, btnCancel);
		getContentPane().add(btnOK);
		initialize();
	}

	private void initialize() {
		setSize(300, 120);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(Preference.socket != null && Preference.user != null){
			setVisible(false);
		} else{
			setVisible(true);
		}
	}
}
