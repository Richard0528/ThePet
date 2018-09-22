package www.petapp.com.thepet.Add;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import www.petapp.com.thepet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddInfoFragment extends Fragment {

    private EditText mBreeder;
    private EditText mName;
    private EditText mAge;
    private EditText mSize;
    private EditText mWeight;
    private EditText mDescription;
    private Button mNext;

    private String INVALID_INPUT = "Please provide required information about your pet.";
    private String EMPTY_INPUT = "Empty Input";



    private OnButtonClickListener mListener;

    public interface OnButtonClickListener{
        void onButtonClicked(View view);
        void getPetInfo(String breeder, String name, String age, String size, String weight,
                        String description);
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
        mBreeder = v.findViewById(R.id.add_Info_breeder);
        mName = v.findViewById(R.id.add_Info_name);
        mAge = v.findViewById(R.id.add_Info_age);
        mSize = v.findViewById(R.id.add_Info_size);
        mWeight = v.findViewById(R.id.add_Info_weight);
        mDescription = v.findViewById(R.id.add_Info_description);
        mNext = v.findViewById(R.id.Info_button);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean result = true;
                String breeder = mBreeder.getText().toString();
                String name = mName.getText().toString();
                String age = mAge.getText().toString();
                String size = mSize.getText().toString();
                String weight = mWeight.getText().toString();
                String description = mDescription.getText().toString();

                if (TextUtils.isEmpty(breeder)) {
                    mBreeder.setError(EMPTY_INPUT);
                    result = false;
                } else if (TextUtils.isEmpty(name)) {
                    mName.setError(EMPTY_INPUT);
                    result = false;
                } else if (TextUtils.isEmpty(age)) {
                    mAge.setError(EMPTY_INPUT);
                    result = false;
                } else if (TextUtils.isEmpty(size)) {
                    mSize.setError(EMPTY_INPUT);
                    result = false;
                } else if (TextUtils.isEmpty(weight)) {
                    mWeight.setError(EMPTY_INPUT);
                    result = false;
                }

                if (result) {
                    mListener.onButtonClicked(v);
                    mListener.getPetInfo(breeder, name, age, size, weight, description);

                }

            }
        });
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
