/** 
* Project Name:ChatRoom 
* File Name:MainWindow.java 
* Package Name:client 
* Date:2016年6月10日下午5:08:26 
* Copyright (c) 2016,All Rights Reserved. 
* 
*/  

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//===============可试化窗口========================
public class MainWindow extends JFrame{
		
		JMenu netMenu = new JMenu("网络");
		JMenuItem connectMenuItem = new JMenuItem("连接");
		JMenuItem disconnectMenuItems = new JMenuItem("断开");
		JTextField textField = new JTextField();
		JTextArea textArea = new JTextArea();
		ChatClient client = null;
		
		//在构造函数里初始化窗口
		public MainWindow(ChatClient clientIn) {
			this.client= clientIn;
			
			//设置尺寸标题
			setLocation(480, 80);
			setSize(400, 300);
			setTitle("客户端:未连接服务器");
			
			//布局管理器
			textArea.setEditable(false);
			textArea.setLineWrap(true);//激活自动换行
			textArea.setWrapStyleWord(true);//激活断行不断字
			JScrollPane scrollPane = new JScrollPane(textArea);
			getContentPane().add(scrollPane,BorderLayout.CENTER);
			
			textField.addActionListener(new ActionListener() {
				//textField 预先响应了回车键事件
				public void actionPerformed(ActionEvent e) {
					String message = textField.getText().trim();
					client.send(message);
				}
	
			});
			getContentPane().add(textField,BorderLayout.SOUTH);
			
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			menuBar.add(netMenu);
			connectMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textArea.setText(textArea.getText()+"正在连接服务端...."+"\n");
					Thread connectThread = new Thread(new Runnable() {
//						@Override
						public void run() {
							client.connect();
						}
					});
					connectThread.start();
				}
			});
			
			netMenu.add(connectMenuItem);
			disconnectMenuItems.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					client.disConnect();
				}
			});
			
			netMenu.add(disconnectMenuItems);
			
			setVisible(true);
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.out.println("客户端退出..");
					client.disConnect();
					System.exit(0);
				}
			});
		}

		/**
		 * writeText:向TextArea区域添加相应的自符串；默认写完会换行.
		 * @author tanglongyong
		 * @param message
		 */
		public void writeText(String message){
			textArea.setText(textArea.getText()+message+"\n");
		}
		
		/**
		 * cleanText:清空TextArea.
		 * @author tanglongyong
		 */
		public void cleanText(){
			textArea.setText("");
		}
		
		/**
		 * 
		 * cleanTextField:清空输入区TextField内容.
		 * @author tanglongyong
		 */
		public void cleanTextField(){
			textField.setText("");
		}
}