function handleCheckbox(chbx){
	const listaDeCheckboxesIndividuais = document.querySelectorAll("#individual-checkbox")
	if(chbx.checked == true){
		listaDeCheckboxesIndividuais.forEach(i => i.checked = true)
	} else {
		listaDeCheckboxesIndividuais.forEach(i => i.checked = false)
	}
}