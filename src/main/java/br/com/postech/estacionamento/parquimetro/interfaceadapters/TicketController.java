package br.com.postech.estacionamento.parquimetro.interfaceadapters;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.estacionamento.parquimetro.domain.service.TicketService;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketRequestDTO;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	private final TicketService ticketService;
	
	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
    @PostMapping("/criar")
    public ResponseEntity<Optional<TicketResponseDTO>> criar(@RequestBody @Valid TicketRequestDTO ticketRequestDTO) {
        var ticketResponseDTO = this.ticketService.criarTicket(ticketRequestDTO);
    	return ResponseEntity.ok(ticketResponseDTO);
    }

	@PatchMapping("/encerrar")
    public ResponseEntity<Optional<TicketResponseDTO>> encerrar(@RequestBody @Valid TicketRequestDTO ticketRequestDTO) {
        var ticketResponseDTO = this.ticketService.encerrarTicket(ticketRequestDTO);
        return ResponseEntity.ok(ticketResponseDTO);
    }

	@PostMapping("/consultar-por-placa")
    public ResponseEntity<Optional<List<TicketResponseDTO>>> consultar(@RequestBody @Valid TicketRequestDTO ticketRequestDTO) {
        var ticketResponseDTO = this.ticketService.consultarTicketPorPlaca(ticketRequestDTO);
        return ResponseEntity.ok(ticketResponseDTO);
    }

	@GetMapping("/consultar")
    public ResponseEntity<Optional<List<TicketResponseDTO>>> consultarTickets() {
        var tickets = this.ticketService.consultarTickets();
        return ResponseEntity.ok(tickets);
    }
}
