module com.example.moriczpeter_asztali_gui_countdown {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.moriczpeter_asztali_gui_countdown to javafx.fxml;
    exports com.example.moriczpeter_asztali_gui_countdown;
}