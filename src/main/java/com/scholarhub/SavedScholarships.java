package com.scholarhub;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SavedScholarships {

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
                new Label("Saved Scholarships");

        title.getStyleClass().add(
                "dashboard-title"
        );


        Label subtitle =
                new Label(
                        "Scholarships you saved for later."
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
        // SCHOLARSHIP LIST
        // ==========================================

        VBox scholarshipList =
                new VBox(18);

        scholarshipList.setPadding(
                new Insets(10, 0, 20, 0)
        );


        // ==========================================
        // DATABASE QUERY
        // ==========================================

        String query = """
                SELECT s.scholarship_id,
                       s.name,
                       s.provider,
                       s.amount,
                       s.deadline,
                       s.category,
                       s.official_link
                FROM saved_scholarships ss
                JOIN scholarships s
                ON ss.scholarship_id = s.scholarship_id
                WHERE ss.user_id = ?
                ORDER BY ss.saved_date DESC
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
            // LOAD SAVED SCHOLARSHIPS
            // ==========================================

            while (result.next()) {

                found = true;


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


                String officialLink =
                        result.getString(
                                "official_link"
                        );


                // ==========================================
                // CARD
                // ==========================================

                VBox card =
                        new VBox(8);

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
                                "Provider: " + provider
                        );

                providerLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // AMOUNT
                // ==========================================

                Label amountLabel =
                        new Label(
                                "Amount: " + amount
                        );

                amountLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // DEADLINE
                // ==========================================

                Label deadlineLabel =
                        new Label(
                                "Deadline: " +
                                (
                                    deadline == null
                                    ? "Check official website"
                                    : deadline
                                )
                        );

                deadlineLabel.getStyleClass().add(
                        "scholarship-info"
                );


                // ==========================================
                // CATEGORY
                // ==========================================

                Label categoryLabel =
                        new Label(
                                "Category: " + category
                        );

                categoryLabel.getStyleClass().add(
                        "scholarship-category"
                );


                // ==========================================
                // ELIGIBILITY STATUS
                // ==========================================

                String eligibilityStatus =
                        EligibilityService.checkEligibility(
                                scholarshipId,
                                1
                        );


                Label eligibilityLabel =
                        new Label(
                                eligibilityStatus
                        );

                eligibilityLabel.getStyleClass().add(
                        "eligibility-status"
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
                        e -> removeSavedScholarship(
                                stage,
                                scholarshipId
                        )
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
                // ADD EVERYTHING TO CARD
                // ==========================================

                card.getChildren().addAll(
                        nameLabel,
                        providerLabel,
                        amountLabel,
                        deadlineLabel,
                        categoryLabel,
                        eligibilityLabel,
                        buttons
                );


                scholarshipList
                        .getChildren()
                        .add(card);
            }


            // ==========================================
            // EMPTY STATE
            // ==========================================

            if (!found) {

                Label emptyMessage =
                        new Label(
                                "You haven't saved any scholarships yet."
                        );

                emptyMessage.getStyleClass().add(
                        "dashboard-subtitle"
                );


                scholarshipList
                        .getChildren()
                        .add(
                                emptyMessage
                        );
            }


        } catch (Exception e) {

            e.printStackTrace();


            Label error =
                    new Label(
                            "Unable to load saved scholarships."
                    );


            scholarshipList
                    .getChildren()
                    .add(error);
        }


        // ==========================================
        // SCROLL PANE
        // ==========================================

        ScrollPane scrollPane =
                new ScrollPane(
                        scholarshipList
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
                SavedScholarships.class
                        .getResource(
                                "/css/style.css"
                        )
                        .toExternalForm();


        scene.getStylesheets().add(css);


        stage.setTitle(
                "ScholarHub - Saved Scholarships"
        );


        stage.setScene(scene);


        stage.show();
    }


    // ==================================================
    // REMOVE SAVED SCHOLARSHIP
    // ==================================================

    private static void removeSavedScholarship(
            Stage stage,
            int scholarshipId
    ) {

        String query = """
                DELETE FROM saved_scholarships
                WHERE user_id = ?
                AND scholarship_id = ?
                """;


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(
                    1,
                    1
            );


            statement.setInt(
                    2,
                    scholarshipId
            );


            statement.executeUpdate();


            System.out.println(
                    "Scholarship removed!"
            );


            // Refresh saved scholarships page

            show(stage);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}