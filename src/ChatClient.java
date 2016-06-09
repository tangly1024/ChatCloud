import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.tools.JavaCompiler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;

public class ChatClient extends JFrame{

	JTextField textField = new JTextField();
	JTextArea textArea = new JTextArea();
	
	public ChatClient() {
		this.setLocation(400, 200);
		this.setSize(400, 300);
		this.setTitle("客户端1");
		//布局管理器
		getContentPane().add(textArea,BorderLayout.CENTER);
		getContentPane().add(textField,BorderLayout.SOUTH);
//		this.pack();
		
		textField.addActionListener(new TFListener());
		
		this.setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}

	private class TFListener implements javax.swing.Action{

		@Override
		public void actionPerformed(ActionEvent e) {
			String string = textField.getText().trim();
			textArea.setText(string);
			textField.setText("");
		}

		@Override
		public Object getValue(String key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void putValue(String key, Object value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setEnabled(boolean b) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {
			// TODO Auto-generated method stub
			
		}


		
	}
	
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		new ChatClient();
	}
}
  