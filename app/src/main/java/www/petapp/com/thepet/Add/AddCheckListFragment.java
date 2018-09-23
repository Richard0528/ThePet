package www.petapp.com.thepet.Add;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import www.petapp.com.thepet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCheckListFragment extends Fragment {

    private Button mBtnSubmit;
    private OnButtonClickListener mListener;

    public interface OnButtonClickListener{
        void onButtonClicked(View view);
        void getPetCheckLists();
    }

    public AddCheckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_check_list, container, false);

        mBtnSubmit = v.findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(v);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }

}
