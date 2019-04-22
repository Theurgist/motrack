#!/usr/bin/env bash
THIS_SCRIPT_DIR="$(dirname "$(readlink -f "$0")")"
cd ${THIS_SCRIPT_DIR}

java -jar jfx-client-0.0.1-SNAPSHOT.jar