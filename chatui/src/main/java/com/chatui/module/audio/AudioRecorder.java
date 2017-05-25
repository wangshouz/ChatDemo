package com.chatui.module.audio;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class AudioRecorder {

    private static String TAG = "AudioRecorder";

    MediaRecorder recorder;

    static final String EXTENSION = ".amr";
    public static final int FILE_INVALID = -1;     // 文件不存在
    public static final int AUDIO_TOO_SHORT = -2;// 录音太短，无效

    private boolean isRecording = false;
    private long startTime;
    private String voiceFilePath = null;
    private String voiceFileName = null;
    private File file;

    public AudioRecorder() {
    }

    /**
     * start recording to the file
     */
    public String startRecording(Context appContext) {
        file = null;
        try {
            // need to create recorder every time, otherwise, will got exception
            // from setOutputFile when try to reuse
            if (recorder != null) {
                recorder.release();
                recorder = null;
            }
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setAudioChannels(1); // MONO
            recorder.setAudioSamplingRate(8000); // 8000Hz
            recorder.setAudioEncodingBitRate(64); // seems if change this to
                                                    // 128, still got same file
                                                    // size.
            // one easy way is to use temp file
            // file = File.createTempFile(PREFIX + userId, EXTENSION,
            // User.getVoicePath());
            // 用用户id等来区分文件
            voiceFileName = getVoiceFileName("uid");
            voiceFilePath = getVoiceFilePath(appContext,voiceFileName);
            file = new File(voiceFilePath);
            recorder.setOutputFile(file.getAbsolutePath());
            recorder.prepare();
            isRecording = true;
            recorder.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        startTime = new Date().getTime();
        Log.i(TAG, "start voice recording to file:" + file.getAbsolutePath());
        return file == null ? null : file.getAbsolutePath();
    }

    /**
     * stop the recoding
     * 
     * @return seconds of the voice recorded
     */

    public void discardRecording() {
        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
                recorder = null;
                if (file != null && file.exists() && !file.isDirectory()) {
                    file.delete();
                }
            } catch (IllegalStateException e) {
            } catch (RuntimeException e){}
            isRecording = false;
        }
    }

    /**
     * 停止录音，返回录音长度
     * @return
     */
    public int stopRecoding() {
        if(recorder != null){
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            
            if(file == null || !file.exists() || !file.isFile()){
                return FILE_INVALID;
            }
            if (file.length() == 0) {
                file.delete();
                return FILE_INVALID;
            }
            int seconds = (int) (new Date().getTime() - startTime) / 1000;
            Log.d(TAG, "voice recording finished. seconds:" + seconds + " file length:" + file.length());
            if (seconds < 1){
                file.delete();
                return AUDIO_TOO_SHORT;
            }
            return seconds;
        }
        return 0;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (recorder != null) {
            recorder.release();
        }
    }

    /**
     * 生成录音文件名
     * @param uid
     * @return
     */
    private String getVoiceFileName(String uid) {
        return uid + System.currentTimeMillis() + EXTENSION;
    }

    /**
     * 生成录音文件路径
     * @param voiceFileName
     * @return
     */
    private String getVoiceFilePath(Context context,String voiceFileName) {
        File file = context.getExternalFilesDir(null);
        if (!file.exists()){
            file = context.getFilesDir();
        }
        return file.getAbsolutePath() + "/" + voiceFileName;
    }

    public boolean isRecording() {
        return isRecording;
    }

    
    public String getVoiceFilePath() {
        return voiceFilePath;
    }
    
    public String getVoiceFileName() {
        return voiceFileName;
    }
}
