package www.petapp.com.thepet.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CompressImageAsyncTask extends AsyncTask<Void, Void, List<Bitmap>> {
    private List<String> mImgPaths;
    private final String TAG = "CompressImageAsyncTask";
    private List<Bitmap> mBitmaps;
    private int mReqWidth;
    private int mReqHeight;
    private OnCompressImagePostExecuteDelegate mListener;

    public interface OnCompressImagePostExecuteDelegate {
        void getCompressedBitmap(List<Bitmap> bitmaps);
    }

    public CompressImageAsyncTask(OnCompressImagePostExecuteDelegate listener, List<String> paths, int reqWidth, int reqHeight) {
        mImgPaths = paths;
        mBitmaps = new ArrayList<>();
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e(TAG, "compress images ");
    }

    @Override
    protected List<Bitmap> doInBackground(Void... voids) {
        Log.e(TAG, "doInBackground: started.");

        for (int i = 0; i < mImgPaths.size(); i++) {
            Bitmap bitmap = decodeImageFromPath(mImgPaths.get(i).toString(), mReqWidth, mReqHeight);

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
        Log.e(TAG, "decodeImageFromPath inSampleSize: " + options.inSampleSize);
        return BitmapFactory.decodeFile(imgPath);
    }

    @Override
    protected void onPostExecute(List<Bitmap> bitmaps) {
        super.onPostExecute(bitmaps);
        mListener.getCompressedBitmap(bitmaps);
    }

}
