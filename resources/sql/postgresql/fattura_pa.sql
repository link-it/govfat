CREATE SEQUENCE seq_lotti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

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
	codice_destinatario VARCHAR(7) NOT NULL,
	xml BYTEA NOT NULL,
	fatturazione_attiva BOOLEAN NOT NULL,
	stato_elaborazione_in_uscita VARCHAR(255),
	tipi_comunicazione VARCHAR(255),
	data_ultima_elaborazione TIMESTAMP,
	dettaglio_elaborazione VARCHAR(255),
	data_prossima_elaborazione TIMESTAMP,
	tentativi_consegna INT NOT NULL,
	data_ricezione DATE NOT NULL,
	stato_inserimento VARCHAR(255) NOT NULL,
	stato_consegna VARCHAR(255) NOT NULL,
	data_consegna TIMESTAMP,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	data_protocollazione TIMESTAMP,
	protocollo TEXT,
	id_egov VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_lotti') NOT NULL,
	-- check constraints
	CONSTRAINT chk_lotti_1 CHECK (formato_trasmissione IN ('FPA12','FPR12','SDI11','SDI10')),
	CONSTRAINT chk_lotti_2 CHECK (formato_archivio_invio_fattura IN ('XML','P7M')),
	CONSTRAINT chk_lotti_3 CHECK (stato_elaborazione_in_uscita IN ('PRESA_IN_CARICO','IN_CORSO_DI_PROTOCOLLAZIONE','IN_CORSO_DI_FIRMA','PROTOCOLLATA','ERRORE_DI_FIRMA','ERRORE_DI_PROTOCOLLO','ERRORE_DI_SPEDIZIONE','RICEVUTA_DALLO_SDI','RICEVUTO_SCARTO_SDI','RICEVUTA_DAL_DESTINATARIO','MANCATA_CONSEGNA','IMPOSSIBILITA_DI_RECAPITO','RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE','RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO','RICEVUTA_DECORRENZA_TERMINI','SOLO_CONSERVAZIONE')),
	CONSTRAINT chk_lotti_4 CHECK (stato_inserimento IN ('NON_INSERITO','ERRORE_INSERIMENTO','INSERITO')),
	CONSTRAINT chk_lotti_5 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_lotti_6 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	-- unique constraints
	CONSTRAINT unique_lotti_1 UNIQUE (identificativo_sdi,fatturazione_attiva),
	-- fk/pk keys constraints
	CONSTRAINT pk_lotti PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_cp_denominazione ON lotti (cp_nazione,cp_codicefiscale);



