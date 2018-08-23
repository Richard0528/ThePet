package www.petapp.com.thepet;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import www.petapp.com.thepet.model.ImageSliderAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class PetProfileFragment extends Fragment {

    private ArrayList<Integer> mImages;
    private ImageSliderAdapter mImageSliderAdapter;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    public PetProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_profile, container, false);
        mPager = view.findViewById(R.id.pet_profile_view_pager);
        mTabLayout = view.findViewById(R.id.tab_dots);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpPetImage();
    }

    private void setUpPetImage() {
        mImages = new ArrayList<>();
        mImages.add(R.drawable.images_1);
        mImages.add(R.drawable.images_2);
        mImages.add(R.drawable.images_3);
        mImages.add(R.drawable.images_4);

        mImageSliderAdapter = new ImageSliderAdapter(getContext(), mImages);
        mPager.setAdapter(mImageSliderAdapter);
        mTabLayout.setupWithViewPager(mPager, true);


    }

}
