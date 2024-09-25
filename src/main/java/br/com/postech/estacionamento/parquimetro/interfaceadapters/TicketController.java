package br.com.postech.estacionamento.parquimetro.interfaceadapters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.estacionamento.parquimetro.domain.service.TicketService;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketRequestDTO;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	private final TicketService ticketService;
	
	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
    @PostMapping("/criar/{placaVeiculo}")
    public ResponseEntity<?> criar(@PathVariable String placaVeiculo) {
    	return this.ticketService.criarTicket(placaVeiculo);
    }

	@DeleteMapping("/encerrar/{idTicket}")
    public ResponseEntity<?> encerrar(@PathVariable String idTicket) {
        return this.ticketService.encerrarTicket(idTicket);
    }

	@GetMapping("/consultar/{placaVeiculo}")
    public ResponseEntity<?> consultar(@PathVariable String placaVeiculo) {
        return this.ticketService.consultarTicketPorPlaca(placaVeiculo);
    }

	@GetMapping("/consultar")
    public ResponseEntity<?> consultarTickets() {
        return this.ticketService.consultarTickets();
    }
}
