package com.gemicle.chat.jframes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import com.gemicle.chat.message.in.MessageInput;
import com.gemicle.chat.message.out.MessageFile;
import com.gemicle.chat.message.out.MessageOutput;
import com.gemicle.chat.message.out.MessageSender;
import com.gemicle.chat.message.out.MessageSimple;
import com.gemicle.chat.service.FileService;

import lombok.Data;
import javax.swing.JLabel;

@Data
public class MainFrame extends JFrame {

	private JTextArea textMessageUsers;
	private JTextArea textMessageUser;
	private JButton btnSendMessage;
	private JButton btnOpenFile;
	private JLabel lblPathFile;
	
	private FileService fileService = new FileService();

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		setResizable(false);
		setSize(800, 500);
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 784, SpringLayout.WEST, getContentPane());
		getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);

		textMessageUsers = new JTextArea();
		sl_panel.putConstraint(SpringLayout.NORTH, textMessageUsers, 0, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, textMessageUsers, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, textMessageUsers, 247, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, textMessageUsers, 774, SpringLayout.WEST, panel);
		panel.add(textMessageUsers);

		JPanel panel_1 = new JPanel();
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -6, SpringLayout.NORTH, panel_1);
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 273, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.EAST, panel);
		getContentPane().add(panel_1);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);

		textMessageUser = new JTextArea();
		sl_panel_1.putConstraint(SpringLayout.NORTH, textMessageUser, 0, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, textMessageUser, 0, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, textMessageUser, 123, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, textMessageUser, 764, SpringLayout.WEST, panel_1);
		panel_1.add(textMessageUser);

		JPanel panel_2 = new JPanel();
		springLayout.putConstraint(SpringLayout.SOUTH, panel_1, -12, SpringLayout.NORTH, panel_2);
		springLayout.putConstraint(SpringLayout.WEST, panel_2, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_2, -10, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel_2, -57, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel_2, -10, SpringLayout.SOUTH, getContentPane());
		getContentPane().add(panel_2);
		SpringLayout sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);

		btnSendMessage = new JButton("Send Message");
		btnSendMessage.setToolTipText("Click this button and your message will be sent to chat users");
		btnSendMessage.addActionListener(listener);

		sl_panel_2.putConstraint(SpringLayout.NORTH, btnSendMessage, 10, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, btnSendMessage, -164, SpringLayout.EAST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, btnSendMessage, 37, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, btnSendMessage, 0, SpringLayout.EAST, panel_2);
		panel_2.add(btnSendMessage);

		btnOpenFile = new JButton("Open file");
		btnOpenFile.addActionListener(listener);
		sl_panel_2.putConstraint(SpringLayout.NORTH, btnOpenFile, 0, SpringLayout.NORTH, btnSendMessage);
		sl_panel_2.putConstraint(SpringLayout.EAST, btnOpenFile, -6, SpringLayout.WEST, btnSendMessage);
		panel_2.add(btnOpenFile);
		
		lblPathFile = new JLabel("");
		lblPathFile.setVisible(false);

	}

	public void readInMessage() {
		MessageInput messageInput = new MessageInput(this);
		messageInput.start();
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent action) {
			MessageSender messageSender = null;
			if (action.getSource() == btnSendMessage) {
				messageSender = new MessageSimple(MainFrame.this);				
			}else if(action.getSource() == btnOpenFile){
				lblPathFile.setText(fileService.getPathFile());
				messageSender = new MessageFile(MainFrame.this);
			}
			Thread messageOutput = new MessageOutput(messageSender);
			messageOutput.start();
			try {
				messageOutput.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			cleanForm();
		}
	};
	
	private void cleanForm(){
		textMessageUser.setText("");
		lblPathFile.setText("");
	}
}
