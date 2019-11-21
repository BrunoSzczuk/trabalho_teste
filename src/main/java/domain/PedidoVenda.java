package domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoVenda {

    Pessoa pessoa;
    Date emissao;
    BigDecimal total;
    List<ItemPedidoVenda> itens = new ArrayList<ItemPedidoVenda>();
}
