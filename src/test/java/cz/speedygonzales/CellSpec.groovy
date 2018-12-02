package cz.speedygonzales

import spock.lang.Specification

class CellSpec extends Specification {

    def 'count number of neighbours'() {

        when:
        def result = cell.getNeighbours(world)

        then:
        result == neighbours

        where:
        world    | cell                 || neighbours
        world1() | new Cell(0,0) || 1
        world2() | new Cell(3,3) || 0
        world3() | new Cell(1,1) || 8
    }

    def 'test if cell will be alive in next iteration'() {

        when:
        def result = cell.shouldLiveInNextTick(world)

        then:
        result == alive

        where:
        world    | cell                 || alive
        world1() | new Cell(0,0) || false
        world2() | new Cell(3,3) || false
        world2() | new Cell(0,0) || true
        world3() | new Cell(1,1) || false
        world4() | new Cell(0,0) || true
        world4() | new Cell(0,1) || true
        world4() | new Cell(1,0) || true
        world4() | new Cell(1,) || true

    }

    Cell[][] world1() {
        [
                [new Cell(0,0, false), new Cell(0,1,true)]
        ]
    }

    Cell[][] world2() {
        [
                [new Cell(0,0, false), new Cell(0,1,true), new Cell(0,2, false)],
                [new Cell(1,0, false), new Cell(1,1,true), new Cell(1,2, false)]
        ]
    }

    Cell[][] world3() {
        [
                [new Cell(0,0, true), new Cell(0,1,true), new Cell(0,2, true)],
                [new Cell(1,0, true), new Cell(1,1,true), new Cell(1,2, true)],
                [new Cell(2,0, true), new Cell(2,1,true), new Cell(2,2, true)]
        ]
    }

    Cell[][] world4() {
        [
                [new Cell(0,0, true), new Cell(0,1,true)],
                [new Cell(1,0, true), new Cell(1,1,true)]
        ]
    }


}
