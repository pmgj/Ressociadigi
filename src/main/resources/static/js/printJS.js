const dataDate = document.querySelector("#dataHoraApresentation");

dataDate.addEventListener("change", ()=>{
   let stringDataDate = dataDate.value;
   let mes = stringDataDate.slice(5,7);
   let dia = stringDataDate.slice(8,10);
   let hora = stringDataDate.slice(11);
   document.querySelector("#diaApresentacao").innerHTML = dia;
   document.querySelector("#mesApresentacao").innerHTML = mes;
   document.querySelector("#horaApresentacao").innerHTML = hora;
});