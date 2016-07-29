/**
 * @QiaoJian
 */
package cn.edu.bjtu.auc.ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.edu.bjtu.auc.ui.utils.ResourceManager;

/**
 * @author QiaoJian
 *
 */
public class SwingComponent {
	JFrame f = new JFrame("美国高校中国留学生信息采集器V1.0");
	JComboBox<String> universityList ;
	ResourceManager resourceManager;
	List<JLabel> messageBox = new ArrayList<JLabel>();
	JPanel messagePanel;
	public void init(ActionListener actionListener){
		Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize(); 
		Insets scrInsets=Toolkit.getDefaultToolkit().getScreenInsets(
				GraphicsEnvironment.getLocalGraphicsEnvironment().
				getDefaultScreenDevice().getDefaultConfiguration()); 
		f.setSize(new Dimension(600,520));
		f.setLayout(new FlowLayout());
		/*大学选择列表*/
		JPanel uaPanel = new JPanel();
		JLabel listTitle = new JLabel("请选择大学:");
		listTitle.setFont(new Font("微软雅黑",1,14));
		uaPanel.add(listTitle);
		resourceManager = ResourceManager.getInstance();
		String[] universities = new String[resourceManager.getSchoolList().size()];
		resourceManager.getSchoolList().toArray(universities);
		universityList = new JComboBox<String>(universities);
		universityList.setPreferredSize(new Dimension(f.getWidth()-100,20));
		uaPanel.add(universityList);
		f.add(uaPanel);
		/*姓氏输入列表*/
		/*JPanel lastNamePanel = new JPanel();
		lastNamePanel.setPreferredSize(new Dimension(f.getWidth(),50));
		JLabel lastNameTitle = new JLabel("请输入姓氏:");
		lastNameTitle.setFont(new Font("微软雅黑",1,14));
		lastNamePanel.add(lastNameTitle);
		JTextField lastNameText = new JTextField(45);
		f.add(lastNamePanel);
		lastNamePanel.add(lastNameText);*/
		JButton start = new JButton("开始");
		start.setMnemonic(MouseEvent.MOUSE_CLICKED);

		f.add(start);
		messagePanel = new JPanel();
		messagePanel.setBackground(Color.white);
		messagePanel.setPreferredSize(new Dimension(f.getWidth()-30,400));
		f.add(messagePanel);


		start.addActionListener(actionListener);


	}
	public void show(){
		//f.setSize(new Dimension(500, 700));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setVisible(true);
	}
	public void createMessageBox(String msg){
		JLabel message = new JLabel();
		message.setPreferredSize(new Dimension(f.getWidth()-30,15));
		message.setFont(new Font("微软雅黑",0,12));
		message.setText("/>"+msg);
		if(messageBox.size()>19){
			JLabel old = messageBox.remove(0);
			messagePanel.remove(old);
		}
		messageBox.add(message);
		messagePanel.add(message);
		messagePanel.validate();
		f.repaint();
	}
	public JComboBox<String> getUniversityList() {
		return universityList;
	}
	public void setUniversityList(JComboBox<String> universityList) {
		this.universityList = universityList;
	}
	
}
