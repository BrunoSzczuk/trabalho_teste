package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EstadoDeFaturamento {
    FATURADO("Faturado"),
    CANCELADO("Cancelado"),
    ABERTO("Aberto");
    @Getter
    String nome;

}
