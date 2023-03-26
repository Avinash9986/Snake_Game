import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_height = 400 ;
    int B_width = 400 ;

    int MAX_DOTS = 1600 ;

    int DOT_SIZE = 10 ;

    int DOTS ;

    int x[] =new int[MAX_DOTS] ;
    int y[] = new int [MAX_DOTS] ;

    int apple_x = 150 ;
    int apple_y = 150 ;

    Image body,head,apple ;

    Timer timer ;
    int DELAY = 150 ;

    boolean leftDirection = true ;
    boolean rightDirection = false ;
    boolean upDirection = false ;
    boolean downDirection = false ;

    boolean inGame = true ;
    Board(){
        TAdapter tAdapter = new TAdapter() ;
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_width,B_height));
        setBackground(Color.BLACK);
        intiGame();
        loadImage();



    }

    public void intiGame(){
        DOTS = 3 ;

        x[0] = 150 ;
        y[0] = 150 ;

        for(int i = 1 ; i < 1600 ; i++){
            x[i] = x[0]+DOT_SIZE*i ;
            y[i] = y[0] ;
        }

       locateApple();
        timer = new Timer(DELAY,this) ;
        timer.start();
    }

    public void loadImage(){
        ImageIcon body_icon = new ImageIcon("src/resources/dot.png") ;
        body = body_icon.getImage() ;

        ImageIcon head_icon = new ImageIcon("src/resources/head.png") ;
        head = head_icon.getImage() ;

        ImageIcon apple_icon = new ImageIcon("src/resources/apple.png") ;
        apple = apple_icon.getImage() ;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g) ;
    }

    public void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this) ;
            for(int i = 0 ; i < DOTS ; i++){
                if(i == 0 ){
                    g.drawImage(head,x[0],y[0],this) ;
                }else{
                    g.drawImage(body,x[i],y[i],this) ;
                }
            }
        }
        else{
            gameOver(g);
            timer.stop();
        }
    }

    public void locateApple(){
        apple_x = ((int)(Math.random()*39))*10 ;
        apple_y = ((int)(Math.random()*39))*10 ;
    }

    public void checkCollision(){
        for(int i = 1 ; i < DOTS ; i++){
            if(i>4&&x[i]==x[0]&&y[i]==y[0]){
                inGame = false ;
            }
        }
        if(x[0]<0){
            inGame = false ;
        }
        if(x[0]>=B_width){
            inGame = false ;
        }
        if(y[0]<0){
            inGame = false ;
        }
        if(y[0]>=B_height){
            inGame = false ;
        }
    }
    public void gameOver(Graphics g){
        String game_over = "GAME OVER" ;
        int score = (DOTS-3)*100 ;
        String s = "SCORE : " + Integer.toString(score);
        Font small = new Font("Halvetica",Font.BOLD,14) ;
        FontMetrics fontmetrics = getFontMetrics(small) ;

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(game_over,(B_width-fontmetrics.stringWidth(game_over))/2,B_height/4);
        g.drawString(s,(B_width-fontmetrics.stringWidth(s))/2,3*B_height/4);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(inGame){
                checkApple();
                move() ;
                checkCollision();
            }
            repaint();

    }
    public void move(){
        for(int i = DOTS-1 ; i >0 ; i--){
            x[i]=x[i-1] ;
            y[i]=y[i-1] ;
        }
        if(leftDirection){
            x[0]-=DOT_SIZE ;
        }
        if(rightDirection){
            x[0]+=DOT_SIZE ;
        }
        if(upDirection){
            y[0]-=DOT_SIZE ;
        }
        if(downDirection){
            y[0]+=DOT_SIZE ;
        }
    }
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            DOTS++ ;
            locateApple();
        }
    }

    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode() ;
            if(key == keyEvent.VK_LEFT && !(rightDirection) ){
                leftDirection = true ;
                upDirection = false ;
                downDirection = false ;
            }
            if(key == keyEvent.VK_RIGHT && !(leftDirection) ){
                rightDirection = true ;
                upDirection = false ;
                downDirection = false ;
            }
            if(key == keyEvent.VK_UP && !(downDirection) ){
                upDirection = true ;
                rightDirection = false ;
                leftDirection = false ;
            }
            if(key == keyEvent.VK_DOWN && !(upDirection) ){
                downDirection = true ;
                rightDirection = false ;
                leftDirection = false ;
            }

        }
    }
}

