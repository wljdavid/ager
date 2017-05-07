package ager.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import ager.common.Constants;

/**
 * 关于年龄助手窗口
 * 
 * @author 李海涛
 * @version 1.0
 */
public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		Font plainFont = new Font("微软雅黑", Font.PLAIN, 12);
		setTitle("关于Ager");
		setModal(true); // 设置窗口阻塞
		setResizable(false);
		ImageIcon icon = new ImageIcon(Constants.ICON_URL);
		setIconImage(icon.getImage());
		setSize(380, 280);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel agecalLabel = new JLabel("");
			agecalLabel.setBounds(63, 10, 242, 66);
			agecalLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ImageIcon icon_large = new ImageIcon(Constants.ICON_LARGE_URL);
			agecalLabel.setIcon(icon_large);
			contentPanel.add(agecalLabel);
		}
		{
			String text = "<table align=\"center\" style=\"font:9px 微软雅黑\"><tr><td style=\"text-align:right;\">名称：</td><td>年龄助手</td></tr><tr><td style=\"text-align:right;\">版本：</td><td>1.0-20151006</td></tr><tr><td style=\"text-align:right;\">作者：</td><td>李海涛</td></tr><tr><td style=\"text-align:right;\">建议与反馈邮箱：</td><td><a href=\"mailto:lihaitaomail@126.com\">lihaitaomail@126.com</a></td></tr></table>";
			JTextPane textPane = new JTextPane();
			textPane.setBounds(38, 75, 292, 108);
			textPane.setBackground(SystemColor.control);
			textPane.setEditable(false);
			textPane.setContentType("text/html");
			textPane.setFont(plainFont);
			textPane.setText(text);
			// 超链接事件监听
			textPane.addHyperlinkListener(new HyperlinkListener() {
				public void hyperlinkUpdate(HyperlinkEvent e) {
					if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED) 
						 return; // 如果不是鼠标点击则返回
					URL linkUrl = e.getURL();
					try {
						Desktop.getDesktop().browse(linkUrl.toURI()); // 打开超链接关键代码
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(getParent(), "打开链接失败！", "错误", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			contentPanel.add(textPane);
		}
		{
			JLabel lblText = new JLabel("© 2015 李海涛");
			lblText.setForeground(Color.GRAY);
			lblText.setFont(plainFont);
			lblText.setBounds(141, 193, 86, 15);
			contentPanel.add(lblText);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确认");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose(); // 关闭对话框
					}
				});
				okButton.setFont(plainFont);
				okButton.setActionCommand("OK");
				okButton.setFocusPainted(false); // 设置不绘制焦点边框
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
