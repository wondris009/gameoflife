package cz.speedygonzales;

import lombok.Getter;

@Getter
class Cell {

    private int x, y;

    private boolean isAlive;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
    }

    Cell(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }

    int getNeighbours(Cell[][] cells) {

        Cell ne = getCellOnCoordinates(x + 1, y + 1, cells);
        Cell e = getCellOnCoordinates(x + 1, y, cells);
        Cell se = getCellOnCoordinates(x + 1, y - 1, cells);
        Cell s = getCellOnCoordinates(x, y - 1, cells);
        Cell sw = getCellOnCoordinates(x - 1, y - 1, cells);
        Cell w = getCellOnCoordinates(x - 1, y, cells);
        Cell nw = getCellOnCoordinates(x - 1, y + 1, cells);
        Cell n = getCellOnCoordinates(x, y + 1, cells);

        return
                countAsLiveNeighbour(ne) +
                countAsLiveNeighbour(e) +
                countAsLiveNeighbour(se) +
                countAsLiveNeighbour(s) +
                countAsLiveNeighbour(sw) +
                countAsLiveNeighbour(w) +
                countAsLiveNeighbour(nw) +
                countAsLiveNeighbour(n);
    }

    private Cell getCellOnCoordinates(int x, int y, Cell[][] cells) {

        try {
            return cells[x][y];
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            //ignore cells out of grid
            return null;
        }
    }


    private int countAsLiveNeighbour(Cell cell) {
        return cell != null && cell.isAlive() ? 1 : 0;
    }

    boolean shouldLiveInNextTick(Cell[][] cells) {

        int neighbourCount = getNeighbours(cells);

        boolean shouldLive = false;

        if (isAlive) {

            if (neighbourCount < 2 || neighbourCount > 3) {
                shouldLive = false;
            } else if (neighbourCount >= 2 && neighbourCount <= 3) {
                shouldLive = true;
            }
        } else {
            if (neighbourCount == 3) {
                shouldLive = true;
            }
        }

        return shouldLive;
    }
}
