package de.meldanor.davis.gui;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class GraphCanvas extends Canvas {

    private Font standardFont;
    private FontMetrics fontMetrics;

    public GraphCanvas(int x, int y) {
        super(x, y);
        standardFont = new Font("Sans Serif", 24);
        fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(standardFont);
    }

    public void draw() {

        GraphicsContext g = this.getGraphicsContext2D();
        g.setFont(standardFont);
        g.fillText("Hello World", getWidth() / 2 - (fontMetrics.computeStringWidth("Hello World") / 2), getHeight() / 2);
        // Drawn something
    }

}
