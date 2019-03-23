package dwx;

/**
 * Created by dwx on 2019/3/20.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String args[]){
        final Show show=new Show();
        show.addMouseListener(new MouseAdapter(){

            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                super.mousePressed(e);
                int tx=e.getY()-show.mDivHeight/2;
                int ty=e.getX();
                if(tx>show.mDivision){
                    show.mSelectX=(tx-show.mDivision)/show.mDivHeight;
                    show.mSelectY=ty/show.mDivWidth;
                }
                show.repaint();
            }

            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                show.mSelectX=-1;
                show.mSelectY=-1;
                show.repaint();
            }
        });
    }
}
