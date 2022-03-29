package com.example.navigationcomponent.iu.arquivoDeRegistro.registro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.navigationcomponent.R
import com.example.navigationcomponent.databinding.FragmentArquivoDataFragmentoBinding
import com.example.navigationcomponent.extensoes.dismissError
import com.example.navigationcomponent.iu.arquivoDeRegistro.RegistroViewModel
import com.google.android.material.textfield.TextInputLayout

class ArquivoDataFragmento : Fragment() {
    private var _binding: FragmentArquivoDataFragmentoBinding? = null
    private val binding get() = _binding!!
    private val registroViewModel: RegistroViewModel by activityViewModels()
    private val navController: NavController by lazy { findNavController() }

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
        setHasOptionsMenu(true)

        val validaCampos = iniciaValidaCampos()
        ouvirEventosRegistroViewlModel(validaCampos)
        ouvirRegistroDaView()
        registroDeviceBackStackCallBack()

    }

    private fun iniciaValidaCampos() = mapOf(
            RegistroViewModel.INPUT_NAME.first to binding.entradaNameLayoutArquivoData,
            RegistroViewModel.INPUT_BIO.first to binding.entradaBioLayoutArquivoData
        )

    private fun ouvirEventosRegistroViewlModel (validaCampos: Map<String, TextInputLayout>){
            registroViewModel.eventoEstadoDoRegistro.observe(viewLifecycleOwner, Observer {estadoDoRegistro ->
                when(estadoDoRegistro){
                    is RegistroViewModel.EstadoDoRegistro.ColetaCredenciais -> {
                        val name = binding.entradaNameArquivoData.text.toString()
                        val directions = ArquivoDataFragmentoDirections.actionArquivoDataFragmento2ToCredenciaisFragmento(name)
                        findNavController().navigate(directions)
                    }
                    is RegistroViewModel.EstadoDoRegistro.ArquivoInvalidoData -> {
                        //val validaCampos: Map<Pair<String, Int>, TextInputLayout> = iniciaValidaCampos()
                        estadoDoRegistro.campos.forEach {
                            campoError -> validaCampos[campoError.first]?.error = getString(campoError.second)
                        }
                    }
                }
            })
        }

    private fun ouvirRegistroDaView(){
        binding.botaoProximoArquivoData.setOnClickListener {
            val name = binding.entradaNameArquivoData.text.toString()
            val arquivoBio = binding.entradaBioArquivoData.text.toString()
            registroViewModel.arquivoColetaData(name, arquivoBio)
        }
        binding.entradaNameArquivoData.addTextChangedListener{
            binding.entradaNameLayoutArquivoData.dismissError()
        }
        binding.entradaBioArquivoData.addTextChangedListener {
            binding.entradaBioLayoutArquivoData.dismissError()
        }
    }

    private fun registroDeviceBackStackCallBack(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            cancelaRegistro()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        cancelaRegistro()
        return true
    }

    private fun cancelaRegistro(){
        registroViewModel.userCancelaRegistro()
        navController.popBackStack(R.id.loginFragment,false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}