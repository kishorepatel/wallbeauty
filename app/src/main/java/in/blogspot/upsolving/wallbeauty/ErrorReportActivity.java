package in.blogspot.upsolving.wallbeauty;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import in.blogspot.upsolving.wallbeauty.utils.Util;

/**
 * Created by Kishore on 2/22/2016.
 */
public class ErrorReportActivity extends AppCompatActivity {
    Button mErrorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_report);
        mErrorButton = (Button) findViewById(R.id.error_btn_retry);
        mErrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.isInternetConnected(ErrorReportActivity.this))
                    finish();
            }
        });
    }

}
