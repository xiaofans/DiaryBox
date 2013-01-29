package xiaofan.diarybox.view;


import java.util.List;
import java.util.regex.Pattern;

import xiaofan.diarybox.R;
import xiaofan.diarybox.app.XFApplication;
import xiaofan.diarybox.bean.XFTextLineInfo;
import xiaofan.diarybox.model.XLModel;
import xiaofan.diarybox.util.Constants;
import xiaofan.diarybox.util.Utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class XFView extends View{
	
	private List<XFTextLineInfo> linesInfo;
	
	private static Bitmap sBackgroundBitmapBlack = null;
	private static Bitmap sBackgroundBitmapWhite = null;
	
	public static int sTextColorBlack = Color.rgb(57, 56, 49);
	public static int sTextColorWhite = Color.rgb(204, 204, 204);
	  
	 
	private Context context;
	private int position;
	
    int mHeaderFooterColor;
	 
	 private static Pattern sChinesePattern = Pattern.compile("[\\u4e00-\\u9fa5]");
	 private static Pattern sEnglishPattern = Pattern.compile("\\p{Lu}|\\p{Ll}|\\p{Lt}|\\p{Lm}|\\p{Nd}|\\p{Nl}|\\p{No}");
	 

	private Canvas mCanvas;
	//private Bitmap mBitmap;
	Paint mPaint = new Paint();
	
	public XFView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		initColors();
		
        
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		this.mCanvas = canvas;
//		if(mBitmap != null)
//            canvas.drawBitmap(mBitmap, 0F, 0F, mPaint);
		
		drawPage(canvas);
	}
	


	  private void drawPage(Canvas paramCanvas)
	  {
		linesInfo = XLModel.getPageInfo().get(position).getLineInfo();
		int mPageWidth = XFApplication.getInstance().getPageWidth();
		int mPageHeight = XFApplication.getInstance().getPageHeight();
		
	    BitmapDrawable localBitmapDrawable = getBackgroundDrawable(XFApplication.getInstance().getColorTheme());
	    if (localBitmapDrawable != null)
	      localBitmapDrawable.draw(paramCanvas);
	    //while (true)
	   // {
	      int i = Utils.dp2pixel(context, 40);
	      int j = Utils.dp2pixel(context, 4);
	      int k = Utils.dp2pixel(context, 36);
	      int m = Utils.dp2pixel(context, Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()]);
