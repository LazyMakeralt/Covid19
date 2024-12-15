package com.example.covid19.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    val covidList = CovidRepository.loadCovid()

    println(covidList)
    }


object CovidRepository{
    val client = OkHttpClient()
    val gson = Gson()

    const val URL_API_COVID = "https://covid-193.p.rapidapi.com/statistics"


    fun sendGet(url: String): String {
        println("url : $url")
        val request = Request.Builder()
            .url("https://covid-193.p.rapidapi.com/statistics")
            .get()
            .addHeader("x-rapidapi-key", "3f6a0e55b3msh97ad02785045ee7p188c8bjsn676a41358d03")
            .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
            .build()
        //Execution of req
        return client.newCall(request).execute().use { //it:Response

            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}\n${it.body.string()}")
            }
            it.body.string()
        }
    }
    fun loadCovid(): List<CovidBean> {
        val json = sendGet(URL_API_COVID.format())
        val data = gson.fromJson(json, CovidResultBean::class.java)

        return data.list
    }
}