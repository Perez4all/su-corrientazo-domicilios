package com.sucorrientazo.model.element;

import com.sucorrientazo.model.Direction;
import com.sucorrientazo.model.grid.Grid;
import com.sucorrientazo.model.Point;
import com.sucorrientazo.model.Side;

public interface Moveable {

    void setGrid(Grid grid);

    void turn(Side side);

    void move();

    String getName();

    Direction getDirection();

    void setPoint(Point point);

    Point getPoint();

    Point getCartesianPoint();

    void resetPosition();

    void dispatch();
}
