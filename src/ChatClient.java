import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JScrollPane;

public class ChatClient extends JFrame{

	JTextField textField = new JTextField();
	JTextArea textArea = new JTextArea();
	JMenu menu = new JMenu("网络");
	JButton button = new JButton("连接服务端");
	
	//在构造函数里初始化
	public ChatClient() {
		//设置尺寸标题
		this.setLocation(400, 200);
		this.setSize(400, 300);
		this.setTitle("客户端1");
		
		//布局管理器
		textArea.setEditable(false);
		textArea.setLineWrap(true);//激活自动换行
		textArea.setWrapStyleWord(true);//激活断行不断字
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
		textField.addActionListener(new ActionListener() {
			//textField 预先响应了回车键事件
			public void actionPerformed(ActionEvent e) {
				String string = textField.getText().trim();
				textArea.setText(textArea.getText()+string+"\n");
				textField.setText("");
			}
		});
		
		getContentPane().add(textField,BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuBar.add(menu);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		menu.add(button);
		
//		this.pack();
		
		this.setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("客户端退出..");
				System.exit(0);
			}
		});
		
	}
	
	//连接函数
	private void connect(){
		try {
			textArea.setText(textArea.getText()+"\n"+"正在连接服务端....");
			Socket s = new Socket("127.0.0.1", 123);
			if(s.isConnected()){
				textArea.setText(textArea.getText()+"\n"+"服务器连接成功....");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		ChatClient chatClient = new ChatClient();
	}
}
  