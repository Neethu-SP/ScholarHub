package com.scholarhub;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        // ==========================================
        // DATABASE CONNECTION TEST
        // ==========================================

        try {
            DatabaseConnection.getConnection().close();

            System.out.println(
                    "Database connected successfully!"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }


        // ==========================================
        // SCHOLARHUB LOGO
        // ==========================================

        Label logoIcon =
                new Label("S");

        logoIcon.setPrefSize(72, 72);

        logoIcon.setAlignment(
                Pos.CENTER
        );

        logoIcon.setStyle(
                "-fx-background-color: #4169E1;" +
                "-fx-background-radius: 36px;" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-font-size: 36px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: #8FB8FF;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 36px;"
        );


        // ==========================================
        // SCHOLARHUB NAME
        // ==========================================

        Label logo =
                new Label("ScholarHub");

        logo.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-font-size: 36px;" +
                "-fx-font-weight: bold;" +
                "-fx-letter-spacing: 1px;"
        );


        // ==========================================
        // SMALL LABEL
        // ==========================================

        Label portalLabel =
                new Label(
                        "SCHOLARSHIP PORTAL"
                );

        portalLabel.setStyle(
                "-fx-text-fill: #8FB8FF;" +
                "-fx-font-family: 'Arial';" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-letter-spacing: 2px;"
        );


        // ==========================================
        // TAGLINE
        // ==========================================

        Label tagline =
                new Label(
                        "Discover. Track. Achieve."
                );

        tagline.setStyle(
                "-fx-text-fill: #D8E5FF;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-font-size: 16px;"
        );


        // ==========================================
        // BRANDING CONTENT
        // ==========================================

        VBox branding =
                new VBox(
                        14,
                        logoIcon,
                        logo,
                        portalLabel,
                        tagline
                );

        branding.setAlignment(
                Pos.CENTER
        );


        // ==========================================
        // LEFT BLUE PANEL
        // ==========================================

        VBox leftPane =
                new VBox(
                        branding
                );

        leftPane.setAlignment(
                Pos.CENTER
        );

        leftPane.setPrefWidth(
                430
        );

        leftPane.setStyle(
                "-fx-background-color: #14213D;"
        );


        // ==========================================
        // WELCOME
        // ==========================================

        Label welcome =
                new Label(
                        "Welcome Back"
                );

        welcome.setStyle(
                "-fx-text-fill: #14213D;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;"
        );


        // ==========================================
        // SUBTITLE
        // ==========================================

        Label subtitle =
                new Label(
                        "Sign in to continue to ScholarHub"
                );

        subtitle.setStyle(
                "-fx-text-fill: #7A8499;" +
                "-fx-font-family: 'Arial';" +
                "-fx-font-size: 14px;"
        );


        // ==========================================
        // EMAIL
        // ==========================================

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Email"
        );

        emailField.setPrefHeight(
                45
        );

        emailField.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #D5DBE5;" +
                "-fx-border-radius: 7px;" +
                "-fx-background-radius: 7px;" +
                "-fx-padding: 0 14px;" +
                "-fx-font-size: 14px;"
        );


        // ==========================================
        // PASSWORD
        // ==========================================

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Password"
        );

        passwordField.setPrefHeight(
                45
        );

        passwordField.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #D5DBE5;" +
                "-fx-border-radius: 7px;" +
                "-fx-background-radius: 7px;" +
                "-fx-padding: 0 14px;" +
                "-fx-font-size: 14px;"
        );


        // ==========================================
        // LOGIN BUTTON
        // ==========================================

        Button loginButton =
                new Button("LOGIN");

        loginButton.setPrefHeight(
                45
        );

        loginButton.setPrefWidth(
                120
        );

        loginButton.setStyle(
                "-fx-background-color: #4169E1;" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Arial';" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
        );


        // ==========================================
        // LOGIN MESSAGE
        // ==========================================

        Label message =
                new Label();

        message.setStyle(
                "-fx-text-fill: #C62828;" +
                "-fx-font-size: 13px;"
        );


        // ==========================================
        // LOGIN ACTION
        // ==========================================

        loginButton.setOnAction(e -> {

            String email =
                    emailField.getText().trim();

            String password =
                    passwordField.getText();


            // ==========================================
            // VALIDATION
            // ==========================================

            if (
                    email.isEmpty() ||
                    password.isEmpty()
            ) {

                message.setText(
                        "Please enter email and password."
                );

                return;
            }


            // ==========================================
            // LOGIN QUERY
            // ==========================================

            String query = """
                    SELECT user_id,
                           name,
                           role
                    FROM users
                    WHERE email = ?
                    AND password = ?
                    """;


            try (
                    java.sql.Connection connection =
                            DatabaseConnection.getConnection();

                    java.sql.PreparedStatement statement =
                            connection.prepareStatement(query)
            ) {

                statement.setString(
                        1,
                        email
                );

                statement.setString(
                        2,
                        password
                );


                java.sql.ResultSet result =
                        statement.executeQuery();


                // ==========================================
                // LOGIN SUCCESS
                // ==========================================

                if (result.next()) {

                    int userId =
                            result.getInt(
                                    "user_id"
                            );

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


                    // ==========================================
                    // ADMIN
                    // ==========================================

                    if (
                            role.equalsIgnoreCase(
                                    "ADMIN"
                            )
                    ) {

                        AdminView.show(stage);

                    }


                    // ==========================================
                    // STUDENT
                    // ==========================================

                    else {

                        Dashboard.show(stage);
                    }


                } else {

                    message.setText(
                            "Invalid email or password."
                    );
                }


            } catch (Exception ex) {

                ex.printStackTrace();

                message.setText(
                        "Unable to connect to database."
                );
            }
        });


        // ==========================================
        // LOGIN FORM
        // ==========================================

        VBox loginForm =
                new VBox(
                        16,
                        welcome,
                        subtitle,
                        emailField,
                        passwordField,
                        loginButton,
                        message
                );

        loginForm.setAlignment(
                Pos.CENTER_LEFT
        );

        loginForm.setMaxWidth(
                390
        );


        // ==========================================
        // RIGHT PANEL
        // ==========================================

        VBox rightPane =
                new VBox(
                        loginForm
                );

        rightPane.setAlignment(
                Pos.CENTER
        );

        rightPane.setPrefWidth(
                570
        );

        rightPane.setStyle(
                "-fx-background-color: #F7F9FC;"
        );


        // ==========================================
        // MAIN LAYOUT
        // ==========================================

        HBox root =
                new HBox(
                        leftPane,
                        rightPane
                );

        root.setStyle(
                "-fx-background-color: #F7F9FC;"
        );


        // ==========================================
        // SCENE
        // ==========================================

        Scene scene =
                new Scene(
                        root,
                        1000,
                        650
                );


        stage.setTitle(
                "ScholarHub"
        );

        stage.setScene(
                scene
        );

        stage.show();
    }


    public static void main(
            String[] args
    ) {

        launch(args);
    }
}