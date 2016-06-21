/*
 * ProxyFatturaPA - Gestione del formato Fattura Elettronica 
 * http://www.gov4j.it/fatturapa
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://link.it). 
 * Copyright (c) 2014-2015 Provincia Autonoma di Bolzano (http://www.provincia.bz.it/). 
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
package org.govmix.proxy.fatturapa.dao.jdbc;

import java.sql.Connection;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.apache.log4j.Logger;

import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceCRUDWithId;
import org.govmix.proxy.fatturapa.IdFattura;
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

import org.govmix.proxy.fatturapa.FatturaElettronica;
import org.govmix.proxy.fatturapa.dao.jdbc.JDBCServiceManager;

/**     
 * JDBCFatturaElettronicaServiceImpl
 *
 * @author Papandrea Giuseppe (papandrea@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCFatturaElettronicaServiceImpl extends JDBCFatturaElettronicaServiceSearchImpl
	implements IJDBCServiceCRUDWithId<FatturaElettronica, IdFattura, JDBCServiceManager> {

	@Override
	public void create(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, FatturaElettronica fatturaElettronica) throws NotImplementedException,ServiceException,Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		

		java.util.List<JDBCObject> lstObjects_fatturaElettronica = new java.util.ArrayList<JDBCObject>();
		
		// Object fatturaElettronica
		sqlQueryObjectInsert.addInsertTable(this.getFatturaElettronicaFieldConverter().toTable(FatturaElettronica.model()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().FORMATO_TRASMISSIONE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getFormatoTrasmissione(),FatturaElettronica.model().FORMATO_TRASMISSIONE.getFieldType()));

		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IDENTIFICATIVO_SDI,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getIdentificativoSdi(),FatturaElettronica.model().IDENTIFICATIVO_SDI.getFieldType()));

		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_RICEZIONE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDataRicezione(),FatturaElettronica.model().DATA_RICEZIONE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().NOME_FILE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getNomeFile(),FatturaElettronica.model().NOME_FILE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().MESSAGE_ID,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getMessageId(),FatturaElettronica.model().MESSAGE_ID.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCedentePrestatoreDenominazione(),FatturaElettronica.model().CEDENTE_PRESTATORE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCedentePrestatorePaese(),FatturaElettronica.model().CEDENTE_PRESTATORE_PAESE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCedentePrestatoreCodiceFiscale(),FatturaElettronica.model().CEDENTE_PRESTATORE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCessionarioCommittenteDenominazione(),FatturaElettronica.model().CESSIONARIO_COMMITTENTE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCessionarioCommittentePaese(),FatturaElettronica.model().CESSIONARIO_COMMITTENTE_PAESE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCessionarioCommittenteCodiceFiscale(),FatturaElettronica.model().CESSIONARIO_COMMITTENTE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittenteDenominazione(),FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_DENOMINAZIONE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittentePaese(),FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_PAESE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTerzoIntermediarioOSoggettoEmittenteCodiceFiscale(),FatturaElettronica.model().TERZO_INTERMEDIARIO_OSOGGETTO_EMITTENTE_CODICE_FISCALE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().POSIZIONE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getPosizione(),FatturaElettronica.model().POSIZIONE.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CODICE_DESTINATARIO,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCodiceDestinatario(),FatturaElettronica.model().CODICE_DESTINATARIO.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().TIPO_DOCUMENTO,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getTipoDocumento(),FatturaElettronica.model().TIPO_DOCUMENTO.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DIVISA,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDivisa(),FatturaElettronica.model().DIVISA.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getData(),FatturaElettronica.model().DATA.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().ANNO,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getAnno(),FatturaElettronica.model().ANNO.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().NUMERO,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getNumero(),FatturaElettronica.model().NUMERO.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().ESITO,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getEsito(),FatturaElettronica.model().ESITO.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getImportoTotaleDocumento(),FatturaElettronica.model().IMPORTO_TOTALE_DOCUMENTO.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getImportoTotaleRiepilogo(),FatturaElettronica.model().IMPORTO_TOTALE_RIEPILOGO.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().CAUSALE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getCausale(),FatturaElettronica.model().CAUSALE.getFieldType()));
		
		if(fatturaElettronica.getStatoConsegna() != null) {
			sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_CONSEGNA,false),"?");
			lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getStatoConsegna(),FatturaElettronica.model().STATO_CONSEGNA.getFieldType()));

		}
		
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_PROTOCOLLAZIONE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getStatoProtocollazione(),FatturaElettronica.model().STATO_PROTOCOLLAZIONE.getFieldType()));

		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_PROTOCOLLAZIONE,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDataProtocollazione(),FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType()));

		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_CONSEGNA,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDataConsegna(),FatturaElettronica.model().DATA_CONSEGNA.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DETTAGLIO_CONSEGNA,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getDettaglioConsegna(),FatturaElettronica.model().DETTAGLIO_CONSEGNA.getFieldType()));
		sqlQueryObjectInsert.addInsertField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().XML,false),"?");
		lstObjects_fatturaElettronica.add(new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(fatturaElettronica.getXml(),FatturaElettronica.model().XML.getFieldType()));
		
		if(fatturaElettronica.getIdDecorrenzaTermini() != null) {
			sqlQueryObjectInsert.addInsertField("id_notifica_decorrenza_termini", "?");
			lstObjects_fatturaElettronica.add(new JDBCObject(JDBCNotificaDecorrenzaTerminiUtils.toLongId(jdbcProperties, log, connection, sqlQueryObject, fatturaElettronica.getIdDecorrenzaTermini()), Long.class));
		}

		// Insert fatturaElettronica
		org.openspcoop2.utils.jdbc.IKeyGeneratorObject keyGenerator = this.getFatturaElettronicaFetch().getKeyGeneratorObject(FatturaElettronica.model());
		long id = jdbcUtilities.insertAndReturnGeneratedKey(sqlQueryObjectInsert, keyGenerator, jdbcProperties.isShowSql(), lstObjects_fatturaElettronica.toArray(new JDBCObject[]{}));

		fatturaElettronica.setId(id);

		
	}

	@Override
	public void update(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura oldId, FatturaElettronica fatturaElettronica) throws NotFoundException, NotImplementedException, ServiceException, Exception {
		

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
				new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectInsert = sqlQueryObject.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectDelete = sqlQueryObjectInsert.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObjectDelete.newSQLQueryObject();
		ISQLQueryObject sqlQueryObjectUpdate = sqlQueryObjectGet.newSQLQueryObject();
				
		Long longIdByLogicId = this.findIdFatturaElettronica(jdbcProperties, log, connection, sqlQueryObjectUpdate.newSQLQueryObject(), oldId, true);
		Long longId = fatturaElettronica.getId();
		if(longId != null && longId.longValue() > 0) {
			if(longId.longValue() != longIdByLogicId.longValue()) {
				throw new Exception("Ambiguous parameter: fatturaElettronica.id ["+longId+"] does not match logic id ["+longIdByLogicId+"]");
			}
		} else {
			longId = longIdByLogicId;
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
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DETTAGLIO_CONSEGNA,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDettaglioConsegna(), FatturaElettronica.model().DETTAGLIO_CONSEGNA.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().STATO_PROTOCOLLAZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getStatoProtocollazione(), FatturaElettronica.model().STATO_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().DATA_PROTOCOLLAZIONE,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getDataProtocollazione(), FatturaElettronica.model().DATA_PROTOCOLLAZIONE.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().PROTOCOLLO,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getProtocollo(), FatturaElettronica.model().PROTOCOLLO.getFieldType()));
		sqlQueryObjectUpdate.addUpdateField(this.getFatturaElettronicaFieldConverter().toColumn(FatturaElettronica.model().XML,false), "?");
		lstObjects_fatturaElettronica.add(new JDBCObject(fatturaElettronica.getXml(), FatturaElettronica.model().XML.getFieldType()));
		
		if(fatturaElettronica.getIdDecorrenzaTermini() != null) {
			sqlQueryObjectUpdate.addUpdateField("id_notifica_decorrenza_termini", "?");
			lstObjects_fatturaElettronica.add(new JDBCObject(JDBCNotificaDecorrenzaTerminiUtils.toLongId(jdbcProperties, log, connection, sqlQueryObject, fatturaElettronica.getIdDecorrenzaTermini()), Long.class));
		}
		
		sqlQueryObjectUpdate.addWhereCondition("id=?");
		lstObjects_fatturaElettronica.add(new JDBCObject(longId, Long.class));

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
	public void updateOrCreate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdFattura oldId, FatturaElettronica fatturaElettronica) throws NotImplementedException,ServiceException,Exception {
	
		if(this.exists(jdbcProperties, log, connection, sqlQueryObject, oldId)) {
			this.update(jdbcProperties, log, connection, sqlQueryObject, oldId, fatturaElettronica);
		} else {
			this.create(jdbcProperties, log, connection, sqlQueryObject, fatturaElettronica);
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
