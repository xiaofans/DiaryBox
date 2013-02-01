package xiaofan.diarybox.provider;

import xiaofan.diarybox.model.DaDB;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import static xiaofan.diarybox.util.Constants.*;


public class DiaryBoxProvider extends ContentProvider{
	
	private static final int DIARYES = 1;
	private static final int DIARY_ID = 2;
	
	private DaDB daDB;
	private UriMatcher uriMatcher;
	
	private static final String CONTENT_TYPE= "vnd.android.cursor.dir/vnd.xiaofan.diarybox";
	
	private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xiaofan.diarybox";
	 
	@Override
	public boolean onCreate() {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "article_info", DIARYES);
		uriMatcher.addURI(AUTHORITY, "article_info/#", DIARY_ID);
		
		daDB = new DaDB(getContext());
		
		return true;
	
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)){
		case DIARYES:
			return CONTENT_TYPE;
		case DIARY_ID:
			return CONTENT_ITEM_TYPE;
		 default:
	         throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if(uriMatcher.match(uri) != DIARYES){
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		SQLiteDatabase database = daDB.getMyWriteableDatabase();
		long id = database.insertOrThrow(TABLE_NAME, null, values);
		// Notify any watchers of the change
	    Uri newUri = ContentUris.withAppendedId(CONTENT_URI, id);
	    getContext().getContentResolver().notifyChange(newUri, null);
	    
		return newUri;
	}

	

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
		return 0;
	}
	
}
