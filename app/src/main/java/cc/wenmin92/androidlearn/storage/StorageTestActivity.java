package cc.wenmin92.androidlearn.storage;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import cc.wenmin92.androidlearn.R;
import timber.log.Timber;

public class StorageTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_test);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            testStorage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void testStorage() {

        // ------------------------------
        // Context
        // ------------------------------
        printFile("getDataDir()", getDataDir());
        printFile("getDir(\"abc\", Context.MODE_PRIVATE)", getDir("abc", Context.MODE_PRIVATE));
        printFile("getDir(\"abc\", Context.MODE_APPEND)", getDir("abc", Context.MODE_APPEND));
        // getDir("abc", Context.MODE_WORLD_READABLE); // deprecated
        // getDir("abc", Context.MODE_WORLD_WRITEABLE); // deprecated
        printFile("getCacheDir()", getCacheDir());
        printFile("getCodeCacheDir()", getCodeCacheDir());
        printFile("getDatabasePath(db_name)", getDatabasePath("db_name"));
        printFile("getNoBackupFilesDir()", getNoBackupFilesDir());

        printFile("getFilesDir()", getFilesDir());
        printFile("getFileStreamPath(\"abc\")", getFileStreamPath("abc"));
        try {
            FileOutputStream fileOutput = openFileOutput("abc", Context.MODE_PRIVATE);
            fileOutput.write("Hello, World!".getBytes());
            fileOutput.close();

            Timber.d(new BufferedReader(new InputStreamReader(openFileInput("abc"))).readLine());

            Timber.d(Arrays.toString(fileList()));

            deleteFile("abc");

            Timber.d(Arrays.toString(fileList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        printFile("getPackageCodePath()", getPackageCodePath());
        printFile("getPackageResourcePath()", getPackageResourcePath());


        // ------------------------------
        // Environment
        // ------------------------------
        // Environment.DIRECTORY_MUSIC
        // Environment.DIRECTORY_PODCASTS
        // Environment.DIRECTORY_RINGTONES
        // Environment.DIRECTORY_ALARMS
        // Environment.DIRECTORY_NOTIFICATIONS
        // Environment.DIRECTORY_PICTURES
        // Environment.DIRECTORY_MOVIES
        printFile("getExternalFilesDir(Environment.DIRECTORY_PICTURES)", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        printFile("getExternalCacheDir()", getExternalCacheDir());
        printFile("getObbDir()", getObbDir());

        printFile("getExternalFilesDirs(Environment.DIRECTORY_PICTURES)", Arrays.toString(getExternalFilesDirs(Environment.DIRECTORY_PICTURES)));
        printFile("getExternalCacheDirs()", Arrays.toString(getExternalCacheDirs()));
        printFile("getObbDirs()", Arrays.toString(getObbDirs()));
        printFile("getExternalMediaDirs()", Arrays.toString(getExternalMediaDirs()));


        // ------------------------------
        // File
        // ------------------------------
        try {
            printFile("File.createTempFile(\"pre\", null)", File.createTempFile("pre", null));
            printFile("File.createTempFile(\"pre\", null, Environment.getRootDirectory())", File.createTempFile("pre", null, getFilesDir()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printFile(String tag, @Nullable Object file) {
        Timber.d("%s: %s", tag, file == null ? "null" : file.toString()); // file.toString() = file.getPath()
    }
}
