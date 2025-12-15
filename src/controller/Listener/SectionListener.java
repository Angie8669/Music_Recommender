package controller.Listener;

import controller.MusicRecommenderController;
import model.Spotify.SpotifyAlbum;
import model.Spotify.SpotifyArtist;
import model.Spotify.SpotifyObject;
import model.Spotify.SpotifyTrack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class SectionListener {
    private MusicRecommenderController controller;
    private JPanel section;

    public SectionListener(MusicRecommenderController c, JPanel s) {
        this.controller = c;
        this.section = s;
    }

    public void update(SpotifyObject obj, boolean displayAddSeedButtons) {
        if (obj instanceof SpotifyArtist artist) {
            setArtistOnView(artist);
        } else if (obj instanceof SpotifyAlbum album) {
            setAlbumOnView(album);
        } else if (obj instanceof SpotifyTrack track) {
            setTrackOnView(track, displayAddSeedButtons);
        } else {
            // Empty the section
            setSection(null, "","","","",false);
        }
    }

    private void setArtistOnView(SpotifyArtist artist) {
        // Build genres list string
        StringBuilder genres = new StringBuilder();
        genres.append("Genres: ");
        for (String genre : artist.getGenres()) {
            genres.append(genre).append(", ");
        }

        setSection(artist, artist.getImage(), artist.getName(), genres.toString(), "", true);
    }

    private void setAlbumOnView(SpotifyAlbum album) {
        // Build artists list string
        StringBuilder artists = new StringBuilder();
        artists.append("Artists: ");
        for (SpotifyArtist artist : album.getArtists()) {
            artists.append(artist.getName()).append(", ");
        }

        // Build genres list string
        StringBuilder genres = new StringBuilder();
        genres.append("Genres: ");
        for (String genre : album.getGenres()) {
            genres.append(genre).append(", ");
        }

        setSection(album, album.getImage(), album.getName(), artists.toString(), genres.toString(), true);
    }

    private void setTrackOnView(SpotifyTrack track, boolean displayAddSeedButtons) {
        // Build artists list string
        StringBuilder artists = new StringBuilder();
        artists.append("Artists: ");
        for (SpotifyArtist artist : track.getArtists()) {
            artists.append(artist.getName()).append(", ");
        }

        setSection(track, track.getAlbum().getImage(), track.getName(), artists.toString(), track.getAlbum().getName(), displayAddSeedButtons);
    }

    private void setSection(SpotifyObject obj, String imagePath, String text0, String text1, String text2,
                            boolean displayAddSeedButton) {
        // Set image
        JLabel songImage = (JLabel) section.getComponent(0);
        try {

            URL url = URI.create(imagePath).toURL();
            BufferedImage image = ImageIO.read(url);
            Image resized = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);

            songImage.setIcon(new ImageIcon(resized));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            songImage.setIcon(null);
        }

        JPanel songText = (JPanel) section.getComponent(1);

        JTextPane songText0 = (JTextPane) songText.getComponent(0);
        songText0.setText(text0);

        JTextPane songText1 = (JTextPane) songText.getComponent(1);
        songText1.setText(text1);

        // No text to display, so clear anything already there.
        JTextPane songText2 = (JTextPane) songText.getComponent(2);
        songText2.setText(text2);

        // Change Add Seed button's visibility
        JButton addSeedButton = (JButton) section.getComponent(2);
        addSeedButton.setVisible(displayAddSeedButton);

        // Remove previous action listeners
        for (ActionListener listener : addSeedButton.getListeners(ActionListener.class)) {
            addSeedButton.removeActionListener(listener);
        }

        // Add new listener
        if (obj != null) {
            addSeedButton.addActionListener(e -> controller.addSeed(obj));
        }
    }
}
