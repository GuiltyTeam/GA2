package studio.Gulity.GA2.Engine.Object.UI.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GA2Swing_ extends JFrame {

    private JPanel Pnl_Main;
    private JPanel Pnl_TitleBar;
    Point Pnt_MouseCursor;

    public GA2Swing_() {
        this.setUndecorated(true);
        this.setContentPane(Pnl_Main);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        System.out.println("1");
        Pnl_TitleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { //鼠标按下事件
                Pnt_MouseCursor = e.getPoint(); //记录鼠标坐标
                System.out.println("2");
            }
        });
        Pnl_TitleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) { // 鼠标拖拽事件
                Point point = e.getPoint();// 获取当前坐标
                Point locationPoint = getLocation();// 获取窗体坐标
                int x = locationPoint.x + point.x - Pnt_MouseCursor.x;
                int y = locationPoint.y + point.y - Pnt_MouseCursor.y;
                setLocation(x, y);// 改变窗体位置
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new GA2Swing_();
    }


}
