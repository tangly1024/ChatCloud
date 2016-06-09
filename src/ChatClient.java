import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionListener;

public class ChatClient extends JFrame{

	JTextField textField = new JTextField();
	JTextArea textArea = new JTextArea();
	
	public ChatClient() {
		this.setLocation(400, 200);
		this.setSize(400, 300);
		this.setTitle("客户端1");
		textArea.setEditable(false);
		//布局管理器
		getContentPane().add(textArea,BorderLayout.CENTER);
		
		textField.addActionListener(new ActionListener() {
			//textField 预先响应了回车键事件
			public void actionPerformed(ActionEvent e) {
				String string = textField.getText().trim();
				textArea.setText(textArea.getText()+"\n"+string);
				textField.setText("");
			}
		});
		getContentPane().add(textField,BorderLayout.SOUTH);
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

	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		ChatClient chatClient = new ChatClient();
		//创建网络连接
		try {
			chatClient.textArea.setText(chatClient.textArea.getText()+"\n"+"正在连接服务端....");
			Socket s = new Socket("127.0.0.1", 123);
			if(s.isConnected()){
				chatClient.textArea.setText(chatClient.textArea.getText()+"\n"+"服务器连接成功....");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
  