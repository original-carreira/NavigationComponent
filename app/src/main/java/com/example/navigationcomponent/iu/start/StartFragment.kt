package com.example.navigationcomponent.iu.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.navigationcomponent.R
import com.example.navigationcomponent.databinding.FragmentStartBinding
import com.example.navigationcomponent.extensoes.navigateComAnimacao


class StartFragment : Fragment(R.layout.fragment_start) {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStartBinding.inflate(
            inflater,container,false
        )
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            findNavController().navigateComAnimacao(R.id.action_startFragment_to_arquivoFragment)
        }
        binding.buttonEnd.setOnClickListener {
            findNavController().navigateComAnimacao(R.id.action_startFragment_to_reciboFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}