package www.petapp.com.thepet;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import www.petapp.com.thepet.model.PetCardItem;
import www.petapp.com.thepet.model.RecommendedRecycleViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecommendedPetRecycleView;
    private ArrayList<PetCardItem> mRecommendedPetList;
    private RecommendedRecycleViewAdapter homeRecommendedPetAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecommendedPetRecycleView = view.findViewById(R.id.home_recommended);
        return view;
    }

    public void onStart() {
        super.onStart();

        // pet news
        mRecommendedPetList = new ArrayList<>();
        mRecommendedPetList.add(new PetCardItem(R.drawable.images_1, "First Pet", "Age, month"));
        mRecommendedPetList.add(new PetCardItem(R.drawable.images_2, "Second Pet", "Age, month"));
        mRecommendedPetList.add(new PetCardItem(R.drawable.images_3, "Third Pet", "Age, month"));
        mRecommendedPetList.add(new PetCardItem(R.drawable.images_4, "Forth Pet", "Age, month"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        mRecommendedPetRecycleView.setLayoutManager(linearLayoutManager);

        homeRecommendedPetAdapter = new RecommendedRecycleViewAdapter(mRecommendedPetList);
        mRecommendedPetRecycleView.setAdapter(homeRecommendedPetAdapter);

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
