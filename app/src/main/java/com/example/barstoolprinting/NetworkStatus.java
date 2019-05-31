package com.example.barstoolprinting;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {
    public interface NetworkCallback {
        void handleConnected(boolean _success);
    }
    private static final String TAG = "NetworkStatus ";

    private static NetworkStatus instance;
    private ConnectivityManager cm;

    public static synchronized NetworkStatus getInstance(){
        if(instance == null ) {
            instance = new NetworkStatus();
        }
        return instance;
    }

    private NetworkStatus(){
    }

    // To be called once by MainActivity
    public void SetConnectivityManager(ConnectivityManager _cm){
        cm = _cm;
    }

    public boolean isInternetConnected() {
        if(cm == null) throw new AssertionError("No Connectivity Manager!" + this);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return ((activeNetwork != null) && activeNetwork.isConnectedOrConnecting());
    }

    public void isOnline(final NetworkCallback _connected) {

        if(isInternetConnected()) {
            _connected.handleConnected(true);
        }
        else {
            _connected.handleConnected(false);
        }
    }
}
