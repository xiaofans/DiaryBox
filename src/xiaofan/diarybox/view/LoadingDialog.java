package xiaofan.diarybox.view;

import xiaofan.diarybox.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class LoadingDialog extends Dialog{


	public LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading);
		
		TextView loadText = (TextView) findViewById(R.id.text_loading);
		loadText.setText("加载中...");
	}

	
	
	
}
