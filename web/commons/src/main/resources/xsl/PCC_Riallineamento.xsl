<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet 
     version="1.1" 
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     xmlns:tes="http://www.tesoro.it/fatture"
     xmlns:a="http://www.fatturapa.gov.it/sdi/fatturapa/v1.1">
     <xsl:output method="html" />
     <xsl:template match="/tes:queryDatiFatturaRispostaTipo">
	<html>
		<head>
		</head>
		<body>

		<p>		
		<h1>Testata</h1>
		<b>codiceFiscaleTrasmittente:</b>  <xsl:value-of select="testataRisposta/codiceFiscaleTrasmittente" />
		<br />
		<b>timestampTrasmissione:</b> <xsl:value-of select="testataRisposta/timestampTrasmissione" />
		<br />
		<b>versioneApplicativa:</b> <xsl:value-of select="testataRisposta/versioneApplicativa" />
		</p>
		<hr />
		<p>
		<h1>Esito</h1>	
		<b>dataFineElaborazione:</b>  <xsl:value-of select="datiRisposta/dataFineElaborazione" />
		<br />
		<b>esitoTrasmissione:</b> <xsl:value-of select="datiRisposta/esitoTrasmissione" />
		<br />
		<b>esitoElaborazioneTransazione:</b> <xsl:value-of select="datiRisposta/esitoElaborazioneTransazione" />
		<br />
		<b>descrizioneElaborazioneTransazione:</b> <xsl:value-of select="datiRisposta/descrizioneElaborazioneTransazione" />
		</p>

		<hr />
		<p>
		<h1>Dettaglio fattura</h1>
		<h2>Dati fornitore:</h2>
		<br />
		<b>CF:</b><xsl:value-of select="datiRisposta/dettaglioFattura/datiFornitore/codiceFiscale" />
		<br />
		<b>Denominazione:</b><xsl:value-of select="datiRisposta/dettaglioFattura/datiFornitore/denominazione" />
		<hr />

		<h2>Dati cessionario:</h2>
		<br />
		<b>codiceFiscaleGiuridicoTipo:</b><xsl:value-of select="datiRisposta/dettaglioFattura/datiCessionario/codiceFiscaleGiuridicoTipo" />
		<br />
		<b>denominazione:</b><xsl:value-of select="datiRisposta/dettaglioFattura/datiCessionario/denominazione" />
		<br />
		<b>dataCessione:</b><xsl:value-of select="datiRisposta/dettaglioFattura/datiCessionario/dataCessione" />
		<hr />

		<h2>Dati amministrazione:</h2>
		<br />
		<b>codiceFiscale: </b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiAmministrazione/codiceFiscale"/>
		<br />
		<b>denominazione: </b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiAmministrazione/denominazione"/>
		<br />
		<b>codiceUfficioPCC: </b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiAmministrazione/codiceUfficioPCC"/>
		<br />
		<b>denominazioneUfficioPCC: </b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiAmministrazione/denominazioneUfficioPCC"/>
		<br />
		<b>codiceUnivocoUfficioIPA: </b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiAmministrazione/codiceUnivocoUfficioIPA"/>
		<br />
		<b>denominazioneUfficioIPA: </b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiAmministrazione/denominazioneUfficioIPA"/>
		<hr />

		<h2>datiDistintaTrasmissione:</h2>
		<br />
		<b>identificativoDistinta:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDistintaTrasmissione/identificativoDistinta"/>
		<br />
		<b>dataImmissione:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDistintaTrasmissione/dataImmissione"/>
		<hr/>

		<p>
		<b>flagFatturaCertificata:</b><xsl:value-of select="datiRisposta/dettaglioFattura/flagFatturaCertificata" />
		<h3>Lista certificazioni</h3>
		<xsl:for-each select="datiRisposta/dettaglioFattura/listaCertificazioni/certificazione">
		<b>numeroIstanza:</b> <xsl:value-of select="numeroIstanza"/>
		<br />
		<b>numeroCertificazione:</b> <xsl:value-of select="numeroCertificazione"/>
		<br/>
		</xsl:for-each>
		</p>
		<b>importoTotaleComunicazioniScadenze:</b><xsl:value-of select="datiRisposta/dettaglioFattura/importoTotaleComunicazioniScadenze" />

		<h3>Lista comunicazioni scadenza</h3>
		<xsl:for-each select="datiRisposta/dettaglioFattura/listaComunicazioneScadenza/comunicazioneScadenza">
		<b>dataScadenza:</b> <xsl:value-of select="dataScadenza"/>
		<br />
		<b>importoInScadenza:</b> <xsl:value-of select="importoInScadenza"/>
		<br />
		<b>importoIniziale:</b> <xsl:value-of select="importoIniziale"/>
		<br />
		<b>pagatoRicontabilizzato:</b> <xsl:value-of select="pagatoRicontabilizzato"/>
		<hr/>
		</xsl:for-each>

		<h2>datiDocumento:</h2>
		<br />
		<b>statoDocumento:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/statoDocumento"/>
		<br />
		<b>IdFiscaleIVA:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/IdFiscaleIVA"/>
		<br />
		<b>progressivoRegistrazione:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/progressivoRegistrazione"/>
		<br />
		<b>identificazioneSDI (lotto):</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/identificazioneSDI/lottoSDI"/>
		<br />
		<b>identificazioneSDI (numero):</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/identificazioneSDI/numeroFattura"/>
		<br />
		<b>numeroProtocollo:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/numeroProtocollo"/>
		<br />
		<b>dataRicezione:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/dataRicezione"/>
		<br />
		<b>tipoDocumento:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/tipoDocumento"/>
		<br />
		<b>dataEmissione:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/dataEmissione"/>
		<br />
		<b>numeroDocumento:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/numeroDocumento"/>
		<br />
		<b>descrizioneCausale:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/descrizioneCausale"/>
		<br />
		<b>importoTotale:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/importoTotale"/>
		<br />
		<b>importoImponibile:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/importoImponibile"/>
		<br />
		<b>totaleImposta:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/totaleImposta"/>
		<br />
		<b>splitPayment:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/splitPayment"/>
		<br />

		<xsl:for-each select="datiRisposta/dettaglioFattura/datiDocumento/datiDocumento/listaRiepilogoAliquote/riepilogoAliquote">
		<b>aliquotaIVA:</b> <xsl:value-of select="aliquotaIVA"/>
		<br />
		<b>codiceEsenzioneIVA:</b> <xsl:value-of select="codiceEsenzioneIVA"/>
		<br />
		<b>totaleImponibileAliquota:</b> <xsl:value-of select="totaleImponibileAliquota"/>
		<br />
		<b>totaleImpostaAliquota:</b> <xsl:value-of select="totaleImpostaAliquota"/>
		<hr />
		</xsl:for-each>
		<br />
		<h3>ripartizioneAttuale:</h3>
		<br />
		<b>importoLiquidato:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/ripartizioneAttuale/importoLiquidato"/>
		<br />
		<b>importoNonLiquidato:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/ripartizioneAttuale/importoNonLiquidato"/>
		<br />
		<b>importoSospeso:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/ripartizioneAttuale/importoSospeso"/>
		<br />
		<b>importoPagato:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/ripartizioneAttuale/importoPagato"/>
		<br />
		<b>dataCertificazione:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/ripartizioneAttuale/dataCertificazione"/>
		<br />
		<b>importoCertificato:</b> <xsl:value-of select="datiRisposta/dettaglioFattura/datiDocumento/ripartizioneAttuale/importoCertificato"/>
		<hr/>

		<h2>Lista dettaglio Pagamento</h2>
		<xsl:for-each select="datiRisposta/dettaglioFattura/datiDocumento/dettaglioDocumento/listaDettaglioPagamento/dettaglioPagamento">
		<b>dataRiferimentoTerminiPagamento:</b> <xsl:value-of select="dataRiferimentoTerminiPagamento"/>
		<br />
		<b>giorniTerminiPagamento:</b> <xsl:value-of select="giorniTerminiPagamento"/>
		<br />
		<b>dataScadenzaPagamento:</b> <xsl:value-of select="dataScadenzaPagamento"/>
		<br />
		<b>importoPagamento:</b> <xsl:value-of select="importoPagamento"/>
		<hr/>
		</xsl:for-each>

		<h2>Lista movimenti</h2>
		<xsl:for-each select="datiRisposta/dettaglioFattura/datiDocumento/listaDettaglioMovimento/movimento">
		<p>  
		<h2>Movimento inviato in data <xsl:value-of select="dataMovimento" /></h2>
		<h3>Dati movimento</h3>
		<hr />
		<b>importo:</b> <xsl:value-of select="importo" />
		<br />
		<b>statoDebito:</b> <xsl:value-of select="statoDebito" />
		<br />
		<b>causale:</b> <xsl:value-of select="causale" />
		<br />
		<b>importoNaturaCorrente:</b> <xsl:value-of select="importoNaturaCorrente" />
		<br />
		<b>importoContoCapitale:</b> <xsl:value-of select="importoContoCapitale" />
		<br />
		<b>capitoloPgConto:</b> <xsl:value-of select="capitoloPgConto" />
		<br />
		<b>estremiImpegno:</b> <xsl:value-of select="estremiImpegno" />
		<br />
		<b>codiceCIG:</b> <xsl:value-of select="codiceCIG" />
		<br />
		<b>codiceCUP:</b> <xsl:value-of select="codiceCUP" />
		<br />
		<b>descrizioneContabilizzazione:</b> <xsl:value-of select="descrizioneContabilizzazione" />
		</p>
		</xsl:for-each>
		</p>
		</body>
	</html>
     </xsl:template>
</xsl:stylesheet>
