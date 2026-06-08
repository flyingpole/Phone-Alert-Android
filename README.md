# Elder Alert v1 Test

This is the first Android test version for an elderly call/text alert system.

Current v1 features:
- Simple Android screen
- Foreground service
- Test CALL button
- Test TEXT button
- STOP button
- Incoming SMS receiver
- Incoming phone ringing receiver

Bluetooth/ESP32 BLE signaling is intentionally not added yet. This version is to confirm the APK installs and the service runs on the phone.

## Build APK with GitHub Actions

1. Create a new GitHub repository.
2. Upload all files in this folder.
3. Open the Actions tab.
4. Run **Build Android APK**.
5. Download the artifact named **ElderAlert-debug-apk**.
6. Install `app-debug.apk` on the Android phone.

You may need to allow installing unknown apps on the phone.
