package br.com.postech.estacionamento.parquimetro.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Getter
@Document
public class Ticket {

	@Id
    private String idTicket;
    private String placaVeiculo;
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;
    private Double valorHora;
    private Double valorFinal;
    private boolean pago;
    
    public Ticket() {}

	public Ticket(String placaVeiculo, LocalDateTime horarioEntrada, LocalDateTime horarioSaida,
			Double valorHora, Double valorFinal, boolean pago) {
		this.placaVeiculo = placaVeiculo;
		this.horarioEntrada = horarioEntrada;
		this.horarioSaida = horarioSaida;
		this.valorHora = valorHora;
		this.valorFinal = valorFinal;
		this.pago = pago;
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

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}
    
}
