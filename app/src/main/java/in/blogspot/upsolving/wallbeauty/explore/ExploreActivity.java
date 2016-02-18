package in.blogspot.upsolving.wallbeauty.explore;

import android.os.Bundle;

import in.blogspot.upsolving.wallbeauty.DrawerLayoutActivity;

/**
 * Created by Kishore on 2/17/2016.
 */
public class ExploreActivity extends DrawerLayoutActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContainerContentView(new ExploreFragment());
        setTitle("Explore");
    }
}
