package xiaofan.diarybox.model;

import java.util.ArrayList;
import java.util.List;

import xiaofan.diarybox.bean.BookBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaDB {
	
	private static final String DATABASE_NAME = "da.db";
	private static final int DATABASE_VERSION = 1;
	private DatabaseHelper mDbHelper;
	
	public DaDB(Context context){
		this.mDbHelper = new DatabaseHelper(context);
	}
	
	public void addArticle(String title,String author,String html){
		
		SQLiteDatabase database = mDbHelper.getWritableDatabase();
		String sql = "INSERT INTO article_info(author,title,html) VALUES(?,?,?)";
//		Cursor cursor = database.rawQuery(sql, new String[]{author,title,html});
//		cursor.getCount();
//		cursor.close();
		database.execSQL(sql, new String[]{author,title,html});
	}
	
	
	public List<BookBean> getBookInfo(){
		List<BookBean> bookList = new ArrayList<BookBean>();
		String sql = "select * from article_info";
		SQLiteDatabase database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			while(cursor.moveToNext()){
				BookBean bookBean = new BookBean();
				bookBean.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
				bookBean.setBookId(cursor.getInt(cursor.getColumnIndex("_id")));
				bookBean.setHtml(cursor.getString(cursor.getColumnIndex("html")));
				bookBean.setTitile(cursor.getString(cursor.getColumnIndex("title")));
				bookList.add(bookBean);
			}
		}
		return bookList;
	}
	
	
	public boolean deleteArticle(int id){
		
		return true;
	}
	


	public void close()
	{
	    this.mDbHelper.close();
	}
	

	private class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE article_info(_id INTEGER PRIMARY KEY AUTOINCREMENT,author TEXT,title TEXT,html TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE article_info IF EXISTS");
		}
		
	}

}


