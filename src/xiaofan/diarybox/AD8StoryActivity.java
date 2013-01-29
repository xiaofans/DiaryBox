package xiaofan.diarybox;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import xiaofan.diarybox.R;
import xiaofan.diarybox.adapter.NViewPagerAdapter;
import xiaofan.diarybox.app.XFApplication;
import xiaofan.diarybox.model.ArticleModel;
import xiaofan.diarybox.model.XLModel;
import xiaofan.diarybox.util.AndroidUtils;
import xiaofan.diarybox.util.Constants;
import xiaofan.diarybox.util.Utils;
import xiaofan.diarybox.view.LoadingDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;




public class AD8StoryActivity extends FragmentActivity {

	private ViewPager nViewPager;
	private LinearLayout mSettingPanel;
	private FrameLayout mParentFrame;
	
	private TextView mTvTimeBookSetting;
	private TextView mTvBookBattery;
	private Button mBtnTheme;
	
	private Button mBtnBrightnessMinus;
    private Button mBtnBrightnessPlus;
    
    private LinearLayout mLayoutBottomBar;
    private LinearLayout mLayoutTopBar;
    
    private Button mBtnTextSizeMinus;
    private Button mBtnTextSizePlus;
    
    private SeekBar mSeekBarBlack;
    private SeekBar mSeekBarWhite;
    
    private XFApplication app;
    
    private ImageView refreshArtileIv;
    
    private NViewPagerAdapter nvpa;
    private int mBrightnessLevel = -1;
    
    
    ImageView mIvSeekbarLeftEdge;
    ImageView mIvSeekbarRightEdge;
    
    ImageView info;
    private Button salonBtn;
    
    private TextView seekProgress;
    
    private int mBrightnessMode;
    
    private ImageView articleRefresh;
	
	
	private BroadcastReceiver timeAndBatteryReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if("android.intent.action.TIME_TICK".equals(action)){
				updateTime();
			}
			
