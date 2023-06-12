<?php
require_once("php_helper_func.php");
session_name('test');
session_start(['cookie_lifetime' => 86400]);
// Check if the form is submitted
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Retrieve form data
    $username = $_REQUEST["username"];
    $email = $_REQUEST["email"];
    $password = $_REQUEST["password"];
    if (!isset($_REQUEST["username"]) || !isset($_REQUEST["username"]) || !isset($_REQUEST["username"])) {
        $_SESSION['logged_in'] = false;
        // header("Location: ../html/projects.php");
        foreach($_POST as $value){
             echo "<p> $value </p>";
        }
        flush();
        ob_flush();
        print_r($_POST);
        return;
    }
    
    // Perform validation and data sanitization (e.g., check if fields are not empty, validate email format)

    // Insert the user data into a database (you need to set up a database and create a table for user registration)
    global $user_db;
    global $user_salt;
    dba_replace($username, hash("sha256", $password . $user_salt), $user_db);

    // Display success message or redirect to a success page
    $_SESSION['logged_in'] = true;
    $_SESSION['user_id'] = $username;
    $_SESSION['login_time'] = time();
    header("Location: ../html/projects.php");
}

?>