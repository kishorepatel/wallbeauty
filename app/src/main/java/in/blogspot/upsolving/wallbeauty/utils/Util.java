package in.blogspot.upsolving.wallbeauty.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Kishore on 2/18/2016.
 */
public class Util {
    public static boolean isInternetConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isConnected  = networkInfo != null && networkInfo.isConnectedOrConnecting();
        return isConnected;
    }
}
