package www.petapp.com.thepet.Add;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import www.petapp.com.thepet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddInfoFragment extends Fragment {

    private EditText mBreeder;
    private EditText mName;
    private EditText mBirthday;
    private EditText mSize;
    private EditText mWeight;
    private EditText mDescription;
    private Button mNext;

    private String breeder;
    private String name;
    private Date birthday;
    private Number size;
    private Number weight;
    private String description;

    private OnButtonClickListener mOnButtonClickListener;

    interface OnButtonClickListener{
        void onButtonClicked(View view);
    }

    public AddInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_info, container, false);

        init(v);

        return v;
    }

    private void init(View v) {
        mBreeder = v.findViewById(R.id.Info_breeder);
        mName = v.findViewById(R.id.Info_name);
        mBirthday = v.findViewById(R.id.Info_birthday);
        mSize = v.findViewById(R.id.Info_size);
        mWeight = v.findViewById(R.id.Info_weight);
        mDescription = v.findViewById(R.id.Info_description);
        mNext = v.findViewById(R.id.Info_button);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClicked(v);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnButtonClickListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }



}
