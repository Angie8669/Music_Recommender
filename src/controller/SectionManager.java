package controller;

import controller.Listener.SectionListener;
import model.Spotify.SpotifyObject;

import java.util.ArrayList;
import java.util.List;

public class SectionManager {
    private List<SectionListener> listeners = new ArrayList<>();

    public void addListener(SectionListener listener) {
        this.listeners.add(listener);
    }

    public void notify(List<SpotifyObject> objects, boolean displayAddSeedButton) {
        for (int i = 0; i < listeners.size(); i++) {
            if (i < objects.size()) {
                listeners.get(i).update(objects.get(i), displayAddSeedButton);
            } else {
                // Pass a null object to clear out the section
                listeners.get(i).update(null, displayAddSeedButton);
            }
        }
    }
}
