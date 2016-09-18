package ru.bogdanov.poem;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.bogdanov.poem.Fragments.HistoryFragment;
import ru.bogdanov.poem.Fragments.PoemFragment;
import ru.bogdanov.poem.Fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {



    MyAdapter mAdapter;
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mPager=(ViewPager) findViewById(R.id.viewPager);
        mAdapter=new MyAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mPager);
    }




    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new HistoryFragment();
                case 1: return new PoemFragment();
                case 2: return new WelcomeFragment();
                default:return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return getString(R.string.history);
                case 1: return getString(R.string.poem);
                case 2: return getString(R.string.add);
                default: return null;
            }
        }
    }


}
