//Modal de Filtro
$(document).on("click", "#icone-filtro", function () {
    $('#filtroModal').modal('show');
});

//Modal de Exclusao
$(document).on("click", "#botao-remover", function () {
    var id = $(this).data('id');
    $("#botao-confirmar-remover").attr("href", "/removerVagaPreenchida?id=" + id);
    $('#confirmModal').modal('show');
});

$(document).on("click", "#ver-mais", function () {
    var id = $(this).data('id');
    getVagaDetails(id)
});



function getVagaDetails(id) {
    $.ajax({
        url: '/detalharVagaPreenchida?id=' + encodeURIComponent(id),
        type: 'GET',
        success: function(response) {
            // Manipule o objeto de usuário retornado

            // Chame a função para exibir os detalhes do usuário em um modal ou em qualquer outro lugar desejado
            exibirDetalhesVagaPreenchida(response);
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}

function exibirDetalhesVagaPreenchida(vaga) {
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
