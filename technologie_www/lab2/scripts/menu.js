
menu = document.getElementById("nav-menu");
links = Array.from(menu.childNodes)
links = links.filter( (elem) =>  elem.nodeName != "#text");

fontColor = "#f3cd98";
mainElemColor = "#A84448";
siblingColor ="#d0798a";
translation = "translate(0,2rem)";
siblingTranslation = "translate(0,1rem)";


for(let link of links){
    // link.style.backgroundColor = "black";
    entry = link
    link.style.transition = "0.3s";

    let base_color = link.style.backgroundColor;
    let baseFontColor = link.style.color;

    let prevElem = link.previousElementSibling;
    let nextElem = link.nextElementSibling;
    link.addEventListener('mouseover', function mouseoverevent() {
        if(prevElem != null){
            prevElem.style.backgroundColor = siblingColor;
            prevElem.style.color = fontColor;
            prevElem.style.transform = siblingTranslation;
        };
        if(nextElem != null){
            nextElem.style.backgroundColor = siblingColor;
            nextElem.style.color = fontColor;
            nextElem.style.transform = siblingTranslation;
        }
        link.style.backgroundColor = mainElemColor;
        link.style.transform = translation;
    });

    link.addEventListener('mouseout', function mouseoverevent() {
        if(prevElem != null){
            prevElem.style.backgroundColor = base_color;
            prevElem.style.color = baseFontColor;
            prevElem.style.transform = "translate(0,0)";
        }
        if(nextElem != null){
            nextElem.style.backgroundColor = base_color;
            nextElem.style.color = baseFontColor;
            nextElem.style.transform = "translate(0,0)";
        }
        link.style.backgroundColor = base_color;
        link.style.transform = "translate(0,0)";
    });
}
