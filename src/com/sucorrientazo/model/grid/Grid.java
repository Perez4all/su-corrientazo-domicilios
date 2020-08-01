package com.sucorrientazo.model.grid;

import com.sucorrientazo.model.Point;
import com.sucorrientazo.model.element.Moveable;

public interface Grid {

    <T extends Moveable> void addElement(T element);

    Moveable[][] getGrid();

    Point getCenter();

    void removeElementAt(Point point);

    <T extends Moveable> void moveElementTo(T element, Point point);

    <T extends Moveable> T findElement(T element);

    Grid clone();
}
