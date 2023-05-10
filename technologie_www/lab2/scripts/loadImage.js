function loadIMG(url, id){
    var P = new Promise(function(resolve,reject){
            var parent = document.getElementById(id);
            var element = document.createElement("img");
            var list_entry = document.createElement("li");
            element.setAttribute("src", url);
            element.setAttribute("alt", url);
            element.setAttribute("width", "30%");
            list_entry.appendChild(element);
            parent.appendChild(list_entry);
            element.onload = function() { resolve(url); };
            element.onerror = function() {reject(url); };
        }
    );
    return P;
}

Promise.all([
    loadIMG("../res/photo1.png","photo-list"),
    loadIMG("../res/photo2.jpg","photo-list"),
    loadIMG("../res/photo3.jpg","photo-list")
]).then(function() {
    console.log("Wszystko z równoległej się załadowało!");
  }).catch(function() {
    console.log("Błąd ładowania galerii rownoległej");
  });