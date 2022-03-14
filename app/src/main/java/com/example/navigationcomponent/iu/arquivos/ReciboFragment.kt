package com.example.navigationcomponent.iu.arquivos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.navigationcomponent.R
import com.example.navigationcomponent.databinding.FragmentReciboBinding
import com.example.navigationcomponent.extensoes.navigateComAnimacao
import java.util.zip.Inflater

class ReciboFragment : Fragment(R.layout.fragment_recibo) {
    private  var _binding: FragmentReciboBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReciboBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAcaoRecibo.setOnClickListener {
            findNavController().navigateComAnimacao(R.id.action_reciboFragment_to_arquivoFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}