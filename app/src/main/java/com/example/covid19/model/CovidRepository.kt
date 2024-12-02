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

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder()
            .url("https://covid-193.p.rapidapi.com/statistics")
            .get()
            .addHeader("x-rapidapi-key", "3f6a0e55b3msh97ad02785045ee7p188c8bjsn676a41358d03")
            .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
            .build()
        //Execution de la requête
        return client.newCall(request).execute().use { //it:Response
            //use permet de fermer la réponse qu'il y ait ou non une exception
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}\n${it.body.string()}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }
    fun loadCovid(): List<CovidBean> {
        //verification
        //requete
        val json = sendGet(URL_API_COVID.format())
        //Parsing
        val data = gson.fromJson(json, CovidResultBean::class.java)
        // traitement, je remplace le nom de l'icone par l'url
        return data.list
    }
}