module fr.univtln.bruno.samples.jfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.logging;
    requires javafaker;
    requires java.sql;

    opens fr.univtln.bruno.samples.jfx.fxapp1 to javafx.fxml;
    exports fr.univtln.bruno.samples.jfx.fxapp1;
    exports fr.univtln.bruno.samples.jfx.fxapp2;
    opens fr.univtln.bruno.samples.jfx.fxapp2 to javafx.fxml;
    exports fr.univtln.bruno.samples.jfx.fxapp2.view;
    opens fr.univtln.bruno.samples.jfx.fxapp2.view to javafx.fxml;
    exports fr.univtln.bruno.samples.jfx.fxapp2.model;
    opens fr.univtln.bruno.samples.jfx.fxapp2.model to javafx.fxml;

}