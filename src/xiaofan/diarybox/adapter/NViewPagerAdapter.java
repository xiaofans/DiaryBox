package xiaofan.diarybox.adapter;

import java.util.List;

import xiaofan.diarybox.bean.XFPage;
import xiaofan.diarybox.view.PageFragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class NViewPagerAdapter extends FragmentPagerAdapter{

	private List<XFPage> pages;
	private int currentPosition;
	
	public NViewPagerAdapter(FragmentManager fm,List<XFPage> pages) {
		super(fm);
		this.pages = pages;
	}
	
	

	@Override
	public Fragment getItem(int position) {
		//PageFragment pf = new PageFragment(pages.get(position));
		this.currentPosition = position;
		PageFragment pf = PageFragment.newInstance(position);
		return pf;
	}

	@Override
	public int getCount() {
		return pages.size();
	}



	public int getCurrentPosition() {
		return currentPosition;
	}



	public void setPages(List<XFPage> pages) {
		this.pages = pages;
	}



	@Override
	public int getItemPosition(Object object) {
		
		return POSITION_NONE;
	}
	

}
