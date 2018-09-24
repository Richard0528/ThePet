package www.petapp.com.thepet.Add;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import www.petapp.com.thepet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddInfoFragment extends Fragment {

    private AutoCompleteTextView mBreeder;
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
        void getPetInfo(String breeder, String name, int ageR, double sizeR, double weightR,
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

        String[] breeds = {"Shiba Inu", "German Shepherd", "Labrador Retriever", "Bulldog", "Beagle",
                    "Poodle", "Pug", "Boxer", "Golden Retriever", "Chihuahua", "Siberian Husky", "English Mastiff",
                    "Yorkshire Terrier", "Rottweiler", "Dobermann", "French Bulldog", "Dachshund", "Chow Chow",
                    "Maltese dog", "Staffordshire Bull Terrier", "Great Dane", "Akita Inu", "Border Collie",
                    "Pomeranian", "shih Tzu", "Pointer", "American Staffordshire Terrier", "Old English Sheepdog",
                    "English Cocker Spaniel", "Australian Shepherd", "Alaskan Malamute", "Bull Terrier",
                    "Pembroke Welsh Corgi", "Greyhound", "Newfoundland dog", "Jack Russel Terrier", "Dalmatian dog",
                    "German Shorthaired Pointer", "Boston Terrier", "Cavalier King Charles Spaniel",
                    "Australian Cattle Dog", "American Eskimo Dog", "Basset Hound", "Airedale Terrier", "Bernese Mountain Dog",
                    "Rough Collie", "Bichon Frise", "Affenpinscher", "Borzoi", "Anatolian Shepherb",
                    "King Charles Spaniel", "Shar Pei"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, breeds);

        // set up for AutoCompleteTextView
        mBreeder = v.findViewById(R.id.add_Info_breeder);
        mBreeder.setThreshold(1);
        mBreeder.setAdapter(adapter);

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
                Log.d(getTag(), "Breeder is " + breeder);
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

                // convert to its real data type
                int ageR = Integer.parseInt(age);
                double sizeR = Double.parseDouble(size);
                double weightR = Double.parseDouble(weight);

                if (result) {
                    mListener.onButtonClicked(v);
                    mListener.getPetInfo(breeder, name, ageR, sizeR, weightR, description);

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
