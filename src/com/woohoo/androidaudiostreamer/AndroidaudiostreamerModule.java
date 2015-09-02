package com.woohoo.androidaudiostreamer;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;

import com.spoledge.aacdecoder.MultiPlayer;
import com.spoledge.aacdecoder.PlayerCallback;

@Kroll.module(name = "Androidaudiostreamer", id = "com.woohoo.androidaudiostreamer")
public class AndroidaudiostreamerModule extends KrollModule {

	protected static final String LOG = "AAS";
	String metaTitle;
	String metaGenre;
	String metaUrl;
	static String currentUrl;
	int status;

	boolean allowBackground = false;
	boolean streamHandlerSet = false;
	boolean playerStarted;
	static boolean isCurrentlyPlaying = false;
	static boolean classInit = true;

	PlayerCallback clb = new PlayerCallback() {

		public void playerStarted() {
			status = 1;
			playerStarted = true;
			if (hasListeners("change")) {
				KrollDict statusProps = new KrollDict();
				statusProps.put("status", status);
				fireEvent("change", statusProps);
			}	
		}

		public void playerPCMFeedBuffer(boolean isPlaying, int bufSizeMs,
				int bufCapacityMs) {
			status = 1;
			if (isPlaying) {
				status = 2;
			}
			if (hasListeners("change")) {
				KrollDict statusProps = new KrollDict();
				statusProps.put("status", status);
				fireEvent("change", statusProps);
			}
		}

		public void playerStopped(int perf) {
			playerStarted = false;
			status = 0;
			if (hasListeners("change")) {
				KrollDict statusProps = new KrollDict();
				statusProps.put("status", status);
				fireEvent("change", statusProps);
			}
		}

		public void playerException(Throwable t) {
			status = 3;
			if (playerStarted) {
				playerStopped(0);
			}
			if (hasListeners("change")) {
				KrollDict statusProps = new KrollDict();
				statusProps.put("status", status);
				fireEvent("change", statusProps);
			}
		}

		public void playerMetadata(final String key, final String value) {
		
			KrollDict metaProps = new KrollDict();

			if ("StreamTitle".equals(key) || "icy-name".equals(key)
					|| "icy-description".equals(key)) {
				metaTitle = value;
				metaProps.put("title", value);
			} else if ("StreamUrl".equals(key) || "icy-url".equals(key)) {
				metaUrl = value;
				metaProps.put("url", value);
			} else if ("StreamGenre".equals(key) || "icy-genre".equals(key)) {
				metaGenre = value;
				metaProps.put("genre", value);
			} else {
				return;
			}
			
			if (hasListeners("metadata") && !metaProps.isEmpty()) {
				metaProps.put("title", metaTitle);
				fireEvent("metadata", metaProps);
			}
		}

		@Override
		public void playerAudioTrackCreated(AudioTrack arg0) {
			// TODO Auto-generated method stub
		}

	};

	private static MultiPlayer aacPlayer;
	static AudioManager audioManager = (AudioManager) TiApplication
			.getInstance().getSystemService(Context.AUDIO_SERVICE);

	public AndroidaudiostreamerModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		try {
			java.net.URL
					.setURLStreamHandlerFactory(new java.net.URLStreamHandlerFactory() {
						public java.net.URLStreamHandler createURLStreamHandler(
								String protocol) {
							Log.d(LOG,
									"Asking for stream handler for protocol: '"
											+ protocol + "'");
							if ("icy".equals(protocol))
								return new com.spoledge.aacdecoder.IcyURLStreamHandler();
							return null;
						}
					});
		} catch (Throwable t) {
			Log.w(LOG, "Cannot set the ICY URLStreamHandler - maybe already set ? - " + t);
		}
		
		PhoneStateListener phoneStateListener = new PhoneStateListener() {
    		@Override
    		public void onCallStateChanged(int state, String incomingNumber) {
        		if (state == TelephonyManager.CALL_STATE_RINGING) {
        			if (isCurrentlyPlaying) {
	            		aacPlayer.stop();
	            	}
        		} else if (state == TelephonyManager.CALL_STATE_IDLE) {
	        		if (isCurrentlyPlaying && currentUrl != null) {
	            		aacPlayer.playAsync(currentUrl);
	            	}
        		} else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
            		//A call is dialing, active or on hold
        		}
        		super.onCallStateChanged(state, incomingNumber);
    		}
		};
		
		TelephonyManager mgr = (TelephonyManager) TiApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
		if (mgr != null) {
    		mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		}
		
	}

	@Kroll.method
	public void stop() {
		if (isCurrentlyPlaying) {
			aacPlayer.stop();
			isCurrentlyPlaying = false;
		}
	}

	@Kroll.method
	public void play(String url) {
		if (!isCurrentlyPlaying) {
		try {
			aacPlayer = new MultiPlayer(clb);
			aacPlayer.playAsync(url);
			currentUrl = url;
			isCurrentlyPlaying = true;
		} catch (Throwable t) {
				Log.e(LOG, "Error starting stream: " + t);
			}
		}
	}

	@Kroll.method
	public void volume(int vol) {
		int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = (maxVol / 10) * vol;
		vol = Math.round(volume);
		if (vol <= maxVol) {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);
		}
	}

	@Kroll.method
	public int getMaxVolume() {
		return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	@Kroll.method
	public int getCurrentVolume() {
		return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	@Kroll.method
	public String getMetaTitle() {
		return metaTitle;
	}

	@Kroll.method
	public String getMetaGenre() {
		return metaGenre;
	}

	@Kroll.method
	public String getMetaUrl() {
		return metaUrl;
	}

	@Kroll.method
	public String getStatus() {
		return String.valueOf(status);
	}

	@Kroll.method
	public void setAllowBackground(boolean allow) {
		allowBackground = allow;
	}

	@Override
	public void onPause(Activity activity) {
		super.onPause(activity);
		if (aacPlayer != null && !allowBackground) {
			aacPlayer.stop();
		}
	}

	@Override
	public void onResume(Activity activity) {
		super.onResume(activity);
		if (aacPlayer != null && !allowBackground) {
			aacPlayer.stop();
		}
	}
  
  }