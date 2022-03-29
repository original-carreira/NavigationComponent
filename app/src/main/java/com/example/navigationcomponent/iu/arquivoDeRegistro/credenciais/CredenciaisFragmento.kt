package com.example.navigationcomponent.iu.arquivoDeRegistro.credenciais

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationcomponent.R
import com.example.navigationcomponent.databinding.FragmentCredenciaisFragmentoBinding
import com.example.navigationcomponent.extensoes.dismissError
import com.example.navigationcomponent.iu.arquivoDeRegistro.RegistroViewModel
import com.example.navigationcomponent.iu.login.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class CredenciaisFragmento : Fragment() {
    private var _binding: FragmentCredenciaisFragmentoBinding? = null
    private val binding get() = _binding!!
    private val registroViewModel: RegistroViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val args: CredenciaisFragmentoArgs by navArgs()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCredenciaisFragmentoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.textoNomeTrocarCredenciais.text = getString(R.string.texto_credenciais_name,args.nameUser)

        val camposInvalidos = iniciaValidacaoCampos()
        ouvirEventosDoEstadoDeRegistro(camposInvalidos)
        ouvirRegistroDaView()
        registerDeviceBackStack()
    }

    private fun iniciaValidacaoCampos() = mapOf (
        RegistroViewModel.INPUT_USERNAME.first to binding.entradaUsernameLayoutCredenciais,
        RegistroViewModel.INPUT_PASSORD.first to binding.entradaPasswordLayoutCredenciais
    )

    private fun ouvirEventosDoEstadoDeRegistro(validacaoCampos: Map<String, TextInputLayout>){
        registroViewModel.eventoEstadoDoRegistro.observe(viewLifecycleOwner, Observer { registroStado ->
            when(registroStado) {
                is  RegistroViewModel.EstadoDoRegistro.RegistroCompleto -> {
                    val token = registroViewModel.authToken
                    val username = binding.entradaUsernameCredencias.text.toString()

                    loginViewModel.autenticaToke(token, username)
                    navController.popBackStack(R.id.arquivoFragment, false)
                }
                is RegistroViewModel.EstadoDoRegistro.CredenciaisInvalidas -> {
                    registroStado.campos.forEach { campoError -> validacaoCampos[campoError.first]?.error = getString(campoError.second
                    ) }
                }
            }
        })
    }

    private  fun ouvirRegistroDaView(){
        binding.botaoFinalizarCredenciais.setOnClickListener {
            val username = binding.entradaUsernameCredencias.text.toString()
            val password = binding.entradaPasswordCredenciais.text.toString()

            registroViewModel.createCredenciais(username, password)
        }
        binding.entradaPasswordCredenciais.addTextChangedListener {
            binding.entradaPasswordLayoutCredenciais.dismissError()
        }
        binding.entradaUsernameCredencias.addTextChangedListener {
            binding.entradaUsernameLayoutCredenciais.dismissError()
        }
    }

    private fun registerDeviceBackStack(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            cancelaRegistro()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cancelaRegistro()
        return super.onOptionsItemSelected(item)
    }

    private fun cancelaRegistro(){
        registroViewModel.userCancelaRegistro()
        navController.popBackStack(R.id.loginFragment, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
