package xiaofan.diarybox;

import java.util.List;

import xiaofan.diarybox.R;
import xiaofan.diarybox.app.XFApplication;
import xiaofan.diarybox.bean.BookBean;
import xiaofan.diarybox.model.ArticleModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleListActivity extends Activity{
	
	private ListView articleList;
	private List<BookBean> articles;
	ArticleAdapter adapter;
	private String from;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_list);
		
		from = getIntent().getStringExtra("from");
		
		articleList = (ListView) findViewById(R.id.list_article);
		ArticleModel amodel  = new ArticleModel();
		articles = amodel.getArticles();
		adapter = new ArticleAdapter(this, articles);
		articleList.setAdapter(adapter);
		
		articleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				BookBean bb = adapter.getItem(position);
				XFApplication.getInstance().setNewArticle(bb);
				if("wel_page".equals(from)){
					Intent intent = new Intent(ArticleListActivity.this,AD8StoryActivity.class);
					ArticleListActivity.this.startActivity(intent);
					ArticleListActivity.this.finish();
				}else{
					ArticleListActivity.this.finish();
				}
				
			}
		});
	}


	
	
	
	
	
	private class ArticleAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private List<BookBean> articles;
		
		public ArticleAdapter(Context context,List<BookBean> articles){
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.articles = articles;
		}
		
		@Override
		public int getCount() {
			return articles.size();
		}

		@Override
		public BookBean getItem(int position) {
			return articles.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 10000L + position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.article_item, null);
				viewHolder = new ViewHolder();
				viewHolder.title = (TextView) convertView.findViewById(R.id.article_title);
				viewHolder.author = (TextView) convertView.findViewById(R.id.article_author);
				convertView.setTag(viewHolder);
				
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			if(position % 2 == 0){
				convertView.setBackgroundResource(R.drawable.book_list_item_odd_bg);
			}else{
				convertView.setBackgroundResource(R.drawable.book_list_item_even_bg);
			}
			viewHolder.title.setText(getItem(position).getTitile());
			viewHolder.author.setText(getItem(position).getAuthor());
			
			return convertView;
		}
		
	}
	
	public class ViewHolder{
		public TextView title;
		public TextView author;
	}
	
	

}




