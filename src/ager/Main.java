package ager;

import java.awt.EventQueue;

import ager.util.AudioManager;
import ager.util.ConfigManager;
import ager.view.MainFrame;

/**
 * 程序入口
 * 
 * @author 李海涛
 * @version 1.0
 */
public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String actionCommand = ConfigManager.getInstance().getProperty(ConfigManager.MUSIC); // 读取配置文件
		AudioManager.getInstance().setMusic(actionCommand); // 播放背景音乐
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
