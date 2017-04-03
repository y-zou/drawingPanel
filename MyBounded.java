package homework5;


import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;



public abstract class MyBounded extends MyShape{
    boolean fill; 
    //width, height: the width and height of the bounded shape.
    //Point (x,y) is at the bounded shape's left corner.
    int width, height,x,y;  
    
    
    public MyBounded(int startx, int starty, int endx, int endy, boolean fill,Paint paint, BasicStroke stroke){
        super(startx,starty,endx,endy,paint,stroke);
        this.fill = fill;
        this.width = Math.abs(startx - endx);
        this.height = Math.abs(starty - endy);
        
        setXY(startx,starty,endx,endy);
    }

    
    @Override
     public void changeEnd(int endx, int endy){
        super.changeEnd(endx, endy);
        setXY(this.startx,this.starty,this.endx,this.endy);
        this.width = Math.abs(this.startx - this.endx);
        this.height = Math.abs(this.starty - this.endy);
    }
    
     
    public final void setXY(int startx, int starty, int endx, int endy){
        if(endy == starty){x = Math.min(startx, endx); y = endy;}
        if(endx == startx){x = endx; y = Math.min(starty, endy);}
        else if(startx < endx && starty > endy)   { x = startx; y = endy;}
        else if(startx > endx && starty > endy) {x = endx; y = endy;} 
        else if(startx > endx && starty < endy) {x = endx; y = starty;}
        else{x=startx; y = starty;}
    }
    
    @Override
    public void draw(Graphics2D g){        
        g.setPaint(paint);
    }    
}
