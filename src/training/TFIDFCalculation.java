package training;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class TFIDFCalculation {
	private static String resultDataBase = "D:\\CodeProject\\eclipseProject\\NewsClassification\\resultdata\\";
	private WordUtil wordUtil;
	private JiebaSegmenter segmenter;
	
	public TFIDFCalculation() {
		this.wordUtil = new WordUtil(IDFTraining.stopwpath);
		this.segmenter = new JiebaSegmenter();
	}
	public JSONArray difArray(String type, JSONObject source) {
		JSONArray array = new JSONArray();
		String path= null;
		for(int i = 10; i <= 1990; i++) {
			path = IDFTraining.sampledata + type + "\\"+i+".txt";
			String text = TextRW.textReader(path);
			List<String> list = wordUtil.filter(segmenter.process(text, SegMode.INDEX));
			List<String> diStrings = wordUtil.distinct(list);
			JSONObject doJson = new JSONObject();
			JSONArray terms = new JSONArray();
			for(String s:diStrings) {
				System.out.println("计算 " + s);
				try {
					float idf = source.getJSONObject(s).getFloat("idfvalue");
					int count = Collections.frequency(list, s);
					float tf = (float)count/list.size();
					float tfidf = idf * tf;
					JSONObject term = new JSONObject(); 
					term.put("term", s);
					term.put("count", count);
					term.put("tfidf", tfidf);
					terms.put(term);
					System.out.println(s+ " " + tfidf);
				}catch (JSONException e) {
					System.out.println("没有这个词汇" + s);
					continue;
				}
				
				
			}
			doJson.put("terms", terms);
			doJson.put("total", list.size());
			doJson.put("id", i);
			array.put(doJson);
		}
			
	return array;
	}
	
	
	public void calTFIDF() {
		String[] fileNames = {"财经","健康","教育","军事","科技","旅游","体育","文化"};
		JSONTokener jsonTokener = null;
		JSONWriter jsonWriter = null;	
		BufferedWriter bw = null;
		try {
			jsonTokener = new JSONTokener(new FileReader(new File(IDFTraining.wordbase)));
			JSONObject wordObject = new JSONObject(jsonTokener);
			
			for(String s: fileNames) {
				System.out.println("读取 "+s);
				File file = new File(resultDataBase+s+".json");
				if(!file.exists()) {
					file.createNewFile();
				}
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),Charset.forName("UTF-8")));
				jsonWriter = new JSONWriter(bw);
				jsonWriter.object();
				jsonWriter.key(s).value(difArray(s, wordObject));
				jsonWriter.endObject();
				bw.flush();
				bw.close();
			}
		
		
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
