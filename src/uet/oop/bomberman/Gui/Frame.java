package uet.oop.bomberman.Gui;

import uet.oop.bomberman.Game;


import javax.swing.*;
import java.awt.*;


public class Frame extends JFrame {

	public GamePanel gamePanel;
	private StatPanel statPanel;

	public Game game;

	final String GameplayPane = "containerPane";
	final String MenuPane = "Menu";
	public volatile boolean start = false;

	public Frame() {
		Image icon = Toolkit.getDefaultToolkit().getImage("res/UI/bomberman.png");
		setIconImage(icon);
		setTitle("Bomberman");

		game = new Game(this);

		CardLayout cl = new CardLayout();
		JPanel mainPanel = new JPanel(cl);

		JPanel containerPane = new JPanel(new BorderLayout());
		gamePanel = new GamePanel(this);
		statPanel = new StatPanel(this);
		MenuPanel menu = new MenuPanel(this);

		containerPane.add(statPanel, BorderLayout.NORTH);
		containerPane.add(gamePanel, BorderLayout.CENTER);
		add(containerPane);

		mainPanel.add(containerPane, GameplayPane);
		mainPanel.add(menu,MenuPane);
		add(mainPanel);
		cl.show(mainPanel,MenuPane);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		while (!start) {
			Thread.onSpinWait();

		}
		game.start();
	}

	public void setLive(int live){
		statPanel.setLive(live);
	}

	public void setTime(int time) {
		statPanel.setTime(time);
	}

	public void setPoint(int point) {
		statPanel.setPoint(point);
	}

}
