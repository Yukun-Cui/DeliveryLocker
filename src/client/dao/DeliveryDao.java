package client.dao;

import client.bean.Delivery;
import client.bean.Final;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * 数据操作模块
 */
public class DeliveryDao {

    /**
     * 设备认证
     */
    public boolean devicesVerify(Socket socket) throws IOException {
        // 传入设备id，服务器判定是否与绑定ip匹配
        OutputStream os1 = socket.getOutputStream();
        PrintStream ps = new PrintStream(os1);
        String id = Final.DEVICES_ID;
        ps.println(id);
        // 获取认证结果
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return "false".equals(br.readLine());
    }

    /**
     * 添加外卖
     */
    public String addDelivery(Delivery delivery) {
        try {
            Socket socket = new Socket("127.0.0.1", 6789);
            // 认证
            if (devicesVerify(socket)) {
                return Final.VERIFY_ERROR;
            }
            // 告知服务器执行添加方法
            OutputStream os2 = socket.getOutputStream();
            PrintStream ps = new PrintStream(os2);
            ps.println(Final.ADD);
            // 将传入的手机号与外卖平台传输到服务器
            ps.println(delivery.getDeliveryPho());
            ps.println(delivery.getDeliveryCom());
            // 获取服务器操作结果和服务器分配后的外卖信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = br.readLine();
            if ("done".equals(str)) {
                return br.readLine();
            }
            socket.close();
            return str;
        } catch (IOException e) {
            return Final.CONNECTED_ERROR;
        }
    }

    /**
     * 用户取餐
     */
    public String userDelivery(int code) {
        try {
            Socket socket = new Socket("127.0.0.1", 6789);
            // 认证
            if (devicesVerify(socket)) {
                return null;
            }
            // 告知服务器执行取餐方法
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.println(Final.OUT);
            // 向服务器传入取餐码
            ps.println(code);
            // 获取服务器操作结果
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            if ("true".equals(br.readLine())) {
                socket.close();
                return "true";
            }
            socket.close();
        } catch (IOException e) {
            return null;
        }
        return "false";
    }

    /**
     * 删除方法
     */
    public String deleteDelivery(int code) {
        try {
            Socket socket = new Socket("127.0.0.1", 6789);
            // 认证
            if (devicesVerify(socket)) {
                return null;
            }
            // 告知服务器执行删除方法
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.println(Final.DEL);
            // 向服务器传入取餐码
            ps.println(code);
            // 获取服务器操作结果
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            if ("true".equals(br.readLine())) {
                socket.close();
                return "true";
            }
            socket.close();
        } catch (IOException e) {
            return null;
        }
        return "false";
    }

    /**
     * 查询方法
     */
    public LinkedList<String> viewDelivery() {
        try {
            Socket socket = new Socket("127.0.0.1", 6789);
            // 认证
            if (devicesVerify(socket)) {
                return null;
            }
            // 告知服务器执行查找方法
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.println(Final.FIND);
            // 获取服务器操作结果
            LinkedList<String> strings = new LinkedList<>();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int n = Integer.parseInt(br.readLine());
            for (int i = 0; i < n; ++i) {
                strings.add(br.readLine());
            }
            socket.close();
            return strings;
        } catch (Exception e) {
            return null;
        }
    }
}
