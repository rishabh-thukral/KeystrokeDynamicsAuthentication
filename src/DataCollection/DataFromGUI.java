package DataCollection;

public class DataFromGUI {
	long timestampKeyStrokes[];
	String userInput;
	int backspaceHits;
	public DataFromGUI(int length) {
		timestampKeyStrokes = new long[2*length];
		backspaceHits = 0;
	}
}
