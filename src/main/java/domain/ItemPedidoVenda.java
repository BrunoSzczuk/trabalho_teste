package domain;

import enums.EstadoDeFaturamento;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;


@Data
@ToString
public class ItemPedidoVenda {
    Integer sequencia;
    PedidoVenda pedidoVenda;
    Item item;
    BigDecimal quantidade;
    BigDecimal unitario;
    BigDecimal total;
    @Builder.Default
    EstadoDeFaturamento estadoDeFaturamento = EstadoDeFaturamento.ABERTO;
    @Builder.Default
    BigDecimal quantidadeFaturada = BigDecimal.ZERO;
    BigDecimal saldo;

    @Builder
    public ItemPedidoVenda(Integer sequencia, PedidoVenda pedidoVenda, Item item, BigDecimal quantidade, BigDecimal unitario) {
        this.sequencia = sequencia;
        this.pedidoVenda = pedidoVenda;
        this.item = item;
        this.quantidade = quantidade;
        this.unitario = unitario;
        if (quantidade != null && unitario != null) {
            this.total = unitario.multiply(quantidade);
        }
        if (saldo == null) {
            this.saldo = quantidade;
        }
    }

    public void fatura(BigDecimal quantidade) {
        if (estadoDeFaturamento.equals(EstadoDeFaturamento.CANCELADO)) {
            throw new RuntimeException("Não é possível faturar um item que está cancelado");
        }
        if (saldo != null && saldo.compareTo(BigDecimal.ZERO) > 0) {
            //Se a quantidade faturada for maior que o saldo, tem que dar ruim
            if (quantidade.compareTo(saldo) > 0) {
                throw new RuntimeException("Não existe saldo suficiente para faturar " + quantidade + " unidades");
            }
            quantidadeFaturada = quantidadeFaturada.add(quantidade);
            saldo = saldo.subtract(quantidade);
            atualizaStatus();
        }
    }

    private void atualizaStatus() {
        if (!estadoDeFaturamento.equals(EstadoDeFaturamento.CANCELADO)) {
            if (saldo.compareTo(BigDecimal.ZERO) == 0) {
                estadoDeFaturamento = EstadoDeFaturamento.FATURADO;
            } else if (saldo.compareTo(BigDecimal.ZERO) > 0) {
                estadoDeFaturamento = EstadoDeFaturamento.ABERTO;
            }
        }
    }

    public void cancela() {
        estadoDeFaturamento = EstadoDeFaturamento.CANCELADO;
    }


    public void devolve(BigDecimal quantidade) {
        if (saldo != null && saldo.add(quantidade).compareTo(this.quantidade) > 0) {
            throw new RuntimeException("A quantidade devolvida não pode ultrapassar a quantidade vendida");
        }
        quantidadeFaturada = quantidadeFaturada.subtract(quantidade);
        saldo = saldo.add(quantidade);
        atualizaStatus();
    }

}
