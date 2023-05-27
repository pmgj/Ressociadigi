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