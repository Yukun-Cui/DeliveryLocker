package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class ServerDataDao {
    private LinkedList<Delivery> deliveryes;
    private final ServerFileDao serverFileDao;
    private final String devicesId;

    /**
     * 构造方法
     * 每个线程（不同外卖柜---用设备id区分）必须传入独立的文件、数据类和设备id
     */
    public ServerDataDao(LinkedList<Delivery> deliveryes, ServerFileDao serverFileDao, String devicesId) {
        this.deliveryes = deliveryes;
        this.serverFileDao = serverFileDao;
        this.devicesId = devicesId;
    }

    /**
     * 从文件中读取数据
     */
    public void loadFile() {
        try {
            deliveryes = serverFileDao.loadFile(devicesId);
        } catch (IOException ignored) {
        }
    }

    /**
     * 将数据存入文件
     */
    public void storeFile() {
        try {
            serverFileDao.store(deliveryes);
        } catch (FileNotFoundException ignored) {
        }
    }

    /**
     * 客户端认证
     */
    public boolean verify(String ip) throws IOException {
        return serverFileDao.verifyFile(devicesId, ip);
    }

    /**
     * 添加外卖的实现方法
     * 存入外卖并调用setCode与setPos生成取餐码和位置
     * setPos返回位置满时，返回错误信息；
     */
    public String addDelivery(Delivery delivery) {
        if (!setPos(delivery)) {
            return Final.FULL;
        } else {
            setCode(delivery);
            deliveryes.add(delivery);
            return Final.DONE;
        }
    }

    /**
     * 用户取餐,得益于removeIf方法，我们可以用一行代码直接搞定用户取餐
     */
    public boolean userDelivery(int code) {
        return deliveryes.removeIf(exp -> exp.getDeliveryCode() == code);
    }

    /**
     * 删除方法，得益于removeIf方法，我们可以用一行代码直接搞定删除功能
     */
    public boolean deleteDelivery(int code) {
        return deliveryes.removeIf(exp -> exp.getDeliveryCode() == code);
    }

    /**
     * 返回刚添加的外卖的信息
     */
    public String returnInfo() {
        return deliveryes.getLast().toString();
    }

    /**
     * 查询方法
     */
    public LinkedList<Delivery> viewDelivery() {
        return deliveryes;
    }

    /**
     * 私有化外卖位置生成方法
     * 这里会使用全局静态常量类中规定的最大外卖柜容量判断外卖柜是否已满
     */
    private boolean setPos(Delivery delivery) {
        int count = 0, pos;
        pos: while (true) {
            pos = new Random().nextInt(Final.MAX_SIZE);
            for (Delivery e : deliveryes) {
                if (e.getDeliveryPos() == pos) {
                    ++count;
                    continue pos;
                }
            }
            if (count == Final.MAX_SIZE) {
                return false;
            } else {
                delivery.setDeliveryPos(pos);
                return true;
            }
        }
    }

    /**
     * 私有化取件码生成方法
     * 由于六位随机数相同的概率极小，所以此方法大多数情况只需要遍历一遍外卖柜
     */
    private void setCode(Delivery delivery) {
        int code;
        code: while (true) {
            code = new Random().nextInt(900000) + 100000;
            for (Delivery e : deliveryes) {
                if (e.getDeliveryCode() == code) {
                    continue code;
                }
            }
            delivery.setDeliveryCode(code);
            return;
        }
    }
}
