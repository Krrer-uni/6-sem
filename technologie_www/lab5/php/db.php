<?php

$databasepath="/home/krrer/tmp/";
$db_id = dba_open($databasepath ."test-db4.db","c","db4");
$user_db = dba_open($databasepath ."users.db","c","db4");
$comments_db = dba_open($databasepath ."comments.db","c","db4");

$user_salt = "0875057173";
if(!$db_id)
{
  echo "cookie dba open failed\n";
  exit;
}

if(!$user_db)
{
  echo "user dba open failed\n";
  exit;
}

if(!$comments_db)
{
  echo "comments dba open failed\n";
  exit;
}
?>
