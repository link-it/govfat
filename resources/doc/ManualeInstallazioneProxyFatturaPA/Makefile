LANG=ita
SOURCE_NAME=ManualeInstallazioneProxyFatturaPA
SOURCE_FILE=${SOURCE_NAME}.xml
SOURCE_CONVERTED=${SOURCE_FILE}.${LANG}
NOME_GUIDA_ESTESO=Guida Installazione Proxy FatturaPA
NOME_GUIDA_BREVE=installazioneProxyFatturaPA
ACCENTI_BIN=./accenti.pl

RSYNC_N_OPTIONS=
# RSYNC_OPTIONS=${RSYNC_N_OPTIONS} -e ssh -ar
RSYNC_OPTIONS=${RSYNC_N_OPTIONS} -e 'ssh -i /home/tito/project/certs/linkit-ec2-hosting.pem' -ar

DB_LATEX_OPTIONS=-P latex.output.revhistory=0 -P doc.collab.show=0
DOC_VERSION=2.0
PDF_DOC_VERSION=1.0

WEB_CONTEXT=openspcoop
LOCAL_DEST=/opt/local/programmi/apache-tomcat-5.5.27/webapps
#WEB_DEST=root@papandrea-ws:/opt/local/programmi/jboss-5.1.0.GA.web/server/default/deploy/${WEB_CONTEXT}.war
WEB_DEST=openspcoop@frontend.link.it:/data/openspcoop

#xmlto html-nochunks -x ../stylesheets/xsl/main-html-nochunks.xsl ${SOURCE_FILE}

html:	#convert-accents
	@echo "Producing html files..."
	xmlto html-nochunks -x ../stylesheets/xsl/main-html-nochunks.xsl ${SOURCE_FILE} --skip-validation
pdf:   #convert-accents
	dblatex ${DB_LATEX_OPTIONS} --pdf ${SOURCE_FILE} -o ${SOURCE_NAME}_v${PDF_DOC_VERSION}.pdf

img:
	@mkdir -p FIGURE_OUT
	@for file in FIGURE_DOC/*.jpg; do \
		( convert $$file -resize 65% FIGURE_OUT/`basename $$file jpg`pdf; convert $$file -resize 65% FIGURE_OUT/`basename $$file`) \
	done
	@echo "Done."

xhtml:
	xmlto xhtml -x ../stylesheets/xsl/main-xhtml-chunks.xsl ${SOURCE_FILE} -o xhtmlout --skip-validation
	make post

post:
	perl -pi -e "s/%=NomeGuida%/${NOME_GUIDA_ESTESO}/g" xhtmlout/*.html
	perl -pi -e "s/href=\"ar/href=\"?guida=${NOME_GUIDA_BREVE}&rel=${DOC_VERSION}&id=ar/g" xhtmlout/*.html
	perl -pi -e "s/href=\"ap/href=\"?guida=${NOME_GUIDA_BREVE}&rel=${DOC_VERSION}&id=ap/g" xhtmlout/*.html
	perl -pi -e "s/href=\"index.html/href=\"?guida=${NOME_GUIDA_BREVE}&rel=${DOC_VERSION}&id=index.html/g" xhtmlout/*.html
	perl -pi -e "s/^<body.*//g" xhtmlout/*.html
	perl -pi -e "s/FIGURE_OUT/\/${WEB_CONTEXT}\/doc\/guida-${NOME_GUIDA_BREVE}\/${DOC_VERSION}\/FIGURE_OUT/g" xhtmlout/*.html
	perl -pi -e "s/^<\/body.*//g" xhtmlout/*.html
	perl -pi -e "s/^<html.*//g" xhtmlout/*.html
	perl -pi -e "s/^<\/html.*//g" xhtmlout/*.html
	perl -pi -e "s/^<link.*//g" xhtmlout/*.html
	perl -pi -e "s/^<meta.*//g" xhtmlout/*.html
	perl -pi -e "s/^<head.*//g" xhtmlout/*.html
	perl -pi -e "s/^<\/head.*//g" xhtmlout/*.html
	perl -pi -e "s/^<title.*//g" xhtmlout/*.html
	perl -pi -e "s/%=openspcoop_doc_index%/documentazione/g" xhtmlout/*.html
	perl -pi -e "s/%=openspcoop_doc_guide%/?guida=${NOME_GUIDA_BREVE}&rel=${DOC_VERSION}&id=index.html/g" xhtmlout/*.html

install:
	mkdir -p ${LOCAL_DEST}/${WEB_CONTEXT}/doc/guida-${NOME_GUIDA_BREVE}/${DOC_VERSION}
	cp -rp proxyFatturaPA_${SOURCE_NAME}${PDF_DOC_VERSION}.pdf FIGURE_OUT xhtmlout/*.html  ${LOCAL_DEST}/${WEB_CONTEXT}/doc/guida-${NOME_GUIDA_BREVE}/${DOC_VERSION}

web-install:
	rsync ${RSYNC_OPTIONS} FIGURE_OUT proxyFatturaPA_${SOURCE_NAME}${PDF_DOC_VERSION}.pdf xhtmlout/*.html ${WEB_DEST}/doc/guida-${NOME_GUIDA_BREVE}/${DOC_VERSION}


clean: clean-html clean-pdf
	rm -f ${SOURCE_FILE}~ 

clean-html:
	rm -f *.html

clean-pdf:
	rm -f proxyFatturaPA_${SOURCE_NAME}${PDF_DOC_VERSION}.pdf 

convert-accents:
	@${ACCENTI_BIN} ${SOURCE_FILE} > ${SOURCE_FILE}.converted || exit 1
	@test ! -z "`cat ${SOURCE_FILE}.converted`" || exit 1
	@mv ${SOURCE_FILE}.converted ${SOURCE_FILE}


