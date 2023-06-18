import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener{  // Game Board
    int b_height=400;  // board height
    int b_width=400;  // // board width

    int max_dots=1600;      // maximum snake size
    int dot_size=10;
    int curr_dots;     // declaring minimum snake size
    int[] x = new int[max_dots];
    int[] y = new int[max_dots];

    // declaring apple positions
    int apple_x;
    int apple_y;

    Image head,body,apple;
    Timer timer;
    int DELAY=100;  // snake speed

    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;

    boolean inGame=true;


    Board(){                // board constructor
        TAdapter tAdapter=new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(b_width, b_height));      // implementing the size to the board
        setBackground(Color.BLACK);     // board bg color
        initGame();         // calling the game properties function
        loadImages();       //  calling the load image function
    }


    public void initGame(){     // in game properties
        curr_dots=3;

        // initializing position of snake at start
        for(int i=0;i<curr_dots;i++){
            x[i]=200+dot_size*i;             // x co-ordinate
            y[i]=300;                        // y co-ordinate
        }

        // initializing apple position
        locateApple();
        timer=new Timer(DELAY,this);
        timer.start();

    }

        //    load image from resource folder to image object
        public  void loadImages(){
            ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png"); // loading image from resource
            body = bodyIcon.getImage(); // getting and storing the image as image object
            ImageIcon headIcon = new ImageIcon("src/resources/head.png");
            head = headIcon.getImage();
            ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
            apple = appleIcon.getImage();
        }

        // draw images at snake's and apple's position
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    // draw image

    public void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for (int i=0;i<curr_dots;i++){
                if(i==0){
                    g.drawImage(head,x[i],y[i],this);
                }else{
                    g.drawImage(body,x[i],y[i],this);
                }
            }
        }else{
            gameOver(g);
            timer.stop();
        }
    }

    // Randomize apple's position
    public void locateApple(){
        apple_x=((int)(Math.random()*39))*dot_size;
        apple_y=((int)(Math.random()*39))*dot_size;
    }

    // Check snake collision with border and body
    public void checkCollision(){
        for(int i=1;i<curr_dots;i++){
            if(i>3 && x[0]==x[i] && y[0]==y[i]){
                inGame=false;
            }
            if(x[0]<0){
                x[0]=b_width;
                break;
            }
            if(x[0]>=400){
                x[0]=0;
                break;
            }
            if(y[0]<0){
                y[0]=b_height;
                break;
            }
            if(y[0]>=400){
                y[0]=0;
                break;
            }
        }
    }

    // Display game over message and score

    public void gameOver(Graphics g){
        String msg="Game Over";
        int score = (curr_dots-3)*10;
        String scoreMsg = "Score : "+Integer.toString(score);
        Font med = new Font("Helvetica",Font.BOLD,40);
        Font small = new Font("Helvetica",Font.BOLD,18);
        FontMetrics fontMetrics2=getFontMetrics(med);
        FontMetrics fontMetrics=getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(med);
        g.drawString(msg,(b_width-fontMetrics2.stringWidth(msg))/2,b_height/4);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(scoreMsg,(b_width-fontMetrics.stringWidth(scoreMsg))/2,b_height/2);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame) {
            checkCollision();
            checkApple();
            move();
        }
        repaint();
    }

    // Making Snake Move
    public void move(){
        for(int i=curr_dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
            x[0]-=dot_size;
        }
        if(rightDirection){
            x[0]+=dot_size;
        }
        if(upDirection){
            y[0]-=dot_size;
        }
        if(downDirection){
            y[0]+=dot_size;
        }
    }

    // checks if snake head collide with apple
    public void checkApple(){
        if(apple_x==x[0] && apple_y==y[0]){
            curr_dots++;
            locateApple();
        }
    }

    // Implementing Controls for Snake
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();      // getting key from keyboard
            if(key==KeyEvent.VK_LEFT && !rightDirection){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && !downDirection){
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && !upDirection){
                leftDirection=false;
                rightDirection=false;
                downDirection=true;
            }
        }

    }


}
