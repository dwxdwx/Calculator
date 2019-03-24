package dwx;

import java.awt.*;
import javax.swing.*;

public class Show extends JFrame {
    public int mDivision;  //按钮与显示的分界线
    public int mDivWidth;  //每个小格的宽
    public int mDivHeight; //每个小格的高
    public int[] mDivX = new int[10];  //存每个小格左上角的x坐标
    public int[] mDivY = new int[10];  //存每个小格左上角的y坐标
    public int mSelectX;
    public int mSelectY;
    public char [][] mSign = {{'C','^','(',')'},{'1','2','3','+'},{'4','5','6','-'},{'7','8','9','×'},{'.','0','=','÷'}};
    public Deal mDeal = new Deal();
    public Show(){
        setTitle("计算器");
        setSize(120 * 3, 160 * 3);
        this.getContentPane().add(new MyPanel());
        setVisible(true);
        setResizable(false);
        setLocation(400,100);
        mSelectX=-1;
        mSelectY=-1;
    }

    public class MyPanel extends JPanel{

        private int flag=0;
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;  //g是Graphics对象
            g2.setStroke(new BasicStroke(3.0f));

            /**
             * 存储位置
             */
            if(flag==0){
                mDivision=this.getWidth()*9/16-30;
                for(int i=0;i<6;i++){
                    mDivX[i]=mDivision+(this.getHeight()-mDivision)*i/5;
                }
                for(int i=0;i<5;i++){
                    mDivY[i]=this.getWidth()*i/4;
                }
                mDivHeight=(this.getHeight()-mDivision)/5; //5行
                mDivWidth=this.getWidth()/4; //4列
                flag=1;
            }

            /**
             * 下面开始绘图
             */
            //背景
            for(int i=0;i<mDivision;i++){
                Color myColor = new Color(155, 155, 155);
                g.setColor(myColor);
                g.drawLine(0, i, this.getWidth(), i);
            }

            for(int i=mDivision;i<this.getHeight();i++){
                //Color myColor = new Color(250-i/3,200-i/3,i/3);
                Color myColor = new Color(90, 90, 90);
                g.setColor(myColor);
                g.drawLine(0, i, this.getWidth(), i);
            }

            //按键效果
            if(mSelectX!=-1&&mSelectY!=-1)
            {
                mDeal.Change(mSign[mSelectX][mSelectY]);
                for(int i=mDivX[mSelectX];i<=mDivX[mSelectX]+mDivHeight;i++){
                    Color myColor = new Color(150, 200, 0);
                    g.setColor(myColor);
                    g.drawLine( mDivY[mSelectY], i, mDivY[mSelectY]+mDivWidth,i);
                }
            }

            g.setColor(Color.white);

            //网格
            for(int i=0;i<5;i++){
                g2.drawLine(0, mDivX[i], this.getWidth(), mDivX[i]);
            }
            for(int i=1;i<=4;i++){
                g2.drawLine(mDivY[i], mDivision, mDivY[i], this.getHeight());
            }
            //绘字
            for(int i=0;i<5;i++){
                for(int j=0;j<4;j++){
                    g2.setFont(new Font("Arial", Font.PLAIN, 25));
                    g2.drawString(String.valueOf(mSign[i][j]), mDivY[j] + mDivWidth / 2 - 2, mDivX[i] + mDivHeight / 2 + 5); //-2  +5是根据显示进行的微调
                }
            }
            //输入框的字
            g.setColor(Color.black);
            Font font = new Font("Arial", Font.PLAIN, 29);
            g.setFont(font);
            if(mDeal.mText==""){
                g.drawString(mDeal.mAns, this.getWidth()-this.getFontMetrics(font).stringWidth(mDeal.mAns)-5, mDivision*2/3);
            }
            else
                g.drawString(mDeal.mText, this.getWidth()-this.getFontMetrics(font).stringWidth(mDeal.mText)-5, mDivision*2/3);

            //记录的字
            Font font1 = new Font("Arial", Font.PLAIN, 16);
            g.setFont(font1);
            String str = new String();
            for(int i=0;i<mDeal.mDetailPos;i++){
                str+=mDeal.mTextDetail[i];
            }
            g.drawString(str, this.getWidth()-this.getFontMetrics(font1).stringWidth(str), mDivision/3);
        }
    }
}
