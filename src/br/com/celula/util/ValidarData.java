package br.com.celula.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ValidarData {
	public static boolean dataOk(String Dt)  
	{  
	  
	  Integer Dia,Mes;  
	  Integer Ano;    
	  if(Dt.trim().length()==10)  
	    {    
	     Dia = Integer.parseInt(Dt.substring(0,2));
	     Mes = Integer.parseInt(Dt.substring(3,5));  
	     Ano = Integer.parseInt(Dt.substring(6,10)); 

	    if(  
	        ( (Mes.equals(1) || Mes.equals(3) || Mes.equals(5) || Mes.equals(7) || Mes.equals(8) || Mes.equals(10) || Mes.equals(12)) && (Dia>=1 && Dia <=31))  
	        ||  
	        ( (Mes.equals(4) || Mes.equals(6) || Mes.equals(9) || Mes.equals(11)) && (Dia>=1 && Dia <=30))  
	        ||  
	        ( (Mes.equals(2)) && (AnoBissexto(Ano)) && (Dia>=1 && Dia <=29))  
	        ||  
	        ( (Mes.equals(2)) && !(AnoBissexto(Ano)) && (Dia>=1 && Dia <=28))
	      ){  
	          return true;  
	        }else{  return false;}  
	    }        
	  else  
	  {  return false;}  
	  
	}  
	public static boolean AnoBissexto (int ano)   
	{     
	     return ano % 4 == 0 && ano % 100 != 0 || ano % 400 == 0;     
	}    
	
	public static boolean maiorQueHoje(String s){
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = dtf.parseDateTime(s);
		return dt.isAfterNow();
	}
	
	public static boolean menorQueTalAno(String s,int ano){
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = dtf.parseDateTime(s);
		return dt.getYear()<ano;
	}
	
	public static Date stringToDate(String s) throws ParseException{
		SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
		return fromUser.parse(s);
	}	
	
    public static String dataParaString(Date d){
    	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
  	  	return dtf.print(new DateTime(d));
    }
    
	public static boolean maiorQueNascimento(String batismo, String nascimento) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dbat = dtf.parseDateTime(batismo);
		DateTime dnasc = dtf.parseDateTime(nascimento);
		return dbat.isBefore(dnasc);
	}
}
