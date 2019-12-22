package com.anlethanh.news.commons

import android.content.Context
import android.util.Patterns
import com.anlethanh.news.MyApplication
import com.anlethanh.news.R
import com.anlethanh.news.models.Profile
import com.google.gson.GsonBuilder
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class Helper {
    val FILE = this::class.java.simpleName + ".FILE"
    val PROFILE = this::class.java.simpleName + ".PROFILE"
    var apiKey: String = ""
    var profile: Profile? = null

    fun load(myApplication: MyApplication) {
        apiKey = myApplication.applicationContext.getString(R.string.apiKey)
    }

    fun saveData(context: Context, value: Profile) {
        val settings = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(PROFILE, GsonBuilder().create().toJson(value))
        editor.commit()
        profile = value
    }

    fun removeData(context: Context) {
        val settings = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(PROFILE, "")
        editor.commit()
        profile = null
    }

    fun validEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun loadData(context: Context) {
        val settings = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        settings.getString(PROFILE, "")?.run {
            if (this.isNotEmpty())
                profile = GsonBuilder().create().fromJson(this, Profile::class.java)
        }
    }

    fun getDateAfter(days: Int): String {
        return DateTimeFormat.forPattern("yyyy-MM-dd").print(DateTime.now().minusDays(days));
    }

    fun getStringDate(date: String): String {
        return DateTimeFormat.forPattern("E, dd MMM yyyy HH:mm:ss z").print(
            DateTime.parse(date)
        );
    }
}