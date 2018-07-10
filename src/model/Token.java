package model;

public class Token {
	private String word;
	private int count;
	private float idfValue;
	
	public Token(String word, int count,float idfValue) {
		this.word = word;
		this.count = count;
		this.idfValue = idfValue;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getIdfValue() {
		return idfValue;
	}

	public void setIdfValue(float idfValue) {
		this.idfValue = idfValue;
	}
	
	public String toString() {
		
		return this.word+","+this.count+","+this.idfValue;
	}
	
}
