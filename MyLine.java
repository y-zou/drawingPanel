package homework5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Line2D;


public class MyLine extends MyShape{

    public MyLine(int startx, int starty, int endx, int endy,Paint paint, BasicStroke stroke){
        super(startx,starty,endx,endy,paint,stroke);
    }
    @Override
    public void draw(Graphics2D g){        
        
        g.setPaint(paint);
        g.setStroke(stroke);
        g.draw(new Line2D.Double(startx,starty,endx,endy));
       

              
    }
}
