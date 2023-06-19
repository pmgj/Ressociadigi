//Modal de Filtro
$(document).on("click", "#icone-filtro", function () {
    $('#filtroModal').modal('show');
});

$(document).on("click", "#botao-remover", function () {
    var nome = $(this).data('nome');
    $("#confirmNome").text(nome);
    var id = $(this).data('id');
    $("#botao-confirmar-remover").attr("href", "/removerVaga?id=" + id);
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

$(document).on("click", "#ver-mais", function () {

    var id = $(this).data('id');
    getVagaDetails(id)
});



function getVagaDetails(id) {
    $.ajax({
        url: '/detalharVaga?id=' + encodeURIComponent(id),
        type: 'GET',
        success: function(response) {
            // Manipule o objeto de usuário retornado

            // Chame a função para exibir os detalhes do usuário em um modal ou em qualquer outro lugar desejado
            exibirDetalhesUsuario(response);
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}

function exibirDetalhesUsuario(vaga) {
    // Manipule os dados do usuário retornado e exiba em um modal ou em qualquer outro lugar desejado na página
    $('#modal-detalhamento').html(vaga);
    $('#modal-detalhamento').css('display', 'block');
    $('#detalhamento-fade').css('display', 'flex');

    $('#btn-voltar-detalhamento').click(()=>{
        $('#modal-detalhamento').css('display', 'none');
        $('#detalhamento-fade').css('display', 'none');
    });
    // Exemplo de inserção dos dados em um modal usando Bootstrap

}

