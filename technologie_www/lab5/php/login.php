<?php
require_once("php_helper_func.php");
session_name('test');
session_start(['cookie_lifetime' => 86400]);
// Check if the form is submitted
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Retrieve form data
    $username = $_POST["loginUsername"];
    $password = $_POST["loginPassword"];

    // Perform validation (e.g., check if fields are not empty)

    // Retrieve user data from the database and compare the entered password

    global $user_db;
    global $user_salt;
    $db_password = dba_fetch($username, $user_db);
    if ($db_password == hash("sha256", $password . $user_salt)) {
        $_SESSION['logged_in'] = true;
        $_SESSION['user_id'] = $username;
        $_SESSION['login_time'] = time();
    } else {
        $_SESSION['logged_in'] = false;
        print($username . $password);

    }
    // If login is successful, redirect to the dashboard or display success message; otherwise, display error message
    header("Location: ../html/projects.php");

}
?>