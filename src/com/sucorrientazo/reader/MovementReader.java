package com.sucorrientazo.reader;

import com.sucorrientazo.model.element.Moveable;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface MovementReader {

    void readAllMoves(String directory);

    String fileMovesToFinalPositions(Consumer<Moveable> intermediate, Moveable moveable, Stream<String> lineStream);

    String performMoves(Moveable element, String moves);

    void writeOutput(String positions, String filename);
}
