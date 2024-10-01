package br.com.postech.estacionamento.parquimetro.interfaceadapters.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TicketRequestDTO (
	@NotNull(message = "A placa do veículo não pode ser nula.")
	@NotBlank(message = "A placa do veículo não pode estar em branco.")
	@Pattern(regexp="^[a-zA-Z]{3}[0-9][A-Za-z0-9][0-9]{2}$", message = "A placa informada é inválida. Apenas os formatos ABC1234 e ABC1D23 são permitidos.")
	String placaVeiculo
) {}
