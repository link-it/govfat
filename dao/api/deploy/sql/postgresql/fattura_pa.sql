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
	codice_destinatario VARCHAR(6) NOT NULL,
	xml BYTEA NOT NULL,
	data_ricezione DATE NOT NULL,
	stato_inserimento VARCHAR(255) NOT NULL,
	stato_consegna VARCHAR(255) NOT NULL,
	data_consegna TIMESTAMP,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	data_protocollazione TIMESTAMP,
	protocollo TEXT,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_lotti') NOT NULL,
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




CREATE SEQUENCE seq_fatture start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE fatture
(
	formato_trasmissione VARCHAR(255) NOT NULL,
	identificativo_sdi INT NOT NULL,
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
	codice_destinatario VARCHAR(6) NOT NULL,
	tipo_documento VARCHAR(255) NOT NULL,
	divisa VARCHAR(3) NOT NULL,
	data DATE NOT NULL,
	anno INT NOT NULL,
	numero VARCHAR(20) NOT NULL,
	esito VARCHAR(255),
	importo_totale_documento DOUBLE PRECISION NOT NULL,
	importo_totale_riepilogo DOUBLE PRECISION NOT NULL,
	causale VARCHAR(1000),
	stato_consegna VARCHAR(255) NOT NULL DEFAULT 'NON_CONSEGNATA',
	data_consegna TIMESTAMP,
	dettaglio_consegna VARCHAR(255),
	stato_protocollazione VARCHAR(255) NOT NULL,
	data_protocollazione TIMESTAMP,
	protocollo VARCHAR(255),
	xml BYTEA NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_fatture') NOT NULL,
	id_notifica_decorrenza_termini BIGINT,
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




CREATE SEQUENCE seq_enti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE enti
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	endpoint VARCHAR(255),
	endpoint_consegna_lotto VARCHAR(255),
	endpoint_richiedi_protocollo VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_enti') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_enti_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_enti PRIMARY KEY (id)
);




CREATE SEQUENCE seq_registri start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE registri
(
	nome VARCHAR(255) NOT NULL,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(35) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_registri') NOT NULL,
	id_ente BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
	CONSTRAINT pk_registri PRIMARY KEY (id)
);




CREATE SEQUENCE seq_registri_props start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE registri_props
(
	nome VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_registri_props') NOT NULL,
	id_ente BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_props_1 UNIQUE (nome,id_ente),
	-- fk/pk keys constraints
	CONSTRAINT fk_registri_props_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
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
	username VARCHAR(20) NOT NULL,
	password VARCHAR(35) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	cognome VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_utenti') NOT NULL,
	id_ente BIGINT NOT NULL,
	-- check constraints
	CONSTRAINT chk_utenti_1 CHECK (role IN ('ADMIN','DEPT_ADMIN','USER')),
	-- unique constraints
	CONSTRAINT unique_utenti_1 UNIQUE (username),
	-- fk/pk keys constraints
	CONSTRAINT fk_utenti_1 FOREIGN KEY (id_ente) REFERENCES enti(id),
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
	CONSTRAINT chk_esito_committente_2 CHECK (scarto IN ('EN00','EN01')),
	-- fk/pk keys constraints
	CONSTRAINT fk_esito_committente_1 FOREIGN KEY (id_fattura_elettronica) REFERENCES fatture(id),
	CONSTRAINT fk_esito_committente_2 FOREIGN KEY (id_utente) REFERENCES utenti(id),
	CONSTRAINT pk_esito_committente PRIMARY KEY (id)
);




CREATE SEQUENCE seq_dipartimenti start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dipartimenti
(
	codice VARCHAR(6) NOT NULL,
	descrizione VARCHAR(255) NOT NULL,
	accettazione_automatica BOOLEAN NOT NULL DEFAULT false,
	modalita_push BOOLEAN NOT NULL DEFAULT true,
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



