package databuilder;

import domain.Pessoa;

public class PessoaDataBuilder {
    public static Pessoa getPessoaDeTeste() {
        return Pessoa.builder().idade(21).nome("Bruno Henrique Pereira Szczuk").build();
    }
}
