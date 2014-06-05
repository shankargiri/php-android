package com.myproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Success extends Activity{
	
	private static final String LOGTAG = "tag";
	private DrawerLayout mDrawerLayout;
	//private RelativeLayout mDrawerRelative;
	private ListView mDrawerList;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] slideMenu;
	
	private ActionBarDrawerToggle mDrawerTogg;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_sucess);
		getActionBar().setTitle(mTitle);
		
		mTitle = mDrawerTitle = getTitle();
		slideMenu = getResources().getStringArray(R.array.slide_menu_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		//mDrawerRelative = (RelativeLayout) findViewById(R.id.relative_layout);

		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, slideMenu));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mDrawerTogg = new ActionBarDrawerToggle(
				this,
				mDrawerLayout, 
				R.drawable.ic_drawer,
				R.string.drawer_open,
				R.string.drawer_close
				){
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
			@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
						getActionBar().setTitle(mDrawerTitle);
						invalidateOptionsMenu();
					}
		};
		mDrawerLayout.setDrawerListener(mDrawerTogg);
		if(savedInstanceState == null){
			selectItem(0);
		}
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(mDrawerTogg.onOptionsItemSelected(item)){
    		return true;
    	}
    	switch(item.getItemId()){
    	
    	}
    	return super.onOptionsItemSelected(item);
    }
    
 
    

	private class DrawerItemClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			selectItem(position);
			
		}
		
	}

	private void selectItem(int position) {
	
		switch (position) {
		case 0:
				
			break;
		case 1:
			
			Toast.makeText(Login_Success.this, "You selected 1", Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}
		
		mDrawerList.setItemChecked(position, true);
		setTitle(slideMenu[position]);
		
		mDrawerLayout.closeDrawer(mDrawerList);

	}
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerTogg.syncState();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerTogg.onConfigurationChanged(newConfig);
	}
}



