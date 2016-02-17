package in.blogspot.upsolving.wallbeauty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kishore on 2/17/2016.
 */
public class ExploreFragment extends Fragment {
    public ExploreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dummy, container, false);
        return rootView;
    }
}
