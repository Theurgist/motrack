#!/bin/bash

if [ -z $1 ]
then
    echo "Utility for automated migration file creation"
    echo
    echo "Usage:"
    echo "  ./gen-migration-template.sh {Migration_Name}"
    exit
fi


THIS_SCRIPT_DIR="$(dirname "$(readlink -f "$0")")"
cd ${THIS_SCRIPT_DIR}

MIGRATIONS_DIR="migration"

NOW=`date +%s`
TIMESTAMP=`date --date="@$NOW" +%Y%m%d%H%M%S`
HUMAN_READABLE=`date --date="@$NOW" "+%F %T %Z"`

NAME=${MIGRATIONS_DIR}/"V${TIMESTAMP}__$1.sql"
HEADER="-- Created at ${HUMAN_READABLE}"

echo $HEADER > $NAME
echo >> $NAME
echo "Created migration file '${NAME}'"




