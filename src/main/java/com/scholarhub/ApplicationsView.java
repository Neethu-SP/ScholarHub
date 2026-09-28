package com.scholarhub;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ApplicationsView {

    public static void show(Stage stage) {

        // ==========================================
        // BACK BUTTON
        // ==========================================

        Button backButton =
                new Button("←");

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
                new Label("My Applications");

        title.getStyleClass().add(
                "dashboard-title"
        );


        Label subtitle =
                new Label(
                        "Track the scholarships you have applied for."
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
                       s.scholarship_id,
                       s.name,
                       s.provider,
                       s.amount,
                       s.deadline,
                       s.category,
                       s.official_link
                FROM applications a
                JOIN scholarships s
                ON a.scholarship_id = s.scholarship_id
                WHERE a.user_id = ?
                ORDER BY a.applied_date DESC
                """;


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            // Test user
            statement.setInt(1, 1);


            ResultSet result =
                    statement.executeQuery();


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


                int scholarshipId =
                        result.getInt(
                                "scholarship_id"
                        );


                String name =
                        result.getString(
                                "name"
                        );


                String provider =
                        result.getString(
                                "provider"
                        );


                String amount =
                        result.getString(
                                "amount"
                        );


                String deadline =
                        result.getString(
                                "deadline"
                        );


                String category =
                        result.getString(
                                "category"
                        );


                String appliedDate =
                        result.getString(
                                "applied_date"
                        );


                String status =
                        result.getString(
                                "status"
                        );


                String officialLink =
                        result.getString(
                                "official_link"
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
                // SCHOLARSHIP NAME
                // ==========================================

                Label nameLabel =
                        new Label(name);

                nameLabel.getStyleClass().add(
                        "scholarship-name"
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
                // AMOUNT
                // ==========================================

                Label amountLabel =
                        new Label(
                                "Amount: " +
                                amount
                        );

                amountLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // APPLIED DATE
                // ==========================================

                Label appliedDateLabel =
                        new Label(
                                "Applied on: " +
                                appliedDate
                        );

                appliedDateLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // STATUS
                // ==========================================

                Label statusLabel =
                        new Label(
                                "● " + status
                        );

                statusLabel.getStyleClass().add(
                        "application-status"
                );


                // ==========================================
                // VIEW DETAILS BUTTON
                // ==========================================

                Button viewButton =
                        new Button(
                                "VIEW DETAILS"
                        );

                viewButton.getStyleClass().add(
                        "login-button"
                );


                viewButton.setOnAction(
                        e -> ScholarshipView.showDetailsFromSaved(
                                stage,
                                scholarshipId,
                                name,
                                provider,
                                amount,
                                deadline,
                                category,
                                officialLink
                        )
                );


                // ==========================================
                // REMOVE BUTTON
                // ==========================================

                Button removeButton =
                        new Button(
                                "REMOVE"
                        );

                removeButton.getStyleClass().add(
                        "remove-button"
                );


                removeButton.setOnAction(
                        e -> {

                            // Show confirmation dialog

                            Alert confirmation =
                                    new Alert(
                                            Alert.AlertType.CONFIRMATION
                                    );

                            confirmation.setTitle(
                                    "Remove Application"
                            );

                            confirmation.setHeaderText(
                                    "Remove this application?"
                            );

                            confirmation.setContentText(
                                    "This will remove the application "
                                    + "from your ScholarHub tracking list."
                            );


                            confirmation.showAndWait()
                                    .ifPresent(
                                            response -> {

                                                if (
                                                        response ==
                                                        javafx.scene.control.ButtonType.OK
                                                ) {

                                                    removeApplication(
                                                            stage,
                                                            applicationId
                                                    );
                                                }
                                            }
                                    );
                        }
                );


                // ==========================================
                // BUTTONS
                // ==========================================

                HBox buttons =
                        new HBox(10);

                buttons.getChildren().addAll(
                        viewButton,
                        removeButton
                );


                // ==========================================
                // CARD CONTENT
                // ==========================================

                card.getChildren().addAll(
                        nameLabel,
                        providerLabel,
                        amountLabel,
                        appliedDateLabel,
                        statusLabel,
                        buttons
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
                                "You haven't applied for any scholarships yet."
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
                ApplicationsView.class
                        .getResource(
                                "/css/style.css"
                        )
                        .toExternalForm();


        scene.getStylesheets().add(css);


        stage.setTitle(
                "ScholarHub - My Applications"
        );


        stage.setScene(scene);

        stage.show();
    }


    // ==================================================
    // REMOVE APPLICATION
    // ==================================================

    private static void removeApplication(
            Stage stage,
            int applicationId
    ) {

        String query = """
                DELETE FROM applications
                WHERE application_id = ?
                AND user_id = ?
                """;


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(
                    1,
                    applicationId
            );


            statement.setInt(
                    2,
                    1
            );


            statement.executeUpdate();


            System.out.println(
                    "Application removed from tracking."
            );


            // Refresh page

            show(stage);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}