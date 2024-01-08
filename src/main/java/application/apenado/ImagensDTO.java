package application.apenado;

import org.springframework.web.multipart.MultipartFile;

public class ImagensDTO {
    private MultipartFile imagemDtoCPF;
    private MultipartFile imagemDtoRG;
    private MultipartFile imagemDtoComprovanteDeResidencia;
    private MultipartFile imagemDtoContaBancaria;
    private MultipartFile imagemDtoAtestadoDePena;

    public MultipartFile getImagemDtoCPF() {
        return imagemDtoCPF;
    }

    public void setImagemDtoCPF(MultipartFile imagemDtoCPF) {
        this.imagemDtoCPF = imagemDtoCPF;
    }

    public MultipartFile getImagemDtoRG() {
        return imagemDtoRG;
    }

    public void setImagemDtoRG(MultipartFile imagemDtoRG) {
        this.imagemDtoRG = imagemDtoRG;
    }

    public MultipartFile getImagemDtoComprovanteDeResidencia() {
        return imagemDtoComprovanteDeResidencia;
    }

    public void setImagemDtoComprovanteDeResidencia(MultipartFile imagemDtoComprovanteDeResidencia) {
        this.imagemDtoComprovanteDeResidencia = imagemDtoComprovanteDeResidencia;
    }

    public MultipartFile getImagemDtoContaBancaria() {
        return imagemDtoContaBancaria;
    }

    public void setImagemDtoContaBancaria(MultipartFile imagemDtoContaBancaria) {
        this.imagemDtoContaBancaria = imagemDtoContaBancaria;
    }

    public MultipartFile getImagemDtoAtestadoDePena() {
        return imagemDtoAtestadoDePena;
    }

    public void setImagemDtoAtestadoDePena(MultipartFile imagemDtoAtestadoDePena) {
        this.imagemDtoAtestadoDePena = imagemDtoAtestadoDePena;
    }
}
