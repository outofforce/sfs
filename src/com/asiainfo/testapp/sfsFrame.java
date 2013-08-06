package com.asiainfo.testapp;


import com.asiainfo.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.asiainfo.lib.DrawerFragment;


/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-6
 * Time: 下午10:04
 * To change this template use File | Settings | File Templates.
 */
public class sfsFrame extends FragmentActivity {

    public DrawerLayout drawerLayout;// 侧边栏布局
    public ListView leftList;// 侧边栏内的选项
    public ArrayAdapter<String> arrayAdapter;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    // 初始化控件
    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        items = getResources().getStringArray(R.array.left_array);
        leftList = (ListView) findViewById(R.id.left_drawer);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.listitem, items);
        leftList.setAdapter(arrayAdapter);
        leftList.setOnItemClickListener(itemListener);
        initFragments();

    }

    // 添加碎片
    private void initFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        DrawerFragment fragment = new DrawerFragment();
        // 设置在fragment中按钮来控制侧边栏的打开
        fragment.setDrawerLayout(drawerLayout, leftList);
        transaction.add(R.id.main_content, fragment);
        transaction.commit();
    }

    // 选项点击事件
    OnItemClickListener itemListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position,
                                long arg3) {
            // TODO Auto-generated method stub
            // 设置Activity的标题，这里只是用来做一个测试，你可以在这里用来处理单击侧边栏的选项事件
            setTitle(items[position]);
            // 关闭侧边栏
            drawerLayout.closeDrawer(leftList);
            Log.i("onItemSelected",
                    "open?:" + drawerLayout.isDrawerOpen(leftList));
        }

    };

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onKeyDown(int,
     * android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        // 使用menu键打开或关闭侧边栏
        if (keyCode == KeyEvent.KEYCODE_MENU) {

            if (drawerLayout.isDrawerOpen(leftList)) {
                drawerLayout.closeDrawer(leftList);
            } else {
                drawerLayout.openDrawer(leftList);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}