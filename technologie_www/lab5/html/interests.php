<!DOCTYPE html>
<html lang="en">

<?php
require '../php/init.php';
?>

<head>
    <?php
    gen_head();
    ?>
    <script type="text/javascript" id="MathJax-script" async
        src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
    <script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3.2.2/es5/tex-chtml.js"></script>
</head>

<body>
    <?php
    gen_header("interests");
    ?>

    <main>
        <article class="interests">
            <h2>Some of my interests</h2>
            <section class="interest">
                <h4>math</h4>
                <p>I really like math Lorem ipsum dolor sit amet consectetur adipisicing elit. Facere temporibus cumque
                    ipsam ex eius alias a ipsum quam voluptatibus voluptatem nesciunt quibusdam aspernatur nemo suscipit
                    aperiam eum, quo in animi nostrum optio laboriosam. Distinctio quaerat nihil facilis, deserunt cum
                    et sint quo ut, vero consequuntur velit voluptate tempora necessitatibus voluptates!
                </p>
            </section>
            <section class="interest">
                <h4>cs</h4>
                <p>I really like cs Lorem ipsum dolor sit amet consectetur adipisicing elit. Facere temporibus cumque
                    ipsam ex eius alias a ipsum quam voluptatibus voluptatem nesciunt quibusdam aspernatur nemo suscipit
                    aperiam eum, quo in animi nostrum optio laboriosam. Distinctio quaerat nihil facilis, deserunt cum
                    et sint quo ut, vero consequuntur velit voluptate tempora necessitatibus voluptates!
                </p>
            </section>
            <section>
                <h3>rest of my interests</h3>
                <ul class="interests-list">
                    <li>teoria mnogosci</li>
                    <li>uczenie maszynowe</li>
                    <li>programowanie funkcyjne</li>
                    <li>mobilki</li>

                </ul>
            </section>
            <section>
                <h3> my fave eq </h3>
                <p>
                    $$X(\omega) = \sum_{n=-\infty}^{+\infty}x(n)e^{-j\omega n}$$ </p>
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