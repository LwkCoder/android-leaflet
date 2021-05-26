#实体类不混淆
-keep class com.sources.javacode.bean.** {*;}
#AndPermission
-dontwarn com.yanzhenjie.permission.**
#自定义注解NotProguard
-keepattributes *Annotation*
-keep @com.lwkandroid.wings.annotation.NotProguard class * {*;}
-keep class * {@com.lwkandroid.wings.annotation.NotProguard <fields>;}
-keepclassmembers class * {@com.lwkandroid.wings.annotation.NotProguard <methods>;}
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#RxBinding无需混淆
# Retrofit
-keepattributes Signature
-keepclassmembernames,allowobfuscation interface * { @retrofit2.http.* <methods>;}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
#ImagePicker
-dontwarn com.lwkandroid.imagepicker.**
-keep class com.lwkandroid.imagepicker.**{*;}
#RTPermission
-dontwarn com.lwkandroid.rtpermission.**
-keep class com.lwkandroid.rtpermission.**{*;}
#RcvAdapter
-dontwarn com.lwkandroid.rcvadapter.**
-keep class com.lwkandroid.rcvadapter.**{*;}
#okhttp okio
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#KLog不需要额外指定混淆规则
#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#---------------------------------基本指令区-------------------------------
#代码混淆的压缩比例，值在0-7之间
-optimizationpasses 5
#混淆后类名都为小写
-dontusemixedcaseclassnames
#指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses
#指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
#不做预校验的操作
-dontpreverify
#生成原类名和混淆后的类名的映射文件
-verbose
-printmapping proguardMapping.txt
#指定混淆是采用的算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*
#不混淆Annotation
-keepattributes *Annotation*,InnerClasses
#不混淆泛型
-keepattributes Signature
#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#忽略警告
-ignorewarnings
#------------------------------------------------------------------------
#-keep class XXXX   保留类名不变，也就是类名不混淆，而类中的成员名不保证。当然也可以是继承XXX类的所有类名不混淆
#-keepclasseswithmembers class XXXX 保留类名和成员名,当然也可以是类中特定方法
#---------------------------------默认保留区-------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.**
-keep public class * extends android.app.Fragment
-dontwarn android.support.**
-keep class android.support.** {*;}
#自定义控件不要混淆
-keep public class * extends android.view.View {*;}
#adapter不能混淆
-keep public class * extends android.widget.BaseAdapter {*;}
#CusorAdapter不混淆
-keep public class * extends android.widget.CusorAdapter{*;}
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {*;}
-keep class **.R$* {*;}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------
#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
-dontwarn javax.annotation.**
-dontwarn javax.inject.**