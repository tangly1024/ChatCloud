/** 
* Project Name:ChatRoom 
* File Name:MainWindow.java 
* Package Name:client 
* Date:2016年6月10日下午5:08:26 
* Copyright (c) 2016,All Rights Reserved. 
* 
*/  
package client;      

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;

import java.awt.GridLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;

import javax.swing.JEditorPane;

import java.awt.Insets;

import javax.swing.JInternalFrame;

import java.awt.TextField;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JSplitPane;

//===============可试化窗口========================
public class MainWindow extends JFrame{
		
		JMenu netMenu = new JMenu("网络");
		JMenuItem connectMenuItem = new JMenuItem("连接");
		JMenuItem disconnectMenuItems = new JMenuItem("断开");
		JTextField textField = new JTextField();
		JTextArea textArea = new JTextArea();
		ChatClient client = null;
		JTextPane rightTextPanel = new JTextPane();
		JPanel bottomPanel = new JPanel();
		JLabel bottomRightLabel = new JLabel("状态栏提示");
		JPanel mainTextPanel = new JPanel();
		JTextPane textPanel = new JTextPane();
		JEditorPane editorPanel = new JEditorPane();
		JSplitPane xSplitPane = new JSplitPane();
		JSplitPane ySplitPane = new JSplitPane();
		//在构造函数里初始化窗口
		public MainWindow(ChatClient clientIn) {
			this.client= clientIn;
			
			//设置尺寸标题
			setLocation(480, 80);
			setSize(new Dimension(473, 364));
			setTitle("客户端:未连接服务器");
			
		/*************************************************************
		 * 							菜单栏
		 ************************************************************/
			/**
			 * 菜单1：网络菜单
			 */
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			menuBar.add(netMenu);
			
			/**
			 * 菜单1-按钮：连接服务端
			 */
			connectMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					writeText("正在连接服务端....");
					Thread connectThread = new Thread(new Runnable() {
						@Override
						public void run() {
							client.connect();
						}
					});
					connectThread.start();
				}
			});
			netMenu.add(connectMenuItem);
			
			/**
			 * 菜单1-按钮：断开连接
			 */
			disconnectMenuItems.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					client.disConnect();
				}
			});
			netMenu.add(disconnectMenuItems);
	
		/*************************************************************
		 * 						右侧文字面板
		 ************************************************************/
			
			getContentPane().add(bottomPanel, BorderLayout.SOUTH);
			bottomPanel.setLayout(new BorderLayout(0, 0));
			
			bottomPanel.add(bottomRightLabel, BorderLayout.EAST);
			
			
		/*************************************************************
		 * 						聊天内容主面板
		 ************************************************************/
			xSplitPane.setContinuousLayout(true);
			xSplitPane.setPreferredSize(new Dimension(300, 200));
			getContentPane().add(xSplitPane, BorderLayout.CENTER);
			xSplitPane.setRightComponent(rightTextPanel);
			
			rightTextPanel.setPreferredSize(new Dimension(60, 200));
			ySplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			xSplitPane.setLeftComponent(ySplitPane);
			
			
			textArea.setEditable(false);
			textArea.setPreferredSize(new Dimension(300, 200));
			textArea.setLineWrap(true);//激活自动换行
			textArea.setWrapStyleWord(true);//激活断行不断字
			JScrollPane scrollPane = new JScrollPane(textArea);
			
			textField.addActionListener(new ActionListener() {
				//textField 预先响应了回车键事件
				public void actionPerformed(ActionEvent e) {
					String message = textField.getText().trim();
					client.send(message);
				}
	
			});
			
			ySplitPane.setTopComponent(scrollPane);
			ySplitPane.setBottomComponent(textField);
			
			
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