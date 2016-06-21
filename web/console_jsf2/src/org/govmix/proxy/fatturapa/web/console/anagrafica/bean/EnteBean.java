package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.net.URI;

import org.govmix.proxy.fatturapa.Ente;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf2.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;
import org.openspcoop2.generic_project.web.view.IViewBean;

public class EnteBean extends BaseBean<Ente, Long> implements IViewBean<Ente, Long>{

	private Text nome = null;
	private Text descrizione = null;
	private Text endpoint = null;


	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = null;

	public EnteBean(){
		try{
			init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
		this.nome = this.getFactory().getOutputFieldFactory().createText("ente","ente.nome");
		this.descrizione = this.getFactory().getOutputFieldFactory().createText("descrizione","ente.descrizione");
		this.endpoint = this.getFactory().getOutputFieldFactory().createText("endpoint","ente.endpoint");
		
		this.setField(this.nome);
		this.setField(this.descrizione);
		this.setField(this.endpoint);

		this.fieldsDatiGenerali = this.getFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",2);
		this.fieldsDatiGenerali.setStyleClass("outputGroupTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");

		this.fieldsDatiGenerali.addField(this.nome);
		this.fieldsDatiGenerali.addField(this.descrizione);
		this.fieldsDatiGenerali.addField(this.endpoint);
	}

	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}

	@Override
	public void setDTO(Ente dto) {
		super.setDTO(dto);

		this.nome.setValue(this.getDTO().getNome());
		this.descrizione.setValue(this.getDTO().getDescrizione());
		URI endpoint2 = this.getDTO().getEndpoint();
		if(endpoint2 != null)
			this.endpoint.setValue(endpoint2.toString());

	}

	public Text getNome() {
		return nome;
	}

	public void setNome(Text nome) {
		this.nome = nome;
	}

	public Text getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(Text descrizione) {
		this.descrizione = descrizione;
	}

	public Text getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Text endpoint) {
		this.endpoint = endpoint;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}
	
	

}
