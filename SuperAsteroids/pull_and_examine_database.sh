cat <<EOF | adb shell
run-as edu.byu.cs.superasteroids
cat /data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite > /sdcard/asteroids.sqlite
exit
exit
EOF
adb pull /sdcard/asteroids.sqlite
sqlite3 asteroids.sqlite