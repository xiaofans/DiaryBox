package xiaofan.diarybox.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import xiaofan.diarybox.bean.BookBean;
import xiaofan.diarybox.model.DaDB;
import xiaofan.diarybox.model.XLModel;
import xiaofan.diarybox.util.Constants;
import xiaofan.diarybox.util.Utils;


import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class XFApplication extends Application{
	

	private int mScale = -1;
	private int mColorTheme = -1;
	
	private int mPageHeight;
	private int mPageWidth;
	
	private List<String> para;
	private String title;
	private String author;
	private String html;
	
	private DaDB daDb;
	
	private static XFApplication instance;
	
	private static Paint textPaint;
	private static Paint linePaint;
	
	private Typeface tf;
	
	private SharedPreferences mPrefs;
	
	public static char sFullWidthEndPunctuations[];
	public static char sFullWidthStartPunctuations[];
	
	private List<String> linkList;
	 
	public static XFApplication getInstance(){
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		init();
	}

	private void init() {
		
		 DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((WindowManager)getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);;
		mPageHeight = localDisplayMetrics.heightPixels;
		mPageWidth = localDisplayMetrics.widthPixels;
		
		
		//tf = Typeface.createFromAsset(getAssets(), "fonts/fzlth.ttf");
		this.mPrefs = getSharedPreferences("datastore", 0);
	}
	
	public  int getScale(){
		
		if(mScale < 0){
			mScale = this.mPrefs.getInt("scale", 1);
		}
		
		return mScale;
	}
	
	public void setScale(int scale){
		if(scale < 0 || scale > 3) return;
		
		this.mScale = scale;
		SharedPreferences.Editor editor = this.mPrefs.edit();
		editor.putInt("scale", scale);
		editor.commit();
		
	}
	
	public int getColorTheme(){
		if(this.mColorTheme < 0)
			this.mColorTheme = this.mPrefs.getInt("theme", 0);
		
		return mColorTheme;
	}
	
	
	public void setColorTheme(int theme){
		this.mColorTheme = theme;
		SharedPreferences.Editor localEditor = this.mPrefs.edit();
		localEditor.putInt("theme", theme); 
		localEditor.commit();
	}


	public int getPageWidth(){
		return mPageWidth;
	}
	
	public int getPageHeight(){
		return mPageHeight;
	}
	
	
	 public int getTextAreaHeight()
	  {
	    int i = Utils.dp2pixel(this, 40);
	    int j = Utils.dp2pixel(this, 36);
	    return this.mPageHeight - i - j;
	  }
	 
	

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		
	}
	
	
	public List<String> getPara() {
		return para;
	}
	
	public DaDB getDaDB(){
		if(daDb == null){
			daDb = new DaDB(this);
		}
		return daDb;
	}

	public boolean loadNovelData(){
		InputStream is = null;
		BufferedReader br  = null;
		para  = null;
		try {
			is = getResources().getAssets().open("novel/xiaozandxiaol.txt");
			br = new BufferedReader(new InputStreamReader(is));
			
			String word = "";
			StringBuilder sb = new StringBuilder();
			para = new ArrayList<String>();
			while((word = br.readLine()) != null){
				if(word.indexOf("���ڣ�2012") != -1){
					para.add(sb.toString());
					sb = new StringBuilder();					
				}else{					
					sb.append(word);
				}
				
				
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	// for
	public boolean loadNovelData(String html){
		this.html = html;
		Document doc = Jsoup.parse(html);
		Elements titleEle   = doc.getElementsByTag("h1");
	//	String title = "";
		//String author = "";
		//String articleContent = "";
		if(titleEle.size() > 0){
			title = titleEle.get(0).text();
		}
		
		Elements authorEle = doc.getElementsByAttributeValue("class", "article_author");
		if(authorEle.size() > 0){
			author = authorEle.get(0).text();
		}
		
		 para = new ArrayList<String>();
		 para.add("作者:"+author);
		
		Elements paraEle = doc.getElementsByAttributeValue("class", "article_text");
		if(paraEle.size() > 0){
			Elements paraele = paraEle.get(0).getElementsByTag("p");
			for(int i =0; i < paraele.size();i++){
				para.add(paraele.get(i).text());
			}
		}
		
		return true;
	}
	
	public boolean loadDiaryData(String html){
		Document doc = Jsoup.parse(html);//Jsoup.connect("http://www.douban.com/note/251109205/").get();
		//System.out.println(doc.getElementsByAttributeValue("id", "link-report").get(0).getElementsByTag("A").size());
		title = doc.getElementsByAttributeValue("class", "note-header").get(0).getElementsByTag("h1").get(0).text();
		//System.out.println("title:" + title);
		
		author =  doc.getElementsByAttributeValue("id", "db-usr-profile").get(0).getElementsByAttributeValue("class", "info").get(0).getElementsByTag("h1").text();
		author = author.substring(0, author.length() - 3);
		//System.out.println("author:"+author);
		
		String content = doc.getElementById("link-report").toString();
		String paras[] = content.split("<br \\/>");
		para = new ArrayList<String>();
		para.add("");
		para.add("作者:"+author);
		para.add("");
		para.add("");
		for(int i =0 ; i < paras.length; i++){
			String str = paras[i].replace("&nbsp;", "");
			str = Jsoup.parse(str).text();
			para.add(str);
		}
		return true;
	}
	
	public void buildNovelData(int wordCountEachLine,int textAreaHeight){		
		XLModel xlm = new XLModel();
		xlm.calculateLineInfos(wordCountEachLine);
		xlm.buildPages(getTextAreaHeight(), Constants.LINE_SPACING[mScale], Constants.TEXTSIZE_NORMAL[mScale]);
		
	}

	public static Paint getTextPaint() {
		return textPaint;
	}

	public static void setTextPaint(Paint textPaint) {
		XFApplication.textPaint = textPaint;
	}

	public static Paint getLinePaint() {
		return linePaint;
	}

	public static void setLinePaint(Paint linePaint) {
		XFApplication.linePaint = linePaint;
	}


	public Typeface getTypeface() {
		return tf;
	}
	
	public void setTypeface(Typeface tf){
		this.tf = tf;
	}

	public int getLastRedePageIndex() {
		return this.mPrefs.getInt("lastReadPageIndex", 0);
	}

	public void setLastRedePageIndex(int lastRedePageIndex) {
		SharedPreferences.Editor  localEditor = this.mPrefs.edit();
		localEditor.putInt("lastReadPageIndex", lastRedePageIndex);
		localEditor.commit();
	}
	 public boolean isPunctuation(char paramChar)
	  {
		boolean bool = false;
	    switch (Character.getType(paramChar))
	    {
		   	case 20:
		    case 21:
		    case 22:
		    case 23:
		    case 24:
		    case 25:
		    case 26:
		    case 27:
		    case 28:
		    case 29:
		    case 30:
		    	bool = true;
		    	break;
		    default:
		    	bool = false;
		    	break;
	    }
	    return bool;
	  }
	 
	 
	   static 
	    {
	        char ac[] = new char[16];
	        ac[0] = '\u201C';
	        ac[1] = '\u300A';
	        ac[2] = '\uFF08';
	        ac[3] = '\u2018';
	        ac[4] = '\u3008';
	        ac[5] = '\u300C';
	        ac[6] = '\u300E';
	        ac[7] = '\u3010';
	        ac[8] = '\u3014';
	        ac[9] = '\u3016';
	        ac[10] = '\u3018';
	        ac[11] = '\u301A';
	        ac[12] = '\u301D';
	        ac[13] = '\uFF3B';
	        ac[14] = '\uFF5B';
	        ac[15] = '\uFF5F';
	        sFullWidthStartPunctuations = ac;
	        char ac1[] = new char[25];
	        ac1[0] = '\uFF0C';
	        ac1[1] = '\u3002';
	        ac1[2] = '\u3001';
	        ac1[3] = '\uFF1A';
	        ac1[4] = '\uFF1B';
	        ac1[5] = '\uFF1F';
	        ac1[6] = '\uFF01';
	        ac1[7] = '\u2019';
	        ac1[8] = '\u201D';
	        ac1[9] = '\uFF09';
	        ac1[10] = '\u300B';
	        ac1[11] = '\u3009';
	        ac1[12] = '\uFF0E';
	        ac1[13] = '\u301E';
	        ac1[14] = '\uFF07';
	        ac1[15] = '\u300F';
	        ac1[16] = '\u300D';
	        ac1[17] = '\u3011';
	        ac1[18] = '\u3015';
	        ac1[19] = '\u3017';
	        ac1[20] = '\u3019';
	        ac1[21] = '\u301B';
	        ac1[22] = '\uFF3D';
	        ac1[23] = '\uFF5D';
	        ac1[24] = '\uFF60';
	        sFullWidthEndPunctuations = ac1;
	    }


	public String getTitle() {
		return title;
	}
	

	 public SharedPreferences getPreferences() {
	    return this.mPrefs;
}

	public String getHtml() {
		return html;
	}

	public String getAuthor() {
		// TODO Auto-generated method stub
		return author;
	}

	public void setNewArticle(BookBean bb) {
		title = bb.getTitile();
		author = bb.getAuthor();
		String p[] = bb.getHtml().split("xiaofan");
		if(para == null) para = new ArrayList<String>();
		
		para.clear();
		for(int i = 0; i < p.length; i++){
			para.add(p[i]);
		}
		
		buildNovelData(getEachLineCount(), getTextAreaHeight());
	}
	
	private int getEachLineCount(){
		int mPageWidth =getPageWidth();
		int m = Utils.dp2pixel(this, Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()]);
		int n = Utils.dp2pixel(this, 18);
	    int i1 = mPageWidth - n * 2;
	    int i2 = i1 - i1 % m;
	    int i3 = (mPageWidth - i2) / 2;
	    
	    int eachLineCount = (mPageWidth - i3*2)/ Utils.dp2pixel(this,Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()]);
		return eachLineCount;
	}

	public List<String> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<String> linkList) {
		this.linkList = linkList;
	}
	
	
	
}


