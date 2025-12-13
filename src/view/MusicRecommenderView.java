package view;

import controller.MusicRecommenderController;

import javax.swing.*;

public class MusicRecommenderView extends JFrame {
    private MusicRecommenderController controller;
    private JPanel panelMain;
    private JLabel song0Image;
    private JTextField testTextField;
    private JButton searchSongsButton;
    private JButton getRecommendationsButton;
    private JTextPane song0Text0;
    private JTextPane song0Text1;
    private JTextPane song0Text2;
    private JButton addSeedButton;
    private JButton clearSeedsButton;

    public MusicRecommenderView() {
        controller = new MusicRecommenderController(panelMain);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1280, 720);
        this.setContentPane(panelMain);
        this.setVisible(true);
    }
}
