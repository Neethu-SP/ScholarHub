package com.scholarhub;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileView {

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
                new Label("My Profile");

        title.getStyleClass().add(
                "dashboard-title"
        );


        Label subtitle =
                new Label(
                        "Manage your personal and academic information."
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
        // DEFAULT VALUES
        // ==========================================

        String loadedName = "User";
        String loadedEmail = "";
        String loadedRole = "STUDENT";

        String loadedCourse = "Not provided";
        int loadedYear = 0;
        String loadedCollege = "Not provided";
        String loadedCategory = "Not provided";
        double loadedIncome = 0;


        // ==========================================
        // DATABASE QUERY
        // ==========================================

        String query = """
                SELECT name,
                       email,
                       role,
                       course,
                       year_of_study,
                       college,
                       category,
                       annual_income
                FROM users
                WHERE user_id = ?
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


            if (result.next()) {

                loadedName =
                        result.getString("name");

                loadedEmail =
                        result.getString("email");

                loadedRole =
                        result.getString("role");


                String course =
                        result.getString("course");

                if (course != null) {
                    loadedCourse = course;
                }


                loadedYear =
                        result.getInt("year_of_study");


                String college =
                        result.getString("college");

                if (college != null) {
                    loadedCollege = college;
                }


                String category =
                        result.getString("category");

                if (category != null) {
                    loadedCategory = category;
                }


                loadedIncome =
                        result.getDouble(
                                "annual_income"
                        );
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        // ==========================================
        // FINAL VALUES
        // ==========================================

        final String name = loadedName;
        final String email = loadedEmail;
        final String role = loadedRole;
        final String course = loadedCourse;
        final int year = loadedYear;
        final String college = loadedCollege;
        final String category = loadedCategory;
        final double income = loadedIncome;


        // ==========================================
        // PROFILE CARD
        // ==========================================

        VBox profileCard =
                new VBox(20);

        profileCard.getStyleClass().add(
                "stat-card"
        );

        profileCard.setPadding(
                new Insets(30)
        );


        // ==========================================
        // NAME
        // ==========================================

        Label nameTitle =
                createTitle("Full Name");

        Label nameValue =
                createValue(name);


        // ==========================================
        // EMAIL
        // ==========================================

        Label emailTitle =
                createTitle("Email");

        Label emailValue =
                createValue(email);


        // ==========================================
        // ROLE
        // ==========================================

        Label roleTitle =
                createTitle("Account Type");

        Label roleValue =
                createValue(role);


        // ==========================================
        // COURSE
        // ==========================================

        Label courseTitle =
                createTitle("Course");

        Label courseValue =
                createValue(course);


        // ==========================================
        // COLLEGE
        // ==========================================

        Label collegeTitle =
                createTitle("College");

        Label collegeValue =
                createValue(college);


        // ==========================================
        // YEAR
        // ==========================================

        Label yearTitle =
                createTitle("Year of Study");

        Label yearValue =
                createValue(
                        year == 0
                        ? "Not provided"
                        : String.valueOf(year)
                );


        // ==========================================
        // CATEGORY
        // ==========================================

        Label categoryTitle =
                createTitle("Student Category");

        Label categoryValue =
                createValue(category);


        // ==========================================
        // INCOME
        // ==========================================

        Label incomeTitle =
                createTitle(
                        "Annual Family Income"
                );

        String incomeText;

        if (income == 0) {
            incomeText = "Not provided";
        } else {
            incomeText =
                    String.format(
                            "₹%,.0f",
                            income
                    );
        }

        Label incomeValue =
                createValue(incomeText);


        // ==========================================
        // GRID
        // ==========================================

        GridPane grid =
                new GridPane();

        grid.setHgap(50);
        grid.setVgap(20);


        grid.add(
                nameTitle,
                0,
                0
        );

        grid.add(
                nameValue,
                0,
                1
        );


        grid.add(
                emailTitle,
                1,
                0
        );

        grid.add(
                emailValue,
                1,
                1
        );


        grid.add(
                roleTitle,
                2,
                0
        );

        grid.add(
                roleValue,
                2,
                1
        );


        grid.add(
                courseTitle,
                0,
                2
        );

        grid.add(
                courseValue,
                0,
                3
        );


        grid.add(
                collegeTitle,
                1,
                2
        );

        grid.add(
                collegeValue,
                1,
                3
        );


        grid.add(
                yearTitle,
                2,
                2
        );

        grid.add(
                yearValue,
                2,
                3
        );


        grid.add(
                categoryTitle,
                0,
                4
        );

        grid.add(
                categoryValue,
                0,
                5
        );


        grid.add(
                incomeTitle,
                1,
                4
        );

        grid.add(
                incomeValue,
                1,
                5
        );


        // ==========================================
        // EDIT BUTTON
        // ==========================================

        Button editButton =
                new Button(
                        "EDIT PROFILE"
                );

        editButton.getStyleClass().add(
                "login-button"
        );


        editButton.setOnAction(
                e -> showEditProfile(
                        stage,
                        name,
                        email,
                        course,
                        year,
                        college,
                        category,
                        income
                )
        );


        HBox buttonBox =
                new HBox(
                        editButton
                );

        buttonBox.setAlignment(
                Pos.CENTER_LEFT
        );


        profileCard.getChildren().addAll(
                grid,
                buttonBox
        );


        // ==========================================
        // MAIN CONTENT
        // ==========================================

        VBox content =
                new VBox(
                        20,
                        header,
                        profileCard
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
                        1100,
                        700
                );


        String css =
                ProfileView.class
                        .getResource(
                                "/css/style.css"
                        )
                        .toExternalForm();

        scene.getStylesheets().add(css);


        stage.setTitle(
                "ScholarHub - My Profile"
        );

        stage.setScene(scene);

        stage.show();
    }


    // ==================================================
    // EDIT PROFILE
    // ==================================================

    private static void showEditProfile(
            Stage stage,
            String currentName,
            String currentEmail,
            String currentCourse,
            int currentYear,
            String currentCollege,
            String currentCategory,
            double currentIncome
    ) {

        // ==========================================
        // BACK
        // ==========================================

        Button backButton =
                new Button("←");

        backButton.getStyleClass().add(
                "back-button"
        );

        backButton.setOnAction(
                e -> show(stage)
        );


        // ==========================================
        // TITLE
        // ==========================================

        Label title =
                new Label("Edit Profile");

        title.getStyleClass().add(
                "dashboard-title"
        );


        Label subtitle =
                new Label(
                        "Update your personal and academic information."
                );

        subtitle.getStyleClass().add(
                "dashboard-subtitle"
        );


        // ==========================================
        // NAME
        // ==========================================

        Label nameLabel =
                createTitle("Full Name");

        TextField nameField =
                new TextField(currentName);

        nameField.setPrefWidth(400);


        // ==========================================
        // EMAIL
        // ==========================================

        Label emailLabel =
                createTitle("Email");

        TextField emailField =
                new TextField(currentEmail);

        emailField.setPrefWidth(400);


        // ==========================================
        // COURSE
        // ==========================================

        Label courseLabel =
                createTitle("Course");

        TextField courseField =
                new TextField(currentCourse);

        courseField.setPrefWidth(400);


        // ==========================================
        // YEAR
        // ==========================================

        Label yearLabel =
                createTitle("Year of Study");

        ComboBox<Integer> yearBox =
                new ComboBox<>();

        yearBox.getItems().addAll(
                1,
                2,
                3,
                4
        );

        if (
                currentYear >= 1 &&
                currentYear <= 4
        ) {
            yearBox.setValue(currentYear);
        }


        // ==========================================
        // COLLEGE
        // ==========================================

        Label collegeLabel =
                createTitle("College");

        TextField collegeField =
                new TextField(currentCollege);

        collegeField.setPrefWidth(400);


        // ==========================================
        // CATEGORY
        // ==========================================

        Label categoryLabel =
                createTitle("Student Category");

        ComboBox<String> categoryBox =
                new ComboBox<>();

        categoryBox.getItems().addAll(
                "General",
                "OBC",
                "SC",
                "ST",
                "EWS"
        );

        categoryBox.setValue(
                currentCategory
        );


        // ==========================================
        // INCOME
        // ==========================================

        Label incomeLabel =
                createTitle(
                        "Annual Family Income"
                );

        TextField incomeField =
                new TextField();

        if (currentIncome > 0) {

            incomeField.setText(
                    String.valueOf(
                            (long) currentIncome
                    )
            );
        }

        incomeField.setPromptText(
                "Example: 250000"
        );

        incomeField.setPrefWidth(400);


        // ==========================================
        // SAVE BUTTON
        // ==========================================

        Button saveButton =
                new Button(
                        "SAVE CHANGES"
                );

        saveButton.getStyleClass().add(
                "login-button"
        );


        saveButton.setOnAction(
                e -> updateProfile(
                        stage,
                        nameField.getText(),
                        emailField.getText(),
                        courseField.getText(),
                        yearBox.getValue(),
                        collegeField.getText(),
                        categoryBox.getValue(),
                        incomeField.getText()
                )
        );


        // ==========================================
        // FORM
        // ==========================================

        GridPane form =
                new GridPane();

        form.setHgap(30);
        form.setVgap(15);

        form.add(
                nameLabel,
                0,
                0
        );

        form.add(
                nameField,
                0,
                1
        );


        form.add(
                emailLabel,
                1,
                0
        );

        form.add(
                emailField,
                1,
                1
        );


        form.add(
                courseLabel,
                0,
                2
        );

        form.add(
                courseField,
                0,
                3
        );


        form.add(
                yearLabel,
                1,
                2
        );

        form.add(
                yearBox,
                1,
                3
        );


        form.add(
                collegeLabel,
                0,
                4
        );

        form.add(
                collegeField,
                0,
                5
        );


        form.add(
                categoryLabel,
                1,
                4
        );

        form.add(
                categoryBox,
                1,
                5
        );


        form.add(
                incomeLabel,
                0,
                6
        );

        form.add(
                incomeField,
                0,
                7
        );


        VBox formCard =
                new VBox(25);

        formCard.getStyleClass().add(
                "stat-card"
        );

        formCard.setPadding(
                new Insets(30)
        );

        formCard.getChildren().addAll(
                form,
                saveButton
        );


        // ==========================================
        // MAIN CONTENT
        // ==========================================

        VBox content =
                new VBox(
                        20,
                        backButton,
                        title,
                        subtitle,
                        formCard
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
                        1100,
                        750
                );


        String css =
                ProfileView.class
                        .getResource(
                                "/css/style.css"
                        )
                        .toExternalForm();

        scene.getStylesheets().add(css);


        stage.setTitle(
                "ScholarHub - Edit Profile"
        );

        stage.setScene(scene);

        stage.show();
    }


    // ==================================================
    // UPDATE PROFILE
    // ==================================================

    private static void updateProfile(
            Stage stage,
            String name,
            String email,
            String course,
            Integer year,
            String college,
            String category,
            String incomeText
    ) {

        // ==========================================
        // BASIC VALIDATION
        // ==========================================

        if (
                name == null ||
                name.isBlank() ||

                email == null ||
                email.isBlank() ||

                course == null ||
                course.isBlank() ||

                college == null ||
                college.isBlank() ||

                year == null ||

                category == null ||
                category.isBlank() ||

                incomeText == null ||
                incomeText.isBlank()
        ) {

            System.out.println(
                    "Please fill all profile fields."
            );

            return;
        }


        double income;


        try {

            income =
                    Double.parseDouble(
                            incomeText
                    );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter a valid income."
            );

            return;
        }


        // ==========================================
        // UPDATE DATABASE
        // ==========================================

        String query = """
                UPDATE users
                SET name = ?,
                    email = ?,
                    course = ?,
                    year_of_study = ?,
                    college = ?,
                    category = ?,
                    annual_income = ?
                WHERE user_id = ?
                """;


        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setString(
                    1,
                    name
            );

            statement.setString(
                    2,
                    email
            );

            statement.setString(
                    3,
                    course
            );

            statement.setInt(
                    4,
                    year
            );

            statement.setString(
                    5,
                    college
            );

            statement.setString(
                    6,
                    category
            );

            statement.setDouble(
                    7,
                    income
            );

            // Test user
            statement.setInt(
                    8,
                    1
            );


            statement.executeUpdate();


            System.out.println(
                    "Profile updated successfully!"
            );


            // Show updated profile
            show(stage);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==================================================
    // HELPER METHODS
    // ==================================================

    private static Label createTitle(
            String text
    ) {

        Label label =
                new Label(text);

        label.getStyleClass().add(
                "card-title"
        );

        return label;
    }


    private static Label createValue(
            String text
    ) {

        Label label =
                new Label(text);

        label.getStyleClass().add(
                "profile-value"
        );

        return label;
    }
}