package uet.oop.bomberman.Gui;

import javax.swing.*;
import java.awt.*;

public class StatPanel extends JPanel {
	private JLabel time;
	private JLabel point;
	private JLabel live;
	private JLabel highScore;
       
	public StatPanel(Frame frame) {
		setLayout(new GridLayout());
		
		time = new JLabel("Times: " + frame.game.getBoard().currentTime);
		point = new JLabel("Points: " + frame.game.getBoard().currentPoint);
		live = new JLabel("Lives: " + frame.game.getLive());
		highScore = new JLabel("High Scores : " + frame.game.getBoard().highScore);

		time.setForeground(Color.WHITE);
		time.setHorizontalAlignment(JLabel.CENTER);
		point.setForeground(Color.WHITE);
		point.setHorizontalAlignment(JLabel.CENTER);
		live.setForeground(Color.WHITE);
		live.setHorizontalAlignment(JLabel.CENTER);
		highScore.setForeground(Color.WHITE);
		highScore.setHorizontalAlignment(JLabel.CENTER);

		add(time);
		add(point);
		add(live);
		add(highScore);
                
		setBackground(Color.black);
		setPreferredSize(new Dimension(40, 40));
	}

	public void setTime(int time) {
		this.time.setText("Time: " + time);
	}

	public void setPoint(int point) {
		this.point.setText("Score: " + point);
	}

	public void setLive(int live) {
		this.live.setText("Lives: " + live);
	}
        
}
