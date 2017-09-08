/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2017 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
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
package org.govmix.proxy.fatturapa.orm.dao.jdbc;

import java.sql.Connection;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.apache.log4j.Logger;

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.govmix.proxy.fatturapa.orm.IdFattura;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.beans.UpdateModel;

import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;

import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;

import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCFatturaElettronicaServiceImpl
 *
 * @author Giuseppe Papandrea (papandrea@link.it)
 * @author Giovanni Bussu (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCFatturaElettronicaServiceImpl extends JDBCFatturaElettronicaServiceSearchImpl
	implements IJDBCServiceCRUDWithId<FatturaElettronica, IdFattura, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, FatturaElettronica fatturaElettronica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
				

		// Object _notificaDecorrenzaTermini
		Long id_notificaDecorrenzaTermini = null;
		org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini idLogic_notificaDecorrenzaTermini = null;
		idLogic_notificaDecorrenzaTermini = fatturaElettronica.getIdDecorrenzaTermini();
		if(idLogic_notificaDecorrenzaTermini!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_notificaDecorrenzaTermini = ((JDBCNotificaDecorrenzaTerminiServiceSearch)(this.getServiceManager().getNotificaDecorrenzaTerminiServiceSearch())).findTableId(idLogic_notificaDecorrenzaTermini, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_notificaDecorrenzaTermini = idLogic_notificaDecorrenzaTermini.getId();
				if(id_notificaDecorrenzaTermini==null || id_notificaDecorrenzaTermini<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _pccTracciaTrasmissioneEsito
		Long id_pccTracciaTrasmissioneEsito = null;
		org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito idLogic_pccTracciaTrasmissioneEsito = null;
		idLogic_pccTracciaTrasmissioneEsito = fatturaElettronica.getIdEsitoContabilizzazione();
		if(idLogic_pccTracciaTrasmissioneEsito!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccTracciaTrasmissioneEsito = ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneEsitoServiceSearch())).findTableId(idLogic_pccTracciaTrasmissioneEsito, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccTracciaTrasmissioneEsito = idLogic_pccTracciaTrasmissioneEsito.getId();
				if(id_pccTracciaTrasmissioneEsito==null || id_pccTracciaTrasmissioneEsito<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _pccTracciaTrasmissioneEsitoInstance2
		Long id_pccTracciaTrasmissioneEsitoInstance2 = null;
		org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito idLogic_pccTracciaTrasmissioneEsitoInstance2 = null;
		idLogic_pccTracciaTrasmissioneEsitoInstance2 = fatturaElettronica.getIdEsitoScadenza();
		if(idLogic_pccTracciaTrasmissioneEsitoInstance2!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_pccTracciaTrasmissioneEsitoInstance2 = ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneEsitoServiceSearch())).findTableId(idLogic_pccTracciaTrasmissioneEsitoInstance2, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_pccTracciaTrasmissioneEsitoInstance2 = idLogic_pccTracciaTrasmissioneEsitoInstance2.getId();
				if(id_pccTracciaTrasmissioneEsitoInstance2==null || id_pccTracciaTrasmissioneEsitoInstance2<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object fatturaElettronica
		sqlQueryObjectInsert.addInsertTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().FORMATO_TRASMISSIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().NOME_FILE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().MESSAGE_ID,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().POSIZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CODICE_DESTINATARIO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TIPO_DOCUMENTO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DIVISA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().ANNO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().NUMERO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().ESITO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DA_PAGARE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CAUSALE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TENTATIVI_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DETTAGLIO_CONSEGNA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_PROTOCOLLAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_SCADENZA,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_PROTOCOLLAZIONE,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().PROTOCOLLO,false),"?");
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().XML,false),"?");
		sqlQueryObjectInsert.addInsertField("id_notifica_decorrenza_termini","?");
		sqlQueryObjectInsert.addInsertField("id_contabilizzazione","?");
		sqlQueryObjectInsert.addInsertField("id_scadenza","?");

		// Insert fatturaElettronica
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getFatturaElettronicaFetch().getKeyGeneratorObject(FatturaElettronica.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getFormatoTrasmissione(),FatturaElettronica.model().FORMATO_TRASMISSIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getIdentificativoSdi(),FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDataRicezione(),FatturaElettronica.model().DATA_RICEZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getNomeFile(),FatturaElettronica.model().NOME_FILE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getMessageId(),FatturaElettronica.model().MESSAGE_ID.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCedentePrestatoreDenominazione(),FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCedentePrestatorePaese(),FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCedentePrestatoreCodiceFiscale(),FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCessionarioCommittenteDenominazione(),FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCessionarioCommittentePaese(),FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCessionarioCommittenteCodiceFiscale(),FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittenteDenominazione(),FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittentePaese(),FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(),FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getPosizione(),FatturaElettronica.model().POSIZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCodiceDestinatario(),FatturaElettronica.model().CODICE_DESTINATARIO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTipoDocumento(),FatturaElettronica.model().TIPO_DOCUMENTO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDivisa(),FatturaElettronica.model().DIVISA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getData(),FatturaElettronica.model().DATA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getAnno(),FatturaElettronica.model().ANNO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getNumero(),FatturaElettronica.model().NUMERO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getEsito(),FatturaElettronica.model().ESITO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDaPagare(),FatturaElettronica.model().DA_PAGARE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getImportoTotaleDocumento(),FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getImportoTotaleRiepilogo(),FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCausale(),FatturaElettronica.model().CAUSALE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getStatoConsegna(),FatturaElettronica.model().STATO_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDataConsegna(),FatturaElettronica.model().DATA_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDataProssimaConsegna(),FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTentativiConsegna(),FatturaElettronica.model().TENTATIVI_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDettaglioConsegna(),FatturaElettronica.model().DETTAGLIO_CONSEGNA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getStatoProtocollazione(),FatturaElettronica.model().STATO_PROTOCOLLAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDataScadenza(),FatturaElettronica.model().DATA_SCADENZA.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDataProtocollazione(),FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getProtocollo(),FatturaElettronica.model().PROTOCOLLO.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getXml(),FatturaElettronica.model().XML.getFieldType()),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_notificaDecorrenzaTermini,Long.class),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_pccTracciaTrasmissioneEsito,Long.class),
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(id_pccTracciaTrasmissioneEsitoInstance2,Long.class)
		);
		fatturaElettronica.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura oldId, FatturaElettronica fatturaElettronica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObject.newSQLQueryObject();
		Long longIdByLogicId = this.findIdFatturaElettronica(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long tableId = fatturaElettronica.getId();
		if(tableId != null && tableId.longValue() > 0) {
			if(tableId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: fatturaElettronica.id ["+tableId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			tableId = longIdByLogicId;
			fatturaElettronica.setId(tableId);
		}
		if(tableId==null || tableId<=0){
			throw new Exception("Retrieve tableId failed");
		}

		this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, fatturaElettronica, idMappingResolutionBehaviour);
	}
	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, FatturaElettronica fatturaElettronica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
		
		boolean setIdMappingResolutionBehaviour = 
			(idMappingResolutionBehaviour==null) ||
			org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) ||
			org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour);
			

		// Object _fatturaElettronica_notificaDecorrenzaTermini
		Long id_fatturaElettronica_notificaDecorrenzaTermini = null;
		org.govmix.proxy.fatturapa.orm.IdNotificaDecorrenzaTermini idLogic_fatturaElettronica_notificaDecorrenzaTermini = null;
		idLogic_fatturaElettronica_notificaDecorrenzaTermini = fatturaElettronica.getIdDecorrenzaTermini();
		if(idLogic_fatturaElettronica_notificaDecorrenzaTermini!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_fatturaElettronica_notificaDecorrenzaTermini = ((JDBCNotificaDecorrenzaTerminiServiceSearch)(this.getServiceManager().getNotificaDecorrenzaTerminiServiceSearch())).findTableId(idLogic_fatturaElettronica_notificaDecorrenzaTermini, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_fatturaElettronica_notificaDecorrenzaTermini = idLogic_fatturaElettronica_notificaDecorrenzaTermini.getId();
				if(id_fatturaElettronica_notificaDecorrenzaTermini==null || id_fatturaElettronica_notificaDecorrenzaTermini<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _fatturaElettronica_pccTracciaTrasmissioneEsito
		Long id_fatturaElettronica_pccTracciaTrasmissioneEsito = null;
		org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito idLogic_fatturaElettronica_pccTracciaTrasmissioneEsito = null;
		idLogic_fatturaElettronica_pccTracciaTrasmissioneEsito = fatturaElettronica.getIdEsitoContabilizzazione();
		if(idLogic_fatturaElettronica_pccTracciaTrasmissioneEsito!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_fatturaElettronica_pccTracciaTrasmissioneEsito = ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneEsitoServiceSearch())).findTableId(idLogic_fatturaElettronica_pccTracciaTrasmissioneEsito, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_fatturaElettronica_pccTracciaTrasmissioneEsito = idLogic_fatturaElettronica_pccTracciaTrasmissioneEsito.getId();
				if(id_fatturaElettronica_pccTracciaTrasmissioneEsito==null || id_fatturaElettronica_pccTracciaTrasmissioneEsito<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}

		// Object _fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2
		Long id_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2 = null;
		org.govmix.proxy.fatturapa.orm.IdTrasmissioneEsito idLogic_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2 = null;
		idLogic_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2 = fatturaElettronica.getIdEsitoScadenza();
		if(idLogic_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2!=null){
			if(idMappingResolutionBehaviour==null ||
				(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour))){
				id_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2 = ((JDBCPccTracciaTrasmissioneEsitoServiceSearch)(this.getServiceManager().getPccTracciaTrasmissioneEsitoServiceSearch())).findTableId(idLogic_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2, false);
			}
			else if(org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour)){
				id_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2 = idLogic_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2.getId();
				if(id_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2==null || id_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2<=0){
					throw new Exception("Logic id not contains table id");
				}
			}
		}


		// Object fatturaElettronica
		sqlQueryObjectUpdate.setANDLogicOperator(true);
		sqlQueryObjectUpdate.addUpdateTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		boolean isUpdate_fatturaElettronica = true;
		java.util.List<JDBCObject> lstObjects_fatturaElettronica = new java.util.ArrayList<JDBCObject>();
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().FORMATO_TRASMISSIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getFormatoTrasmissione(), FatturaElettronica.model().FORMATO_TRASMISSIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getIdentificativoSdi(), FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDataRicezione(), FatturaElettronica.model().DATA_RICEZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().NOME_FILE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getNomeFile(), FatturaElettronica.model().NOME_FILE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().MESSAGE_ID,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getMessageId(), FatturaElettronica.model().MESSAGE_ID.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getCedentePrestatoreDenominazione(), FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getCedentePrestatorePaese(), FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getCedentePrestatoreCodiceFiscale(), FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getCessionarioCommittenteDenominazione(), FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getCessionarioCommittentePaese(), FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getCessionarioCommittenteCodiceFiscale(), FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittenteDenominazione(), FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittentePaese(), FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(), FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().POSIZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getPosizione(), FatturaElettronica.model().POSIZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CODICE_DESTINATARIO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getCodiceDestinatario(), FatturaElettronica.model().CODICE_DESTINATARIO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TIPO_DOCUMENTO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getTipoDocumento(), FatturaElettronica.model().TIPO_DOCUMENTO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DIVISA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDivisa(), FatturaElettronica.model().DIVISA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getData(), FatturaElettronica.model().DATA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().ANNO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getAnno(), FatturaElettronica.model().ANNO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().NUMERO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getNumero(), FatturaElettronica.model().NUMERO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().ESITO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getEsito(), FatturaElettronica.model().ESITO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DA_PAGARE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDaPagare(), FatturaElettronica.model().DA_PAGARE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getImportoTotaleDocumento(), FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getImportoTotaleRiepilogo(), FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CAUSALE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getCausale(), FatturaElettronica.model().CAUSALE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getStatoConsegna(), FatturaElettronica.model().STATO_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_CONSEGNA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDataConsegna(), FatturaElettronica.model().DATA_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDataProssimaConsegna(), FatturaElettronica.model().DATA_PROSSIMA_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TENTATIVI_CONSEGNA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getTentativiConsegna(), FatturaElettronica.model().TENTATIVI_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DETTAGLIO_CONSEGNA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDettaglioConsegna(), FatturaElettronica.model().DETTAGLIO_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_PROTOCOLLAZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getStatoProtocollazione(), FatturaElettronica.model().STATO_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_SCADENZA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDataScadenza(), FatturaElettronica.model().DATA_SCADENZA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_PROTOCOLLAZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDataProtocollazione(), FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().PROTOCOLLO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getProtocollo(), FatturaElettronica.model().PROTOCOLLO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().XML,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getXml(), FatturaElettronica.model().XML.getFieldType()));
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_notifica_decorrenza_termini","?");
		}
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_contabilizzazione","?");
		}
		if(setIdMappingResolutionBehaviour){
			sqlQueryObjectUpdate.addUpdateField("id_scadenza","?");
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_fatturaElettronica.add(new JDBCObject(id_fatturaElettronica_notificaDecorrenzaTermini, Long.class));
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_fatturaElettronica.add(new JDBCObject(id_fatturaElettronica_pccTracciaTrasmissioneEsito, Long.class));
		}
		if(setIdMappingResolutionBehaviour){
			lstObjects_fatturaElettronica.add(new JDBCObject(id_fatturaElettronica_pccTracciaTrasmissioneEsitoInstance2, Long.class));
		}
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_fatturaElettronica.add(new JDBCObject(tableId, Long.class));

		if(isUpdate_fatturaElettronica) {
			// Update fatturaElettronica
			jdbcUtilities.executeUpdate(sqlQueryObjectUpdate.createSQLUpdate(), jdbcProperties.isShowSql(), 
				lstObjects_fatturaElettronica.toArray(new JDBCObject[]{}));
		}


	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getFatturaElettronicaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getFatturaElettronicaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura id, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()), 
				this._getMapTableToPKColumn(), 
				this._getRootTablePrimaryKeyValues(jdbcProperties, log, connection, sqlQueryObject, id),
				this.getFatturaElettronicaFieldConverter(), this, updateModels);
	}	
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getFatturaElettronicaFieldConverter(), this, null, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, IExpression condition, UpdateField ... updateFields) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getFatturaElettronicaFieldConverter(), this, condition, updateFields);
	}
	
	@Override
	public void updateFields(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, UpdateModel ... updateModels) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		java.util.List<Object> ids = new java.util.ArrayList<Object>();
		ids.add(tableId);
		JDBCUtilities.updateFields(jdbcProperties, log, connection, sqlQueryObject, 
				this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()), 
				this._getMapTableToPKColumn(), 
				ids,
				this.getFatturaElettronicaFieldConverter(), this, updateModels);
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura oldId, FatturaElettronica fatturaElettronica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, fatturaElettronica,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, fatturaElettronica,idMappingResolutionBehaviour);
		}
		
	}
	
	@Override
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, FatturaElettronica fatturaElettronica, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException,ServiceException,Exception {
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, tableId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, tableId, fatturaElettronica,idMappingResolutionBehaviour);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, fatturaElettronica,idMappingResolutionBehaviour);
		}
	}
	
	@Override
	public void delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, FatturaElettronica fatturaElettronica) throws NotImplementedException,ServiceException,Exception {
		
		
		Long longId = null;
		if( (fatturaElettronica.getId()!=null) && (fatturaElettronica.getId()>0) ){
			longId = fatturaElettronica.getId();
		}
		else{
			IdFattura idFatturaElettronica = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,fatturaElettronica);
			longId = this.findIdFatturaElettronica(jdbcProperties,log,connection,sqlQueryObject,idFatturaElettronica,false);
			if(longId == null){
				return; // entry not exists
			}
		}		
		
		this._delete(jdbcProperties, log, connection, sqlQueryObject, longId);
		
	}

	private void _delete(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long id) throws NotImplementedException,ServiceException,Exception {
	
		if(id!=null && id.longValue()<=0){
			throw new ServiceException("Id is less equals 0");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObject.newSQLQueryObject();
		

		// Object fatturaElettronica
		sqlQueryObjectDelete.setANDLogicOperator(true);
		sqlQueryObjectDelete.addDeleteTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		if(id != null)
			sqlQueryObjectDelete.addWhereCondition("id=?");

		// Delete fatturaElettronica
		jdbcUtilities.execute(sqlQueryObjectDelete.createSQLDelete(), jdbcProperties.isShowSql(), 
			new JDBCObject(id,Long.class));

	}

	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura idFatturaElettronica) throws NotImplementedException,ServiceException,Exception {

		Long id = null;
		try{
			id = this.findIdFatturaElettronica(jdbcProperties, log, connection, sqlQueryObject, idFatturaElettronica, true);
		}catch(NotFoundException notFound){
			return;
		}
		this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		
	}
	
	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject) throws NotImplementedException,ServiceException,Exception {
		
		return this.deleteAll(jdbcProperties, log, connection, sqlQueryObject, new JDBCExpression(this.getFatturaElettronicaFieldConverter()));

	}

	@Override
	public NonNegativeNumber deleteAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {

		java.util.List<Long> lst = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, new JDBCPaginatedExpression(expression));
		
		for(Long id : lst) {
			this._delete(jdbcProperties, log, connection, sqlQueryObject, id);
		}
		
		return new NonNegativeNumber(lst.size());
	
	}



	// -- DB
	
	@Override
	public void deleteById(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws ServiceException, NotImplementedException, Exception {
		this._delete(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
}
