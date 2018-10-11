package org.govmix.fatturapa.parer.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.DipartimentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.EnteBD;

public class CacheEnti {

	private DipartimentoBD dipartimentoBD;
	private EnteBD enteBD;
//	private Logger log;
	
	Map<String, Ente> mapEnti;
	
	public CacheEnti(Logger log) throws Exception {
		this.enteBD = new EnteBD(log);
		this.dipartimentoBD = new DipartimentoBD(log);
//		this.log = log;
		this.mapEnti = new HashMap<String, Ente>();
	}
	
	public Ente getEnte(String codiceDipartimento) throws Exception {
		
		if(!this.mapEnti.containsKey(codiceDipartimento)) {
			IdDipartimento idDipartimento = new IdDipartimento();
			idDipartimento.setCodice(codiceDipartimento);
			Dipartimento dipartimento = this.dipartimentoBD.get(idDipartimento);
			Ente ente = this.enteBD.get(dipartimento.getEnte());
			this.mapEnti.put(codiceDipartimento, ente);
		}
		
		return this.mapEnti.get(codiceDipartimento);
	}

}
