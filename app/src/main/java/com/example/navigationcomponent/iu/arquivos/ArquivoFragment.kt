package com.example.navigationcomponent.iu.arquivos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.navigationcomponent.R
import com.example.navigationcomponent.databinding.FragmentArquivoBinding
import com.example.navigationcomponent.extensoes.navigateComAnimacao
import com.example.navigationcomponent.iu.login.LoginViewModel

class ArquivoFragment : Fragment(R.layout.fragment_arquivo) {
    private var _binding: FragmentArquivoBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArquivoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.EventoEstadoAutenticacao.observe(viewLifecycleOwner, Observer {
            estadoautenticacao ->
            when (estadoautenticacao){
                is LoginViewModel.EstadoAutenticacao.AutenticacaoValida -> {
                    binding.textoFragmentoArquivo.text  = getString(R.string.texto_nomeUsuario, loginViewModel.username)
                }
                is LoginViewModel.EstadoAutenticacao.NaoAutenticado -> {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        })
        binding.buttonAcaoArquivo.setOnClickListener {
            findNavController().navigateComAnimacao(R.id.action_arquivoFragment_to_startFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}