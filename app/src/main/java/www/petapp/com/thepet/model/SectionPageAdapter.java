package www.petapp.com.thepet.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * SectionPageAdapter is a FragmentPagerAdapter that holds all the fragments in the Home page.
 * Class that stores fragments for tabs
 */
public class SectionPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList;

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * return the total number of fragments.
     * @return
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * add a fragment to the collection.
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }
}
