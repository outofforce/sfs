/**
 * 
 */
package com.asiainfo.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.asiainfo.R;
import com.asiainfo.testapp.PublishListFragment;
import com.asiainfo.testapp.ResumeFragment;

/**
 * @author BillKalin
 * 
 */
public class DrawerFragment extends Fragment implements OnClickListener {
    private static final String[] CONTENT = new String[] { "公告栏", "同学们", "我的简历"};
	public DrawerLayout layout;
	public View view;

	public DrawerFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setDrawerLayout(DrawerLayout layout, View view) {
		this.layout = layout;
		this.view = view;
	}

    public void onClick(View v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater
	 * , android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container,
                false);
        FragmentPagerAdapter adapter = new GoogleMusicAdapter(super.getFragmentManager());

        ViewPager pager = (ViewPager)rootView.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)rootView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
		return rootView;
	}


    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position % CONTENT.length == 0) {
                return PublishListFragment.newInstance(position % CONTENT.length);
            } else {
                return ResumeFragment.newInstance(position % CONTENT.length);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
}
