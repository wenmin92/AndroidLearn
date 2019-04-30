package cc.wenmin92.androidlearn.service.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;

public class LocationUtil {

    @SuppressWarnings("unused")
    public static void getLocation(@NonNull Fragment fragment, LocationCallBack locationCallBack) {
        // doGetCurrentLocation(fragment.getContext(), new RxPermissions(fragment), locationCallBack);
    }

    @SuppressWarnings("unused")
    public static void getLocation(@NonNull FragmentActivity activity, LocationCallBack locationCallBack) {
        //     doGetCurrentLocation(activity, new RxPermissions(activity), locationCallBack);
    }
    //
    // @SuppressLint({"CheckResult", "MissingPermission"})
    // private static void doGetCurrentLocation(Context context, RxPermissions rxPermissions, LocationCallBack
    // locationCallBack) {
    //     if (locationCallBack == null || context == null) {
    //         return;
    //     }
    //     final Context appContext = context.getApplicationContext();
    //     rxPermissions
    //             .requestEachCombined(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
    // .ACCESS_FINE_LOCATION)
    //             .subscribe(permission -> {
    //                 if (permission.granted || checkPermissionM(appContext)) { // 自己判断如果有权限也可
    //                     // Acquire a reference to the system Location Manager
    //                     LocationManager locationManager = (LocationManager) appContext.getApplicationContext()
    // .getSystemService(Context.LOCATION_SERVICE);
    //                     assert locationManager != null;
    //
    //                     // Define a listener that responds to location updates
    //                     LocationListener locationListener = new MyLocationListener(locationManager,
    // locationCallBack);
    //                     if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
    //                         locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
    // locationListener);
    //                     } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    //                         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
    // locationListener);
    //                     } else {
    //                         locationCallBack.onLocationFail("no enabled provider");
    //                     }
    //                 } else {
    //                     locationCallBack.onLocationFail("no permission");
    //                 }
    //             });
    // }

    /**
     * 6.0 及以上时，RxPermissions可能误判为没有权限，使用原生方式再次判断是否已经拥有权限。
     */
    private static boolean checkPermissionM(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    public interface LocationCallBack {
        void onLocationSuccess(Location location);

        void onLocationFail(String msg);
    }

    private static class MyLocationListener implements LocationListener {
        private final LocationManager locationManager;
        private final WeakReference<LocationCallBack> locationCallBackRef; // 解决内存泄漏
        private boolean hasReceived;

        public MyLocationListener(LocationManager locationManager, LocationCallBack locationCallBack) {
            this.locationManager = locationManager;
            this.locationCallBackRef = new WeakReference<>(locationCallBack);
            hasReceived = false;
        }

        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            // makeUseOfNewLocation(location);
            locationManager.removeUpdates(this);
            if (hasReceived) return;

            if (locationCallBackRef.get() == null) return;

            if (location != null) {
                locationCallBackRef.get().onLocationSuccess(location);
                hasReceived = true;
            } else {
                locationCallBackRef.get().onLocationFail("location == null");
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {
            locationManager.removeUpdates(this);
            if (locationCallBackRef.get() == null) return;
            locationCallBackRef.get().onLocationFail("device not support");
        }
    }
}
