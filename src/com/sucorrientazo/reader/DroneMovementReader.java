package com.sucorrientazo.reader;

import com.sucorrientazo.model.Point;
import com.sucorrientazo.model.Side;
import com.sucorrientazo.model.element.Dron;
import com.sucorrientazo.model.element.Moveable;
import com.sucorrientazo.model.grid.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DroneMovementReader implements MovementReader{

    public static final String INPUT_DIR = System.getProperty("user.dir") + "\\src\\com\\sucorrientazo\\resources\\input\\";

    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\com\\sucorrientazo\\resources\\output\\";

    private int maxQuantityOfLunch = 3;

    private final Grid grid;

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public DroneMovementReader(Grid grid){
        this.grid = grid;
    }

    @Override
    public void readAllMoves(String directory){
        try(Stream<Path> files = Files.list(Paths.get(directory))){
            files.forEach(file -> {
                executorService.execute(() -> {
                    Grid droneGrid = grid.clone();
                    String fileName = file.getFileName().toString();
                    Dron dron = new Dron(fileName.replace(".txt", ""));
                    droneGrid.addElement(dron);
                    try (Stream<String> lineStream = Files.lines(file).limit(maxQuantityOfLunch)) {
                        String positions = fileMovesToFinalPositions(e -> dron.setDeliveredLunch(dron.getDeliveredLunch() + 1),
                                dron, lineStream);
                        writeOutput(positions, dron.getName().replace("in", "out"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dron.dispatch();
                });
            });
            executorService.shutdown();
            try {
                executorService.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String fileMovesToFinalPositions(Consumer<Moveable> intermediate, Moveable moveable, Stream<String> lineStream){
        return lineStream.map(s -> {
            String finalPos = performMoves(moveable, s);
            intermediate.accept(moveable);
            return finalPos;
        }).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String performMoves(Moveable element, String moves){
        moves.chars().forEach(m -> {
            char c = (char) m;
            if(c == 'D'){
                element.turn(Side.RIGHT);
            }
            if(c == 'I'){
                element.turn(Side.LEFT);
            }
            if(c == 'A'){
                element.move();
            }
        });
        StringBuilder builder = new StringBuilder();
        Point cartesianPoint = element.getCartesianPoint();
        builder.append("(").append(cartesianPoint.X).append(",")
                .append(cartesianPoint.Y)
                .append(") dirección ").append(element.getDirection());
        element.resetPosition();
        return builder.toString();
    }

    @Override
    public void writeOutput(String positions, String filename){
        Path path = Paths.get(OUTPUT_DIR + filename + ".txt");
        try {
            byte[] bytes = positions.getBytes();
            Files.write(path, bytes);
        } catch(IOException ex){
            ex.printStackTrace();
        }

    }

    public void setMaxQuantityOfLunch(int maxQuantityOfLunch) {
        this.maxQuantityOfLunch = maxQuantityOfLunch;
    }
}
