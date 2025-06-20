package client.admin;

import client.MainFrame;
import vo.CouponVO;

import javax.swing.*;
import java.util.List;

public class CouponManagerDialog extends JDialog {
    MainFrame f;
    CouponVO vo;
    List<CouponVO> couponList;
    CouponManagerPanel p;
    int i;

    public CouponManagerDialog(MainFrame f, CouponManagerPanel p, boolean modal, CouponVO vo, List<CouponVO> couponList, int i) {
        this.f = f;
        this.vo = vo;
        this.couponList = couponList;
        this.i = i;
        this.p = p;


    }
}
