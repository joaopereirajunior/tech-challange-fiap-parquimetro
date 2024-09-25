package br.com.postech.estacionamento.parquimetro.domain.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.postech.estacionamento.parquimetro.domain.model.Ticket;
import br.com.postech.estacionamento.parquimetro.domain.repository.TicketRepository;
import br.com.postech.estacionamento.parquimetro.domain.service.TicketService;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketResponseDTO;
import br.com.postech.estacionamento.parquimetro.utils.Validador;

@Service
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;
	private final MongoTemplate mongoTemplate;

	@Value("${postech.estacionamento.parquimetro.valorHora}")
	private Double valorHora;

	public TicketServiceImpl(TicketRepository ticketRepository, MongoTemplate mongoTemplate) {
		this.ticketRepository = ticketRepository;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public ResponseEntity<?> criarTicket(String placaVeiculo) {
		try {

			// Verifica se a formatacao da placa é válida
			if (!Validador.validaPlaca(placaVeiculo)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A placa " + placaVeiculo
						+ " é inválida. Apenas os formatos ABC1234 e ABC1D23 são permitidos.");
			}

			// Verifica se o valor de hora foi parametrizado corretamente
			if (valorHora <= 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						"O valor da hora deve ser informado de acordo. Verifique o arquivo de propriedades (postech.estacionamento.parquimetro.valorHora)");
			}

			// Verifica se o ticket ja esta ativo (veiculo estacionado)
			if (consultarTicketAbertoPorPlaca(placaVeiculo)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A placa " + placaVeiculo
						+ " já esta cadastrada.");
			}

			// Cria a instancia do objeto Ticket
			// A opção em atribuição de propriedades ao inves da utilização do contrutor foi
			// para facilitar o entendimento
			Ticket ticket = new Ticket();

			ticket.setPlacaVeiculo(placaVeiculo.toUpperCase());
			ticket.setValorHora(valorHora);
			ticket.setHorarioEntrada(LocalDateTime.now());

			// Persiste o objeto em base
			this.ticketRepository.save(ticket);

			// Converte o objeto para o formato de saida
			TicketResponseDTO ticketResponseDTO = converterParaDTO(ticket);

			return ResponseEntity.status(HttpStatus.CREATED).body(ticketResponseDTO);

		} catch (Exception e) {
			// Em caso de erro não tratado retorna a mensagem
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> encerrarTicket(String idTicket) {
		try {
			// Caso seja feita uma exclusão a nível de base o metodo seria este abaixo
			// Entretanto faremos a exclusão de forma lógica a fim de manter o histórico
			// this.ticketRepository.deleteById(ticketId);

			// Realiza o encerramento em forma logica
			Ticket ticket = this.ticketRepository.findTicketByIdTicket(idTicket);

			// VPesquisa o ticket a ser encerrado
			if (ticket == null || ticket.getHorarioSaida() != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Não foi possível realizar o encerramento. O ticket de Id " +
								idTicket + " não foi encontrado ou já esta encerrado.");
			}

			// Aplica as regras de encerramento
			ticket.setHorarioSaida(LocalDateTime.now());

			// Calcula o tempo de permanencia (minimo de 1 hora, ultrapassado a hora fechada
			// é cobrado horas adicionais)
			var duracaoTicketEmHoras = Duration.between(ticket.getHorarioEntrada(), ticket.getHorarioSaida())
					.toHours() + 1;
			ticket.setValorFinal(ticket.getValorHora() * duracaoTicketEmHoras);

			// TODO: Discutir esta variavel e o momento que atualizaremos
			ticket.setPago(true);

			// Persiste o objeto em base
			this.ticketRepository.save(ticket);

			return ResponseEntity.status(HttpStatus.OK).body("O ticket de Id " + idTicket + " e placa "
					+ ticket.getPlacaVeiculo() + " foi encerrado com sucesso!");

		} catch (Exception e) {
			// Em caso de erro não tratado retorna a mensagem
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao encerrar " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> consultarTicketPorPlaca(String placa) {

		Ticket ticket = this.ticketRepository.findTicketByPlacaVeiculo(placa);

		if (ticket == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("O ticket com a placa " + placa + " não foi encontrado");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(converterParaDTO(ticket));
		}
	}

	@Override
	public Boolean consultarTicketAbertoPorPlaca(String placa) {
		Criteria criteria = new Criteria();

		// Monta filtro por placa e por horario de saida
		criteria.and("placaVeiculo").is(placa);
		criteria.and("horarioSaida").isNull();

		return this.mongoTemplate.exists(new Query(criteria), Ticket.class);
	}

	@Override
	public ResponseEntity<?> consultarTickets() {
		Criteria criteria = new Criteria();

		// Monta filtro por placa e por horario de saida
		criteria.and("horarioSaida").isNull();

		List<TicketResponseDTO> tickets = new ArrayList<>();

		for (Ticket ticket : this.ticketRepository.findAll()) {
			tickets.add(converterParaDTO(ticket));
		}

		if (tickets.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(tickets);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Não foram encontrados tickets ativos");
		}

	}

	private TicketResponseDTO converterParaDTO(Ticket ticket) {
		if (ticket == null) {
			return null;
		}

		return new TicketResponseDTO(ticket.getIdTicket(), ticket.getPlacaVeiculo(),
				ticket.getHorarioEntrada(), ticket.getHorarioSaida(), ticket.getValorHora(),
				ticket.getValorFinal(), ticket.isPago());
	}

}
