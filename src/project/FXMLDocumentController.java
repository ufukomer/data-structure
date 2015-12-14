package project;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import queue.LinkedQueue;
import tree.Tree;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Omer Ufuk Efendioglu
 */
public class FXMLDocumentController implements Initializable {

    private static final LinkedQueue q1 = new LinkedQueue();
    private static final Tree t1 = new Tree();
    private int counter;
    double firstWidth;
    double lastWidt;
    int firstX, firstY, lastX, lastY;

    @FXML
    private ComboBox comboBox;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private TextField elementTextField;

    @FXML
    private TextArea textArea;

    @FXML
    private Label label;

    @FXML
    private TextField arrayLenghtTextField;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Button sortButton;

    @FXML
    private AnchorPane treeAnchorPane;

    @FXML
    private TextField treeTextField;

    @FXML
    private CheckBox treeCheckBox;

    @FXML
    private Button insertTreeButton;

    @FXML
    private AnchorPane treeAnchorPane2;

    @FXML
    private ComboBox orderComboBox;

    @FXML
    private TextArea textArea2;

    @FXML
    private TabPane tabPane;

    @FXML   //Change tab to Queue Tab
    private void changeTabToQueue() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);
    }

    @FXML   //Change tab to Sorting Tab
    private void changeTabToSorting() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(1);
    }

    @FXML   //Change tab to Tree
    private void changeTabToTree() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(2);
    }

    @FXML   //Reset tree, queue and remove all text data
    private void resetAll(ActionEvent event) {
        deleteTree(event);
        while (counter != 0) {
            removeQueueData(event);
            counter--;
        }
        textArea.setText(null);
        elementTextField.setText(null);
        arrayLenghtTextField.setText(null);
        treeTextField.setText(null);
        label.setText(null);
        pieChart.setVisible(false);
        barChart.setVisible(false);
    }

    @FXML   //Order tree according to user's request
    private void treeOrder(ActionEvent event) {

        if (orderComboBox.getValue() == "In-order") {
            textArea2.appendText("In-order:");
            t1.inOrder(t1.root, textArea2);
        } else if (orderComboBox.getValue() == "Pre-order") {
            textArea2.appendText("Pre-order:");
            t1.preOrder(t1.root, textArea2);
        } else if (orderComboBox.getValue() == "Post-order") {
            textArea2.appendText("Post-order:");
            t1.postOrder(t1.root, textArea2);
        } else if (orderComboBox.getValue() == "Level-order") {
            textArea2.appendText("Level-order:");
            t1.levelOrder(textArea2);
        }
        textArea2.appendText("\n");
    }

    @FXML   //Delete all tree data
    private void deleteTree(ActionEvent event) {

        textArea2.setText(null);
        while (t1.root != null) {
            t1.deleteAll(t1.root);
        }

        ObservableList<Node> children = treeAnchorPane.getChildren();
        treeAnchorPane.getChildren().removeAll(children);
    }

    @FXML
    private void insertTreeNode(ActionEvent event) {
        try {
            if (treeTextField.getText().length() <= 3) {

                if (!treeCheckBox.isSelected()) {
                    t1.insert(treeAnchorPane, Integer.parseInt(treeTextField.getText()));
                } else if (treeCheckBox.isSelected()) {
                    Methods m1 = new Methods();
                    int array[] = m1.getRandomArray(Integer.parseInt(treeTextField.getText()), 999);
                    for (int data : array) {
                        t1.insert(treeAnchorPane, data);
                    }
                }

                new Thread() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                insertTreeButton.setDisable(true);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                insertTreeButton.setDisable(false);
                            }
                        });
                    }
                }.start();
            }
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    @FXML   //Get a random array and put sorting process times into charts
    private void sortAnalysis(ActionEvent event) {
        boolean powTwo = false;
        for (int i = 1; Integer.parseInt(arrayLenghtTextField.getText()) + 1 > Math.pow(2, i); i++) {
            System.out.println(Math.pow(2, i));
            if (Integer.parseInt(arrayLenghtTextField.getText()) == (int) Math.pow(2, i)) {
                powTwo = true;
            }
        }

        //Pie Chart
        if (comboBox.getValue() == "Pie Chart") {
            try {
                barChart.setVisible(false);
                pieChart.setVisible(true);

                int arrayLenght = Integer.parseInt(arrayLenghtTextField.getText());
                Methods m1 = new Methods();
                int[] array = m1.getRandomArray(arrayLenght, 10000);

                double bitonicValue = m1.getBitonicTime(arrayLenght, array);
                double countingValue = m1.getCountingTime(arrayLenght, array);
                double heapValue = m1.getHeapTime(arrayLenght, array);
                double mergeValue = m1.getMergeTime(arrayLenght, array);
                double radixValue = m1.getRadixTime(arrayLenght, array);
                double shellValue = m1.getShellTime(arrayLenght, array);

                if (powTwo) {
                    ObservableList<PieChart.Data> pieChartData =
                            FXCollections.observableArrayList(
                                    new PieChart.Data("Bitonic Sort", bitonicValue),
                                    new PieChart.Data("Counting Sort", countingValue),
                                    new PieChart.Data("Heap Sort", heapValue),
                                    new PieChart.Data("Merge Sort", mergeValue),
                                    new PieChart.Data("Radix Sort", radixValue),
                                    new PieChart.Data("Shell Sort", shellValue));
                    pieChart.setData(pieChartData);
                } else if (!powTwo) {
                    ObservableList<PieChart.Data> pieChartData =
                            FXCollections.observableArrayList(
                                    new PieChart.Data("Counting Sort", countingValue),
                                    new PieChart.Data("Heap Sort", heapValue),
                                    new PieChart.Data("Merge Sort", mergeValue),
                                    new PieChart.Data("Radix Sort", radixValue),
                                    new PieChart.Data("Shell Sort", shellValue));
                    pieChart.setData(pieChartData);
                }
            } catch (Exception ex) {
                ex.getStackTrace();
            }
        }

        //Bar Chart
        else if (comboBox.getValue() == "Bar Chart") {
            try {
                pieChart.setVisible(false);
                barChart.setVisible(true);

                int arrayLenght = Integer.parseInt(arrayLenghtTextField.getText());
                Methods m1 = new Methods();
                int[] array = m1.getRandomArray(arrayLenght, 10000);

                double bitonicValue = m1.getBitonicTime(arrayLenght, array);
                double countingValue = m1.getCountingTime(arrayLenght, array);
                double heapValue = m1.getHeapTime(arrayLenght, array);
                double mergeValue = m1.getMergeTime(arrayLenght, array);
                double radixValue = m1.getRadixTime(arrayLenght, array);
                double shellValue = m1.getShellTime(arrayLenght, array);

                barChart.setData(getChartData(arrayLenghtTextField.getText(), bitonicValue,
                        countingValue, heapValue, mergeValue, radixValue, shellValue, powTwo));
            } catch (Exception ex) {
                ex.getStackTrace();
            }
        }

        //Disable sortButton for a specific time
        new Thread() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        sortButton.setDisable(true);
                    }
                });
                try {
                    Thread.sleep(1000); //5 seconds, obviously replace with your chosen time
                } catch (InterruptedException ex) {
                }

                Platform.runLater(new Runnable() {
                    public void run() {
                        sortButton.setDisable(false);
                    }
                });
            }
        }.start();
    }

    @FXML   //If isSelected insert counter into queue, else insert by getting from arrayLenghtTextField
    private void insertQueueData(ActionEvent event) {

        try {
            textArea.setText(null);
            if (checkBox.isSelected()) {
                for (int i = 0; i < Integer.parseInt(elementTextField.getText()); i++) {
                    counter++;
                    q1.insert(counter);
                }
            } else if (!checkBox.isSelected()) {
                counter++;
                q1.insert(counter);
            }

            q1.display(textArea);

            Methods m1 = new Methods();
            m1.fadeTrans(label, 0.0, 1.0, 500);
            label.setText("Average Processing Time: " + String.valueOf(q1.averageProcessingTime()) + " sec");

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException ex) {
            System.out.println("Type integer number.");
            textArea.setText(null);
            textArea.appendText("Type integer number.");
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    @FXML   //Remove front of the queue
    private void removeQueueData(ActionEvent event) {
        try {
            if (!q1.isEmpty()) {
                textArea.setText(null);
            }
            q1.remove(textArea);

            Methods m1 = new Methods();
            m1.fadeTrans(label, 0.0, 1.0, 500);
            label.setText("Average Processing Time: " + String.valueOf(q1.averageProcessingTime()) + " sec");
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    @FXML   //If !checkBox.isSelected disable elementTextField
    private void checkBoxAction() {

        if (!checkBox.isSelected()) {
            elementTextField.setDisable(true);
        } else {
            elementTextField.setDisable(false);
        }
    }

    @FXML   //Get stage from source and close it
    private void closeAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Methods m1 = new Methods();
        treeAnchorPane = m1.makeDraggable(treeAnchorPane);
        firstWidth = treeAnchorPane.getWidth();

        //Change translateX of children of treeAnchorPane for prevent display decrement
        treeAnchorPane.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                try {
                    lastWidt = treeAnchorPane.getWidth();
                    ObservableList<Node> children = treeAnchorPane.getChildren();
                    for (Node list : children) {
                        //AnchorPane.setRightAnchor(list, treeAnchorPane.getWidth()/2);
                        //list.setLayoutX(list.getLayoutX()+(lastWidt-firstWidth)- list.getLayoutBounds().getMinX());
                        if (firstWidth != 0)
                            list.setTranslateX(list.getTranslateX() + ((lastWidt - firstWidth) / 2));
                    }
                    firstWidth = treeAnchorPane.getWidth();
                } catch (Exception ex) {
                    ex.getStackTrace();
                }
            }
        });

        //Set minimum size values of treeAnchorPane for prevent sight decrement
        treeAnchorPane2.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                try {
                    treeAnchorPane.setMinHeight(treeAnchorPane2.getHeight());
                    treeAnchorPane.setMinWidth(treeAnchorPane2.getWidth());

                } catch (Exception ex) {
                    ex.getStackTrace();
                }
            }
        });

        //Set items into comboBox
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Pie Chart",
                        "Bar Chart"
                );
        comboBox.setItems(options);
        comboBox.setValue("Pie Chart");

        ObservableList<String> orders =
                FXCollections.observableArrayList(
                        "Level-order",
                        "In-order",
                        "Pre-order",
                        "Post-order"
                );
        orderComboBox.setItems(orders);
        orderComboBox.setValue("Level-order");

    }

    //Method to create data for Bar Chart
    private ObservableList<XYChart.Series<String, Double>> getChartData(String arraylenght, double bitonicValue,
                                                                        double countingValue, double heapValue,
                                                                        double mergeValue, double radixValue,
                                                                        double shellValue, boolean powTwo) {
        if (powTwo) {
            ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
            Series<String, Double> bitonicSeries = new Series<String, Double>();
            Series<String, Double> countingSeries = new Series<String, Double>();
            Series<String, Double> heapSeries = new Series<String, Double>();
            Series<String, Double> mergeSeries = new Series<String, Double>();
            Series<String, Double> radixSeries = new Series<String, Double>();
            Series<String, Double> shellSeries = new Series<String, Double>();
            bitonicSeries.setName("Bitonic Sort");
            countingSeries.setName("Counting Sort");
            heapSeries.setName("Heap Sort");
            mergeSeries.setName("Merge Sort");
            radixSeries.setName("Radix Sort");
            shellSeries.setName("Shell Sort");
            arraylenght = "Array Lenght: " + arraylenght;
            bitonicSeries.getData().add(new XYChart.Data(arraylenght, bitonicValue));
            countingSeries.getData().add(new XYChart.Data(arraylenght, countingValue));
            heapSeries.getData().add(new XYChart.Data(arraylenght, heapValue));
            mergeSeries.getData().add(new XYChart.Data(arraylenght, mergeValue));
            radixSeries.getData().add(new XYChart.Data(arraylenght, radixValue));
            shellSeries.getData().add(new XYChart.Data(arraylenght, shellValue));

            answer.addAll(bitonicSeries, countingSeries, heapSeries, mergeSeries, radixSeries, shellSeries);
            return answer;
        } else {
            ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
            Series<String, Double> countingSeries = new Series<String, Double>();
            Series<String, Double> heapSeries = new Series<String, Double>();
            Series<String, Double> mergeSeries = new Series<String, Double>();
            Series<String, Double> radixSeries = new Series<String, Double>();
            Series<String, Double> shellSeries = new Series<String, Double>();
            countingSeries.setName("Counting Sort");
            heapSeries.setName("Heap Sort");
            mergeSeries.setName("Merge Sort");
            radixSeries.setName("Radix Sort");
            shellSeries.setName("Shell Sort");
            arraylenght = "Array Lenght: " + arraylenght;
            countingSeries.getData().add(new XYChart.Data(arraylenght, countingValue));
            heapSeries.getData().add(new XYChart.Data(arraylenght, heapValue));
            mergeSeries.getData().add(new XYChart.Data(arraylenght, mergeValue));
            radixSeries.getData().add(new XYChart.Data(arraylenght, radixValue));
            shellSeries.getData().add(new XYChart.Data(arraylenght, shellValue));

            answer.addAll(countingSeries, heapSeries, mergeSeries, radixSeries, shellSeries);
            return answer;
        }
    }
}