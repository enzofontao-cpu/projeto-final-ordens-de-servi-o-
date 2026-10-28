package br.senai.sistema.model;

/**
 * Situações de uma ordem de serviço.
 * Um enum é um tipo com um conjunto FIXO de valores: só estes valores são aceitos.
 * Cada valor guarda também o texto mostrado na tela e a cor do selo (badge) do Bootstrap.
 */
public enum StatusOrdem {

    ABERTA("Aberta", "secondary"),
    EM_ANDAMENTO("Em andamento", "warning"),
    CONCLUIDA("Concluída", "success");

    private final String descricao;   // texto mostrado na tela
    private final String cor;         // cor do selo: success, warning, secondary...

    StatusOrdem(String descricao, String cor) {
        this.descricao = descricao;
        this.cor = cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCor() {
        return cor;
    }
}