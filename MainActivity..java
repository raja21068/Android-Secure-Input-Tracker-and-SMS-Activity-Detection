import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView deviceInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceInfoTextView = findViewById(R.id.device_info);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "Username: " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "Password: " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        String deviceName = getDeviceName();
        String ipAddress = getIPAddress(this);

        String deviceInfo = "Device Name: " + deviceName + "\nIP Address: " + ipAddress;
        deviceInfoTextView.setText(deviceInfo);
    }

    // Get the device name (manufacturer and model)
    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    // Capitalize the first letter of the string
    private String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char first = str.charAt(0);
        if (Character.isUpperCase(first)) {
            return str;
        } else {
            return Character.toUpperCase(first) + str.substring(1);
        }
    }

    // Get the IP address of the device
    public static String getIPAddress(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                    for (NetworkInterface intf : interfaces) {
                        List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                        for (InetAddress addr : addrs) {
                            if (!addr.isLoopbackAddress()) {
                                return addr.getHostAddress();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
}
