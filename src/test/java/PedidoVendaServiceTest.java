import databuilder.ItemDataBuilder;
import databuilder.ItemPedidoVendaDataBuilder;
import databuilder.PessoaDataBuilder;
import domain.ItemPedidoVenda;
import domain.Pessoa;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import enums.EstadoDeFaturamento;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class PedidoVendaServiceTest {

    PedidoVendaService service;

    @Before
    public void init() {
        service = new PedidoVendaService();
        service.init();
    }

    @Test(expected = RuntimeException.class)
    public void tenta_adicionar_item_de_pedido_sem_quantidade_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(BigDecimal.ZERO, BigDecimal.TEN, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
    }

    @Test(expected = RuntimeException.class)
    public void tenta_adicionar_item_de_pedido_sem_valor_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(BigDecimal.ONE, null, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
    }

    @Test(expected = RuntimeException.class)
    public void tenta_adicionar_item_de_pedido_sem_item_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(BigDecimal.ONE, BigDecimal.TEN, null);
        service.adicionaItem(item);
    }

    @Test(expected = RuntimeException.class)
    public void tentar_adicionar_pessoa_fail() {
        service.colocaPessoa(null);
    }


    @Test(expected = RuntimeException.class)
    public void tentar_salvar_pedido_sem_pessoa_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(BigDecimal.ONE, BigDecimal.TEN, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
        service.salvarPedido();
    }

    @Test(expected = RuntimeException.class)
    public void tenta_adicionar_item_com_quantidade_negativa_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(BigDecimal.ONE, new BigDecimal(-50), ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
    }

    @Test(expected = RuntimeException.class)
    public void tenta_adicionar_item_com_valor_negativo_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(-50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
    }

    @Test(expected = RuntimeException.class)
    public void tenta_atualizar_item_nao_adicionado_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.atualizaItem(item);
    }

    @Test
    public void tenta_atualizar_item_adicionado() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
        item.fatura(BigDecimal.TEN);
        service.atualizaItem(item);
        assertThat(item.getSaldo(), equalTo(new BigDecimal(40)));
        assertThat(item.getEstadoDeFaturamento(), equalTo(EstadoDeFaturamento.ABERTO));
    }


    @Test(expected = RuntimeException.class)
    public void fatura_item_mais_que_o_saldo_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
        item.fatura(new BigDecimal(500));
        service.atualizaItem(item);
    }

    @Test
    public void fatura_item_completo() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
        item.fatura(new BigDecimal(50));
        service.atualizaItem(item);
        assertThat(item.getSaldo(), equalTo(BigDecimal.ZERO));
        assertThat(item.getEstadoDeFaturamento(), equalTo(EstadoDeFaturamento.FATURADO));
    }

    @Test(expected = RuntimeException.class)
    public void tenta_devolver_mais_do_que_a_quantidade_vendida_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
        item.devolve(new BigDecimal(1500));
        service.atualizaItem(item);
    }

    @Test(expected = RuntimeException.class)
    public void tenta_devolver_quantidade_sem_faturar_fail() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
        item.devolve(new BigDecimal(10));
    }

    @Test
    public void tenta_devolver_quantidade() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
        item.fatura(new BigDecimal(30));
        service.atualizaItem(item);
        item.devolve(new BigDecimal(10));
        service.atualizaItem(item);
        assertThat(item.getEstadoDeFaturamento(), equalTo(EstadoDeFaturamento.ABERTO));
        assertThat(item.getQuantidadeFaturada(), equalTo(new BigDecimal(20)));
        assertThat(item.getSaldo(), equalTo(new BigDecimal(30)));
    }

    @Test(expected = RuntimeException.class)
    public void tenta_faturar_item_cancelado() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(new BigDecimal(50), BigDecimal.ONE, ItemDataBuilder.getItemTeste());
        service.adicionaItem(item);
        item.cancela();
        item.fatura(BigDecimal.TEN);
    }

    @Test
    public void tentar_salvar_pedido() {
        ItemPedidoVenda item = ItemPedidoVendaDataBuilder.geraItemComQuantidadeEValor(BigDecimal.ONE, BigDecimal.TEN, ItemDataBuilder.getItemTeste());
        Pessoa pessoa = PessoaDataBuilder.getPessoaDeTeste();
        service.adicionaItem(item);
        service.colocaPessoa(pessoa);
        service.salvarPedido();
    }

}