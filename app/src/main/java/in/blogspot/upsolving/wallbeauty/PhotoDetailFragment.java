package in.blogspot.upsolving.wallbeauty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by NEW on 21-02-2016.
 */
public class PhotoDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photo_detail, container, false);
        ImageView image = (ImageView) rootView.findViewById(R.id.detail_image);

        String photoLink = getActivity().getIntent().getStringExtra("LINK");

        Glide.with(getActivity()).load(photoLink).into(image);

        return rootView;
    }
}
