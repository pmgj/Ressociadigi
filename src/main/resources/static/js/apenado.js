//Implementação da Máscara dos campos Telefone
const inputTelefone = document.getElementById('telefone');
const inputTelefone2 = document.getElementById('telefone2');
inputTelefone.addEventListener("keyup", formatarTelefone);
inputTelefone2.addEventListener("keyup", formatarTelefone);
function formatarTelefone(e){
	var v = e.target.value.replace(/\D/g, "");
	v = v.replace(/^(\d\d)(\d)/g,"($1) $2");
	v=v.replace(/(\d{5})(\d)/,"$1-$2");
	e.target.value = v;
}
//Fim da Implementação da Máscara



//Implementação do campo dinâmico da Área de Restrição
function areaRestricaoBehavior(){
	document.getElementById('radiobtn-restricao-sim').checked ? document.getElementById('area-restricao').disabled = false :
	document.getElementById('area-restricao').disabled = true;
}
//Fim da Implementação do campo dinâmico

function exibirTela(evt) {
    let menuItems = Array.prototype.slice.call(document.querySelector("#menu-lateral ul").children);
    let index = evt ? menuItems.indexOf(evt.target) : 0;
    let inputs = document.querySelectorAll("#container-inputs > div");

    inputs.forEach((item, i) => {
        if(index === i) {
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

function mudarTitulo(){
    let title = document.getElementById("titulox");
    let dp = document.getElementById("dp");
    let ende = document.getElementById("ende");
    let inst = document.getElementById("inst");
    let situ = document.getElementById("situ");
    dp.addEventListener("click", function(){
        title.innerText = "Dados Pessoais"
    })
    ende.addEventListener("click", function(){
        title.innerText = "Endereço"
    })
    inst.addEventListener("click", function(){
        title.innerText = "Instrução"
    })
    situ.addEventListener("click", function(){
        title.innerText = "Situacional"
    })

}
mudarTitulo()