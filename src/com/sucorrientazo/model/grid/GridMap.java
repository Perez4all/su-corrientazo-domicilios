package com.sucorrientazo.model.grid;

import com.sucorrientazo.model.Point;
import com.sucorrientazo.model.element.Moveable;

public class GridMap implements Grid {

    private int size = 10;
    private int columns;
    private int rows;
    private Point center;
    private Moveable[][] grid;

    public GridMap(int size){
        this.size = size;
        int rows = (size * 2) + 1;
        int columns = (size * 2) + 1;
        grid = new Moveable[rows][columns];
        center = new Point(this.size, this.size);
    }

    @Override
    public <T extends Moveable> void addElement(T element) {
        grid[this.size][this.size] = element;
        element.setPoint(new Point(this.size, this.size));
        element.setGrid(this);
    }


    @Override
    public Moveable[][] getGrid() {
        return grid;
    }

    @Override
    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    @Override
    public void removeElementAt(Point point) {
        grid[point.X][point.Y] = null;
    }

    @Override
    public  <T extends Moveable> void moveElementTo(T element, Point point) {
        removeElementAt(element.getPoint());
        try {
            grid[point.X][point.Y] = element;
        } catch (IndexOutOfBoundsException ex){
            System.out.println("Drone can only deliver at 10 blocks around");
        }
    }

    @Override
    public <T extends Moveable> T findElement(T element){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(grid[i][j] == element){
                    return element;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                matrix.append(grid[i][j] == null ? "-" : "O").append(" ");
            }
            matrix.append("\n");
        }
        return "GridMap{" +
                "grid=\n" + matrix.toString() +
                "\n}";
    }

    @Override
    public Grid clone() {
        return new GridMap(this.size);
    }
}
