package no.realitylab.arface.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import no.realitylab.arface.R
import no.realitylab.arface.adapter.ItemAdapter
import no.realitylab.arface.providers.ItemProvider
import no.realitylab.arface.viewmodels.ItemViewModel
import no.realitylab.arface.viewmodels.UserViewModel


class ModelsListFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val itemViewModel : ItemViewModel by activityViewModels()
    private lateinit var inflate: View
    private lateinit var adapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_models_list, container, false)
        initRecyclerView()

        return inflate
    }

    private fun initRecyclerView(){
        recyclerView = inflate.findViewById(R.id.recycler_models)
        adapter = ItemAdapter(itemViewModel.models.value ?: emptyList() )
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
    }

    companion object {

        const val ARG_MODEL_ID = "param1"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModelsListFragment().apply {
                arguments = Bundle()
            }
    }
}