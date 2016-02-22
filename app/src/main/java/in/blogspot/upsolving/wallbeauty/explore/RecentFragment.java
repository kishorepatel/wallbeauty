package in.blogspot.upsolving.wallbeauty.explore;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import in.blogspot.upsolving.wallbeauty.ErrorReportActivity;
import in.blogspot.upsolving.wallbeauty.PhotoDetailActivity;
import in.blogspot.upsolving.wallbeauty.R;
import in.blogspot.upsolving.wallbeauty.utils.Util;

/**
 * Created by Kishore on 2/18/2016.
 */
public class RecentFragment extends Fragment {
    private static final String TAG = RecentFragment.class.getSimpleName();

    private static int IN_GRID_SIZE = -1;

    GridView mPhotoGrid;
    ArrayList<String> photoLinks;
    PhotoGridAdapter adapter;

    String KEY = "8ab527212fbe0a8bd799dc5322908530";
    String SECRET = "e2c51aec29d09722";

    String link = "https://api.flickr.com/services/rest/?method=flickr.favorites.getList&api_key=8ab527212fbe0a8bd799dc5322908530&user_id=140322786%40N07&format=json&nojsoncallback=1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photo_grid, container, false);

        mPhotoGrid = (GridView)rootView.findViewById(R.id.photo_grid);
        photoLinks = new ArrayList<>();
        adapter = new PhotoGridAdapter();

        mPhotoGrid.setAdapter(adapter);
        mPhotoGrid.setOnItemClickListener(mGridOnItemClickListener);
       
        return rootView;
    }



    //---------------------------ERROR HANDLING----------------------


    @Override
    public void onResume() {
        super.onResume();
        if(!Util.isInternetConnected(getActivity())){
            Intent intent = new Intent(getActivity(), ErrorReportActivity.class);
            startActivity(intent);
        }
        else{
            new PhotoAsyncList().execute();
        }
    }

    //---------------------------------------------------------------------------

    private AdapterView.OnItemClickListener mGridOnItemClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "position: " + position);
            Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
            intent.putExtra("LINK", photoLinks.get(position));
            startActivity(intent);
        }
    } ;


    private class PhotoGridAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return photoLinks.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(IN_GRID_SIZE <= 0){
                GridView grid = (GridView) parent;
                IN_GRID_SIZE = grid.getWidth()/ grid.getNumColumns();
            }

            ImageView image = null;

            if(convertView == null){
                image = new ImageView(getActivity());
                image.setLayoutParams( new GridView.LayoutParams(IN_GRID_SIZE, IN_GRID_SIZE));
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                convertView = image;
            }
            else{
                image = (ImageView)convertView;
            }

            Glide.with(getActivity()).load(photoLinks.get(position)).into(image);

            return convertView;
        }
    }


    private class PhotoAsyncList extends AsyncTask<Void, Void, String[]>{
        @Override
        protected void onPostExecute(String[] list) {
            if(list == null){
                Log.d(TAG, "onPostExecute: List is null");
                return;
            }

            for(String link : list)
                photoLinks.add(link);

            adapter.notifyDataSetChanged();
        }

        @Override
        protected String[] doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonString = null;
            try {
                URL url = new URL(link);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();
                if(stream == null){
                    Log.d(TAG, "InputStream is null");
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line;
                while((line = reader.readLine()) != null){
                    buffer.append(line+"\n");
                }

                if(buffer.length() == 0){
                    Log.d(TAG, "Buffer is empty");
                    return null;
                }

                jsonString = buffer.toString();

                return parseJsonData(jsonString);
            }
            catch(IOException e){
                e.printStackTrace();
            }
            finally {
                if(urlConnection != null){
                    try{
                        urlConnection.disconnect();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }


        private String[] parseJsonData(String json){
            String PHOTOS = "photos";

            String TOTAL = "total";

            String PHOTO = "photo";

            String ID = "id";
            String SECRET = "secret";
            String SERVER = "server";
            String FARM = "farm";

            String[] links = null;
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject photos = jsonObject.getJSONObject(PHOTOS);
                int total = photos.getInt(TOTAL);
                links = new String[total];

                JSONArray photoArray = photos.getJSONArray(PHOTO);
                for(int i = 0; i < total; i++){
                    JSONObject photo = photoArray.getJSONObject(i);
                    String id = photo.getString(ID);
                    String secret = photo.getString(SECRET);
                    String server = photo.getString(SERVER);
                    int farm = photo.getInt(FARM);

                    links[i] = createLink(id, secret, server, farm);
                    Log.d(TAG, links[i]);
                }

            }
            catch(JSONException e){
                e.printStackTrace();
                Log.d(TAG, e.getMessage());
            }

            return links;
        }

        private String createLink(String id, String secret, String server, int farm){
            return "https://farm" +farm+ ".staticflickr.com/"+server+"/"+id+"_"+secret+"_q.jpg";
        }
    }
}
