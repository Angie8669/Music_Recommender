package controller;

import controller.Listener.SectionListener;
import model.MusicRecommenderModel;
import model.Spotify.*;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MusicRecommenderController {
    private MusicRecommenderModel model;
    private SectionManager sectionManager;

    private JPanel panelMain;
    private JTextField searchField;
    private JTextPane statusMessage;
    private JPanel loading;

    // Use a SwingWorker to handle API calls in the background
    private boolean swingWorkerHelper = false;
    private SwingWorker<List<SpotifyObject>, Void> swingWorker;

    public MusicRecommenderController(JPanel panel) {
        this.model = new MusicRecommenderModel();
        this.panelMain = panel;

        JPanel menuContainer = (JPanel) this.panelMain.getComponent(0);
        JPanel menu = (JPanel) menuContainer.getComponent(0);
        this.searchField = (JTextField) menu.getComponent(2);

        // Setup button listeners
        JButton searchArtistsButton = (JButton) menu.getComponent(3);
        searchArtistsButton.addActionListener(e -> getArtists());

        JButton searchAlbumsButton = (JButton) menu.getComponent(4);
        searchAlbumsButton.addActionListener(e -> getAlbums());

        JButton searchSongsButton = (JButton) menu.getComponent(5);
        searchSongsButton.addActionListener(e -> getTracks());

        JButton clearSeedsButton = (JButton) menu.getComponent(6);
        clearSeedsButton.addActionListener(e -> clearSeeds());

        JButton recommendationsButton = (JButton) menu.getComponent(7);
        recommendationsButton.addActionListener(e -> getRecommendations());

        JPanel statusContainer = (JPanel) menu.getComponent(8);
        this.statusMessage = (JTextPane) statusContainer.getComponent(0);

        this.loading = (JPanel) menu.getComponent(9);
        JButton cancelRequestButton = (JButton) this.loading.getComponent(1);
        cancelRequestButton.addActionListener(e -> cancelRequest());

         JPanel songDisplay = (JPanel) this.panelMain.getComponent(2);

        // Setup observers
        this.sectionManager = new SectionManager();

        for (int i = 0; i < 3; i++) {
            JPanel section = (JPanel) songDisplay.getComponent(i);
            SectionListener listener = new SectionListener(this, section);
            this.sectionManager.addListener(listener);
        }
    }

    private void notifyListeners(List<SpotifyObject> objects, String type, boolean displayAddSeedButtons) {
        // Remove loading spinner and Cancel button
        this.loading.setVisible(false);

        // If empty list, output different status message.
        if (objects.isEmpty()) {
            this.statusMessage.setText("Retrieved no data for provided search query.");
            this.sectionManager.notify(objects, displayAddSeedButtons);
            return;
        }

        // Update status message
        if (type.equals("artists")) {
            this.statusMessage.setText("Successfully retrieved artist data.");
        } else if (type.equals("albums")) {
            this.statusMessage.setText("Successfully retrieved album data.");
        } else if (type.equals("tracks")) {
            this.statusMessage.setText("Successfully retrieved track data.");
        } else if (type.equals("recs")) {
            this.statusMessage.setText("Successfully retrieved recommendation data.");
        }

        this.sectionManager.notify(objects, displayAddSeedButtons);
    }

    private void getArtists() {
        if (!swingWorkerHelper) {
            String searchQuery = this.searchField.getText();

            swingWorker = new SwingWorker<>() {
                @Override
                protected List<SpotifyObject> doInBackground() throws Exception {
                    if (!swingWorkerHelper) {
                        swingWorkerHelper = true;
                    }
                    return model.searchForArtists(searchQuery);
                }

                @Override
                public void done() {
                    try {
                        notifyListeners(this.get(), "artists", true);
                        swingWorkerHelper = false;
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace(System.out);
                    }
                }
            };
            swingWorker.execute();
            this.statusMessage.setText("Getting artist data from Spotify...");

            // Display loading spinner
            this.loading.setVisible(true);
        } else {
            this.statusMessage.setText("Cannot execute task, task already in progress.");
        }
    }

    private void getAlbums() {
        if (!swingWorkerHelper) {
            String searchQuery = this.searchField.getText();

            swingWorker = new SwingWorker<>() {
                @Override
                protected List<SpotifyObject> doInBackground() throws Exception {
                    if (!swingWorkerHelper) {
                        swingWorkerHelper = true;
                    }
                    return model.searchForAlbums(searchQuery);
                }

                @Override
                public void done() {
                    try {
                        notifyListeners(this.get(), "albums", true);
                        swingWorkerHelper = false;
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace(System.out);
                    }
                }
            };
            swingWorker.execute();
            this.statusMessage.setText("Getting album data from Spotify...");

            // Display loading spinner
            this.loading.setVisible(true);
        } else {
            this.statusMessage.setText("Cannot execute task, task already in progress.");
        }
    }

    private void getTracks() {
        if (!swingWorkerHelper) {
            String searchQuery = this.searchField.getText();

            swingWorker = new SwingWorker<>() {
                @Override
                protected List<SpotifyObject> doInBackground() throws Exception {
                    if (!swingWorkerHelper) {
                        swingWorkerHelper = true;
                    }
                    return model.searchForTracks(searchQuery);
                }

                @Override
                public void done() {
                    try {
                        notifyListeners(this.get(), "tracks", true);
                        swingWorkerHelper = false;
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace(System.out);
                    }
                }
            };
            swingWorker.execute();
            this.statusMessage.setText("Getting track data from Spotify...");

            // Display loading spinner
            this.loading.setVisible(true);
        } else {
            this.statusMessage.setText("Cannot execute task, task already in progress.");
        }
    }

    private void getRecommendations() {
        if (!swingWorkerHelper) {
            swingWorker = new SwingWorker<>() {
                @Override
                protected List<SpotifyObject> doInBackground() {
                    if (!swingWorkerHelper) {
                        swingWorkerHelper = true;
                    }
                    return model.getRecommendations();
                }

                @Override
                public void done() {
                    try {
                        notifyListeners(this.get(), "recs", false);
                        swingWorkerHelper = false;
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace(System.out);
                    }
                }
            };
            swingWorker.execute();
            this.statusMessage.setText("Getting recommendations from Spotify...");

            // Display loading spinner
            this.loading.setVisible(true);
        } else {
            this.statusMessage.setText("Cannot execute task, task already in progress.");
        }
    }

    public void addSeed(SpotifyObject obj) {
        this.model.addSeed(obj);
        this.statusMessage.setText("Added " + obj.getName() + " to seed list.");
    }

    private void clearSeeds() {
        this.model.clearSeeds();
        this.statusMessage.setText("Cleared seed list.");
    }

    private void cancelRequest() {
        // Check value of swingWorkerHelper to determine which SwingWorker to cancel.
        if (swingWorkerHelper) {
            this.swingWorker.cancel(true);
        }

        // Reset swingWorkerHelper
        this.swingWorkerHelper = false;
        this.loading.setVisible(false);
        this.statusMessage.setText("Cancelled API request.");
    }
}
