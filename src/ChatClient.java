import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JTextField;

public class ChatClient extends JFrame{

	private static final long serialVersionUID = 1L;

	JTextField textField = new JTextField();
	JTextArea textArea = new JTextArea();
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame(){
		this.setLocation(400, 200);
		this.setSize(400, 300);
		this.setTitle("客户端1");
		//布局管理器
		getContentPane().add(textArea,BorderLayout.CENTER);
		getContentPane().add(textField,BorderLayout.SOUTH);
//		this.pack();
		this.setVisible(true);
		
		
		
	}
}
  