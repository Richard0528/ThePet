package www.petapp.com.thepet.Add;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import www.petapp.com.thepet.R;
import www.petapp.com.thepet.model.SectionPageAdapter;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setupViewPager();
    }

    /**
     * Responsible for adding the 3 tabs: 1.Media, 2.Info, 3.CheckLists
     */
    private void setupViewPager() {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddImageFragment());
        adapter.addFragment(new AddInfoFragment());
        adapter.addFragment(new AddCheckListFragment());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
