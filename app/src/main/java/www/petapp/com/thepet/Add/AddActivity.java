package www.petapp.com.thepet.Add;

import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import www.petapp.com.thepet.R;
import www.petapp.com.thepet.model.SectionPageAdapter;

public class AddActivity extends AppCompatActivity implements
        AddInfoFragment.OnButtonClickListener,
        AddImageFragment.OnButtonClickListener {

    private ViewPager viewPager;
    private String TAG = "AddActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // remove elevation below the action bar
        getSupportActionBar().setElevation(0);

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
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Media");
        tabLayout.getTabAt(1).setText("Info");
        tabLayout.getTabAt(2).setText("Checklists");
    }

    @Override
    public void onButtonClicked(View view){
        int currPos = viewPager.getCurrentItem();

        switch(view.getId()){

            case R.id.Info_button:
                viewPager.setCurrentItem(currPos+1);
                break;
            case R.id.Image_button:
                viewPager.setCurrentItem(currPos+1);
                break;
        }
    }

    /**
     *
     * @param uris the selected images' uris
     */
    @Override
    public void getImgUris(List<Uri> uris) {
        //ready to upload
        Log.e(TAG, "the selected images size: " + uris.size());
    }

}
