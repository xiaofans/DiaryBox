package xiaofan.diarybox.model;

import java.util.ArrayList;
import java.util.List;

import xiaofan.diarybox.app.XFApplication;
import xiaofan.diarybox.bean.XFPage;
import xiaofan.diarybox.bean.XFTextLineInfo;


public class XLModel {
	private List<String> paras;
	public static final int PARA_SPACE = 2;
	//public static  int TEXT_SIZE = 30;
	//public static  int LINE_SPACE = 25;
	private static List<Integer> listParaCount;
	private static List<XFTextLineInfo> totalList;
	private static List<XFPage> pageInfo;
	
	public static List<XFPage> getPageInfo() {
		return pageInfo;
	}

	public XLModel(){
		
		paras = XFApplication.getInstance().getPara();
	}
	
	public List<XFTextLineInfo> getTotalList() {
		return totalList;
	}

	public void setTotalList(List<XFTextLineInfo> totalList) {
		this.totalList = totalList;
	}

	// ���ÿ��ʵ����ռ����
	public void paraRealTakeWordCount(int wordsCountEachLine){
		listParaCount = new ArrayList<Integer>();

		for(int i = 0; i < paras.size(); i++){
			
			String paraContent = paras.get(i);
			int paraCount = paraContent.toCharArray().length +PARA_SPACE;
			int lineLeft = paraCount % wordsCountEachLine;
			if(lineLeft  == 0){				
				listParaCount.add(paraCount);
			}else{
				listParaCount.add(paraCount - lineLeft + wordsCountEachLine);
			}
			
		}
	}
	
	public List<Integer> paraRealTakeLine(int wordsCountEachLine){
		List<Integer> listParaLines = new ArrayList<Integer>();
		for(int i = 0; i < paras.size(); i++){
			String paraContent = String.valueOf(format(paras.get(i)));
			int paraCount = paraContent.toCharArray().length +PARA_SPACE;
			int lineLeft = paraCount % wordsCountEachLine;
			if(lineLeft  == 0){				
				listParaLines.add(paraCount / wordsCountEachLine);
			}else{
				listParaLines.add(paraCount / wordsCountEachLine +1);
			}
			
		}
		return listParaLines;
	}
	
	 //����������
	public int calculateTotalWordCount(int wordsCountEachLine){
		int count = 0;
		for(int i = 0; i < paras.size(); i++){
			String paraContent = paras.get(i);
			int paraCount = paraContent.toCharArray().length +PARA_SPACE;
			int lineLeft = paraCount % wordsCountEachLine;
			if(lineLeft  == 0){				
				count += paraCount;
			}else{
				count += paraCount - lineLeft + wordsCountEachLine;
			}
			
		}
		
		return count;
	}
	
	
	
	//������ҳ��
	public int calculatePage(int textAreaWidth,int textAreaHeight,int textSize , int lineSpace){
		
		int wordsCountEachLine = textAreaWidth / textSize;
		int lines = textAreaHeight / (textSize + lineSpace);	
		
		int wordsCountOnePage = wordsCountEachLine * lines;
		int totalWordCount = calculateTotalWordCount(wordsCountEachLine);
		
		if(totalWordCount % wordsCountOnePage == 0){
			return  totalWordCount / wordsCountOnePage;
		}else{
			return  totalWordCount / wordsCountOnePage +1;
		}
	}
	
	
	
	/**
	 * 
	 * @param wordsCountEachLine
	 * @return
	 * ����ÿҳ�������ɶ�����
	 */
	public int calculateLineCountOnePage(int textAreaHeight,int lineSpace,int textSize){
		return textAreaHeight / textSize + lineSpace;		
	}
	
	// ����һ���ж���ҳ
	public int calculateTotalPages(int lines,int totalLines){
		int left = totalLines % lines;
		if(left == 0){
			return totalLines / lines;
		}else{
			return totalLines / lines + 1;
		}
	}
	
	
	// ����ÿҳ����Ϣ
	public void buildPages(int textAreaHeight,int lineSpace,int textSize){
		int totalLines = totalList.size();
		int lines = textAreaHeight / (textSize + lineSpace);
		int pages =  totalLines % lines == 0  ? totalLines / lines : totalLines / lines + 1;
		pageInfo = new ArrayList<XFPage>();
		for(int x = 0; x < pages; x++){
			XFPage xfp = new XFPage();
			//if((x +1)*lines > totalList.size()){
			if(x+1 == pages){	
				for(int i = x*lines; i < totalList.size(); i++){
					xfp.getLineInfo().add(totalList.get(i));
				}
			}else{
				for(int i = x*lines ; i < (x + 1)*lines;i++){		
					xfp.getLineInfo().add(totalList.get(i));
				}
			}
					
			pageInfo.add(xfp);
		}
		
	}
	
	
	// �����е���Ϣ
	public void calculateLineInfos(int wordsCountEachLine){
		List<XFTextLineInfo> lineInfos = new ArrayList<XFTextLineInfo>();
		List<Integer> listParaLines = paraRealTakeLine(wordsCountEachLine);
		for(int i = 0; i < paras.size(); i++){
			String paraContent = String.valueOf(format(paras.get(i)));
			XFTextLineInfo xt = new XFTextLineInfo();
			xt.setParaStart(true);
			if(wordsCountEachLine - 2 > paraContent.toCharArray().length){
			//	xt.setLineWords(paraContent.substring(0, paraContent.toCharArray().length).toCharArray());
				xt.setLineWords(paraContent.substring(0, paraContent.toCharArray().length));
			}else{
				//xt.setLineWords(paraContent.substring(0, wordsCountEachLine - 2).toCharArray());
				xt.setLineWords(paraContent.substring(0, wordsCountEachLine - 2));
			} 
			lineInfos.add(xt);
//			for(int z = 0 ; z < xt.getLineWords().length ; z++){
//				System.out.println(z+":"+xt.getLineWords()[z]);
//			}
			for(int j = 1; j<listParaLines.get(i);j++){
				XFTextLineInfo xtli = new XFTextLineInfo();
				xtli.setParaStart(false);
				if(j+1 == listParaLines.get(i)){
					xtli.setLineWords(paraContent.substring(j*wordsCountEachLine - 2, paraContent.toCharArray().length));
				}else{
					String content = paraContent;
					int startIndex = j*wordsCountEachLine - 2;
					int endIndex = (j+1)*wordsCountEachLine - 2;
					String lines = content.substring(startIndex, endIndex);
					xtli.setLineWords(lines);
				}
				
				lineInfos.add(xtli);
			}
		}
		totalList = lineInfos;
	}
	
	
	
	/**
	 * 
	 * @param word
	 * @return
	 * ��ʽ���ַ����пո�������
	 */
	private char[] format(String word) {
		char [] x = word.toCharArray();
		char [] theNew;
		List<Character> sort = new ArrayList<Character>();
		for(int i =0 ; i< x.length;i++){
			if(x[i] == (char)0 ||x[i] == (char)32 ){
				System.out.println("index is:"+i);
			}else{
				sort.add(x[i]);
			}
		}
		theNew = new char[sort.size()];
		for(int i = 0;i<sort.size();i++){
			theNew[i] =  sort.get(i);
		}
	
		return theNew;
	}

}
