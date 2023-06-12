<?php
require_once("php_helper_func.php");
session_name('test');
session_start(['cookie_lifetime' => 86400]);
// Check if the form is submitted
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Retrieve form data
    $user_id = $_POST["user"];
    $comment = $_POST["projectComment"];
    $project_name = $_POST["project"];

    global $comments_db;
    dba_replace($project_name . "_" . $user_id, $comment, $comments_db);

    header("Location: ../html/projects.php");
}
?>