package br.com.postech.estacionamento.parquimetro.infrastructure.handler;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(){
        super();
    }
    public EntityNotFoundException(String mensagem){
        super(mensagem);
    }
}
