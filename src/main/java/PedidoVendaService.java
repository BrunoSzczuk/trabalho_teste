import domain.ItemPedidoVenda;
import domain.PedidoVenda;
import domain.Pessoa;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoVendaService {

    PedidoVenda pedidoVenda;
    List<ItemPedidoVenda> itens;


    public void init() {
        pedidoVenda = new PedidoVenda();
        itens = new ArrayList<ItemPedidoVenda>();
    }

    public void salvarPedido() {
        if (pedidoVenda == null) {
            throw new RuntimeException("Pedido não pode ser nulo");
        }
        if (itens == null || itens.isEmpty()) {
            throw new RuntimeException("Os itens devem ser informados");
        }
        if (pedidoVenda.getPessoa() == null) {
            throw new RuntimeException("A pessoa deve ser informada");
        }
        pedidoVenda.setItens(itens);
    }

    public void colocaPessoa(Pessoa pessoa) {
        if (pessoa == null) {
            throw new RuntimeException("A pessoa deve ser informada");
        }
        pedidoVenda.setPessoa(pessoa);
    }

    public void adicionaItem(ItemPedidoVenda itemPedidoVenda) {
        validaItem(itemPedidoVenda);
        itens.add(itemPedidoVenda);
    }

    public void atualizaItem(ItemPedidoVenda itemPedidoVenda) {
        validaItem(itemPedidoVenda);
        if (itens.contains(itemPedidoVenda)) {
            itens.set(itens.indexOf(itemPedidoVenda), itemPedidoVenda);
        } else {
            throw new RuntimeException("Utilize a operação de adicionar");
        }
    }

    private void validaItem(ItemPedidoVenda itemPedidoVenda) {
        if (itemPedidoVenda == null) {
            throw new RuntimeException("O item do pedido não pode ser nulo");
        }
        if (itemPedidoVenda.getItem() == null) {
            throw new RuntimeException("O item deve ser informado para o item do pedido");
        }
        if (itemPedidoVenda.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("A quantidade do produto deve ser informada");
        }
        if (itemPedidoVenda.getUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O valor do produto deve ser informado");
        }
    }


}
