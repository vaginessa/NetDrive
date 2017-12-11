package com.homenas.netdrive.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.homenas.netdrive.Constants;

import java.lang.ref.WeakReference;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by engss on 11/12/2017.
 */

public class NetworkUtils extends AsyncTask {
    private final String TAG = getClass().getSimpleName();
    private WeakReference<Context> contextRef;
    private String mjob;

    public NetworkUtils(Context context, String job){
        contextRef = new WeakReference<>(context);
        mjob = job;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        switch (mjob) {
            case Constants.getConType:
                getConnectionType();
                getIPAddress();
                break;
        }
        return null;
    }

    private void getConnectionType() {
        ConnectivityManager conMan = (ConnectivityManager) contextRef.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if( netInfo != null) {
            switch (netInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    Constants.isWifi = false;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    Constants.isWifi = true;
                    break;
                default:
                    Constants.isWifi = false;
            }
        }
    }

    private void getIPAddress() {
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                if(!networkInterface.isLoopback()){
                    List<InterfaceAddress> list = networkInterface.getInterfaceAddresses();
                    Iterator<InterfaceAddress> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        InterfaceAddress interfaceAddress = iterator.next();
                        String sAddr = interfaceAddress.getAddress().toString().substring(1);
                        boolean isIPv4 = sAddr.indexOf(':')<0;
                        if(isIPv4){
                            Constants.IPAddress = sAddr;
                            Constants.BcastAddress = interfaceAddress.getBroadcast().toString().substring(1);
                        }else{
                            int delim = sAddr.indexOf('%');
                            Constants.IPAddress = delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
