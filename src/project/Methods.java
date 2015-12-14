package project;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sorting.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Omer Ufuk Efendioglu
 */
public class Methods {

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    //Makes draggable to the given Anchor Pane
    protected AnchorPane makeDraggable(final AnchorPane anchorPane) {
        final DragContext dragContext = new DragContext();

        anchorPane.addEventFilter(
                javafx.scene.input.MouseEvent.ANY,
                new EventHandler<javafx.scene.input.MouseEvent>() {
                    public void handle(final javafx.scene.input.MouseEvent mouseEvent) {
                        mouseEvent.consume();
                    }
                });

        anchorPane.addEventFilter(
                javafx.scene.input.MouseEvent.MOUSE_PRESSED,
                new EventHandler<javafx.scene.input.MouseEvent>() {
                    public void handle(final javafx.scene.input.MouseEvent mouseEvent) {
                        // remember initial mouse cursor coordinates
                        // and node position
                        dragContext.mouseAnchorX = mouseEvent.getX();
                        dragContext.mouseAnchorY = mouseEvent.getY();
                        dragContext.initialTranslateX =
                                anchorPane.getTranslateX();
                        dragContext.initialTranslateY =
                                anchorPane.getTranslateY();
                    }
                });

        anchorPane.addEventFilter(
                javafx.scene.input.MouseEvent.MOUSE_DRAGGED,
                new EventHandler<javafx.scene.input.MouseEvent>() {
                    public void handle(final javafx.scene.input.MouseEvent mouseEvent) {
                        // shift node from its initial position by delta
                        // calculated from mouse cursor movement
                        anchorPane.setTranslateX(
                                dragContext.initialTranslateX
                                        + mouseEvent.getX()
                                        - dragContext.mouseAnchorX);
                        anchorPane.setTranslateY(
                                dragContext.initialTranslateY
                                        + mouseEvent.getY()
                                        - dragContext.mouseAnchorY);
                    }
                });

        return anchorPane;
    }

    public void fadeTrans(Node node, double fromValue, double toValue, int millis) {
        FadeTransition fadeTransition
                = new FadeTransition(Duration.millis(millis), node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.play();
    }

    public int[] getRandomArray(int arrayLenght, int n) {
        Random rnd = new Random();
        int[] array = new int[arrayLenght];
        int j = 0;
        for (int i = 0; i < arrayLenght; i++) {
            j = rnd.nextInt(n);
            array[i] = j;
        }
        return array;
    }

    public double getBitonicTime(int arrayLenght, int[] array) {
        double start = System.nanoTime();
        BitonicSort bitonic = new BitonicSort(array);
        double end = System.nanoTime();
        double timeInMillis = TimeUnit.MICROSECONDS.convert((long) (end - start), TimeUnit.NANOSECONDS);
        return timeInMillis;
    }

    public double getCountingTime(int arrayLenght, int[] array) {
        double start = System.nanoTime();
        CountingSort counting = new CountingSort();
        counting.counting_sort(array);
        double end = System.nanoTime();
        return (double) TimeUnit.MICROSECONDS.convert((long) (end - start), TimeUnit.NANOSECONDS);
    }

    public double getHeapTime(int arrayLenght, int[] array) {
        double start = System.nanoTime();
        HeapSort heap = new HeapSort();
        heap.heapSort(array);
        double end = System.nanoTime();
        return (double) TimeUnit.MICROSECONDS.convert((long) (end - start), TimeUnit.NANOSECONDS);
    }

    public double getMergeTime(int arrayLenght, int[] array) {
        double start = System.nanoTime();
        MergeSort merge = new MergeSort();
        merge.mergeSort(array);
        double end = System.nanoTime();
        return (double) TimeUnit.MICROSECONDS.convert((long) (end - start), TimeUnit.NANOSECONDS);
    }

    public double getRadixTime(int arrayLenght, int[] array) {
        double start = System.nanoTime();
        RadixSort radix = new RadixSort();
        radix.radixSort(array);
        double end = System.nanoTime();
        return (double) TimeUnit.MICROSECONDS.convert((long) (end - start), TimeUnit.NANOSECONDS);
    }

    public double getShellTime(int arrayLenght, int[] array) {
        double start = System.nanoTime();
        ShellSort shell = new ShellSort();
        shell.shellSort(array);
        double end = System.nanoTime();
        return (double) TimeUnit.MICROSECONDS.convert((long) (end - start), TimeUnit.NANOSECONDS);
    }
}