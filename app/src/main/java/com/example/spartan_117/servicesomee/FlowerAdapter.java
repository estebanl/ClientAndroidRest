package com.example.spartan_117.servicesomee;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spartan_117.servicesomee.model.Flower;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Context context;
    private List<Flower> flowerList;
    private LruCache<Integer,Bitmap> imageCache;


    public FlowerAdapter(Context context, int resource, List<Flower> objects) {
        super(context, resource,objects);
        this.context = context;
        this.flowerList = objects;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory /8 ;
        imageCache = new LruCache<>(cacheSize);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_flower, parent, false);

        Flower flower = flowerList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        TextView tv2 = (TextView) view.findViewById(R.id.textView2);

        tv.setText(flower.getName());
        tv2.setText(flower.getCategory());


        Bitmap bitmapCache = imageCache.get(flower.getProductId());
        if(bitmapCache != null)
       // if (flower.getBitmap() != null)
        {
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
            imageView.setImageBitmap(flower.getBitmap());
        }
        else
        {
            FlowerAndView andView = new FlowerAndView();
            andView.flower = flower;
            andView.view = view;

            ImageLoader loader = new ImageLoader();
            loader.execute(andView);
        }

        return view;
    }

    class FlowerAndView
    {
        public Flower flower;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<FlowerAndView,Void,FlowerAndView>
    {

        @Override
        protected FlowerAndView doInBackground(FlowerAndView... params) {

            FlowerAndView container = params[0];
            Flower flower= container.flower;

            try
            {
                String imageUrl = MainActivity.PHOTOS_BASE_URL + flower.getPhoto();
                InputStream inputStream = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                flower.setBitmap(bitmap);
                inputStream.close();
                container.bitmap = bitmap;
                return container;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(FlowerAndView result) {
            ImageView imageView = (ImageView) result.view.findViewById(R.id.imageView1);
            imageView.setImageBitmap(result.bitmap);
           // result.flower.setBitmap(result.bitmap);
            imageCache.put(result.flower.getProductId(),result.bitmap);
        }
    }
}
