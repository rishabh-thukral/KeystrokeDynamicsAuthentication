package DataCollection;

public class Node {
	public String originalText;
	public String userText;
	public double timePerChar;
	public double timePerWord;
	public double editDistance;
	public double backspaceCount;
	public Node(){
		originalText = "";
		userText = "";
		timePerChar = 0;
		timePerWord = 0;
		editDistance = 0;
		backspaceCount = 0;
	}
	public void setOriginalText(String text){
		originalText = text;
	}
	public String getOriginalText(){
		return originalText;
	}
	public void setUserText(String text){
		userText = text;
	}
	public String getUserText(){
		return userText;
	}
	public void setFeatures(double tpch,double tpwd,double ed,double bc){
		timePerChar = tpch;
		timePerWord = tpwd;
		editDistance = ed;
		backspaceCount = bc;
	}
	public void print(){
		System.out.println("userText : " + userText);
		System.out.println("timePerChar : " + timePerChar);
		System.out.println("timePerWord : " + timePerWord);
		System.out.println("typos : " + editDistance);
		System.out.println("backspcaes " + backspaceCount);
		System.out.println();
	}
}
