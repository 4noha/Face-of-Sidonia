package jp.nokkii.apps.faceofsidonia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

/**
 * Created by nokkii on 2015/05/21.
 */
public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        Log.d("aaaaa", "sssssssss");
        Intent startServiceIntent = new Intent(context,FaceOfSidonia.class);
        //startServiceIntent.putExtra(FaceOfSidonia.REQUEST_TYPE, FaceOfSidonia.REQUEST_PROCESS);
        context.startService(startServiceIntent);
    }
}
