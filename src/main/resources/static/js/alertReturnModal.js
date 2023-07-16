//               modal voltar com fade

document.querySelector('#botao-voltar').addEventListener('click',()=>{

    let modal1 = document.querySelector('.modal-confirm');
    let fade = document.querySelector('.fade-modal');
    modal1.style.display='flex';
    fade.style.display='flex';
})
document.querySelector('#cancelar').addEventListener('click', ()=>{
    let modal1 = document.querySelector('.modal-confirm');
    let fade = document.querySelector('.fade-modal');
    modal1.style.display='none';
    fade.style.display='none';
})

//validação cnpj
document.addEventListener('DOMContentLoaded', function() {
    var form = document.getElementById('formulario');
    form.addEventListener('submit', function(event) {
        var cnpjInput = document.getElementById('cnpj');
        var nomeEmpresa = document.querySelector('#nomeEmpresa')
        var cnpj = cnpjInput.value;

        if (!validarCNPJ(cnpj)) {
            event.preventDefault();
            alert('CNPJ inválido! Por favor, verifique o CNPJ inserido.');
            return false;
        }
        if(nomeEmpresa.value == ''){
            event.preventDefault();
            alert('Nome da empresa inválido! Por favor, verifique o Nome da empresa inserido.');
            return false;
        }
    });
});

function validarCNPJ(cnpj) {
    cnpj = cnpj.replace(/[^\d]+/g, '');

    if (cnpj.length !== 14 || /^(.)\1+$/.test(cnpj)) {
        return false;
    }

    var tamanho = cnpj.length - 2;
    var numeros = cnpj.substring(0, tamanho);
    var digitos = cnpj.substring(tamanho);
    var soma = 0;
    var pos = tamanho - 7;

    for (var i = tamanho; i >= 1; i--) {
        soma += parseInt(numeros.charAt(tamanho - i)) * pos--;
        if (pos < 2) {
            pos = 9;
        }
    }

    var resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);

    if (resultado !== parseInt(digitos.charAt(0))) {
        return false;
    }

    tamanho = tamanho + 1;
    numeros = cnpj.substring(0, tamanho);
    soma = 0;
    pos = tamanho - 7;

    for (var i = tamanho; i >= 1; i--) {
        soma += parseInt(numeros.charAt(tamanho - i)) * pos--;
        if (pos < 2) {
            pos = 9;
        }
    }

    resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);

    if (resultado !== parseInt(digitos.charAt(1))) {
        return false;
    }

    return true;
}
