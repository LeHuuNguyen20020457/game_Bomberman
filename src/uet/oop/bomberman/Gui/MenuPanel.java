package uet.oop.bomberman.Gui;

import uet.oop.bomberman.Util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    public MenuPanel(Frame frame) {
        setPreferredSize(new Dimension(Utils.WIDTH * Utils.SCALE, Utils.HEIGHT * Utils.SCALE));
        setBackground(Color.black);
        setFont(new Font("Serif", Font.BOLD, 60));


        ImageIcon Img = new ImageIcon("res/UI/Menu.jpg");
        ImageIcon start = new ImageIcon("res/UI/start.png");
        ImageIcon exit = new ImageIcon("res/UI/exit.png");

        JLabel label = new JLabel(Img , JLabel.CENTER);
        JLabel startButton = new JLabel(start,JLabel.CENTER);
        JLabel exitButton = new JLabel(exit,JLabel.CENTER);

        GridBagLayout Grid = new GridBagLayout();
        setLayout(Grid);
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;
        c.weightx = 0.5;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        add(label, c);

        c.gridy = 2;
        startButton.setOpaque(false);
        add(startButton,c);
        startButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                frame.gamePanel.setVisible(true);
                frame.start = true;

            }
        });

        c.gridy =3;
        add(exitButton,c);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

    }
}



