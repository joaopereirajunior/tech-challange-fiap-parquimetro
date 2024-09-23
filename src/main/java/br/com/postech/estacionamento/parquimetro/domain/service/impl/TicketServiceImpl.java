package br.com.postech.estacionamento.parquimetro.domain.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.postech.estacionamento.parquimetro.domain.model.Ticket;
import br.com.postech.estacionamento.parquimetro.domain.repository.TicketRepository;
import br.com.postech.estacionamento.parquimetro.domain.service.TicketService;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketRequestDTO;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketResponseDTO;

@Service
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;
	
	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}
	
	@Override
	public TicketResponseDTO criarTicket(TicketRequestDTO ticketRequestDTO) {
		
		Ticket ticket = new Ticket();
		ticket.setPlacaVeiculo(ticketRequestDTO.placaVeiculo());
		ticket.setValorHora(ticketRequestDTO.valorHora());
		
		// Regras
		ticket.setHorarioEntrada(LocalDateTime.now());
		ticket.setHorarioSaida(null);
		ticket.setPago(false);
		
		ticket.setValorFinal(null);
		
		ticketRepository.save(ticket);
		
		TicketResponseDTO ticketResponseDTO = converterParaDTO(ticket);
		
		return ticketResponseDTO;
	}

	@Override
	public TicketResponseDTO encerrarTicket(String ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TicketResponseDTO> consultarTicketPorPlaca(String placa) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	private TicketResponseDTO converterParaDTO(Ticket ticket) {
		return new TicketResponseDTO(ticket.getIdTicket(), ticket.getPlacaVeiculo(),
				ticket.getHorarioEntrada(), ticket.getHorarioSaida(), ticket.getValorHora(),
				ticket.getValorFinal(), ticket.isPago());
	}

}
