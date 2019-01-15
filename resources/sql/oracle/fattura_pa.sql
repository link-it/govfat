CREATE SEQUENCE seq_sip MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE sip
(
	registro VARCHAR(255),
	anno NUMBER,
	numero VARCHAR(255),
	stato_consegna VARCHAR(255) NOT NULL,
	data_ultima_consegna TIMESTAMP,
	rapporto_versamento CLOB,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_sip_1 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	-- fk/pk keys constraints
	CONSTRAINT pk_sip PRIMARY KEY (id)
);


ALTER TABLE sip MODIFY stato_consegna DEFAULT 'NON_CONSEGNATA';

CREATE TRIGGER trg_sip
BEFORE
insert on sip
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_sip.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_tracce_sdi MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE tracce_sdi
(
	identificativo_sdi NUMBER NOT NULL,
	posizione NUMBER,
	tipo_comunicazione VARCHAR2(255 CHAR) NOT NULL,
	nome_file VARCHAR2(50 CHAR) NOT NULL,
	codice_dipartimento VARCHAR2(7 CHAR) NOT NULL,
	data TIMESTAMP NOT NULL,
	id_egov VARCHAR2(255 CHAR) NOT NULL,
	content_type VARCHAR2(255 CHAR) NOT NULL,
	raw_data BLOB,
	stato_protocollazione VARCHAR2(255 CHAR) NOT NULL,
	data_protocollazione TIMESTAMP,
	data_prossima_protocollazione TIMESTAMP,
	tentativi_protocollazione NUMBER NOT NULL,
	dettaglio_protocollazione VARCHAR2(255 CHAR),
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_tracce_sdi_1 CHECK (tipo_comunicazione IN ('FAT_OUT','FAT_IN','RC','NS','MC','NE','MT','EC','SE','DT_ATT','DT_PASS','AT')),
	CONSTRAINT chk_tracce_sdi_2 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','IN_RICONSEGNA','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	-- fk/pk keys constraints
	CONSTRAINT pk_tracce_sdi PRIMARY KEY (id)
);

