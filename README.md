# SensorRawLogger
Log raw sensor data on Android

## Usage

- Connect Android device to your PC, activate GPS
- Start App
- Use ADB Logcat on PC to receive sensor data: `adb logcat "System.out:I" "*:S"`

## Example

There are some Shortcuts in `./tools`!

- Log output to file:
  ```adb logcat "System.out:I" "*:S" | tee -a logs/"gpsstatus-`date '+%Y-%m-%d_%H.%M.%S'`.log"```
- Extract JSON:
  `cut -d' ' -f2- logs/gpsstatus-*.log | egrep '^{' > filt`
- Import into MongoDB for postprocessing:
  `mongoimport --db gps --collection a --file filt`

Example data:
```
{"type":"fix","time":1471879442328,"satsTotal":15,"satsInFix":14,"gpsTotal":12,"gpsInFix":12}
{"type":"loc","time":1471879443309,"loctime":1471879444000,"lat":12.3456,"lon":12.3456,"alt":12.3456,"bea":0,"spd":0,"acc":6,"prv":"gps"}
{"type":"event","time":1471879443335,"event":"GPS_EVENT_SATELLITE_STATUS"}
{"type":"sat","time":1471879443335,"prn":1,"azi":286,"ele":47,"snr":25,"alm":false,"eph":false,"fix":true}
{"type":"sat","time":1471879443335,"prn":3,"azi":231,"ele":13,"snr":25,"alm":false,"eph":false,"fix":true}
{"type":"sat","time":1471879443335,"prn":8,"azi":196,"ele":61,"snr":28,"alm":false,"eph":false,"fix":true}
{"type":"sat","time":1471879443335,"prn":10,"azi":59,"ele":35,"snr":31,"alm":false,"eph":false,"fix":true}
{"type":"sat","time":1471879443335,"prn":11,"azi":285,"ele":62,"snr":24,"alm":false,"eph":false,"fix":true}
{"type":"sat","time":1471879443335,"prn":14,"azi":130,"ele":36,"snr":24,"alm":false,"eph":false,"fix":true}
{"type":"sat","time":1471879443335,"prn":18,"azi":58,"ele":4,"snr":24,"alm":false,"eph":false,"fix":true}
...
```
