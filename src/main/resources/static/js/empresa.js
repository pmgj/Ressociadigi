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

//Funcionamento de modal do icone de filtro

var iconeFiltro = document.querySelector('#icone-filtro');

var modal = document.querySelector('.modal');

var closeBtn = document.querySelector('.close');




// Add click event listener to the image element
iconeFiltro.addEventListener('click', ()=> {
    modal.style.display = 'block';
});

closeBtn.addEventListener('click', ()=> {
    modal.style.display = 'none';
});

modal.addEventListener('click', function(event) {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

$(document).on("click", "#botao-remover", function () {
    var nome = $(this).data('nome');
    $("#confirmNome").text(nome);
    var cnpj = $(this).data('cnpj');
    $("#botao-confirmar-remover").attr("href", "/removerEmpresa?cnpj=" + cnpj);
    $('#confirmModal').modal('show');
});


