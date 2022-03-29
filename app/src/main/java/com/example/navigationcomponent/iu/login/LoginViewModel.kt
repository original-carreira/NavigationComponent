package com.example.navigationcomponent.iu.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponent.R

class LoginViewModel : ViewModel() {

    sealed class EstadoAutenticacao {
        object NaoAutenticado: EstadoAutenticacao()
        object AutenticacaoValida: EstadoAutenticacao()
        class AutenticacaoInvalida(  val campos:List<Pair<String, Int>> ): EstadoAutenticacao()
        /*ID da string de erro.*Aqui vai se mapeado uma msg de erro para o campo */
    }

    var username: String = ""
    var token: String = ""

    private val _EventoEstadoAutenticacao = MutableLiveData<EstadoAutenticacao>()
    val EventoEstadoAutenticacao: LiveData<EstadoAutenticacao>
        get() = _EventoEstadoAutenticacao

    init {
       autenticacaoRecusada()
    }
    fun autenticacaoRecusada(){
        _EventoEstadoAutenticacao.value = EstadoAutenticacao.NaoAutenticado
    }

    fun autenticaToke(token: String, username: String){
        this.token = token
        this.username = username
        _EventoEstadoAutenticacao.value = EstadoAutenticacao.AutenticacaoValida
    }

    fun authentication (username:String, password:String){
        if (validacaoDadosEntrada(username, password)){
            this.username = username
            _EventoEstadoAutenticacao.value = EstadoAutenticacao.AutenticacaoValida
//usuario autenticado
        }
    }

    private fun validacaoDadosEntrada (username: String, password: String): Boolean {
        val camposInvalidos = arrayListOf<Pair<String, Int>>()
        if (username.isEmpty()){
            camposInvalidos.add(INPUT_USERNAME)
        }
        if (password.isEmpty()){
            camposInvalidos.add(INPUT_PASSWORD)
        }
    /* Vai verificar se um campo não está valido*/
        if (camposInvalidos.isNotEmpty()){
            _EventoEstadoAutenticacao.value = EstadoAutenticacao.AutenticacaoInvalida(camposInvalidos)
            return false
        }
        return true
    }

    companion object{
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.texto_error_invalid_username
        val INPUT_PASSWORD = "INPUT_PASSWORD" to R.string.texto_error_invalid_password
    }
}

/*##### Representação do Estado do Usuário, se está logado ou não (Todos os possíveis estados de login) ######
*###### Faz uma validação de autenticação                         #####*/
/*###### fun validacaodadosEntrada -> Fará a validação dos dados inseridos nos campos de usuário e senha, retornando msg de erro #####*/