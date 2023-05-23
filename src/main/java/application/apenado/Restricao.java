package application.apenado;

public enum Restricao {
    SIM("Sim"), NAO("Não");

    private String texto;

    Restricao(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
