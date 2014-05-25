package de.meldanor.davis;

import de.meldanor.davis.gui.MainGUI;
import javafx.application.Application;

public class Core {

    public static void main(String[] args) {
        System.out.println("Launching GUI...");
        Application.launch(MainGUI.class, args);
    }
}
