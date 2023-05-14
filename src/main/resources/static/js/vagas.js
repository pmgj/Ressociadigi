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