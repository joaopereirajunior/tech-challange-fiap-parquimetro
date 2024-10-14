package br.com.postech.estacionamento.parquimetro.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Getter
@Document
public class Ticket {

	@Id
    private String idTicket;
	@TextIndexed // Indica que o campo é indexado para pesquisas melhorando a performance
    private String placaVeiculo;
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;
    private Double valorHora;
    private Double valorFinal;
    private String observacao;
    public Ticket() {}

	public Ticket(String placaVeiculo, LocalDateTime horarioEntrada, LocalDateTime horarioSaida,
			Double valorHora, Double valorFinal, String observacao) {
		this.placaVeiculo = placaVeiculo;
		this.horarioEntrada = horarioEntrada;
		this.horarioSaida = horarioSaida;
		this.valorHora = valorHora;
		this.valorFinal = valorFinal;
		this.observacao = observacao;
	}

	public String getIdTicket() {
		return idTicket;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public LocalDateTime getHorarioEntrada() {
		return horarioEntrada;
	}

	public void setHorarioEntrada(LocalDateTime horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}

	public LocalDateTime getHorarioSaida() {
		return horarioSaida;
	}

	public String getObservacao(){
		return observacao;
	}
	
	public void setHorarioSaida(LocalDateTime horarioSaida) {
		this.horarioSaida = horarioSaida;
	}

	public Double getValorHora() {
		return valorHora;
	}

	public void setValorHora(Double valorHora) {
		this.valorHora = valorHora;
	}

	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}
	
	public void setObservacao(String observacao){
		this.observacao = observacao;
	}
}
