/**
 * 
 */
package com.asiainfo.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.asiainfo.R;

/**
 * @author BillKalin
 * 
 */
public class DrawerFragment extends Fragment implements OnClickListener {

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
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_layout, container,
				false);
		Button openBtn = (Button) rootView.findViewById(R.id.open);
		openBtn.setOnClickListener(this);
		return rootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.open) {
			//�򿪲����
			if (!layout.isDrawerOpen(view)) {
				layout.openDrawer(view);
			}
		}
	}
}
