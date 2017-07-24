package com.gemicle.chat.jframes;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.gemicle.chat.message.out.MessageOutput;
import com.gemicle.chat.message.out.MessageUser;
import com.gemicle.chat.pojo.User;
import com.gemicle.chat.preferences.Preference;
import com.gemicle.chat.server.SocketManager;

public class AuthorizationFrame extends JFrame {

	private static final int PORT = 5555;
	private static final String IP = "127.0.0.1";
	private JTextField textUserName;
	private JButton btnOK;
	private JButton btnCancel;
	private Thread messageOutput = new MessageOutput(new MessageUser());

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnOK) {
				Preference.user = new User(textUserName.getText());
				messageOutput.start();
			}
			setVisible(false);
		}
	};

	public AuthorizationFrame() {
		super("Authorization");
		initialize();
	}

	public static void main(String[] args) {
		SocketManager.connectToServer(IP, PORT);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame main = new MainFrame();
					main.setVisible(true);
					main.readInMessage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		if (Preference.user == null) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						AuthorizationFrame auth = new AuthorizationFrame();
						auth.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void initialize() {
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

		setSize(400, 120);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
	}
}
