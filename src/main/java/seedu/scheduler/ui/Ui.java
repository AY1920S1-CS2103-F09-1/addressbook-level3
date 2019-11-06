package seedu.scheduler.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui extends RefreshListener, TabListener {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

}
