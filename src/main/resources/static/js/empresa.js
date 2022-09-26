function teste(){
	let dadosIniciaisOption = document.querySelector("#dp");
	
	dadosIniciaisOption.style.borderLeft = "solid 3px rgba(246, 130, 31, 1)";
}

teste();

/*
function exibirTela(evt) {
    let menuItems = Array.prototype.slice.call(document.querySelector("#menu-lateral ul").children);
    let index = evt ? menuItems.indexOf(evt.target) : 0;
    let inputs = document.querySelectorAll("#container-input > div");

    inputs.forEach((item, i) => {
        if (index === i) {
            menuItems[i].style.borderLeft = "solid 3px rgba(246, 130, 31, 1)";
            menuItems[i].style.paddingLeft = "8px";
            menuItems[i].style.color = "black";
            item.style.display = "grid";
        } else {
            menuItems[i].style.borderLeft = "none";
            menuItems[i].style.paddingLeft = "0";
            menuItems[i].style.color = "rgba(26, 24, 24, 0.4)";
            item.style.display = "none";
        }
    });
}

exibirTela();
let menuItems = document.querySelectorAll("#menu-lateral li");
menuItems.forEach(i => i.onclick = exibirTela);
*/