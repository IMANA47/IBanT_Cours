package org.filatb.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {

    private static MediaPlayer mediaPlayer;

    /**
     * Joue un fichier audio situé dans le classpath (ressources).
     * @param resourcePath chemin relatif aux ressources, ex: "/sounds/call.wav"
     */
    public static void jouerSon(String resourcePath) {
        try {
            java.net.URL resource = AudioPlayer.class.getResource(resourcePath);
            if (resource == null) {
                System.err.println("Fichier audio non trouvé : " + resourcePath);
                return;
            }
            Media sound = new Media(resource.toString());
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