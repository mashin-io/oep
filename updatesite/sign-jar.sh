output=$1'/'$2
jarsigner -keystore oepkeystore -signedjar $output $2 -tsa http://timestamp.digicert.com oep
