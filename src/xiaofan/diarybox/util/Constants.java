package xiaofan.diarybox.util;

public class Constants {
	
      public static final String LOVE_LINKS = "http://www.douban.com/note/251924149/";
      
	  public static final int MARGIN_BOTTOM = 36;
	  public static final int MARGIN_LEFT = 18;
	  public static final int MARGIN_LEFT_NOTE = 26;
	  public static final int MARGIN_TOP = 40;
	  public static final int MARGIN_TOP_NOTE = 20;
	  public static final int MARGIN_TOP_SPACING = 4;
	  
	  public static int BRIGHTNESS_COUNT = 10;
	  

	  public static final int[] LINE_SPACING;
	  public static final int[] LINE_SPACING_LEGEND;
	  public static final int[] TEXTSIZE_TITLE;
	  public static final int[] TEXTSIZE_SUBTITLE;
	  public static final int[] TEXTSIZE_AUTHOR;
	  public static final int[] TEXTSIZE_NORMAL;
	  public static final int[] TEXTSIZE_HEADLINE;
	  public static final int[] TEXTSIZE_LEGEND;
	  public static float[] BRIGHTNESS_VALUE;
	  
	 static
	  {
	    int[] arrayOfInt1 = new int[4];
	    arrayOfInt1[0] = 18;
	    arrayOfInt1[1] = 20;
	    arrayOfInt1[2] = 22;
	    arrayOfInt1[3] = 24;
	    LINE_SPACING = arrayOfInt1;
	    int[] arrayOfInt2 = new int[4];
	    arrayOfInt2[0] = 8;
	    arrayOfInt2[1] = 8;
	    arrayOfInt2[2] = 8;
	    arrayOfInt2[3] = 8;
	    LINE_SPACING_LEGEND = arrayOfInt2;
	    int[] arrayOfInt3 = new int[4];
	    arrayOfInt3[0] = 28;
	    arrayOfInt3[1] = 28;
	    arrayOfInt3[2] = 28;
	    arrayOfInt3[3] = 28;
	    TEXTSIZE_TITLE = arrayOfInt3;
	    int[] arrayOfInt4 = new int[4];
	    arrayOfInt4[0] = 20;
	    arrayOfInt4[1] = 20;
	    arrayOfInt4[2] = 20;
	    arrayOfInt4[3] = 20;
	    TEXTSIZE_SUBTITLE = arrayOfInt4;
	    int[] arrayOfInt5 = new int[4];
	    arrayOfInt5[0] = 16;
	    arrayOfInt5[1] = 16;
	    arrayOfInt5[2] = 16;
	    arrayOfInt5[3] = 16;
	    TEXTSIZE_AUTHOR = arrayOfInt5;
	    int[] arrayOfInt6 = new int[4];
	    arrayOfInt6[0] = 16;
	    arrayOfInt6[1] = 18;
	    arrayOfInt6[2] = 20;
	    arrayOfInt6[3] = 22;
	    TEXTSIZE_NORMAL = arrayOfInt6;
	    int[] arrayOfInt7 = new int[4];
	    arrayOfInt7[0] = 20;
	    arrayOfInt7[1] = 22;
	    arrayOfInt7[2] = 24;
	    arrayOfInt7[3] = 26;
	    TEXTSIZE_HEADLINE = arrayOfInt7;
	    int[] arrayOfInt8 = new int[4];
	    arrayOfInt8[0] = 12;
	    arrayOfInt8[1] = 14;
	    arrayOfInt8[2] = 16;
	    arrayOfInt8[3] = 18;
	    TEXTSIZE_LEGEND = arrayOfInt8;
	    float[] arrayOfFloat = new float[10];
	    arrayOfFloat[0] = 0.1F;
	    arrayOfFloat[1] = 0.2F;
	    arrayOfFloat[2] = 0.3F;
	    arrayOfFloat[3] = 0.4F;
	    arrayOfFloat[4] = 0.5F;
	    arrayOfFloat[5] = 0.6F;
	    arrayOfFloat[6] = 0.7F;
	    arrayOfFloat[7] = 0.8F;
	    arrayOfFloat[8] = 0.9F;
	    arrayOfFloat[9] = 1.0F;
	    BRIGHTNESS_VALUE = arrayOfFloat;
	  }
	 
	  public static final class Theme
	  {
	    public static final int BLACK = 1;
	    public static final int WHITE = 0;
	  }
}
