package com.example.navigationcomponent.iu.start

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigationcomponent.R
import com.example.navigationcomponent.databinding.FragmentStartBinding


class StartFragment : Fragment(R.layout.fragment_start) {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: OnButtonclick

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStartBinding.inflate(
            inflater,container,false
        )
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            listener.buttonClicked()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnButtonclick){
            listener = context
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        fun newInstance ():StartFragment{
            return StartFragment()
        }
    }
    interface OnButtonclick{
        fun buttonClicked ()
    }
}