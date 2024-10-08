package br.com.postech.estacionamento.parquimetro.interfaceadapters;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.estacionamento.parquimetro.domain.service.TicketService;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketRequestDTO;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Parquímetro", description = "Operações relacionadas a parquímetro.")
public class TicketController {

	private final TicketService ticketService;
	
	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
    @Operation(description = "Efetua a criação/inicialização de um ticket e retorna os dados do mesmo.")
    @PostMapping("/criar")
    public ResponseEntity<Optional<TicketResponseDTO>> criar(@RequestBody @Valid TicketRequestDTO ticketRequestDTO) {
        var ticketResponseDTO = this.ticketService.criarTicket(ticketRequestDTO);
    	return ResponseEntity.ok(ticketResponseDTO);
    }

    @Operation(description = "Finaliza/encerra o ticket, calcula o valor do período utilizado e retorna os dados do mesmo.")
	@PatchMapping("/encerrar")
    public ResponseEntity<Optional<TicketResponseDTO>> encerrar(@RequestBody @Valid TicketRequestDTO ticketRequestDTO) {
        var ticketResponseDTO = this.ticketService.encerrarTicket(ticketRequestDTO);
        return ResponseEntity.ok(ticketResponseDTO);
    }

    @Operation(description = "Consulta por placa: Lista todos os ticktes de um determinado veículo.")
	@GetMapping("/consultar-por-placa/{placa}")
    public ResponseEntity<Optional<List<TicketResponseDTO>>> consultar(@PathVariable(required = true) String placa) {
        var ticketResponseDTO = this.ticketService.consultarTicketPorPlaca(placa);
        return ResponseEntity.ok(ticketResponseDTO);
    }

    @Operation(description = "Lista todos os tickes da base de dados.")
	@GetMapping("/consultar")
    public ResponseEntity<Optional<List<TicketResponseDTO>>> consultarTickets() {
        var tickets = this.ticketService.consultarTickets();
        return ResponseEntity.ok(tickets);
    }
}