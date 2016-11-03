package com.buihha.audiorecorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Message;
import android.util.Log;

public class Mp3Recorder {
	
	private static final String TAG = Mp3Recorder.class.getSimpleName();
	
	static {
		System.loadLibrary("mp3lame");
	}

	private static final int DEFAULT_SAMPLING_RATE = 22050;
	
	private static final int FRAME_COUNT = 160;
	
	/* Encoded bit rate. MP3 file will be encoded with bit rate 32kbps */ 
	private static final int BIT_RATE = 32;

	private AudioRecord audioRecord = null;

	private int bufferSize;

	private File mp3File;
	
	private RingBuffer ringBuffer;
	
	private byte[] buffer;

	private FileOutputStream os = null;

	private DataEncodeThread encodeThread;

	private int samplingRate;

	private int channelConfig;

	private PCMFormat audioFormat;
	
	private boolean isRecording = false;
	
	private boolean isRealRecording = false;
	
	private boolean isCancel = false;
	
	private String directory = "";
	
	private String fileName = "";
	
	int num = 0;
	boolean isSend = false;
	Timer timer = null;
	TimerTask task = null;
	
	RecordListener recordListener;
	Context context;
	
	public Mp3Recorder(int samplingRate, int channelConfig,
			PCMFormat audioFormat, RecordListener recordListener,Context context) {
		this.samplingRate = samplingRate;
		this.channelConfig = channelConfig;
		this.audioFormat = audioFormat;
		this.recordListener = recordListener;
		this.context = context;
	}

	/**
	 * Default constructor. Setup recorder with default sampling rate 1 channel,
	 * 16 bits pcm
	 */
	public Mp3Recorder(Context context, RecordListener recordListener) {
		this(DEFAULT_SAMPLING_RATE, AudioFormat.CHANNEL_IN_MONO,
				PCMFormat.PCM_16BIT, recordListener, context);
		
	}

	private void stopTimer () {
		if (null != task && null != timer) {
			task.cancel();
			timer.cancel();
			task = null;
			timer = null;
		}
	}	
	
	/**
	 * Start recording. Create an encoding thread. Start record from this
	 * thread.
	 * 
	 * @throws IOException
	 */
	public void startRecording() throws IOException {
		if (isRealRecording) return; 
		if (isRecording) return;
		isSend = false;
		num = 0;
		((Activity) context).runOnUiThread(new Runnable() {   
			@Override   
            public void run() {   
				recordListener.onRecording();
            }   
        });
		Log.d(TAG, "Start recording");
		Log.d(TAG, "BufferSize = " + bufferSize);
		// Initialize audioRecord if it's null.
		if (audioRecord == null) {
			if (!initAudioRecorder()) {
				return;
			}
		}

		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				num++;
				((Activity) context).runOnUiThread(new Runnable() {   
					@Override   
                    public void run() {   
						recordListener.onTimerUpdate(num);
                    }   
                });
				if (num >= 60) {
					try {
						stopRecording(false);
					} catch (IOException e) {
						Log.d(TAG, "Stop error");
					}
				}
			}
		};
		timer.schedule(task, 0, 1000);
		
		audioRecord.startRecording();
		
		new Thread() {

			@Override
			public void run() {
				isRecording = true;
				isRealRecording = true;
				while (isRecording) {
					int bytes = audioRecord.read(buffer, 0, bufferSize);
					if (bytes > 0) {
						ringBuffer.write(buffer, bytes);
					}
				}
				
				// release and finalize audioRecord
				try {
					audioRecord.stop();
					audioRecord.release();
					audioRecord = null;

					// stop the encoding thread and try to wait
					// until the thread finishes its job
					Message msg = Message.obtain(encodeThread.getHandler(),
							DataEncodeThread.PROCESS_STOP);
					msg.sendToTarget();
					
					Log.d(TAG, "waiting for encoding thread");
					encodeThread.join();
					Log.d(TAG, "done encoding thread");
					isRealRecording = false;
					
					if (num < 2) {
						isCancel = true;
					}
					
					if (isCancel) {
//						if (null != audioFile) {
//							audioFile.deleteOnExit();
//						}
						
						((Activity) context).runOnUiThread(new Runnable() {   
							@Override   
	                        public void run() {   
								recordListener.onCancel(mp3File, num - 1);
	                        }   
	                    });
					}
					else {
						if (!isSend) {
							((Activity) context).runOnUiThread(new Runnable() {   
								@Override   
		                        public void run() {   
									recordListener.onDone(mp3File, num - 1);
									isSend = true;
		                        }   
		                    });								
						}
					}
				} catch (InterruptedException e) {
					Log.d(TAG, "Faile to join encode thread");
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}			
		}.start();
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void stopRecording(boolean isCancel) throws IOException {
		if (!isSend) {
			Log.d(TAG, "stop recording");
			this.isCancel = isCancel;
			isRecording = false;
			
			stopTimer();			
		}
	}

	/**
	 * Initialize audio recorder
	 */
	private boolean initAudioRecorder() throws IOException {
		File dfile = new File(directory);
		if (!dfile.exists()) {
			boolean isSuccess = dfile.mkdirs();
			if (!isSuccess) {
				recordListener.onInitFileError();
				return false;
			}
		}		
		
		int bytesPerFrame = audioFormat.getBytesPerFrame();
		/* Get number of samples. Calculate the buffer size (round up to the
		   factor of given frame size) */
		int frameSize = AudioRecord.getMinBufferSize(samplingRate,
				channelConfig, audioFormat.getAudioFormat()) / bytesPerFrame;
		if (frameSize % FRAME_COUNT != 0) {
			frameSize = frameSize + (FRAME_COUNT - frameSize % FRAME_COUNT);
			Log.d(TAG, "Frame size: " + frameSize);
		}
		
		bufferSize = frameSize * bytesPerFrame;

		/* Setup audio recorder */
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
				samplingRate, channelConfig, audioFormat.getAudioFormat(),
				bufferSize);
		
		// Setup RingBuffer. Currently is 10 times size of hardware buffer
		// Initialize buffer to hold data
		ringBuffer = new RingBuffer(10 * bufferSize);
		buffer = new byte[bufferSize];
		
		// Initialize lame buffer
		// mp3 sampling rate is the same as the recorded pcm sampling rate 
		// The bit rate is 32kbps
		SimpleLame.init(samplingRate, 1, samplingRate, BIT_RATE);
		
		// Initialize the place to put mp3 file
//		String externalPath = Environment.getExternalStorageDirectory()
//				.getAbsolutePath();
//		File directory = new File(externalPath + "/" + "AudioRecorder");
//		if (!directory.exists()) {
//			directory.mkdirs();
//			Log.d(TAG, "Created directory");
//		}
		
		mp3File = new File(dfile, fileName);
//		Log.e("yz", "mp3File = " + mp3File.getAbsolutePath());
		os = new FileOutputStream(mp3File);

		// Create and run thread used to encode data
		// The thread will 
		encodeThread = new DataEncodeThread(ringBuffer, os, bufferSize);
		encodeThread.start();
		audioRecord.setRecordPositionUpdateListener(encodeThread, encodeThread.getHandler());
		audioRecord.setPositionNotificationPeriod(FRAME_COUNT);
		
		return true;
	}
	
	public void setDirAndName (String directory, String fileName) {
		this.directory = directory;
		this.fileName = fileName;
	}	
	
	public boolean isRecording () {
		return isRecording;
	}
	
	public interface RecordListener {

		void onRecording();

		void onCancel(File file, int time);

		void onDone(File file, int time);

		void onInitFileError();
		
		void onTimerUpdate(int num);
	}	
}