package org.govmix.proxy.fatturapa.web.commons.exporter;

import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;

public class ExtendedNotificaEsitoCommittente extends NotificaEsitoCommittente {

	private static final long serialVersionUID = 1;
	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public static ExtendedNotificaEsitoCommittente newExtendedNotificaEsitoCommittente(NotificaEsitoCommittente nec, Integer index) {
		ExtendedNotificaEsitoCommittente ext = new ExtendedNotificaEsitoCommittente();
		ext.setAnno(nec.getAnno());
		ext.setDataInvioEnte(nec.getDataInvioEnte());
		ext.setDataInvioSdi(nec.getDataInvioSdi());
		ext.setDataProssimaConsegnaSdi(nec.getDataProssimaConsegnaSdi());
		ext.setDataUltimaConsegnaSdi(nec.getDataUltimaConsegnaSdi());
		ext.setDescrizione(nec.getDescrizione());
		ext.setEsito(nec.getEsito());
		ext.setId(nec.getId());
		ext.setIdentificativoSdi(nec.getIdentificativoSdi());
		ext.setIdFattura(nec.getIdFattura());
		ext.setMessageIdCommittente(nec.getMessageIdCommittente());
		ext.setModalitaBatch(nec.getModalitaBatch());
		ext.setNomeFile(nec.getNomeFile());
		ext.setNumeroFattura(nec.getNumeroFattura());
		ext.setPosizione(nec.getPosizione());
		ext.setScarto(nec.getScarto());
		ext.setScartoNote(nec.getScartoNote());
		ext.setScartoXml(nec.getScartoXml());
		ext.setStatoConsegnaSdi(nec.getStatoConsegnaSdi());
		ext.setTentativiConsegnaSdi(nec.getTentativiConsegnaSdi());
		ext.setUtente(nec.getUtente());
		ext.setXml(nec.getXml());
		
		ext.setIndex(index);
		
		return ext;
	}
}
