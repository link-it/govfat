CREATE TABLE lotti
(
	formato_trasmissione VARCHAR(255) NOT NULL,
	identificativo_sdi INT NOT NULL,
	nome_file VARCHAR(50) NOT NULL,
	formato_archivio_invio_fattura VARCHAR(255) NOT NULL,
	message_id VARCHAR(14) NOT NULL,
	cp_denominazione VARCHAR(80),
	cp_nome VARCHAR(80),
	cp_cognome VARCHAR(80),
	cp_idcodice VARCHAR(80),
	cp_nazione VARCHAR(2),
	cp_codicefiscale VARCHAR(28),
	cc_denominazione VARCHAR(80),
	cc_nome VARCHAR(80),
	cc_cognome VARCHAR(80),
	cc_idcodice VARCHAR(80),
	cc_nazione VARCHAR(2),
	cc_codicefiscale VARCHAR(28),
	se_denominazione VARCHAR(80),
	se_nome VARCHAR(80),
	se_cognome VARCHAR(80),
	se_idcodice VARCHAR(80),
	se_nazione VARCHAR(2),
	se_codicefiscale VARCHAR(28),
	codice_destinatario VARCHAR(6) NOT NULL,
	xml MEDIUMBLOB NOT NULL,
	data_ricezione TIMESTAMP NOT NULL DEFAULT 0,
	stato_inserimento VARCHAR(255) NOT NULL,
	stato_consegna VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_consegna TIMESTAMP(3) DEFAULT 0,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_protocollazione TIMESTAMP(3) DEFAULT 0,
	protocollo LONGTEXT,
	id_egov VARCHAR(255),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- check constraints
	CONSTRAINT chk_lotti_1 CHECK (formato_trasmissione IN ('SDI11','SDI10')),
	CONSTRAINT chk_lotti_2 CHECK (formato_archivio_invio_fattura IN ('XML','P7M')),
	CONSTRAINT chk_lotti_3 CHECK (stato_inserimento IN ('NON_INSERITO','ERRORE_INSERIMENTO','INSERITO')),
	CONSTRAINT chk_lotti_4 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_lotti_5 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	-- unique constraints
	CONSTRAINT unique_lotti_1 UNIQUE (identificativo_sdi),
	-- fk/pk keys constraints
	CONSTRAINT pk_lotti PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_lotti_1 ON lotti (identificativo_sdi);
CREATE INDEX idx_cp_denominazione ON lotti (cp_nazione,cp_codicefiscale);



CREATE TABLE decorrenza_termini
(
	identificativo_sdi INT NOT NULL,
	nome_file VARCHAR(50) NOT NULL,
	descrizione VARCHAR(255),
	message_id VARCHAR(14) NOT NULL,
	note VARCHAR(255),
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_ricezione TIMESTAMP(3) NOT NULL DEFAULT 0,
	xml MEDIUMBLOB NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- unique constraints
	CONSTRAINT unique_decorrenza_termini_1 UNIQUE (identificativo_sdi),
	-- fk/pk keys constraints
	CONSTRAINT pk_decorrenza_termini PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_decorrenza_termini_1 ON decorrenza_termini (identificativo_sdi);



CREATE TABLE pcc_tracce
(
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_creazione TIMESTAMP(3) NOT NULL DEFAULT 0,
	cf_trasmittente VARCHAR(255) NOT NULL,
	versione_applicativa VARCHAR(255) NOT NULL,
	id_pcc_amministrazione INT NOT NULL,
	id_pa_transazione VARCHAR(255),
	id_pa_transazione_rispedizione VARCHAR(255),
	sistema_richiedente VARCHAR(255) NOT NULL,
	utente_richiedente VARCHAR(255) NOT NULL,
	id_fattura BIGINT,
	codice_dipartimento VARCHAR(6),
	richiesta_xml MEDIUMBLOB NOT NULL,
	risposta_xml MEDIUMBLOB,
	operazione VARCHAR(255) NOT NULL,
	tipo_operazione VARCHAR(255) NOT NULL,
	stato VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_ultima_trasmissione TIMESTAMP(3) NOT NULL DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_ultimo_tentativo_esito TIMESTAMP(3) DEFAULT 0,
	rispedizione BOOLEAN NOT NULL DEFAULT false,
	rispedizione_max_tentativi INT,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	rispedizione_prox_tentativo TIMESTAMP(3) DEFAULT 0,
	rispedizione_numero_tentativi INT,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	rispedizione_ultimo_tentativo TIMESTAMP(3) DEFAULT 0,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- check constraints
	CONSTRAINT chk_pcc_tracce_1 CHECK (tipo_operazione IN ('PROXY','READ')),
	CONSTRAINT chk_pcc_tracce_2 CHECK (stato IN ('S_OK','S_ERRORE','AS_PRESA_IN_CARICO','AS_ERRORE_PRESA_IN_CARICO','AS_OK','AS_ERRORE')),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_tracce PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE pcc_tracce_trasmissioni
(
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	ts_trasmissione TIMESTAMP(3) NOT NULL DEFAULT 0,
	id_pcc_transazione VARCHAR(255),
	esito_trasmissione VARCHAR(255) NOT NULL,
	stato_esito VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	gdo TIMESTAMP(3) NOT NULL DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_fine_elaborazione TIMESTAMP(3) DEFAULT 0,
	dettaglio_errore_trasmissione LONGTEXT,
	id_egov_richiesta VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_pcc_traccia BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_tracce_trasmissioni_1 CHECK (esito_trasmissione IN ('OK','KO')),
	CONSTRAINT chk_pcc_tracce_trasmissioni_2 CHECK (stato_esito IN ('PRESENTE','NON_PRESENTE')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_tracce_trasmissioni_1 FOREIGN KEY (id_pcc_traccia) REFERENCES pcc_tracce(id),
	CONSTRAINT pk_pcc_tracce_trasmissioni PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE tracce_trasmissioni_esiti
(
	esito_elaborazione VARCHAR(255) NOT NULL,
	descrizione_elaborazione LONGTEXT,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_fine_elaborazione TIMESTAMP(3) DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	gdo TIMESTAMP(3) NOT NULL DEFAULT 0,
	esito_trasmissione VARCHAR(255) NOT NULL,
	dettaglio_errore_trasmissione LONGTEXT,
	id_egov_richiesta VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_pcc_traccia_trasmissione BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_esiti_1 CHECK (esito_trasmissione IN ('OK','KO')),
	-- fk/pk keys constraints
	CONSTRAINT fk_tracce_trasmissioni_esiti_1 FOREIGN KEY (id_pcc_traccia_trasmissione) REFERENCES pcc_tracce_trasmissioni(id),
	CONSTRAINT pk_tracce_trasmissioni_esiti PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE pcc_errori_elaborazione
(
	tipo_operazione VARCHAR(255),
	progressivo_operazione INT,
	codice_esito VARCHAR(255) NOT NULL,
	descrizione_esito LONGTEXT,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_esito BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_errori_elaborazione_1 CHECK (tipo_operazione IN ('CO','CP','SP','CS','RF','RC','CCS','SC')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_errori_elaborazione_1 FOREIGN KEY (id_esito) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT pk_pcc_errori_elaborazione PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE fatture
(
	formato_trasmissione VARCHAR(255) NOT NULL,
	identificativo_sdi INT NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_ricezione TIMESTAMP(3) NOT NULL DEFAULT 0,
	nome_file VARCHAR(50) NOT NULL,
	message_id VARCHAR(14) NOT NULL,
	cp_denominazione VARCHAR(80) NOT NULL,
	cp_nazione VARCHAR(2) NOT NULL,
	cp_codicefiscale VARCHAR(28) NOT NULL,
	cc_denominazione VARCHAR(80) NOT NULL,
	cc_nazione VARCHAR(2),
	cc_codicefiscale VARCHAR(28),
	se_denominazione VARCHAR(80),
	se_nazione VARCHAR(2),
	se_codicefiscale VARCHAR(28),
	posizione INT NOT NULL DEFAULT 1,
	codice_destinatario VARCHAR(6) NOT NULL,
	tipo_documento VARCHAR(255) NOT NULL,
	divisa VARCHAR(3) NOT NULL,
	data TIMESTAMP NOT NULL DEFAULT 0,
	anno INT NOT NULL,
	numero VARCHAR(20) NOT NULL,
	esito VARCHAR(255),
	da_pagare BOOLEAN NOT NULL,
	importo_totale_documento DOUBLE NOT NULL,
	importo_totale_riepilogo DOUBLE NOT NULL,
	causale VARCHAR(1000),
	stato_consegna VARCHAR(255) NOT NULL DEFAULT 'NON_CONSEGNATA',
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_consegna TIMESTAMP(3) DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_prossima_consegna TIMESTAMP(3) DEFAULT 0,
	tentativi_consegna INT NOT NULL,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_scadenza TIMESTAMP(3) DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_protocollazione TIMESTAMP(3) DEFAULT 0,
	protocollo VARCHAR(255),
	xml MEDIUMBLOB NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_notifica_decorrenza_termini BIGINT,
	id_contabilizzazione BIGINT,
	id_scadenza BIGINT,
	-- check constraints
	CONSTRAINT chk_fatture_1 CHECK (formato_trasmissione IN ('SDI11','SDI10')),
	CONSTRAINT chk_fatture_2 CHECK (tipo_documento IN ('TD01','TD02','TD03','TD04','TD05','TD06')),
	CONSTRAINT chk_fatture_3 CHECK (esito IN ('IN_ELABORAZIONE_ACCETTATO','IN_ELABORAZIONE_RIFIUTATO','INVIATA_ACCETTATO','INVIATA_RIFIUTATO','SCARTATA_ACCETTATO','SCARTATA_RIFIUTATO')),
	CONSTRAINT chk_fatture_4 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_fatture_5 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	-- unique constraints
	CONSTRAINT unique_fatture_1 UNIQUE (identificativo_sdi,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_fatture_1 FOREIGN KEY (id_notifica_decorrenza_termini) REFERENCES decorrenza_termini(id) ON DELETE CASCADE,
	CONSTRAINT fk_fatture_2 FOREIGN KEY (id_contabilizzazione) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT fk_fatture_3 FOREIGN KEY (id_scadenza) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT pk_fatture PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_fatture_1 ON fatture (identificativo_sdi,posizione);
CREATE INDEX idx_cp_denominazione_fatt ON fatture (cp_nazione,cp_codicefiscale);



CREATE TABLE allegati
(
	nome_attachment VARCHAR(60) NOT NULL,
	algoritmo_compressione VARCHAR(10),
	formato_attachment VARCHAR(10),
	descrizione_attachment VARCHAR(100),
	attachment MEDIUMBLOB NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_fattura_elettronica BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_allegati_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_allegati PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE comunicazioni_sdi
(
	identificativo_sdi INT NOT NULL,
	tipo_comunicazione VARCHAR(255) NOT NULL,
	progressivo INT NOT NULL,
	data_ricezione TIMESTAMP NOT NULL DEFAULT 0,
	nome_file VARCHAR(50),
	content_type VARCHAR(255) NOT NULL,
	raw_data MEDIUMBLOB NOT NULL,
	stato_consegna VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_consegna TIMESTAMP(3) DEFAULT 0,
	dettaglio_consegna VARCHAR(255),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- check constraints
	CONSTRAINT chk_comunicazioni_sdi_1 CHECK (tipo_comunicazione IN ('FATTURA_USCITA','NOTIFICA_SCARTO','RICEVUTA_CONSEGNA','NOTIFICA_MANCATA_CONSEGNA','ATTESTAZIONE_TRASMISSIONE_FATTURA','NOTIFICA_ESITO_COMMITTENTE','NOTIFICA_DECORRENZA_TERMINI_TRASMITTENTE','AVVENUTA_TRASMISSIONE_IMPOSSIBILITA_RECAPITO')),
	CONSTRAINT chk_comunicazioni_sdi_2 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	-- unique constraints
	CONSTRAINT unique_comunicazioni_sdi_1 UNIQUE (identificativo_sdi,tipo_comunicazione,progressivo),
	-- fk/pk keys constraints
	CONSTRAINT pk_comunicazioni_sdi PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_comunicazioni_sdi_1 ON comunicazioni_sdi (identificativo_sdi,tipo_comunicazione,progressivo);



CREATE TABLE metadati
(
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_comunicazione_sdi BIGINT,
	-- fk/pk keys constraints
	CONSTRAINT fk_metadati_1 FOREIGN KEY (id_comunicazione_sdi) REFERENCES comunicazioni_sdi(id),
	CONSTRAINT pk_metadati PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE enti
(
	nome VARCHAR(255) NOT NULL,
	id_pcc_amministrazione VARCHAR(255),
	cf_auth VARCHAR(255),
	descrizione VARCHAR(255),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- unique constraints
	CONSTRAINT unique_enti_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_enti PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_enti_1 ON enti (nome);



CREATE TABLE protocolli
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	endpoint VARCHAR(255) NOT NULL,
	endpoint_consegna_lotto VARCHAR(255),
	endpoint_richiedi_protocollo VARCHAR(255),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- unique constraints
	CONSTRAINT unique_protocolli_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_protocolli PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_protocolli_1 ON protocolli (nome);



CREATE TABLE registri
(
	nome VARCHAR(255) NOT NULL,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(35) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_protocollo BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_1 FOREIGN KEY (id_protocollo) REFERENCES protocolli(id),
	CONSTRAINT pk_registri PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_registri_1 ON registri (nome);



CREATE TABLE registri_props
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_protocollo BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_props_1 UNIQUE (nome,id_protocollo),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_props_1 FOREIGN KEY (id_protocollo) REFERENCES protocolli(id),
	CONSTRAINT pk_registri_props PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_registri_props_1 ON registri_props (nome,id_protocollo);



CREATE TABLE registri_prop_values
(
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_registro BIGINT NOT NULL,
	id_registro_property BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_prop_values_1 UNIQUE (id_registro_property,id_registro),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_prop_values_1 FOREIGN KEY (id_registro) REFERENCES registri(id),
	CONSTRAINT fk_registri_prop_values_2 FOREIGN KEY (id_registro_property) REFERENCES registri_props(id),
	CONSTRAINT pk_registri_prop_values PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_registri_prop_values_1 ON registri_prop_values (id_registro_property,id_registro);



CREATE TABLE utenti
(
	username VARCHAR(50) NOT NULL,
	password VARCHAR(35) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	cognome VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL,
	tipo VARCHAR(255),
	esterno BOOLEAN NOT NULL,
	sistema VARCHAR(20),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- check constraints
	CONSTRAINT chk_utenti_1 CHECK (role IN ('ADMIN','DEPT_ADMIN','USER')),
	CONSTRAINT chk_utenti_2 CHECK (tipo IN ('INTERNO','ESTERNO')),
	-- unique constraints
	CONSTRAINT unique_utenti_1 UNIQUE (username),
	-- fk/pk keys constraints
	CONSTRAINT pk_utenti PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_utenti_1 ON utenti (username);



CREATE TABLE esito_committente
(
	identificativo_sdi INT NOT NULL,
	numero_fattura VARCHAR(20),
	anno INT,
	posizione INT,
	esito VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	message_id_committente VARCHAR(14),
	nome_file VARCHAR(50) NOT NULL,
	modalita_batch BOOLEAN NOT NULL DEFAULT false,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_invio_ente TIMESTAMP(3) NOT NULL DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_invio_sdi TIMESTAMP(3) DEFAULT 0,
	stato_consegna_sdi VARCHAR(255) NOT NULL DEFAULT 'NON_CONSEGNATA',
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_ultima_consegna_sdi TIMESTAMP(3) DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_prossima_consegna_sdi TIMESTAMP(3) DEFAULT 0,
	tentativi_consegna_sdi INT NOT NULL,
	scarto VARCHAR(255),
	scarto_note VARCHAR(255),
	scarto_xml MEDIUMBLOB,
	xml MEDIUMBLOB,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_fattura_elettronica BIGINT NOT NULL,
	id_utente BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_esito_committente_1 CHECK (esito IN ('EC01','EC02')),
	CONSTRAINT chk_esito_committente_2 CHECK (stato_consegna_sdi IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_esito_committente_3 CHECK (scarto IN ('EN00','EN01')),
	-- fk/pk keys constraints
	CONSTRAINT fk_esito_committente_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT fk_esito_committente_2 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT pk_esito_committente PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE dipartimenti
(
	codice VARCHAR(6) NOT NULL,
	descrizione VARCHAR(255) NOT NULL,
	accettazione_automatica BOOLEAN NOT NULL DEFAULT false,
	modalita_push BOOLEAN NOT NULL DEFAULT true,
	lista_email_notifiche LONGTEXT,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_ente BIGINT NOT NULL,
	id_registro BIGINT,
	-- unique constraints
	CONSTRAINT unique_dipartimenti_1 UNIQUE (codice),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
	CONSTRAINT fk_dipartimenti_2 FOREIGN KEY (id_registro) REFERENCES registri(id),
	CONSTRAINT pk_dipartimenti PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_dipartimenti_1 ON dipartimenti (codice);



CREATE TABLE dipartimenti_props
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_ente BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_dipartimenti_props_1 UNIQUE (nome,id_ente),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_props_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
	CONSTRAINT pk_dipartimenti_props PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_dipartimenti_props_1 ON dipartimenti_props (nome,id_ente);



CREATE TABLE dipartimenti_prop_values
(
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_dipartimento BIGINT NOT NULL,
	id_dipartimento_property BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT un_dipart_prop_values_1 UNIQUE (id_dipartimento_property,id_dipartimento),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipart_prop_values_1 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT fk_dipart_prop_values_2 FOREIGN KEY (id_dipartimento_property) REFERENCES dipartimenti_props(id),
	CONSTRAINT pk_dipart_prop_values PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX idx_dipart_prop_values_1 ON dipartimenti_prop_values (id_dipartimento_property,id_dipartimento);



CREATE TABLE utenti_dipartimenti
(
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_utente BIGINT NOT NULL,
	id_dipartimento BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_utenti_dipartimenti_1 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT fk_utenti_dipartimenti_2 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT pk_utenti_dipartimenti PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE pcc_operazioni
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- check constraints
	CONSTRAINT chk_pcc_operazioni_1 CHECK (nome IN ('ConsultazioneTracce','DatiFattura','PagamentoIva','InserimentoFattura','StatoFattura','ElencoMovimentiErarioIva','DownloadDocumento','OperazioneContabile_CP','OperazioneContabile_CO','OperazioneContabile_CS','OperazioneContabile_CCS','OperazioneContabile_SP','OperazioneContabile_RF','OperazioneContabile_SC','OperazioneContabile_RC')),
	-- unique constraints
	CONSTRAINT unique_pcc_operazioni_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_operazioni PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_pcc_operazioni_1 ON pcc_operazioni (nome);



CREATE TABLE dipartimenti_operazioni
(
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_dipartimento BIGINT NOT NULL,
	id_pcc_operazione BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_operazioni_1 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT fk_dipartimenti_operazioni_2 FOREIGN KEY (id_pcc_operazione) REFERENCES pcc_operazioni(id),
	CONSTRAINT pk_dipartimenti_operazioni PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE pcc_utenti_operazioni
(
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_utente BIGINT NOT NULL,
	id_pcc_operazione BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_utenti_operazioni_1 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT fk_pcc_utenti_operazioni_2 FOREIGN KEY (id_pcc_operazione) REFERENCES pcc_operazioni(id),
	CONSTRAINT pk_pcc_utenti_operazioni PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE pcc_contabilizzazioni
(
	importo_movimento DOUBLE NOT NULL,
	natura_spesa VARCHAR(255) NOT NULL,
	capitoli_spesa VARCHAR(255),
	stato_debito VARCHAR(255) NOT NULL,
	causale VARCHAR(255),
	descrizione VARCHAR(255),
	estremi_impegno VARCHAR(255),
	codice_cig VARCHAR(255) NOT NULL,
	codice_cup VARCHAR(255) NOT NULL,
	id_importo VARCHAR(255) NOT NULL,
	sistema_richiedente VARCHAR(255) NOT NULL,
	utente_richiedente VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_richiesta TIMESTAMP(3) NOT NULL DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_query TIMESTAMP(3) DEFAULT 0,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_fattura_elettronica BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_contabilizzazioni_1 CHECK (natura_spesa IN ('CO','CA','NA')),
	CONSTRAINT chk_pcc_contabilizzazioni_2 CHECK (stato_debito IN ('LIQ','NOLIQ','SOSP')),
	CONSTRAINT chk_pcc_contabilizzazioni_3 CHECK (causale IN ('ATTLIQ','CONT','ATTNC','NCRED','PAGTERZI','IVARC')),
	-- unique constraints
	CONSTRAINT unique_pcc_contabilizzazioni_1 UNIQUE (id_importo,sistema_richiedente,id_fattura_elettronica),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_contabilizzazioni_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_pcc_contabilizzazioni PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_pcc_contabilizzazioni_1 ON pcc_contabilizzazioni (id_importo,sistema_richiedente,id_fattura_elettronica);



CREATE TABLE pcc_scadenze
(
	importo_in_scadenza DOUBLE,
	importo_iniziale DOUBLE,
	pagato_ricontabilizzato DOUBLE,
	data_scadenza TIMESTAMP DEFAULT 0,
	sistema_richiedente VARCHAR(255) NOT NULL,
	utente_richiedente VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_richiesta TIMESTAMP(3) NOT NULL DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_query TIMESTAMP(3) DEFAULT 0,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_fattura_elettronica BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_scadenze_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_pcc_scadenze PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE pcc_pagamenti
(
	importo_pagato DOUBLE NOT NULL,
	natura_spesa VARCHAR(255) NOT NULL,
	capitoli_spesa VARCHAR(255),
	estremi_impegno VARCHAR(255),
	numero_mandato VARCHAR(255),
	data_mandato TIMESTAMP DEFAULT 0,
	id_fiscale_iva_beneficiario VARCHAR(255) NOT NULL,
	codice_cig VARCHAR(255),
	codice_cup VARCHAR(255),
	descrizione VARCHAR(255),
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_richiesta TIMESTAMP(3) NOT NULL DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_query TIMESTAMP(3) DEFAULT 0,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_fattura_elettronica BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_pagamenti_1 CHECK (natura_spesa IN ('CO','CA','NA')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_pagamenti_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_pcc_pagamenti PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE pcc_rispedizioni
(
	max_numero_tentativi INT NOT NULL,
	intervallo_tentativi INT NOT NULL,
	codice_errore VARCHAR(255) NOT NULL,
	abilitato BOOLEAN NOT NULL,
	descrizione_errore LONGTEXT NOT NULL,
	tipo_errore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- check constraints
	CONSTRAINT chk_pcc_rispedizioni_1 CHECK (tipo_errore IN ('TRASMISSIONE','ELABORAZIONE')),
	-- unique constraints
	CONSTRAINT unique_pcc_rispedizioni_1 UNIQUE (codice_errore),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_rispedizioni PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_pcc_rispedizioni_1 ON pcc_rispedizioni (codice_errore);



CREATE TABLE pcc_notifiche
(
	stato_consegna VARCHAR(255) NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_creazione TIMESTAMP(3) NOT NULL DEFAULT 0,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	data_consegna TIMESTAMP(3) DEFAULT 0,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	id_pcc_traccia BIGINT NOT NULL,
	id_dipartimento BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_notifiche_1 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_notifiche_1 FOREIGN KEY (id_pcc_traccia) REFERENCES pcc_tracce(id),
	CONSTRAINT fk_pcc_notifiche_2 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT pk_pcc_notifiche PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;




CREATE TABLE notifiche_eventi
(
	tipo VARCHAR(255) NOT NULL,
	codice VARCHAR(255) NOT NULL,
	severita INT NOT NULL,
	-- Precisione ai millisecondi supportata dalla versione 5.6.4, se si utilizza una versione precedente non usare il suffisso '(3)'
	ora_registrazione TIMESTAMP(3) NOT NULL DEFAULT 0,
	descrizione VARCHAR(255),
	id_transazione VARCHAR(255),
	configurazione MEDIUMTEXT,
	cluster_id VARCHAR(255),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- fk/pk keys constraints
	CONSTRAINT pk_notifiche_eventi PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;

-- index
CREATE INDEX index_notifiche_eventi_1 ON notifiche_eventi (ora_registrazione);


