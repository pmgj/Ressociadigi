package apenado;

public enum Restricao {
    SIM("Sim"), NAO("Não");

    private String texto;

    Restricao(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
