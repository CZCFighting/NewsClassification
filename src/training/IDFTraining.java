package training;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

import model.Token;

public class IDFTraining {
	//
	public static final int dataSize = 1990 *8;
	public static String sampledata = "D:\\CodeProject\\eclipseProject\\NewsClassification\\sampledata\\";
	
	public static String wordbase = "D:\\CodeProject\\eclipseProject\\NewsClassification\\resultdata\\wordBase.json";
	
	public static String stopwpath = "D:\\CodeProject\\eclipseProject\\NewsClassification\\resultdata\\stopWords.txt";
	
	private JiebaSegmenter segmenter;

	private HashMap<String, Integer> allWords;
	private WordUtil wordUtil;
	
	public IDFTraining(){
		this.segmenter = new JiebaSegmenter();
		this.allWords = new HashMap<>();
		this.wordUtil = new WordUtil(stopwpath);
		
	}
	
	
	public List<Token> toTokens(){
		List<Token> list = new ArrayList<>();
		String[] fileNames = {"财经","健康","教育","军事","科技","旅游","体育","文化"};
		int num = 0;
		String path = null;
		for(String file:fileNames) {
//			System.out.println("******** "+ ++num + ". 读取" + text.getTitle()+"********");
			for(int i = 10; i <= 1990; i++) {
				path = sampledata + file + "\\"+i+".txt";
				System.out.println("******** "+ ++num + ". 读取" +path +"********");
				String text = TextRW.textReader(path);
				List<String> words =wordUtil.distinct(wordUtil.filter(segmenter.process(text, SegMode.INDEX)));
				for(String s:words) {
					if(!allWords.containsKey(s)) {
						allWords.put(s, 1);
						System.out.println(s+"  "+1);
					}else {
						int count = allWords.get(s);
						allWords.replace(s, count, count+1);
						System.out.println(s+"  "+ (count+1));
					}
				}
			}
//			
			
			
		}
		for(Entry<String, Integer> entry:allWords.entrySet()) {
			int count = entry.getValue();
			String word = entry.getKey();
			float idf = (float)Math.log10(dataSize/(count+1));
			if(idf>0) {
				Token token = new Token(word, count, idf);
				list.add(token);
			}	
		}	
		return list;
	}
	
	
	public void initIDF(){
		BufferedWriter bw = null;
		JSONWriter writer = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wordbase), Charset.forName("UTF-8")));
			writer = new JSONWriter(bw);
			writer.object();
				for(Token t:toTokens()) {
					System.out.println("写入  "+ t.getWord());
					JSONObject termob = new JSONObject();
					termob.put("count",t.getCount());
					termob.put("idfvalue", t.getIdfValue());
					writer.key(t.getWord()).value(termob);
				}
				writer.endObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
