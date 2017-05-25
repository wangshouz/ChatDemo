package com.chatui.module;


import com.chatui.model.ChatMessage;

/**
 * 会话窗口提供给子模块的代理接口。
 */
public interface ModuleProxy {
    // 发送消息
    boolean sendMessage(ChatMessage msg);

    // 消息输入区展开时候的处理---如列表上滑至最后一条
    void onInputPanelExpand();

    // 开始录音---需要停止正在播放的语音
    void onStartAudioRecord();
}
