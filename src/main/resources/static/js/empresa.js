
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


