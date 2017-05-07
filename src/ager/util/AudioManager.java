package ager.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import ager.common.Constants;

/**
 * 背景音乐工具类
 * 
 * @author 李海涛
 * @version 1.0
 */
public class AudioManager {
	private AudioClip audio; // 背景音乐
	private static AudioManager audioManager = null;
	
	private AudioManager() {
		
	}
	
	public synchronized static AudioManager getInstance() {
		if (audioManager == null) {
			audioManager = new AudioManager();
		}
		return audioManager;
	}

	/**
	 * 播放背景音乐
	 */
	public void playAudio(String pathname) {
		File file = new File(pathname); //音乐文件所在路径
		URL url = null;
		try {
			url = file.toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		audio = Applet.newAudioClip(url);
		audio.loop(); // 循环播放
		// audio.play();
		// audio.stop() // 停止播放 
	}
	
	/**
	 * 停止播放
	 */
	public void stop() {
		if (audio != null) {
			audio.stop();
		}
	}
	
	/**
	 * 设置背景音乐
	 * 
	 * @param actionCommand 动作命令字符串
	 */
	public void setMusic(String actionCommand) {
		stop(); // 停止播放
		if (Constants.NO_MUSIC_CODE.equals(actionCommand)) {
		} else if (Constants.MUSIC_CODE_1.equals(actionCommand)) {
			playAudio(Constants.MUSIC_URL_1);
		} else if (Constants.MUSIC_CODE_2.equals(actionCommand)) {
			playAudio(Constants.MUSIC_URL_2);
		} else if (Constants.MUSIC_CODE_3.equals(actionCommand)) {
			playAudio(Constants.MUSIC_URL_3);
		} else if (Constants.MUSIC_CODE_4.equals(actionCommand)) {
			playAudio(Constants.MUSIC_URL_4);
		} else if (Constants.MUSIC_CODE_5.equals(actionCommand)) {
			playAudio(Constants.MUSIC_URL_5);
		} else if (Constants.MUSIC_CODE_6.equals(actionCommand)) {
			playAudio(Constants.MUSIC_URL_6);
		} else {
			playAudio(Constants.MUSIC_URL_DEFAULT); // 默认音乐
		}
	}
	
}
