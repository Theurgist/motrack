#!/usr/bin/env bash
THIS_SCRIPT_DIR="$(dirname "$(readlink -f "$0")")"
cd ${THIS_SCRIPT_DIR}/..

sbt clean assembly