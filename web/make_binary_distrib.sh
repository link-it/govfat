#!/bin/sh
usage ()
{
  echo 'Usage : '$0' [VERSIONE]'
  exit
}

if [ "$1" == "-h" ]; then
  usage
fi

if [ "$#" -ge 2 ]; then
  usage
fi

NOME_PRODOTTO=proxyFatturaPA
VERSIONE=1.0

if [ "$1" != "" ] ; then
	VERSIONE=$1;
fi

for i in api console; do cp $i/local_env.xml{.template,}; done

pushd commons/deploy/properties

cp daoFactory.properties.template daoFactory.properties
cp webCommons.properties.template webCommons.properties

popd

NOME_FINALE=${NOME_PRODOTTO}_v${VERSIONE}

cp -r installazione $NOME_FINALE
ant clean_setup prepare_setup -Dinstall.dir=`pwd`/$NOME_FINALE
sed -i -e 's/#VERSIONE#/'${VERSIONE}'/g' ${NOME_FINALE}/README.txt
mkdir ${NOME_FINALE}/docs
DIR_DOCS=../doc

cp $DIR_DOCS/ManualeFatturazioneElettronica/ManualeUtente_FatturaPA_v1.0.pdf $NOME_FINALE/docs/${NOME_PRODOTTO}_ManualeUtente${VERSIONE}.pdf

cp $DIR_DOCS/ManualeInstallazioneProxyFatturaPA/ManualeInstallazioneProxyFatturaPA_v1.0.pdf $NOME_FINALE/docs/${NOME_PRODOTTO}_ManualeInstallazioneBin${VERSIONE}.pdf


rm $NOME_FINALE.zip
zip -r $NOME_FINALE.zip $NOME_FINALE
rm -rf $NOME_FINALE
