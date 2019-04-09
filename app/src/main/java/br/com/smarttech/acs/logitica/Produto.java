package br.com.smarttech.acs.logitica;

public class Produto {
    private String nome;
    private String perecivel;
    private String qtdEstoque;
    private String unidadeMedida;
    private String valorUnitario;

    public Produto() {
    }

    public Produto(String nome, String perecivel, String qtdEstoque, String unidadeMedida, String valorUnitario) {
        this.nome = nome;
        this.perecivel = perecivel;
        this.qtdEstoque = qtdEstoque;
        this.unidadeMedida = unidadeMedida;
        this.valorUnitario = valorUnitario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPerecivel() {
        return perecivel;
    }

    public void setPerecivel(String perecivel) {
        this.perecivel = perecivel;
    }

    public String getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(String qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "Produto:\n" +
                "Nome: " + nome + '\n' +
                "QtdEstoque: " + qtdEstoque + '\n' +
                "Perecivel: " + perecivel + '\n' +
                "UnidadeMedida: " + unidadeMedida + '\n' +
                "ValorUnitario: " + valorUnitario + '\n'
                ;

    }

}
