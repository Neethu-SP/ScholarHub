package com.scholarhub;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class ScholarshipView {

    // ==================================================
    // FIND SCHOLARSHIPS
    // ==================================================

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
                new Label("Find Scholarships");

        title.getStyleClass().add(
                "dashboard-title"
        );


        Label subtitle =
                new Label(
                        "Explore scholarships available for students like you."
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


        String query = """
                SELECT scholarship_id,
                       name,
                       provider,
                       amount,
                       deadline,
                       category,
                       official_link
                FROM scholarships
                ORDER BY scholarship_id
                """;


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query);

                ResultSet result =
                        statement.executeQuery()
        ) {

            while (result.next()) {

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
                // NAME
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
                // ELIGIBILITY
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
                // VIEW DETAILS
                // ==========================================

                Button viewButton =
                        new Button(
                                "VIEW DETAILS"
                        );

                viewButton.getStyleClass().add(
                        "login-button"
                );


                viewButton.setOnAction(
                        e -> showDetailsFromSaved(
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
                // SAVE
                // ==========================================

                Button saveButton =
                        new Button("SAVE");

                saveButton.getStyleClass().add(
                        "save-button"
                );


                saveButton.setOnAction(
                        e -> saveScholarship(
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
                        saveButton
                );


                // ==========================================
                // CARD CONTENT
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


        } catch (Exception e) {

            e.printStackTrace();


            scholarshipList
                    .getChildren()
                    .add(
                            new Label(
                                    "Unable to load scholarships."
                            )
                    );
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
        // CONTENT
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
                ScholarshipView.class
                        .getResource(
                                "/css/style.css"
                        )
                        .toExternalForm();


        scene.getStylesheets().add(css);


        stage.setTitle(
                "ScholarHub - Find Scholarships"
        );

        stage.setScene(scene);

        stage.show();
    }


    // ==================================================
    // SAVE SCHOLARSHIP
    // ==================================================

    private static void saveScholarship(
            int scholarshipId
    ) {

        String query = """
                INSERT INTO saved_scholarships
                (user_id, scholarship_id)
                VALUES (?, ?)
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
                    "Scholarship saved successfully!"
            );


        } catch (
                java.sql.SQLIntegrityConstraintViolationException e
        ) {

            System.out.println(
                    "Scholarship already saved!"
            );


        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==================================================
    // SCHOLARSHIP DETAILS
    // ==================================================

    public static void showDetailsFromSaved(
            Stage stage,
            int scholarshipId,
            String name,
            String provider,
            String amount,
            String deadline,
            String category,
            String officialLink
    ) {

        // ==========================================
        // BACK BUTTON
        // ==========================================

        Button backButton =
                new Button("←");

        backButton.getStyleClass().add(
                "back-button"
        );

        backButton.setOnAction(
                e -> ScholarshipView.show(stage)
        );


        // ==========================================
        // TITLE
        // ==========================================

        Label title =
                new Label(name);

        title.getStyleClass().add(
                "dashboard-title"
        );


        // ==========================================
        // BASIC INFORMATION
        // ==========================================

        Label providerLabel =
                new Label(
                        "Provider: " + provider
                );


        Label amountLabel =
                new Label(
                        "Amount: " + amount
                );


        Label deadlineLabel =
                new Label(
                        "Deadline: " +
                        (
                            deadline == null
                            ? "Check official website"
                            : deadline
                        )
                );


        Label categoryLabel =
                new Label(
                        "Category: " + category
                );


        // ==========================================
        // POTENTIAL ELIGIBILITY
        // ==========================================

        Label eligibilityStatusTitle =
                new Label(
                        "Your Eligibility"
                );

        eligibilityStatusTitle.getStyleClass().add(
                "section-title"
        );


        String eligibilityStatus =
                EligibilityService.checkEligibility(
                        scholarshipId,
                        1
                );


        Label eligibilityStatusLabel =
                new Label(
                        eligibilityStatus
                );

        eligibilityStatusLabel.getStyleClass().add(
                "eligibility-status"
        );


        // ==========================================
        // ELIGIBILITY DETAILS
        // ==========================================

        Label eligibilityTitle =
                new Label(
                        "Eligibility Criteria"
                );

        eligibilityTitle.getStyleClass().add(
                "section-title"
        );


        Label eligibility =
                new Label();


        // ==========================================
        // REQUIRED DOCUMENTS
        // ==========================================

        Label documentsTitle =
                new Label(
                        "Required Documents"
                );

        documentsTitle.getStyleClass().add(
                "section-title"
        );


        Label documents =
                new Label();


        // ==========================================
        // LOAD DETAILS FROM DATABASE
        // ==========================================

        String query = """
                SELECT eligibility,
                       required_documents
                FROM scholarships
                WHERE scholarship_id = ?
                """;


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(
                    1,
                    scholarshipId
            );


            ResultSet result =
                    statement.executeQuery();


            if (result.next()) {

                eligibility.setText(
                        result.getString(
                                "eligibility"
                        )
                );


                documents.setText(
                        result.getString(
                                "required_documents"
                        )
                );
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        eligibility.setWrapText(true);

        documents.setWrapText(true);


        // ==========================================
        // APPLY NOW
        // ==========================================

        Button applyButton =
                new Button(
                        "APPLY NOW"
                );

        applyButton.getStyleClass().add(
                "login-button"
        );


        applyButton.setOnAction(
                e -> applyForScholarship(
                        stage,
                        scholarshipId,
                        officialLink
                )
        );


        // ==========================================
        // BUTTONS
        // ==========================================

        HBox buttons =
                new HBox(10);

        buttons.getChildren().addAll(
                applyButton,
                backButton
        );


        // ==========================================
        // CONTENT
        // ==========================================

        VBox content =
                new VBox(
                        15,
                        title,
                        providerLabel,
                        amountLabel,
                        deadlineLabel,
                        categoryLabel,

                        eligibilityStatusTitle,
                        eligibilityStatusLabel,

                        eligibilityTitle,
                        eligibility,

                        documentsTitle,
                        documents,

                        buttons
                );


        content.setPadding(
                new Insets(35)
        );


        content.setStyle(
                "-fx-background-color: #F7F9FC;"
        );


        // ==========================================
        // SCROLL PANE
        // ==========================================

        ScrollPane scrollPane =
                new ScrollPane(
                        content
                );

        scrollPane.setFitToWidth(true);

        scrollPane.setStyle(
                "-fx-background-color: transparent;"
        );


        // ==========================================
        // SCENE
        // ==========================================

        Scene scene =
                new Scene(
                        scrollPane,
                        1000,
                        700
                );


        String css =
                ScholarshipView.class
                        .getResource(
                                "/css/style.css"
                        )
                        .toExternalForm();


        scene.getStylesheets().add(css);


        stage.setTitle(
                "ScholarHub - Scholarship Details"
        );

        stage.setScene(scene);

        stage.show();
    }


    // ==================================================
    // APPLY FOR SCHOLARSHIP
    // ==================================================

    private static void applyForScholarship(
            Stage stage,
            int scholarshipId,
            String officialLink
    ) {

        String query = """
                INSERT INTO applications
                (user_id, scholarship_id, applied_date, status)
                VALUES (?, ?, ?, ?)
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


            statement.setDate(
                    3,
                    java.sql.Date.valueOf(
                            LocalDate.now()
                    )
            );


            statement.setString(
                    4,
                    "APPLIED"
            );


            statement.executeUpdate();


            System.out.println(
                    "Application added successfully!"
            );


            // ==========================================
            // OPEN OFFICIAL WEBSITE
            // ==========================================

            if (
                    officialLink != null &&
                    !officialLink.isBlank()
            ) {

                try {

                    if (
                            Desktop.isDesktopSupported()
                    ) {

                        Desktop.getDesktop().browse(
                                new URI(
                                        officialLink
                                )
                        );
                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }


            // ==========================================
            // OPEN APPLICATIONS
            // ==========================================

            ApplicationsView.show(stage);


        } catch (
                java.sql.SQLIntegrityConstraintViolationException e
        ) {

            System.out.println(
                    "You have already applied for this scholarship."
            );


            ApplicationsView.show(stage);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}