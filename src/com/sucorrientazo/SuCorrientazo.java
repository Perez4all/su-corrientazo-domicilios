package com.sucorrientazo;

import com.sucorrientazo.model.grid.GridMap;
import com.sucorrientazo.reader.DroneMovementReader;


public class SuCorrientazo {

    public static void main(String[] args) {
        DroneMovementReader movementReader = new DroneMovementReader(new GridMap(10));
        movementReader.setMaxQuantityOfLunch(3);
        movementReader.readAllMoves(DroneMovementReader.INPUT_DIR);
    }

}
