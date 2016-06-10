package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

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
	static Integer idToClient = 1;
	static List<Service> services = new ArrayList<ChatServer.Service>();
	
	public void start(){
		
		//生成服务器的可视化界面
		serverWindow.init();
		
		//开启端口号监听
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
		while(ss!=null){
			//接受到一个socket
			try{
				s = ss.accept();//阻塞方法
				//每接入一个用户就启动一个线程
				if(s.isConnected()){
					Service service = new Service(this);
					services.add(service);
					new Thread(service).start();
				}
			} catch (IOException e4) {
				serverWindow.textArea.setText(serverWindow.textArea.getText()+e4.toString()+"连接中断！"+"\n");
			}
			
		}
	}
	
	//为每个连入的客户端启动一个单独的服务线程
	public class Service implements Runnable{
		
		public Socket s = null;
		public ServerWindow serverWindow = null;
		public DataInputStream dis = null;
		public DataOutputStream dos = null;
		public Integer clientId = null;
		
		public Service(ChatServer chatServer) {
			//传入服务器接受到的客户端socket 以及界面；
			this.s = chatServer.s;
			this.serverWindow = chatServer.serverWindow;
		}
		
		//运行服务
		@Override
		public void run() {
			
			//保存输入输出管道
			if(s!=null){
				try {
					//存下socket中的dis和dos 假设一直不关闭
					dis = new DataInputStream(s.getInputStream());
					dos = new DataOutputStream(s.getOutputStream());
				} catch (IOException e) {
					serverWindow.textArea.setText(serverWindow.textArea.getText()+e.toString()+"\n连接中断\n");
					this.disconnect(this);
				}
			}
			
			//先发一段反馈信息给客户端
			if(dos!=null){
				try {
					dos.writeUTF("连接成功...");
					dos.flush();
					//分配id给该客户端
					this.clientId = idToClient;
					dos.writeInt(idToClient++);
					dos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			serverWindow.textArea.setText(serverWindow.textArea.getText()+"游客"+clientId+"已连接\n");
			
			//死循环进行消息的接收
			//和转发
			while (s!=null&&!s.isClosed()) {
					try{
						
						//阻塞函数：运行卡在这里
						String message = dis.readUTF();
						serverWindow.textArea.setText(serverWindow.textArea.getText()+"游客"+this.clientId+": "+message+"\n");
						//每次从任一个客户端收到消息后给其他所有客户端转发
						for(Service service : services){
//System.out.println("遍历过程的客户端id"+service.clientId);
//System.err.println("当前收到消息的客户端id"+this.clientId);
//System.out.println("是否应该发出数据:"+(service.clientId != this.clientId));
							if (service.clientId != this.clientId){
								service.dos.writeUTF("游客"+this.clientId+": "+message);
								service.dos.flush();
							}
						}
						
					}catch(EOFException e1){
						disconnect(this);
						serverWindow.textArea.setText(serverWindow.textArea.getText()+"游客"+this.clientId+"已断开！"+"\n");
					} catch (IOException e) {
						disconnect(this);
						serverWindow.textArea.setText(serverWindow.textArea.getText()+e.toString()+"错误！"+"\n");
					} 
					
			}
		}
		
		//断开连接
		public void disconnect(Service service){
			try{
				if(service.s!=null){
					service.s.close();
				}
				if(service.dis!=null){
					service.dis.close();
				}
			}catch(IOException e){
				System.out.println("关闭连接错误");
			}finally{
				if(service.s!=null){
					service.s=null;
				}
				if(service.dis!=null){
					service.dis=null;
				}
				if(service!=null){
					//移除该线程，不再对其做发送和接受的处理
					services.remove(service);
					service=null;
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
			
			//setVisible 放在最后 解决了textArea有时候无法正常显示的问题
			setVisible(true);
		}
		
	}

	
	//main函数：入口
	public static void main(String[] args) {
		ChatServer chatServer = new ChatServer();
		chatServer.start();
	}
}
  