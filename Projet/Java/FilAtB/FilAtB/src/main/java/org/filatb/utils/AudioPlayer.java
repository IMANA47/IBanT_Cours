package org.filatb.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class AudioPlayer {
    private static MediaPlayer mediaPlayer;

    public static void jouerSon(String resourcePath) {
        try {
            URL resource = AudioPlayer.class.getResource(resourcePath);
            if (resource == null) {
                System.err.println("Fichier audio non trouvé : " + resourcePath);
                return;
            }
            Media sound = new Media(resource.toString());
            // Arrêter le précédent s'il joue encore
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}