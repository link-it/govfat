package org.govmix.fatturapa.parer.utils;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.Dipartimento;
import org.govmix.proxy.fatturapa.orm.Ente;
import org.govmix.proxy.fatturapa.orm.IdDipartimento;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.DipartimentoBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.EnteBD;

public class CacheEnti {

	private DipartimentoBD dipartimentoBD;
	private EnteBD enteBD;
	public CacheEnti(Logger log) throws Exception {
		this.enteBD = new EnteBD(log);
		this.dipartimentoBD = new DipartimentoBD(log);
	}
	
	public Ente getEnte(String codiceDipartimento) throws Exception {
		
		IdDipartimento idDipartimento = new IdDipartimento();
		idDipartimento.setCodice(codiceDipartimento);
		Dipartimento dipartimento = this.dipartimentoBD.get(idDipartimento);
		return this.enteBD.get(dipartimento.getEnte());
	}

}
