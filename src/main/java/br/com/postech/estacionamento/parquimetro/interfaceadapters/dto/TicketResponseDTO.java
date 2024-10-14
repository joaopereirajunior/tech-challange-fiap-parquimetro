package br.com.postech.estacionamento.parquimetro.interfaceadapters.dto;

import java.time.LocalDateTime;

public record TicketResponseDTO (
		
		String idTicket,
		String placaVeiculo,
		LocalDateTime horarioEntrada,
		LocalDateTime horarioSaida,
		Double valorHora,
		Double valorFinal,
		String observacao
) {}
