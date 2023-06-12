<?php
require_once("db.php");
require_once("php_helper_func.php");

session_name( 'test' );
session_start(['cookie_lifetime' => 86400]);
check_user_status();

?>