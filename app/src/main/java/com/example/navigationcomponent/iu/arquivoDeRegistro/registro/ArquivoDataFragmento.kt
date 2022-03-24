package com.example.navigationcomponent.iu.arquivoDeRegistro.registro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.navigationcomponent.databinding.FragmentArquivoDataFragmentoBinding
import com.example.navigationcomponent.iu.arquivoDeRegistro.RegistroViewModel

class ArquivoDataFragmento : Fragment() {
    private var _binding: FragmentArquivoDataFragmentoBinding? = null
    private val binding get() = _binding!!
    private val registroViewModel: RegistroViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArquivoDataFragmentoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registroViewModel.eventoEstadoDoRegistro.observe(viewLifecycleOwner, Observer {estadoDoRegistro ->
            when(estadoDoRegistro){
                is RegistroViewModel.EstadoDoRegistro.ColetaCredenciais -> {

                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}