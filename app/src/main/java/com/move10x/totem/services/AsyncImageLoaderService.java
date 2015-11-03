package com.move10x.totem.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Ravi on 10/13/2015.
 */
public class AsyncImageLoaderService extends AsyncTask<Integer, Void, Bitmap> {

    private LruCache<String, Bitmap> mMemoryCache;
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    private String imageUrl;

    public AsyncImageLoaderService(ImageView imageView, String imageUrl) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.imageUrl = imageUrl;

        //Init LRU Cache
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        try{
            Bitmap imageBitmap = getBitmapFromMemCache(imageUrl);
            Log.d("asyncImageLoader","BItmap null: " + imageBitmap == null ? "null" : "not null");
            if(imageBitmap == null){
                java.net.URL url = new java.net.URL(imageUrl);
                imageBitmap = BitmapFactory.decodeStream(url.openStream());
                addBitmapToMemoryCache(imageUrl,imageBitmap);
            }
            return imageBitmap;
        }catch (Exception ex){
            return null;
        }
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.d("asyncImageLoaderService","Fetching image");
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                //RoundImage roundedImage = new RoundImage(bitmap);
                //imageView.setImageDrawable(roundedImage);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}