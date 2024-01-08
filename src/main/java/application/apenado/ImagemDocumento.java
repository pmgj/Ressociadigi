package application.apenado;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ImagemDocumento {


    @Column(insertable = false, updatable = false)
    private String enderecoDoDiretorio;
    @Column(insertable = false, updatable = false)
    private TipoDeDocumento tipoDeDocumento;





    public ImagemDocumento(){}
    public ImagemDocumento(String endereco, TipoDeDocumento tipoDeDocumento){
        this.enderecoDoDiretorio = endereco;
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public String getEnderecoDoDiretorio() {
        return enderecoDoDiretorio;
    }

    public void setEnderecoDoDiretorio(String enderecoDoDiretorio) {
        this.enderecoDoDiretorio = enderecoDoDiretorio;
    }

    public TipoDeDocumento getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }
}
