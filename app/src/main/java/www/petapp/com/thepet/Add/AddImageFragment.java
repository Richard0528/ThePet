package www.petapp.com.thepet.Add;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import www.petapp.com.thepet.MainActivity;
import www.petapp.com.thepet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddImageFragment extends Fragment implements SelectPhotoDialogFragment.OnPhotoSelectedListener {

    private ImageView mPetImg;
    private Button mNext;
    private OnButtonClickListener mOnButtonClickListener;
    private final String TAG = "AddImageFragment";
    private final int REQUEST_CODE = 231;

    interface OnButtonClickListener{
        void onButtonClicked(View view);
    }

    public AddImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_image, container, false);
        mPetImg = v.findViewById(R.id.list_pet_imgview_pet2);

        init(v);
        return v;
    }

    private void init(View v) {
        mPetImg.setOnClickListener((view) -> {
            Log.d(TAG, "pet image on click");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                verifyPermissions();
            } else {
                SelectPhotoDialogFragment fragment = new SelectPhotoDialogFragment();
                fragment.show(getFragmentManager(), TAG);
                fragment.setTargetFragment(AddImageFragment.this,1 );
            }

        });

        mNext = v.findViewById(R.id.Image_button);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClicked(v);
            }
        });
    }

    private void verifyPermissions() {
        final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(getContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {

            SelectPhotoDialogFragment fragment = new SelectPhotoDialogFragment();
            fragment.show(getFragmentManager(), TAG);
            fragment.setTargetFragment(AddImageFragment.this,1 );
        } else {
            requestPermissions(permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void getImagePath(Uri imagePath) {

    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {

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
