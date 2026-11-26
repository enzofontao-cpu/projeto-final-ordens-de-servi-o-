package br.senai.sistema.model;

public enum TipoOcorrencia {
    DEFEITO("Possível defeito"),
    MELHORIA("Sugestão de melhoria");

    private final String descricao;

    TipoOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
