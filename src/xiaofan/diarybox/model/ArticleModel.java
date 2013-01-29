package xiaofan.diarybox.model;

import java.util.List;

import xiaofan.diarybox.app.XFApplication;
import xiaofan.diarybox.bean.BookBean;


public class ArticleModel {
	private DaDB daDb;
	
	
	public ArticleModel() {
		daDb = XFApplication.getInstance().getDaDB();
	}
	

	
	public void saveArtile(){
		String title = XFApplication.getInstance().getTitle();
		String author = XFApplication.getInstance().getAuthor();
//		String html = XFApplication.getInstance().getHtml();
		List<String> para = XFApplication.getInstance().getPara();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < para.size(); i++){
			sb.append(para.get(i));
			if(i+1 < para.size()){
				sb.append("xiaofan");
			}
		}
		daDb.addArticle(title, author, sb.toString());
	}
	
	
	public List<BookBean> getArticles(){
		return daDb.getBookInfo();
	}

}
