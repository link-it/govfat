alter table fatture add column xmlB BYTEA;
update fatture set xmlB = convert_to(xml, 'UTF8');
alter table fatture drop column xml;
alter table fatture RENAME COLUMN xmlB to xml;
alter table fatture alter column xml set not null;

alter table esito_committente add column xmlB BYTEA;
update esito_committente set xmlB = convert_to(xml, 'UTF8');
alter table esito_committente drop column xml;
alter table esito_committente RENAME COLUMN xmlB to xml;

alter table esito_committente add column xmlB BYTEA;
update esito_committente set xmlB = convert_to(scarto_xml, 'UTF8');
alter table esito_committente drop column scarto_xml;
alter table esito_committente RENAME COLUMN xmlB to scarto_xml;

alter table decorrenza_termini add column xmlB BYTEA;
update decorrenza_termini set xmlB = convert_to(xml, 'UTF8');
alter table decorrenza_termini drop column xml;
alter table decorrenza_termini RENAME COLUMN xmlB to xml;
alter table decorrenza_termini alter column xml set not null;
