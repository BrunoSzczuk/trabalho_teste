package domain;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Pessoa {
    String nome;
    Integer idade;
}
