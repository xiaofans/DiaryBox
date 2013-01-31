package xiaofan.diarybox.push;


import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import xiaofan.diarybox.R;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ParsePushReceiver extends BroadcastReceiver{

	private static final String TAG = "MyCustomReceiver";
	
	private NotificationManager mNotifyManager;
	private NotificationCompat.Builder mBuilder;
	
	private String doubanNodeId;
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println(TAG + "=onReceive=");
		
		try {
		      String action = intent.getAction();
		      String channel = intent.getExtras().getString("com.parse.Channel");
		      JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
		 
		      System.out.println(TAG+"got action " + action + " on channel " + channel + " with:");
//		      Iterator itr = json.keys();
//		      while (itr.hasNext()) {
//		        String key = (String) itr.next();
//		        System.out.println(TAG+"..." + key + " => " + json.getString(key));
//		      }
		      
		      doubanNodeId = json.getString("nodeId");
		    } catch (JSONException e) {
		    	System.out.println(TAG+"JSONException: " + e.getMessage());
		    }
	
	//------------------------------------------------------------------
		    mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		    mBuilder  = new NotificationCompat.Builder(context);
		    mBuilder.setContentTitle("豆瓣好日记")
		    .setContentText("更新日记....")
		    .setSmallIcon(R.drawable.ic_action_search);
		    
		    mBuilder.setProgress(0, 0, true);
		    mNotifyManager.notify(0, mBuilder.build());
		    new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mNotifyManager.cancel(0);
				}
		    }).start();
		    
		    
		   /* new Thread(
		    	    new Runnable() {
		    	        @Override
		    	        public void run() {
		    	            int incr;
		    	            // Do the "lengthy" operation 20 times
		    	            for (incr = 0; incr <= 20; incr+=5) {
		    	                    // Sets the progress indicator to a max value, the
		    	                    // current completion percentage, and "determinate"
		    	                    // state
		    	                    //mBuilder.setProgress(100, incr, false);
		    	            		mBuilder.setProgress(0, 0, true);
		    	                    // Displays the progress bar for the first time.
		    	                    mNotifyManager.notify(0, mBuilder.build());
		    	                        // Sleeps the thread, simulating an operation
		    	                        // that takes time
		    	                        try {
		    	                            // Sleep for 5 seconds
		    	                            Thread.sleep(5*1000);
		    	                        } catch (InterruptedException e) {
		    	                            Log.d(TAG, "sleep failure");
		    	                        }
		    	            }
		    	            // When the loop is finished, updates the notification
		    	           // mBuilder.setContentText("Download complete")
		    	            // Removes the progress bar
		    	                 //   .setProgress(0,0,false);
		    	           ///.notify(0, mBuilder.build());
		    	        }
		    	    }
		    	// Starts the thread by calling the run() method in its Runnable
		    	).start();*/
	}

}
