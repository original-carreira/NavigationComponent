package com.example.navigationcomponent.iu.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponent.R

class LoginViewModel : ViewModel() {
/*##### Representação do Estado do Usuário, se está logado ou não (Todos os possíveis estados de login) ######
*###### Faz uma validação de autenticação                         #####*/

    sealed class EstadoAutenticacao {
        object NaoAutenticado: EstadoAutenticacao()
        object AutenticacaoValida: EstadoAutenticacao()
        class AutenticacaoInvalida(
            val campos:List<Pair<String, Int>> /*ID da string de erro.
                                                *Aqui vai se mapeado uma msg de erro para o campo */
        ): EstadoAutenticacao()
    }

    val EventoEstadoAutenticacao = MutableLiveData<EstadoAutenticacao>()
    var nomeUsuario:String = ""
    init {
       autenticacaoRecusada()
    }
    fun autenticacaoRecusada(){
        EventoEstadoAutenticacao.value = EstadoAutenticacao.NaoAutenticado
    }
    fun authentication (username:String, password:String){
        if (validacaoDadosEntrada(username, password)){
            this.nomeUsuario = nomeUsuario
            EventoEstadoAutenticacao.value = EstadoAutenticacao.AutenticacaoValida
//usuario autenticado
        }
    }

/*###### Fará a validação dos dados inseridos nos campos de usuário e senha, retornando msg de erro #####*/

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
            EventoEstadoAutenticacao.value = EstadoAutenticacao.AutenticacaoInvalida(camposInvalidos)
            return false
        }
        return true
    }

    companion object{
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.texto_error_invalid_username
        val INPUT_PASSWORD = "INPUT_PASSWORD" to R.string.texto_error_invalid_password
    }
}