package com.chatui.module.input;

import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chatui.R;
import com.chatui.model.ChatMessage;
import com.chatui.module.Container;
import com.chatui.module.audio.AudioRecorder;

/**
 * Created by admin on 2017/5/25.
 */

public class AudioPanel {

    private Container container;
    private Chronometer time;
    private TextView timerTip;
    private LinearLayout timerTipContainer;
    private boolean started = false;
    private boolean cancelled = false;
    private boolean touched = false; // 是否按着
    protected Button audioRecordBtn; // 录音按钮
    protected View audioAnimLayout; // 录音动画布局
    private AudioRecorder audioRecorder;


    public AudioPanel(Container container, View view) {

        this.container = container;

        // 语音
        audioRecordBtn = (Button) view.findViewById(R.id.audioRecord);
        audioAnimLayout = view.findViewById(R.id.layoutPlayAudio);
        time = (Chronometer) view.findViewById(R.id.timer);
        timerTip = (TextView) view.findViewById(R.id.timer_tip);
        timerTipContainer = (LinearLayout) view.findViewById(R.id.timer_tip_container);
        initAudioRecordButton();

    }

    public void onPause() {
        if (audioRecorder != null){
            onEndAudioRecord(true);
        }
    }

    /**
     * 显示录音按钮
     */
    public void showAudioBtn(){
        if (audioRecordBtn != null){
            audioRecordBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏录音按钮
     */
    public void hiddenAudioBtn(){
        if (audioRecordBtn != null){
            audioRecordBtn.setVisibility(View.GONE);
        }
    }

    /**
     * ****************************** 语音 ***********************************
     */
    private void initAudioRecordButton() {
        audioRecordBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touched = true;
                    initAudioRecord();
                    onStartAudioRecord();
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    touched = false;
                    onEndAudioRecord(isCancelled(v, event));
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    touched = true;
                    cancelAudioRecord(isCancelled(v, event));
                }

                return false;
            }
        });
    }

    // 上滑取消录音判断
    private static boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
    }

    /**
     * 初始化AudioRecord
     */
    private void initAudioRecord() {
        if (audioRecorder == null) {
            audioRecorder = new AudioRecorder();
        }
    }

    /**
     * 开始语音录制
     */
    private void onStartAudioRecord() {

        // 录音当前正在播放语音，需要停止播放
        container.proxy.onStartAudioRecord();

        container.activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        cancelled = false;

        audioRecorder.startRecording(container.activity);
        onRecordStart();
        cancelled = false;
    }

    /**
     * 结束语音录制
     *
     * @param cancel
     */
    private void onEndAudioRecord(boolean cancel) {
        started = false;
        container.activity.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (cancel){
            audioRecorder.discardRecording(); // 取消录音
        } else {
            int time = audioRecorder.stopRecoding();
            if (time > 0){
                // 发送录音消息
                container.proxy.sendMessage(new ChatMessage());
            } else if (time == AudioRecorder.AUDIO_TOO_SHORT){
                Snackbar.make(audioRecordBtn,R.string.recording_too_short,Snackbar.LENGTH_SHORT).show();
            } else if (time == AudioRecorder.FILE_INVALID) {
                Snackbar.make(audioRecordBtn,R.string.recording_file_invalid,Snackbar.LENGTH_SHORT).show();
            }
        }

        audioRecordBtn.setText(R.string.record_audio);
        audioRecordBtn.setBackgroundResource(R.drawable.nim_message_input_edittext_box);
        stopAudioRecordAnim();
    }

    /**
     * 取消语音录制
     *
     * @param cancel
     */
    private void cancelAudioRecord(boolean cancel) {
        // reject
        if (!started) {
            return;
        }
        // no change
        if (cancelled == cancel) {
            return;
        }

        cancelled = cancel;
        updateTimerTip(cancel);
    }

    /**
     * 正在进行语音录制和取消语音录制，界面展示
     *
     * @param cancel
     */
    private void updateTimerTip(boolean cancel) {
        if (cancel) {
            timerTip.setText(R.string.recording_cancel_tip);
            timerTipContainer.setBackgroundResource(R.drawable.cancel_record_red_bg);
        } else {
            timerTip.setText(R.string.recording_cancel);
            timerTipContainer.setBackgroundResource(0);
        }
    }

    /**
     * 开始语音录制动画
     */
    private void playAudioRecordAnim() {
        audioAnimLayout.setVisibility(View.VISIBLE);
        time.setBase(SystemClock.elapsedRealtime());
        time.start();
    }

    /**
     * 结束语音录制动画
     */
    private void stopAudioRecordAnim() {
        audioAnimLayout.setVisibility(View.GONE);
        time.stop();
        time.setBase(SystemClock.elapsedRealtime());
    }

    public void onRecordStart() {
        started = true;
        if (!touched) {
            return;
        }

        audioRecordBtn.setText(R.string.record_audio_end);
        audioRecordBtn.setBackgroundResource(R.drawable.nim_message_input_edittext_box_pressed);

        updateTimerTip(false); // 初始化语音动画状态
        playAudioRecordAnim();
    }

}


