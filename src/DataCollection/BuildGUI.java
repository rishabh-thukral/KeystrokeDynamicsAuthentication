package DataCollection;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ColorModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BuildGUI extends JFrame implements KeyListener {
	private JFrame frame;
	private JPanel textPanel;
	private JPanel inputPanel;
	private JPanel buttonPanel;
	private JTextArea instructionText;
	private JTextArea typeString;
	private JTextField userInput;
	private JButton okBtn;
	private JButton cancelBtn;
	private static final String instruction = "Type the following text in the textbox and Click Okay Button(or Press Enter) to submit your Typing Rhythm. Dont Worry Some Typos are acceptable";
	public BuildGUI(String typingTest,boolean isVerification) {
		frame = new JFrame("KeyStroke Dynamics Authentication - Machine Learning");
		if(isVerification){
			frame.setTitle("Verification "+frame.getTitle());
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(850, 250);
		frame.setLayout(new GridLayout(4, 1));
		
		
		textPanel = new JPanel();
		inputPanel = new JPanel();
		buttonPanel = new JPanel();
		frame.add(textPanel);
		frame.add(inputPanel);
		frame.add(buttonPanel);
		
		
		instructionText = new JTextArea(instruction);
		instructionText.setBackground(getBackground());
		typeString = new JTextArea(typingTest);
		instructionText.setEditable(false);
		typeString.setEditable(false);
		//typeString.setBackground(new Color((float)0.5,(float)0.0,(float)0.0));
		textPanel.add(instructionText);
		textPanel.add(typeString);
		
		userInput = new JTextField(typeString.getText().length());
		userInput.setEnabled(true);
		userInput.addKeyListener(this);
		inputPanel.add(userInput);
		
		okBtn = new JButton("Okay");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Collect.inGUI = true;
				frame.dispose();	
			}
		});
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		frame.setVisible(true);
		
		userInput.requestFocusInWindow();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode()==KeyEvent.VK_BACK_SPACE){
			Collect.collection.backspaceHits++;
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
			Collect.inGUI = true;
			frame.dispose();
		}
		Collect.collection.userInput = userInput.getText();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		if(!arg0.isActionKey()){
			//System.out.println(userInput.getCaretPosition());
			Collect.collection.timestampKeyStrokes[userInput.getCaretPosition()] = System.currentTimeMillis();
//			if(Collect.collection.timestampKeyStrokes[userInput.getText().length()-1]==0){
//				System.out.print(arg0.getKeyChar());
//			}
			if(arg0.getKeyCode()==KeyEvent.VK_BACK_SPACE){
				Collect.collection.backspaceHits++;
			}
		//	System.out.println("in BuildGUI class we have updated " + Collect.collection.userInput);
		}
		
	}
//	public static void main(String args[]){
//		BuildGUI gui = new BuildGUI("halleuelah");
//	}
}
