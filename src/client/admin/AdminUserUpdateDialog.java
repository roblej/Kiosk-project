package client.admin;

import client.MainFrame;
import vo.UserVO;

import javax.swing.*;

public class AdminUserUpdateDialog extends JDialog {
    MainFrame f;
    public AdminUserUpdateDialog(MainFrame f, boolean modal, UserVO vo){
        super(f,modal);
        this.f = f;
        setTitle("Update User");
        setSize(400, 300);
        setLocationRelativeTo(f); // 부모 프레임 중앙에 위치


    }
}
