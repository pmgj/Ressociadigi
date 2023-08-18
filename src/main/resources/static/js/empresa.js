//               modal voltar com fade

// document.querySelector('#botao-voltar').addEventListener('click',()=>{
//
//     let modal1 = document.querySelector('.modal-confirm');
//     let fade = document.querySelector('.fade-modal');
//     modal1.style.display='flex';
//     fade.style.display='flex';
// })
// document.querySelector('#cancelar').addEventListener('click', ()=>{
//     let modal1 = document.querySelector('.modal-confirm');
//     let fade = document.querySelector('.fade-modal');
//     modal1.style.display='none';
//     fade.style.display='none';
// })
//mudar leganda

/*
let legenda = document.querySelector('.legenda')
let select = document.querySelector('#select-tipoBusca')

function teste (){
     if (select.options[0].selected){
        legenda.innerHTML = 'dhiw3hed'
    }
     if (select.options[3].selected){
        legenda.innerHTML = '2'
    }

} 
teste() 
*/

//Modal de Filtro
$(document).on("click", "#icone-filtro", function () {
    $('#filtroModal').modal('show');
});


$(document).on("click", "#botao-remover", function () {
    var nome = $(this).data('nome');
    $("#confirmNome").text(nome);
    var cnpj = $(this).data('cnpj');
    $("#botao-confirmar-remover").attr("href", "/removerEmpresa?cnpj=" + cnpj);
    $('#confirmModal').modal('show');
});


$(document).on("click", "#ver-mais", function () {

    var cnpj = $(this).data('cnpj');
    getUserDetails(cnpj)
});



function getUserDetails(cnpj) {
    $.ajax({
        url: '/detalharEmpresa?cnpj=' + encodeURIComponent(cnpj),
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

function exibirDetalhesUsuario(cnpj) {
    // Manipule os dados do usuário retornado e exiba em um modal ou em qualquer outro lugar desejado na página
    $('#modal-detalhamento').html(cnpj);
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
