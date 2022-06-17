package apenado;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Apenado {

    @Id
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio")
    @Pattern(regexp = "[0-9]{11}", message = "CPF deve possuir 11 dígitos")
    private String cpf;

    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio")
    private String nome;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}
