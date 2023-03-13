package uet.oop.bomberman.Gui;


import uet.oop.bomberman.Util.Utils;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
	public GamePanel(Frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Utils.REALWITDH, Utils.REALHEIGHT));
		add(frame.game);
	}
}
