package com.beehapps.mitraaalondri.main;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.beehapps.mitraaalondri.R;
import com.beehapps.mitraaalondri.main.handle_fragment.MainFragment;
import com.beehapps.mitraaalondri.main.handle_fragment.ProfileFragment;
import com.beehapps.mitraaalondri.main.handle_fragment.StatisticFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.adapter_tab, null);
        tabOne.setText("Beranda");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_beranda, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.adapter_tab, null);
        tabTwo.setText("Order");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_order, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.adapter_tab, null);
        tabThree.setText("Pesan");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_chat, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.adapter_tab, null);
        tabFour.setText("Statistik");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_statistik, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.adapter_tab, null);
        tabFive.setText("Profil");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_profil, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "Beranda");
        adapter.addFragment(new MainFragment(), "Order");
        adapter.addFragment(new MainFragment(), "Pesan");
        adapter.addFragment(new StatisticFragment(), "Statistik");
        adapter.addFragment(new ProfileFragment(), "Profil");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan lagi untuk keluar!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
