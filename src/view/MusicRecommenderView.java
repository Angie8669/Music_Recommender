package view;

import controller.MusicRecommenderController;

import javax.swing.*;
import java.io.IOException;

public class MusicRecommenderView extends JFrame {
    private MusicRecommenderController controller;
    private JPanel panelMain;
    private JLabel loadingIcon;
    private JTextField textField1;
    private JLabel song0Image;
    private JTextPane song0Text0;
    private JTextPane song0Text1;
    private JTextPane song0Text2;
    private JButton addSeedButton;
    private JTextPane statusMessage;
    private JButton cancelRequestButton;

    public MusicRecommenderView() throws IOException {
        controller = new MusicRecommenderController(panelMain);

        ImageIcon loadingImage = new ImageIcon("./assets/loading.gif");
        loadingIcon.setIcon(loadingImage);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1280, 720);
        this.setContentPane(panelMain);
        this.setVisible(true);
    }
}
