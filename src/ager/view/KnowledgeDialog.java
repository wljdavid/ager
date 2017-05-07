package ager.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import ager.common.Constants;

/**
 * 常识窗口
 * 
 * @author 李海涛
 * @version 1.0
 */
public class KnowledgeDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public KnowledgeDialog() {
		Font plainFont = new Font("微软雅黑", Font.PLAIN, 12);
		Font boldFont = new Font("微软雅黑", Font.BOLD, 12);
		ImageIcon icon = new ImageIcon(Constants.ICON_KNOWLEDGE_URL);
		setModal(true); // 设置窗口阻塞
		setResizable(false);
		setIconImage(icon.getImage());
		setTitle("常识");
		setSize(420, 280);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel label = new JLabel("关于虚岁/周岁");
			label.setFont(boldFont);
			contentPanel.add(label);
		}
		{
			String text = "\r\n虚岁只和过年有关，周岁只和生日有关\r\n出生时1虚岁，0周岁\r\n过完春节长一虚岁，过完生日长一周岁\r\n虚岁 = 今年 - 出生年 + 1\r\n周岁 = 今年 - 出生年（已过生日）（未过生日还要 - 1）\r\n虚岁，周岁传统都用农历计算\r\n在过年到生日期间 虚岁 - 周岁 = 2（即虚两岁）\r\n在生日到过年期间 虚岁 - 周岁 = 1（即虚一岁）\r\n";
			JTextPane textPane = new JTextPane();
			textPane.setEditable(false);
			textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			textPane.setFont(new Font("楷体", Font.PLAIN, 14));
			textPane.setText(text);
			contentPanel.add(textPane);
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
