package de.vorlesung.wille.smlabcosmic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CountActivity extends Activity {

    CosmicApplication mApplication;
    private final String TAG = getClass().getSimpleName();
    private TextView entryText;
    private TextView exitText;
    private TextView readText;
    private TextView writeText;
    private TextView showCount;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        mApplication = (CosmicApplication) getApplicationContext();
        entryText = (TextView) findViewById(R.id.entryItem);
        exitText = (TextView) findViewById(R.id.exitItem);
        readText = (TextView) findViewById(R.id.readItem);
        writeText = (TextView) findViewById(R.id.writeItem);
        showCount = (TextView) findViewById(R.id.showCount);

        Intent myIntent = getIntent();
        itemName = myIntent.getStringExtra("Item");
        entryText.setText(String.valueOf(mApplication.getValue(itemName, 0)));
        exitText.setText(String.valueOf(mApplication.getValue(itemName, 1)));
        readText.setText(String.valueOf(mApplication.getValue(itemName, 2)));
        writeText.setText(String.valueOf(mApplication.getValue(itemName, 3)));
        countFP();
    }

    public void onClickBtnAddEntry(View view) {

        int entry = mApplication.getValue(itemName, 0);
        entry = entry + 1;
        mApplication.setValue(entry, 0, itemName);
        entryText.setText(String.valueOf(mApplication.getValue(itemName, 0)));
        countFP();
    }

    public void onClickBtnSubEntry(View view) {

        int entry = mApplication.getValue(itemName, 0);
        if (entry > 0) {
            entry = entry - 1;
            mApplication.setValue(entry, 0, itemName);
            entryText.setText(String.valueOf(mApplication.getValue(itemName, 0)));
            countFP();
        } else {
            Toast.makeText(this, (this.getString(R.string.VALUE_TO_SMALL)), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBtnAddExit(View view) {

        int exit = mApplication.getValue(itemName, 1);
        exit = exit + 1;
        mApplication.setValue(exit, 1, itemName);
        exitText.setText(String.valueOf(mApplication.getValue(itemName, 1)));
        countFP();
    }

    public void onClickBtnSubExit(View view) {

        int exit = mApplication.getValue(itemName, 1);
        if (exit > 0) {
            exit = exit - 1;
            mApplication.setValue(exit, 1, itemName);
            exitText.setText(String.valueOf(mApplication.getValue(itemName, 1)));
            countFP();
        } else {
            Toast.makeText(this, (this.getString(R.string.VALUE_TO_SMALL)), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBtnAddRead(View view) {

        int read = mApplication.getValue(itemName, 2);
        read = read + 1;
        mApplication.setValue(read, 2, itemName);
        readText.setText(String.valueOf(mApplication.getValue(itemName, 2)));
        countFP();
    }

    public void onClickBtnSubRead(View view) {

        int read = mApplication.getValue(itemName, 2);
        if (read > 0) {
            read = read - 1;
            mApplication.setValue(read, 2, itemName);
            readText.setText(String.valueOf(mApplication.getValue(itemName, 2)));
            countFP();
        } else {
            Toast.makeText(this, (this.getString(R.string.VALUE_TO_SMALL)), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBtnAddWrite(View view) {

        int write = mApplication.getValue(itemName, 3);
        write = write + 1;
        mApplication.setValue(write, 3, itemName);
        writeText.setText(String.valueOf(mApplication.getValue(itemName, 3)));
        countFP();
    }

    public void onClickBtnSubWrite(View view) {

        int write = mApplication.getValue(itemName, 3);
        if (write > 0) {
            write = write - 1;
            mApplication.setValue(write, 3, itemName);
            writeText.setText(String.valueOf(mApplication.getValue(itemName, 3)));
            countFP();
        } else {
            Toast.makeText(this, (this.getString(R.string.VALUE_TO_SMALL)), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBtnHelp(View view) {

        Intent intent = new Intent(CountActivity.this, HelpActivity.class);
        startActivityForResult(intent, 2);// Activity is started with requestCode 2
    }


    public void countFP() {
        int entry = mApplication.getValue(itemName, 0);
        int exit = mApplication.getValue(itemName, 1);
        int read = mApplication.getValue(itemName, 2);
        int write = mApplication.getValue(itemName, 3);
        int count = entry + exit + read + write;
        showCount.setText(String.valueOf(count));
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {

            Log.d(TAG, "onActivityResult hat functioniert");

        }
    }
}
