package client.view;

import client.bean.Delivery;
import client.bean.Final;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.Dialog.ModalityType;

import javax.swing.*;

/**
 * 视图类
 */
public class View {

    private JFrame frame;
    private JDialog statusFrame;

    public View() {
        frame = new JFrame("RUC外卖柜");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
    }

    private void initStatusFrame() {
        statusFrame = new JDialog(frame, "外卖柜占用情况", ModalityType.APPLICATION_MODAL);
        statusFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        statusFrame.setSize(1200, 300);
        statusFrame.setLocationRelativeTo(frame);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void verifyError() {
        showMessage("认证失败 !");
    }

    public void connectError() {
        showMessage("连接失败 !");
    }

    public void welcome() {
        showMessage("欢迎使用RUC外卖柜 !");
    }

    public void bye() {
        showMessage("感谢您选择RUC外卖柜 ! 再见 !");
    }

    public void failure() {
        showMessage("操作有误 !");
    }

    public void success() {
        showMessage("操作成功 !");
    }

    public void full() {
        showMessage("外卖柜空间不足 !");
    }

    public void printAllDelivery(LinkedList<String> occupied) {
        if (statusFrame == null) {
            initStatusFrame();
        }
        JPanel panel = new JPanel(new GridLayout(5, 20));

        Set<Integer> occupiedNumbers = new HashSet<>();
        for (String record : occupied) {
            Pattern pattern = Pattern.compile("外卖柜编号：(\\d+)");
            Matcher matcher = pattern.matcher(record);
            if (matcher.find()) {
                occupiedNumbers.add(Integer.parseInt(matcher.group(1)));
            }
        }
        // MAX_SIZE为100
        for (int i = 1; i <= Final.MAX_SIZE; i++) {
            JLabel label = new JLabel(String.format("%03d", i));
            label.setOpaque(true);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (occupiedNumbers.contains(i)) {
                label.setBackground(Color.DARK_GRAY);
            } else {
                label.setBackground(Color.LIGHT_GRAY);
            }
            panel.add(label);
        }
        statusFrame.getContentPane().removeAll();
        statusFrame.add(panel);
        statusFrame.revalidate();
        statusFrame.repaint();
        statusFrame.setVisible(true);
    }

    public void printDelivery(String str) {
        showMessage("请牢记取餐码 ! ! !\n\n" + str);
    }

    public int mainMenu() {
        String[] options = { "用户", "外卖员", "退出" };
        int choice = JOptionPane.showOptionDialog(frame, "请选择身份：", "身份选择",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return (choice + 1) % 3; // (1-用户 2-外卖员 0-退出)
    }

    public int userMenu() {
        String code = JOptionPane.showInputDialog(frame, "请输入取餐码：");;
        while(!code.matches("\\d{6}")) {        
            code = JOptionPane.showInputDialog(frame, "请重新输入6位取餐码：");
        }
        return Integer.parseInt(code);
    }

    public int adminMenu() {
        String[] options = { "录入外卖", "删除外卖", "查看外卖柜占用情况", "退出" };
        int choice = JOptionPane.showOptionDialog(frame, "请选择操作：", "外卖员菜单",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return (choice + 1) % 4; // (1-录入外卖 2-删除外卖 3-查看外卖柜占用情况 0-退出)
    }

    public Delivery add() {
        Delivery delivery = new Delivery();
        String deliveryPho = JOptionPane.showInputDialog(frame, "请输入新的外卖手机号：");;
        while(!deliveryPho.matches("\\d{11}")){
            deliveryPho = JOptionPane.showInputDialog(frame, "请重新输入11位手机号：");
        }
        delivery.setDeliveryPho(deliveryPho);
        String deliveryCom = JOptionPane.showInputDialog(frame, "请输入新的外卖平台：");
        while(deliveryCom == null || deliveryCom.trim().isEmpty()){
            deliveryCom = JOptionPane.showInputDialog(frame, "请重新输入外卖平台：");
        }
        delivery.setDeliveryCom(deliveryCom);
        return delivery;
    }

    public int doSth() {
        String code = JOptionPane.showInputDialog(frame, "请输入要操作外卖的取餐码：");;
        while(!code.matches("\\d{6}")) {        
            code = JOptionPane.showInputDialog(frame, "请重新输入6位取餐码：");
        }
        int confirm = JOptionPane.showConfirmDialog(frame, "确认继续吗？此操作不可恢复 ! ! !", "确认操作", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return -1;
        }
        return Integer.parseInt(code);
    }
}
