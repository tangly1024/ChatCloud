import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class ChatClient extends JFrame{

	JTextField textField = new JTextField();
	JTextArea textArea = new JTextArea();
	Socket s = null;
	DataOutputStream dos = null;
	String serverURL = "127.0.0.1";
	int port = 123;
	
	//连接服务端
	private void connect(){
		textArea.setText(textArea.getText()+"正在连接服务端...."+"\n");
		try {
			//连接的同时保存下输出流的引用
			s = new Socket(serverURL, port);
			if(s.isConnected()){
				textArea.setText(textArea.getText()+"服务器连接成功...."+"\n");
				setTitle("客户端:已连接");
			}
			dos = new DataOutputStream(s.getOutputStream());
		}catch(ConnectException e1){
			textArea.setText(textArea.getText()+"在"+serverURL+":"+ port + " 上没有找到服务器...."+"\n");
		}catch (SocketException e) {
			textArea.setText(textArea.getText()+"已从服务器断开连接"+"\n");
		}
		catch (IOException e2) {
			// TODO Auto-generated catch block
			textArea.setText(textArea.getText()+"连接错误...."+"\n"+e2.toString());
		}
	}
	
	//断开连接
	private void disConnect(){
		if(s!=null && dos !=null){
			try {
				dos.close();
				s.close();
				textArea.setText(textArea.getText()+"已断开与服务器的连接\n");
				setTitle("客户端:连接已断开");
			} catch (IOException e) {
				textArea.setText(textArea.getText()+e.toString()+"并没有连接\n");
			}
		}else {
			textArea.setText(textArea.getText()+"并没有连接\n");
		}
		
	}
	
	//输入框响应发送
	private void send(){
		String string = textField.getText().trim();
		textArea.setText(textArea.getText()+string+"\n");
		textField.setText("");
		//一个输出流
		try {
			if(s!=null && !s.isClosed()){
			dos.writeUTF(string);
			dos.flush();
//			dos.close();
			}else{
				textArea.setText(textArea.getText()+"未连接服务器"+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static final long serialVersionUID = 1L;
	private final JMenu netMenu = new JMenu("网络");
	private final JMenuItem connectMenuItem = new JMenuItem("连接");
	private final JMenuItem disconnectMenuItems = new JMenuItem("断开");
	
	public static void main(String[] args) {
		ChatClient chatClient = new ChatClient();
	}
	
	
	//在构造函数里初始化窗口
	public ChatClient() {
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
				send();
			}

		});
		getContentPane().add(textField,BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuBar.add(netMenu);
		connectMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		netMenu.add(connectMenuItem);
		disconnectMenuItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disConnect();
			}
		});
		
		netMenu.add(disconnectMenuItems);
		
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("客户端退出..");
				disConnect();
				System.exit(0);
			}
		});
	}
	
	
}
  