//	      int m = Utils.dp2pixel(context, 22);
	      int n = Utils.dp2pixel(context, 18);
	      int i1 = mPageWidth - n * 2;
	      int i2 = i1 - i1 % m;
	      int i3 = (mPageWidth - i2) / 2;
	      Paint localPaint = new Paint();
	      localPaint.setTypeface(XFApplication.getInstance().getTypeface());
	      localPaint.setAntiAlias(true);
	      localPaint.setSubpixelText(true);
	     
	      try
	      {
	       //localPaint.setHinting(1);
	       // localPaint.setTypeface(this.mApp.getSansSerifTypeface());
	    	localPaint.setColor(mHeaderFooterColor);
	        int i4 = Utils.dp2pixel(context, 14);
	        localPaint.setTextSize(i4);
	        int i5 = (i - i4) / 2 - localPaint.getFontMetricsInt().top;
	        paramCanvas.drawText(XFApplication.getInstance().getTitle(), i3, i5, localPaint);
	        int i6 = i - j;
	        paramCanvas.drawLine(i3, i6, mPageWidth - i3, i6, localPaint);
	        if(XFApplication.getInstance().getColorTheme() ==0){
	        	localPaint.setColor(0xff3a3931);
	        }else{
	        	localPaint.setColor(mHeaderFooterColor);
	        }
	        localPaint.setTextSize(Utils.dp2pixel(context, Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()]));
	        int i9 = Utils.dp2pixel(context, 60);
	        for(int l = 0; l < linesInfo.size(); l++){
				String lineWords = linesInfo.get(l).getLineWords();
	        	char[] lineText = linesInfo.get(l).getLineWords().toCharArray();
	        	if(linesInfo.get(l).isEndPunctuation() && linesInfo.get(l).isFullWidthEndPunctuations()){
					StringBuilder sb = new StringBuilder();
					sb.append(lineWords.substring(0, lineWords.length()-1));
					sb.append(" ");
					sb.append(lineWords.substring(lineWords.length()-1, lineWords.length()));
					lineText = sb.toString().toCharArray();
	        	}
	        	if(linesInfo.get(l).isStartPunctuation() && linesInfo.get(l).isFullStartPunctuations()){
	        		StringBuilder sb = new StringBuilder();
	        		sb.append(lineWords.charAt(0));
	        		sb.append(" ");
	        		sb.append(lineWords.substring(1, lineWords.length()));
	        	}
				if(linesInfo.get(l).isParaStart()){
					 paramCanvas.drawText(lineText, 0, lineText.length, i3 + Utils.dp2pixel(context, Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()] * 2), i9 + l*(Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()] + Constants.LINE_SPACING[XFApplication.getInstance().getScale()]), localPaint);
				}else{
					if(linesInfo.get(l).isStartPunctuation() && linesInfo.get(l).isFullStartPunctuations()){
						paramCanvas.drawText(lineText, 0, lineText.length, i3 - Utils.dp2pixel(context, Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()])/2, i9 + l*(Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()] + Constants.LINE_SPACING[XFApplication.getInstance().getScale()]), localPaint);
					}else{
						paramCanvas.drawText(lineText, 0, lineText.length, i3, i9 + l*(Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()] + Constants.LINE_SPACING[XFApplication.getInstance().getScale()]), localPaint);
						
					}
				}
			}
	        
	        int i7 = Utils.dp2pixel(context, 14);
	        int i8 = mPageHeight - (k - i7) / 2 - localPaint.getFontMetricsInt().bottom;
	        localPaint.setTextSize(12);
	        paramCanvas.drawText("douban.com", i3, i8, localPaint);
	        
	        localPaint.setTypeface(Typeface.SERIF);
	        localPaint.setTextAlign(Paint.Align.CENTER);
	        localPaint.setTextSize(i7);
	        localPaint.setFakeBoldText(true);
	        localPaint.setColor(mHeaderFooterColor);
	        paramCanvas.drawText(Integer.toString(1 + position), mPageWidth / 2, i8, localPaint);
	        
	       
	      }
	      catch (NoSuchMethodError localNoSuchMethodError)
	      {
	      }
	      
	      //localPaint.setTextSize(10);
	     // paramCanvas.drawText("douban.com", i3, Utils.dp2pixel(context, 44), localPaint);
	  }

	  public void redraw(){
		  initColors();
		 // linesInfo = XLModel.getPageInfo().get(position).getLineInfo();
		  drawPage(mCanvas);
		  invalidate();
		  Log.i("XFVIEW", "redraw....");   
	  }
	  

	private BitmapDrawable getBackgroundDrawable(int paramInt) {
		int mPageWidth = XFApplication.getInstance().getPageWidth();
		int mPageHeight = XFApplication.getInstance().getPageHeight();
		   BitmapDrawable localBitmapDrawable = null;
		   Bitmap localBitmap = null;
		    if (paramInt == 1){
		    	 if (sBackgroundBitmapBlack == null)
				        sBackgroundBitmapBlack = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_black);
		    	 localBitmap = sBackgroundBitmapBlack;
		    }else{
		    	if (sBackgroundBitmapWhite == null)
			        sBackgroundBitmapWhite = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_white);
		    	    localBitmap = sBackgroundBitmapWhite;
		    }
		     
		      if (localBitmap != null)
		      {
		        localBitmapDrawable = new BitmapDrawable(context.getResources(), localBitmap);
		        localBitmapDrawable.setBounds(new Rect(0, 0, mPageWidth,mPageHeight));
		        localBitmapDrawable.setTileModeX(android.graphics.Shader.TileMode.REPEAT);
		        localBitmapDrawable.setTileModeY(android.graphics.Shader.TileMode.REPEAT);
		      }
		      return localBitmapDrawable;
	}




	public void setPosition(int index) {
		position = index;
	}
	
	  private void initColors()
	    {
	        if(XFApplication.getInstance().getColorTheme() == 1)
	            mHeaderFooterColor = 0xffcccccc;
	        else
	            mHeaderFooterColor = 0xff888888;
	    }
	
	

}
