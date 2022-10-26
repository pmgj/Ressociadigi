//Implementação da Máscara dos campos Telefone
const inputTelefone = document.getElementById('telefone');
const inputTelefone2 = document.getElementById('telefone2');
inputTelefone.addEventListener("keyup", formatarTelefone);
inputTelefone2.addEventListener("keyup", formatarTelefone);
function formatarTelefone(e) {
    var v = e.target.value.replace(/\D/g, "");
    v = v.replace(/^(\d\d)(\d)/g, "($1) $2");
    v = v.replace(/(\d{5})(\d)/, "$1-$2");
    e.target.value = v;
}
//Fim da Implementação da Máscara

//Implementação do campo dinâmico da Área de Restrição
function areaRestricaoBehavior(evt) {
    let checkedRadio = () => {
        const restricaoRadioButtons = document.querySelectorAll("#restricao input[type='radio']");
        for (let button of restricaoRadioButtons) {
            if (button.checked) {
                return button;
            }
        }
    };
    let radio = evt ? evt.currentTarget : checkedRadio();
    let textarea = document.querySelector("#area-restricao");
    textarea.disabled = radio.value === "NAO";
}
const restricaoRadioButtons = document.querySelectorAll("#restricao input[type='radio']");
restricaoRadioButtons.forEach(radio => radio.onclick = areaRestricaoBehavior);
areaRestricaoBehavior();
//Fim da Implementação do campo dinâmico

function exibirTela(evt) {
    let menuItems = Array.prototype.slice.call(document.querySelector("#menu-lateral ul").children);
    let index = evt ? menuItems.indexOf(evt.target) : 0;
    let inputs = document.querySelectorAll("#container-inputs > div");

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


function mudarTitulo() {
    let menus = document.getElementsByTagName("li");
    let title = document.getElementById("titulox");
    let menusArray = Array.from(menus);
    menusArray[0].addEventListener("click", () => {
        title.innerText = "Dados Pessoais"
    })
    menusArray[1].addEventListener("click", () => {
        title.innerText = "Endereço"
    })
    menusArray[2].addEventListener("click", () => {
        title.innerText = "Instrução"
    })
    menusArray[3].addEventListener("click", () => {
        title.innerText = "Situacional"
    })
}
mudarTitulo();

/*********************************************************/
/****************MECANISMO DE HABILIDADES*****************/
/*********************************************************/

let buttonAdicionarHabilidade = document.querySelector(".buttonAdicionarHabilidade")
buttonAdicionarHabilidade.addEventListener("click", function (){

    let inputValor = document.getElementById("habilidade").value;
    let selectListaHabilidade = document.getElementById("listaHabilidade");
    let isValidAdd = true;
    let elementOption = document.createElement("option");
	
	for(let option of selectListaHabilidade.options){
		if(option.text == inputValor || inputValor.trim() === ""){
			isValidAdd = false;
		}
	}
	
	if(isValidAdd){
        elementOption.text = inputValor;
        elementOption.id = inputValor;
        selectListaHabilidade.appendChild(elementOption);
        inputValor = "";
    }
})

let selectCurrentOptionID = "";
let selectListaHabilidade = document.getElementById("listaHabilidade");


selectListaHabilidade.addEventListener("change", function(){
	selectCurrentOptionID = selectListaHabilidade.options[selectListaHabilidade.selectedIndex].id;	
})


let buttonRemoverHabilidade = document.querySelector(".buttonRemoverHabilidade");
buttonRemoverHabilidade.addEventListener("click", function (){
	for(let option of selectListaHabilidade.options){
		if(option.id == selectCurrentOptionID){
			selectListaHabilidade.remove(option.index);
		}
	}
}) 

function multiInputs(btnAdicinar){
    return{



    }

}

// A ideia desse EventListener é perceber a mudança de option selecionado no Select.
// Quando há a mudança, uma variável global(selectCurrentOptionID) pega o ID desse option no Select e guarda.

// Utilizando a variável global(selectCurrentOptionID) inicializada através do "change" do EventListener acima(linha 108)
// Nós utilizaremos ela para comparar com o ID de cada Option. Quando encontrarmos esse Option, removeremos ele...
// Utilizando o método remove().