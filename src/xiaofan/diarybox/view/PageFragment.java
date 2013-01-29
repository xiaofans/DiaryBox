package xiaofan.diarybox.view;

import xiaofan.diarybox.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment extends Fragment{
	
	private int index;
	private XFView fview;
	public static PageFragment newInstance(int index){
		
		PageFragment pf = new PageFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		pf.setArguments(args);
		return pf;
	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		index = getArguments() != null ? getArguments().getInt("index") : 1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		fview =  (XFView) inflater.inflate(R.layout.xfview, container,false);
		fview.setPosition(index);
		return fview;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	
	
	@Override
	public void onDestroyOptionsMenu() {
		super.onDestroyOptionsMenu();
	} 
	
	@Override
	public void onDetach() {
		super.onDetach();
	}

	public int getIndex() {
		return index;
	}
	
}
