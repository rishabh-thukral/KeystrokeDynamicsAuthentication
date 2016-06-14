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

public class Collect {
	Node dataSet[];
	public static DataFromGUI collection;
	public static volatile boolean inGUI = false;
	public Collect(String[] originalStrings) {
		dataSet = new Node[originalStrings.length];
		for(int i=0;i<originalStrings.length;i++){
			dataSet[i] = new Node();
			dataSet[i].setOriginalText(originalStrings[i]);
		}
	}
	
	public static int EDiterative(String s,String t){
		int m=s.length();
		int n=t.length();
		int[][] DP =new int[m+1][n+1];//defined as DP[i][j] is edit distance for strings last i characters of s and last j characters of t
		for(int i=0;i<m+1;i++){
			DP[i][0] = i;
		}
		for(int j=0;j<n+1;j++){
			DP[0][j] = j;
		}
		for(int i=1;i<m+1;i++){
			for(int j=1;j<n+1;j++){
				if(s.charAt(m-i)==t.charAt(n-j)){
					DP[i][j] = DP[i-1][j-1];
				}
				else{
					int o1,o2,o3;
					o1=1+DP[i][j-1];//insertion
					o2=1+DP[i-1][j];//removal
					o3=1+DP[i-1][j-1];//subsi
					if(o1<o2){
						if(o3<o1){
							DP[i][j]=o3;
						}
						else{
							DP[i][j]=o1;
						}
					}
					else{//o2<01
						if(o3<o2){
							DP[i][j]=o3;
						}
						else{
							DP[i][j]=o2;
						}
					}
				}
			}
		}
		return DP[m][n];
	}
	public static Node minorTest(){
		inGUI = false;
		String testString = "this is your test";
		Node testResult = new Node();
		BuildGUI testGui = new BuildGUI(testString,true);
		Collect.collection = new DataFromGUI(testString.length());
		while(inGUI == false){ 
		    try { 
		       Thread.sleep(200);
		    } catch(InterruptedException e) {
		    } 
		} 
		double bc = (double) Collect.collection.backspaceHits;
		double ed = (double) EDiterative(testString, Collect.collection.userInput);
		double tpch = 0;
		if(testString.length()!=0){
			tpch = (Collect.collection.timestampKeyStrokes[testString.length()-1]-Collect.collection.timestampKeyStrokes[0])/(testString.length());
		}
		double tpwd=calculateTimePerWord(Collect.collection.userInput,Collect.collection.timestampKeyStrokes);
		testResult.setFeatures(tpch, tpwd, ed, bc);
		testResult.setUserText(Collect.collection.userInput);
		System.out.println("Test Result");
		testResult.print();
		return testResult;
	}
	public static Node[] majorLearning(){
		String originalStrings[]={
				"copy this text",
				"text 2 for you",
				"here comes the third text"
		};
		Collect data = new Collect(originalStrings);
		for(int i=0;i<originalStrings.length;i++){
			Collect.collection = new DataFromGUI(originalStrings[i].length());
			// launch GUI from here with originalStrings[i] as a parameter
			BuildGUI gui = new BuildGUI(originalStrings[i],false);
			//wait till user presses ok in GUI for doing that
			while(inGUI == false){ 
			    try { 
			       Thread.sleep(200);
			    } catch(InterruptedException e) {
			    } 
			} 
			inGUI = false;
			//ActionListener for gui
//			public void actionPerformed(ActionEvent e) {
//				using string param gui should fill collection  member of this class and i have made it static
//			    MainClass.inGUI = true;
//			}
			
			// perform operations when inGUI is true 
			//Collect.collection has all the data for the current string 
			// we have to extract all features from this and fill them in Node[i];
			double bc = (double) Collect.collection.backspaceHits;
			//System.out.println("Now i am sending "+ i +" these two for edit distance " + originalStrings[i] + " and " + Collect.collection.userInput );
			double ed = (double) EDiterative(originalStrings[i], Collect.collection.userInput);
			double tpch = 0;
			//Approach 1
//			if(originalStrings[i].length()!=0){
//				tpch = (Collect.collection.timestampKeyStrokes[originalStrings[i].length()-1]-Collect.collection.timestampKeyStrokes[0])/(originalStrings[i].length());
//			}
			//Approach 2
			tpch = calculateTimePerChar(Collect.collection.timestampKeyStrokes,originalStrings[i].length());
			double tpwd=calculateTimePerWord(Collect.collection.userInput,Collect.collection.timestampKeyStrokes);
			data.dataSet[i].setFeatures(tpch, tpwd, ed, bc);
			data.dataSet[i].setUserText(Collect.collection.userInput);
			System.out.println("Learning " + (i+1) + " example\ntimeStamps : ");
			for(int j=0;j<originalStrings[i].length();j++){
				System.out.print(Collect.collection.timestampKeyStrokes[j] + "  ");
			}
			data.dataSet[i].print();
		}
		//data is ready in variable 'data'
		return data.dataSet;
	}

	private static double calculateTimePerChar(long[] timestampKeyStrokes,
			int length) {
		for(int i=0,j=length;i<j;){
			if(timestampKeyStrokes[i]!=0&&timestampKeyStrokes[j]!=0){
				return ((double)(timestampKeyStrokes[j]-timestampKeyStrokes[i]))/((double)(j-i+1));
			}else if(timestampKeyStrokes[i]!=0){
				j--;
			}else if(timestampKeyStrokes[j]!=0){
				i++;
			}else{
				i++;
				j--;
			}
		}
		return 1;
	}

	private static double calculateTimePerWord(String userInput,
			long[] timestampKeyStrokes) {

		userInput = userInput + " ";
		//System.out.println("In T/Word : " +userInput);
		int length = userInput.length();
		long wc = 0;
		for(int i=0;i<length;i++){
			if(userInput.charAt(i)==' '&&timestampKeyStrokes[i]!=0){
				wc++;
			}
		}
		if(wc==0){
			wc++;
		}
		for(int i=0,j=length;i<j;){
			if(timestampKeyStrokes[i]!=0&&timestampKeyStrokes[j]!=0){
				return ((double)(timestampKeyStrokes[j]-timestampKeyStrokes[i]))/((double)(wc));
			}else if(timestampKeyStrokes[i]!=0){
				j--;
			}else if(timestampKeyStrokes[j]!=0){
				i++;
			}else{
				i++;
				j--;
			}
		}
		return 1;
	}
	
	
	
	
	
	
	
	
	
}
