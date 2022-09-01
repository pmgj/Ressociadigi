package apenado;

public enum Prioridade {
    ALTA("Alta"), MEDIA("Média"), BAIXA("Baixa");

    private String texto;

    Prioridade(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
