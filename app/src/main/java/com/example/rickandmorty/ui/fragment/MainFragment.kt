package com.example.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.characters.CharactersList
import com.example.rickandmorty.databinding.FragmentMainBinding
import com.example.rickandmorty.ui.model.app_view_model.NavigationViewModel
import com.example.rickandmorty.ui.model.request_view_model.CharactersListViewModel
import com.example.rickandmorty.ui.recycleview.adapter.CharactersListAdapter
import com.example.rickandmorty.ui.recycleview.core.BaseAdapter
import com.example.rickandmorty.ui.recycleview.decorations.GroupVerticalItemDecoration
import com.example.rickandmorty.ui.recycleview.model.CharactersListDataModel
import com.example.rickandmorty.utils.BackButtonListener
import com.example.rickandmorty.utils.common.extensions.insetterRecyclerBottom
import com.example.rickandmorty.utils.common.extensions.px
import com.example.rickandmorty.utils.nullOnDestroy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), BackButtonListener {

    private var binding: FragmentMainBinding by nullOnDestroy()

    private val navigationViewModel by viewModels<NavigationViewModel>()
    private val charactersListViewModel by viewModels<CharactersListViewModel>()

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment().apply {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.charactersListViewModel = charactersListViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.insetterRecyclerBottom()

        val adapter = BaseAdapter(listOf(CharactersListAdapter(::onCharactersClick)))

        lifecycleScope.launchWhenCreated {
            binding.recycler.adapter = adapter
            binding.recycler.addItemDecoration(
                GroupVerticalItemDecoration(
                    R.layout.item_characters_list,
                    0,
                    12.px
                )
            )
        }
    }

    private fun onCharactersClick(newItem: CharactersListDataModel) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed(): Boolean {
        TODO("Not yet implemented")
    }
}