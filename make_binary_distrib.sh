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
TMP=TMP
if [ "$1" != "" ] ; then
	VERSIONE=$1;
fi

NOME_FINALE=${NOME_PRODOTTO}_v${VERSIONE}

svn export svn://gov4j.it/gov4j/fatturapa/trunk $TMP
pushd $TMP/web
sh make_binary_distrib.sh $VERSIONE
popd
 
cp $TMP/web/$NOME_FINALE.zip .
rm -r $TMP
