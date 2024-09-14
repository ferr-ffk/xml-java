package model;

import java.util.Objects;

public class Pessoa {
    private String idPessoa;
    private String nome;
    private String sobrenome;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String nacionalidade;

    
    public Pessoa() {}

    public Pessoa(String nome, String sobrenome, String email, String logradouro, String numero) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.logradouro = logradouro;
        this.numero = numero;
    }

    
    public Pessoa(String idPessoa, String nome, String sobrenome, String email, String logradouro, String numero, String bairro, String cidade, String estado, String pais, String nacionalidade) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.nacionalidade = nacionalidade;
    }

    // Getters e Setters
    public String getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(String idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return idPessoa == pessoa.idPessoa && Objects.equals(nome, pessoa.nome) && Objects.equals(sobrenome, pessoa.sobrenome) && Objects.equals(email, pessoa.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPessoa, nome, sobrenome, email);
    }

    @Override
    public String toString() {
        return "{"
                + "\"idPessoa\":" + idPessoa + ","
                + "\"nome\":\"" + nome + "\","
                + "\"sobrenome\":\"" + sobrenome + "\","
                + "\"email\":\"" + email + "\","
                + "\"logradouro\":\"" + logradouro + "\","
                + "\"numero\":\"" + numero + "\","
                + "\"bairro\":\"" + bairro + "\","
                + "\"cidade\":\"" + cidade + "\","
                + "\"estado\":\"" + estado + "\","
                + "\"pais\":\"" + pais + "\","
                + "\"nacionalidade\":\"" + nacionalidade + "\""
        + "}";
    
    }
}