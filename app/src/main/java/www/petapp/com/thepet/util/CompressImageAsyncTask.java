package www.petapp.com.thepet.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CompressImageAsyncTask extends AsyncTask<Void, Void, List<Bitmap>> {
    private List<Uri> mImgUris;
    private final String TAG = "CompressImageAsyncTask";
    private List<Bitmap> mBitmaps;
    private int mReqWidth;
    private int mReqHeight;

    public CompressImageAsyncTask(List<Uri> uris, int reqWidth, int reqHeight) {
        mImgUris = uris;
        mBitmaps = new ArrayList<>();
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e(TAG, "compress images ");
    }

    @Override
    protected List<Bitmap> doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: started.");

        for (int i = 0; i < mImgUris.size(); i++) {
            Bitmap bitmap = decodeImageFromPath(mImgUris.get(i).toString(), mReqWidth, mReqHeight);
            mBitmaps.add(bitmap);
        }
        return mBitmaps;
    }

    private int calculateInSampleSize(String imgPath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);
        final int imgWidth = options.outWidth;
        final int imgHeight = options.outHeight;
        int inSampleSize = 1;

        if (imgHeight > reqHeight || imgWidth > reqWidth) {
            final int halfHeight = imgHeight / 2;
            final int halfWidth = imgWidth / 2;

            while ((halfHeight / inSampleSize) >= reqHeight &&
                    (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Bitmap decodeImageFromPath(String imgPath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(imgPath, reqWidth, reqHeight);
        return BitmapFactory.decodeFile(imgPath);
    }

    @Override
    protected void onPostExecute(List<Bitmap> bitmaps) {
        super.onPostExecute(bitmaps);
    }
}
