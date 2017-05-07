package ager.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import ager.common.Constants;
import ager.util.AudioManager;
import ager.util.ConfigManager;

/**
 * 选项-设置背景音乐窗口
 * 
 * @author 李海涛
 * @version 1.0
 */
public class OptionsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton noMusicRadio; // 无背景音乐
	private JRadioButton defaultRadio; // 默认音乐
	private JRadioButton radio1;
	private JRadioButton radio2;
	private JRadioButton radio3;
	private JRadioButton radio4;
	private JRadioButton radio5;
	private JRadioButton radio6;

	/**
	 * Create the dialog.
	 */
	public OptionsDialog() {
		Font plainFont = new Font("微软雅黑", Font.PLAIN, 12);
		Font boldFont = new Font("微软雅黑", Font.BOLD, 12);
		ImageIcon icon = new ImageIcon(Constants.ICON_OPTIONS_URL);
		setModal(true); // 设置窗口阻塞
		setResizable(false);
		setIconImage(icon.getImage());
		setTitle("选项");
		setSize(360, 260);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
		contentPanel.setLayout(new GridLayout(5, 2, 0, 0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel musicLabel = new JLabel("设置背景音乐");
			ImageIcon musicIcon = new ImageIcon(Constants.ICON_MUSIC_URL);
			musicLabel.setIcon(musicIcon);
			musicLabel.setFont(boldFont);
			contentPanel.add(musicLabel);
		}
		{
			// 空标签，用来占位
			JLabel emptyLabel = new JLabel("");
			contentPanel.add(emptyLabel);
		}
		{
			noMusicRadio = new JRadioButton(Constants.NO_MUSIC_TEXT);
			noMusicRadio.setActionCommand(Constants.NO_MUSIC_CODE); // 设置动作命令字符串
			noMusicRadio.setFocusPainted(false); // 设置不绘制焦点边框
			noMusicRadio.setFont(plainFont);
			buttonGroup.add(noMusicRadio);
			contentPanel.add(noMusicRadio);
		}
		{
			defaultRadio = new JRadioButton(Constants.MUSIC_TEXT_DEFAULT);
			defaultRadio.setSelected(true); // 默认选中
			defaultRadio.setActionCommand(Constants.MUSIC_CODE_DEFAULT); // 设置动作命令字符串
			defaultRadio.setFocusPainted(false); // 设置不绘制焦点边框
			defaultRadio.setFont(plainFont);
			buttonGroup.add(defaultRadio);
			contentPanel.add(defaultRadio);
		}
		{
			radio1 = new JRadioButton(Constants.MUSIC_TEXT_1);
			radio1.setActionCommand(Constants.MUSIC_CODE_1); // 设置动作命令字符串
			radio1.setFocusPainted(false); // 设置不绘制焦点边框
			radio1.setFont(plainFont);
			buttonGroup.add(radio1);
			contentPanel.add(radio1);
		}
		{
			radio2 = new JRadioButton(Constants.MUSIC_TEXT_2);
			radio2.setActionCommand(Constants.MUSIC_CODE_2); // 设置动作命令字符串
			radio2.setFocusPainted(false); // 设置不绘制焦点边框
			radio2.setFont(plainFont);
			buttonGroup.add(radio2);
			contentPanel.add(radio2);
		}
		{
			radio3 = new JRadioButton(Constants.MUSIC_TEXT_3);
			radio3.setActionCommand(Constants.MUSIC_CODE_3); // 设置动作命令字符串
			radio3.setFocusPainted(false); // 设置不绘制焦点边框
			radio3.setFont(plainFont);
			buttonGroup.add(radio3);
			contentPanel.add(radio3);
		}
		{
			radio4 = new JRadioButton(Constants.MUSIC_TEXT_4);
			radio4.setActionCommand(Constants.MUSIC_CODE_4); // 设置动作命令字符串
			radio4.setFocusPainted(false); // 设置不绘制焦点边框
			radio4.setFont(plainFont);
			buttonGroup.add(radio4);
			contentPanel.add(radio4);
		}
		{
			radio5 = new JRadioButton(Constants.MUSIC_TEXT_5);
			radio5.setActionCommand(Constants.MUSIC_CODE_5); // 设置动作命令字符串
			radio5.setFocusPainted(false); // 设置不绘制焦点边框
			radio5.setFont(plainFont);
			buttonGroup.add(radio5);
			contentPanel.add(radio5);
		}
		{
			radio6 = new JRadioButton(Constants.MUSIC_TEXT_6);
			radio6.setActionCommand(Constants.MUSIC_CODE_6); // 设置动作命令字符串
			radio6.setFocusPainted(false); // 设置不绘制焦点边框
			radio6.setFont(plainFont);
			buttonGroup.add(radio6);
			contentPanel.add(radio6);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EmptyBorder(10, 0, 0, 0));
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确认");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						String actionCommand = buttonGroup.getSelection().getActionCommand();
						AudioManager.getInstance().setMusic(actionCommand); // 设置背景音乐
						ConfigManager.getInstance().setProperty(ConfigManager.MUSIC, actionCommand); // 设置配置文件
					}
				});
				okButton.setFont(plainFont);
				okButton.setActionCommand("OK");
				okButton.setFocusPainted(false); // 设置不绘制焦点边框
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton); // 确认按钮为默认按钮
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose(); // 关闭对话框
					}
				});
				cancelButton.setFont(plainFont);
				cancelButton.setActionCommand("Cancel");
				cancelButton.setFocusPainted(false); // 设置不绘制焦点边框
				buttonPane.add(cancelButton);
			}
			{
				JButton applyButton = new JButton("应用");
				applyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String actionCommand = buttonGroup.getSelection().getActionCommand();
						AudioManager.getInstance().setMusic(actionCommand); // 设置背景音乐
						ConfigManager.getInstance().setProperty(ConfigManager.MUSIC, actionCommand); // 设置配置文件
					}
				});
				applyButton.setFont(plainFont);
				applyButton.setActionCommand("Apply");
				applyButton.setFocusPainted(false); // 设置不绘制焦点边框
				buttonPane.add(applyButton);
			}
		}
		String actionCommand = ConfigManager.getInstance().getProperty(ConfigManager.MUSIC); // 读取配置文件
		// 选中用户设置音乐的按钮
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		while (buttons.hasMoreElements()) {
			AbstractButton button = buttons.nextElement();
			if (button.getActionCommand().equals(actionCommand)) {
				buttonGroup.setSelected(button.getModel(), true);
				break;
			}
		}
	}

}
