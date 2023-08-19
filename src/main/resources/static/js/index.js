const icon = document.querySelectorAll(".caixa");
const dica = document.querySelectorAll(".dicas");

function dicasAparecem (index){
    dica[index].style.display = "flex";
}
function dicasSomem (index){
    dica[index].style.display = "none";
}

icon.forEach((i, index)=>{
    i.addEventListener("mouseenter", ()=>{
        dicasAparecem(index);
    })
    i.addEventListener("mouseleave", ()=>{
        dicasSomem(index);
    })
})