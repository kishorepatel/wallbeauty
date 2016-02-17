package in.blogspot.upsolving.wallbeauty;

import android.os.Bundle;

/**
 * Created by Kishore on 2/17/2016.
 */
public class ExploreActivity extends DrawerLayoutActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContainerContentView(new ExploreFragment());
    }
}
