package br.com.postech.estacionamento.parquimetro.utils;

public class Validador {
    /**
	 * Valida se uma placa de veículo está no formato antigo (AAA1234) ou no
	 * formato Mercosul (ABC1D23).
	 *
	 * @param placa A placa do veículo a ser validada.
	 * @return true se a placa for válida, false caso contrário.
	 */
	public static boolean validaPlaca(String placa) {
        // Verifica se a placa tem 7 caracteres
        if (placa.length() != 7) {
            return false;
        }

        // Expressão regular para o formato antigo (AAA-1234)
        String regexAntiga = "^[A-Z]{3}[0-9]{4}$";
        // Expressão regular para o formato Mercosul (ABC1D23)
        String regexMercosul = "^[A-Z]{3}[0-9][A-Z][0-9]{2}$";

        // Verifica se a placa corresponde a qualquer um dos formatos
        return placa.matches(regexAntiga) || placa.matches(regexMercosul);
    }
}
