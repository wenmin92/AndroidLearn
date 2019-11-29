package cc.wenmin92.androidlearn.storage

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.documentfile.provider.DocumentFile
import cc.wenmin92.androidlearn.R
import kotlinx.android.synthetic.main.activity_storage_test.*
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


class StorageTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_test)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            testStorage()
        }

        testSCF()
        // testMediaStore()
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun testStorage() {
        // ------------------------------
        // Context
        // ------------------------------
        printFile("getDataDir()", dataDir)
        printFile("getDir(\"abc\", Context.MODE_PRIVATE)", getDir("abc", Context.MODE_PRIVATE))
        printFile("getDir(\"abc\", Context.MODE_APPEND)", getDir("abc", Context.MODE_APPEND))
        // getDir("abc", Context.MODE_WORLD_READABLE); // deprecated
        // getDir("abc", Context.MODE_WORLD_WRITEABLE); // deprecated
        printFile("getCacheDir()", cacheDir)
        printFile("getCodeCacheDir()", codeCacheDir)
        printFile("getDatabasePath(db_name)", getDatabasePath("db_name"))
        printFile("getNoBackupFilesDir()", noBackupFilesDir)
        printFile("getFilesDir()", filesDir)
        printFile("getFileStreamPath(\"abc\")", getFileStreamPath("abc"))
        try {
            val fileOutput = openFileOutput("abc", Context.MODE_PRIVATE)
            fileOutput.write("Hello, World!".toByteArray())
            fileOutput.close()
            Timber.d(BufferedReader(InputStreamReader(openFileInput("abc"))).readLine())
            Timber.d(Arrays.toString(fileList()))
            deleteFile("abc")
            Timber.d(Arrays.toString(fileList()))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        printFile("getPackageCodePath()", packageCodePath)
        printFile("getPackageResourcePath()", packageResourcePath)
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
        printFile("getExternalFilesDir(Environment.DIRECTORY_PICTURES)", getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        printFile("getExternalCacheDir()", externalCacheDir)
        printFile("getObbDir()", obbDir)
        printFile("getExternalFilesDirs(Environment.DIRECTORY_PICTURES)", Arrays.toString(getExternalFilesDirs(Environment.DIRECTORY_PICTURES)))
        printFile("getExternalCacheDirs()", Arrays.toString(externalCacheDirs))
        printFile("getObbDirs()", Arrays.toString(obbDirs))
        printFile("getExternalMediaDirs()", Arrays.toString(externalMediaDirs))
        // ------------------------------
        // File
        // ------------------------------
        try {
            printFile("File.createTempFile(\"pre\", null)", File.createTempFile("pre", null))
            printFile("File.createTempFile(\"pre\", null, Environment.getRootDirectory())", File.createTempFile("pre", null, filesDir))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun printFile(tag: String, file: Any?) {
        Timber.d("%s: %s", tag, file?.toString() ?: "null") // file.toString() = file.getPath()
    }


    /**
     * Storage Access Framework
     */
    private fun testSCF() {
        tv_hint.setOnClickListener {
            try {
                val uri = Uri.parse(getPreferences(Context.MODE_PRIVATE).getString("uri", ""))
                val get = contentResolver.persistedUriPermissions // 重启也不会丢失，还可以获取到
                val outgoing = contentResolver.outgoingPersistedUriPermissions // outgoing
                val check = checkUriPermission(uri!!, Binder.getCallingPid(), Binder.getCallingUid(), Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                require(check == PackageManager.PERMISSION_GRANTED)

                val tree = DocumentFile.fromTreeUri(this, uri)
                val doc = tree?.createFile("text/txt", "file_${System.currentTimeMillis()}")
                Timber.d("listFiles: ${tree?.listFiles()?.joinToString()}")
            } catch (e: Exception) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    // 无需在这里添加flag，不影响
                    // flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    //         or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    //         or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                    // 指定文件名无效
                    // putExtra(Intent.EXTRA_TITLE, "myFileName")
                }
                // val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                //     type = "text/txt"
                //     putExtra(Intent.EXTRA_TITLE, "myFileName")
                // }
                startActivityForResult(intent, 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.data != null) {
            val flags = data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            val uri = data.data!!
            contentResolver.takePersistableUriPermission(uri, flags)
            getPreferences(Context.MODE_PRIVATE).edit { putString("uri", uri.toString()) }
        }
    }

    private fun testMediaStore() {
        tv_hint.setOnClickListener {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DESCRIPTION, "This is an test image")
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "test_image.png")
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            values.put(MediaStore.Images.Media.TITLE, "test_image.png")
            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //     values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/test")
            // }

            val external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val resolver: ContentResolver = contentResolver

            // 在真机测试，小米仍然需要权限，华为不需要
            val insertUri = resolver.insert(external, values)
            Timber.d("insertUri: $insertUri")

            if (insertUri != null) {
                resolver.openOutputStream(insertUri).use {
                    val bitmap = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, it)
                }
            }
        }
    }
}