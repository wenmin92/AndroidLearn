package cc.wenmin92.androidlearn.service.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

public class LocationUtilLeak {

    @SuppressLint({"CheckResult", "MissingPermission"})
    public static void getLocation(FragmentActivity activity, LocationCallBack locationCallBack) {
        // RxPermissions rxPermissions = new RxPermissions(activity);
        // rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
        //         Manifest.permission.ACCESS_FINE_LOCATION)
        //         .subscribe(permission -> {
        //             Context context = activity.getApplicationContext();
        //             if (permission.granted || checkPermissionM(context)) {
        //                 LocationManager locService =
        //                         (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //                 assert locService != null;
        //                 LocationListener locationListener = new LocationListener() {
        //
        //                     /*
        //                      * 注意这里, 匿名内部类, 隐含方法的参数作为域, 所以这里会持有locService和locationCallBack,
        //                      * locService是全局的, 没关系. locationCallBack不是, 并且也是一个匿名类, 持有activity, 导致activity不能回收.
        //                      * 解决方法是, 把这个匿名类变成静态内部类, locationCallBack作为构造方法的参数传入, 使用弱引用.
        //                      */
        //
        //                     @Override
        //                     public void onLocationChanged(Location location) {
        //                         Timber.d("onLocationChanged: " + location);
        //                         locService.removeUpdates(this);
        //                         locationCallBack.onLocationSuccess(location);
        //                     }
        //
        //                     @Override
        //                     public void onStatusChanged(String provider, int status, Bundle extras) {
        //                         Timber.d("onStatusChanged: " + provider);
        //                     }
        //
        //                     @Override
        //                     public void onProviderEnabled(String provider) {
        //                         Timber.d("onProviderEnabled: " + provider);
        //                     }
        //
        //                     @Override
        //                     public void onProviderDisabled(String provider) {
        //                         Timber.d("onProviderDisabled: " + provider);
        //                         locationCallBack.onLocationFail("no enabled provider");
        //                     }
        //                 };
        //                 if (locService.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        //                     locService.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
        // locationListener);
        //                 } else if (locService.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        //                     locService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        //                 } else {
        //                     locationCallBack.onLocationFail("no enabled provider");
        //                 }
        //             }
        //         });
    }

    private static boolean checkPermissionM(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    public interface LocationCallBack {
        void onLocationSuccess(Location location);

        void onLocationFail(String msg);
    }
}
