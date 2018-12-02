# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontoptimize

 #预校验
-dontpreverify

 #混淆时是否记录日志
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

 #避免混淆泛型
-keepattributes Singature

-keepattributes EnclosingMethod
 #不混淆注释
-keepattributes *Annotation*
 #不混淆R文件
-keep class **.R$* { *; }

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

 #保护异常，内部类等
-keepattributes Exceptions,InnerClasses
-keepattributes SourceFile,LineNumberTable


 #忽略警告
-ignorewarning

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.os.IInterface

-keep class com.ronghui.ronghui_library.util.** {*;}
-keep class com.ronghui.ronghui_library.adapter.** {*;}
-keep class com.ronghui.ronghui_library.bean.** {*;}
-keep class com.ronghui.ronghui_library.constant.** {*;}
-keep class com.ronghui.ronghui_library.base.** {*;}


 #忽略单独警告
-dontwarn in.srain.cube.**

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn android.support.**
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v4.app.Fragment

-dontwarn android.support.**
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.v7.app.Fragment

-dontwarn com.alibaba.fastjson.**

-keep class com.alibaba.fastjson.** { *; }

#-keepnames class * implements java.io.Serializable
-keep public class * implements java.io.Serializable {
        public *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-libraryjars libs/commons-lang3-3.1.jar
-libraryjars libs/nohttp1.0.4-include-source.jar
-libraryjars libs/org.apache.commons.codec.jar
-libraryjars libs/sun.misc.BASE64Decoder.jar


-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**

-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-dontwarn Decoder.**
-keep class Decoder.**{*;}
-dontwarn com.github.bumptech.glide.**
-keep class com.github.bumptech.glide.** {*;}
-dontwarn com.facebook.fresco.**
-keep class com.facebook.fresco.** {*;}
-dontwarn com.yolanda.nohttp.**
-keep class com.yolanda.nohttp.** {*;}
-dontwarn com.facebook.**
-keep class com.facebook.** {*;}
-dontwarn org.apache.commons.lang3.**
-keep class org.apache.commons.lang3.** {*;}
-dontwarn org.apache.commons.codec.**
-keep class org.apache.commons.codec.** {*;}

