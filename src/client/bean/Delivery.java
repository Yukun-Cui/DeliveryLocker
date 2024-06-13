package client.bean;

/**
 * 外卖基本单元类
 */
public class Delivery {
    private String deliveryPho;
    private String deliveryCom;
    private int deliveryCode;
    private int deliveryPos;

    public String getDeliveryPho() {
        return deliveryPho;
    }

    public void setDeliveryPho(String deliveryPho) {
        this.deliveryPho = deliveryPho;
    }

    public String getDeliveryCom() {
        return deliveryCom;
    }
    public void setDeliveryCom(String deliveryCom) {
        this.deliveryCom = deliveryCom;
    }

    @Override
    public String toString() {
        return "手机号：" + deliveryPho + 
             ", 外卖平台：" + deliveryCom +
             ", 取餐码：" + deliveryCode +
             ", 外卖柜编号：" + deliveryPos;
    }
}
