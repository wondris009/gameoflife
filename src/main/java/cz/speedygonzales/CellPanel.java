package cz.speedygonzales;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.WHITE;

class CellPanel extends JPanel {

    CellPanel(Cell cell) {

        setLayout(new GridLayout(1, 1));

        setBorder(BorderFactory.createLineBorder(LIGHT_GRAY));

        if (cell.isAlive()) {
            setBackground(BLACK);
        } else {
            setBackground(WHITE);
        }
    }

}
