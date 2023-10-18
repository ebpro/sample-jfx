module fr.univtln.bruno.samples.jfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens fr.univtln.bruno.samples.jfx to javafx.fxml;
    exports fr.univtln.bruno.samples.jfx;
}