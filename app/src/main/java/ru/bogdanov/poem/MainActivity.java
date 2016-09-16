package ru.bogdanov.poem;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;
import android.widget.TextView;

import ru.bogdanov.poem.Fragments.HistoryFragment;
import ru.bogdanov.poem.Fragments.PoemFragment;
import ru.bogdanov.poem.Fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addTabHost();
        if (Storage.getPoemText().equals("")){
        startFragment(new HistoryFragment());
            tabHost.setCurrentTab(0);
        }
        else {startFragment(new PoemFragment());
            tabHost.setCurrentTab(1);
        }
    }

    private void addTabHost() {
        tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.linearLayout3);
        tabSpec.setIndicator(getString(R.string.history));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout4);
        tabSpec.setIndicator(getString(R.string.poem));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.linearLayout5);
        tabSpec.setIndicator(getString(R.string.add));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                switch (s){
                    case "tag1":{
                        startFragment(new HistoryFragment());
                        break;
                    }
                    case "tag2":{
                        startFragment(new PoemFragment());
                        break;
                    }
                    case "tag3":{
                        startFragment(new WelcomeFragment());
                        break;
                    }
                }
            }
        });
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }


    public void startFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContentLayout,fragment)
                .commit();
    }
}
