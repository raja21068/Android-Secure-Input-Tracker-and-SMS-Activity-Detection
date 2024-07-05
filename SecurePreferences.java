import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurePreferences {

    private static final String PREF_NAME = "secure_prefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String PASSWORD_VAULT_KEY = "password_vault";
    private static final String ENCRYPTION_KEY = "your-encryption-key"; // Replace with your actual key

    private SharedPreferences sharedPreferences;

    public SecurePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUsername(String username) {
        sharedPreferences.edit().putString(USERNAME_KEY, username).apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME_KEY, "");
    }

    public void savePassword(String password) {
        sharedPreferences.edit().putString(PASSWORD_KEY, password).apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(PASSWORD_KEY, "");
    }

    public void savePasswordToVault(String encryptedPassword) {
        sharedPreferences.edit().putString(PASSWORD_VAULT_KEY, encryptedPassword).apply();
    }

    public String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }

    private Key generateKey() throws Exception {
        return new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
    }
}
