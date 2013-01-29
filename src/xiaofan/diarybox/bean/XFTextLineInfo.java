package xiaofan.diarybox.bean;

import xiaofan.diarybox.app.XFApplication;

public class XFTextLineInfo {

	private int startWord;
	private int endWord;
	
	private boolean isParaStart;
	//private char[] lineWords;
	private String lineWords;
	private boolean isEndPunctuation;
	
	public int getStartWord() {
		return startWord;
	}
	public void setStartWord(int startWord) {
		this.startWord = startWord;
	}
	public int getEndWord() {
		return endWord;
	}
	public void setEndWord(int endWord) {
		this.endWord = endWord;
	}
	public boolean isParaStart() {
		return isParaStart;
	}
	public void setParaStart(boolean isParaStart) {
		this.isParaStart = isParaStart;
	}
//	public char[] getLineWords() {
//		return lineWords;
//	}
//	public void setLineWords(char[] lineWords) {
//		this.lineWords = lineWords;
//	}
	public String getLineWords() {
		return lineWords;
	}
	public void setLineWords(String lineWords) {
		this.lineWords = lineWords;
	}
	public boolean isEndPunctuation() {
		if(lineWords.length() == 0){
			return false;
		}else{			
			char last = lineWords.charAt(lineWords.length() -1);
			return XFApplication.getInstance().isPunctuation(last);
		}
	}
	
	public boolean isFullWidthEndPunctuations(){
		char last = lineWords.charAt(lineWords.length() -1);
		char all[] = XFApplication.sFullWidthEndPunctuations;
		boolean isContains = false;
		for(int i = 0; i < all.length; i++){
			if(all[i] == last){
				isContains = true;
			}
		}
		return isContains;
	}
	
	public boolean isStartPunctuation(){
		if(lineWords.length() == 0){
			return false;
		}else{			
			char first = lineWords.charAt(0);
			return XFApplication.getInstance().isPunctuation(first);
		}
	}
	
	public boolean isFullStartPunctuations(){
		char last = lineWords.charAt(lineWords.length() -1);
		char all[] = XFApplication.sFullWidthStartPunctuations;
		boolean isContains = false;
		for(int i = 0; i < all.length; i++){
			if(all[i] == last){
				isContains = true;
			}
		}
		return isContains;
	}
	
	
}
