package com.springboot.wubilan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import javax.swing.JFrame;

/**
 * 打印心形的图形，使用awt包
 */
public class Heart extends JFrame{

    private static final long serialVersionUID = -7128362663435137794L;
    public void draw(Graphics g){
        for(int i=0;i<=180;i++){
            for(int j=0;j<=180;j++){
                double r=Math.PI/45*i*(1-Math.sin(Math.PI/45*j))*20;
                double x=r*Math.cos(Math.PI/45*j)*Math.sin(Math.PI/45*i)+300;
                double y=-r*Math.sin(Math.PI/45*j)+200;
                Color c=Color.getHSBColor(i*j/8100.0f, 0.9999f,0.9999f);
                g.setColor(c);
                g.drawOval((int)x, (int)y, 1,1);
                try{
                    Thread.sleep(1);
                }catch(Exception e){}
            }
        }
    }
    public void showUI(){
        this.setSize(new Dimension(1200,1200));
        this.setVisible(true);
        Graphics g = this.getGraphics();
        draw(g);
    }
    public  static void main(String[] args) {
        Heart h = new Heart();
        h.showUI();

    }



}