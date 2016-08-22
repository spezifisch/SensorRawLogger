#!/bin/bash -ex

mkdir -p logs
adb logcat "System.out:I" "*:S" | tee -a logs/"gpsstatus-`date '+%Y-%m-%d_%H.%M.%S'`.log"

