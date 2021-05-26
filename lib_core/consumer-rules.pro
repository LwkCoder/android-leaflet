#BusUtils
-keepclassmembers class * {@com.lwkandroid.lib.core.utils.common.BusUtils$Bus <methods>;}
#NotProguard
-keep @com.lwkandroid.lib.core.annotation.NotProguard class * {*;}
-keep class * {@com.lwkandroid.lib.core.annotation.NotProguard <fields>;}
-keepclassmembers class * {@com.lwkandroid.lib.core.annotation.NotProguard <methods>;}
