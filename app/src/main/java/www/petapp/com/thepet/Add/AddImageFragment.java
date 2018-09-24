package www.petapp.com.thepet.Add;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.ArrayList;
import java.util.List;

import www.petapp.com.thepet.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddImageFragment extends Fragment  {

    private List<ImageView> mPetImgs;
    private Button mBtnNext;
    private OnButtonClickListener mListener;
    private final String TAG = "AddImageFragment";
    private final int REQUEST_CODE = 231;
    private ImageView mFirstPetImg;
    private List<Uri> mImgUris;
    private List<String> mImgPaths;

    public interface OnButtonClickListener{
        void onButtonClicked(View view);
        void getImgUris(List<Uri> imgUris);
        void getImgPaths(List<String> imgPaths);
    }

    public AddImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_image, container, false);
        mPetImgs = new ArrayList<>();
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet0));
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet1));
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet2));
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet3));
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet4));
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet5));
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet6));
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet7));
        mPetImgs.add(v.findViewById(R.id.add_img_imgview_pet8));
        mFirstPetImg = v.findViewById(R.id.add_img_imgview_pet0);



        init(v);
        return v;
    }

    private void init(View v) {

        verifyPermissions();


        mBtnNext = v.findViewById(R.id.Image_button);
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(v);
            }
        });
    }

    private void verifyPermissions() {
        final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(getContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {

            selectImgFromGallery();

        } else {
            requestPermissions(permissions, REQUEST_CODE);
        }
    }

    private void selectImgFromGallery() {
        mFirstPetImg.setOnClickListener((view) -> {
            Log.d(TAG, "pet image on click");
            Matisse.from(this)
                    .choose(MimeType.ofAll(), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(
                            new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                    .maxSelectable(9)
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(
                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                    .imageEngine(new Glide4Engine())    // for glide-V4
                    .setOnSelectedListener(new OnSelectedListener() {
                        @Override
                        public void onSelected(
                                @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                            // DO SOMETHING IMMEDIATELY HERE
                            Log.e("onSelected", "onSelected: pathList=" + pathList);
                            mListener.getImgPaths(pathList);

                        }
                    })
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .setOnCheckedListener(new OnCheckedListener() {
                        @Override
                        public void onCheck(boolean isChecked) {
                            // DO SOMETHING IMMEDIATELY HERE
                            Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                        }
                    })
                    .forResult(REQUEST_CODE);

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.e(TAG, "get img uris from activity");
            mImgUris = Matisse.obtainResult(data);

            Glide4Engine glide = new Glide4Engine();
            //set pet images
            for (int i = 0; i < mImgUris.size(); i++) {
                glide.loadGifThumbnail(getContext(), 150, ContextCompat.getDrawable(getContext(), R.drawable.ic_menu_camera),
                        mPetImgs.get(i), mImgUris.get(i));
                Log.e(TAG, "pet image uri: " + mImgUris.get(i));
            }

        } else {
            Log.e(TAG, "fail to get img uris from activity");
        }

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
