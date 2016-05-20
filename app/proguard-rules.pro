-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

#混下不知道方法名，这样定位到行
-keepattributes SourceFile,LineNumberTable
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#不混淆泛型
-keepattributes Signature
-keep class * extends java.lang.annotation.Annotation { *; }
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService
#-keep class java.lang.reflect.** {*; }


# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#
#-keepnames class * implements java.io.Serializable
#support
-dontwarn android.support.**
#afinal_0.5.1_bin.jar
-keep class net.tsz.afinal.** { *; }
-dontwarn net.tsz.afinal.**
#commons-codec-1.8.jar
-keep class org.apache.** { *; }
-dontwarn org.apache.**
#core-2.2.jar
-keep class com.google.zxing.** { *; }
-dontwarn com.google.zxing.**
#gson-2.3.1.jar
-dontwarn com.google.gson.**
-keep class com.google.gson.** {*; }
#idcard_engine_20160322.jar
-dontwarn com.ym.**
-keep class com.ym.** {*; }
-dontwarn com.yunmai.**
-keep class com.yunmai.** {*; }
#ksoap2-android-assembly-2.5.8-jar-with-dependencies.jar
-dontwarn org.**
-keep class org.** {*; }
#pstore-sdk-v1.2.jar
-dontwarn  cn.com.cybertech.**
-keep class cn.com.cybertech.** { *;}
#TendencyNFC3.0.jar
#-dontwarn  com.tdr.tendencynfc.**
#-keep class com.tdr.tendencynfc.** { *;}
#ysidcard.jar
-dontwarn  com.ivsign.android.**
-keep class com.ivsign.android.** { *;}
-dontwarn  com.otg.idcard.**
-keep class com.otg.idcard.** { *;}
#ZBarDecoder.jar
-dontwarn  net.sourceforge.zbar.**
-keep class net.sourceforge.zbar.** { *;}

#xutils-3.3.26
-dontwarn  android.backport.webp.**
-keep class android.backport.webp.** { *;}
-dontwarn  org.xutils.**
-keep class  org.xutils.** { *;}


##butterknife-7.0.1
#-keep class butterknife.** { *; }
##-keepnames class * { @butterknife.Bind *;}
#-dontwarn butterknife.**
##-keep class **$$ViewBinder { *; }
##
##-keepclasseswithmembernames class * {
##    @butterknife.* <fields>;
##}
##
##-keepclasseswithmembernames class * {
##    @butterknife.* <methods>;
##}




-keep class com.tdr.citycontrolpolice.entity.** {*; }

#-keep class javax.annotation.** {*; }

-dontwarn  com.tencent.**
-keep class com.tencent.** {*; }

#==================butterknife==================
-keep class butterknife.** { *; }
-keepnames class * extends android.app.Activity { @butterknife.Bind *;}
-keepnames class * extends android.support.v4.app.Fragment { @butterknife.Bind *;}
-dontwarn butterknife.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * extends android.app.Activity  {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * extends android.app.Activity {
    @butterknife.* <methods>;
}
-keepclasseswithmembernames class * extends android.support.v4.app.Fragment  {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * extends android.support.v4.app.Fragment {
    @butterknife.* <methods>;
}





-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet,int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}










