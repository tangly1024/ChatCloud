import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ChatServer {
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		
		ServerWindow serverWindow = new ServerWindow();
		serverWindow.init();
		serverWindow.textArea.setText("服务器已经启动！------");
		
		try {
			//创建server
			ServerSocket ss = new ServerSocket(123);
			//用死循环接受
			while(true){
				//接受到一个socket
				Socket socket = ss.accept();
				serverWindow.textArea.setText(serverWindow.textArea.getText()+"\n一个客户端已经连接====================");
				serverWindow.textArea.setText(serverWindow.textArea.getText()+"\n"+socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class ServerWindow extends JFrame{
		
		private static final long serialVersionUID = 1L;
		
		JTextArea textArea = new JTextArea();
		
		public void init() {
			
			setTitle("聊天室服务端");
			setLocation(400, 200);
			setSize(400, 300);
			setVisible(true);
			
			add(textArea, BorderLayout.CENTER);
			
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
  