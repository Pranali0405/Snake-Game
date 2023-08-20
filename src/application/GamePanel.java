package application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private int[] snakexlen = new int[750];
    private int[] snakeylen = new int[750];
    private int lengthOfSnake = 3;

    private int[] xPos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] yPos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random = new Random();
    private int enemyX,enemyY;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private int moves = 0;
    private int score = 0;
    private boolean gameOver = false;
    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));

    private Timer timer;
    private int delay = 100;

    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay,this);
        timer.start();
        newEnemy();

    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.cyan);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);
        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.darkGray);
        g.fillRect(25,75,850,575);

        if(moves==0){
            snakexlen[0] = 100;
            snakexlen[1] = 75;
            snakexlen[2] = 50;

            snakeylen[0] = 100;
            snakeylen[1] = 100;
            snakeylen[2] = 100;

        }
        if(left){
            leftmouth.paintIcon(this,g,snakexlen[0],snakeylen[0]);
        }
        if(right){
            rightmouth.paintIcon(this,g,snakexlen[0],snakeylen[0]);
        }
        if(up){
            upmouth.paintIcon(this,g,snakexlen[0],snakeylen[0]);
        }
        if(down){
            downmouth.paintIcon(this,g,snakexlen[0],snakeylen[0]);
        }
        for(int i=1;i<lengthOfSnake;i++){
            snakeimage.paintIcon(this,g,snakexlen[i],snakeylen[i]);
        }
        enemy.paintIcon(this,g,enemyX,enemyY);
        if(gameOver){
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("Game Over",300,300);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("Press SPACE to Restart",230,350);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.BOLD,14));
        g.drawString("Score: "+score,750,30);
        g.drawString("Length: "+lengthOfSnake,750,50);
        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1;i>0;i--){
            snakexlen[i] = snakexlen[i-1];
            snakeylen[i] = snakeylen[i-1];
        }
        if(left){
            snakexlen[0] = snakexlen[0] - 25;
        }
        if(right){
            snakexlen[0] = snakexlen[0] + 25;
        }
        if(up){
            snakeylen[0] = snakeylen[0] - 25;
        }
        if(down){
            snakeylen[0] = snakeylen[0] + 25;
        }
        if(snakexlen[0]>850)snakexlen[0] = 25;
        if(snakexlen[0]<25)snakexlen[0] = 850;

        if(snakeylen[0]>625)snakeylen[0] = 75;
        if(snakeylen[0]<75)snakeylen[0] = 625;

        collidesWithEnemy();
        collidesWithBody();

        repaint();
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && (!right)){
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)){
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && (!down)){
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && (!up)){
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
        }



    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    private void newEnemy() {
        enemyX = xPos[random.nextInt(34)];
        enemyY = yPos[random.nextInt(23)];
        for(int i=lengthOfSnake-1;i>=0;i--){
            if(snakexlen[i]==enemyX && snakeylen[i]==enemyY){
                newEnemy();
            }
        }
    }
    private void collidesWithEnemy(){
        if(snakexlen[0]==enemyX && snakeylen[0]==enemyY){
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }
    private void collidesWithBody(){
        for(int i=lengthOfSnake-1;i>0;i--){
            if(snakexlen[i]==snakexlen[0] && snakeylen[i]==snakeylen[0]){
                timer.stop();
                gameOver = true;
            }
        }
    }
    private void restart(){
        gameOver=false;
        moves = 0;
        score = 0;
        lengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }

}
