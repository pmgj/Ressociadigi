//Modal de Filtro
$(document).on("click", "#icone-filtro", function () {
	$('#filtroModal').modal('show');
});


//Modal de Exclusao
$(document).on("click", "#botao-remover", function () {
	var nome = $(this).data('nome');
	$("#confirmNome").text(nome);
	var cpf = $(this).data('cpf');
	$("#botao-confirmar-remover").attr("href", "/removerApenado?cpf=" + cpf);
	$('#confirmModal').modal('show');
});


function handleCheckbox(chbx){
	const listaDeCheckboxesIndividuais = document.querySelectorAll("#individual-checkbox")
	if(chbx.checked == true){
		listaDeCheckboxesIndividuais.forEach(i => i.checked = true)
	} else {
		listaDeCheckboxesIndividuais.forEach(i => i.checked = false)
	}
}

// FUNCAO QUE MOSTRA O MODAL
// function setModal(modalId, nomeDoApenado, cpfApenado){
// 	const modal = document.getElementById(modalId);
// 	document.getElementById("mensagem-de-confirmacao").innerText = `Você tem certeza que deseja excluir o registro de ${nomeDoApenado} definitivamente?`;
// 	modal.classList.add("mostrar");
// 	modal.addEventListener("click", (e) => {
// 		if(e.target.id == modalId || e.target.className == "fechar" || e.target.className == "botao-nao"){
// 			modal.classList.remove("mostrar")
// 		} else if(e.target.className == "botao-sim"){
// 			window.location.assign("www.google.com");
// 		}
// 	})
// }
//
//
// //tem que trocar o ".filtrar" por classe/id que tu colocar na imagem de lixeira
// const excluir = document.querySelectorAll("#botao-remover")
// excluir.forEach((i) => {
// 	i.addEventListener("click", function(){
// 		let nomeDoApenado = i.getAttribute("data-nome");
// 		let cpfApenado = i.getAttribute("data-cpf");
// 		setModal("modal", nomeDoApenado, cpfApenado);
// 	})
// })


