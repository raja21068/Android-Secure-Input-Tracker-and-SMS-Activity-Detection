# Android-Secure-Input-Tracker-and-SMS-Activity-Detection




### Repository Description
This Android application demonstrates the integration of a custom input method service (keyboard) with secure password storage and SMS handling. It includes functionalities for capturing and encrypting passwords entered through the custom keyboard, securely storing them using `SecurePreferences`, and receiving and processing SMS messages. The app uses AES encryption to ensure the security of stored passwords and provides network state management capabilities.

#### Key Features:
- **Custom Keyboard Service**: Implementation of a custom input method service (`MyInputMethodService`) to capture and securely store passwords.
- **Secure Password Storage**: Utilizes AES encryption for password storage through the `SecurePreferences` class.
- **SMS Receiver**: Includes an SMS receiver to handle incoming SMS messages.
- **Network State Management**: Permissions and methods to manage and retrieve network state information.

### Files:
1. **MainActivity.java**: Manages the main user interface and integrates text watchers for capturing input.
2. **SecurePreferences.java**: Handles secure storage of sensitive data using AES encryption.
3. **SmsReceiver.java**: Receives and processes incoming SMS messages.
4. **TextWatcher.java**: Provides text watcher implementations for input fields.
5. **AndroidManifest.xml**: Configures app permissions and services including the SMS receiver and input method service.

### Permissions:
- INTERNET
- ACCESS_NETWORK_STATE
- ACCESS_WIFI_STATE
- RECEIVE_SMS
- READ_SMS

This repository is ideal for developers looking to implement secure data handling and custom input services in Android applications.
