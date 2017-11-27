#!/bin/bash

# The following folders and files are used by the application
KEYS_FOLDER=~/keys
OWM_APPID_KEYFILE=owmAppId.key
OWM_KEYFILE_PATH=$KEYS_FOLDER/$OWM_APPID_KEYFILE
SPOTIFY_KEYFILE=spotify.key
SPOTIFY_KEYFILE_PATH=$KEYS_FOLDER/$SPOTIFY_KEYFILE


if [ ! -d $KEYS_FOLDER ]; then
    mkdir -p $KEYS_FOLDER
fi

echo "Type your OpenWeatherMap AppId: ";
read owmAppId
echo "$owmAppId" > $OWM_KEYFILE_PATH
echo -e "'$OWM_KEYFILE_PATH' created!\n"

echo "Type your Spotify Client ID: ";
read spotifyClientId
echo "Type your Spotify Client Secret: ";
read spotifyClientSecret
echo "$spotifyClientId:$spotifyClientSecret" > $SPOTIFY_KEYFILE_PATH
echo -e "'$SPOTIFY_KEYFILE_PATH' created!\n"

echo "Done!"

