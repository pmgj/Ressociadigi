package application.vaga;

import application.empresa.Empresa;

public class VagaDTO {
    private String empresa;
    private String tipo;
    private String quantidadeVagasMasculinas;
    private String quantidadeVagasFemininas;
    private String cargaHoraria;



    //Métodos
    public String getEmpresa() {
        return empresa;
    }
    public void setEmpresa(String nome) {
        if(nome == null || nome.isEmpty()){
            this.empresa = null;
        }else{
            this.empresa = nome;
        }
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        if(tipo == null || tipo.isEmpty()){
            this.tipo = null;
        }else{
            this.tipo = tipo;
        }
    }

    public String getQuantidadeVagasMasculinas() {

            return this.quantidadeVagasMasculinas;

    }
    public void setQuantidadeVagasMasculinas(String quantidadeVagasMasculinas) {
        if(quantidadeVagasMasculinas == null || quantidadeVagasMasculinas.isEmpty()){
            this.quantidadeVagasMasculinas = null;
        }else{
            this.quantidadeVagasMasculinas = quantidadeVagasMasculinas;
        }
    }


    public String getQuantidadeVagasFemininas() {

            return this.quantidadeVagasFemininas;

    }
    public void setQuantidadeVagasFemininas(String quantidadeVagasFemininas) {
        if(quantidadeVagasFemininas == null || quantidadeVagasFemininas.isEmpty()){
            this.quantidadeVagasFemininas = null;
        }else{
            this.quantidadeVagasFemininas = quantidadeVagasFemininas;
        }
    }


    public String getCargaHoraria() {

            return this.cargaHoraria;

    }
    public void setCargaHoraria(String cargaHoraria) {
        if(cargaHoraria == null || cargaHoraria.isEmpty()){
            this.cargaHoraria = null;
        }else{
            this.cargaHoraria = cargaHoraria;
        }
    }
}
