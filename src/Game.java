import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Game{
    JLabel counterLabel, perSecLabel;
    JButton catButton, button1, button2, button3, button4;
    JTextArea messageText;
    int catCounter, timerSpeed, cursorNumber, cursorPrice, grandpaNumber,grandpaPrice;
    double perSecond;
    boolean timerOn, grandpaUnlocked;
    Font font1,font2;
    CatHandler cHandler = new CatHandler();
    Timer timer;
    MouseHandler mHandler = new MouseHandler();

    public static void main(String[] args) {
        new Game();


    }

    public Game(){
        grandpaUnlocked = false;
        timerOn = false;
        catCounter = 0;
        perSecond = 0;
        cursorNumber = 0;
        cursorPrice = 10;
        grandpaPrice = 100;
        createFont();
        createUI();

    }

    public void createFont(){
        font1 = new Font("Comic Sans MS", Font.PLAIN, 32);
        font2 = new Font("Comic Sans MS", Font.PLAIN, 15);
    }

    public void createUI(){
        //window 800x600

        JFrame window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        JPanel catPanel = new JPanel();
        catPanel.setBounds(100,220,200,200);
        catPanel.setBackground(Color.black);

        //buttons
        ImageIcon cat = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("cat200x200.png")));
        ImageIcon catPressed = new ImageIcon(cat.getImage().getScaledInstance(190, 190, Image.SCALE_DEFAULT));

        catButton = new JButton();
        catButton.setBorder(null);
        catButton.setBackground(Color.black);
        catButton.setFocusPainted(false);
        catButton.setIcon(cat);
        catButton.setPressedIcon(catPressed);
        catButton.setActionCommand("Cat");
        catButton.addActionListener(cHandler);
        catButton.setContentAreaFilled(false);
        catButton.addMouseListener(mHandler);
        catPanel.add(catButton);
        window.add(catPanel);

        JPanel counterPanel = new JPanel();
        counterPanel.setBounds(100, 100,200,100);
        counterPanel.setBackground(null);
        counterPanel.setLayout(new GridLayout(2,1));
        window.add(counterPanel);

        counterLabel = new JLabel(catCounter + " cats");
        counterLabel.setForeground(Color.white);
        counterLabel.setFont(font1);
        counterPanel.add(counterLabel);

        perSecLabel = new JLabel();
        perSecLabel.setForeground(Color.white);
        perSecLabel.setFont(font2);
        counterPanel.add(perSecLabel);

        JPanel itemPanel = new JPanel();
        itemPanel.setBounds(500, 170, 250,250 );
        itemPanel.setVisible(true);
        itemPanel.setForeground(Color.LIGHT_GRAY);
        itemPanel.setLayout(new GridLayout(4,1));
        window.add(itemPanel);

        button1 = new JButton("Cursor");
        button1.setFont(font1);
        button1.addActionListener(cHandler);
        button1.setActionCommand("Cursor");
        button1.setFocusPainted(false);
        button1.addMouseListener(mHandler);
        itemPanel.add(button1);

        button2 = new JButton("?");
        button2.setFont(font1);
        button2.addActionListener(cHandler);
        button2.setActionCommand("Grandpa");
        button2.setFocusPainted(false);
        button2.addMouseListener(mHandler);
        itemPanel.add(button2);

        button3 = new JButton("?");
        button3.setFont(font1);
        button3.addActionListener(cHandler);
        button3.setActionCommand("Cursor");
        button3.setFocusPainted(false);
        button3.addMouseListener(mHandler);
        itemPanel.add(button3);

        button4 = new JButton("?");
        button4.setFont(font1);
        button4.addActionListener(cHandler);
        button4.setActionCommand("Cursor");
        button4.setFocusPainted(false);
        button4.addMouseListener(mHandler);
        itemPanel.add(button4);

        JPanel messagePanel = new JPanel();
        messagePanel.setBounds(500,70,250,150);
        messagePanel.setBackground(Color.black);
        window.add(messagePanel);

        messageText = new JTextArea();
        messageText.setBounds(500,70,250,150);
        messageText.setForeground(Color.white);
        messageText.setBackground(Color.black);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(font2);
        messageText.setEditable(false);
        messagePanel.add(messageText);

        window.setVisible(true);
    }

    public void setTimer(){
        timer = new Timer(timerSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catCounter++;
                counterLabel.setText(catCounter + " cats");

                if(!grandpaUnlocked){
                    if(catCounter >= 100){
                        grandpaUnlocked = true;
                        button2.setText("Grandpa" + "(" + grandpaNumber + ")");
                    }
                }
            }
        });
    }
    public void timerUpdate(){
        if(!timerOn){
            timerOn = true;
        }else {
            timer.stop();
        }

        double speed = 1/perSecond * 1000;
        timerSpeed = (int)(Math.round(speed));

        String s = String.format("%.1f", perSecond);
        perSecLabel.setText(s + " cats per second");

        setTimer();
        timer.start();
    }

    public class CatHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            switch (action) {
                case "Cat":
                    catCounter++;
                    counterLabel.setText(catCounter + " cats");
                    break;
                case "Cursor":
                    if(catCounter >= cursorPrice){
                        catCounter = catCounter - cursorPrice;
                        cursorPrice = cursorPrice + 5;
                        counterLabel.setText(catCounter + "cats");
                        cursorNumber++;
                        button1.setText("Cursor " + "(" + cursorNumber + ")");
                        messageText.setText("Cursor\n[price: " + cursorPrice + "]\nAutoclicks once every 10 seconds.");
                        perSecond = perSecond + 0.1;
                        timerUpdate();
                    }
                    else{
                        messageText.setText("You need more cats!");
                    }
                    break;
                case "Grandpa":
                    if(catCounter >= grandpaPrice){
                        catCounter = catCounter - grandpaPrice;
                        grandpaPrice = grandpaPrice + 50;
                        counterLabel.setText(catCounter + "cats");
                        grandpaNumber++;
                        button2.setText("Grandpa " + "(" + grandpaNumber + ")");
                        messageText.setText("Grandpa\n[price: " + grandpaPrice + "]\nEach grandpa produces 1 cat per second.");
                        perSecond = perSecond + 1;
                        timerUpdate();
                    }
                    else{
                        messageText.setText("You need more cats!");
                    }
                    break;
            }
        }
    }
    class MouseHandler implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            if(button == catButton) {

            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton) e.getSource();

            if(button == button1){
                messageText.setText("Cursor\n[price: " + cursorPrice + "]\nAutoclicks once every 10 seconds.");
            }
            else if(button == button2){
                if(!grandpaUnlocked){
                    messageText.setText("This item is currently locked!");
                }else{
                    messageText.setText("Grandpa\n[price: " + grandpaPrice + "]\nEach grandpa produces 1 cat per second.");
                }

            }else if(button == button3){
                messageText.setText("Wait for update 3");
            }else if(button == button4){
                messageText.setText("Wait for update 4");
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getSource();

            if(button == button1){
                messageText.setText("Check buildings");
            }
            else if(button == button2){
                messageText.setText(null);
            }
            else if(button == button3){
                messageText.setText(null);
            }
            else if(button == button4){
                messageText.setText(null);
            }
        }
    }

}

