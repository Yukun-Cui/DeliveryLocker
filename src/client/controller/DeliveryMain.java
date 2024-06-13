package client.controller;

import client.bean.Delivery;
import client.bean.Final;
import client.dao.DeliveryDao;
import client.view.View;

import java.util.LinkedList;

public class DeliveryMain {

    /**
     * 初始化视图类对象
     * 初始化数据操作类对象
     */
    static final View VIEW = new View();
    static final DeliveryDao DELIVERY_DAO = new DeliveryDao();

    /**
     * 程序入口调用mainSelect()方法显示主菜单
     */
    public static void main(String[] args) {
        VIEW.welcome();
        while (true) {
            try {
                mainSelect();
                break;
            } catch (Exception e) {
                VIEW.failure();
            }
        }
        VIEW.bye();
    }

    /**
     * 主菜单
     */
    private static void mainSelect() {
        while (true) {
            switch (VIEW.mainMenu()) {
                case 1:
                    userSelect();
                    break;
                case 2:
                    adminSelect();
                    break;
                case 0:
                    return;
                default:
                    VIEW.failure();
                    break;
            }
        }
    }

    /**
     * 外卖员菜单
     */
    private static void adminSelect() {
        while (true) {
            try {
                switch (VIEW.adminMenu()) {
                    case 1:
                        addSelect();
                        break;
                    case 2:
                        delSelect();
                        break;
                    case 3:
                        viewSelect();
                        break;
                    case 0:
                        return;
                    default:
                        VIEW.failure();
                        break;
                }
            } catch (Exception e) {
                VIEW.failure();
            }
        }
    }

    /**
     * 外卖员查看外卖柜占用情况
     */
    private static void viewSelect() {
        LinkedList<String> ls = DELIVERY_DAO.viewDelivery();
        if (ls == null) {
            VIEW.connectError();
            return;
        }
        VIEW.printAllDelivery(ls);
    }

    /**
     * 外卖员删除外卖
     */
    private static boolean delSelect() {
        int code = VIEW.doSth();
        if (code == -1) {
            return true;
        }
        String s = DELIVERY_DAO.deleteDelivery(code);
        if (s == null) {
            VIEW.connectError();
            return false;
        }
        if (Final.FALSE.equals(s)) {
            VIEW.failure();
            return true;
        }
        VIEW.success();
        return true;
    }

    /**
     * 外卖员添加外卖
     */
    private static void addSelect() {
        Delivery delivery = VIEW.add();
        String pos = DELIVERY_DAO.addDelivery(delivery);
        switch (pos) {
            case Final.FULL:
                VIEW.full();
                break;
            case Final.CONNECTED_ERROR:
                VIEW.connectError();
                break;
            case Final.VERIFY_ERROR:
                VIEW.verifyError();
                break;
            default:
                VIEW.success();
                VIEW.printDelivery(pos);
        }
    }

    /**
     * 用户菜单
     */
    private static void userSelect() {
        String str = DELIVERY_DAO.userDelivery(VIEW.userMenu());
        if (str == null) {
            VIEW.connectError();
            return;
        }
        if (Final.FALSE.equals(str)) {
            VIEW.failure();
            return;
        }
        VIEW.success();
    }
}
