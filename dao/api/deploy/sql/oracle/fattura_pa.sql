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
	codice_destinatario VARCHAR(6) NOT NULL,
	xml BLOB NOT NULL,
	data_ricezione DATE NOT NULL,
	stato_inserimento VARCHAR(255) NOT NULL,
	stato_consegna VARCHAR(255) NOT NULL,
	data_consegna TIMESTAMP,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	data_protocollazione TIMESTAMP,
	protocollo CLOB,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_lotti_1 CHECK (formato_trasmissione IN ('SDI11','SDI10')),
	CONSTRAINT chk_lotti_2 CHECK (formato_archivio_invio_fattura IN ('XML','P7M')),
	CONSTRAINT chk_lotti_3 CHECK (stato_inserimento IN ('NON_INSERITO','ERRORE_INSERIMENTO','INSERITO')),
	CONSTRAINT chk_lotti_4 CHECK (stato_consegna IN ('NON_CONSEGNATA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_lotti_5 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	-- unique constraints
	CONSTRAINT unique_lotti_1 UNIQUE (identificativo_sdi),
	-- fk/pk keys constraints
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
	xml BLOB NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_decorrenza_termini_1 UNIQUE (identificativo_sdi),
	-- fk/pk keys constraints
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



CREATE SEQUENCE seq_fatture MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE fatture
(
	formato_trasmissione VARCHAR(255) NOT NULL,
	identificativo_sdi NUMBER NOT NULL,
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
	codice_destinatario VARCHAR(6) NOT NULL,
	tipo_documento VARCHAR(255) NOT NULL,
	divisa VARCHAR(3) NOT NULL,
	data DATE NOT NULL,
	anno NUMBER NOT NULL,
	numero VARCHAR(20) NOT NULL,
	esito VARCHAR(255),
	importo_totale_documento BINARY_DOUBLE NOT NULL,
	importo_totale_riepilogo BINARY_DOUBLE NOT NULL,
	causale VARCHAR(1000),
	stato_consegna VARCHAR(255) NOT NULL,
	data_consegna TIMESTAMP,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	data_protocollazione TIMESTAMP,
	protocollo VARCHAR(255),
	xml BLOB NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_notifica_decorrenza_termini NUMBER,
	-- check constraints
	CONSTRAINT chk_fatture_1 CHECK (formato_trasmissione IN ('SDI11','SDI10')),
	CONSTRAINT chk_fatture_2 CHECK (tipo_documento IN ('TD01','TD02','TD03','TD04','TD05','TD06')),
	CONSTRAINT chk_fatture_3 CHECK (esito IN ('IN_ELABORAZIONE_ACCETTATO','IN_ELABORAZIONE_RIFIUTATO','INVIATA_ACCETTATO','INVIATA_RIFIUTATO','SCARTATA_ACCETTATO','SCARTATA_RIFIUTATO')),
	CONSTRAINT chk_fatture_4 CHECK (stato_consegna IN ('NON_CONSEGNATA','ERRORE_CONSEGNA','CONSEGNATA')),
	CONSTRAINT chk_fatture_5 CHECK (stato_protocollazione IN ('NON_PROTOCOLLATA','PROTOCOLLATA_IN_ELABORAZIONE','ERRORE_PROTOCOLLAZIONE','PROTOCOLLATA')),
	-- unique constraints
	CONSTRAINT unique_fatture_1 UNIQUE (identificativo_sdi,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_fatture_1 FOREIGN KEY (id_notifica_decorrenza_termini) REFERENCES decorrenza_termini(id) ON DELETE CASCADE,
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



CREATE SEQUENCE seq_enti MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE enti
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	endpoint VARCHAR(255),
	endpoint_consegna_lotto VARCHAR(255),
	endpoint_richiedi_protocollo VARCHAR(255),
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



CREATE SEQUENCE seq_registri MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE registri
(
	nome VARCHAR(255) NOT NULL,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(35) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_ente NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
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
	id_ente NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_props_1 UNIQUE (nome,id_ente),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_props_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
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
	username VARCHAR(20) NOT NULL,
	password VARCHAR(35) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	cognome VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_ente NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_utenti_1 CHECK (role IN ('ADMIN','DEPT_ADMIN','USER')),
	-- unique constraints
	CONSTRAINT unique_utenti_1 UNIQUE (username),
	-- fk/pk keys constraints
	CONSTRAINT fk_utenti_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
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
	scarto VARCHAR(255),
	scarto_note VARCHAR(255),
	scarto_xml BLOB,
	xml BLOB,
	-- fk/pk columns
	id NUMBER NOT NULL,
	id_fattura_elettronica NUMBER NOT NULL,
	id_utente NUMBER NOT NULL,
	-- check constraints
	CONSTRAINT chk_esito_committente_1 CHECK (esito IN ('EC01','EC02')),
	CONSTRAINT chk_esito_committente_2 CHECK (scarto IN ('EN00','EN01')),
	-- fk/pk keys constraints
	CONSTRAINT fk_esito_committente_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT fk_esito_committente_2 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT pk_esito_committente PRIMARY KEY (id)
);


ALTER TABLE esito_committente MODIFY modalita_batch DEFAULT 0;

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
	codice VARCHAR(6) NOT NULL,
	descrizione VARCHAR(255) NOT NULL,
	accettazione_automatica NUMBER NOT NULL,
	modalita_push NUMBER NOT NULL,
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