CREATE TRIGGER trg_tracce_sdi
BEFORE
insert on tracce_sdi
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_tracce_sdi.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_lotti MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE lotti
(
	formato_trasmissione VARCHAR(255) NOT NULL,
	identificativo_sdi NUMBER NOT NULL,
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
	xml BLOB NOT NULL,
	fatturazione_attiva NUMBER NOT NULL,
	stato_elaborazione_in_uscita VARCHAR(255),
        tipi_comunicazione VARCHAR(255),
	data_ultima_elaborazione TIMESTAMP,
	dettaglio_elaborazione VARCHAR(255),
	data_prossima_elaborazione TIMESTAMP,
	tentativi_consegna NUMBER NOT NULL,
	data_ricezione DATE NOT NULL,
	stato_inserimento VARCHAR(255) NOT NULL,
	stato_consegna VARCHAR(255) NOT NULL,
	data_consegna TIMESTAMP,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	dominio VARCHAR(255) NOT NULL,
	sottodominio VARCHAR(255),
	pago_pa VARCHAR(255 CHAR),
	data_protocollazione TIMESTAMP,
	protocollo CLOB,
	id_egov VARCHAR(255),
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_sip NUMBER,
	-- check constraints
	CONSTRAINT chk_lotti_1 CHECK (formato_trasmissione IN ('FPA12','FPR12','SDI11','SDI10')),
	CONSTRAINT chk_lotti_2 CHECK (formato_archivio_invio_fattura IN ('XML','P7M')),
	CONSTRAINT chk_lotti_3 CHECK (stato_elaborazione_in_uscita IN ('PRESA_IN_CARICO','IN_CORSO_DI_PROTOCOLLAZIONE','IN_CORSO_DI_FIRMA','DA_INVIARE_ALLO_SDI','ERRORE_DI_FIRMA','ERRORE_DI_PROTOCOLLO','ERRORE_DI_SPEDIZIONE','RICEVUTA_DALLO_SDI','RICEVUTO_SCARTO_SDI','RICEVUTA_DAL_DESTINATARIO','MANCATA_CONSEGNA','IMPOSSIBILITA_DI_RECAPITO','RICEVUTO_ESITO_CEDENTE_PRESTATORE_ACCETTAZIONE','RICEVUTO_ESITO_CEDENTE_PRESTATORE_RIFIUTO','RICEVUTA_DECORRENZA_TERMINI','SOLO_CONSERVAZIONE')),
	CONSTRAINT chk_lotti_4 CHECK (stato_inserimento IN ('NON_INSERITO','ERRORE_INSERIMENTO','INSERITO')),
	CONSTRAINT chk_lotti_5 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_lotti_6 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	CONSTRAINT chk_lotti_7 CHECK (dominio IN ('PA','B2B')),
	CONSTRAINT chk_lotti_8 CHECK (sottodominio IN ('ESTERO','PEC')),
	-- unique constraints
	CONSTRAINT unique_lotti_1 UNIQUE (identificativo_sdi,fatturazione_attiva),
	-- fk/pk keys constraints
	CONSTRAINT fk_lotti_1 FOREIGN KEY (id_sip) REFERENCES sip(id),
	CONSTRAINT pk_lotti PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_cp_denominazione ON lotti (cp_nazione,cp_codicefiscale);
CREATE TRIGGER trg_lotti
BEFORE
insert on lotti
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_lotti.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_decorrenza_termini MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE decorrenza_termini
(
	identificativo_sdi NUMBER NOT NULL,
	nome_file VARCHAR(50) NOT NULL,
	descrizione VARCHAR(255),
	message_id VARCHAR(14) NOT NULL,
	note VARCHAR(255),
	data_ricezione TIMESTAMP NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_traccia_sdi NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_decorrenza_termini_1 UNIQUE (identificativo_sdi),
	-- fk/pk keys constraints
	CONSTRAINT fk_decorrenza_termini_1 FOREIGN KEY (id_traccia_sdi) REFERENCES tracce_sdi(id),
	CONSTRAINT pk_decorrenza_termini PRIMARY KEY (id)
);

CREATE TRIGGER trg_decorrenza_termini
BEFORE
insert on decorrenza_termini
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_decorrenza_termini.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_tracce MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_tracce
(
	data_creazione TIMESTAMP NOT NULL,
	cf_trasmittente VARCHAR(255) NOT NULL,
	versione_applicativa VARCHAR(255) NOT NULL,
	id_pcc_amministrazione NUMBER NOT NULL,
	id_pa_transazione VARCHAR(255),
	id_pa_transazione_rispedizione VARCHAR(255),
	sistema_richiedente VARCHAR(255) NOT NULL,
	utente_richiedente VARCHAR(255) NOT NULL,
	id_fattura NUMBER,
	codice_dipartimento VARCHAR(7),
	richiesta_xml BLOB NOT NULL,
	risposta_xml BLOB,
	operazione VARCHAR(255) NOT NULL,
	tipo_operazione VARCHAR(255) NOT NULL,
	stato VARCHAR(255) NOT NULL,
	data_ultima_trasmissione TIMESTAMP NOT NULL,
	data_ultimo_tentativo_esito TIMESTAMP,
	codici_errore VARCHAR(1000),
	rispedizione NUMBER NOT NULL,
	rispedizione_dopo_query NUMBER NOT NULL,
	rispedizione_max_tentativi NUMBER,
	rispedizione_prox_tentativo TIMESTAMP,
	rispedizione_numero_tentativi NUMBER,
	rispedizione_ultimo_tentativo TIMESTAMP,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_tracce_1 CHECK (tipo_operazione IN ('PROXY','READ')),
	CONSTRAINT chk_pcc_tracce_2 CHECK (stato IN ('S_OK','S_ERRORE','AS_PRESA_IN_CARICO','AS_ERRORE_PRESA_IN_CARICO','AS_OK','AS_ERRORE')),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_tracce PRIMARY KEY (id)
);


ALTER TABLE pcc_tracce MODIFY rispedizione DEFAULT 0;

CREATE TRIGGER trg_pcc_tracce
BEFORE
insert on pcc_tracce
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_tracce.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_tracce_trasmissioni MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_tracce_trasmissioni
(
	ts_trasmissione TIMESTAMP NOT NULL,
	id_pcc_transazione VARCHAR(255),
	esito_trasmissione VARCHAR(255) NOT NULL,
	stato_esito VARCHAR(255) NOT NULL,
	gdo TIMESTAMP NOT NULL,
	data_fine_elaborazione TIMESTAMP,
	dettaglio_errore_trasmissione CLOB,
	id_egov_richiesta VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_pcc_traccia NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_tracce_trasmissioni_1 CHECK (esito_trasmissione IN ('OK','KO')),
	CONSTRAINT chk_pcc_tracce_trasmissioni_2 CHECK (stato_esito IN ('PRESENTE','NON_PRESENTE')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_tracce_trasmissioni_1 FOREIGN KEY (id_pcc_traccia) REFERENCES pcc_tracce(id),
	CONSTRAINT pk_pcc_tracce_trasmissioni PRIMARY KEY (id)
);

CREATE TRIGGER trg_pcc_tracce_trasmissioni
BEFORE
insert on pcc_tracce_trasmissioni
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_tracce_trasmissioni.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_tracce_trasmissioni_esiti MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE tracce_trasmissioni_esiti
(
	esito_elaborazione VARCHAR(255) NOT NULL,
	descrizione_elaborazione CLOB,
	data_fine_elaborazione TIMESTAMP,
	gdo TIMESTAMP NOT NULL,
	esito_trasmissione VARCHAR(255) NOT NULL,
	dettaglio_errore_trasmissione CLOB,
	id_egov_richiesta VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_pcc_traccia_trasmissione NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_esiti_1 CHECK (esito_trasmissione IN ('OK','KO')),
	-- fk/pk keys constraints
	CONSTRAINT fk_tracce_trasmissioni_esiti_1 FOREIGN KEY (id_pcc_traccia_trasmissione) REFERENCES pcc_tracce_trasmissioni(id),
	CONSTRAINT pk_tracce_trasmissioni_esiti PRIMARY KEY (id)
);

CREATE TRIGGER trg_tracce_trasmissioni_esiti
BEFORE
insert on tracce_trasmissioni_esiti
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_tracce_trasmissioni_esiti.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_errori_elaborazione MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_errori_elaborazione
(
	tipo_operazione VARCHAR(255),
	progressivo_operazione NUMBER,
	codice_esito VARCHAR(255) NOT NULL,
	descrizione_esito CLOB,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_esito NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_errori_elaborazione_1 CHECK (tipo_operazione IN ('CO','CP','SP','CS','RF','RC','CCS','SC')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_errori_elaborazione_1 FOREIGN KEY (id_esito) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT pk_pcc_errori_elaborazione PRIMARY KEY (id)
);

CREATE TRIGGER trg_pcc_errori_elaborazione
BEFORE
insert on pcc_errori_elaborazione
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_errori_elaborazione.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_fatture MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE fatture
(
	formato_trasmissione VARCHAR(255) NOT NULL,
	identificativo_sdi NUMBER NOT NULL,
	fatturazione_attiva NUMBER NOT NULL,
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
	posizione NUMBER NOT NULL,
	codice_destinatario VARCHAR(7) NOT NULL,
	tipo_documento VARCHAR(255) NOT NULL,
	divisa VARCHAR(3) NOT NULL,
	data DATE NOT NULL,
	anno NUMBER NOT NULL,
	numero VARCHAR(20) NOT NULL,
	esito VARCHAR(255),
	da_pagare NUMBER NOT NULL,
	importo_totale_documento BINARY_DOUBLE NOT NULL,
	importo_totale_riepilogo BINARY_DOUBLE NOT NULL,
	causale VARCHAR(1000),
	stato_consegna VARCHAR(255) NOT NULL,
	data_consegna TIMESTAMP,
	data_prossima_consegna TIMESTAMP,
	tentativi_consegna NUMBER NOT NULL,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	data_scadenza TIMESTAMP,
	data_protocollazione TIMESTAMP,
	protocollo VARCHAR(255),
	xml BLOB NOT NULL,
	stato_conservazione VARCHAR2(255 CHAR) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_notifica_decorrenza_termini NUMBER,
	id_sip NUMBER,
	id_contabilizzazione NUMBER,
	id_scadenza NUMBER,
	-- check constraints
	CONSTRAINT chk_fatture_1 CHECK (formato_trasmissione IN ('FPA12','FPR12','SDI11','SDI10')),
	CONSTRAINT chk_fatture_2 CHECK (tipo_documento IN ('TD01','TD02','TD03','TD04','TD05','TD06','TDXX')),
	CONSTRAINT chk_fatture_3 CHECK (esito IN ('IN_ELABORAZIONE_ACCETTATO','IN_ELABORAZIONE_RIFIUTATO','INVIATA_ACCETTATO','INVIATA_RIFIUTATO','SCARTATA_ACCETTATO','SCARTATA_RIFIUTATO')),
	CONSTRAINT chk_fatture_4 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_fatture_5 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	CONSTRAINT chk_fatture_6 CHECK (stato_conservazione IN ('NON_INVIATA','PRESA_IN_CARICO','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSERVAZIONE_COMPLETATA','CONSERVAZIONE_FALLITA')),
	-- unique constraints
	CONSTRAINT unique_fatture_1 UNIQUE (identificativo_sdi,posizione,fatturazione_attiva),
	-- fk/pk keys constraints
	CONSTRAINT fk_fatture_1 FOREIGN KEY (id_notifica_decorrenza_termini) REFERENCES decorrenza_termini(id) ON DELETE CASCADE,
	CONSTRAINT fk_fatture_2 FOREIGN KEY (id_sip) REFERENCES sip(id),
	CONSTRAINT fk_fatture_3 FOREIGN KEY (id_contabilizzazione) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT fk_fatture_4 FOREIGN KEY (id_scadenza) REFERENCES tracce_trasmissioni_esiti(id),
	CONSTRAINT pk_fatture PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_cp_denominazione_fatt ON fatture (cp_nazione,cp_codicefiscale);

ALTER TABLE fatture MODIFY posizione DEFAULT 1;
ALTER TABLE fatture MODIFY stato_consegna DEFAULT 'NON_CONSEGNATA';

CREATE TRIGGER trg_fatture
BEFORE
insert on fatture
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_fatture.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_allegati MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE allegati
(
	nome_attachment VARCHAR(60) NOT NULL,
	algoritmo_compressione VARCHAR(10),
	formato_attachment VARCHAR(10),
	descrizione_attachment VARCHAR(100),
	attachment BLOB NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_fattura_elettronica NUMBER NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_allegati_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_allegati PRIMARY KEY (id)
);

CREATE TRIGGER trg_allegati
BEFORE
insert on allegati
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_allegati.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_metadati MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE metadati
(
	richiesta NUMBER NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_traccia_sdi NUMBER,
	-- fk/pk keys constraints
	CONSTRAINT fk_metadati_1 FOREIGN KEY (id_traccia_sdi) REFERENCES tracce_sdi(id),
	CONSTRAINT pk_metadati PRIMARY KEY (id)
);

CREATE TRIGGER trg_metadati
BEFORE
insert on metadati
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_metadati.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_enti MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE enti
(
	nome VARCHAR(255) NOT NULL,
	id_pcc_amministrazione VARCHAR(255),
	cf_auth VARCHAR(255),
	descrizione VARCHAR(255),
	ente_versatore VARCHAR(255 CHAR),
	struttura_versatore VARCHAR(255 CHAR),
	nodo_codice_pagamento VARCHAR(255 CHAR),
	prefisso_codice_pagamento VARCHAR(255 CHAR),
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_enti_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_enti PRIMARY KEY (id)
);

CREATE TRIGGER trg_enti
BEFORE
insert on enti
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_enti.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_protocolli MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE protocolli
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	endpoint VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_protocolli_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_protocolli PRIMARY KEY (id)
);

CREATE TRIGGER trg_protocolli
BEFORE
insert on protocolli
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_protocolli.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_registri MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE registri
(
	nome VARCHAR(255) NOT NULL,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(35) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_protocollo NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_1 FOREIGN KEY (id_protocollo) REFERENCES protocolli(id),
	CONSTRAINT pk_registri PRIMARY KEY (id)
);

CREATE TRIGGER trg_registri
BEFORE
insert on registri
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_registri.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_registri_props MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE registri_props
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_protocollo NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_props_1 UNIQUE (nome,id_protocollo),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_props_1 FOREIGN KEY (id_protocollo) REFERENCES protocolli(id),
	CONSTRAINT pk_registri_props PRIMARY KEY (id)
);

CREATE TRIGGER trg_registri_props
BEFORE
insert on registri_props
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_registri_props.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_registri_prop_values MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE registri_prop_values
(
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_registro NUMBER NOT NULL,
	id_registro_property NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_prop_values_1 UNIQUE (id_registro_property,id_registro),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_prop_values_1 FOREIGN KEY (id_registro) REFERENCES registri(id),
	CONSTRAINT fk_registri_prop_values_2 FOREIGN KEY (id_registro_property) REFERENCES registri_props(id),
	CONSTRAINT pk_registri_prop_values PRIMARY KEY (id)
);

CREATE TRIGGER trg_registri_prop_values
BEFORE
insert on registri_prop_values
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_registri_prop_values.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_utenti MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE utenti
(
	username VARCHAR(50) NOT NULL,
	password VARCHAR(35) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	cognome VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL,
	tipo VARCHAR(255),
	esterno NUMBER NOT NULL,
	sistema VARCHAR(20),
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_utenti_1 CHECK (role IN ('ADMIN','DEPT_ADMIN','USER')),
	CONSTRAINT chk_utenti_2 CHECK (tipo IN ('INTERNO','ESTERNO')),
	-- unique constraints
	CONSTRAINT unique_utenti_1 UNIQUE (username),
	-- fk/pk keys constraints
	CONSTRAINT pk_utenti PRIMARY KEY (id)
);

CREATE TRIGGER trg_utenti
BEFORE
insert on utenti
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_utenti.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_esito_committente MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE esito_committente
(
	identificativo_sdi NUMBER NOT NULL,
	numero_fattura VARCHAR(20),
	anno NUMBER,
	posizione NUMBER,
	esito VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	message_id_committente VARCHAR(14),
	nome_file VARCHAR(50) NOT NULL,
	modalita_batch NUMBER NOT NULL,
	data_invio_ente TIMESTAMP NOT NULL,
	data_invio_sdi TIMESTAMP,
	stato_consegna_sdi VARCHAR(255) NOT NULL,
	data_ultima_consegna_sdi TIMESTAMP,
	data_prossima_consegna_sdi TIMESTAMP,
	tentativi_consegna_sdi NUMBER NOT NULL,
	scarto VARCHAR(255),
	scarto_note VARCHAR(255),
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_fattura_elettronica NUMBER NOT NULL,
	id_utente NUMBER NOT NULL,
	id_traccia_notifica NUMBER,
	id_traccia_scarto NUMBER,
	-- check constraints
	CONSTRAINT chk_esito_committente_1 CHECK (esito IN ('EC01','EC02')),
	CONSTRAINT chk_esito_committente_2 CHECK (stato_consegna_sdi IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_esito_committente_3 CHECK (scarto IN ('EN00','EN01')),
	-- fk/pk keys constraints
	CONSTRAINT fk_esito_committente_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT fk_esito_committente_2 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT fk_esito_committente_3 FOREIGN KEY (id_traccia_notifica) REFERENCES tracce_sdi(id),
	CONSTRAINT fk_esito_committente_4 FOREIGN KEY (id_traccia_scarto) REFERENCES tracce_sdi(id),
	CONSTRAINT pk_esito_committente PRIMARY KEY (id)
);


ALTER TABLE esito_committente MODIFY modalita_batch DEFAULT 0;
ALTER TABLE esito_committente MODIFY stato_consegna_sdi DEFAULT 'NON_CONSEGNATA';

CREATE TRIGGER trg_esito_committente
BEFORE
insert on esito_committente
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_esito_committente.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_dipartimenti MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE dipartimenti
(
	codice VARCHAR(7) NOT NULL,
	descrizione VARCHAR(255) NOT NULL,
	fatturazione_attiva NUMBER NOT NULL,
	id_procedimento VARCHAR(255),
	id_procedimento_b2b VARCHAR(255),
	firma_automatica NUMBER NOT NULL,
	accettazione_automatica NUMBER NOT NULL,
	modalita_push NUMBER NOT NULL,
	lista_email_notifiche CLOB,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_ente NUMBER NOT NULL,
	id_registro NUMBER,
	-- unique constraints
	CONSTRAINT unique_dipartimenti_1 UNIQUE (codice),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
	CONSTRAINT fk_dipartimenti_2 FOREIGN KEY (id_registro) REFERENCES registri(id),
	CONSTRAINT pk_dipartimenti PRIMARY KEY (id)
);


ALTER TABLE dipartimenti MODIFY fatturazione_attiva DEFAULT 0;
ALTER TABLE dipartimenti MODIFY firma_automatica DEFAULT 0;
ALTER TABLE dipartimenti MODIFY accettazione_automatica DEFAULT 0;
ALTER TABLE dipartimenti MODIFY modalita_push DEFAULT 1;

CREATE TRIGGER trg_dipartimenti
BEFORE
insert on dipartimenti
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_dipartimenti.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_dipartimenti_props MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE dipartimenti_props
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_ente NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_dipartimenti_props_1 UNIQUE (nome,id_ente),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_props_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
	CONSTRAINT pk_dipartimenti_props PRIMARY KEY (id)
);

CREATE TRIGGER trg_dipartimenti_props
BEFORE
insert on dipartimenti_props
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_dipartimenti_props.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_dipartimenti_prop_values MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE dipartimenti_prop_values
(
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_dipartimento NUMBER NOT NULL,
	id_dipartimento_property NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT un_dipart_prop_values_1 UNIQUE (id_dipartimento_property,id_dipartimento),
	-- fk/pk keys constraints
	CONSTRAINT fk_dipart_prop_values_1 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT fk_dipart_prop_values_2 FOREIGN KEY (id_dipartimento_property) REFERENCES dipartimenti_props(id),
	CONSTRAINT pk_dipart_prop_values PRIMARY KEY (id)
);

CREATE TRIGGER trg_dipartimenti_prop_values
BEFORE
insert on dipartimenti_prop_values
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_dipartimenti_prop_values.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_utenti_dipartimenti MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE utenti_dipartimenti
(
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_utente NUMBER NOT NULL,
	id_dipartimento NUMBER NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_utenti_dipartimenti_1 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT fk_utenti_dipartimenti_2 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT pk_utenti_dipartimenti PRIMARY KEY (id)
);

CREATE TRIGGER trg_utenti_dipartimenti
BEFORE
insert on utenti_dipartimenti
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_utenti_dipartimenti.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_operazioni MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_operazioni
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_operazioni_1 CHECK (nome IN ('ConsultazioneTracce','DatiFattura','PagamentoIva','InserimentoFattura','StatoFattura','ElencoMovimentiErarioIva','DownloadDocumento','OperazioneContabile_CP','OperazioneContabile_CO','OperazioneContabile_CS','OperazioneContabile_CCS','OperazioneContabile_CPS','OperazioneContabile_CSPC','OperazioneContabile_SP','OperazioneContabile_RF','OperazioneContabile_SC','OperazioneContabile_RC')),
	-- unique constraints
	CONSTRAINT unique_pcc_operazioni_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_operazioni PRIMARY KEY (id)
);

CREATE TRIGGER trg_pcc_operazioni
BEFORE
insert on pcc_operazioni
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_operazioni.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_dipartimenti_operazioni MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE dipartimenti_operazioni
(
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_dipartimento NUMBER NOT NULL,
	id_pcc_operazione NUMBER NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_dipartimenti_operazioni_1 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT fk_dipartimenti_operazioni_2 FOREIGN KEY (id_pcc_operazione) REFERENCES pcc_operazioni(id),
	CONSTRAINT pk_dipartimenti_operazioni PRIMARY KEY (id)
);

CREATE TRIGGER trg_dipartimenti_operazioni
BEFORE
insert on dipartimenti_operazioni
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_dipartimenti_operazioni.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_utenti_operazioni MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_utenti_operazioni
(
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_utente NUMBER NOT NULL,
	id_pcc_operazione NUMBER NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_utenti_operazioni_1 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT fk_pcc_utenti_operazioni_2 FOREIGN KEY (id_pcc_operazione) REFERENCES pcc_operazioni(id),
	CONSTRAINT pk_pcc_utenti_operazioni PRIMARY KEY (id)
);

CREATE TRIGGER trg_pcc_utenti_operazioni
BEFORE
insert on pcc_utenti_operazioni
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_utenti_operazioni.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_contabilizzazioni MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_contabilizzazioni
(
	importo_movimento BINARY_DOUBLE NOT NULL,
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
	id NUMBER NOT NULL,
	id_fattura_elettronica NUMBER NOT NULL,
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

CREATE TRIGGER trg_pcc_contabilizzazioni
BEFORE
insert on pcc_contabilizzazioni
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_contabilizzazioni.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_scadenze MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_scadenze
(
	importo_in_scadenza BINARY_DOUBLE,
	importo_iniziale BINARY_DOUBLE,
	pagato_ricontabilizzato BINARY_DOUBLE,
	data_scadenza DATE,
	sistema_richiedente VARCHAR(255) NOT NULL,
	utente_richiedente VARCHAR(255) NOT NULL,
	data_richiesta TIMESTAMP NOT NULL,
	data_query TIMESTAMP,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_fattura_elettronica NUMBER NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_scadenze_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_pcc_scadenze PRIMARY KEY (id)
);

CREATE TRIGGER trg_pcc_scadenze
BEFORE
insert on pcc_scadenze
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_scadenze.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_pagamenti MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_pagamenti
(
	importo_pagato BINARY_DOUBLE NOT NULL,
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
	id NUMBER NOT NULL,
	id_fattura_elettronica NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_pagamenti_1 CHECK (natura_spesa IN ('CO','CA','NA')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_pagamenti_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT pk_pcc_pagamenti PRIMARY KEY (id)
);

CREATE TRIGGER trg_pcc_pagamenti
BEFORE
insert on pcc_pagamenti
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_pagamenti.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_rispedizioni MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_rispedizioni
(
	max_numero_tentativi NUMBER NOT NULL,
	intervallo_tentativi NUMBER NOT NULL,
	codice_errore VARCHAR(255) NOT NULL,
	abilitato NUMBER NOT NULL,
	descrizione_errore CLOB NOT NULL,
	tipo_errore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_rispedizioni_1 CHECK (tipo_errore IN ('TRASMISSIONE','ELABORAZIONE')),
	-- unique constraints
	CONSTRAINT unique_pcc_rispedizioni_1 UNIQUE (codice_errore),
	-- fk/pk keys constraints
	CONSTRAINT pk_pcc_rispedizioni PRIMARY KEY (id)
);

CREATE TRIGGER trg_pcc_rispedizioni
BEFORE
insert on pcc_rispedizioni
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_rispedizioni.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_pcc_notifiche MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE pcc_notifiche
(
	stato_consegna VARCHAR(255) NOT NULL,
	data_creazione TIMESTAMP NOT NULL,
	data_consegna TIMESTAMP,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_pcc_traccia NUMBER NOT NULL,
	id_dipartimento NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_pcc_notifiche_1 CHECK (stato_consegna IN ('NON_CONSEGNATA','IN_RICONSEGNA','ERRORE_CONSEGNA','CONSEGNATA')),
	-- fk/pk keys constraints
	CONSTRAINT fk_pcc_notifiche_1 FOREIGN KEY (id_pcc_traccia) REFERENCES pcc_tracce(id),
	CONSTRAINT fk_pcc_notifiche_2 FOREIGN KEY (id_dipartimento) REFERENCES dipartimenti(id),
	CONSTRAINT pk_pcc_notifiche PRIMARY KEY (id)
);

CREATE TRIGGER trg_pcc_notifiche
BEFORE
insert on pcc_notifiche
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_pcc_notifiche.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_notifiche_eventi MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE notifiche_eventi
(
	tipo VARCHAR(255) NOT NULL,
	codice VARCHAR(255) NOT NULL,
	severita NUMBER NOT NULL,
	ora_registrazione TIMESTAMP NOT NULL,
	descrizione VARCHAR(255),
	id_transazione VARCHAR(255),
	configurazione CLOB,
	cluster_id VARCHAR(255),
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_notifiche_eventi PRIMARY KEY (id)
);

-- index
CREATE INDEX index_notifiche_eventi_1 ON notifiche_eventi (ora_registrazione);
CREATE TRIGGER trg_notifiche_eventi
BEFORE
insert on notifiche_eventi
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_notifiche_eventi.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/


