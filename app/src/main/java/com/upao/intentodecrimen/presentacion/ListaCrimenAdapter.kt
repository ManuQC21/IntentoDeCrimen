package com.upao.intentodecrimen.presentacion

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upao.intentodecrimen.databinding.ItemViewBinding
import com.upao.intentodecrimen.modelo.Crimen
import java.util.UUID

class CrimenHolder(
    val binding:ItemViewBinding
): RecyclerView.ViewHolder(binding.root){
    fun enlazar(crimen: Crimen, onCrimenPulsado: (crimenId: UUID) -> Unit){
        binding.apply{
            tituloCrimen.text=crimen.titulo
            fechaCrimen.text= DateFormat.format("dd/MM/yy",crimen.fecha).toString()
            root.setOnClickListener{
                //Toast.makeText(root.context,"${crimen.titulo}", Toast.LENGTH_LONG).show()
                onCrimenPulsado(crimen.id)
            }
            imageView.visibility=if(crimen.resuelto){View.VISIBLE} else { View.GONE}
        }
    }
}
class CrimenAdapter(private val crimenes: List<Crimen>,
                    private val onCrimenPulsado:(crimenId: UUID)-> Unit): RecyclerView.Adapter<CrimenHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): CrimenHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding = ItemViewBinding.inflate(inflater,parent,false)
        return CrimenHolder(binding)
    }
    override fun getItemCount()=crimenes.size
    override fun onBindViewHolder(holder: CrimenHolder, position: Int){
        val crimen =crimenes.get(position)
        holder.enlazar(crimen,onCrimenPulsado)
    }
}
