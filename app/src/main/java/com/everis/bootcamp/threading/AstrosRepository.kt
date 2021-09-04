package com.everis.bootcamp.threading

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request


//TODO: 010 - Criar a classe responsável por carregar os dados
class AstrosRepository {

    //TODO: 011 - Criar função para carregar os astronautas
    fun loadData(): List<AstroPeople>? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://api.open-notify.org/astros.json")
            .build()
        val response = client.newCall(request).execute()
        val result = parseJsonToResult(response.body?.string()) // body = conteúdo do json
        return result.people
    }

    //TODO: 012 - Criar função para converter o response da função acima para json(AstrosResult)
    fun parseJsonToResult(json: String?) =
        Gson().fromJson(json, AstrosResult::class.java)
}