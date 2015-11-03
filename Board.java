//Jeffrey Phung 10-20-15

/*
 * Creates the board and the implementation of the entire game.
 * Called on by Snake.java
 */

import javax.swing.*;    //Compenents
import java.awt.*;       //Drawings and Actions
import java.awt.event.*; //Events and Listeners
import java.util.Random;      //Random
import java.io.*;

/*
 * This class is a JPanel. Goes "on the JFrame container".
 */
public class Board extends JPanel implements ActionListener{

  //Instance Variables
  private int boardWidth = 320;
  private int boardHeight = 240;
  private int maxSnakeLength = 560;
  private int length = 0;
  private int speed;

  private int snakeX[] = new int[maxSnakeLength];  //stores the Xcoords
  private int snakeY[] = new int[maxSnakeLength];  //stores the Ycoords

  private Image apple;
  private Image head;
  private Image body;

  private boolean gameOver = false;

  private boolean up = false;
  private boolean down = false;
  private boolean left = false;
  private boolean right = false;

  private Timer timer;
  private Random random; 
  private int appleX;
  private int appleY;

  /*
   * Constructor for Board
   */
  public Board(int diff){
    
    requestFocusInWindow();

    //Background Board stuff
    KeyboardListener key = new KeyboardListener();
    addKeyListener(key);
    setFocusable(true);
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(boardWidth, boardHeight));

    //Set-Up Textures
    ImageIcon appleIcon = new ImageIcon("Apple.png");
    ImageIcon bodyIcon = new ImageIcon("Body.png");
    ImageIcon headIcon = new ImageIcon("Head.png");

    apple = appleIcon.getImage();
    body = bodyIcon.getImage();
    head = headIcon.getImage();

    this.speed = diff;
    //Spawn the snake and apple initial
    startGame();

  } //end constructor

  private void startGame(){
    //Randomly coordinate an apple
    appleCoordinates();

    //Start of a 3 length snake
    length = 3;

    for(int i = 0; i < length; i++){
      snakeX[i] = 20 - i * 10;
      snakeY[i] = 20;
    }

    //set speed here
    timer = new Timer(speed, this);
    timer.start();
  }

  @Override
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      drawBoard(g);
    }

  private void drawBoard(Graphics g){
    //if game end, draw the Game Over screen
    if(gameOver == true){
      gameOver(g);
    }
    //else game is still going on, draw the apple and snake
    else{
      g.drawImage(apple, appleX, appleY, this); //draw apple

      for(int i = 0; i < length; i++){
        //head
        if(i == 0){
          g.drawImage(head, snakeX[i], snakeY[i], this);
        }
        //body
        else{
          g.drawImage(body, snakeX[i], snakeY[i], this);
        }
      } //end loop
    }
  } //end drawBoard

  private void gameOver(Graphics g){
    //print gameover
    String family = "Lucida Sans";
    int size = 20;
    int style = Font.BOLD;
    Font font = new Font(family, style, size);
    g.setFont(font);
    g.setColor(Color.RED);
    g.drawString("Game Over", (boardWidth/2) - 65, (boardHeight/2) );
    retryButton();
  }
  private void retryButton(){
    setLayout(new BorderLayout());
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton retry = new JButton("RETRY");
    retry.addMouseListener(new MouseListener(){
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
        new Snake().setUpHomeScreen(true);
        }
        });

    buttonPanel.setBackground(Color.BLACK);
    buttonPanel.add(retry);
    add(buttonPanel, BorderLayout.SOUTH);
    retry.setVisible(true);
    this.revalidate();
    this.repaint();
  }

  private void hitApple(){
    if((snakeX[0] == appleX) && snakeY[0] == appleY){
      length++;

      //update apple coords
      appleCoordinates();
    }
  }
  private void appleCoordinates(){
    int r = (int) (Math.random() * 29);
    appleX= ((r * 10));
    r = (int) (Math.random() * 23);
    appleY = ((r * 10));

  }
  private void checkHits(){
    //snake collision
    for(int i = length; i > 0; i--){
      //run check after initial length and if head hit anywhere on body
      if( (snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i]) && (i > 4)){
        gameOver = true;
      }
    }

    //bottom wall
    if(snakeY[0] > boardHeight - 10){
      gameOver = true;
    }
    //top wall
    if(snakeY[0] < -10){
      gameOver = true;
    }
    //right wall
    if(snakeX[0] > boardWidth - 10){
      gameOver = true;
    }
    //left wall
    if(snakeX[0] < -10){
      gameOver = true;
    }

    if(gameOver == true){
      timer.stop();
    }
  }
  private void moveSnake(){
    for(int i = length; i > 0; i--){
      snakeX[i] = snakeX[i-1];
      snakeY[i] = snakeY[i-1];
    }
    if(up){
      snakeY[0] -= 10;
    }
    if(down){
      snakeY[0] += 10;
    }
    if(left){
      snakeX[0] -= 10;
    }
    if(right){
      snakeX[0] += 10;
    }
  }
  //Called when key is pressed
  public void actionPerformed(ActionEvent e){

    if(gameOver == false){
      hitApple();
      moveSnake();
      checkHits();
    }

    repaint();
  }
  /***************************************************
   *                Inner Class
   * *************************************************/

  //KeyBoardListener Inner Class
  public class KeyboardListener implements KeyListener{
    public void keyTyped(KeyEvent e){}
    @Override
      public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_UP) && (!down)) {
          up = true;
          right = false;
          left = false;
        }
        if ((key == KeyEvent.VK_DOWN) && (!up)) {
          down = true;
          right = false;
          left = false;
        }
        if ((key == KeyEvent.VK_LEFT) && (!right)) {
          left = true;
          up = false;
          down = false;
        }
        if ((key == KeyEvent.VK_RIGHT) && (!left)) {
          right = true;
          up = false;
          down = false;
        }
      }
    public void keyReleased(KeyEvent e){}
  }

} //end Board class