			if("android.intent.action.BATTERY_CHANGED".equals(action)){
				int i = intent.getIntExtra("level", 0);
				int j = intent.getIntExtra("scale", 100);
				updateBattery((i * 100) / j);
			}
			
		}
		
	};
	

	private Handler updateHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 1:
				Calendar calendar = Calendar.getInstance();
			    String s1 = (new SimpleDateFormat("HH:mm")).format(calendar.getTime());
			    mTvTimeBookSetting.setText(s1);
				break;
			case 2:
				int mBatteryLevel = msg.arg1;
			    String s = (new StringBuilder()).append("").append(mBatteryLevel).append("%").toString();
				mTvBookBattery.setText(s);
				setBatteryIcon(mTvBookBattery, mBatteryLevel);
				break;
			}
		}
		
		
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        app = (XFApplication) getApplication();
        initBrightnessLevel();
        setUpViews();
        
        if(XFApplication.getInstance().getColorTheme() == 1){
			setBlackMode();
		}else{
			setWhiteMode();
		}
        
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.TIME_TICK");
        intentfilter.addAction("android.intent.action.BATTERY_CHANGED");
        
        registerReceiver(timeAndBatteryReceiver, intentfilter);
        
        
    }
    @Override
    protected void onResume() {
    	super.onResume();
    	nvpa = new NViewPagerAdapter(getSupportFragmentManager(),XLModel.getPageInfo());
		nViewPager.setAdapter(nvpa);
		mSeekBarWhite.setMax(XLModel.getPageInfo().size());
		mSeekBarBlack.setMax(XLModel.getPageInfo().size());
		
	      
	       
		// mBrightnessMode = android.provider.Settings.System.getInt(getContentResolver(), "screen_brightness_mode", 1);
		 mBrightnessMode = android.provider.Settings.System.getInt(getContentResolver(), "screen_brightness_mode", 1);
	     android.provider.Settings.System.putInt(getContentResolver(), "screen_brightness_mode", 0);
    }
    
    
    @Override
    protected void onStart() {
    	super.onStart();
    }
    protected void setBatteryIcon(TextView textView,int mBatteryLevel) {
		if(mBatteryLevel == 0){
			textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_statusbar_battery_0, 0, 0, 0);
		}else if(0 < mBatteryLevel && mBatteryLevel <= 25){
			textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_statusbar_battery_1, 0, 0, 0);
		}else if(25 < mBatteryLevel && mBatteryLevel <= 50){
			textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_statusbar_battery_2, 0, 0, 0);
		}else if(50 < mBatteryLevel && mBatteryLevel <= 75){
			textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_statusbar_battery_3, 0, 0, 0);
		}else if(75 < mBatteryLevel && mBatteryLevel < 100){
			textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_statusbar_battery_4, 0, 0, 0);
		}else if(mBatteryLevel == 100){
			textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_statusbar_battery_5, 0, 0, 0);
		}
	}

	private void updateBattery(int i)
    {
        Message message = updateHandler.obtainMessage();
        message.what = 2;
        message.arg1 = i;
        updateHandler.sendMessage(message);
    }

    private void updateTime()
    {
        Message message = updateHandler.obtainMessage();
        message.what = 1;
        updateHandler.sendMessage(message);
    }


	private void setUpViews() {
		nViewPager = (ViewPager) findViewById(R.id.nViewPager);
		//nViewPager.setCurrentItem(XFApplication.getInstance().getLastRedePageIndex());
		
		nViewPager.setOnTouchListener(new OnTouchListener() {
			int x=0,y=0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					x = (int)event.getX();
					y = (int)event.getY();
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					int end_x = Math.abs(x - (int)event.getX());
					int end_y = Math.abs(y - (int)event.getY());
					
					if(end_x <= 40 &&  end_y <= 40){
						if(mSettingPanel.getVisibility() == View.GONE){
							//mSettingPanel.setVisibility(View.VISIBLE);
							showPanel();
						}else{
							mSettingPanel.setVisibility(View.GONE);
						}
					}
				}
				return false;
			}
		});
		
		nViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				mSeekBarWhite.setProgress(position +1);
				mSeekBarBlack.setProgress(position +1);
				seekProgress.setText((position+1) * 100 / XLModel.getPageInfo().size() + "%");
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				if(state == ViewPager.SCROLL_STATE_DRAGGING){
					if(mSettingPanel.getVisibility() == View.VISIBLE){					
						mSettingPanel.startAnimation(AnimationUtils.loadAnimation(XFApplication.getInstance(), R.anim.fade_out));
						mSettingPanel.setVisibility(View.GONE);
					}
				}
			}
		});
	
		
		mParentFrame = (FrameLayout) findViewById(R.id.mainParentFrame);
		mSettingPanel = (LinearLayout) getLayoutInflater().inflate(R.layout.panel_setting, null);
		//mSettingPanel = (LinearLayout) getLayoutInflater().inflate(R.layout.panel_setting_black, null);
		mParentFrame.addView(mSettingPanel);
		
		mTvTimeBookSetting = (TextView) mParentFrame.findViewById(R.id.text_time);
		mTvBookBattery = (TextView) mParentFrame.findViewById(R.id.text_battery);
		
		mBtnTheme = (Button) mParentFrame.findViewById(R.id.colortheme);
		mBtnTheme.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(XFApplication.getInstance().getColorTheme() == 0){
					XFApplication.getInstance().setColorTheme(1);
					nvpa.notifyDataSetChanged();
					setBlackMode();
				}else{
					XFApplication.getInstance().setColorTheme(0);
					nvpa.notifyDataSetChanged();
					setWhiteMode();
				}
				
			}
		});
		mSettingPanel.setVisibility(View.GONE);
		
		mBtnBrightnessMinus = (Button) mParentFrame.findViewById(R.id.light_minus);
		mBtnBrightnessMinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if(mBrightnessLevel > 0)
	                {
	                    int l = -1 +  AD8StoryActivity.this.mBrightnessLevel;
	                    AD8StoryActivity.this.mBrightnessLevel = l;
	                    AD8StoryActivity.this.setBrightnessLevel(l);
	                }
	                if(mBrightnessLevel == 0)
	                    mBtnBrightnessMinus.setEnabled(false);
	                mBtnBrightnessPlus.setEnabled(true);
	                
				
			
			}
		});
		mBtnBrightnessPlus = (Button) mParentFrame.findViewById(R.id.light_plus);
		mBtnBrightnessPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                {
                    int l = 1 + AD8StoryActivity.this.mBrightnessLevel;
                    AD8StoryActivity.this.mBrightnessLevel = l;
                    AD8StoryActivity.this.setBrightnessLevel(l);
                    if(mBrightnessLevel == -1 + Constants.BRIGHTNESS_COUNT)
                        mBtnBrightnessPlus.setEnabled(false);
                    mBtnBrightnessMinus.setEnabled(true);
                }
				
			
			}
		});
		
		if(mBrightnessLevel == 0)
            mBtnBrightnessMinus.setEnabled(false);
        if(mBrightnessLevel == -1 + Constants.BRIGHTNESS_COUNT)
            mBtnBrightnessPlus.setEnabled(false);
        
		mLayoutBottomBar = (LinearLayout) mParentFrame.findViewById(R.id.linearLayout_reading_bottom_bar);
		mLayoutBottomBar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		mLayoutTopBar = (LinearLayout) mParentFrame.findViewById(R.id.linearLayout_reading_top_bar);
		mLayoutTopBar.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				
			}
			
		});
		
		mBtnTextSizeMinus = (Button) mParentFrame.findViewById(R.id.size_minus);
		mBtnTextSizeMinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int procress = nvpa.getCurrentPosition() * 100 / XLModel.getPageInfo().size();
				app.setScale(app.getScale() -1);
				app.buildNovelData(getEachLineCount(), XFApplication.getInstance().getTextAreaHeight());
				nvpa.setPages(XLModel.getPageInfo());
				nvpa.notifyDataSetChanged();
				int newPosition = procress * XLModel.getPageInfo().size() /100;
				nViewPager.setCurrentItem(newPosition, false);
				
				if(app.getScale() == 0){
				  mBtnTextSizeMinus.setEnabled(false);
				}
				mBtnTextSizePlus.setEnabled(true);
				
			}
		});
		
		mBtnTextSizePlus = (Button) mParentFrame.findViewById(R.id.size_plus);
		mBtnTextSizePlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int procress = nvpa.getCurrentPosition() * 100 / XLModel.getPageInfo().size();
				app.setScale(app.getScale() + 1);
				app.buildNovelData(getEachLineCount(), XFApplication.getInstance().getTextAreaHeight());
				nvpa.setPages(XLModel.getPageInfo());
				nvpa.notifyDataSetChanged();
				int newPosition = procress * XLModel.getPageInfo().size() /100;
				nViewPager.setCurrentItem(newPosition, false);
		
				if(app.getScale() == 3){
				  mBtnTextSizePlus.setEnabled(false);
				}
				mBtnTextSizeMinus.setEnabled(true);
				mSeekBarWhite.setMax(XLModel.getPageInfo().size());
				mSeekBarBlack.setMax(XLModel.getPageInfo().size());
			}
		});
		
		//mSeekBarBlack = (SeekBar) mParentFrame.findViewById(R.id.seekbar_black);
		//mSeekBarWhite = (SeekBar) mParentFrame.findViewById(R.id.seekbar_white);
		refreshArtileIv = (ImageView) findViewById(R.id.refresh_article);
		refreshArtileIv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				//loadRandomArtile();
				Intent intent = new Intent(AD8StoryActivity.this,ArticleListActivity.class);
				AD8StoryActivity.this.startActivity(intent);
			}
		});
		
		info = (ImageView) findViewById(R.id.info);
		info.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAbout();
				
			}
		});
	
		salonBtn = (Button) findViewById(R.id.salon);
		salonBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArticleModel artModel = new ArticleModel();
				artModel.saveArtile();
				Toast.makeText(AD8StoryActivity.this, "文章已保存!", Toast.LENGTH_SHORT).show();
			}
		});
		
		seekProgress = (TextView) findViewById(R.id.progress);
		seekProgress.setText(100 / XLModel.getPageInfo().size() + "%");
		
		mSeekBarWhite = (SeekBar) findViewById(R.id.seekbar_white);
		mSeekBarWhite.setMax(XLModel.getPageInfo().size());
		mSeekBarWhite.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				seekProgress.setText((seekBar.getProgress() + 1) * 100 / XLModel.getPageInfo().size() + "%");
				nViewPager.setCurrentItem(seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
			}
		});
		mSeekBarBlack = (SeekBar) findViewById(R.id.seekbar_black);
		mSeekBarBlack.setMax(XLModel.getPageInfo().size());
		mSeekBarBlack.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				seekProgress.setText((seekBar.getProgress() + 1) * 100 / XLModel.getPageInfo().size() + "%");
				nViewPager.setCurrentItem(seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
			}
		});
		
		mIvSeekbarLeftEdge = (ImageView) findViewById(R.id.seekbar_leftedge);
		mIvSeekbarRightEdge = (ImageView) findViewById(R.id.seekbar_rightedge);
		
		articleRefresh = (ImageView) findViewById(R.id.sync);
		articleRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(AndroidUtils.isNetworkConnected(AD8StoryActivity.this)){					
					loadRandomArtile();
				}else{
					Toast.makeText(AD8StoryActivity.this, "请检查网络连接!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	

	protected void setWhiteMode() {
		//nViewPager.setBackgroundResource(R.drawable.overlay_tip_normal);
		info.setImageResource(R.drawable.iconwhite_info);
		refreshArtileIv.setImageResource(R.drawable.iconwhite_content);
		salonBtn.setBackgroundResource(R.drawable.btnwhite_gosalon);
		salonBtn.setTextColor(Color.rgb(77, 75, 72));
		mLayoutTopBar.setBackgroundResource(R.drawable.repeat_bg);
		mLayoutBottomBar.setBackgroundResource(R.drawable.repeat_bg);
		mIvSeekbarLeftEdge.setImageResource(R.drawable.seekbar_white_leftedge);
	    mIvSeekbarRightEdge.setImageResource(R.drawable.seekbar_white_rightedge);
	    mSeekBarWhite.setVisibility(View.VISIBLE);
	    mSeekBarBlack.setVisibility(View.GONE);
	    mBtnBrightnessPlus.setBackgroundResource(R.drawable.btnwhite_bright_up_bg);
	    mBtnBrightnessMinus.setBackgroundResource(R.drawable.btnwhite_bright_down_bg);
	    mBtnTextSizePlus.setBackgroundResource(R.drawable.btnwhite_textsize_up_bg);
	    mBtnTextSizeMinus.setBackgroundResource(R.drawable.btnwhite_textsize_down_bg);
	    mBtnTheme.setBackgroundResource(R.drawable.btnwhite_night_bg);
	    
	    articleRefresh.setImageResource(R.drawable.iconwhite_refresh);
	}

	protected void setBlackMode() {
		//nViewPager.setBackgroundResource(R.drawable.overlay_tip_normal);
		//mBtnTheme.setBackgroundResource(R.drawable.btnwhite_gosalon);
		info.setImageResource(R.drawable.iconblack_info);
		refreshArtileIv.setImageResource(R.drawable.iconblack_content);
		salonBtn.setBackgroundResource(R.drawable.btnblack_gosalon);
		salonBtn.setTextColor(Color.rgb(153, 153, 153));
		mLayoutTopBar.setBackgroundResource(R.drawable.repeat_black_bg);
		mLayoutBottomBar.setBackgroundResource(R.drawable.repeat_black_bg);
		mIvSeekbarLeftEdge.setImageResource(R.drawable.seekbar_black_leftedge);
	    mIvSeekbarRightEdge.setImageResource(R.drawable.seekbar_black_rightedge);
	    mSeekBarWhite.setVisibility(View.GONE);
	    mSeekBarBlack.setVisibility(View.VISIBLE);
	    mBtnBrightnessPlus.setBackgroundResource(R.drawable.btnblack_bright_up_bg);
	    mBtnBrightnessMinus.setBackgroundResource(R.drawable.btnblack_bright_down_bg);
	    mBtnTextSizePlus.setBackgroundResource(R.drawable.btnblack_textsize_up_bg);
	    mBtnTextSizeMinus.setBackgroundResource(R.drawable.btnblack_textsize_down_bg);
	    mBtnTheme.setBackgroundResource(R.drawable.btnblack_day_bg);
	    
	    articleRefresh.setImageResource(R.drawable.iconblack_refresh);
	}
	
	

	protected void showPanel() {
		updateTime();
		updateBatteryLevel();
		mSettingPanel.setVisibility(View.VISIBLE);
	}

	private void updateBatteryLevel() {
		
	}


	@Override
	protected void onPause() {
		super.onPause();
		int index = ((NViewPagerAdapter)nViewPager.getAdapter()).getCurrentPosition();
		XFApplication.getInstance().setLastRedePageIndex(index);
		
		android.provider.Settings.System.putInt(getContentResolver(), "screen_brightness_mode", mBrightnessMode);
	}


	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(timeAndBatteryReceiver);
	}
	

	private void showAbout() {
		Intent intent = new Intent(this,About.class);
		startActivity(intent);
	}
		
	public void loadRandomArtile(){
		final LoadingDialog ld = new LoadingDialog(this,R.style.LoadingDialogTheme);
		ld.setCancelable(false);
		ld.show();
		Random random = new Random();
		List<String> links = XFApplication.getInstance().getLinkList();
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.83 Safari/537.1");
		client.get(links.get(random.nextInt(links.size()-1)), new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(String response) {
				super.onSuccess(response);
				ld.dismiss();
				if(app.loadDiaryData(response)){
					app.buildNovelData(getEachLineCount(), XFApplication.getInstance().getTextAreaHeight());
					nvpa.setPages(XLModel.getPageInfo());
					nvpa.notifyDataSetChanged();
					nViewPager.setCurrentItem(0,false);	
					mSeekBarWhite.setMax(XLModel.getPageInfo().size());
					mSeekBarBlack.setMax(XLModel.getPageInfo().size());
				}
				
			}

			@Override
			public void onFailure(Throwable t, String arg1) {
				
			}


		});
		
	}
	

	
	private int getEachLineCount(){
		int mPageWidth = XFApplication.getInstance().getPageWidth();
		int m = Utils.dp2pixel(this, Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()]);
		int n = Utils.dp2pixel(this, 18);
	    int i1 = mPageWidth - n * 2;
	    int i2 = i1 - i1 % m;
	    int i3 = (mPageWidth - i2) / 2;
	    
	    int eachLineCount = (mPageWidth - i3*2)/ Utils.dp2pixel(this,Constants.TEXTSIZE_NORMAL[XFApplication.getInstance().getScale()]);
		return eachLineCount;
	}
	
	private void initBrightnessLevel(){
		if(mBrightnessLevel < 0)
			mBrightnessLevel = app.getPreferences().getInt("brightness_level", -1);
		if(mBrightnessLevel < 0){
			int i = android.provider.Settings.System.getInt(getContentResolver(), "screen_brightness",-1);
			if(i < 0)
				i = 128;
			mBrightnessLevel = i / 51;
			android.content.SharedPreferences.Editor editor = app.getPreferences().edit();
            editor.putInt("brightness_level", mBrightnessLevel);
            editor.commit();
		}else{
			 setBrightnessLevel(mBrightnessLevel);
		}
			
		
	}

	private void setBrightnessLevel(int mBrightnessLevel) {
		if(mBrightnessLevel >= 0 && mBrightnessLevel <= -1 + Constants.BRIGHTNESS_COUNT){
			WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
			layoutParams.screenBrightness = Constants.BRIGHTNESS_VALUE[mBrightnessLevel];
			getWindow().setAttributes(layoutParams);
			SharedPreferences.Editor editor = app.getPreferences().edit();
			editor.putInt("brightness_level", mBrightnessLevel);
		}
	}
	
}


