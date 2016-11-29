package de.vorlesung.wille.smlabcosmic;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    CosmicApplication mApplication;
    private final String TAG = getClass().getSimpleName();
    EditText edit;
    public static boolean isDelPressed = false;

    /**
     * Items entered by the user is stored in this ArrayList variable
     */
    public ArrayList<String> list = new ArrayList<String>();

    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    ArrayAdapter<String> adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /** Setting a custom layout for the list activity */
        setContentView(R.layout.activity_main);
        mApplication = (CosmicApplication) getApplicationContext();

        /** List of Projects set */
        setList();

        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        edit = (EditText) findViewById(R.id.txtItem);

        /** Setting the adapter to the ListView */
        setListAdapter(adapter);

    }

    public void setList() {
        list = mApplication.getAllProjectNames();

        String txt = "";

        for (String s : list) {
            txt += s + "\t";
        }
    }

    // when an item of the list is clicked
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {


        super.onListItemClick(list, view, position, id);

        final String selectedItem = (String) getListView().getItemAtPosition(position);

        if (!isDelPressed) {

            Intent intent = new Intent(this, CountActivity.class);
            intent.putExtra("Item", selectedItem);
            startActivityForResult(intent, 2);

        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle(this.getString(R.string.SECURITY_QUERY));

            // set dialog message
            alertDialogBuilder
                    .setMessage(this.getString(R.string.PROJECT_DELETION))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mApplication.deleteItem(selectedItem);
                            adapter.remove(selectedItem);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

            // set boolean value to false
            isDelPressed = false;
        }

    }

    /** Function to add a new project */

    public void onClickBtnAdd(View view) {

        Log.d(TAG, "Button Add pressed");

        //Check that textfield is not empty

        if (!TextUtils.isEmpty(edit.getText().toString().trim())) {

            String str = (edit.getText().toString());

            list = mApplication.getAllProjectNames();

            String txt = "";

            for (String s : list) {
                txt += s + "\t";
            }

            // Check that no same project name already exists.

            if (Pattern.compile(Pattern.quote(str), Pattern.CASE_INSENSITIVE).matcher(txt).find()) {

                Toast.makeText(this, (this.getString(R.string.PROJECT_EXIST)), Toast.LENGTH_LONG).show();
            } else {
                mApplication.setProject(str);
                list.add(str);
                adapter.add(str);
                adapter.notifyDataSetChanged();
                edit.setText("");
            }
        } else {
            Toast.makeText(this, (this.getString(R.string.PROJECT_NAME_EMPTY)), Toast.LENGTH_LONG).show();
        }
    }

    /** Button delete sets the boolean value to true   */
    public void onClickBtnDelItem(View view) {

        isDelPressed = true;
        Toast.makeText(this, (this.getString(R.string.PROJECT_TO_DELETE)), Toast.LENGTH_LONG).show();
    }

    public void onClickBtnDebug(View view) {

        ArrayList<String> list = new ArrayList<String>();
        list = mApplication.getAllProjectNames();
        String txt = "";

        for (String s : list) {
            txt += s + "\t";
        }
        System.out.println(txt);
    }

    public void onClickBtnDeleteAll(View view) {

        //       System.out.println(mApplication.getProjectName());
 //       mApplication.deleteAll();
        list = mApplication.getAllProjectNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
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
