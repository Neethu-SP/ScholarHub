package com.scholarhub;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dashboard {

    private static final int CURRENT_USER_ID = 1;

    public static void show(Stage stage) {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("dashboard-root");

        // =========================================================
        // SIDEBAR
        // =========================================================

        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");

        sidebar.setPadding(new Insets(30, 18, 25, 18));
        sidebar.setPrefWidth(240);
        sidebar.setMinWidth(220);

        Label logo = new Label("ScholarHub");
        logo.getStyleClass().add("dashboard-logo");

        Label tagline = new Label("SCHOLARSHIP PORTAL");
        tagline.getStyleClass().add("dashboard-tagline");

        Button dashboardBtn =
                createSidebarButton("▣   Dashboard");

        Button scholarshipsBtn =
                createSidebarButton("⌕   Find Scholarships");

        Button savedBtn =
                createSidebarButton("♡   Saved Scholarships");

        Button applicationsBtn =
                createSidebarButton("✓   My Applications");

        Button profileBtn =
                createSidebarButton("●   My Profile");

        VBox navigation = new VBox(7);

        navigation.getChildren().addAll(
                dashboardBtn,
                scholarshipsBtn,
                savedBtn,
                applicationsBtn,
                profileBtn
        );

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logoutBtn =
                createSidebarButton("↪   Logout");

        sidebar.getChildren().addAll(
                logo,
                tagline,
                navigation,
                spacer,
                logoutBtn
        );

        root.setLeft(sidebar);

        // =========================================================
        // MAIN CONTENT
        // =========================================================

        VBox content = new VBox(25);

        content.getStyleClass().add("dashboard-content");

        content.setPadding(
                new Insets(35, 45, 40, 45)
        );

        content.setFillWidth(true);

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        Label welcome =
                new Label("Welcome back, Neethu");

        welcome.getStyleClass().add(
                "dashboard-title"
        );

        Label subtitle =
                new Label(
                        "Discover scholarships, track your applications, "
                                + "and stay on top of opportunities."
                );

        subtitle.getStyleClass().add(
                "dashboard-subtitle"
        );

        subtitle.setWrapText(true);

        VBox header = new VBox(5);

        header.getChildren().addAll(
                welcome,
                subtitle
        );

        // ---------------------------------------------------------
        // STATISTICS
        // ---------------------------------------------------------

        int scholarshipCount =
                getAvailableScholarshipCount();

        int savedCount =
                getSavedScholarshipCount(
                        CURRENT_USER_ID
                );

        int applicationCount =
                getApplicationCount(
                        CURRENT_USER_ID
                );

        HBox statistics = new HBox(18);

        statistics.setFillHeight(true);
        statistics.setMaxWidth(Double.MAX_VALUE);

        VBox availableCard =
                createStatCard(
                        "AVAILABLE SCHOLARSHIPS",
                        String.valueOf(scholarshipCount),
                        "Opportunities available"
                );

        VBox savedCard =
                createStatCard(
                        "SAVED SCHOLARSHIPS",
                        String.valueOf(savedCount),
                        "Opportunities you saved"
                );

        VBox applicationCard =
                createStatCard(
                        "MY APPLICATIONS",
                        String.valueOf(applicationCount),
                        "Applications being tracked"
                );

        HBox.setHgrow(
                availableCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                savedCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                applicationCard,
                Priority.ALWAYS
        );

        availableCard.setMinWidth(0);
        savedCard.setMinWidth(0);
        applicationCard.setMinWidth(0);

        statistics.getChildren().addAll(
                availableCard,
                savedCard,
                applicationCard
        );

        // ---------------------------------------------------------
        // QUICK ACTIONS
        // ---------------------------------------------------------

        Label quickTitle =
                new Label("Quick Actions");

        quickTitle.getStyleClass().add(
                "section-title"
        );

        HBox quickActions =
                new HBox(15);

        quickActions.setMaxWidth(
                Double.MAX_VALUE
        );

        Button exploreButton =
                new Button("Find Scholarships");

        exploreButton.getStyleClass().add(
                "primary-action"
        );

        exploreButton.setPrefHeight(45);
        exploreButton.setMinWidth(160);

        Button savedAction =
                new Button("View Saved");

        savedAction.getStyleClass().add(
                "secondary-action"
        );

        savedAction.setPrefHeight(45);
        savedAction.setMinWidth(140);

        Button applicationAction =
                new Button("My Applications");

        applicationAction.getStyleClass().add(
                "secondary-action"
        );

        applicationAction.setPrefHeight(45);
        applicationAction.setMinWidth(160);

        quickActions.getChildren().addAll(
                exploreButton,
                savedAction,
                applicationAction
        );

        // ---------------------------------------------------------
        // INFORMATION CARD
        // ---------------------------------------------------------

        VBox informationCard =
                new VBox(10);

        informationCard.getStyleClass().add(
                "dashboard-info-card"
        );

        informationCard.setPadding(
                new Insets(22)
        );

        informationCard.setMaxWidth(
                Double.MAX_VALUE
        );

        Label infoTitle =
                new Label(
                        "Stay ahead of scholarship opportunities"
                );

        infoTitle.getStyleClass().add(
                "info-title"
        );

        Label infoText =
                new Label(
                        "Browse available scholarships based on your "
                                + "profile, save opportunities for later, "
                                + "and track the scholarships you have applied for."
                );

        infoText.getStyleClass().add(
                "info-text"
        );

        infoText.setWrapText(true);

        informationCard.getChildren().addAll(
                infoTitle,
                infoText
        );

        // ---------------------------------------------------------
        // SCHOLARSHIP JOURNEY
        // ---------------------------------------------------------

        Label activityTitle =
                new Label("Your Scholarship Journey");

        activityTitle.getStyleClass().add(
                "section-title"
        );

        VBox activityCard =
                new VBox(12);

        activityCard.getStyleClass().add(
                "dashboard-info-card"
        );

        activityCard.setPadding(
                new Insets(20)
        );

        activityCard.setMaxWidth(
                Double.MAX_VALUE
        );

        Label step1 =
                new Label(
                        "01    Explore scholarships"
                );

        Label step2 =
                new Label(
                        "02    Check your potential eligibility"
                );

        Label step3 =
                new Label(
                        "03    Save opportunities you are interested in"
                );

        Label step4 =
                new Label(
                        "04    Apply through the official scholarship website"
                );

        Label step5 =
                new Label(
                        "05    Track your application status"
                );

        Label[] steps = {
                step1,
                step2,
                step3,
                step4,
                step5
        };

        for (Label step : steps) {

            step.getStyleClass().add(
                    "journey-step"
            );

            step.setWrapText(true);

            step.setMaxWidth(
                    Double.MAX_VALUE
            );
        }

        activityCard.getChildren().addAll(
                step1,
                step2,
                step3,
                step4,
                step5
        );

        // ---------------------------------------------------------
        // ADD EVERYTHING
        // ---------------------------------------------------------

        content.getChildren().addAll(
                header,
                statistics,
                quickTitle,
                quickActions,
                informationCard,
                activityTitle,
                activityCard
        );

        // =========================================================
        // SCROLL PANE
        // =========================================================

        ScrollPane scrollPane =
                new ScrollPane(content);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setPannable(true);

        scrollPane.getStyleClass().add(
                "dashboard-scroll"
        );

        root.setCenter(scrollPane);

        // =========================================================
        // NAVIGATION
        // =========================================================

        dashboardBtn.setOnAction(
                e -> Dashboard.show(stage)
        );

        scholarshipsBtn.setOnAction(
                e -> ScholarshipView.show(stage)
        );

        savedBtn.setOnAction(
                e -> SavedScholarships.show(stage)
        );

        applicationsBtn.setOnAction(
                e -> ApplicationsView.show(stage)
        );

        profileBtn.setOnAction(
                e -> ProfileView.show(stage)
        );

        exploreButton.setOnAction(
                e -> ScholarshipView.show(stage)
        );

        savedAction.setOnAction(
                e -> SavedScholarships.show(stage)
        );

        applicationAction.setOnAction(
                e -> ApplicationsView.show(stage)
        );

        logoutBtn.setOnAction(
                e -> showLoginPage(stage)
        );

        // =========================================================
        // SCENE
        // =========================================================

        Scene scene =
                new Scene(
                        root,
                        1200,
                        750
                );

        scene.getStylesheets().add(
                Dashboard.class
                        .getResource(
                                "/css/style.css"
                        )
                        .toExternalForm()
        );

        stage.setTitle(
                "ScholarHub - Dashboard"
        );

        stage.setScene(scene);

        stage.setMinWidth(950);
        stage.setMinHeight(650);

        stage.show();
    }

    // =============================================================
    // SIDEBAR BUTTON
    // =============================================================

    private static Button createSidebarButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.getStyleClass().add(
                "sidebar-button"
        );

        button.setMaxWidth(
                Double.MAX_VALUE
        );

        button.setPrefHeight(45);

        return button;
    }

    // =============================================================
    // STAT CARD
    // =============================================================

    private static VBox createStatCard(
            String title,
            String number,
            String description
    ) {

        VBox card =
                new VBox(7);

        card.getStyleClass().add(
                "stat-card"
        );

        card.setPadding(
                new Insets(20)
        );

        card.setMinWidth(0);

        Label titleLabel =
                new Label(title);

        titleLabel.getStyleClass().add(
                "card-title"
        );

        titleLabel.setWrapText(true);

        Label numberLabel =
                new Label(number);

        numberLabel.getStyleClass().add(
                "card-number"
        );

        Label descriptionLabel =
                new Label(description);

        descriptionLabel.getStyleClass().add(
                "card-description"
        );

        descriptionLabel.setWrapText(true);

        card.getChildren().addAll(
                titleLabel,
                numberLabel,
                descriptionLabel
        );

        return card;
    }

    // =============================================================
    // AVAILABLE SCHOLARSHIPS
    // =============================================================

    private static int getAvailableScholarshipCount() {

        String sql =
                "SELECT COUNT(*) FROM scholarships";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            if (result.next()) {
                return result.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =============================================================
    // SAVED COUNT
    // =============================================================

    private static int getSavedScholarshipCount(
            int userId
    ) {

        String sql =
                "SELECT COUNT(*) "
                        + "FROM saved_scholarships "
                        + "WHERE user_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {
                    return result.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =============================================================
    // APPLICATION COUNT
    // =============================================================

    private static int getApplicationCount(
            int userId
    ) {

        String sql =
                "SELECT COUNT(*) "
                        + "FROM applications "
                        + "WHERE user_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {
                    return result.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =============================================================
    // LOGIN PAGE
    // =============================================================

    private static void showLoginPage(
            Stage stage
    ) {

        BorderPane root =
                new BorderPane();

        // ---------------------------------------------------------
        // BRANDING
        // ---------------------------------------------------------

        VBox branding =
                new VBox(15);

        branding.setPadding(
                new Insets(55)
        );

        branding.setPrefWidth(500);

        branding.setStyle(
                "-fx-background-color: #14213D;"
        );

        Label logoCircle =
                new Label("S");

        logoCircle.setMinSize(65, 65);
        logoCircle.setMaxSize(65, 65);

        logoCircle.setAlignment(
                Pos.CENTER
        );

        logoCircle.setStyle(
                "-fx-background-color: #4169E1;"
                        + "-fx-background-radius: 50%;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-size: 32px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-font-family: Georgia;"
        );

        Label brand =
                new Label("ScholarHub");

        brand.setStyle(
                "-fx-text-fill: white;"
                        + "-fx-font-size: 38px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-font-family: Georgia;"
        );

        Label portal =
                new Label(
                        "SCHOLARSHIP PORTAL"
                );

        portal.setStyle(
                "-fx-text-fill: #8FB8FF;"
                        + "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
        );

        Label description =
                new Label(
                        "Discover opportunities.\n"
                                + "Track your applications.\n"
                                + "Build your future."
                );

        description.setStyle(
                "-fx-text-fill: #D8E1F0;"
                        + "-fx-font-size: 17px;"
                        + "-fx-line-spacing: 7px;"
        );

        branding.getChildren().addAll(
                logoCircle,
                brand,
                portal,
                description
        );

        root.setLeft(branding);

        // ---------------------------------------------------------
        // LOGIN
        // ---------------------------------------------------------

        VBox loginContainer =
                new VBox(18);

        loginContainer.setAlignment(
                Pos.CENTER_LEFT
        );

        loginContainer.setPadding(
                new Insets(
                        70,
                        90,
                        70,
                        90
                )
        );

        loginContainer.setStyle(
                "-fx-background-color: #F7F9FC;"
        );

        Label loginTitle =
                new Label(
                        "Welcome back"
                );

        loginTitle.setStyle(
                "-fx-text-fill: #14213D;"
                        + "-fx-font-size: 30px;"
                        + "-fx-font-weight: bold;"
        );

        Label loginSubtitle =
                new Label(
                        "Sign in to continue to ScholarHub"
                );

        loginSubtitle.setStyle(
                "-fx-text-fill: #7A8499;"
                        + "-fx-font-size: 14px;"
        );

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Email address"
        );

        emailField.setPrefHeight(45);

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Password"
        );

        passwordField.setPrefHeight(45);

        Button loginButton =
                new Button("Sign In");

        loginButton.setPrefHeight(45);

        loginButton.setMaxWidth(
                Double.MAX_VALUE
        );

        loginButton.setStyle(
                "-fx-background-color: #4169E1;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-size: 15px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 7px;"
                        + "-fx-cursor: hand;"
        );

        Label footer =
                new Label(
                        "ScholarHub • Scholarship Discovery & Tracking"
                );

        footer.setStyle(
                "-fx-text-fill: #9AA4B5;"
                        + "-fx-font-size: 12px;"
        );

        loginContainer.getChildren().addAll(
                loginTitle,
                loginSubtitle,
                emailField,
                passwordField,
                loginButton,
                footer
        );

        root.setCenter(
                loginContainer
        );

        // ---------------------------------------------------------
        // LOGIN ACTION
        // ---------------------------------------------------------

        loginButton.setOnAction(e -> {

            String email =
                    emailField.getText().trim();

            String password =
                    passwordField.getText();

            if (
                    email.isEmpty()
                            || password.isEmpty()
            ) {

                showAlert(
                        Alert.AlertType.WARNING,
                        "Missing Information",
                        "Please enter both email and password."
                );

                return;
            }

            String sql =
                    "SELECT user_id, name, role "
                            + "FROM users "
                            + "WHERE email = ? "
                            + "AND password = ?";

            try (
                    Connection connection =
                            DatabaseConnection.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(sql)
            ) {

                statement.setString(
                        1,
                        email
                );

                statement.setString(
                        2,
                        password
                );

                try (
                        ResultSet result =
                                statement.executeQuery()
                ) {

                    if (result.next()) {

                        String name =
                                result.getString(
                                        "name"
                                );

                        String role =
                                result.getString(
                                        "role"
                                );

                        System.out.println(
                                "Login successful!"
                        );

                        System.out.println(
                                "User: " + name
                        );

                        System.out.println(
                                "Role: " + role
                        );

                        if (
                                "ADMIN".equalsIgnoreCase(
                                        role
                                )
                        ) {

                            AdminView.show(stage);

                        } else {

                            Dashboard.show(stage);
                        }

                    } else {

                        showAlert(
                                Alert.AlertType.ERROR,
                                "Login Failed",
                                "Invalid email or password."
                        );
                    }
                }

            } catch (Exception ex) {

                ex.printStackTrace();

                showAlert(
                        Alert.AlertType.ERROR,
                        "Database Error",
                        "Unable to connect to the database."
                );
            }
        });

        Scene scene =
                new Scene(
                        root,
                        1100,
                        700
                );

        stage.setTitle(
                "ScholarHub - Login"
        );

        stage.setScene(scene);

        stage.show();
    }

    // =============================================================
    // ALERT
    // =============================================================

    private static void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {

        Alert alert =
                new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}