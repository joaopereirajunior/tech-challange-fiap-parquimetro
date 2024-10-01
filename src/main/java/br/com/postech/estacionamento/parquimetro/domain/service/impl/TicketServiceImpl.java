package br.com.postech.estacionamento.parquimetro.domain.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.com.postech.estacionamento.parquimetro.domain.model.Ticket;
import br.com.postech.estacionamento.parquimetro.domain.repository.TicketRepository;
import br.com.postech.estacionamento.parquimetro.domain.service.TicketService;
import br.com.postech.estacionamento.parquimetro.infrastructure.handler.EntityNotFoundException;
import br.com.postech.estacionamento.parquimetro.interfaceadapters.dto.TicketRequestDTO;
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
	public Optional<TicketResponseDTO> criarTicket(TicketRequestDTO ticketRequestDTO) {
		try {

			var placaVeiculo = ticketRequestDTO.placaVeiculo();

			// Verifica se o valor de hora foi parametrizado corretamente
			if (valorHora <= 0) {
				throw new RuntimeException("O valor da hora deve ser informado de acordo. Verifique o arquivo de propriedades (postech.estacionamento.parquimetro.valorHora)");
			}

			// Verifica se o ticket ja esta ativo (veiculo estacionado)
			if (consultarTicketAbertoPorPlaca(ticketRequestDTO)) {
				throw new RuntimeException("A placa " + placaVeiculo + " já esta cadastrada.");
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

			return Optional.of(ticketResponseDTO);

		} catch (Exception e) {
			// Em caso de erro não tratado retorna a mensagem
			throw new RuntimeException("Erro ao criar o ticket: " + e.getMessage());
		}
	}

	@Override
	public Optional<TicketResponseDTO> encerrarTicket(TicketRequestDTO ticketRequestDTO) {
		try {
			// Caso seja feita uma exclusão a nível de base o metodo seria este abaixo
			// Entretanto faremos a exclusão de forma lógica a fim de manter o histórico
			// this.ticketRepository.deleteById(ticketId);

			Criteria criteria = new Criteria();
			var placaVeiculo = ticketRequestDTO.placaVeiculo();

			// Monta filtro por placa e por horario de saida
			criteria.and("placaVeiculo").is(placaVeiculo);
			criteria.and("horarioSaida").isNull();
			
			// Realiza o encerramento em forma logica
			Ticket ticket = this.mongoTemplate.findOne(new Query(criteria), Ticket.class);
			
			// VPesquisa o ticket a ser encerrado
			if (ticket == null || ticket.getHorarioSaida() != null) {
				throw new RuntimeException("Não foi possível realizar o encerramento. O veículo com placa " +
				placaVeiculo + " não foi encontrado ou já esta encerrado.");
			}

			// Aplica as regras de encerramento
			ticket.setHorarioSaida(LocalDateTime.now());

			// Calcula o tempo de permanencia (minimo de 1 hora, ultrapassado a hora fechada
			// é cobrado horas adicionais)
			var duracaoTicketEmHoras = Duration.between(ticket.getHorarioEntrada(), ticket.getHorarioSaida())
					.toHours() + 1;
			ticket.setValorFinal(ticket.getValorHora() * duracaoTicketEmHoras);

			// Persiste o objeto em base
			this.ticketRepository.save(ticket);

			var mensagem ="O ticket para a placa " + placaVeiculo + " foi encerrado com sucesso!";

			var ticketDTO = converterParaDTO(ticket, mensagem);

			return Optional.of(ticketDTO);

		} catch (Exception e) {
			// Em caso de erro não tratado retorna a mensagem
			throw new RuntimeException("Erro ao encerrar " + e.getMessage());
		}
	}

	@Override
	public Optional<List<TicketResponseDTO>> consultarTicketPorPlaca(String placa) {

		if(!Validador.validaPlaca(placa))
			throw new RuntimeException("A placa informada é inválida. Apenas os formatos ABC1234 e ABC1D23 são permitidos.");

		var tickets = this.ticketRepository.findTicketByPlacaVeiculo(placa);

		if (tickets == null || tickets.size() == 0) {
			throw new EntityNotFoundException("O ticket com a placa " + placa + " não foi encontrado");
		} else {
			return Optional.of(tickets.stream().map(p -> converterParaDTO(p)).collect(Collectors.toList()));
		}
	}

	@Override
	public Boolean consultarTicketAbertoPorPlaca(TicketRequestDTO ticketRequestDTO) {
		var placa = ticketRequestDTO.placaVeiculo();
		Criteria criteria = new Criteria();

		// Monta filtro por placa e por horario de saida
		criteria.and("placaVeiculo").is(placa);
		criteria.and("horarioSaida").isNull();

		return this.mongoTemplate.exists(new Query(criteria), Ticket.class);
	}

	@Override
	public Optional<List<TicketResponseDTO>> consultarTickets() {
		Criteria criteria = new Criteria();

		// Monta filtro por placa e por horario de saida
		criteria.and("horarioSaida").isNull();

		List<Ticket> tickets = this.ticketRepository.findAll();

		if (tickets.size() > 0) {
			return Optional.of(tickets.stream().map(p -> converterParaDTO(p)).collect(Collectors.toList()));
		} else {
			throw new EntityNotFoundException("Não foram encontrados tickets ativos");
		}

	}

	private TicketResponseDTO converterParaDTO(Ticket ticket) {
		return this.converterParaDTO(ticket, null);
	}

	private TicketResponseDTO converterParaDTO(Ticket ticket, String mensagem) {
		if (ticket == null) {
			return null;
		}

		return new TicketResponseDTO(ticket.getIdTicket(), ticket.getPlacaVeiculo(),
				ticket.getHorarioEntrada(), ticket.getHorarioSaida(), ticket.getValorHora(),
				ticket.getValorFinal(), mensagem);
	}

}
