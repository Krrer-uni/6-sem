
var menu = document.getElementById("nav-menu");
var links = [];

var hamburger = document.createElement("button");
var menu_entries = ["about", "projects", "interests", "hobby"];
menu_entries.forEach(element => {
    newLink = document.createElement("a");
    newLink.className="nav-link";
    newLink.href=element + ".php";
    newLink.innerHTML=element;
    links.push(newLink);
    menu.appendChild(newLink);
});



const fontColor = "#f3cd98";
const mainElemColor = "#A84448";
const siblingColor ="#d0798a";
const translation = "translate(0,2rem)";
const siblingTranslation = "translate(0,1rem)";



function handleHamburger(){
    var nav = document.getElementById("nav-menu");
    if(nav.className === "nav-links"){
        nav.className += " responsive";
    } else{
        nav.className = "nav-links";
    }
}

for(let link of links){
    // link.style.backgroundColor = "black";
    entry = link
    link.style.transition = "0.3s";

    let base_color = link.style.backgroundColor;
    let baseFontColor = link.style.color;

    let prevElem = link.previousElementSibling;
    let nextElem = link.nextElementSibling;

    link.addEventListener("mouseover", function mouseoverevent() {
        nav = document.getElementById("nav-menu");
        if(nav.className === "nav-links responsive"){
            return;
        }
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

    link.addEventListener("mouseout", function mouseoverevent() {
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
