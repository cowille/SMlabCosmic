package de.vorlesung.wille.smlabcosmic;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static android.R.attr.value;

/**
 * This class provides the functionality of the Realm database.
 */

public class CosmicApplication extends Application {

    private static CosmicApplication singleton;
    private final String TAG = getClass().getSimpleName();
    private int mValue;
    CosmicApplication mApplication;
    private Realm mRealm;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static CosmicApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getInstance(config);
        mApplication = new CosmicApplication();
    }

    /** Initializes a new project */
    public void setProject(String txt) {
        final String name = txt;

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm mRealm) {

                Project cosmic = mRealm.createObject(Project.class);
                cosmic.setProject(name);
                cosmic.setEntry(0);
                cosmic.setExit(0);
                cosmic.setRead(0);
                cosmic.setWrite(0);
            }
        });
    }

    /** Returns a current ArrayList of the projects */
    public ArrayList<String> getAllProjectNames() {
        String txt = "";
        ArrayList<String> ar = new ArrayList<String>();

        RealmQuery<Project> query = mRealm.where(Project.class);
        RealmResults<Project> myresult = query.findAll();

        for (Project results : myresult) {
            ar.add(results.getProject());
        }
        return ar;
    }


/*
    public String getProjectName() {
        String txt = "";

        RealmQuery<Project> query = mRealm.where(Project.class);
        RealmResults<Project> myresult = query.findAll();
        System.out.println("Bla");
        for (Project results : myresult) {

            txt = txt + (results.getProject() + "\n");
        }
        return txt;
    }


*/

    /** Deletes a selected project. */
    public void deleteItem(final String str) {
        final String localStr = str;

        mRealm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm mRealm) {

                RealmResults<Project> result = mRealm.where(Project.class).equalTo("ProjectName", localStr).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    /** Select which value should be changed. */
    public void setValue(final int value, final int selection, final String itemName) {

        mRealm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm mRealm) {

                RealmResults<Project> result = mRealm.where(Project.class).findAll();
                Project mProject = result.where().equalTo("ProjectName", itemName).findFirst();
                switch (selection) {
                    case 0:
                        mProject.setEntry(value);
                        break;
                    case 1:
                        mProject.setExit(value);
                        break;
                    case 2:
                        mProject.setRead(value);
                        break;
                    case 3:
                        mProject.setWrite(value);
                        break;
                    default:
                        System.out.println("Fehler");
                }
            }
        });
    }

    /**
     * Get Entry from Realm
     */
    public int getValue(String itemName, int selection) {
        final String Item = itemName;
        final int mySelection = selection;

        mRealm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm mRealm) {
                int mValue = 0;
                RealmResults<Project> result = mRealm.where(Project.class).findAll();
                Project mProject = result.where().equalTo("ProjectName", Item).findFirst();

                //   entry = entry + Integer.valueOf(results.getEntry() + "\n");
                switch (mySelection) {
                    case 0:
                        mValue = Integer.valueOf(mProject.getEntry());
                        break;
                    case 1:
                        mValue = Integer.valueOf(mProject.getExit());
                        break;
                    case 2:
                        mValue = Integer.valueOf(mProject.getRead());
                        break;
                    case 3:
                        mValue = Integer.valueOf(mProject.getWrite());
                        break;
                    default:
                        System.out.println("Fehler");
                }

                mApplication.setInternalValue(mValue);
            }
        });

        int myValue = mApplication.getInternalValue();

        return myValue;
    }

    /** Set Value from inner class */
    public void setInternalValue(int mValue) {

        this.mValue = mValue;
    }

    /** Get Value from inner Class */
    public int getInternalValue() {

        return mValue;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mRealm.close();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mRealm.close();
    }


/*    public void deleteAll() {

        // obtain the results of a query
        final RealmResults<Project> results = mRealm.where(Project.class).findAll();

        // All changes to data must happen in a transaction
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // Delete all matches
                results.deleteAllFromRealm();
            }
        });
    }

    */
}