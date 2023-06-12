<!DOCTYPE html>
<html lang="en">

<?php
require '../php/init.php';
?>

<head>
    <?php
    gen_head();
    ?>
</head>

<body>
    <?php
    gen_header("about");
    ?>

    <main>
        <article>
            <section class="about-me">
                <img src="../res/zdjęcie-strona.jpg" alt="my-image">
                <div class="about-me-text">
                    <h2 class="who-am-i-caption">Who am I?</h2>
                    <p>Hi!, I am a 3rd year student of algorithmic computer science at Wrocław University of Technology.
                        I come from Ostrów Wielkopolski and moved to my town of study this year. In free time I work on
                        autonomous electric car as software developer, my niche there is perception and sensor fusion. I
                        can't live without doing some kind of sport activity, mostly climbing, running or weightlifting.
                        Traveling is also a huge part of my life and I always try capture best moment in photographs.
                        More info in tabs!
                    </p>
                </div>
            </section>
            <section class="gallery">
                <h2>gallery</h2>
                <ul class="photo-list" id="photo-list">
                </ul>
                <script src="../scripts/loadImage.js"></script>
            </section>
        </article>
    </main>

    <footer>
        <?php
        gen_footer()
            ?>
    </footer>

</body>

</html>