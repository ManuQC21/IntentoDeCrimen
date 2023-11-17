package com.upao.intentodecrimen.presentacion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.upao.intentodecrimen.R
import com.upao.intentodecrimen.databinding.FragmentCrimenBinding
import com.upao.intentodecrimen.datos.CrimenViewModelFactory
import com.upao.intentodecrimen.datos.CrimenViewModel
import com.upao.intentodecrimen.modelo.Crimen
import kotlinx.coroutines.launch
import java.util.Date

private const val TAG="registroCriminall"
class CrimenFragment : Fragment() {
    private var _binding: FragmentCrimenBinding? = null
    private val binding get() = _binding!!

    private val args: CrimenFragmentArgs by navArgs()
    private val crimenViewModel: CrimenViewModel by viewModels {
        CrimenViewModelFactory(args.crimenId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    //se agrego:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_crimen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete_crimen -> {
                crimenViewModel.eliminarCrimen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //hasta aqui

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isTituloValido()) {
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "El título no puede estar en blanco", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //se agrego:
        binding.btnHoraCrimen.setOnClickListener {
            mostrarSelectorHora()
        }

        callback.isEnabled = true
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.apply {
            txtTituloCrimen.doOnTextChanged { texto, _, _, _ ->
                crimenViewModel.actualizarCrimen { anterior ->
                    anterior.copy(titulo = texto.toString())
                }
            }

            btnFechaCrimen.apply {
                isEnabled = true
            }

            chkCrimenResuelto.setOnCheckedChangeListener { _, seleccionado ->
                crimenViewModel.actualizarCrimen { anterior ->
                    anterior.copy(resuelto = seleccionado)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                crimenViewModel.crimen.collect { crimen ->
                    crimen?.let { actualizarUI(crimen) }
                }
            }
        }
        setFragmentResultListener(DatePickerFragment.CLAVE_FECHA_SOLICITADA){
            _, bundle ->
            val nuevaFecha=bundle.getSerializable(DatePickerFragment.CLAVE_FECHA_BUNDLE) as Date
            crimenViewModel.actualizarCrimen { it.copy(fecha = nuevaFecha) }
        }
    }
    private fun isTituloValido(): Boolean {
        // Verifica si el título del crimen es válido (no está en blanco)
        return binding.txtTituloCrimen.text.isNotBlank()
    }

    private fun actualizarUI(crimen: Crimen) {
        binding.apply {
            if (txtTituloCrimen.text.toString() != crimen.titulo) {
                txtTituloCrimen.setText(crimen.titulo)
            }
            btnFechaCrimen.text = crimen.fecha.toString()
            btnFechaCrimen.setOnClickListener {
                findNavController().navigate(
                    CrimenFragmentDirections.irADatePicker(crimen.fecha)
                )
            }
            chkCrimenResuelto.isChecked = crimen.resuelto
        }
    }
    //se agrego:

    private fun mostrarSelectorHora() {
        val fragmentoTimePicker = FragmentoTimePicker()
        fragmentoTimePicker.show(parentFragmentManager, "timePicker")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
