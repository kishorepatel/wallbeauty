package in.blogspot.upsolving.wallbeauty.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import in.blogspot.upsolving.wallbeauty.BuildConfig;
import in.blogspot.upsolving.wallbeauty.R;

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

    public static void goToAppStore(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(context.getString(R.string.app_market)));
        if(intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
        else{
            Intent market = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(context.getString(R.string.app_play_store)));
            if(intent.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(market);
            }
            else{
                Toast.makeText(context, context.getString(R.string.install_app_store), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void sendMail(Context context, String msg){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{context.getString(R.string.mail_id)});
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));

        String completeMsg = msg +
                " \n\n\n ----SYSTEM----\n "
                + Build.MANUFACTURER + "/" +Build.MODEL
                + "/version:" + BuildConfig.VERSION_CODE +"/"+ BuildConfig.VERSION_NAME
                + "/sdkInt:" + Build.VERSION.SDK_INT;

        intent.putExtra(Intent.EXTRA_TEXT, completeMsg);

        if(intent.resolveActivity(context.getPackageManager()) != null){
            context.startActivity(intent);
        }
        else{
            Toast.makeText(context, context.getString(R.string.no_mail_agent), Toast.LENGTH_SHORT).show();
        }
    }

    public static void goToMyAppHome(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(context.getString(R.string.my_app_store_account)));
        if(intent.resolveActivity(context.getPackageManager()) != null){
            context.startActivity(intent);
        }
        else{
            //TODO market else toast
        }
    }
}
