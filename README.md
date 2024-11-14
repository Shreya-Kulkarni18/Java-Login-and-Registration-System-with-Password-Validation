# Java-Login-and-Registration-System-with-Password-Validation
A simple Java application with user account management, data persistence, and password strength validation.
Java Login and Registration System with Password Validation
This Java application provides a basic login and registration system that includes password validation and data persistence. It demonstrates key features like creating new user accounts, verifying user credentials during login, and modifying existing user passwords. User data is securely saved in a local file using Java's file handling and serialization features.

Features :

User Registration: Users can create new accounts by providing their first name, last name, and birth year. A unique ID is generated based on these details, and users are prompted to set a secure password.
Password Validation: Enforces strong password requirements, including minimum length, uppercase and lowercase letters, digits, and special characters.
User Login: Allows registered users to log in using their unique ID and password.
Modify Password: Users can update their password after login.
Data Persistence: Stores user data in a local file, allowing the application to load and save information across sessions.

Project Structure :

Application.java: The main application interface with options for New User registration and Login.
LoginData.java: Contains the user data structure and methods for password validation.
IPD.java: Manages the list of user data, including loading and saving user information to a file.
NewUser.java: Handles the user registration process and displays the generated user ID.
LoginUser.java: Facilitates the login process, with password validation and an option to modify the password.
ModifyPassword.java: Allows users to update their password securely.
mypkg.Util.java: A utility class located in the mypkg package, which includes helper methods for the application
