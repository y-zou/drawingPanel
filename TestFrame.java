package homework5;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class TestFrame {
    public static void main(String args[]){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                Frame f = new Frame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.getContentPane().setBackground(Color.GRAY);
                f.setSize(800,800);
                f.setVisible(true);
            }
        });
    }
}
