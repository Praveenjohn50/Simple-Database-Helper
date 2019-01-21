package program.sample.praveen.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import program.sample.praveen.myapplication.mydatabase.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataBaseHelper=new DatabaseHelper(this);
        /**
         * Use this Database helper to do your database operations
         * Modify the code according to your Convenience
         */
    }
}
