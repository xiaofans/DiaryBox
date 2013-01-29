package xiaofan.diarybox;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import xiaofan.diarybox.R;

public class About extends Activity implements OnClickListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setUpViews();
		
	}


	private void setUpViews() {

		Button okButton = (Button) findViewById(R.id.okButton);
		okButton.setOnClickListener(this);
		
		TextView url = (TextView) findViewById(R.id.project_url);
		url.setOnClickListener(this);
		
		
	}

	public void onClick(View view) {
		
		switch(view.getId()){
		
			case R.id.okButton:
				finish();
				break;
			case R.id.project_url:
				jumpThere();
				break;
		
		}
		
	}

	private void jumpThere() {
		    Uri uri = Uri.parse(getString(R.string.about_url));
	        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	        startActivity(intent);
	}
	

}

