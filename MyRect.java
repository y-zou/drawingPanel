package homework5;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;


public class MyRect extends MyBounded{

   
    public MyRect(int startx, int starty, int endx, int endy, boolean fill,Paint paint, BasicStroke stroke){
        super(startx,starty,endx,endy,fill,paint,stroke);
    }

    
    @Override
    public void draw(Graphics2D g){        
        super.draw(g);
        if(fill) g.fillRect(x,y,width,height);
        else {
            g.setStroke(stroke);
            g.draw(new Rectangle2D.Double(x,y,width,height));
        }
    }
}
