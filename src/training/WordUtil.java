package training;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huaban.analysis.jieba.SegToken;

public class WordUtil {
	
	private List<String> stopWords;
	public WordUtil(String path) {
		this.stopWords = new ArrayList<>();
		File file = new File(path);
		String line;
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(file));
			while((line=bReader.readLine())!=null) {
				this.stopWords.add(line.trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public List<String> getStopWords() {
		return stopWords;
	}
	public void setStopWords(List<String> stopWords) {
		this.stopWords = stopWords;
	}
	
	public boolean isWhiteSpace(String s) {
		if(s.equals("\n")||s.equals("\t")||s.equals("\r")) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * ȥ��ͣ�ô�
	 * @param list
	 * @return
	 */
	public List<String> filter(List<SegToken> list){
		List<String> resultList = new ArrayList<>();
		for(SegToken s:list) {
			if(!this.stopWords.contains(s.word)) {
				resultList.add(s.word);
			}
		}
		return resultList;
	}
	
	/**
	 * ��ȥһ�������б��е��ظ������һ��Ԫ�ز��ظ����б�
	 * @param wordList
	 * @return
	 */
	public List<String> distinct(List<String> wordList){
		List<String> list= new ArrayList<>();
		for(String word:wordList) {
			if(!list.contains(word)) {
				list.add(word);
			}
		}
		return list;
	}
	/**
	 * ͳ��һ�������б��г��ֵĴʵ�Ƶ��
	 * @param wordList
	 * @return
	 */
	public HashMap<String, Integer> countWordFry(List<String> wordList){
		HashMap<String, Integer> map = new HashMap<>();
		for(String s:wordList) {
			map.put(s, Collections.frequency(wordList, s));
		}
		return map;
	}
}
