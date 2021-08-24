package br.com.pedrodeveloper.springdataexamples.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class StringValidationUtils {
	
	/**
	 * Método para converter um parâmetro String em um número válido para
	 * paginação. Em caso de erro na conversão, retorna zero por padrão.
	 * @param page	A String a ser convertida em número.
	 * @return A String convertida em Inteiro, ou zero se der erro na conversão.
	 */
	public static Integer stringToPageNumber(String page) {
		Integer pageInt = 0;
		try {
			pageInt = Integer.valueOf(page);
			if (pageInt == null || pageInt.compareTo(0) < 0) pageInt = 0;
		} catch (NumberFormatException e) {
			//se informar pagina invalida deixa como 0 mesmo (primeira pagina)
		}
		
		return pageInt;
	}
	

	/**
	 * Método para transformar uma string em data. Se falhar para converter, retorna nulo. 
	 * @param date	String com data no formato yyyy-MM-dd
	 * @return A data convertida ou null se falhar a conversão
	 */
	public static LocalDateTime stringToDate(String date) {
		LocalDateTime converted = null;
		try {
			Date parseDate = DateUtils.parseDate(date, "yyyy-MM-dd");
			converted = LocalDateTime.ofInstant(parseDate.toInstant(), ZoneId.systemDefault());
		} catch (ParseException | IllegalArgumentException e) {
			// Em caso de erro de conversão desconsidera a data
		}
		
		return converted;
	}
}
