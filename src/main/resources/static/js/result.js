function handleCheckbox(chbx){
	const listaDeCheckboxesIndividuais = document.querySelectorAll("#individual-checkbox")
	if(chbx.checked == true){
		listaDeCheckboxesIndividuais.forEach(i => i.checked = true)
	} else {
		listaDeCheckboxesIndividuais.forEach(i => i.checked = false)
	}
}


//FUNCAO QUE MOSTRA O MODAL
function setModal(modalId){
	const modal = document.getElementById(modalId);
	modal.classList.add("mostrar");
	modal.addEventListener("click", (e) => {
		if(e.target.id == modalId || e.target.className == "fechar"){
			modal.classList.remove("mostrar")
		}
	})
}
//tem que trocar o ".filtrar" por classe/id que tu colocar na imagem de lixeira
const excluir = document.querySelector(".filtrar")
excluir.addEventListener("click", function(){
	setModal("modal");
})