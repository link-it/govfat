package org.govmix.proxy.fatturapa.web.commons.exporter;

import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;

public class ExtendedNotificaEsitoCommittente extends NotificaEsitoCommittente {

	private static final long serialVersionUID = 1;
	private Integer index;
	private byte[] xml;
	private byte[] scartoXml;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public static ExtendedNotificaEsitoCommittente newExtendedNotificaEsitoCommittente(NotificaEsitoCommittente nec, TracciaSDI tracciaNEC, TracciaSDI tracciaScarto, Integer index) {
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
		ext.setScartoXml(tracciaScarto.getRawData());
		ext.setStatoConsegnaSdi(nec.getStatoConsegnaSdi());
		ext.setTentativiConsegnaSdi(nec.getTentativiConsegnaSdi());
		ext.setUtente(nec.getUtente());
		ext.setXml(tracciaNEC.getRawData());
		
		ext.setIndex(index);
		
		return ext;
	}

	public byte[] getScartoXml() {
		return scartoXml;
	}

	public void setScartoXml(byte[] scartoXml) {
		this.scartoXml = scartoXml;
	}

	public byte[] getXml() {
		return xml;
	}

	public void setXml(byte[] xml) {
		this.xml = xml;
	}
}
