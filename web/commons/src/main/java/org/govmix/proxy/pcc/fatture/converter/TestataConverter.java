/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2016 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.govmix.proxy.pcc.fatture.converter;

import it.tesoro.fatture.TestataAsyncTipo;
import it.tesoro.fatture.TestataBaseTipo;
import it.tesoro.fatture.TestataQueryTipo;
import it.tesoro.fatture.TestataTransazioneTipo;

import java.util.Date;

import org.govmix.proxy.fatturapa.orm.PccTraccia;
import org.govmix.proxy.pcc.fatture.authorization.AuthorizationBeanResponse;
import org.govmix.proxy.pcc.fatture.utils.PccProperties;

public class TestataConverter {

	public static TestataAsyncTipo toPCCTestataProxy(org.govmix.pcc.fatture.TestataAsyncRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		TestataAsyncTipo pccVO = new TestataAsyncTipo();
		pccVO.setIdentificativoTransazionePA(vo.getIdentificativoTransazionePA());
		pccVO.setIdentificativoPCCAmministrazione(Long.parseLong(beanResponse.getIdPccAmministrazione()));
		pccVO.setTimestampTrasmissione(new Date());
		pccVO.setVersioneApplicativa(PccProperties.getInstance().getVersioneApplicativa());
		pccVO.setCodiceFiscaleTrasmittente(beanResponse.getCfTrasmittente());
		return pccVO;
	}

	public static TestataAsyncTipo toPCCTestataProxy(org.govmix.pcc.fatture.TestataAsyncTipo vo, AuthorizationBeanResponse beanResponse) {
		TestataAsyncTipo pccVO = new TestataAsyncTipo();
		pccVO.setTimestampTrasmissione(new Date());
		pccVO.setIdentificativoPCCAmministrazione(Long.parseLong(beanResponse.getIdPccAmministrazione()));
		pccVO.setTimestampTrasmissione(new Date());
		pccVO.setVersioneApplicativa(PccProperties.getInstance().getVersioneApplicativa());
		pccVO.setCodiceFiscaleTrasmittente(beanResponse.getCfTrasmittente());
		return pccVO;
	}

	public static TestataQueryTipo toPCCTestataQuery(org.govmix.pcc.fatture.TestataAsyncRichiestaTipo vo, String cfTrasmittente, String idPccTrasmissione, String idPccAmministrazione) {
		TestataQueryTipo pccVO = new TestataQueryTipo();
		pccVO.setIdentificativoTransazionePCC(idPccTrasmissione);
		pccVO.setIdentificativoTransazionePA(vo.getIdentificativoTransazionePA());
		pccVO.setIdentificativoPCCAmministrazione(Long.parseLong(idPccAmministrazione));
		pccVO.setTimestampTrasmissione(new Date());
		pccVO.setVersioneApplicativa(PccProperties.getInstance().getVersioneApplicativa());
		pccVO.setCodiceFiscaleTrasmittente(cfTrasmittente);
		return pccVO;
	}

	public static TestataTransazioneTipo toPCCTestataRead(org.govmix.pcc.fatture.TestataBaseRichiestaTipo vo, AuthorizationBeanResponse beanResponse) {
		TestataTransazioneTipo pccVO = new TestataTransazioneTipo();
		pccVO.setIdentificativoPCCAmministrazione(Long.parseLong(beanResponse.getIdPccAmministrazione()));
		pccVO.setTimestampTrasmissione(new Date());
		pccVO.setVersioneApplicativa(PccProperties.getInstance().getVersioneApplicativa());
		pccVO.setCodiceFiscaleTrasmittente(beanResponse.getCfTrasmittente());
		return pccVO;
	}

	public static TestataBaseTipo toPCCTestataBase(org.govmix.pcc.fatture.TestataBaseRichiestaTipo testataRichiesta, AuthorizationBeanResponse beanResponse) {
		TestataBaseTipo vo = new TestataBaseTipo();
		vo.setTimestampTrasmissione(new Date());
		vo.setVersioneApplicativa(PccProperties.getInstance().getVersioneApplicativa());
		vo.setCodiceFiscaleTrasmittente(beanResponse.getCfTrasmittente());
		return vo;
	}

	public static org.govmix.pcc.fatture.TestataAsyncTipo toProxyTestataQuery(TestataAsyncTipo vo) {
		org.govmix.pcc.fatture.TestataAsyncTipo pccVO = new org.govmix.pcc.fatture.TestataAsyncTipo();
		pccVO.setDataElaborazione(new Date());
		return pccVO;
	}

	public static org.govmix.pcc.fatture.TestataBaseRispostaTipo toProxyTestataRead(TestataTransazioneTipo testataRisposta) {
		org.govmix.pcc.fatture.TestataAsyncTipo pccVO = new org.govmix.pcc.fatture.TestataAsyncTipo();
		return pccVO;
	}

	public static org.govmix.pcc.fatture.TestataBaseRispostaTipo toPCCTestataQuery(TestataBaseTipo testataRisposta) {
		org.govmix.pcc.fatture.TestataAsyncTipo pccVO = new org.govmix.pcc.fatture.TestataAsyncTipo();
		return pccVO;
	}

	public static TestataQueryTipo toPCCTestataQuery(PccTraccia traccia, String idPccTrasmissione) {
		TestataQueryTipo pccVO = new TestataQueryTipo();
		pccVO.setIdentificativoTransazionePCC(idPccTrasmissione);
		if(traccia.getIdPaTransazioneRispedizione() != null)
			pccVO.setIdentificativoTransazionePA(traccia.getIdPaTransazioneRispedizione());
		else 
			pccVO.setIdentificativoTransazionePA(traccia.getIdPaTransazione());
		
		pccVO.setIdentificativoPCCAmministrazione(new Long(traccia.getIdPccAmministrazione()));
		pccVO.setTimestampTrasmissione(new Date());
		pccVO.setVersioneApplicativa(PccProperties.getInstance().getVersioneApplicativa());
		pccVO.setCodiceFiscaleTrasmittente(traccia.getCfTrasmittente());
		return pccVO;

	}

}
