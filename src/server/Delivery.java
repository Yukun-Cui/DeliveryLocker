package server;

/**
 * 基本单元类
 */
public class Delivery {
    private final String deliveryPho;
    private final String deliveryCom;
    private int deliveryCode;
    private int deliveryPos;

    public Delivery(String deliveryPho, String deliveryCom) {
        this.deliveryPho = deliveryPho;
        this.deliveryCom = deliveryCom;
    }

    public Delivery(String deliveryPho, String deliveryCom, int deliveryCode, int deliveryPos) {
        this.deliveryPho = deliveryPho;
        this.deliveryCom = deliveryCom;
        this.deliveryCode = deliveryCode;
        this.deliveryPos = deliveryPos;
    }

    public int getDeliveryPos() {
        return deliveryPos;
    }

    public void setDeliveryPos(int deliveryPos) {
        this.deliveryPos = deliveryPos;
    }

    public int getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(int deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String fileString() {
        return "#" + deliveryPho + "#$" + deliveryCom + "$%" + deliveryCode + "%!" + deliveryPos + "!";
    }

    @Override
    public String toString() {
        return "手机号：" + deliveryPho + 
             ", 外卖平台：" + deliveryCom +
             ", 取餐码：" + deliveryCode +
             ", 外卖柜编号：" + deliveryPos;
    }
}
