package databuilder;

import domain.Item;
import domain.ItemPedidoVenda;

import java.math.BigDecimal;

public class ItemPedidoVendaDataBuilder {

    public static ItemPedidoVenda geraItemComQuantidadeEValor(BigDecimal quantiade, BigDecimal valorUnitario, Item item){
        return ItemPedidoVenda.builder().quantidade(quantiade).unitario(valorUnitario).item(item).build();
    }
}
