package com.example.moriczpeter_asztali_gui_countdown;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    @FXML
    private TextField dateInput;

    @FXML
    private Button startButton;

    @FXML
    private Label countdownLabel;

    private Timer timer;

    @FXML
    public void startCountdown(){
        String input = dateInput.getText();
        LocalDateTime targetDateTime;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            targetDateTime = LocalDateTime.parse(input, formatter);
        } catch (Exception e){
            showAlert("Hiba", "Kérjük, adjon meg egy érvényes dátumot a következő formátumban: YYYY-MM-DD HH:MM:SS");
            return;
        }

        if (targetDateTime.isBefore(LocalDateTime.now())){
            showAlert("Hiba", "A megadott dátumnak a jövőben kell lennie.");
            return;
        }

        startTimer(targetDateTime);
    }

    private void startTimer(LocalDateTime targetDateTime){
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalDateTime now =  LocalDateTime.now();
                Duration duration = Duration.between(now, targetDateTime);
                Period period =  Period.between(now.toLocalDate(), targetDateTime.toLocalDate());

                if (duration.isNegative() || duration.isZero()){
                    timer.cancel();
                    showAlert("Idő lejárt", "Az idő lejárt");
                    countdownLabel.setText("Visszaszámlálás: YYYY-HH-DD 00:00:00");
                } else {
                    int years = period.getYears();
                    int months = period.getMonths();
                    int days = period.getDays();

                    long hours = duration.toHours();
                    long minutes = duration.toMinutes() % 60;
                    long seconds = duration.getSeconds() % 60;
                    String countdown = String.format("Visszaszámlálás: %d év, %d hónap, %d nap, %02d:%02d:%02d", years, months, days, hours, minutes, seconds);

                    Platform.runLater(() -> countdownLabel.setText(countdown));

                }
            }
        }, 0, 1000); //másodperces frissítés
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}