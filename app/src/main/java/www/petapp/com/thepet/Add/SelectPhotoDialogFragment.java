package www.petapp.com.thepet.Add;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.petapp.com.thepet.R;

public class SelectPhotoDialogFragment extends DialogFragment {

    private final String TAG = "SelectPhotoDialogFragme";
    private static final int CHOOSE_ALBUM_CODE = 1;
    private static final int TAKE_PHOTO_CODE = 2;
    OnPhotoSelectedListener mOnPhotoSelectedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_select_photo, container,false);

        TextView chooseAlbum = view.findViewById(R.id.textview_choose_album);
        chooseAlbum.setOnClickListener(v -> {
            Log.d(TAG, "choose from album");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, CHOOSE_ALBUM_CODE);
        });

        TextView takePhoto = view.findViewById(R.id.textview_take_photo);
        takePhoto.setOnClickListener(v -> {
            Log.d(TAG, "take photo");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, TAKE_PHOTO_CODE);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_ALBUM_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Log.d(TAG, "onActivityResult: image uri " + selectedImageUri);
            mOnPhotoSelectedListener.getImagePath(selectedImageUri);
            getDialog().dismiss();


        } else if (requestCode == TAKE_PHOTO_CODE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: done taking new photo" );
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mOnPhotoSelectedListener.getImageBitmap(bitmap);
            getDialog().dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /**
         * instantiate the interface
         */
        try {
            mOnPhotoSelectedListener = (OnPhotoSelectedListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: " + e.getMessage());
        }
    }

    public interface OnPhotoSelectedListener{
        void getImagePath(Uri imagePath);
        void getImageBitmap(Bitmap bitmap);
    }
}
