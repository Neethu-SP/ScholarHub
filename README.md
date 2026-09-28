# ScholarHub 🎓

### Real-World Scholarship Discovery & Application Tracker

ScholarHub is a JavaFX-based desktop application designed to help students discover scholarship opportunities, check potential eligibility, save scholarships, and track their applications in one place.

The application provides scholarship information and redirects students to the official scholarship website for the actual application process.

---

## ✨ Features

### 👨‍🎓 Student Features

- Student login
- Personalized student profile
- Browse available scholarships
- View scholarship details
- Check potential eligibility based on profile information
- Save scholarships for later
- Remove saved scholarships
- Track scholarship applications
- View application status
- Remove application tracking records
- Open official scholarship application websites
- Logout and return to login

### 👨‍💼 Admin Features

- Admin login
- View student applications
- View student and scholarship information
- Update application status
- Track application progress

---

## 🖥️ Application Modules

### 1. Login

Users can log in using their registered email and password.

ScholarHub supports two roles:

- Student
- Admin

The application automatically redirects users to the appropriate dashboard based on their role.

---

### 2. Student Dashboard

The dashboard provides an overview of the student's scholarship activity.

It displays:

- Available scholarships
- Saved scholarships
- My applications
- Quick actions
- Scholarship journey

---

### 3. Scholarship Discovery

Students can browse scholarship opportunities stored in the ScholarHub database.

Each scholarship provides information such as:

- Scholarship name
- Provider
- Scholarship amount
- Deadline
- Category
- Eligibility information
- Required documents
- Official application link

---

### 4. Eligibility Checking

ScholarHub performs a preliminary eligibility check using information from the student's profile.

The eligibility logic considers factors such as:

- Course
- Year of study
- Category
- Annual family income

> **Note:** The eligibility result is only a potential eligibility indicator. Students should always verify the official eligibility requirements on the scholarship provider's website.

---

### 5. Saved Scholarships

Students can save scholarships that they are interested in.

Saved scholarships can later be:

- Viewed
- Opened for details
- Removed from the saved list

---

### 6. Application Tracking

Students can track scholarship applications through ScholarHub.

Application information includes:

- Scholarship
- Provider
- Applied date
- Application status

Possible statuses include:

- APPLIED
- UNDER REVIEW
- APPROVED
- REJECTED

ScholarHub does not submit the scholarship application itself. The student is redirected to the official scholarship website to complete the application.

---

### 7. Admin Management

Administrators can view scholarship applications submitted through the ScholarHub tracking system.

The admin can update application statuses such as:

- APPLIED
- UNDER REVIEW
- APPROVED
- REJECTED

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Application development |
| JavaFX | Graphical user interface |
| JDBC | Database connectivity |
| MySQL | Data storage |
| Maven | Project and dependency management |
| CSS | User interface styling |
| Git | Version control |
| GitHub | Source code hosting |
| VS Code | Development environment |

---

## 🏗️ Project Architecture

```text
ScholarHub
│
├── JavaFX Frontend
│       │
│       ├── Login
│       ├── Dashboard
│       ├── Scholarship Discovery
│       ├── Saved Scholarships
│       ├── Applications
│       ├── Profile
│       └── Admin Panel
│
├── Java Backend Logic
│       │
│       ├── Eligibility Service
│       ├── Application Management
│       └── Database Operations
│
└── MySQL Database
        │
        ├── users
        ├── scholarships
        ├── applications
        └── saved_scholarships