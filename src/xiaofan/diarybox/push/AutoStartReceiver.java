package xiaofan.diarybox.push;

import xiaofan.diarybox.AD8StoryActivity;

import com.parse.Parse;
import com.parse.PushService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("==== on Receive=====");
		
		Parse.initialize(context, "yW5e739owjkcHfDaOC7NJBgJnTKWvKpj9GdNGiUY", "matrMMxHfUZmVWQXqL2zQ5M1IEcZkDaQ1yvBSCGK");
        PushService.subscribe(context, "box1", AD8StoryActivity.class);
	}

}
