package application;
import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static void  main(String[] args) {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame("Snake Game");
        frame.setBounds(10,10,905,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();
        panel.setBackground(Color.black);
        frame.add(panel);
        frame.setVisible(true);


    }

}