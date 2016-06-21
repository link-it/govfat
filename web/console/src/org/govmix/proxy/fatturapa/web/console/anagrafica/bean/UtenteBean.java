package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.constants.UserRole;
import org.openspcoop2.generic_project.web.bean.BaseBean;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputField;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputGroup;
import org.openspcoop2.generic_project.web.impl.jsf1.output.field.OutputText;

public class UtenteBean extends BaseBean<Utente, Long>{
	
  	private OutputField<String> username = null;
  	private OutputField<String> password = null;
  	private OutputField<String> nome = null;
  	private OutputField<String> cognome = null;
  	private OutputField<String> ente = null;
//  	private OutputField<String> numeroDipartimenti = null;
  	private OutputField<String> ruolo = null;
	
  	// Gruppo Informazioni Dati Genareli
 	private OutputGroup fieldsDatiGenerali = new OutputGroup();
  	
 	// Lista Dipartimenti associati all'utente
  	private List<DipartimentoBean> listaDipartimenti = null;
  	
  	private DipartimentoBean metadataDipartimento = null;
  	
  	public UtenteBean(){
  		init();
  	}
  	
  	
  	private void init(){
  		
		this.username = new OutputText();
		this.username.setLabel("utente.username");
		this.username.setName("username");
		
		this.password = new OutputText();
		this.password.setLabel("utente.password");
		this.password.setName("password");
		this.password.setSecret(true); 
		
		this.cognome = new OutputText();
		this.cognome.setLabel("utente.cognome");
		this.cognome.setName("cognome");
		
		this.nome = new OutputText();
		this.nome.setLabel("utente.nome");
		this.nome.setName("nome");
		
		this.ente = new OutputText();
		this.ente.setLabel("utente.ente");
		this.ente.setName("ente");
		
		this.ruolo = new OutputText();
		this.ruolo.setLabel("utente.ruolo");
		this.ruolo.setName("ruolo");
		
//		this.numeroDipartimenti = new OutputText();
//		this.numeroDipartimenti.setLabel("utente.numeroDipartimenti");
//		this.numeroDipartimenti.setName("numeroDipartimenti");
  		
  		this.fieldsDatiGenerali = new OutputGroup();
		this.fieldsDatiGenerali.setIdGroup("datiGenerali");
		this.fieldsDatiGenerali.setColumns(2);
		this.fieldsDatiGenerali.setRendered(true);
		this.fieldsDatiGenerali.setStyleClass("datiTrasmissioneTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx");

		this.fieldsDatiGenerali.addField(this.cognome);
		this.fieldsDatiGenerali.addField(this.nome);
		this.fieldsDatiGenerali.addField(this.username);
		this.fieldsDatiGenerali.addField(this.ruolo);
//		this.fieldsDatiGenerali.addField(this.password);
		
		this.metadataDipartimento = new DipartimentoBean();
  	}
  	
	@Override
	public Long getId() {
		return this.dto != null ? this.dto.getId() : -1L;
	}
  	
  	@Override
  	public void setDTO(Utente dto) {
  		super.setDTO(dto);
  		
  		this.cognome.setValue(this.getDTO().getCognome());
  		this.nome.setValue(this.getDTO().getNome());
  		this.ente.setValue(this.getDTO().getEnte().getNome());
  		this.username.setValue(this.getDTO().getUsername());
  		this.password.setValue(this.getDTO().getPassword());
//  		int size = this.getDTO().sizeUtenteDipartimentoList();
//  		this.numeroDipartimenti.setValue("" + size);
  		
  		
		UserRole role = this.getDTO().getRole();

		if(role!= null){
			this.ruolo.setValue(role.getValue());
		}else 
			this.ruolo.setValue(null);
  		
  		
  	}


	public OutputField<String> getUsername() {
		return username;
	}


	public void setUsername(OutputField<String> username) {
		this.username = username;
	}


	public OutputField<String> getPassword() {
		return password;
	}


	public void setPassword(OutputField<String> password) {
		this.password = password;
	}


	public OutputField<String> getNome() {
		return nome;
	}


	public void setNome(OutputField<String> nome) {
		this.nome = nome;
	}


	public OutputField<String> getCognome() {
		return cognome;
	}


	public void setCognome(OutputField<String> cognome) {
		this.cognome = cognome;
	}


	public OutputField<String> getEnte() {
		return ente;
	}


	public void setEnte(OutputField<String> ente) {
		this.ente = ente;
	}


//	public OutputField<String> getNumeroDipartimenti() {
//		return numeroDipartimenti;
//	}
//
//
//	public void setNumeroDipartimenti(OutputField<String> numeroDipartimenti) {
//		this.numeroDipartimenti = numeroDipartimenti;
//	}


	public OutputGroup getFieldsDatiGenerali() {
		return fieldsDatiGenerali;
	}


	public void setFieldsDatiGenerali(OutputGroup fieldsDatiGenerali) {
		this.fieldsDatiGenerali = fieldsDatiGenerali;
	}


	public List<DipartimentoBean> getListaDipartimenti() {
		return listaDipartimenti;
	}


	public void setListaDipartimenti(List<DipartimentoBean> listaDipartimenti) {
		this.listaDipartimenti = listaDipartimenti;
	}


	public DipartimentoBean getMetadataDipartimento() {
		return metadataDipartimento;
	}


	public void setMetadataDipartimento(DipartimentoBean metadataDipartimento) {
		this.metadataDipartimento = metadataDipartimento;
	}


	public OutputField<String> getRuolo() {
		return ruolo;
	}


	public void setRuolo(OutputField<String> ruolo) {
		this.ruolo = ruolo;
	}
  	
	
  	
}