CREATE SEQUENCE seq_decorrenza_termini start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE decorrenza_termini
(
	identificativo_sdi INT NOT NULL,
	nome_file VARCHAR(50) NOT NULL,
	descrizione VARCHAR(255),
	message_id VARCHAR(14) NOT NULL,
	note VARCHAR(255),
	data_ricezione TIMESTAMP NOT NULL,
	xml BYTEA NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_decorrenza_termini') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_decorrenza_termini_1 UNIQUE (identificativo_sdi),
	-- fk/pk keys constraints
	CONSTRAINT pk_decorrenza_termini PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_tracce start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_tracce
(
	data_creazione TIMESTAMP NOT NULL,
	cf_trasmittente VARCHAR(255) NOT NULL,
	versione_applicativa VARCHAR(255) NOT NULL,
	id_pcc_amministrazione INT NOT NULL,
	id_pa_transazione VARCHAR(255),
	id_pa_transazione_rispedizione VARCHAR(255),
	sistema_richiedente VARCHAR(255) NOT NULL,
	utente_richiedente VARCHAR(255) NOT NULL,
	id_fattura BIGINT,
	codice_dipartimento VARCHAR(7),
	richiesta_xml BYTEA NOT NULL,
	risposta_xml BYTEA,
	operazione VARCHAR(255) NOT NULL,
	tipo_operazione VARCHAR(255) NOT NULL,
	stato VARCHAR(255) NOT NULL,
	data_ultima_trasmissione TIMESTAMP NOT NULL,
	data_ultimo_tentativo_esito TIMESTAMP,
	codici_errore VARCHAR(1000),
	rispedizione BOOLEAN NOT NULL DEFAULT false,
	rispedizione_dopo_query BOOLEAN NOT NULL,
	rispedizione_max_tentativi INT,
	rispedizione_prox_tentativo TIMESTAMP,
	rispedizione_numero_tentativi INT,
	rispedizione_ultimo_tentativo TIMESTAMP,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_tracce') NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_tracce_1 CHECK (tipo_operazione IN ('PROXY','READ')),
	CONSTRAINT chk_pcc_tracce_2 CHECK (stato IN ('S_OK','S_ERRORE','AS_PRESA_IN_CARICO','AS_ERRORE_PRESA_IN_CARICO','AS_OK','AS_ERRORE')),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_tracce PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_tracce_trasmissioni start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_tracce_trasmissioni
(
	ts_trasmissione TIMESTAMP NOT NULL,
	id_pcc_transazione VARCHAR(255),
	esito_trasmissione VARCHAR(255) NOT NULL,
	stato_esito VARCHAR(255) NOT NULL,
	gdo TIMESTAMP NOT NULL,
	data_fine_elaborazione TIMESTAMP,
	dettaglio_errore_trasmissione TEXT,
	id_egov_richiesta VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_tracce_trasmissioni') NOT NULL,
	id_pcc_traccia BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_tracce_trasmissioni_1 CHECK (esito_trasmissione IN ('OK','KO')),
	CONSTRAINT chk_pcc_tracce_trasmissioni_2 CHECK (stato_esito IN ('PRESENTE','NON_PRESENTE')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_tracce_trasmissioni_1 FOREIGN KEY (id_pcc_traccia) REFERENCES pcc_tracce(id),
	CONSTRAINT pk_pcc_tracce_trasmissioni PRIMARY KEY (id)
);




CREATE SEQUENCE seq_tracce_trasmissioni_esiti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE tracce_trasmissioni_esiti
(
	esito_elaborazione VARCHAR(255) NOT NULL,
	descrizione_elaborazione TEXT,
	data_fine_elaborazione TIMESTAMP,
	gdo TIMESTAMP NOT NULL,
	esito_trasmissione VARCHAR(255) NOT NULL,
	dettaglio_errore_trasmissione TEXT,
	id_egov_richiesta VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_tracce_trasmissioni_esiti') NOT NULL,
	id_pcc_traccia_trasmissione BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_esiti_1 CHECK (esito_trasmissione IN ('OK','KO')),
	-- fk/pk keys constraints
	CONSTRAINT fk_tracce_trasmissioni_esiti_1 FOREIGN KEY (id_pcc_traccia_trasmissione) REFERENCES pcc_tracce_trasmissioni(id),
	CONSTRAINT pk_tracce_trasmissioni_esiti PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_errori_elaborazione start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_errori_elaborazione
(
	tipo_operazione VARCHAR(255),
	progressivo_operazione INT,
	codice_esito VARCHAR(255) NOT NULL,
	descrizione_esito TEXT,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_errori_elaborazione') NOT NULL,
	id_esito BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_errori_elaborazione_1 CHECK (tipo_operazione IN ('CO','CP','SP','CS','RF','RC','CCS','SC')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_errori_elaborazione_1 FOREIGN KEY (id_esito) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT pk_pcc_errori_elaborazione PRIMARY KEY (id)
);




CREATE SEQUENCE seq_fatture start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE fatture
(
	formato_trasmissione VARCHAR(255) NOT NULL,
	identificativo_sdi INT NOT NULL,
	fatturazione_attiva BOOLEAN NOT NULL,
	data_ricezione TIMESTAMP NOT NULL,
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
	codice_destinatario VARCHAR(7) NOT NULL,
	tipo_documento VARCHAR(255) NOT NULL,
	divisa VARCHAR(3) NOT NULL,
	data DATE NOT NULL,
	anno INT NOT NULL,
	numero VARCHAR(20) NOT NULL,
	esito VARCHAR(255),
	da_pagare BOOLEAN NOT NULL,
	importo_totale_documento DOUBLE PRECISION NOT NULL,
	importo_totale_riepilogo DOUBLE PRECISION NOT NULL,
	causale VARCHAR(1000),
	stato_consegna VARCHAR(255) NOT NULL DEFAULT 'NON_CONSEGNATA',
	data_consegna TIMESTAMP,
	data_prossima_consegna TIMESTAMP,
	tentativi_consegna INT NOT NULL,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	data_scadenza TIMESTAMP,
	data_protocollazione TIMESTAMP,
	protocollo VARCHAR(255),
	xml BYTEA NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_fatture') NOT NULL,
	id_notifica_decorrenza_termini BIGINT,
	id_contabilizzazione BIGINT,
	id_scadenza BIGINT,
	-- check constraints
	CONSTRAINT chk_fatture_1 CHECK (formato_trasmissione IN ('FPA12','FPR12','SDI11','SDI10')),
	CONSTRAINT chk_fatture_2 CHECK (tipo_documento IN ('TD01','TD02','TD03','TD04','TD05','TD06')),
	CONSTRAINT chk_fatture_3 CHECK (esito IN ('IN_ELABORAZIONE_ACCETTATO','IN_ELABORAZIONE_RIFIUTATO','INVIATA_ACCETTATO','INVIATA_RIFIUTATO','SCARTATA_ACCETTATO','SCARTATA_RIFIUTATO')),
	CONSTRAINT chk_fatture_4 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_fatture_5 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	-- unique constraints
	CONSTRAINT unique_fatture_1 UNIQUE (identificativo_sdi,posizione,fatturazione_attiva),
	-- fk/pk keys constraints
	CONSTRAINT fk_fatture_1 FOREIGN KEY (id_notifica_decorrenza_termini) REFERENCES decorrenza_termini(id) ON DELETE CASCADE,
	CONSTRAINT fk_fatture_2 FOREIGN KEY (id_contabilizzazione) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT fk_fatture_3 FOREIGN KEY (id_scadenza) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT pk_fatture PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_cp_denominazione_fatt ON fatture (cp_nazione,cp_codicefiscale);



CREATE SEQUENCE seq_allegati start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE allegati
(
	nome_attachment VARCHAR(60) NOT NULL,
	algoritmo_compressione VARCHAR(10),
	formato_attachment VARCHAR(10),
	descrizione_attachment VARCHAR(100),
	attachment BYTEA NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_allegati') NOT NULL,
	id_fattura_elettronica BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_allegati_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_allegati PRIMARY KEY (id)
);




CREATE SEQUENCE seq_tracce_sdi start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE tracce_sdi
(
	identificativo_sdi INT NOT NULL,
	posizione INT,
	tipo_comunicazione VARCHAR(255) NOT NULL,
	nome_file VARCHAR(50) NOT NULL,
	data TIMESTAMP NOT NULL,
	id_egov VARCHAR(255) NOT NULL,
	content_type VARCHAR(255) NOT NULL,
	raw_data BYTEA,
	stato_protocollazione VARCHAR(255) NOT NULL,
	data_protocollazione TIMESTAMP,
	data_prossima_protocollazione TIMESTAMP,
	tentativi_protocollazione INT NOT NULL,
	dettaglio_protocollazione VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_tracce_sdi') NOT NULL,
	-- check constraints
	CONSTRAINT chk_tracce_sdi_1 CHECK (tipo_comunicazione IN ('FAT_OUT','FAT_IN','RC','NS','MC','NE','MT','EC','SE','DT','AT')),
	CONSTRAINT chk_tracce_sdi_2 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	-- fk/pk keys constraints
	CONSTRAINT pk_tracce_sdi PRIMARY KEY (id)
);




CREATE SEQUENCE seq_metadati start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE metadati
(
	richiesta BOOLEAN NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_metadati') NOT NULL,
	id_traccia_sdi BIGINT,
	-- fk/pk keys constraints
	CONSTRAINT fk_metadati_1 FOREIGN KEY (id_traccia_sdi) REFERENCES tracce_sdi(id),
	CONSTRAINT pk_metadati PRIMARY KEY (id)
);




CREATE SEQUENCE seq_enti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE enti
(
	nome VARCHAR(255) NOT NULL,
	id_pcc_amministrazione VARCHAR(255),
	cf_auth VARCHAR(255),
	descrizione VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_enti') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_enti_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_enti PRIMARY KEY (id)
);




CREATE SEQUENCE seq_protocolli start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE protocolli
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	endpoint VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_protocolli') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_protocolli_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_protocolli PRIMARY KEY (id)
);




CREATE SEQUENCE seq_registri start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE registri
(
	nome VARCHAR(255) NOT NULL,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(35) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_registri') NOT NULL,
	id_protocollo BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_1 FOREIGN KEY (id_protocollo) REFERENCES protocolli(id),
	CONSTRAINT pk_registri PRIMARY KEY (id)
);




CREATE SEQUENCE seq_registri_props start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE registri_props
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_registri_props') NOT NULL,
	id_protocollo BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_props_1 UNIQUE (nome,id_protocollo),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_props_1 FOREIGN KEY (id_protocollo) REFERENCES protocolli(id),
	CONSTRAINT pk_registri_props PRIMARY KEY (id)
);




CREATE SEQUENCE seq_registri_prop_values start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE registri_prop_values
(
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_registri_prop_values') NOT NULL,
	id_registro BIGINT NOT NULL,
	id_registro_property BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_prop_values_1 UNIQUE (id_registro_property,id_registro),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_prop_values_1 FOREIGN KEY (id_registro) REFERENCES registri(id),
	CONSTRAINT fk_registri_prop_values_2 FOREIGN KEY (id_registro_property) REFERENCES registri_props(id),
	CONSTRAINT pk_registri_prop_values PRIMARY KEY (id)
);




CREATE SEQUENCE seq_utenti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

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
	id BIGINT DEFAULT nextval('seq_utenti') NOT NULL,
	-- check constraints
	CONSTRAINT chk_utenti_1 CHECK (role IN ('ADMIN','DEPT_ADMIN','USER')),
	CONSTRAINT chk_utenti_2 CHECK (tipo IN ('INTERNO','ESTERNO')),
	-- unique constraints
	CONSTRAINT unique_utenti_1 UNIQUE (username),
	-- fk/pk keys constraints
	CONSTRAINT pk_utenti PRIMARY KEY (id)
);




CREATE SEQUENCE seq_esito_committente start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

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
	data_invio_ente TIMESTAMP NOT NULL,
	data_invio_sdi TIMESTAMP,
	stato_consegna_sdi VARCHAR(255) NOT NULL DEFAULT 'NON_CONSEGNATA',
	data_ultima_consegna_sdi TIMESTAMP,
	data_prossima_consegna_sdi TIMESTAMP,
	tentativi_consegna_sdi INT NOT NULL,
	scarto VARCHAR(255),
	scarto_note VARCHAR(255),
	scarto_xml BYTEA,
	xml BYTEA,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_esito_committente') NOT NULL,
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
);




CREATE SEQUENCE seq_dipartimenti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dipartimenti
(
	codice VARCHAR(7) NOT NULL,
	descrizione VARCHAR(255) NOT NULL,
	fatturazione_attiva BOOLEAN NOT NULL DEFAULT false,
	id_procedimento VARCHAR(255),
	firma_automatica BOOLEAN NOT NULL DEFAULT false,
	accettazione_automatica BOOLEAN NOT NULL DEFAULT false,
	modalita_push BOOLEAN NOT NULL DEFAULT true,
	lista_email_notifiche TEXT,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_dipartimenti') NOT NULL,
	id_ente BIGINT NOT NULL,
	id_registro BIGINT,
	-- unique constraints
	CONSTRAINT unique_dipartimenti_1 UNIQUE (codice),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
	CONSTRAINT fk_dipartimenti_2 FOREIGN KEY (id_registro) REFERENCES registri(id),
	CONSTRAINT pk_dipartimenti PRIMARY KEY (id)
);




CREATE SEQUENCE seq_dipartimenti_props start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dipartimenti_props
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_dipartimenti_props') NOT NULL,
	id_ente BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_dipartimenti_props_1 UNIQUE (nome,id_ente),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_props_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
	CONSTRAINT pk_dipartimenti_props PRIMARY KEY (id)
);




CREATE SEQUENCE seq_dipartimenti_prop_values start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dipartimenti_prop_values
(
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_dipartimenti_prop_values') NOT NULL,
	id_dipartimento BIGINT NOT NULL,
	id_dipartimento_property BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT un_dipart_prop_values_1 UNIQUE (id_dipartimento_property,id_dipartimento),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipart_prop_values_1 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT fk_dipart_prop_values_2 FOREIGN KEY (id_dipartimento_property) REFERENCES dipartimenti_props(id),
	CONSTRAINT pk_dipart_prop_values PRIMARY KEY (id)
);




CREATE SEQUENCE seq_utenti_dipartimenti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE utenti_dipartimenti
(
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_utenti_dipartimenti') NOT NULL,
	id_utente BIGINT NOT NULL,
	id_dipartimento BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_utenti_dipartimenti_1 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT fk_utenti_dipartimenti_2 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT pk_utenti_dipartimenti PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_operazioni start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_operazioni
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_operazioni') NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_operazioni_1 CHECK (nome IN ('ConsultazioneTracce','DatiFattura','PagamentoIva','InserimentoFattura','StatoFattura','ElencoMovimentiErarioIva','DownloadDocumento','OperazioneContabile_CP','OperazioneContabile_CO','OperazioneContabile_CS','OperazioneContabile_CCS','OperazioneContabile_CPS','OperazioneContabile_CSPC','OperazioneContabile_SP','OperazioneContabile_RF','OperazioneContabile_SC','OperazioneContabile_RC')),
	-- unique constraints
	CONSTRAINT unique_pcc_operazioni_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_operazioni PRIMARY KEY (id)
);




CREATE SEQUENCE seq_dipartimenti_operazioni start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dipartimenti_operazioni
(
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_dipartimenti_operazioni') NOT NULL,
	id_dipartimento BIGINT NOT NULL,
	id_pcc_operazione BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_operazioni_1 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT fk_dipartimenti_operazioni_2 FOREIGN KEY (id_pcc_operazione) REFERENCES pcc_operazioni(id),
	CONSTRAINT pk_dipartimenti_operazioni PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_utenti_operazioni start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_utenti_operazioni
(
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_utenti_operazioni') NOT NULL,
	id_utente BIGINT NOT NULL,
	id_pcc_operazione BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_utenti_operazioni_1 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT fk_pcc_utenti_operazioni_2 FOREIGN KEY (id_pcc_operazione) REFERENCES pcc_operazioni(id),
	CONSTRAINT pk_pcc_utenti_operazioni PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_contabilizzazioni start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_contabilizzazioni
(
	importo_movimento DOUBLE PRECISION NOT NULL,
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
	data_richiesta TIMESTAMP NOT NULL,
	data_query TIMESTAMP,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_contabilizzazioni') NOT NULL,
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
);




CREATE SEQUENCE seq_pcc_scadenze start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_scadenze
(
	importo_in_scadenza DOUBLE PRECISION,
	importo_iniziale DOUBLE PRECISION,
	pagato_ricontabilizzato DOUBLE PRECISION,
	data_scadenza DATE,
	sistema_richiedente VARCHAR(255) NOT NULL,
	utente_richiedente VARCHAR(255) NOT NULL,
	data_richiesta TIMESTAMP NOT NULL,
	data_query TIMESTAMP,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_scadenze') NOT NULL,
	id_fattura_elettronica BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_scadenze_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_pcc_scadenze PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_pagamenti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_pagamenti
(
	importo_pagato DOUBLE PRECISION NOT NULL,
	natura_spesa VARCHAR(255) NOT NULL,
	capitoli_spesa VARCHAR(255),
	estremi_impegno VARCHAR(255),
	numero_mandato VARCHAR(255),
	data_mandato DATE,
	id_fiscale_iva_beneficiario VARCHAR(255) NOT NULL,
	codice_cig VARCHAR(255),
	codice_cup VARCHAR(255),
	descrizione VARCHAR(255),
	data_richiesta TIMESTAMP NOT NULL,
	data_query TIMESTAMP,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_pagamenti') NOT NULL,
	id_fattura_elettronica BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_pagamenti_1 CHECK (natura_spesa IN ('CO','CA','NA')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_pagamenti_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_pcc_pagamenti PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_rispedizioni start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_rispedizioni
(
	max_numero_tentativi INT NOT NULL,
	intervallo_tentativi INT NOT NULL,
	codice_errore VARCHAR(255) NOT NULL,
	abilitato BOOLEAN NOT NULL,
	descrizione_errore TEXT NOT NULL,
	tipo_errore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_rispedizioni') NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_rispedizioni_1 CHECK (tipo_errore IN ('TRASMISSIONE','ELABORAZIONE')),
	-- unique constraints
	CONSTRAINT unique_pcc_rispedizioni_1 UNIQUE (codice_errore),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_rispedizioni PRIMARY KEY (id)
);




CREATE SEQUENCE seq_pcc_notifiche start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pcc_notifiche
(
	stato_consegna VARCHAR(255) NOT NULL,
	data_creazione TIMESTAMP NOT NULL,
	data_consegna TIMESTAMP,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pcc_notifiche') NOT NULL,
	id_pcc_traccia BIGINT NOT NULL,
	id_dipartimento BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_notifiche_1 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_notifiche_1 FOREIGN KEY (id_pcc_traccia) REFERENCES pcc_tracce(id),
	CONSTRAINT fk_pcc_notifiche_2 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT pk_pcc_notifiche PRIMARY KEY (id)
);




CREATE SEQUENCE seq_notifiche_eventi start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE notifiche_eventi
(
	tipo VARCHAR(255) NOT NULL,
	codice VARCHAR(255) NOT NULL,
	severita INT NOT NULL,
	ora_registrazione TIMESTAMP NOT NULL,
	descrizione VARCHAR(255),
	id_transazione VARCHAR(255),
	configurazione TEXT,
	cluster_id VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_notifiche_eventi') NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_notifiche_eventi PRIMARY KEY (id)
);

-- index
CREATE INDEX index_notifiche_eventi_1 ON notifiche_eventi (ora_registrazione);


