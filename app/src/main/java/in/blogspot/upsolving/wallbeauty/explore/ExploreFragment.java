package in.blogspot.upsolving.wallbeauty.explore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.blogspot.upsolving.wallbeauty.R;

/**
 * Created by Kishore on 2/17/2016.
 */
public class ExploreFragment extends Fragment {
    ViewPager mViewPager;
    PagerTabStrip mPagerTabStrip; //else use PagerTitleStrip
    ExplorePagerAdapter mExplorePagerAdapter;

    public ExploreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        assignViewAndListenersAndAdapter(rootView);

        return rootView;
    }

    private void assignViewAndListenersAndAdapter(View view){
        mViewPager = (ViewPager) view.findViewById(R.id.explore_view_pager);
        mPagerTabStrip = (PagerTabStrip) view.findViewById(R.id.explore_pager_tab_strip);
        mExplorePagerAdapter = new ExplorePagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mExplorePagerAdapter);

        mViewPager.setCurrentItem(2);
    }


    private class ExplorePagerAdapter extends FragmentPagerAdapter{
        private static final int SIZE = 7;

        private final String[] TITLES = {
                "CATEGORIES",
                "FEATURED",
                "RECENT",
                "DAILY POPULAR",
                "WEEKLY POPULAR",
                "MONTHLY POPULAR",
                "ALL TIME POPULAR"
        };

        public ExplorePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return new CategoriesFragment();
            else if(position == 1)
                return new FeaturedFragment();
            else if(position == 2)
                return new RecentFragment();
            else if(position == 3)
                return new DailyPopularFragment();
            else if(position == 4)
                return new WeeklyPopularFragment();
            else if(position == 5)
                return new MonthlyPopularFragment();
            else if(position == 6)
                return new AllTimePopularFragment();
            return null;
        }

        @Override
        public int getCount() {
            return SIZE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < SIZE)
                return TITLES[position];
            return super.getPageTitle(position);
        }
    }
}
