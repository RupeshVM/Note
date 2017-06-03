package webnet.org.note.context_wrapper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;


import org.greenrobot.greendao.query.QueryBuilder;

import webnet.org.note.database.DaoMaster;
import webnet.org.note.database.DaoSession;


/**
 * Created by sandesh on 20/5/15.
 */
public class AppController extends Application  {

    public static final String TAG = AppController.class.getSimpleName();
    private static final String DB_NAME = "ParcelHike";
    public static AppCompatActivity activity;
    private static AppController appControllerInstance;
    // Member variables
    private DaoSession daoSession;

    public static void setActivity(AppCompatActivity activity) {
        AppController.activity = activity;
    }

    /**
     * Gets the instance of AppController throughout the App
     *
     * @return AppController
     */
    public static AppController getInstance() {
        return appControllerInstance;
    }

    /**
     * Get the context from AppController   throughout the App
     *
     * @return Context
     */
    public static Context getAppContext() {
        return appControllerInstance.getApplicationContext();
    }

    /**
     * Gets the instance of AppController throughout the App(Sync)
     *
     * @return Application
     */
    public static synchronized AppController getInstanceSyn() {
        return appControllerInstance;
    }

    /**
     * Close the current DB session
     */
    public static void close() {
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static void saveToPreferencesInt(Context context, String preferenceName, int preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString(preferenceName, "");
    }

    public static int readFromPreferencesInt(Context context, String preferenceName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getInt(preferenceName, 0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appControllerInstance = AppController.this;
        setupDatabase();
        Troubleshooting(true);
    }




    private void Troubleshooting(boolean isEnabled) {
        QueryBuilder.LOG_SQL = isEnabled;
        QueryBuilder.LOG_VALUES = isEnabled;
    }


    /**
     * setup the initial Database
     */

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /**
     * Get the instance of DaoSession
     *
     * @return DaoSession
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }



}