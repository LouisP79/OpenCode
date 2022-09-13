# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable
# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile
# # -------------------------------------------
# #  ############### MODELS ###############
# # -------------------------------------------
-keepclassmembers class com.quickstore.data.** {*;}
-keepclassmembers class com.quickstore.ui.useCase.main.model.** {*;}
# # -------------------------------------------
# #  ############### Glide ###############
# # -------------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
 **[] $VALUES;
 public *;
}
# # -------------------------------------------
# #  ############### Retrofit ###############
# # -------------------------------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
# # -------------------------------------------
# #  ############### Jackson ###############
# # -------------------------------------------
-keep class com.fasterxml.jackson.databind.ObjectMapper {
    public <methods>;
    protected <methods>;
}
-keep class com.fasterxml.jackson.databind.ObjectWriter {
    public * writeValueAsString(*);
}
# # -------------------------------------------
# #  ############### KOIN ###############
# # -------------------------------------------
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
# # -------------------------------------------
# #  ############### Okhttp3 ###############
# # -------------------------------------------
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
# # -------------------------------------------
# #  ############### Okio ###############
# # -------------------------------------------
-dontwarn org.codehaus.mojo.animal_sniffer.*
# # -------------------------------------------
# #  ############### RXKotlin ###############
# # -------------------------------------------
-keep class rx.schedulers.Schedulers {
     public static <methods>;
 }
 -keep class rx.schedulers.ImmediateScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.TestScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.Schedulers {
     public static ** test();
 }
## -------------------------------------------
# #  ############### Gson ###############
# # -------------------------------------------
-keepattributes Annotation
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
 ## -------------------------------------------
 # #  ############### FloatingSearchView ###############
 # # -------------------------------------------
 -keepclassmembers class com.arlib.** { *; }
## -------------------------------------------
# #  ############### Crashlytics ###############
# # -------------------------------------------
 -keep class com.crashlytics.** { *; }
 -keep class com.crashlytics.android.**
 -keepattributes SourceFile, LineNumberTable, *Annotation*
 -keep public class * extends java.lang.Exception
