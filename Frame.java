package homework5;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;



public class Frame extends JFrame {
    private final JTextField lineTextField, dashTextField;
    private final JButton buttonUndo, buttonClear,buttonColor1, buttonColor2;
    private final JComboBox<String> shapeComboBox;
    private static final String[] SHAPECOMBO = {"line", "oval", "rectangle"};
    private final JLabel statusBar;
    private final JCheckBox fillCheckBox,dashCheckBox, gradientCheckBox;
    
    private int index, lineWidth, dashLength;
    private Color color1 = Color.LIGHT_GRAY, color2 = Color.LIGHT_GRAY;
    
    private final JPanel toolbox1, toolbox2, toolbox, statusPanel;
    private final DrawPanel drawPanel;

    public Frame(){
        //toolbox1 is the first row of toolbox
        toolbox1 = new JPanel();
        toolbox1.setPreferredSize(new Dimension(this.getWidth(),50));
        
        
        //toolbox2 is the second row of toolbox
        toolbox2 = new JPanel();
        toolbox2.setPreferredSize(new Dimension(this.getWidth(),50));
      
        
        toolbox = new JPanel();
        GridLayout gridLayout = new GridLayout(2,1);
        toolbox.setLayout(gridLayout);
        toolbox.add(toolbox1);
        toolbox.add(toolbox2);
        
        this.add(toolbox, BorderLayout.NORTH);
       
        statusPanel = new JPanel();
        statusBar = new JLabel("hi");
        statusPanel.add(statusBar);
        
        
        buttonUndo = new JButton("Undo");
        toolbox1.add(buttonUndo);
        
        buttonClear = new JButton("Clear");
        toolbox1.add(buttonClear);
        
        shapeComboBox = new JComboBox<>(SHAPECOMBO);
        shapeComboBox.setMaximumRowCount(3);
        toolbox1.add(shapeComboBox);
        
        fillCheckBox = new JCheckBox("Fill");
        toolbox1.add(fillCheckBox);

         
        gradientCheckBox = new JCheckBox("Gradient");
        toolbox2.add(gradientCheckBox);
        
        buttonColor1 = new JButton("First Color ...");
        toolbox2.add(buttonColor1);
        
        buttonColor2 = new JButton("Second Color ...");
        toolbox2.add(buttonColor2);
        
        
        
        JLabel labelLine = new JLabel("line width");
        toolbox2.add(labelLine);
        
        lineTextField = new JTextField(3);
        toolbox2.add(lineTextField); 
        
        JLabel labelDash = new JLabel("dash length");
        toolbox2.add(labelDash);
        
        dashTextField = new JTextField(3);
        toolbox2.add(dashTextField);  
         
        dashCheckBox = new JCheckBox("Dash");
        toolbox2.add(dashCheckBox);
        
        
       
        //////////implement all the JCompenents above//////////
        buttonUndo.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event){
                        drawPanel.undo();
                    }
                });
        
        
        //Clear Button
        buttonClear.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event){
                        drawPanel.clear();
                    }
                });
       
        
        //Combo Box
        shapeComboBox.addItemListener(
            new ItemListener(){
                @Override               
                public void itemStateChanged(ItemEvent event){
                    if(event.getStateChange() == ItemEvent.SELECTED){
                        Frame.this.setIndex(shapeComboBox.getSelectedIndex());       
                    }
                }
            });
        
        
        buttonColor1.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event){
                        color1 = JColorChooser.showDialog(Frame.this,"choose a color",color1);
                        if(color1 == null)   color1 = Color.LIGHT_GRAY;
                        buttonColor1.setBackground(color1);
                    }
                });
        
        
        
        buttonColor2.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event){
                        color2 = JColorChooser.showDialog(Frame.this,"choose a color", color2);
                        if(color2 == null)   color2 = Color.LIGHT_GRAY;
                        buttonColor2.setBackground(color2);
                    }
                });
         
         
        //set default line width = 10 
        this.lineWidth = 10;
        lineTextField.setText("10");
        lineTextField.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event){
                    String s = event.getActionCommand();
                    Frame.this.setLineWidth(Integer.parseInt(s));
                }
            });
        
        
        //set default dash width = 12
        this.dashLength = 12;
        dashTextField.setText("12");    
        dashTextField.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event){
                    String s = event.getActionCommand();
                    Frame.this.setDashLength(Integer.parseInt(s));
                }
            });
        
        
        ////////////////////draw Panel////////////////////////////
        drawPanel = new DrawPanel(){
            {
                setPreferredSize(new Dimension(550,550));
                setBackground(Color.WHITE);
                addMouseListener(new MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e){
                        startx = e.getX();
                        starty = e.getY();
                        statusBar.setText(String.format("Pressed at [%d,%d]", startx,starty));
                    }
                    @Override
                    public void mouseReleased(MouseEvent e){

                        endx = e.getX();
                        endy = e.getY();
                        statusBar.setText(String.format("Released at [%d,%d]",endx,endy));
                        //if(!shapes.isEmpty() && (startx!=endx || starty!=endy)) { shapes.get(shapes.size()-1).changeEnd(endx,endy);}
                        currentShape = null;
                    }
                });

                addMouseMotionListener(new MouseMotionAdapter(){
                    @Override
                    public void mouseMoved(MouseEvent e){
                        statusBar.setText(String.format("Moving at [%d,%d]",e.getX(),e.getY()));
                    }
                    
                    @Override
                    public void mouseDragged(MouseEvent e){

                        endx = e.getX();
                        endy = e.getY();
                        statusBar.setText(String.format("Dragging at [%d,%d]",endx,endy));
                        if(currentShape == null){
                            int index = Frame.this.getIndex();
                            if(index == 0) currentShape = new MyLine(startx,starty,endx,endy, Frame.this.getPaint(),Frame.this.getStroke());
                            if(index == 1) currentShape = new MyOval(startx,starty,endx,endy,fillCheckBox.isSelected(),Frame.this.getPaint(),Frame.this.getStroke());
                            if(index == 2) currentShape = new MyRect(startx,starty,endx,endy,fillCheckBox.isSelected(),Frame.this.getPaint(),Frame.this.getStroke());
                            shapes.add(currentShape);
                        }
                        else {
                            shapes.get(shapes.size()-1).changeEnd(endx,endy);
                        }
                        repaint();
                    }
                });
            }
            
                 @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                //currentShape.draw(g)
                for(MyShape s: shapes){
                    s.draw(g2d);
                }
                repaint();
            }
            
        };       
        this.add(drawPanel,BorderLayout.CENTER);
        this.add(statusPanel, BorderLayout.SOUTH);
    }
    
    
    private class DrawPanel extends JPanel{
        protected int startx, starty, endx, endy;
        protected ArrayList<MyShape> shapes = new ArrayList<>();
        protected MyShape currentShape;
        
        public DrawPanel(){
            shapes = new ArrayList<>();
            currentShape = null;
        }
        

        public void undo(){
            if(!shapes.isEmpty()){shapes.remove(shapes.size()-1);}
        }

        public void clear(){
            shapes.clear();
        }
        
    }
    
    

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }    

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        if(this.lineWidth <= 0)
            throw new IllegalArgumentException("line width should > 0");
        this.lineWidth = lineWidth;
    }

   
    public int getDashLength() {
       
        return dashLength;
    }

    public void setDashLength(int dashLength) {
         if(this.dashLength <= 0)
            throw new IllegalArgumentException("dash length should > 0");
        this.dashLength = dashLength;
    }
    
    public BasicStroke getStroke(){
        if(dashCheckBox.isSelected())  return (new BasicStroke(getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, new float[]{getDashLength()}, 0));
        else return (new BasicStroke(getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    }
    
    public Paint getPaint(){
        if(gradientCheckBox.isSelected())   return(new GradientPaint(0,0,color1,50,50,color2,true));
        else return color1;
    }
}    


