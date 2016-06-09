import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import data.CODE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatServer {

	static Integer port = 123;
	static ServerSocket ss =null;
	static DataInputStream dis = null;
	static Socket s = null;
	
	public static void main(String[] args) {
		
		ChatServer chatServer = new ChatServer();
		
		ServerWindow serverWindow = new ServerWindow();
		serverWindow.init();
		
		try {
			//创建server
			ss = new ServerSocket(port);
			serverWindow.textArea.setText("服务器启动成功！"+ss.getLocalSocketAddress().toString()+"\n");
		}catch(SocketException e1){
			serverWindow.textArea.setText(serverWindow.textArea.getText()+"无法启动服务器，端口号: "+port+" 已被占用"+"\n");
		}catch (IOException e2) {
			serverWindow.textArea.setText(serverWindow.textArea.getText()+e2.toString()+"\n");
		}

		//用死循环接收
		while(!ss.isClosed()){
			//接受到一个socket
			try{
				s = ss.accept();
				serverWindow.textArea.setText(serverWindow.textArea.getText()+"\n"+s+"客户端连接\n");
				dis = new DataInputStream(s.getInputStream());
			} catch (IOException e4) {
				serverWindow.textArea.setText(serverWindow.textArea.getText()+e4.toString()+"连接中断！"+"\n");
				chatServer.disconnect();
			}
			
			//对收到的数据进行处理
			while (s!=null) {
				if (!s.isClosed()) {
					try{
						String message = dis.readUTF();
						serverWindow.textArea.setText(serverWindow.textArea.getText()+"客户端："+message+"\n");
					}catch(EOFException e1){
						chatServer.disconnect();
						System.out.println("客户端断开！");
						serverWindow.textArea.setText(serverWindow.textArea.getText()+"客户端已断开！"+"\n");
					} catch (IOException e) {
						serverWindow.textArea.setText(serverWindow.textArea.getText()+e.toString()+"错误！"+"\n");
						chatServer.disconnect();
					} 
				}
			}
		}
	}
	
	public void disconnect(){
		try{
			if(s!=null){
				s.close();
			}
			if(dis!=null){
				dis.close();
			}
		}catch(IOException e){
			System.out.println("关闭连接错误");
		}finally{
			if(s!=null){
				s=null;
			}
			if(dis!=null){
				dis=null;
			}
		}
	}
	//窗体内部类
	private static class ServerWindow extends JFrame{
		
		private static final long serialVersionUID = 1L;
		JTextArea textArea = new JTextArea();
		
		public void init() {
			
			setTitle("聊天室服务端");
			setLocation(80, 80);
			setSize(400, 300);
			setVisible(true);
			
			//布局
			textArea.setLineWrap(true);//激活自动换行
			textArea.setBackground(new Color(64, 224, 208));
//			textArea.setWrapStyleWord(true);//激活断行不断字
			JScrollPane scrollPane = new JScrollPane(textArea);
			add(scrollPane, BorderLayout.CENTER);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.out.println("服务器退出");
					System.exit(0);
				}
			});
			
		}
		
	}

}
  