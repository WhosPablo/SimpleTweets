package com.whospablo.simpletweets.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.whospablo.simpletweets.R;
import com.whospablo.simpletweets.SimpleTweetsApplication;
import com.whospablo.simpletweets.ui.compose.ComposeActivity;
import com.whospablo.simpletweets.ui.login.LoginActivity;
import com.whospablo.simpletweets.ui.profile.ProfileActivity;
import com.whospablo.simpletweets.util.fragments.RecyclerFragment;
import com.whospablo.simpletweets.util.models.User;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public interface RefreshableFragment{
        void refresh( OnRefreshDoneListener listener );
    }

    public interface OnRefreshDoneListener{
        void refreshDone();
    }

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) PagerSlidingTabStrip tabsStrip;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    HomeFragmentsPagerAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ComposeActivity.class);
                startActivity(i);

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);

        mFragmentAdapter = new HomeFragmentsPagerAdapter(getSupportFragmentManager());
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager.setAdapter(mFragmentAdapter);

//
//        // Give the PagerSlidingTabStrip the ViewPager
//        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);




        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {
                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Fragment fragment =
                            mFragmentAdapter.getRegisteredFragment(viewPager.getCurrentItem());
                    if(fragment instanceof RefreshableFragment){
                        ((RefreshableFragment) fragment).refresh(new OnRefreshDoneListener() {
                            @Override
                            public void refreshDone() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }
            });
        }

        SimpleTweetsApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = new User(response);
                SimpleTweetsApplication.setCurrentUser(user);
                setDrawerHeader(user);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    private void setDrawerHeader(User user) {

        ImageView navHeaderImg = (ImageView) navigationView.findViewById(R.id.nav_header_img);
        TextView navHeaderUserName = (TextView) navigationView.findViewById(R.id.nav_header_user_name);
        TextView navHeaderTwitterHandle = (TextView) navigationView.findViewById(R.id.nav_header_twitter_handle);
        Glide.with(this)
                .load(user.getImgUrl())
                .bitmapTransform(new CropCircleTransformation(this))
                .into(navHeaderImg);
        navHeaderTwitterHandle.setText(user.getHandle());
        navHeaderUserName.setText(user.getName());

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));

        } else if (id == R.id.nav_sign_out) {
            SimpleTweetsApplication.getRestClient().clearAccessToken();
            startActivity(new Intent(this, LoginActivity.class));

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void enableDisableSwipeRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    class HomeFragmentsPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

        private int tabTitleIcons[] = {R.drawable.ic_home, R.drawable.ic_bell, R.drawable.ic_message};
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();



        public HomeFragmentsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        // Register the fragment when the item is instantiated
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public RecyclerFragment getItem(int position) {
            if (position == 0) {
                getSupportActionBar().setTitle("Home");
                return new HomeFragment();
            } else if (position == 1) {
                getSupportActionBar().setTitle("Mentions");
                return new MentionsFragment();
            } else if (position == 2) {
                getSupportActionBar().setTitle("Messages");
                return new MessagesFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitleIcons.length;
        }

        @Override
        public int getPageIconResId(int position) {
            return tabTitleIcons[position];
        }

        // Unregister when the item is inactive
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}
