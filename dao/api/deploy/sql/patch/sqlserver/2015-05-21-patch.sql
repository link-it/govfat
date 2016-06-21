create or replace function CLOB_TO_BLOB (p_clob CLOB) return BLOB
as
 l_blob          blob;
 l_dest_offset   integer := 1;
 l_source_offset integer := 1;
 l_lang_context  integer := DBMS_LOB.DEFAULT_LANG_CTX;
 l_warning       integer := DBMS_LOB.WARN_INCONVERTIBLE_CHAR;
BEGIN

  DBMS_LOB.CREATETEMPORARY(l_blob, TRUE);
  DBMS_LOB.CONVERTTOBLOB
  (
   dest_lob    =>l_blob,
   src_clob    =>p_clob,
   amount      =>DBMS_LOB.LOBMAXSIZE,
   dest_offset =>l_dest_offset,
   src_offset  =>l_source_offset,
   blob_csid   =>DBMS_LOB.DEFAULT_CSID,
   lang_context=>l_lang_context,
   warning     =>l_warning
  );
  return l_blob;
END;
\

alter table fatture add xmlB VARBINARY(MAX);
update fatture set xmlB = clob_to_blob(xml);;
alter table fatture drop column xml;
EXEC sp_rename  'fatture.xmlB' , 'xml' , 'COLUMN'
alter table fatture alter column xml VARBINARY(MAX) not null;

alter table esito_committente add xmlB VARBINARY(MAX);
update esito_committente set xmlB = clob_to_blob(xml);;
alter table esito_committente drop column xml;
EXEC sp_rename  'esito_committente.xmlB' , 'xml' , 'COLUMN'

alter table esito_committente add xmlB VARBINARY(MAX);
update esito_committente set xmlB = clob_to_blob(scarto_xml);;
alter table esito_committente drop column scarto_xml;
EXEC sp_rename  'esito_committente.xmlB' , 'scarto_xml' , 'COLUMN'

alter table decorrenza_termini add xmlB VARBINARY(MAX);
update decorrenza_termini set xmlB = clob_to_blob(xml);;
alter table decorrenza_termini drop column xml;
EXEC sp_rename  'decorrenza_termini.xmlB' , 'xml' , 'COLUMN'
alter table decorrenza_termini alter column xml VARBINARY(MAX) not null;
