package in.blogspot.upsolving.wallbeauty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import in.blogspot.upsolving.wallbeauty.explore.ExploreActivity;
import in.blogspot.upsolving.wallbeauty.favourites.FavouritesActivity;
import in.blogspot.upsolving.wallbeauty.history.HistoryActivity;
import in.blogspot.upsolving.wallbeauty.utils.Util;

/**
 * Created by Kishore on 2/17/2016.
 */
public class DrawerLayoutActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToogle;
    NavigationView mNavigationView;
    Toolbar mToolbar;
    TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_drawer_menu);

        assignViewAndListeners();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToogle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToogle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setContainerContentView(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.data_container, fragment).commit();
    }




    //--------------------PRIVATE METHODS-----------------------------------------------------

    private void assignViewAndListeners(){
        //set views
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.naviation_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTitleText = (TextView) findViewById(R.id.toolbar_title);
        mDrawerToogle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_open, R.string.navigation_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        //set listeners
        mDrawerLayout.setDrawerListener(mDrawerToogle);
        mNavigationView.setNavigationItemSelectedListener(mNavItemsListener);
    }

    private NavigationView.OnNavigationItemSelectedListener mNavItemsListener = new NavigationView.OnNavigationItemSelectedListener() {
        //TODO
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();
            if(id == R.id.action_explore){
                Intent intent = new Intent(DrawerLayoutActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.action_favourites){
                Intent intent = new Intent(DrawerLayoutActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.action_history){
                Intent intent = new Intent(DrawerLayoutActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.action_rate){
                showRateDialog();
            }
            else if(id == R.id.action_feedback){
                Util.sendMail(DrawerLayoutActivity.this, getString(R.string.feedback));
            }
            else if(id == R.id.action_otherApps){
                //TODO
                Util.goToMyAppHome(DrawerLayoutActivity.this);
            }
            else if(id == R.id.action_settings){
                Intent intent = new Intent(DrawerLayoutActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
            return false;
        }
    };


    protected void setTitle(String msg){
        mTitleText.setText(msg);
    }


    private void showRateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.rate_this_app);
        builder.setMessage(R.string.rate_this_app_content);
        builder.setPositiveButton(R.string.yes_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Util.goToAppStore(DrawerLayoutActivity.this);
            }
        });
        builder.setNegativeButton(R.string.later_label, null);
        builder.show();
    }
}
