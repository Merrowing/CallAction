package com.example.ostap.callaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.example.ostap.callaction.model.Person;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

public class CallReceiver extends PhonecallReceiver {

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, ClassNotFoundException {

        List<Person> listPhones;
        listPhones = (List<Person>) InternalStorage.readObject(MyApplication.getAppContext(), "data");
        for (Person phone : listPhones) {
            if (number.equals(phone.getPhone()) && phone.getCheck()) {
                TelephonyManager tm = (TelephonyManager) ctx.getSystemService(TELEPHONY_SERVICE);

                Method m1 = tm.getClass().getDeclaredMethod("getITelephony");
                m1.setAccessible(true);
                Object iTelephony = m1.invoke(tm);

                Method m2 = iTelephony.getClass().getDeclaredMethod("silenceRinger");
                Method m3 = iTelephony.getClass().getDeclaredMethod("endCall");

                m2.invoke(iTelephony);
                m3.invoke(iTelephony);

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "PUT TELEPHONE HERE"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                ctx.startActivity(intent);
            }
        }
    }

}
