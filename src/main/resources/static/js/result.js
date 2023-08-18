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


$(document).on("click", "#ver-mais", function () {

	var cpf = $(this).data('cpf');
	getUserDetails(cpf)
});

function getUserDetails(cpf) {
	$.ajax({
		url: '/detalharApenado?cpf=' + encodeURIComponent(cpf),
		type: 'GET',
		success: function(response) {
			// Manipule o objeto de usuário retornado
			console.log(response)
			console.log(response.nome);
			// Chame a função para exibir os detalhes do usuário em um modal ou em qualquer outro lugar desejado
			exibirDetalhesUsuario(response);
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}

function exibirDetalhesUsuario(user) {
	// Manipule os dados do usuário retornado e exiba em um modal ou em qualquer outro lugar desejado na página
	$('#modal-detalhamento').html(user);
	$('#modal-detalhamento').css('display', 'block');
	$('#detalhamento-fade').css('display', 'flex');

	$('#btn-voltar-detalhamento').click(()=>{
		$('#modal-detalhamento').css('display', 'none');
		$('#detalhamento-fade').css('display', 'none');
	});
	// Exemplo de inserção dos dados em um modal usando Bootstrap

}

	document.getElementById('tamanho_da_lista').addEventListener('change', function(evt) {
		evt.currentTarget.parentNode.submit();
	});



