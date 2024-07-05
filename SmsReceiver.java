import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        if (bundle != null) {
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    strMessage = msgs[i].getMessageBody();
                    String sender = msgs[i].getOriginatingAddress();

                    // Log message and sender for debugging
                    Log.d(TAG, "SMS from " + sender + ": " + strMessage);

                    // Check if the message matches the expected format
                    if (strMessage.contains("is your One Time Password (OTP) for Device Registration with HBL Digital")) {
                        // Extract OTP
                        String otp = extractOTP(strMessage);
                        Log.d(TAG, "Extracted OTP: " + otp);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception caught", e);
            }
        }
    }

    // Extract OTP from the message
    private String extractOTP(String message) {
        String[] parts = message.split(" ");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equalsIgnoreCase("your") && parts[i + 1].equalsIgnoreCase("One") &&
                parts[i + 2].equalsIgnoreCase("Time") && parts[i + 3].equalsIgnoreCase("Password") &&
                parts[i + 4].equalsIgnoreCase("(OTP)")) {
                return parts[i + 6];
            }
        }
        return null;
    }
}
