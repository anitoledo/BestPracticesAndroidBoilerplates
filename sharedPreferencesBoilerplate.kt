class Prefs (context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences("com.punk.myeventkotlin.prefs", 0);

    var TOKEN: String
        get() = prefs.getString("TOKEN", "")
        set(value) = prefs.edit().putString("TOKEN", value).apply()
}