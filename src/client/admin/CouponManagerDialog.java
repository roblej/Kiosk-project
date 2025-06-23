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
        super(f,modal);
        this.f = f;
        this.vo = vo;
        this.couponList = couponList;
        this.i = i;
        this.p = p;
        ImageUploadButton btn = new ImageUploadButton("이미지 업로드",this);
        this.add(btn);
        setTitle("Update Coupon");
        setSize(400, 300);
        setLocationRelativeTo(f); // 부모 프레임 중앙에 위치
    }
}
