package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class ChatClient {

	//窗口界面
	MainWindow mainWindow = null;
	
	//网络信息
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	Integer clientId = null;
	String serverURL = "127.0.0.1";
	int port = 123;
	
	//==============主函数===========================
	public static void main(String[] args) {
		ChatClient client = new ChatClient();
		MainWindow mainWidow = new MainWindow(client);
		client.mainWindow = mainWidow;
	}
	
	//========================方法=============================

	//连接服务端
	public void connect(){
		
		try {
			//连接的同时保存下输出流的引用
			s = new Socket(serverURL, port);
			if(s.isConnected()){
				dos = new DataOutputStream(s.getOutputStream());
				dis = new DataInputStream(s.getInputStream());
				//获取服务端反馈的信息
				String clientMessage = dis.readUTF();
				mainWindow.writeText("服务端: "+clientMessage);
				clientId = dis.readInt();
				String clientUser = "ID: 游客"+clientId;
				mainWindow.setTitle("客户端:已连接 "+clientUser);
			}
			
		}catch(ConnectException e1){
			mainWindow.writeText("在"+serverURL+":"+ port + " 上没有找到服务器....");
		}catch (SocketException e) {
			mainWindow.writeText("已从服务器断开连接");
		}catch (IOException e2) {
			mainWindow.writeText("连接错误....");
		}
		
		//连接成功后启动监听服务器消息的线程；
		new Thread(new Runnable() {
			public void run() {
				 receive();
			}
		}).start();
		
	}
	
	//断开连接
	public void disConnect(){
		
		if(s==null && dos ==null){
			mainWindow.writeText("并没有连接\n");
		}
		
		if(s!=null){
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			s=null;
			mainWindow.writeText("已断开与服务器的连接\n");
			mainWindow.setTitle("客户端:连接已断开");
		}
		if(dos !=null){
			try {
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dos=null;
		}
		
	}
	
	//输入框响应发送
	public void send(String message){
		mainWindow.writeText("游客"+clientId+":"+message);
		mainWindow.cleanTextField();
		//一个输出流
		try {
			if(s!=null && !s.isClosed()){
			dos.writeUTF(message);
			dos.flush();
			}else{
				mainWindow.setTitle("客户端:连接已断开");
				mainWindow.writeText("\n--未连接服务器--");
			}
		} catch (SocketException e2) {
			disConnect();
			mainWindow.setTitle("客户端:连接已断开");
			mainWindow.writeText("\n--已从服务器断开--");
		} catch (IOException e) {
			disConnect();
			mainWindow.writeText(e.toString());
		} 
	}

	//接受消息并且显示的方法
	public void receive(){
		while(s!=null && !s.isClosed()){
			try {
				if(s!=null && !s.isClosed()){
				String message = dis.readUTF();
				mainWindow.writeText(message);
				}
			} catch (SocketException e2) {
				disConnect();
				mainWindow.setTitle("客户端:连接出错");
				mainWindow.writeText("\n--连接出错");
			} catch (IOException e) {
				disConnect();
				mainWindow.writeText(e.toString());
			} 
		}
	}

}
  
			