package org.govmix.proxy.fatturapa.web.console.anagrafica.bean;

import java.util.List;

import org.govmix.proxy.fatturapa.Utente;
import org.govmix.proxy.fatturapa.constants.UserRole;
import org.openspcoop2.generic_project.web.factory.FactoryException;
import org.openspcoop2.generic_project.web.impl.jsf2.bean.BaseBean;
import org.openspcoop2.generic_project.web.output.OutputGroup;
import org.openspcoop2.generic_project.web.output.Text;
import org.openspcoop2.generic_project.web.view.IViewBean;

public class UtenteBean extends BaseBean<Utente, Long> implements IViewBean<Utente, Long>{
	
  	private Text username = null;
  	private Text password = null;
  	private Text nome = null;
  	private Text cognome = null;
  	private Text ente = null;
//  	private Text numeroDipartimenti = null;
  	private Text ruolo = null;
	
  	// Gruppo Informazioni Dati Genareli
 	private OutputGroup fieldsDatiGenerali = null;
  	
 	// Lista Dipartimenti associati all'utente
  	private List<DipartimentoBean> listaDipartimenti = null;
  	
  	private DipartimentoBean metadataDipartimento = null;
  	
  	public UtenteBean(){
		try{
			init();
		}catch(Exception e){

		}
	}

	public void init() throws FactoryException{
  		
		this.username = this.getFactory().getOutputFieldFactory().createText("username","utente.username");
		this.password = this.getFactory().getOutputFieldFactory().createText("password","utente.password");
		this.password.setSecret(true); 
		this.cognome = this.getFactory().getOutputFieldFactory().createText("cognome","utente.cognome");
		this.nome = this.getFactory().getOutputFieldFactory().createText("nome","utente.nome");
		this.ente = this.getFactory().getOutputFieldFactory().createText("ente","utente.ente");
		this.ruolo = this.getFactory().getOutputFieldFactory().createText("ruolo","utente.ruolo");

		this.setField(this.cognome);
		this.setField(this.nome);
		this.setField(this.username);
		this.setField(this.ruolo);
		
  		this.fieldsDatiGenerali = this.getFactory().getOutputFieldFactory().createOutputGroup("datiGenerali",4);
		this.fieldsDatiGenerali.setStyleClass("outputGroupTable"); 
		this.fieldsDatiGenerali.setColumnClasses("labelAllineataDx,valueAllineataSx,labelAllineataDx,valueAllineataSx");

		
		this.fieldsDatiGenerali.addField(this.username);
		this.fieldsDatiGenerali.addField(this.cognome);
	
		this.fieldsDatiGenerali.addField(this.ruolo);
		this.fieldsDatiGenerali.addField(this.nome);
		
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

  		UserRole role = this.getDTO().getRole();

		if(role!= null){
			this.ruolo.setValue(role.getValue());
		}else 
			this.ruolo.setValue(null);
  		
  		
  	}


	public Text getUsername() {
		return username;
	}


	public void setUsername(Text username) {
		this.username = username;
	}


	public Text getPassword() {
		return password;
	}


	public void setPassword(Text password) {
		this.password = password;
	}


	public Text getNome() {
		return nome;
	}


	public void setNome(Text nome) {
		this.nome = nome;
	}


	public Text getCognome() {
		return cognome;
	}


	public void setCognome(Text cognome) {
		this.cognome = cognome;
	}


	public Text getEnte() {
		return ente;
	}


	public void setEnte(Text ente) {
		this.ente = ente;
	}

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


	public Text getRuolo() {
		return ruolo;
	}


	public void setRuolo(Text ruolo) {
		this.ruolo = ruolo;
	}
  	
	
  	
}
