Eseguire lo script make_binary_distrib.sh presente nella cartella corrente.
Lo script non richiede nessun parametro, ma opzionalmente si puo' specificare la versione da costruire.
Es:	./make_binary_distrib.sh costruisce la versione 1.0
	./make_binary_distrib.sh 1.0b2 costruisce la versione 1.0b2

il nome dello zip, i path all'interno e il nome dei file pdf del manuale inseriti saranno influenzati dal numero di versione scelta.

L'installer e i documenti del manuale verranno prelevati dall'svn, mentre il codice sorgente del software dovra' essere gia' presente sulla macchina.
