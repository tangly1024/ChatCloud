package server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatServer {

	static Integer port = 123;
	static ServerSocket ss =null;
	static Socket s = null;
	static ServerWindow serverWindow = new ServerWindow();
	
	public static void main(String[] args) {
		new ChatServer().start();
	}
	
	public void start(){
		
		//生成服务器的可视化界面
		serverWindow.init();
		
		try {
			//创建server
			ss = new ServerSocket(port);
			serverWindow.textArea.setText("服务器启动成功！"+ss.getLocalSocketAddress().toString()+"\n");
			serverWindow.repaint();//有时候界面无法显示，重绘窗口
		}catch(SocketException e1){
			serverWindow.textArea.setText(serverWindow.textArea.getText()+"无法启动服务器，端口号: "+port+" 已被占用"+"\n");
		}catch (IOException e2) {
			serverWindow.textArea.setText(serverWindow.textArea.getText()+e2.toString()+"\n");
		}

		//用死循环等待用户的连接
		while(!ss.isClosed()){
			//接受到一个socket
			try{
				System.out.println("进入等待状态");
				s = ss.accept();//阻塞方法
				if(s.isConnected()){
					Service service = new Service(this);
					new Thread(service).start();
				}
			} catch (IOException e4) {
				serverWindow.textArea.setText(serverWindow.textArea.getText()+e4.toString()+"连接中断！"+"\n");
			}
			
		}
	}
	
	public class Service implements Runnable{
		
		public Socket s = null;
		public ServerWindow serverWindow = null;
		public DataInputStream dis = null;
		
		public Service(ChatServer chatServer) {
			//传入服务器接受到的客户端socke 以及界面；
			this.s = chatServer.s;
			this.serverWindow = chatServer.serverWindow;
		}
		
		//运行服务
		@Override
		public void run() {
			
			serverWindow.textArea.setText(serverWindow.textArea.getText()+"\n"+s+"客户端连接\n");
			//对收到的数据进行处理
			while (s!=null) {
				try {
					dis = new DataInputStream(s.getInputStream());
				} catch (IOException e) {
					serverWindow.textArea.setText(serverWindow.textArea.getText()+e.toString()+"\n连接中断\n");
					this.disconnect();
				}
				if (!s.isClosed()) {
					try{
						//阻塞函数：运行卡在这里
						String message = dis.readUTF();
						serverWindow.textArea.setText(serverWindow.textArea.getText()+"客户端："+message+"\n");
					}catch(EOFException e1){
						this.disconnect();
						System.out.println("客户端断开！");
						serverWindow.textArea.setText(serverWindow.textArea.getText()+"客户端已断开！"+"\n");
					} catch (IOException e) {
						serverWindow.textArea.setText(serverWindow.textArea.getText()+e.toString()+"错误！"+"\n");
						this.disconnect();
					} 
				}
			}
		}
		
		//断开连接
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
  