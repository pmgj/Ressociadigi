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