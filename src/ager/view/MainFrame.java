package ager.view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ager.common.Constants;
import ager.util.AgeUtil;
import ager.util.CalendarUtil;
import ager.util.ChineseCalendar;

/**
 * 年龄助手主界面
 * 
 * @author 李海涛
 * @version 1.0
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // 主面板
	private JTable table; // 表格
	private DefaultTableModel tableModel; // 表格数据
	private JLabel queryBtn; // 查询按钮
	private ImageIcon queryIcon; // 查询按钮图标
	private ImageIcon queryIconGo; // 鼠标移到按钮时的图标
	private ButtonGroup bg; // 农历生日 公历生日 按钮集合
	private JComboBox glNianCB; // 公历年下拉框
	private JComboBox nlNianCB; // 农历年下拉框
	private JComboBox glYueCB; // 公历月下拉框
	private JComboBox nlYueCB; // 农历月下拉框
	private JComboBox glRiCB; // 公历日下拉框
	private JComboBox nlRiCB; // 农历日下拉框
	
	private int age; // 周岁
	private int nominalAge; // 虚岁
	private String animalsYear = ""; // 生肖
	private String starSigns = ""; // 星座
	private String glBirthday = ""; // 公历生日
	private String nlBirthday = ""; // 农历生日
	private String glFestival = ""; // 公历节日
	private String nlFestival = ""; // 农历节日
	private long days; // 生活天数
	private long toBirthDays; // 距下次公历生日天数
	private String nextBirthWeek = ""; // 下次生日星期几
	
	private String[] nlYueArr = { "正", "二", "三", "四", "五", "六", "七", "八", "九", "十",
			"冬", "腊", "闰正", "闰二", "闰三", "闰四", "闰五", "闰六", "闰七", "闰八", "闰九",
			"闰十", "闰冬", "闰腊" };
	private String[] nlRiArr = { "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八",
			"初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八",
			"十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八",
			"廿九", "三十" };

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		try {
			// 设置界面风格为系统默认风格
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int screenWidth = kit.getScreenSize().width; // 屏幕宽度
		int screenHeight = kit.getScreenSize().height; // 屏幕高度
		int frameWidth = 450; // 主窗口宽度
		int frameHeight = 420; // 主窗口高度
		setBounds(screenWidth / 2 - frameWidth / 2, screenHeight / 2 - frameHeight / 2, frameWidth, frameHeight);
		Date today = new Date();
		String glDateStr = CalendarUtil.dateToStr(today); // 今天公历日期
		String nlDateStr = new ChineseCalendar(today).toString(); // 今天农历日期
		setTitle("年龄助手 - " + glDateStr + " " + nlDateStr);
		ImageIcon icon = new ImageIcon(Constants.ICON_URL);
		setIconImage(icon.getImage());
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setToolTipText("");
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		Font plainFont = new Font("微软雅黑", Font.PLAIN, 12);
		Font boldFont = new Font("微软雅黑", Font.BOLD, 12);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(plainFont);
		menuBar.setBounds(0, 0, 444, 21);
		contentPane.add(menuBar);
		
		JMenu ager = new JMenu("Ager");
		ager.setFont(plainFont);
		menuBar.add(ager);
		
		JMenuItem close = new JMenuItem("关闭");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		close.setFont(plainFont);
		ager.add(close);
		
		JMenu tools = new JMenu("工具");
		tools.setFont(plainFont);
		menuBar.add(tools);
		
		JMenuItem export = new JMenuItem("导出");
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				export();
			}
		});
		ImageIcon exportIcon = new ImageIcon(Constants.ICON_EXPORT_URL);
		export.setIcon(exportIcon);
		export.setFont(plainFont);
		tools.add(export);
		
		JMenuItem options = new JMenuItem("选项");
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toOptions();
			}
		});
		options.setFont(plainFont);
		tools.add(options);
		
		JMenu help = new JMenu("帮助");
		help.setFont(plainFont);
		menuBar.add(help);
		
		JMenuItem knowledge = new JMenuItem("常识");
		knowledge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toKnowledge();
			}
		});
		knowledge.setFont(plainFont);
		help.add(knowledge);
		
		JMenuItem checkUpdate = new JMenuItem("检查更新");
		checkUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// getRootPane() 表示在根面板中间位置弹出对话框
				JOptionPane.showMessageDialog(getRootPane(), "没有版本更新！", "", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		checkUpdate.setFont(plainFont);
		help.add(checkUpdate);
		
		JMenuItem about = new JMenuItem("关于Ager");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toAbout();
			}
		});
		about.setFont(plainFont);
		help.add(about);
		
		List<String> nianList = new ArrayList<String>();
		List<String> glYueList = new ArrayList<String>();
		List<String> glRiList = new ArrayList<String>();
		for (int i = 1901; i <= 2099; i++) {
			nianList.add(i + "");
		}
		for (int i = 1; i <= 12; i++) {
			glYueList.add(i + "");
		}
		for (int i = 1; i <= 31; i++) {
			glRiList.add(i + "");
		}
		
		JRadioButton glRadio = new JRadioButton("公历生日");
		glRadio.setActionCommand("gl");
		glRadio.setFocusPainted(false); // 设置无焦点边框
		glRadio.setSelected(true);
		glRadio.setFont(boldFont);
		glRadio.setBackground(SystemColor.textHighlight);
		glRadio.setBounds(47, 50, 73, 23);
		contentPane.add(glRadio);
		
		JRadioButton nlRadio = new JRadioButton("农历生日");
		nlRadio.setActionCommand("nl");
		nlRadio.setFocusPainted(false); // 设置无焦点边框
		nlRadio.setFont(boldFont);
		nlRadio.setBackground(SystemColor.textHighlight);
		nlRadio.setBounds(47, 78, 73, 23);
		contentPane.add(nlRadio);
		
		bg = new ButtonGroup();
		bg.add(glRadio);
		bg.add(nlRadio);
		
		glNianCB = new JComboBox();
		glNianCB.setMaximumRowCount(15);
		glNianCB.setFont(plainFont);
		glNianCB.setModel(new DefaultComboBoxModel(nianList.toArray()));
		glNianCB.setBounds(132, 51, 54, 21);
		contentPane.add(glNianCB);
		
		nlNianCB = new JComboBox();
		nlNianCB.setMaximumRowCount(15);
		nlNianCB.setFont(plainFont);
		nlNianCB.setModel(new DefaultComboBoxModel(nianList.toArray()));
		nlNianCB.setBounds(132, 79, 54, 21);
		contentPane.add(nlNianCB);
		
		JTextPane glNianText = new JTextPane();
		glNianText.setFont(plainFont);
		glNianText.setEditable(false);
		glNianText.setBackground(SystemColor.textHighlight);
		glNianText.setText("年");
		glNianText.setBounds(187, 51, 18, 21);
		contentPane.add(glNianText);
		
		JTextPane nlNianText = new JTextPane();
		nlNianText.setText("年");
		nlNianText.setFont(plainFont);
		nlNianText.setEditable(false);
		nlNianText.setBackground(SystemColor.textHighlight);
		nlNianText.setBounds(187, 79, 18, 21);
		contentPane.add(nlNianText);
		
		glYueCB = new JComboBox();
		glYueCB.setFont(plainFont);
		glYueCB.setModel(new DefaultComboBoxModel(glYueList.toArray()));
		glYueCB.setBounds(207, 51, 40, 21);
		contentPane.add(glYueCB);
		
		nlYueCB = new JComboBox();
		nlYueCB.setFont(plainFont);
		nlYueCB.setModel(new DefaultComboBoxModel(nlYueArr));
		nlYueCB.setBounds(207, 79, 52, 21);
		contentPane.add(nlYueCB);
		
		JTextPane glYueText = new JTextPane();
		glYueText.setFont(plainFont);
		glYueText.setEditable(false);
		glYueText.setBackground(SystemColor.textHighlight);
		glYueText.setText("月");
		glYueText.setBounds(249, 51, 18, 21);
		contentPane.add(glYueText);
		
		JTextPane nlYueText = new JTextPane();
		nlYueText.setText("月");
		nlYueText.setFont(plainFont);
		nlYueText.setEditable(false);
		nlYueText.setBackground(SystemColor.textHighlight);
		nlYueText.setBounds(259, 78, 18, 21);
		contentPane.add(nlYueText);
		
		glRiCB = new JComboBox();
		glRiCB.setFont(plainFont);
		glRiCB.setModel(new DefaultComboBoxModel(glRiList.toArray()));
		glRiCB.setBounds(269, 51, 40, 21);
		contentPane.add(glRiCB);
		
		nlRiCB = new JComboBox();
		nlRiCB.setFont(plainFont);
		nlRiCB.setModel(new DefaultComboBoxModel(nlRiArr));
		nlRiCB.setBounds(279, 79, 52, 21);
		contentPane.add(nlRiCB);
		
		JTextPane riText = new JTextPane();
		riText.setFont(plainFont);
		riText.setEditable(false);
		riText.setBackground(SystemColor.textHighlight);
		riText.setText("日");
		riText.setBounds(313, 51, 18, 21);
		contentPane.add(riText);
		
		// 查询按钮
		queryBtn = new JLabel();
		queryIcon = new ImageIcon(Constants.ICON_QUERYBTN_1_URL);
		queryIconGo = new ImageIcon(Constants.ICON_QUERYBTN_2_URL);
		queryBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				query();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				queryBtn.setIcon(queryIconGo);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				queryBtn.setIcon(queryIcon);
			}
		});
		queryBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 设置鼠标为手形
		queryBtn.setBackground(SystemColor.textHighlight);
		queryBtn.setIcon(queryIcon);
		queryBtn.setBounds(345, 50, 50, 50);
		contentPane.add(queryBtn);
		
		// 表格
		table = new JTable();
		table.setBackground(SystemColor.text);
		table.setFont(boldFont);
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		tableModel = new DefaultTableModel(
			new Object[][] {
				{"周岁", ""},
				{"虚岁", ""},
				{"生肖", ""},
				{"星座", ""},
				{"公历生日", ""},
				{"农历生日", ""},
				{"生活天数", ""},
				{"下次公历生日", ""}
			},
			new String[] {
				"name", "description"
			}
		);
		table.setModel(tableModel);
		DefaultTableCellRenderer nameRender = new DefaultTableCellRenderer();
		nameRender.setHorizontalAlignment(SwingConstants.CENTER); // 设置字体居中
	    MyTableCellRenderer desRender = new MyTableCellRenderer();
	    desRender.setFont(plainFont);
	    table.getColumnModel().getColumn(0).setCellRenderer(nameRender);
	    table.getColumnModel().getColumn(1).setCellRenderer(desRender);
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(310);
		table.setRowHeight(28);
		table.setBounds(27, 120, 390, 224);
		contentPane.add(table);
		
		JLabel lblText = new JLabel();
		lblText.setForeground(SystemColor.inactiveCaptionText);
		lblText.setFont(plainFont);
		lblText.setText("© 2015 李海涛");
		lblText.setBounds(177, 356, 90, 21);
		contentPane.add(lblText);
	}
	
	/**
	 * 查询
	 */
	private void query() {
		int year = 1901;
		int month = 1;
		int day = 1;
		if ("gl".equals(bg.getSelection().getActionCommand())) {
			// 根据公历生日查询
			year = Integer.parseInt(glNianCB.getSelectedItem().toString()); // 输入年 公历
			month = Integer.parseInt(glYueCB.getSelectedItem().toString()); // 输入月 公历
			day = Integer.parseInt(glRiCB.getSelectedItem().toString()); // 输入日 公历
			int lastDay = 0;
			
			if (month == 2) { // 如果是2月
				if (CalendarUtil.isLeapYear(year)) { // 是否闰年
					lastDay = 29;
				} else {
					lastDay = 28;
				}
				if (day > lastDay) {
					JOptionPane.showMessageDialog(getRootPane(),
							year + "年2月共有" + lastDay + "天！请重新选择！", "",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				// 4 6 9 11 共30天
				lastDay = 30;
				if (day > lastDay) {
					JOptionPane.showMessageDialog(getRootPane(),
							month + "月共有30天！请重新选择！", "",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		} else {
			// 根据农历生日查询
			int yue = 1;
			int ri = 1;
			boolean isLeapMonth = false;
			int nlYear = Integer.parseInt(nlNianCB.getSelectedItem().toString()); // 输入年 农历
			String nlYue = nlYueCB.getSelectedItem().toString(); // 输入月 农历
			String nlRi = nlRiCB.getSelectedItem().toString(); // 输入日 农历
			for (int i = 0; i < nlYueArr.length; i++) {
				if (nlYue.equals(nlYueArr[i])) {
					if (i > 11) {
						yue = i - 11;
						isLeapMonth = true;
					} else {
						yue = i + 1;
						isLeapMonth = false;
					}
					break;
				}
			}
			for (int i = 0; i < nlRiArr.length; i++) {
				if (nlRi.equals(nlRiArr[i])) {
					ri = i + 1;
					break;
				}
			}
			int[] glDateArr = ChineseCalendar.lunarToSolar(nlYear, yue, ri, isLeapMonth); // 农历转公历
			year = glDateArr[0]; // 公历年 
			month = glDateArr[1]; // 公历月
			day = glDateArr[2]; // 公历日
		}
			
		// 是否大于今天
		if (CalendarUtil.getToDays(year, month, day) > 0) {
			JOptionPane.showMessageDialog(getRootPane(),
					"你选择的这一天还没到来！请重新选择！", "",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String dateStr = year + "-" + month + "-" + day; // 输入日期 yyyy-MM-dd
		AgeUtil service = new AgeUtil(dateStr);
		age = service.getAge();
		nominalAge = service.getNominalAge();
		animalsYear = service.getAnimalsYear();
		starSigns = service.getStarSigns();
		glBirthday = service.getGLBirthday();
		nlBirthday = service.getNLBirthday();
		glFestival = service.getGLFestival();
		nlFestival = service.getNLFestival();
		days = service.getDays();
		toBirthDays = service.getToGLBirthDays();
		nextBirthWeek = service.getNextGLBirthWeek();
		
		// 给结果显示表格中设值
		tableModel.setValueAt(age + "岁", 0, 1); // 周岁
		tableModel.setValueAt(nominalAge + "岁", 1, 1); // 虚岁
		tableModel.setValueAt(animalsYear, 2, 1); // 生肖
		tableModel.setValueAt(starSigns, 3, 1); // 星座
		tableModel.setValueAt(glBirthday + " " + glFestival, 4, 1); // 公历生日 + 节日
		tableModel.setValueAt(nlBirthday + " " + nlFestival, 5, 1); // 农历生日 + 节日
		tableModel.setValueAt("你在这个世界上生活了：" + days + "天", 6, 1); // 生活天数
		tableModel.setValueAt("距下次生日还有：" + toBirthDays + "天，那天是星期" + nextBirthWeek, 7, 1); // 下次公历生日
	}
	
	/**
	 * 选项
	 */
	private void toOptions() {
		try {
			OptionsDialog dialog = new OptionsDialog();
			int dialogWidth = dialog.getWidth();
			int dialogHeight = dialog.getHeight();
			int dialogX = getX() + (getWidth() / 2 - dialogWidth / 2);
			int dialogY = getY() + (getHeight() / 2 - dialogHeight / 2);
			dialog.setLocation(dialogX, dialogY); // 设置窗口的位置在主窗口的中间显示
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出
	 */
	private void export() {
		String text = "\r\n★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆"
					+ "\r\n                       " + new Date().toLocaleString()
					+ "\r\n周岁：" + age + "岁"
					+ "\r\n虚岁：" + nominalAge + "岁"
					+ "\r\n生肖：" + animalsYear
					+ "\r\n星座：" + starSigns
					+ "\r\n公历生日：" + glBirthday + " " + glFestival
					+ "\r\n农历生日：" + nlBirthday + " " + nlFestival
					+ "\r\n你在这个世界上生活了：" + days + "天"
					+ "\r\n距下次生日还有：" + toBirthDays + "天，那天是星期" + nextBirthWeek
					+ "\r\n★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆";
		FileSystemView fsv = FileSystemView.getFileSystemView(); // 得到系统视图对象
	    File root = fsv.getHomeDirectory(); // 获取桌面的路径
		JFileChooser fileChooser = new JFileChooser(root.getPath());
		fileChooser.setSelectedFile(new File("/年龄助手.txt")); // 默认文件名
		int option = fileChooser.showSaveDialog(getRootPane());
        if(option == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			FileWriter writer = null;
			try {
				writer = new FileWriter(file);
				char[] arry = text.toCharArray();
				writer.write(arry);
				JOptionPane.showMessageDialog(getRootPane(), "已保存至 " + file.toString(), "成功", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.flush();
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
        }
	}
	
	/**
	 * 常识
	 */
	private void toKnowledge() {
		try {
			KnowledgeDialog dialog = new KnowledgeDialog();
			int dialogWidth = dialog.getWidth();
			int dialogHeight = dialog.getHeight();
			int dialogX = getX() + (getWidth() / 2 - dialogWidth / 2);
			int dialogY = getY() + (getHeight() / 2 - dialogHeight / 2);
			dialog.setLocation(dialogX, dialogY); // 设置窗口的位置在主窗口的中间显示
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关于年龄助手
	 */
	private void toAbout() {
		try {
			AboutDialog dialog = new AboutDialog();
			int dialogWidth = dialog.getWidth();
			int dialogHeight = dialog.getHeight();
			int dialogX = getX() + (getWidth() / 2 - dialogWidth / 2);
			int dialogY = getY() + (getHeight() / 2 - dialogHeight / 2);
			dialog.setLocation(dialogX, dialogY); // 设置窗口的位置在主窗口的中间显示
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * 单元格渲染器
 * 
 * @author 李海涛
 * @version 1.0
 */
class MyTableCellRenderer extends JLabel implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setHorizontalAlignment(SwingConstants.CENTER);
		setValue(value);
		return this;
	}
	
	protected void setValue(Object value) {
        setText((value == null) ? "" : value.toString());
    }
	
}
