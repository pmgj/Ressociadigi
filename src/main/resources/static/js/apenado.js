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




let numeroDeclicks = 1;
let formIntrucao = document.getElementById("grupo-input-instrucao");
let habilidadeBotao = document.querySelector(".btn-habilidade")
let div = document.createElement("div")
let contador = 1;
let Input = document.getElementById("habilidade");
habilidadeBotao.addEventListener("click", function () {

    if (contador <= 5 || !Input.value === "") {
        let valorInput = Input.value;
        let span = document.createElement("span");
        let botaoDeleteHabilidade = document.createElement("button");
        botaoDeleteHabilidade.className = "botaoDeleteHabilidade"
        botaoDeleteHabilidade.innerHTML = "--"
        span.className = "cardHabilidade"
        span.append(valorInput);

        let divContainer = document.createElement("div")
        divContainer.append(span)
        divContainer.append(botaoDeleteHabilidade)
        divContainer.className = "divContainer"
        formIntrucao.append(divContainer);
        Input.value = ""

    }
    contador++
});