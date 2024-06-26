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
    let menuItems = Array.prototype.slice.call(document.querySelector("#menu-rodape ul").children);
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
let menuItems = document.querySelectorAll("#menu-rodape li");

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

/*
let buttonAdicionarHabilidade = document.querySelector(".buttonAdicionarHabilidade")
buttonAdicionarHabilidade.addEventListener("click", function () {
    let inputValor = document.getElementById("habilidade").value;
    let selectListaHabilidade = document.getElementById("listaHabilidade");
    let isValidAdd = true;
    let elementOption = document.createElement("option");

    for (let option of selectListaHabilidade.options) {
        if (option.text == inputValor || inputValor.trim() === "") {
            isValidAdd = false;
        }
    }

    if (isValidAdd) {
        elementOption.text = inputValor;
        elementOption.id = inputValor;
        selectListaHabilidade.appendChild(elementOption);
        inputValor = "";
    }
});

let selectCurrentOptionID = "";
let selectListaHabilidade = document.getElementById("listaHabilidade");
selectListaHabilidade.addEventListener("change", function () {
    selectCurrentOptionID = selectListaHabilidade.options[selectListaHabilidade.selectedIndex].id;
});
let buttonRemoverHabilidade = document.querySelector(".buttonRemoverHabilidade");
buttonRemoverHabilidade.addEventListener("click", function () {
    for (let option of selectListaHabilidade.options) {
        if (option.id == selectCurrentOptionID) {
            selectListaHabilidade.remove(option.index);
        }
    }
}); */ 

function changeDialogData(button) {
	const modalTitle = document.querySelector(".modalHabilidadeLabel");
	const modalAddLabel = document.querySelector(".labelAddHabilidade");
	const modalRemoveLabel = document.querySelector(".labelRemove");
	const buttonNameAtt = button.getAttribute('name');
	
	modalTitle.innerHTML = `${buttonNameAtt}`;
	modalAddLabel.innerHTML = `Adicione um(a) ${buttonNameAtt.substring(0, buttonNameAtt.length - 1).toLowerCase()}.`;
	modalRemoveLabel.innerHTML = `Selecione e remova um(a) ${buttonNameAtt.substring(0, buttonNameAtt.length - 1).toLowerCase()} da lista.`;
}

function showDialog(id, button) {
	changeDialogData(button)
    let hidden = document.querySelector("#dialogHidden");
    hidden.value = id;
    let dialog = document.querySelector("dialog");
   	
   	if(id == "selHabilidade") { //Puxa as opções já existentes com ID "selHabilidade" e joga dentro do select do modal
		let selHabilidade = document.querySelector("#selHabilidade");
		let optionsSelHabilidade = selHabilidade.querySelectorAll("option");
		let dialogSelect = dialog.querySelector("#dialogSelect")
		let optionsDialogSelect = dialogSelect.querySelectorAll("option");
		optionsDialogSelect.forEach(e => e.remove());
	   	optionsSelHabilidade.forEach(option => dialogSelect.appendChild(option));
	   	
   	} else if(id == "selCursos") {
		let selCursos = document.querySelector("#selCursos");
		let optionsSelCursos = selCursos.querySelectorAll("option");
		let dialogSelect = dialog.querySelector("#dialogSelect")
		let optionsDialogSelect = dialogSelect.querySelectorAll("option");
		optionsDialogSelect.forEach(e => e.remove());
		optionsSelCursos.forEach(option => dialogSelect.appendChild(option));
	} else if(id == "selArtigos") {
		let selArtigos = document.querySelector("#selArtigos");
		let optionsSelArtigos = selArtigos.querySelectorAll("option");
		let dialogSelect = dialog.querySelector("#dialogSelect")
		let optionsDialogSelect = dialogSelect.querySelectorAll("option");
		optionsDialogSelect.forEach(e => e.remove());
		optionsSelArtigos.forEach(option => dialogSelect.appendChild(option));
	};
    
    dialog.showModal();
    let dAdd = document.querySelector("#dialogAdd");
    dAdd.onclick = dialogAdd;
    let dDel = document.querySelector("#dialogDel");
    dDel.onclick = dialogDel;
    let dClose = document.querySelector("#dialogClose");
    dClose.onclick = dialogClose;
}
function dialogAdd() {
    let dialogValue = document.querySelector("#dialogValue");
    let value = dialogValue.value;
    let select = document.querySelector("#dialogSelect");
    let option = document.createElement("option");
    option.text = value;
    option.setAttribute("selected", true);
    
    option.setAttribute("id", "hab"); //
	
	select.add(option);
    dialogValue.value = "";
}
function dialogDel() {
    let select = document.querySelector("#dialogSelect");
    select.remove(select.selectedIndex);
}
function dialogClose() {
    let hidden = document.querySelector("#dialogHidden");
    let mainSelect = document.getElementById(hidden.value);
    let dSelect = document.querySelector("#dialogSelect");
    mainSelect.innerHTML = dSelect.innerHTML;
    let dialog = document.querySelector("dialog");
    dSelect.innerHTML = ""; //
    
    dialog.close();
}
let botoesAdd = document.querySelectorAll("button[data-add]");
botoesAdd.forEach(b => {
    b.onclick = () => showDialog(b.dataset.add, b);
});

// A ideia desse EventListener é perceber a mudança de option selecionado no Select.
// Quando há a mudança, uma variável global(selectCurrentOptionID) pega o ID desse option no Select e guarda.

// Utilizando a variável global(selectCurrentOptionID) inicializada através do "change" do EventListener acima(linha 108)
// Nós utilizaremos ela para comparar com o ID de cada Option. Quando encontrarmos esse Option, removeremos ele...
// Utilizando o método remove().


//               modal voltar com fade

document.querySelector('#botao-voltar').addEventListener('click',()=>{
    let modal = document.querySelector('.modal-confirm');
    let fade = document.querySelector('.fade-modal');
    modal.style.display='flex';
    fade.style.display='flex';
})

document.querySelector('#cancelar').addEventListener('click', ()=>{
    let modal = document.querySelector('.modal-confirm');
    let fade = document.querySelector('.fade-modal');
    modal.style.display='none';
    fade.style.display='none';
})

//                     Validação CPF

function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]+/g, ''); // Remove caracteres não numéricos

    if (cpf.length !== 11 || /^(.)\1+$/.test(cpf)) {
        return false;
    }

    var soma = 0;
    for (var i = 0; i < 9; i++) {
        soma += parseInt(cpf.charAt(i)) * (10 - i);
    }

    var resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) {
        resto = 0;
    }

    if (resto !== parseInt(cpf.charAt(9))) {
        return false;
    }

    soma = 0;
    for (var i = 0; i < 10; i++) {
        soma += parseInt(cpf.charAt(i)) * (11 - i);
    }

    resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) {
        resto = 0;
    }

    if (resto !== parseInt(cpf.charAt(10))) {
        return false;
    }

    return true;
}

document.addEventListener('DOMContentLoaded', function() {
    var form = document.getElementById('formulario');
    form.addEventListener('submit', function(event) {
        var cpfInput = document.getElementById('cpf');
        var cpf = cpfInput.value;
        document.querySelector('#cpfInvalido').setAttribute('style', 'display:none;');
        if (!validarCPF(cpf)) {
            event.preventDefault();
            document.querySelector('#cpfInvalido').setAttribute('style', 'display:block;');
            exibirTela();
            return false;
        }
    });
});