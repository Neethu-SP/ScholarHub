package com.scholarhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EligibilityService {

    public static String checkEligibility(
            int scholarshipId,
            int userId
    ) {

        String userQuery = """
                SELECT course,
                       year_of_study,
                       category,
                       annual_income
                FROM users
                WHERE user_id = ?
                """;

        String scholarshipQuery = """
                SELECT name
                FROM scholarships
                WHERE scholarship_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement userStatement =
                        connection.prepareStatement(userQuery);

                PreparedStatement scholarshipStatement =
                        connection.prepareStatement(scholarshipQuery)
        ) {

            // ==========================================
            // GET STUDENT PROFILE
            // ==========================================

            userStatement.setInt(1, userId);

            ResultSet userResult =
                    userStatement.executeQuery();

            if (!userResult.next()) {
                return "Profile information not found";
            }

            String course =
                    userResult.getString("course");

            int year =
                    userResult.getInt("year_of_study");

            String category =
                    userResult.getString("category");

            double income =
                    userResult.getDouble("annual_income");


            // ==========================================
            // GET SCHOLARSHIP NAME
            // ==========================================

            scholarshipStatement.setInt(
                    1,
                    scholarshipId
            );

            ResultSet scholarshipResult =
                    scholarshipStatement.executeQuery();

            if (!scholarshipResult.next()) {
                return "Scholarship information not found";
            }

            String scholarshipName =
                    scholarshipResult.getString("name");


            // ==========================================
            // AICTE PRAGATI
            // ==========================================

            if (
                    scholarshipName
                            .toLowerCase()
                            .contains("pragati")
            ) {

                if (
                        year >= 1 &&
                        year <= 4 &&
                        income <= 800000
                ) {
                    return "✓ Potentially Eligible";
                }

                return "⚠ Check Eligibility";
            }


            // ==========================================
            // AICTE SAKSHAM
            // ==========================================

            if (
                    scholarshipName
                            .toLowerCase()
                            .contains("saksham")
            ) {

                if (
                        year >= 1 &&
                        year <= 4 &&
                        income <= 800000
                ) {
                    return "✓ Potentially Eligible";
                }

                return "⚠ Check Eligibility";
            }


            // ==========================================
            // AICTE SWANATH
            // ==========================================

            if (
                    scholarshipName
                            .toLowerCase()
                            .contains("swanath")
            ) {

                if (
                        year >= 1 &&
                        year <= 4 &&
                        income <= 800000
                ) {
                    return "✓ Potentially Eligible";
                }

                return "⚠ Check Eligibility";
            }


            // ==========================================
            // PM-USP
            // ==========================================

            if (
                    scholarshipName
                            .toLowerCase()
                            .contains("pm-usp")
            ) {

                if (
                        year >= 1 &&
                        year <= 4 &&
                        income <= 450000
                ) {
                    return "✓ Potentially Eligible";
                }

                return "⚠ Check Eligibility";
            }


            // ==========================================
            // RELIANCE FOUNDATION
            // ==========================================

            if (
                    scholarshipName
                            .toLowerCase()
                            .contains("reliance")
            ) {

                if (
                        year >= 1 &&
                        year <= 4 &&
                        income <= 150000
                ) {
                    return "✓ Potentially Eligible";
                }

                return "⚠ Check Eligibility";
            }


            // ==========================================
            // LIC GOLDEN JUBILEE
            // ==========================================

            if (
                    scholarshipName
                            .toLowerCase()
                            .contains("lic")
            ) {

                if (
                        year >= 1 &&
                        year <= 4 &&
                        income <= 400000
                ) {
                    return "✓ Potentially Eligible";
                }

                return "⚠ Check Eligibility";
            }


            // ==========================================
            // NATIONAL MEANS-CUM-MERIT
            // ==========================================

            if (
                    scholarshipName
                            .toLowerCase()
                            .contains("means-cum-merit")
            ) {

                if (
                        year >= 1 &&
                        year <= 4 &&
                        income <= 350000
                ) {
                    return "✓ Potentially Eligible";
                }

                return "⚠ Check Eligibility";
            }


            // ==========================================
            // ST FELLOWSHIP
            // ==========================================

            if (
                    scholarshipName
                            .toLowerCase()
                            .contains("students")
            ) {

                if (
                        category != null &&
                        category.equalsIgnoreCase("ST")
                ) {
                    return "✓ Potentially Eligible";
                }

                return "⚠ Category requirement may not match";
            }


            // ==========================================
            // DEFAULT
            // ==========================================

            return "ℹ Check Eligibility";

        } catch (Exception e) {

            e.printStackTrace();

            return "Unable to check eligibility";
        }
    }
}