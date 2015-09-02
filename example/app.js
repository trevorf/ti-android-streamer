// Require the Audio Streamer module
var streamer = require('com.woohoo.androidaudiostreamer');

var isPlaying = false;

// Create a window
var window = Ti.UI.createWindow({
	backgroundColor : 'black',
	exitOnClose : true
});

// Create a play button and add it to the window
var playButton = Ti.UI.createButton({
	title : 'Play',
	top : 100,
	left : 100
});
window.add(playButton);

// Create a play button 'click' listener - this will play the stream (MP3, AAC & AAC+ streams are supported)
playButton.addEventListener('click', function(e){
	isPlaying = true;
	streamer.play('http://209.85.88.199:8080'); // this is an AAC stream http://198.144.148.12:9002/
	
	
        /*
         * STREAM STATUS CHANGE LISTENER
         *
         * This listener provides the stream status in real time
         */
        var status;
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

        /*
         * METADATA CHANGE LISTENER
         *
         * This listener is useful when streams provide playlists via stream metadata - the values
         * returned are dependent on what the stream provided.
         * If any values are returned as undefined this means the stream does not provide them.
         */
        streamer.addEventListener('metadata', function(e) {
            Ti.API.log(e.title);
            Ti.API.log(e.url);
            Ti.API.log(e.genre);
        }); 

});

// Create a stop button and add it to the window
var stopButton = Ti.UI.createButton({
	title : 'Stop',
	top : 100,
	right : 100
});
window.add(stopButton);

// Create a stop button 'click' listener - this will stop the stream if it is playing
stopButton.addEventListener('click', function(e){
	isPlaying = false;
	streamer.stop();
});

/* Create some variables for the volume
 * currentVolume - get the volume level that the device is currently set at
 * maxVolume - get the maximum volume for the device (different Android devices have different maximum volume levels)
 */
var currentVolume = 5;
var maxVolume = streamer.getMaxVolume();

// You could use the getCurrentVolume() method to return the device's currently set volume
// streamer.getCurrentVolume()

// Add a volume label to the window
var volumeLabel = Ti.UI.createLabel({
	text : 'Volume',
	font : {
		fontSize : '12pt'
	},
	textAlign : 'center',
	top : 250,
	left : 50
});
window.add(volumeLabel);

// Create a 'volume down' button and add it to the window - this will decrease the volume of the stream
var volumeDownButton = Ti.UI.createButton({
	title : '-',
	top : 300,
	left : '40%'
});
window.add(volumeDownButton);

/* Create a 'click' listener on the volume button to decrease the volume - this uses the volume() method of the module to set the volume
 * The volume() method take a Number parameter from 0 to 10, where 10 is the maximum volume level
 */
volumeDownButton.addEventListener('click', function(e){
	var volume = currentVolume - 1; // decrease the current volume setting
	currentVolume = volume; // set the currentVolume variable to the current volume setting
	streamer.volume(volume); // set the streamer volume
});

// Create a 'volume up' button and add it to the window - this will increase the volume of the stream
var volumeUpButton = Ti.UI.createButton({
	title : '+',
	top : 300,
	right : '40%'
});
window.add(volumeUpButton);

/* Create a 'click' listener on the volume button to increase the volume - this uses the volume() method of the module to set the volume
 * The volume() method take a Number parameter from 0 to 10, where 10 is the maximum volume level
 */
volumeUpButton.addEventListener('click', function(e){
		var volume = currentVolume + 1;
		currentVolume = volume;
		streamer.volume(volume);
});

// Create a label to display the device's maximum volume level
var maxVolumeLabel = Ti.UI.createLabel({
	text : 'The maximum volume level for this device is ' + maxVolume.toString(),
	font : {
		fontSize : '12pt'
	},
	textAlign : 'center',
	bottom : 200
});
window.add(maxVolumeLabel);

var metadataLabel = Ti.UI.createLabel({
	font : {
		fontSize : '12pt'
	},
	textAlign : 'center',
	bottom : '40%'
});
window.add(metadataLabel);

var getMetadataBtn = Ti.UI.createButton({
	title : 'Get Metadata',
	top : 460
});
window.add(getMetadataBtn);

// Retrieve the stream metadata - 
// getMetaTitle() retrieves the current song or the stream title/description if this is not available
// getMetaGenre() retrieves the stream genre 
// and getMetaUrl() retrieves the URL of the stream
getMetadataBtn.addEventListener('click', function(e) {
	metadataLabel.text = 'Current Song/Title: ' + streamer.getMetaTitle() + '\nGenre: ' + streamer.getMetaGenre() + '\nURL: ' + streamer.getMetaUrl();
});

// Open the window
window.open();




