package com.sucorrientazo.model.element;

import com.sucorrientazo.model.Direction;
import com.sucorrientazo.model.grid.Grid;
import com.sucorrientazo.model.Point;
import com.sucorrientazo.model.Side;

public class Dron implements Moveable{

    private String name;

    private Point point;

    private int deliveredLunch;

    private Direction direction = Direction.NORTH;

    private Grid grid;

    public Dron(){

    }

    public Dron(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getDeliveredLunch() {
        return deliveredLunch;
    }

    public void setDeliveredLunch(int deliveredLunch) {
        this.deliveredLunch = deliveredLunch;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public void resetPosition() {
        this.grid.moveElementTo(this, this.grid.getCenter());
    }

    @Override
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    @Override
    public Point getCartesianPoint(){
        int x = this.grid.getCenter().X;
        int y = this.grid.getCenter().Y;
        return new Point(this.point.X - x, this.point.Y - y);
    }

    @Override
    public void turn(Side side) {
        if(this.direction == Direction.NORTH){
            if(side == Side.RIGHT){
                this.direction = Direction.EAST;
            } else {
                this.direction = Direction.WEST;
            }
        }else if(this.direction == Direction.WEST){
            if(side == Side.RIGHT) {
                this.direction = Direction.NORTH;
            } else {
                this.direction = Direction.SOUTH;
            }
        }else if(this.direction == Direction.SOUTH){
            if(side == Side.LEFT){
                this.direction = Direction.WEST;
            } else {
                this.direction = Direction.EAST;
            }
        }else if(this.direction == Direction.EAST){
            if(side == Side.LEFT){
                this.direction = Direction.NORTH;
            } else {
                this.direction = Direction.SOUTH;
            }
        }
    }

    @Override
    public void move() {
        if(grid != null) {
            grid.removeElementAt(point);
            if (this.direction.equals(Direction.NORTH)) {
                this.point.Y++;
            }
            if (this.direction.equals(Direction.EAST)) {
                this.point.X++;
            }
            if (this.direction.equals(Direction.WEST)) {
                this.point.X--;
            }
            if (this.direction.equals(Direction.SOUTH)) {
                this.point.Y--;
            }
            grid.moveElementTo(this, this.point);
        }
    }

    @Override
    public void dispatch(){
        grid.removeElementAt(this.getPoint());
    }

    @Override
    public String toString() {
        return "Dron{" +
                "name=" + name +
                ", point=" + point +
                ", cartesianPoint=" + getCartesianPoint() +
                ", lunchQuantity=" + deliveredLunch +
                ", direction=" + direction +
                '}';
    }


}
