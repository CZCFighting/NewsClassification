package training;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import model.Token;

public class TextRW {

	public static String textReader(String path) {
		File file = new File(path);
		StringBuilder builder = new StringBuilder();
		try {
			String s = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("GBK")));
			while((s=br.readLine())!=null) {
				builder.append(System.lineSeparator()+s);				
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return builder.toString();
	}
	
	public static List<Token> readToToken(String path) {
		List<Token> list = new ArrayList<>();
		try {
			String s = null;
			String word = null;
			Token token = null;
			int count = 0;
			float idf = 0;
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			while((s=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(s, ",");
				while(st.hasMoreTokens()) {
					word = st.nextToken();
					count = Integer.parseInt(st.nextToken());
					idf = Float.parseFloat(st.nextToken());
				}
				token = new Token(word, count, idf);
				list.add(token);
			}
			br.close();
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
//	public static void textWritter(String s) {
//		 PrintWriter pw;
//		try {
//			pw = new PrintWriter( new FileWriter("D:\\CodeProject\\eclipseProject\\jiebaTokenizer\\src\\jiebaTokenizer\\wordBase.idf") );
//			pw.print(s);
//			pw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	   
//	}
}
