package com.example.demoforpermissions



import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat

/**
 * Here we will demonstrate how to write to the external storage of a device.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // button to set the on click listener for the permissions to start.
        button = findViewById(R.id.button)
        button.setOnClickListener { requestPermissions() }
    }

    /**
     * When asking for permissions for real applications, we should always check what
     * type of version of Android the user is using, or the app may crash.
     *
     * Also, ActivityCompat is used to check permissions, as well as other core Android
     * concepts.
     */


    /*
    Method to write to the external storage of the device.
     */
    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(
            // checks whether a user accepted a permission in the past/currently
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE // type of permission
        ) == PackageManager.PERMISSION_GRANTED // returns a result of permission granted

    /*
    Coarse location is used to determine the foreground location of the application.
     */
    private fun hasLocationForegroundPermission() =
        ActivityCompat.checkSelfPermission(
            // checks whether a user accepted a permission in the past/currently
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION // type of permission
        ) == PackageManager.PERMISSION_GRANTED // returns a result of permission granted

    /*
    Fine location is used to determine the background location of the application.
     */
    private fun hasLocationBackgroundPermission() =
        ActivityCompat.checkSelfPermission(
            // checks whether a user accepted a permission in the past/currently
            this,
            Manifest.permission.ACCESS_FINE_LOCATION // type of permission
        ) == PackageManager.PERMISSION_GRANTED // returns a result of permission granted

    /*
    We always need to define the permissions as an array even if we only have one permission.

    We will use a string array because the permissions are all Strings.
     */
    private fun requestPermissions() {
        // declare the mutable list of type string to add permissions if necessary.
        val permissionsToRequest = mutableListOf<String>()

        // If user did not accept permissions, then add it to the list.
        if(!hasWriteExternalStoragePermission()) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(!hasLocationForegroundPermission()) {
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(!hasLocationBackgroundPermission()) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        /*
        Now the mutable list has all the permissions that the user has either not accepted,
        or revoked.

        Now we check to see if the list is not empty, if not we want ot request all of
        the permissions inside of the list.
         */
        if(permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(), // returns a typed array of permissions.
                0 )
        }
    }

    /*
    Method called when the permissions are requested from the user.

    Request code is used when we want to request several permissions at different times.

    Different times = different request codes.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, // used ot differentiate between several requested permissions.
        permissions: Array<out String>,
        grantResults: IntArray // contains package manager permission granted integers.
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()) {
            // can loop through grant results array.
            for(i in grantResults.indices) {// indices = size -1
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity Permissions Request.",
                        "${permissions[i]} granted.") // print the permissions granted
                }
            }
        }
    }
}