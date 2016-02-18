package in.blogspot.upsolving.wallbeauty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.blogspot.upsolving.wallbeauty.utils.Util;


/**
 * Created by Kishore on 2/18/2016.
 */
public class ErrorReportActivity extends AppCompatActivity {
    Button mRetry;
    TextView mReportProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_report);

        setViews();
    }

    private void setViews(){
        mRetry = (Button) findViewById(R.id.error_btn_retry);
        mReportProblem = (TextView) findViewById(R.id.error_report_problem);
        setListeners();
    }

    private void setListeners(){
        mRetry.setOnClickListener(btnRetryListener);
        mReportProblem.setOnClickListener(reportProblemListener);
    }

    private View.OnClickListener btnRetryListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(Util.isInternetConnected(ErrorReportActivity.this))
                finish();
        }
    };

    private View.OnClickListener reportProblemListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //TODO who can handle mail

        }
    };
}
