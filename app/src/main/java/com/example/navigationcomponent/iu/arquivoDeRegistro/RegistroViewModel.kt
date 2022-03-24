package com.example.navigationcomponent.iu.arquivoDeRegistro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponent.R

class RegistroViewModel: ViewModel() {
    sealed class EstadoDoRegistro{
        object ArquivoColetaData: EstadoDoRegistro()
        object ColetaCredenciais: EstadoDoRegistro()
        object RegistroCompleto: EstadoDoRegistro()
        class ArquivoInvalidoData(val campos: List<Pair<String, Int>>): EstadoDoRegistro()
        class CredenciaisInvalidas(val campos: List<Pair<String, Int>>): EstadoDoRegistro()
    }

    private val _eventoEstadoRegistro = MutableLiveData<EstadoDoRegistro>(EstadoDoRegistro.ArquivoColetaData)
    val eventoEstadoDoRegistro: LiveData<EstadoDoRegistro>
        get() = _eventoEstadoRegistro

    var authToken  = ""
        private set

    fun arquivoColetaData(name: String, bio: String){
        if (isValidArquivoData(name,bio)){
            _eventoEstadoRegistro.value = EstadoDoRegistro.ColetaCredenciais
        }
    }
    private fun isValidArquivoData(name: String, bio: String): Boolean{
        val CamposInvalidos = arrayListOf<Pair<String, Int>>()
        if (name.isEmpty()){
            CamposInvalidos.add(INPUT_NAME)
        }
        if (bio.isEmpty()){
            CamposInvalidos.add(INPUT_BIO)
        }
        if (CamposInvalidos.isNotEmpty()){
            _eventoEstadoRegistro.value = EstadoDoRegistro.ArquivoInvalidoData(CamposInvalidos)
            return false
        }
        return true
    }
    fun createCredenciais(username: String, password: String){
        if(isInvalidaCredencial(username, password)){
            this.authToken = "token"
            _eventoEstadoRegistro.value = EstadoDoRegistro.RegistroCompleto
        }
    }
    private fun isInvalidaCredencial(username: String,password: String):Boolean{
        val CamposInvalidos = arrayListOf<Pair<String, Int>>()
        if (username.isEmpty()){
            CamposInvalidos.add(INPUT_USERNAME)
        }
        if (password.isEmpty()){
            CamposInvalidos.add(INPUT_PASSORD)
        }
        if (CamposInvalidos.isNotEmpty()){
            _eventoEstadoRegistro.value = EstadoDoRegistro.CredenciaisInvalidas(CamposInvalidos)
            return false
        }
        return true
    }

    fun userCancelaRegistro(): Boolean{
        authToken = ""
        _eventoEstadoRegistro.value = EstadoDoRegistro.ArquivoColetaData
        return true
    }

    companion object{
        val INPUT_NAME = "INPUT_NAME" to R.string.nome_invalido_layout_entrada_arquivoData
        val INPUT_BIO = "INPUT_BIO" to R.string.bio_invalida_layout_entrada_arquivoData
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.username_invalido_layout_entrada_credenciais
        val INPUT_PASSORD = "INPUT_PASSWORD" to R.string.password_invalido_layout_entrada_credenciais
    }
}