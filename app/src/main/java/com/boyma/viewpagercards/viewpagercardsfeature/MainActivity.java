package com.boyma.viewpagercards.viewpagercardsfeature;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.boyma.viewpagercards.R;
import com.boyma.viewpagercards.customviews.ShadowTransformer;
import com.boyma.viewpagercards.viewpagercardsfeature.view.cardfragment.CardFragmentPagerAdapter;
import com.boyma.viewpagercards.viewpagercardsfeature.view.info1fragment.Info1Fragment;
import com.boyma.viewpagercards.viewpagercardsfeature.view.info2fragment.Info2Fragment;
import com.boyma.viewpagercards.viewpagercardsfeature.view.listfragment.ListFragment;
import com.boyma.viewpagercards.viewpagercardsfeature.domain.models.Person;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private List<Person>  al;
    private SwipeRefreshLayout swipe_container;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsing_toolbar;
    private MainActivityViewModel mViewModel;
    private ViewPager viewPager;
    private DrawerLayout drawer_layout;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        handler = new Handler();

        initData();

        initUI();

        initSubs();
    }

    private void initSubs() {
        mViewModel.isRefreshing().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                System.out.println("onChangedMainActivity");
                if (i == null){
                    swipe_container.setRefreshing(false);
                }
            }
        });
    }

    private void initData() {
        al = new ArrayList<>();
        al.add(new Person("name0","surname0","date0"));
        al.add(new Person("name1","surname1","date1"));
        al.add(new Person("name2","surname2","date2"));
        al.add(new Person("name3","surname3","date3"));
        al.add(new Person("name4","surname4","date4"));
    }



    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.appbar);

        viewPager = findViewById(R.id.viewPager);
        setupHeaderViewPager(viewPager);

        ViewPager viewpagertabs = findViewById(R.id.viewpagertabs);
        if (viewpagertabs != null) {
            setupTabViewPager(viewpagertabs);
        }

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpagertabs);

        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this,"started refresh" + String.valueOf(viewPager.getCurrentItem()), Toast.LENGTH_SHORT).show();
                swipe_container.setRefreshing(true);
                mViewModel.setRefreshing(viewPager.getCurrentItem());
            }
        });
        swipe_container.setColorScheme(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);


    }

    private void setupHeaderViewPager(ViewPager viewPager) {
        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(1, this),al);
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupTabViewPager(ViewPager viewpagertabs) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(Info1Fragment.newInstance(), "info 1");
        adapter.addFragment(Info2Fragment.newInstance(), "info 2");
        adapter.addFragment(ListFragment.newInstance(al), "list");
        viewpagertabs.setAdapter(adapter);
        viewpagertabs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("onPageScrolled:"+position);
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("onPageSelected:"+position);
                //header.setText(String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("onPageScrollStateChanged:"+state);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //System.out.println("verticalOffset:"+verticalOffset);
        if (verticalOffset != 0) {
            //drawer_layout.setOnTouchListener(null);
            swipe_container.setEnabled(false);
        } else {

            swipe_container.setEnabled(true);
        }
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    /**
     * Change value in dp to pixels
     * @param dp
     * @param context
     * @return
     */
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
