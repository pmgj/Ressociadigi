//Funcionamento de modal do icone de filtro

var iconeFiltro = document.querySelector('#icone-filtro');

var modal = document.querySelector('.modal');

var closeBtn = document.querySelector('.close');

// Add click event listener to the image element
iconeFiltro.addEventListener('click', function() {
	modal.style.display = 'block';
});

closeBtn.addEventListener('click', function() {
	modal.style.display = 'none';
});

modal.addEventListener('click', function(event) {
	if (event.target === modal) {
		modal.style.display = 'none';
	}
});


function handleCheckbox(chbx){
	const listaDeCheckboxesIndividuais = document.querySelectorAll("#individual-checkbox")
	if(chbx.checked == true){
		listaDeCheckboxesIndividuais.forEach(i => i.checked = true)
	} else {
		listaDeCheckboxesIndividuais.forEach(i => i.checked = false)
	}
}


//FUNCAO QUE MOSTRA O MODAL
function setModal(modalId, nomeDoApenado, cpfApenado){
	const modal = document.getElementById(modalId);
	document.getElementById("mensagem-de-confirmacao").innerText = `Você tem certeza que deseja excluir o registro de ${nomeDoApenado} definitivamente?`;
	modal.classList.add("mostrar");
	modal.addEventListener("click", (e) => {
		if(e.target.id == modalId || e.target.className == "fechar" || e.target.className == "botao-nao"){
			modal.classList.remove("mostrar")
		} else if(e.target.className == "botao-sim"){
			window.location.assign("www.google.com");
		}
	})
}


//tem que trocar o ".filtrar" por classe/id que tu colocar na imagem de lixeira
const excluir = document.querySelectorAll()
excluir.forEach((i) => {
	i.addEventListener("click", function(){
		let nomeDoApenado = i.getAttribute("data-nome");
		let cpfApenado = i.getAttribute("data-cpf");
		setModal("modal", nomeDoApenado, cpfApenado);
	})
})


