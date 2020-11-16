package org.govmix.proxy.fatturapa.web.console.bean;

import org.apache.commons.lang.StringUtils;
import org.govmix.proxy.fatturapa.web.commons.consegnaFattura.DatiDocumentiCorrelatiType;
import org.openspcoop2.generic_project.web.bean.IBean;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf1.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.DateTime;
import org.openspcoop2.generic_project.web.output.Text;

public class DatiDocumentiCorrelatiBean extends BaseBean<DatiDocumentiCorrelatiType, Long> implements IBean<DatiDocumentiCorrelatiType, Long>{

	private Text riferimentoNumeroLinea = null;
	private Text idDocumento = null;
	private DateTime data;
	private Text numItem = null;
	private Text codiceCommessaConvenzione = null;
	private Text codiceCUP = null;
	private Text codiceCIG = null;
	private long idLinea;
	
	public DatiDocumentiCorrelatiBean(){
		try{
			this.init();
		}catch(Exception e){

		}
	}

	private void init() throws FactoryException{
		this.riferimentoNumeroLinea = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("riferimentoNumeroLinea","datiDocumentiCorrelati.riferimentoNumeroLinea");
		this.idDocumento = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("idDocumento","datiDocumentiCorrelati.idDocumento");
		this.data = this.getWebGenericProjectFactory().getOutputFieldFactory().createDateTime("data","datiDocumentiCorrelati.data",org.govmix.proxy.fatturapa.web.console.costanti.Costanti.FORMATO_DATA_DD_M_YYYY);
		this.numItem = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("numItem","datiDocumentiCorrelati.numItem");
		this.codiceCommessaConvenzione = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceCommessaConvenzione","datiDocumentiCorrelati.codiceCommessaConvenzione");
		this.codiceCUP = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceCUP","datiDocumentiCorrelati.codiceCUP");
		this.codiceCIG = this.getWebGenericProjectFactory().getOutputFieldFactory().createText("codiceCIG","datiDocumentiCorrelati.codiceCIG");

		this.setField(this.riferimentoNumeroLinea);
		this.setField(this.idDocumento);
		this.setField(this.data);
		this.setField(this.numItem);
		this.setField(this.codiceCommessaConvenzione);
		this.setField(this.codiceCUP);
		this.setField(this.codiceCIG);
	}

	@Override
	public Long getId() {
		return idLinea;
	}

	@Override
	public void setDTO(DatiDocumentiCorrelatiType dto) {
		super.setDTO(dto);
		
		String rifNumeroLinea = null;
		
		if(this.getDTO().getRiferimentoNumeroLinea() != null) {
			rifNumeroLinea = StringUtils.join(this.getDTO().getRiferimentoNumeroLinea(), ", ");
		}
		
		this.riferimentoNumeroLinea.setValue(rifNumeroLinea);
		this.idDocumento.setValue(this.getDTO().getIdDocumento());
		this.data.setValue(this.getDTO().getData());
		this.numItem.setValue(this.getDTO().getNumItem());
		this.codiceCommessaConvenzione.setValue(this.getDTO().getCodiceCommessaConvenzione());
		this.codiceCUP.setValue(this.getDTO().getCodiceCUP());
		this.codiceCIG.setValue(this.getDTO().getCodiceCIG());
		
	}

	public Text getRiferimentoNumeroLinea() {
		return riferimentoNumeroLinea;
	}

	public void setRiferimentoNumeroLinea(Text riferimentoNumeroLinea) {
		this.riferimentoNumeroLinea = riferimentoNumeroLinea;
	}

	public Text getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Text idDocumento) {
		this.idDocumento = idDocumento;
	}

	public DateTime getData() {
		return data;
	}

	public void setData(DateTime data) {
		this.data = data;
	}

	public Text getNumItem() {
		return numItem;
	}

	public void setNumItem(Text numItem) {
		this.numItem = numItem;
	}

	public Text getCodiceCommessaConvenzione() {
		return codiceCommessaConvenzione;
	}

	public void setCodiceCommessaConvenzione(Text codiceCommessaConvenzione) {
		this.codiceCommessaConvenzione = codiceCommessaConvenzione;
	}

	public Text getCodiceCUP() {
		return codiceCUP;
	}

	public void setCodiceCUP(Text codiceCUP) {
		this.codiceCUP = codiceCUP;
	}

	public Text getCodiceCIG() {
		return codiceCIG;
	}

	public void setCodiceCIG(Text codiceCIG) {
		this.codiceCIG = codiceCIG;
	}

	public long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(long idLinea) {
		this.idLinea = idLinea;
	}
	
	
}
