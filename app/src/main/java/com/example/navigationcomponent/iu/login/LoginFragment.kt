package com.example.navigationcomponent.iu.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.navigationcomponent.R
import com.example.navigationcomponent.databinding.LoginFragmentBinding
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment(R.layout.login_fragment) {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.EventoEstadoAutenticacao.observe(viewLifecycleOwner, Observer {estadoautenticacao ->
            when(estadoautenticacao) {
                is LoginViewModel.EstadoAutenticacao.AutenticacaoValida -> {
                    findNavController().popBackStack()
                }
                is LoginViewModel.EstadoAutenticacao.AutenticacaoInvalida -> {
                    //tratando o erro de autenticação invalida
                    val validacaoCampos:Map<String, TextInputLayout> = iniciaValidacaoDeCampos()
                    estadoautenticacao.campos.forEach { CampoError -> validacaoCampos[CampoError.first]?.error = getString(CampoError.second)}

                }
            }
        })

        binding.buttonLoginConfirma.setOnClickListener {
            val username = binding.entradaCampoLogin.text.toString()
            val password = binding.entradaCampoPassword.text.toString()
            viewModel.authentication(username,password)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            cancelaAutenticacao()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cancelaAutenticacao()
        return true
    }
    private fun cancelaAutenticacao(){
        viewModel.autenticacaoRecusada()
        findNavController().popBackStack(R.id.startFragment,false)
    }

    /* vincula os componentes do companhion object com as constantes do loginViewModel*/
    private fun iniciaValidacaoDeCampos() = mapOf(
        LoginViewModel.INPUT_USERNAME.first to binding.entradaLayoutCampoLogin,
        LoginViewModel.INPUT_PASSWORD.first to binding.entradaLayoutCampoPassword
    )
}
/*
* //viewModel = ViewModelProvider(this).get(LoginViewModel::class.java) - ja foi inciado e informado que o escopo da activit é a MainActitivt
*it: LoginViewModel.EstadoAutenticacao
*estou agrupando todos os estados pra observação!
*/
/*it: Pair<String,Int>, nomei como campoError.
*Feito um mapeamento usando as chaves do ViewModel(que e referenciada a uma string de erro) com as
*chaves do loginfragement(que é referenciada ao componente de layout do xml;
*/
//setHasOptionsMenu(true) -> indica ao aplicativo que tem uma opção que pode ser clicada (onOptionsItemSelected)