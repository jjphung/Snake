//Jeffrey Phung 10-18-2015

/*
 * This class calls upon Board.java to run the Game.
 */

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class Snake{

  private JFrame snake;
  private JPanel homeScreen;
  private JButton easy;
  private JButton medium;
  private JButton hard;
  private JButton play;
  private int difficulty;

  public void setUpHomeScreen(boolean restart){
    difficulty = 200 ;  // default medium speed
    snake = new JFrame();
    snake.setLayout(new BorderLayout());

    if(restart){
      snake.dispose();
      snake = new JFrame();
      snake.setLayout(new BorderLayout());

    }

    //home screen panel
    homeScreen = new JPanel(new BorderLayout());
    homeScreen.setPreferredSize(new Dimension(320, 240)); // probably you need to remove this line!

    JLabel bg = new JLabel();
    bg.setPreferredSize(new Dimension(320, 240));
    ImageIcon icon = new ImageIcon("HomeBG.gif");
    icon.getImage().flush();
    bg.setIcon(icon);
    homeScreen.add(bg);

    easy = new JButton("Easy");
    medium = new JButton("Medium");
    hard = new JButton("Hard");
    play = new JButton("Play");
    play.addMouseListener(new MouseListener(){
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
        snake.remove(play);
        snake.remove(easy);
        snake.remove(medium);
        snake.remove(hard);
        snake.getContentPane().remove(homeScreen);

        //game panel
        Board board = new Board(difficulty);
        snake.add(board);
        board.requestFocusInWindow();
        snake.getContentPane().validate();
        snake.getContentPane().repaint();
        }

    });
    easy.addMouseListener(new MouseListener(){
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
        difficulty = 300;
        easy.setBackground(Color.WHITE);
        medium.setBackground(Color.BLACK);
        hard.setBackground(Color.BLACK);
        }
        });
    medium.addMouseListener(new MouseListener(){
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e){
        difficulty = 200;
        medium.setBackground(Color.WHITE);
        easy.setBackground(Color.BLACK);
        hard.setBackground(Color.BLACK);
        }
        });
    hard.addMouseListener(new MouseListener(){
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e){
        difficulty = 100;
        hard.setBackground(Color.WHITE);
        easy.setBackground(Color.BLACK);
        medium.setBackground(Color.BLACK);
        }
        });

    easy.setForeground(Color.RED);
    easy.setBackground(Color.BLACK);
    easy.setFocusPainted(false);

    medium.setForeground(Color.RED);
    medium.setBackground(Color.BLACK);
    medium.setFocusPainted(false);

    hard.setForeground(Color.RED);
    hard.setBackground(Color.BLACK);
    hard.setFocusPainted(false);

    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonsPanel.setBackground(Color.BLACK);
    buttonsPanel.add(easy);
    buttonsPanel.add(medium);
    buttonsPanel.add(hard);
    buttonsPanel.add(play);
    buttonsPanel.repaint();
    homeScreen.add(buttonsPanel, BorderLayout.SOUTH);

    snake.add(homeScreen, BorderLayout.CENTER);
    snake.setTitle("Snake Game");
    snake.setResizable(false);
    snake.pack();
    snake.setVisible(true);
    snake.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
  public static void main (String [] args){
    Snake snake = new Snake();
    snake.setUpHomeScreen(false);
  }
} //end snake class
