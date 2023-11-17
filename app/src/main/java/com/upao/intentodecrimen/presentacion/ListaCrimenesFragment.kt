package com.upao.intentodecrimen.presentacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.upao.intentodecrimen.R
import com.upao.intentodecrimen.databinding.FragmentListaCrimenesBinding
import com.upao.intentodecrimen.datos.ListaCrimenesViewModel
import com.upao.intentodecrimen.modelo.Crimen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class ListaCrimenesFragment : Fragment() {
    private var _binding: FragmentListaCrimenesBinding? = null
    private val binding get() = _binding!!

    private val listaCrimenesViewModel: ListaCrimenesViewModel by viewModels()
    private lateinit var crimenAdapter: CrimenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListaCrimenesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            listaCrimenesViewModel.crimenes.collect { crimenes ->
                crimenAdapter.actualizarCrimenes(crimenes)
            }
        }
    }

    private fun setupRecyclerView() {
        crimenAdapter = CrimenAdapter(mutableListOf()) { crimenId ->
            // Manejar clic en un crimen
            val action = ListaCrimenesFragmentDirections.irACrimen(crimenId)
            findNavController().navigate(action)
        }
        binding.crimenRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = crimenAdapter
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nuevo_crimen -> {
                mostrarNuevoCrimen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun mostrarNuevoCrimen() {
        viewLifecycleOwner.lifecycleScope.launch {
            val nuevoCrimen = Crimen(
                id = UUID.randomUUID(),
                titulo = "",
                fecha = Date(),
                resuelto = false
            )
            listaCrimenesViewModel.ingresarCrimen(nuevoCrimen)
            val action = ListaCrimenesFragmentDirections.irACrimen(nuevoCrimen.id)
            findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragmento_lista_crimenes, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
