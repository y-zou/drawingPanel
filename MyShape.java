package homework5;


import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Graphics2D;



public abstract class MyShape{
  
    int startx,starty,endx,endy;
    BasicStroke stroke;
    Paint paint;
    
    public MyShape(int startx,int starty, int endx, int endy, Paint paint, BasicStroke stroke){

        this.paint = paint;
        this.stroke = stroke;

        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        
    }
    
    public void changeEnd(int endx, int endy){
        this.endx = endx;
        this.endy = endy;
    }
    
    

    
   public abstract void draw(Graphics2D g);
}
