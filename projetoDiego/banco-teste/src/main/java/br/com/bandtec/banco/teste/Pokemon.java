package br.com.bandtec.banco.teste;

public class Pokemon {

    private Integer id;
    private String nome;
    private String tipo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return String.format("\nID: %d\nNOME: %s\nTIPO: %s\n",
                this.id, this.nome, this.tipo);
    }
}
