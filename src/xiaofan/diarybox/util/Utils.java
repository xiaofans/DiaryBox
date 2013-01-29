package xiaofan.diarybox.util;

import android.content.Context;

public class Utils {

	public static int dp2pixel(Context paramContext, int paramInt){
	    return (int)(0.5F + paramContext.getResources().getDisplayMetrics().density * paramInt);
	  }
	
}
