package xiaofan.diarybox;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xiaofan.diarybox.R;
import xiaofan.diarybox.app.XFApplication;
import xiaofan.diarybox.util.AndroidUtils;
import xiaofan.diarybox.util.Constants;
import xiaofan.diarybox.util.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class WelcomeActivity extends Activity{
	private XFApplication app;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		
		setUpViews();
		app = (XFApplication) getApplication();
		//at.execute("");
		
		//loadArticle();
		if(AndroidUtils.isNetworkConnected(this)){			
			LoadTypefaceTask ltt = new LoadTypefaceTask();
			ltt.execute();
		}else{
			//Toast.makeText(this, "无网络连接，将转到保存文章列表...", Toast.LENGTH_LONG).show();
			new AlertDialog.Builder(this).setTitle("提示").setMessage("无网络连接,是查看已保存文章?").setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent intent = new Intent(WelcomeActivity.this,ArticleListActivity.class);
					intent.putExtra("from", "wel_page");
					WelcomeActivity.this.startActivity(intent);
					WelcomeActivity.this.finish();
				}
			}).setNeutralButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					WelcomeActivity.this.finish();
				}
			}).show();
		}
	}
	
	
	private void loadLoveLinks(){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://www.douban.com/note/259752362/", new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(Throwable t, String arg1) {
				super.onFailure(t, arg1);
				
				Log.i("DiaryBox", t.toString() + "args:" + arg1);
				Toast.makeText(WelcomeActivity.this, "初始化程序失败，请检查您的网络!", Toast.LENGTH_SHORT).show();
				WelcomeActivity.this.finish();
			}

			@Override
			public void onSuccess(String response) {
				super.onSuccess(response);
				Document doc = Jsoup.parse(response);
				List<String> links = new ArrayList<String>();
				Element ele = doc.getElementById("link-report");
				String[] ids = ele.text().split("、");
				for(int i = 0; i < ids.length; i++){
					links.add("http://www.douban.com/note/" +ids[i]+  "/");
				}
				//for(int i = 0; i < eles.size(); i++){
					//links.add(eles.get(i).attr("href"));
				//}
				
				XFApplication.getInstance().setLinkList(links);
				loadArticle();
			}
			
		});
	}


private void loadArticle() {
	if(XFApplication.getInstance().getLinkList() == null || XFApplication.getInstance().getLinkList().size() == 0){
		Toast.makeText(WelcomeActivity.this, "网络请求失败!", Toast.LENGTH_LONG).show();
		WelcomeActivity.this.finish();
	}
	List<String> links = XFApplication.getInstance().getLinkList();
	AsyncHttpClient client = new AsyncHttpClient();
	//client.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.83 Safari/537.1");
	System.out.println("link is:"+links.get(links.size()-1));
	client.get(links.get(links.size()-1), new AsyncHttpResponseHandler(){

		@Override
		public void onSuccess(String response) {
			super.onSuccess(response);
			if(app.loadDiaryData(response)){
				app.buildNovelData(getEachLineCount(), XFApplication.getInstance().getTextAreaHeight());
				Intent intent = new Intent(WelcomeActivity.this,AD8StoryActivity.class);
				startActivity(intent);
				finish();
				
			}else{
				Toast.makeText(WelcomeActivity.this, "网络请求失败!", Toast.LENGTH_LONG).show();
				WelcomeActivity.this.finish();
			}
			
		}

		@Override
		public void onFailure(Throwable t, String arg1) {
			// TODO Auto-generated method stub
			super.onFailure(t, arg1);
			
			Log.i("DailyArticle", t.toString() + "args:" + arg1);
			
			Toast.makeText(WelcomeActivity.this, "初始化程序失败，请检查您的网络!", Toast.LENGTH_SHORT).show();
			WelcomeActivity.this.finish();
		}


	});
	
	}



	private void setUpViews() {
		
		
		
		
	}



	@Override
	protected void onResume() {
		super.onResume();
	}


	/**
	 * @return
	 */
	
	
	
	private int getEachLineCount(){
		int mPageWidth = XFApplication.getInstance().getPageWidth();
		//int m = Utils.dp2pixel(this, 22);
		int m = Utils.dp2pixel(this, Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()]);
		int n = Utils.dp2pixel(this, 18);
	    int i1 = mPageWidth - n * 2;
	    int i2 = i1 - i1 % m;
	    int i3 = (mPageWidth - i2) / 2;
	    
	    int eachLineCount = (mPageWidth - i3*2)/ Utils.dp2pixel(this,Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()]);
		return eachLineCount;
	}
	
	
	AsyncTask<String,Integer,Boolean> at = new AsyncTask<String,Integer,Boolean>(){

		@Override
		protected Boolean doInBackground(String... params) {
			//Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/fzlth.ttf");
			//XFApplication.getInstance().setTypeface(tf);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(app.loadNovelData()){
				return true;
			}else{
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(result){
				app.buildNovelData(getEachLineCount(), XFApplication.getInstance().getTextAreaHeight());
				Intent intent = new Intent(WelcomeActivity.this,AD8StoryActivity.class);
				startActivity(intent);
				finish();
				
			}else{
				Toast.makeText(WelcomeActivity.this, "加载程序失败!", Toast.LENGTH_SHORT).show();
				WelcomeActivity.this.finish();
			}
		}
		
			
	};
	
	
	
	class LoadTypefaceTask extends AsyncTask<Void,Void,Typeface>{

		@Override
		protected Typeface doInBackground(Void... params) {
			Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/fzlth.ttf");
			return tf;
		}

		
		@Override
		protected void onPostExecute(Typeface result) {
			super.onPostExecute(result);

			XFApplication.getInstance().setTypeface(result);
			//loadArticle();
			loadLoveLinks();
		}
	}
	
}



