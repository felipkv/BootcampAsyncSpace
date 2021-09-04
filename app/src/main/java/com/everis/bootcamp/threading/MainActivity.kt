package com.everis.bootcamp.threading

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: 018 - fazer o handle do clique do botão
        button_load_data.setOnClickListener {
            launchAstrosTask()
        }
    }


    //TODO: 013 - Criar função para exibir os dados carregados
    fun showData(list: List<AstroPeople>) {
        textview_data.text = "" // limpar a caixa de texto toda vez que apertar o botão antes de gerar a nova resposta(append juntaria tudo numa lista)
        list?.forEach { people ->
            textview_data.append("${people.name} - ${people.craft} \n\n")
        }
    }

    //TODO: 014 - Criar função para exibir a ProgressBar
    fun showLoadingIndicator() {
        progressbar_load_indicator.visibility = View.VISIBLE
    }

    //TODO: 015 - Criar função para esconder a ProgressBar
    fun hideLoadingIndicator() {
        progressbar_load_indicator.visibility = View.GONE
    }

    //TODO: 017 - Criar função para lançar a Task
    fun launchAstrosTask() {
        val task = TaskAstros()
        task.execute()
    }

    //TODO: 016 - Criar classe interna para rodar a tarefa assincrona
    inner class TaskAstros() : AsyncTask<Void, Int, List<AstroPeople>>() {
        private val repository = AstrosRepository()

        override fun onPreExecute() { // PreExecute = momento antes da tarefa ser lançada, ainda se pode fazer mudanças na UI
            super.onPreExecute()
            showLoadingIndicator()
        }

        override fun doInBackground(vararg params: Void?): List<AstroPeople>? { // lançar a thread secundária e vai executar em background
            return repository.loadData() // retorna uma lista de People
        }

        override fun onPostExecute(result: List<AstroPeople>?) { // quando a thread secundária termina, ela synca com a principal e a UI é atualizada
            super.onPostExecute(result)
            hideLoadingIndicator()
            if (result != null) {
                showData(result)
            }
        }
    }
}
