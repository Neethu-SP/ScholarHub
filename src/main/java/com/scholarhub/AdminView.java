package com.scholarhub;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminView {

    public static void show(Stage stage) {

        // ==========================================
        // BACK BUTTON
        // ==========================================

        Button backButton = new Button("←");

        backButton.getStyleClass().add(
                "back-button"
        );

        backButton.setOnAction(
                e -> Dashboard.show(stage)
        );


        // ==========================================
        // TITLE
        // ==========================================

        Label title =
                new Label("Application Management");

        title.getStyleClass().add(
                "dashboard-title"
        );


        Label subtitle =
                new Label(
                        "Review and manage scholarship applications."
                );

        subtitle.getStyleClass().add(
                "dashboard-subtitle"
        );


        VBox header =
                new VBox(8);

        header.getChildren().addAll(
                backButton,
                title,
                subtitle
        );


        // ==========================================
        // APPLICATION LIST
        // ==========================================

        VBox applicationList =
                new VBox(18);

        applicationList.setPadding(
                new Insets(10, 0, 20, 0)
        );


        // ==========================================
        // DATABASE QUERY
        // ==========================================

        String query = """
                SELECT a.application_id,
                       a.applied_date,
                       a.status,
                       u.name AS student_name,
                       u.email,
                       s.scholarship_id,
                       s.name AS scholarship_name,
                       s.provider
                FROM applications a
                JOIN users u
                ON a.user_id = u.user_id
                JOIN scholarships s
                ON a.scholarship_id = s.scholarship_id
                ORDER BY a.applied_date DESC
                """;


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query);

                ResultSet result =
                        statement.executeQuery()
        ) {

            boolean found = false;


            // ==========================================
            // LOAD APPLICATIONS
            // ==========================================

            while (result.next()) {

                found = true;


                int applicationId =
                        result.getInt(
                                "application_id"
                        );


                String studentName =
                        result.getString(
                                "student_name"
                        );


                String email =
                        result.getString(
                                "email"
                        );


                String scholarshipName =
                        result.getString(
                                "scholarship_name"
                        );


                String provider =
                        result.getString(
                                "provider"
                        );


                String appliedDate =
                        result.getString(
                                "applied_date"
                        );


                String currentStatus =
                        result.getString(
                                "status"
                        );


                // ==========================================
                // APPLICATION CARD
                // ==========================================

                VBox card =
                        new VBox(10);

                card.getStyleClass().add(
                        "scholarship-card"
                );

                card.setPadding(
                        new Insets(20)
                );


                // ==========================================
                // STUDENT
                // ==========================================

                Label studentLabel =
                        new Label(
                                "Student: " +
                                studentName
                        );

                studentLabel.getStyleClass().add(
                        "scholarship-name"
                );


                // ==========================================
                // EMAIL
                // ==========================================

                Label emailLabel =
                        new Label(
                                "Email: " +
                                email
                        );

                emailLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // SCHOLARSHIP
                // ==========================================

                Label scholarshipLabel =
                        new Label(
                                "Scholarship: " +
                                scholarshipName
                        );

                scholarshipLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // PROVIDER
                // ==========================================

                Label providerLabel =
                        new Label(
                                "Provider: " +
                                provider
                        );

                providerLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // APPLIED DATE
                // ==========================================

                Label dateLabel =
                        new Label(
                                "Applied on: " +
                                appliedDate
                        );

                dateLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // CURRENT STATUS
                // ==========================================

                Label statusTitle =
                        new Label(
                                "Current Status"
                        );

                statusTitle.getStyleClass().add(
                        "section-title"
                );


                Label statusLabel =
                        new Label(
                                "● " + currentStatus
                        );

                statusLabel.getStyleClass().add(
                        "application-status"
                );


                // ==========================================
                // STATUS DROPDOWN
                // ==========================================

                ComboBox<String> statusBox =
                        new ComboBox<>();


                statusBox.getItems().addAll(
                        "APPLIED",
                        "UNDER REVIEW",
                        "APPROVED",
                        "REJECTED"
                );


                statusBox.setValue(
                        currentStatus
                );


                statusBox.setPrefWidth(
                        180
                );


                // ==========================================
                // UPDATE BUTTON
                // ==========================================

                Button updateButton =
                        new Button(
                                "UPDATE STATUS"
                        );

                updateButton.getStyleClass().add(
                        "login-button"
                );


                updateButton.setOnAction(
                        e -> {

                            String newStatus =
                                    statusBox.getValue();

                            updateApplicationStatus(
                                    applicationId,
                                    newStatus
                            );

                            show(stage);
                        }
                );


                // ==========================================
                // STATUS CONTROLS
                // ==========================================

                HBox statusControls =
                        new HBox(10);

                statusControls.getChildren().addAll(
                        statusBox,
                        updateButton
                );


                // ==========================================
                // ADD TO CARD
                // ==========================================

                card.getChildren().addAll(
                        studentLabel,
                        emailLabel,
                        scholarshipLabel,
                        providerLabel,
                        dateLabel,
                        statusTitle,
                        statusLabel,
                        statusControls
                );


                applicationList
                        .getChildren()
                        .add(card);
            }


            // ==========================================
            // EMPTY STATE
            // ==========================================

            if (!found) {

                Label emptyMessage =
                        new Label(
                                "No scholarship applications found."
                        );

                emptyMessage.getStyleClass().add(
                        "dashboard-subtitle"
                );


                applicationList
                        .getChildren()
                        .add(
                                emptyMessage
                        );
            }


        } catch (Exception e) {

            e.printStackTrace();


            Label error =
                    new Label(
                            "Unable to load applications."
                    );


            applicationList
                    .getChildren()
                    .add(error);
        }


        // ==========================================
        // SCROLL PANE
        // ==========================================

        ScrollPane scrollPane =
                new ScrollPane(
                        applicationList
                );

        scrollPane.setFitToWidth(true);

        scrollPane.setStyle(
                "-fx-background-color: transparent;"
        );


        // ==========================================
        // MAIN CONTENT
        // ==========================================

        VBox content =
                new VBox(
                        10,
                        header,
                        scrollPane
                );


        content.setPadding(
                new Insets(
                        30,
                        35,
                        35,
                        35
                )
        );


        content.getStyleClass().add(
                "dashboard-content"
        );


        // ==========================================
        // SCENE
        // ==========================================

        Scene scene =
                new Scene(
                        content,
                        1000,
                        700
                );


        String css =
                AdminView.class
                        .getResource(
                                "/css/style.css"
                        )
                        .toExternalForm();


        scene.getStylesheets().add(css);


        stage.setTitle(
                "ScholarHub - Application Management"
        );


        stage.setScene(scene);

        stage.show();
    }


    // ==================================================
    // UPDATE APPLICATION STATUS
    // ==================================================

    private static void updateApplicationStatus(
            int applicationId,
            String newStatus
    ) {

        String query = """
                UPDATE applications
                SET status = ?
                WHERE application_id = ?
                """;


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setString(
                    1,
                    newStatus
            );


            statement.setInt(
                    2,
                    applicationId
            );


            statement.executeUpdate();


            System.out.println(
                    "Application status updated to: "
                    + newStatus
            );


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}