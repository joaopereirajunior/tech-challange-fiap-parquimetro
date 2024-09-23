package br.com.postech.estacionamento.parquimetro.domain.service;

import java.util.Optional;

import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketRequestDTO;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketResponseDTO;

public interface TicketService {
	
    /**
     * Cria um novo Ticket.
     *
     * @param TicketRequestDTO a entidade a ser criada.
     * @return TicketResponseDTO com a entidade criada.
     */
	public TicketResponseDTO criarTicket(TicketRequestDTO ticketRequestDTO);

    /**
     * Encerra o ciclo do Ticket em aberto.
     *
     * @param ticketId informando o Ticket a ser encerrado.
     * @return TicketResponseDTO com a entidade encerrada.
     */
	public TicketResponseDTO encerrarTicket(String ticketId);
	
    /**
     * Retorna um ticket específico através de um ticketId informado.
     *
     * @param placa informando a placa do veículo a ser buscado.
     * @return um Optional contendo o registro encontrado, ou vazio se não encontrado.
     */
	public Optional<TicketResponseDTO> consultarTicketPorPlaca(String placa);
	
}
