# ti-android-streamer
Android Media Streamer Module for Titantium. 

This module can be built and used in a project to support MP3, AAC and AAC+ local and remote audio streams. It has been developed to mainly to provide AAC support for the Titanium SDK what is only currently support on iOS.

Below is an outline of the main methods used in the module. A more comprehensive example can be found in <a href="https://github.com/trevorf/ti-android-streamer/blob/master/example/app.js">example/app.js</a>

# Require the Audio Streamer module
Firstly, require the module into the controller 

	var streamer = require('com.woohoo.androidaudiostreamer');

# Play a stream
In this example we are using a shoutcast AAC stream.

	streamer.play('http://209.85.88.199:8080'); // this is an AAC stream http://198.144.148.12:9002/
	
# Listen to a change in the stream status	

        streamer.addEventListener('change', function(e) {
            switch (e.status) {
            case 0 :
                status = 'STOPPED';
                break;
            case 1 :
                status = 'BUFFERING/CONNECTING';
                break;
            case 2 :
                status = 'PLAYING';
                break;
            case 3 :
                status = 'STREAM CONNECTION ERROR';
                break;
            }

            // Output the status to the console log
            Ti.API.log(status);
        });

# Get stream metadata

        streamer.addEventListener('metadata', function(e) {
            Ti.API.log(e.title);
            Ti.API.log(e.url);
            Ti.API.log(e.genre);
        }); 

# Stop a stream currently playing

	streamer.stop();

# Stream volume control

Create some variables for the volume
currentVolume - get the volume level that the device is currently set at
maxVolume - get the maximum volume for the device (different Android devices have different maximum volume levels)

	var currentVolume = 5;
	var maxVolume = streamer.getMaxVolume();

You could use the getCurrentVolume() method to return the device's currently set volume
	streamer.getCurrentVolume()

Then set the volume
	streamer.volume(volume);
The volume() method take a Number parameter from 0 to 10, where 10 is the maximum volume level


# Retrieve more stream metadata

- getMetaTitle() retrieves the current song or the stream title/description if this is not available
- getMetaGenre() retrieves the stream genre 
- getMetaUrl() retrieves the URL of the stream
