package br.com.postech.estacionamento.parquimetro.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.estacionamento.parquimetro.domain.model.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String>{

}
