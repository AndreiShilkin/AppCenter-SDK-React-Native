#!/usr/bin/env bash
echo "Executing post clone script in `pwd`"
echo $NPM_RC | base64 --decode > $APPCENTER_SOURCE_DIRECTORY/DemoApp/.npmrc
echo $GOOGLE_SERVICES_JSON | base64 --decode > $APPCENTER_SOURCE_DIRECTORY/DemoApp/android/app/google-services.json
# Delete everything except DemoApp folder
rm -rf ../appcenter* ../AppCenterReactNativeShared ../TestApp34 ../BrownfieldTestApp ../TestApp
