package br.com.postech.estacionamento.parquimetro.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketRequestDTO;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketResponseDTO;

public interface TicketService {
	
    /**
     * Cria um novo Ticket.
     *
     * @param placaVeiculo a placa do ticket a ser criada
     */
	public ResponseEntity<?> criarTicket(String placaVeiculo);

    /**
     * Encerra o ciclo do Ticket em aberto.
     *
     * @param ticketId informando o Ticket a ser encerrado.
     */
	public ResponseEntity<?> encerrarTicket(String ticketId);
	
    /**
     * Retorna um ticket específico através de um ticketId informado.
     *
     * @param placa informando a placa do veículo a ser buscado.
     */
	public ResponseEntity<?> consultarTicketPorPlaca(String placa);

    /**
     * Retorna a lista de tickets ativos (estacionados)
     *
     */
	public ResponseEntity<?> consultarTickets();

    /**
     * Retorna se o ticket ja esta aberto por placa
     *
     * @param placa informando a placa do veículo a ser buscado.
     */
    public Boolean consultarTicketAbertoPorPlaca(String placa);
	
}
