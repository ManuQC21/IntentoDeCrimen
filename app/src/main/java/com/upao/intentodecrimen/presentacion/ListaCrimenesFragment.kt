package com.upao.intentodecrimen.presentacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upao.intentodecrimen.R
import com.upao.intentodecrimen.databinding.FragmentListaCrimenesBinding
import com.upao.intentodecrimen.datos.ListaCrimenesViewModel
import com.upao.intentodecrimen.modelo.Crimen
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "ListaCrimenesFragment"
class ListaCrimenesFragment : Fragment() {
    private lateinit var binding: FragmentListaCrimenesBinding
    private lateinit var crimenRecyclerView:RecyclerView
    private var crimenAdapter:CrimenAdapter?=null
    private val listaCrimenesViewModel: ListaCrimenesViewModel by viewModels()
    private var _binding:FragmentListaCrimenesBinding?=null

        get() = checkNotNull(_binding){

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentListaCrimenesBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                /*listaCrimenesViewModel.ingresarCrimen(
                    Crimen(
                    UUID.randomUUID(),
                    "Juicio Caso 3",
                    Date(),
                    false)
                )*/
                listaCrimenesViewModel.crimenes.collect(){ crimenes ->
                    binding.crimenRecyclerView.adapter=CrimenAdapter(crimenes){crimenId ->
                        findNavController().navigate(ListaCrimenesFragmentDirections.irACrimen(crimenId))
                    }
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        _binding = FragmentListaCrimenesBinding.inflate(layoutInflater,container,false)
        binding.crimenRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
    override fun onDestroyView(){
        super.onDestroyView()
        _binding=null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragmento_lista_crimenes,menu)
    }
}
