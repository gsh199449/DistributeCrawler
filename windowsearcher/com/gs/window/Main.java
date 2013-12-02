package com.gs.window;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import com.gs.searcher.Indexer;
import com.gs.searcher.Hit;
import com.gs.searcher.PagePOJO;
import com.gs.searcher.Searcher;

public class Main {

	private JFrame frame;
	private JTextField textField;
	private final Action action = new SwingAction();
	private JTextField txtDtestjson;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("新闻搜索");
		frame.setBounds(100, 100, 750, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel, "name_1607874912653");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 752, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("新闻搜索", null, panel_1, null);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		
		
		JLabel lblNewLabel = new JLabel("指定路径");
		
		txtDtestjson = new JTextField();
		txtDtestjson.setText("D://Test//Json");
		txtDtestjson.setColumns(10);
		
		final JCheckBox checkBox = new JCheckBox("显示新闻内容");
		JButton button = new JButton("搜索");
		JCheckBox checkBox_1 = new JCheckBox("聚类");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(21)
							.addComponent(txtDtestjson, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(60)
							.addComponent(lblNewLabel)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(111)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 105, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button)
							.addGap(148)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox)
						.addComponent(checkBox_1))
					.addGap(130))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 701, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(8)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtDtestjson, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkBox))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(checkBox_1)
								.addComponent(button))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		final JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		panel_1.setLayout(gl_panel_1);
		
		button.setAction(action);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("站点测试", null, panel_2, null);
		
		JButton button_1 = new JButton("测试");
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(337)
							.addComponent(button_1)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(22)
					.addComponent(button_1)
					.addGap(18)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
		);
		
		final JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String url[] = {"http://202.206.64.193","http://202.206.64.193/caifeng","http://202.206.64.193/blog","http://202.206.64.193/phpMyAdmin"};
				textArea.append("测试时间"+new Date(System.currentTimeMillis()).toLocaleString()+"\n");
				for (int i = 0; i < url.length; i++) {
					HttpClient hc = new HttpClient();
					GetMethod get;
					hc.setConnectionTimeout(5000);
					get = new GetMethod(url[i]);
					int status = 0;
					try {
						status = hc.executeMethod(get);
					} catch (HttpException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						textArea.append("站点："+url[i]+ "\n状态码："+status +"\n"+(status==200?"正常":"失败")+"\n=========\n");
					}
					get.releaseConnection();
					textArea.paintImmediately(textArea.getBounds());
				}
			}
		});
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Index", null, panel_3, null);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		final JLabel lblNewLabel_2 = new JLabel("请稍候");
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Indexer i = new Indexer();
				i.index(textField_2.getText(), textField_1.getText());
				lblNewLabel_2.setText("Finish");
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("json");
		
		JLabel lblIndex = new JLabel("index");
		
		
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(197)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblIndex))
							.addGap(43)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(282)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2)
								.addComponent(btnNewButton))))
					.addContainerGap(372, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(33)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIndex))
					.addGap(37)
					.addComponent(btnNewButton)
					.addGap(28)
					.addComponent(lblNewLabel_2)
					.addContainerGap(194, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Searcher s = new Searcher(txtDtestjson.getText());
				LinkedList<Hit> list = s.search(textField.getText());
				String re = "";
				for(Hit h : list){
					PagePOJO pojo = h.getPagePOJO();
					re+=pojo.title+"\n";
					if(checkBox.isSelected()){
						re+=pojo.content+"\n====================================\n";
					}
				}
				if(re.equals(""))re="找不到"+textField.getText();
				textPane.setText(re);
				
			}
		});

	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "搜索");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
