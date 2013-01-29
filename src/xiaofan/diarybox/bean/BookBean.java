package xiaofan.diarybox.bean;

import java.util.List;

public class BookBean {
	
	private int bookId;
	private String titile;
	private String author;
	private List<String> para;
	private String html;
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<String> getPara() {
		return para;
	}
	public void setPara(List<String> para) {
		this.para = para;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

}
