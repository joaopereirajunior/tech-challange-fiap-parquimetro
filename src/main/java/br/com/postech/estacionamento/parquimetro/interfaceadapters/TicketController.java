package br.com.postech.estacionamento.parquimetro.interfaceadapters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.estacionamento.parquimetro.domain.service.TicketService;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketRequestDTO;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketResponseDTO;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	private final TicketService ticketService;
	
	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
    @PostMapping
    public ResponseEntity<TicketResponseDTO> criar(@RequestBody TicketRequestDTO ticketRequestDTO) {
    	TicketResponseDTO clienteCriado = ticketService.criarTicket(ticketRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }
}
