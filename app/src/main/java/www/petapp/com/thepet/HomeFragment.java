package www.petapp.com.thepet;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import www.petapp.com.thepet.model.EndlessRecyclerViewScrollListener;
import www.petapp.com.thepet.model.PetCardItem;
import www.petapp.com.thepet.model.RecommendedRecycleViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    // recommended section
    private RecyclerView mRecommendedPetRecycleView;
    private ArrayList<PetCardItem> mRecommendedPetList;
    private RecommendedRecycleViewAdapter homeRecommendedPetAdapter;

    // explore section
    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerView exploreItemRecycleView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecommendedPetRecycleView = view.findViewById(R.id.home_recommended);
        exploreItemRecycleView = view.findViewById(R.id.home_explore);
        return view;
    }

    public void onStart() {
        super.onStart();

        setUpRecommended();

        setUpExplore();
    }

    private void setUpRecommended() {
        // Recommended section
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

    private void setUpExplore() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        exploreItemRecycleView.setLayoutManager(gridLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        exploreItemRecycleView.addOnScrollListener(scrollListener);
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
