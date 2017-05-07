/*
 * Created on : 2015年10月6日
 */
package ager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import ager.common.Constants;

/**
 * ini配置文件读写工具类
 * 
 * @author 李海涛
 * @version 1.0
 */
public class ConfigManager {
	public static final String MUSIC = "music"; // 背景音乐配置属性名
	private String pathname = Constants.CONFIGPATH;
	private static ConfigManager configManager;
	private static Properties properties;
	
	private ConfigManager() {
		properties = new Properties();
	}
	
	/**
	 * 创建ConfigManager的实例
	 * 
	 * @return
	 */
	public static ConfigManager getInstance() {
		if (configManager == null) {
			configManager = new ConfigManager();
		}
		return configManager;
	}
	
	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		File file = new File(pathname);
		if (!file.exists()) {
			return null;
		}
		String value = null;
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			properties.load(is);
			value = properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	/**
	 * 修改属性值
	 * 
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pathname);
			properties.setProperty(key, value);
			properties.store(fos, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
