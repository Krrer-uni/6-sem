<?php
require_once("db.php");

function debug_to_console($data)
{
    $output = $data;
    if (is_array($output))
        $output = implode(',', $output);

    echo "<script>console.log('Debug Objects: " . $output . "' );</script>";
}
function gen_header($name)
{
    echo "<header>
    <div class=\"logo-containter\">
        <h1>Wojciech Rymer</h1>
        <hr>
        <h2>$name</h2>
    </div>

    <nav class=\"nav-links\" id=\"nav-menu\">
        <button class=\"hamburger\" onClick=\"handleHamburger()\">
            <i class=\"fa fa-bars\"></i>
        </button>
        <noscript>
            <a class=\"nav-link\" href=\"index.php\">about me</a>
            <a class=\"nav-link\" href=\"projects.php\">projects</a>
            <a class=\"nav-link\" href=\"interests.php\">interests</a>
            <a class=\"nav-link\" href=\"hobby.php\">hobby</a>
        </noscript>     
    </nav>       
    <script src=\"../scripts/menu.js\"></script>
</header>";
}

function gen_head()
{
    echo "<link href=\"../css/style.css\" rel=\"stylesheet\" type=\"text/css\" />
    <meta charset=\"UTF-8\">
    <meta name=\"robots\" content=\"index,follow\" />
    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">
    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">
    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">
    <title>Wojciech Rymer</title>";
}

function gen_footer()
{
    echo "  <ul>
    <li>phone: +48 503 402 466 </li>
    <li>email: wrymer2@gmail.com</li>
    <li><a href=\"https://www.linkedin.com/in/wojciech-rymer/\" target=\"_blank\">linkedin</a></li>";

    global $db_id;

    $val = dba_fetch('visits', $db_id);
    if ($val == false) {
        $val = 1;
        dba_replace('visits', $val, $db_id);
    }
    if (!isset($_COOKIE['usr_id'])) {
        $new_id = uniqid();
        setcookie('usr_id', $new_id, time() + 5);

        // global $val;
        $val++;
        dba_replace('visits', $val, $db_id);
    }


    echo "<li>Visits:" . $val . "</li>";
    echo "</ul>";
}

function check_user_status()
{
    $session_timeout = 5 * 60;
    if (!isset($_SESSION['logged_in'])) {
        $_SESSION['logged_in'] = false;
        debug_to_console("user status not set");
    }
    if ($_SESSION['logged_in'] === false) {
        debug_to_console("user not logged in");
        return;
    }
    if (!isset($_SESSION['login_time'])) {
        $_SESSION['logged_in'] = false;
        debug_to_console("user timeout not specified");
        return;
    }
    if (time() - $_SESSION['login_time'] > $session_timeout) {
        unset($_SESSION['login_time']);
        unset($_SESSION['user_id']);
        $_SESSION['logged_in'] = false;
        debug_to_console("user login timeout");
        return;
    }
    $_SESSION['login_time'] = time();
    debug_to_console("user login refreshed");
}

function load_login()
{
    if ($_SESSION['logged_in'] === false) {
        echo "<h2>Registration</h2>
        <form action=\"../php/register.php\" method=\"post\" enctype=\"multipart/form-data\">
          <input type=\"text\" id=\"username\" name=\"username\" placeholder=\"Username\" required>
          <input type=\"email\" id=\"email\" name=\"email\" placeholder=\"Email\" required>
          <input type=\"password\" id=\"password\" name=\"password\" placeholder=\"Password\" required>
          <button type=\"submit\">Register</button>
        </form>
        
        <h2>Login</h2>
        <form action=\"../php/login.php\" method=\"POST\" enctype=\"multipart/form-data\">
          <input type=\"text\" id=\"loginUsername\" name=\"loginUsername\" placeholder=\"Username\" required>
          <input type=\"password\" id=\"loginPassword\" name=\"loginPassword\" placeholder=\"Password\" required>
          <button type=\"submit\">Login</button>
        </form>";
    } else {
        echo "<p> Logged in as " . $_SESSION['user_id'] . "</p>";
    }
}
function write_comment($PROJECT_NAME)
{
    if ($_SESSION['logged_in']) {
        echo "<form id=\"addcomment\" action=\"../php/add_comment.php\" method=\"POST\" enctype=enctype=\"multipart/form-data\">
        <input type=\"text\" id=\"projectComment\" name=\"projectComment\" placeholder=\"Write your comment\" required>
        <input type=\"hidden\" name=\"user\" value=" . $_SESSION["user_id"] . " />
        <input type=\"hidden\" name=\"project\" value=" . $PROJECT_NAME . ">
        </form>";
    }
}

function load_comments($PROJECT_NAME)
{
    global $comments_db;
    echo "<h2> COMMENTS </h2>";
    write_comment($PROJECT_NAME);
    echo "<ul>";
    $key = dba_firstkey($comments_db);
    while ($key !== false) {
        $project_user = explode('_', $key);
        $comment = dba_fetch($key, $comments_db);
        if ($project_user[0] == $PROJECT_NAME){
            echo "<li>
                <p id=\"username\"><b>$project_user[1]:</b></p> 
                <p id=\"comment\">$comment</p>
                </li>";
        }
        $key = dba_nextkey($comments_db);
    
    }
    echo "</ul>";

}
?>