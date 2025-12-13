package controller;

import model.MusicRecommenderModel;
import model.Spotify.SpotifyAlbum;
import model.Spotify.SpotifyArtist;
import model.Spotify.SpotifyData;
import model.Spotify.SpotifyTrack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class MusicRecommenderController {
    private MusicRecommenderModel model;
    private JPanel panelMain;
    private JTextField searchField;
    private JPanel songDisplay;

    public MusicRecommenderController(JPanel panel) {
        this.model = new MusicRecommenderModel();
        this.panelMain = panel;

        JPanel menu = (JPanel) this.panelMain.getComponent(0);
        this.searchField = (JTextField) menu.getComponent(2);

        // Setup button listeners
        JPanel searchButtons = (JPanel) menu.getComponent(3);
        JButton searchArtistsButton = (JButton) searchButtons.getComponent(0);
        searchArtistsButton.addActionListener(e -> getArtists());

        JButton searchAlbumsButton = (JButton) searchButtons.getComponent(1);
        searchAlbumsButton.addActionListener(e -> getAlbums());

        JButton searchSongsButton = (JButton) searchButtons.getComponent(2);
        searchSongsButton.addActionListener(e -> getTracks());

        JPanel recommendationButtons = (JPanel) menu.getComponent(4);
        JButton recommendationsButton = (JButton) recommendationButtons.getComponent(0);
        recommendationsButton.addActionListener(e -> getRecommendations());

        JButton clearSeedsButton = (JButton) recommendationButtons.getComponent(1);
        clearSeedsButton.addActionListener(e -> this.model.clearSeeds());

        this.songDisplay = (JPanel) this.panelMain.getComponent(2);
    }

    private void setEmptySection(int index) {
        JPanel song = (JPanel) this.songDisplay.getComponent(index);

        JLabel songImage = (JLabel) song.getComponent(0);
        songImage.setIcon(null);

        JPanel songText = (JPanel) song.getComponent(1);
        JTextPane songText0 = (JTextPane) songText.getComponent(0);
        songText0.setText("");

        JTextPane songText1 = (JTextPane) songText.getComponent(1);
        songText1.setText("");

        JTextPane songText2 = (JTextPane) songText.getComponent(2);
        songText2.setText("");

        // Set Add Seed button to invisible
        JPanel songButtonPanel = (JPanel) song.getComponent(2);
        JButton addSeedButton = (JButton) songButtonPanel.getComponent(0);

        addSeedButton.setVisible(false);

        // Remove previous action listeners
        for (ActionListener listener : addSeedButton.getListeners(ActionListener.class)) {
            addSeedButton.removeActionListener(listener);
        }
    }

    private void setArtistOnView(List<SpotifyArtist> artists) {
        // Display up to 3 songs
        for(int i = 0; i < 3; i++) {
            SpotifyArtist artist = artists.get(i);
            JPanel song = (JPanel) this.songDisplay.getComponent(i);
            // Set image
            try {
                JLabel songImage = (JLabel) song.getComponent(0);

                URL url = URI.create(artist.getImage()).toURL();
                BufferedImage image = ImageIO.read(url);
                Image resized = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH);

                songImage.setIcon(new ImageIcon(resized));
            } catch (Exception e) {

            }

            JPanel songText = (JPanel) song.getComponent(1);
            JTextPane songText0 = (JTextPane) songText.getComponent(0);
            songText0.setText(artist.getName());

            JTextPane songText1 = (JTextPane) songText.getComponent(1);
            StringBuilder genres = new StringBuilder();
            for (String genre : artist.getGenres()) {
                genres.append(genre).append(", ");
            }
            songText1.setText("Genres: " + genres);

            // No text to display, so clear anything already there.
            JTextPane songText2 = (JTextPane) songText.getComponent(2);
            songText2.setText("");

            // Set Add Seed button to visible
            JPanel songButtonPanel = (JPanel) song.getComponent(2);
            JButton addSeedButton = (JButton) songButtonPanel.getComponent(0);

            addSeedButton.setVisible(true);

            // Remove previous action listeners
            for (ActionListener listener : addSeedButton.getListeners(ActionListener.class)) {
                addSeedButton.removeActionListener(listener);
            }

            // Add new listener
            addSeedButton.addActionListener(e -> model.addSeed(artist));

        }
    }

    private void setAlbumsOnView(List<SpotifyAlbum> albums) {
        // Display up to 3 songs
        for(int i = 0; i < 3; i++) {
            SpotifyAlbum album = albums.get(i);
            JPanel song = (JPanel) this.songDisplay.getComponent(i);
            // Set image
            try {
                JLabel songImage = (JLabel) song.getComponent(0);

                URL url = URI.create(album.getImage()).toURL();
                BufferedImage image = ImageIO.read(url);
                Image resized = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH);

                songImage.setIcon(new ImageIcon(resized));
            } catch (Exception e) {

            }

            JPanel songText = (JPanel) song.getComponent(1);
            JTextPane songText0 = (JTextPane) songText.getComponent(0);
            songText0.setText(album.getName());

            JTextPane songText1 = (JTextPane) songText.getComponent(1);
            StringBuilder artists = new StringBuilder();
            for (SpotifyArtist artist : album.getArtists()) {
                artists.append(artist.getName()).append(", ");
            }
            songText1.setText("Artists: " + artists);

            JTextPane songText2 = (JTextPane) songText.getComponent(2);
            StringBuilder genres = new StringBuilder();
            for (String genre : album.getGenres()) {
                genres.append(genre).append(", ");
            }
            songText2.setText("Genres: " + genres);

            // Set Add Seed button to visible
            JPanel songButtonPanel = (JPanel) song.getComponent(2);
            JButton addSeedButton = (JButton) songButtonPanel.getComponent(0);

            addSeedButton.setVisible(true);

            // Remove previous action listeners
            for (ActionListener listener : addSeedButton.getListeners(ActionListener.class)) {
                addSeedButton.removeActionListener(listener);
            }

            // Add new listener
            addSeedButton.addActionListener(e -> model.addSeed(album));
        }
    }

    private void setTracksOnView(List<SpotifyTrack> tracks, boolean displayAddSeedButtons) {
        // Display up to 3 songs
        for(int i = 0; i < 3; i++) {
            if (i < tracks.size()) {
                SpotifyTrack track = tracks.get(i);
                JPanel song = (JPanel) this.songDisplay.getComponent(i);
                // Set image
                try {
                    JLabel songImage = (JLabel) song.getComponent(0);

                    URL url = URI.create(track.getAlbum().getImage()).toURL();
                    BufferedImage image = ImageIO.read(url);
                    Image resized = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);

                    songImage.setIcon(new ImageIcon(resized));
                } catch (Exception e) {

                }

                JPanel songText = (JPanel) song.getComponent(1);
                JTextPane songText0 = (JTextPane) songText.getComponent(0);
                songText0.setText(track.getName());

                JTextPane songText1 = (JTextPane) songText.getComponent(1);
                StringBuilder artists = new StringBuilder();
                for (SpotifyArtist artist : track.getArtists()) {
                    artists.append(artist.getName()).append(", ");
                }
                songText1.setText("Artists: " + artists);

                JTextPane songText2 = (JTextPane) songText.getComponent(2);
                songText2.setText("Album: " + track.getAlbum().getName());

                // Set Add Seed button to visible
                JPanel songButtonPanel = (JPanel) song.getComponent(2);
                JButton addSeedButton = (JButton) songButtonPanel.getComponent(0);

                addSeedButton.setVisible(displayAddSeedButtons);

                // Remove previous action listeners
                for (ActionListener listener : addSeedButton.getListeners(ActionListener.class)) {
                    addSeedButton.removeActionListener(listener);
                }

                // Add new listener
                addSeedButton.addActionListener(e -> model.addSeed(track));
            } else {
                setEmptySection(i);
            }
        }
    }

    private void getArtists() {
        String searchQuery = this.searchField.getText();
        List<SpotifyArtist> artists = model.searchForArtists(searchQuery);

        setArtistOnView(artists);
    }

    private void getAlbums() {
        String searchQuery = this.searchField.getText();
        List<SpotifyAlbum> albums = model.searchForAlbums(searchQuery);

        System.out.println(albums);

        setAlbumsOnView(albums);
    }

    private void getTracks() {
        String searchQuery = this.searchField.getText();
        List<SpotifyTrack> tracks = model.searchForTracks(searchQuery);

        setTracksOnView(tracks, true);
    }

    private void getRecommendations() {
        List<SpotifyTrack> tracks = model.getRecommendations();

        setTracksOnView(tracks, false);
    }
}
