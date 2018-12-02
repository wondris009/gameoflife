package cz.speedygonzales;

import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

class GameOfLifeWorld {

    private int width, height;

    @Getter
    private Cell[][] cells;

    GameOfLifeWorld(int width, int height) {

        this.width = width;
        this.height = height;

        cells = new Cell[width][height];

        for (int w = 0; w < width; w++) {

            for (int h = 0; h < height; h++) {

                cells[w][h] = new Cell(w, h, getRandomLife());
            }
        }
    }

    Cell[][] updateWorldToNextIteration() {

        Cell[][] result = new Cell[width][height];

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                result[w][h] = new Cell(w, h, cells[w][h].shouldLiveInNextTick(cells));
            }
        }

        cells = result;

        return result;
    }

    private boolean getRandomLife() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
