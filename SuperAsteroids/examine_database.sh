adb -d shell run-as edu.byu.cs.superasteroids "cat databases/asteroids.sqlite > /sdcard/asteroids.sqlite"
adb pull /sdcard/asteroids.sqlite
sqlite3 asteroids.sqlite