package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.net.URI;

import org.govmix.proxy.fatturapa.Ente;
import org.openspcoop2.generic_project.web.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputField;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputGroup;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputText;

public class EnteBean extends BaseBean<Ente, Long>{

	private OutputField<String> nome = null;
	private OutputField<String> descrizione = null;
	private OutputField<String> endpoint = null;


	// Gruppo Informazioni Dati Genareli
	private OutputGroup fieldsDatiGenerali = new OutputGroup();

	public EnteBean(){
		init();
	}

	private void init() {
		this.nome = new OutputText();
		this.nome.setLabel("ente.nome");
		this.nome.setName("ente");

		this.descrizione = new OutputText();
		this.descrizione.setLabel("ente.descrizione");
		this.descrizione.setName("descrizione");

		this.endpoint = new OutputText();
		this.endpoint.setLabel("ente.endpoint");
		this.endpoint.setName("endpoint");


		this.fieldsDatiGenerali = new OutputGroup();
		this.fieldsDatiGenerali.setIdGroup("datiGenerali");
		this.fieldsDatiGenerali.setColumns(2);
		this.fieldsDatiGenerali.setRendered(true);
		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
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

	public OutputField<String> getNome() {
		return nome;
	}

	public void setNome(OutputField<String> nome) {
		this.nome = nome;
	}

	public OutputField<String> getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(OutputField<String> descrizione) {
		this.descrizione = descrizione;
	}

	public OutputField<String> getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(OutputField<String> endpoint) {
		this.endpoint = endpoint;
	}

	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}

	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}
	
	

}
